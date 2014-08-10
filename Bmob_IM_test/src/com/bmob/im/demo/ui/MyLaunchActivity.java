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
 * 我发起的请客
 * 
 * @Title: MyLaunchActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 下午5:52:14
 * @version V1.0
 */
public class MyLaunchActivity extends BaseActivity {
	
	public static final int HTTP_REQUEST_SUCCESS = -1;
	
	public static final int HTTP_REQUEST_ERROR = 0;
	
	private PullToRefreshListView pullToRefreshListView = null;
	
	private NewListAdapter newAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_launch);
		newAdapter = new NewListAdapter(this, NewsUtil.getSimulationNews(10));
		pullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.list_activity);
		initTopBarForLeft("我发起的请客");
		initPullToRefreshListView(pullToRefreshListView, newAdapter);
	}
	
	private void initPullToRefreshListView(PullToRefreshListView rtflv,
			NewListAdapter adapter) {
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
			String label = DateUtils.formatDateTime(MyLaunchActivity.this,
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
			if (CommonUtils.isWifiConnected(MyLaunchActivity.this)) {
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
				Toast.makeText(MyLaunchActivity.this, "请检查网络", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			mPtrlv.onRefreshComplete();
		}
	}

}
