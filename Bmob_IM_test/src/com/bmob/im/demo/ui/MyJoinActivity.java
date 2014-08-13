package com.bmob.im.demo.ui;

import android.os.*;
import android.text.format.*;
import android.widget.*;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.*;
import com.bmob.im.demo.util.*;
import com.handmark.pulltorefresh.library.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

/**
 * 我加入的请客
 * 
 * @Title: MyJoinActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 下午5:52:14
 * @version V1.0
 */
public class MyJoinActivity extends BaseActivity {
	
	public static final int HTTP_REQUEST_SUCCESS = -1;
	
	public static final int HTTP_REQUEST_ERROR = 0;
	
	private PullToRefreshListView pullToRefreshListView = null;
	
	private ActivityListAdapter newAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_join);
		newAdapter = new ActivityListAdapter(this, NewsUtil.getSimulationNews(10),"myjoin");
		pullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.list_activity);
		initTopBarForLeft("我加入的请客");
		initPullToRefreshListView(pullToRefreshListView, newAdapter);
	}

	private void initPullToRefreshListView(PullToRefreshListView rtflv,
			ActivityListAdapter adapter) {
		rtflv.setMode(Mode.PULL_FROM_START);
		rtflv.setOnRefreshListener(new MyOnRefreshListener2(rtflv));
		rtflv.setAdapter(adapter);
	}

	class MyOnRefreshListener2 implements OnRefreshListener2<ListView> {

		private final PullToRefreshListView mPtflv;

		public MyOnRefreshListener2(PullToRefreshListView ptflv) {
			this.mPtflv = ptflv;
		}

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String label = DateUtils.formatDateTime(MyJoinActivity.this,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			new GetNewsTask(mPtflv).execute();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			
		}

	}
	
	/**
	 * 请求网络获得新闻信息
	 * 
	 * @author Louis
	 * 
	 */
	class GetNewsTask extends AsyncTask<String, Void, Integer> {
		private final PullToRefreshListView mPtrlv;

		public GetNewsTask(PullToRefreshListView ptrlv) {
			this.mPtrlv = ptrlv;
		}

		@Override
		protected Integer doInBackground(String... params) {
			if (CommonUtils.isWifiConnected(MyJoinActivity.this)) {
				try {
					Thread.sleep(1000);
					return HTTP_REQUEST_SUCCESS;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return HTTP_REQUEST_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case HTTP_REQUEST_SUCCESS:
				newAdapter.addNews(NewsUtil.getSimulationNews(10));
				newAdapter.notifyDataSetChanged();
				break;
			case HTTP_REQUEST_ERROR:
				Toast.makeText(MyJoinActivity.this, "请检查网络", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			mPtrlv.onRefreshComplete();
		}
	}
}
