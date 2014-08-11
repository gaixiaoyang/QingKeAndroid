package com.bmob.im.demo.ui.fragment;

import android.annotation.*;
import android.os.*;
import android.text.format.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.*;
import com.bmob.im.demo.ui.*;
import com.bmob.im.demo.util.*;
import com.handmark.pulltorefresh.library.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

/**
 * 活动列表
 * 
 * @ClassName: ActivityFragment
 * @Description: TODO
 * @author smile
 * @date 2014-6-7 下午1:02:05
 */
@SuppressLint("DefaultLocale")
public class ActivityFragment extends FragmentBase implements OnItemClickListener, OnItemLongClickListener {

	public static final int HTTP_REQUEST_SUCCESS = -1;
	
	public static final int HTTP_REQUEST_ERROR = 0;
	
	private PullToRefreshListView pullToRefreshListView = null;
	
	private NewListAdapter newAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_activity, container, false);
	}
	
	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//activity_view = LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_activity, null);
		newAdapter = new NewListAdapter(this.getActivity(), NewsUtil.getSimulationNews(10),"activity");
		pullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.list_activity);
		initTopBarForOnlyTitle("周围的请客");
		initPullToRefreshListView(pullToRefreshListView, newAdapter);
	};
	
	private void initPullToRefreshListView(PullToRefreshListView rtflv,
			NewListAdapter adapter) {
		rtflv.setMode(Mode.PULL_FROM_START);
		rtflv.setOnRefreshListener(new MyOnRefreshListener2(rtflv));
		rtflv.setAdapter(adapter);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
	
	class MyOnRefreshListener2 implements OnRefreshListener2<ListView> {

		private final PullToRefreshListView mPtflv;

		public MyOnRefreshListener2(PullToRefreshListView ptflv) {
			this.mPtflv = ptflv;
		}

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String label = DateUtils.formatDateTime(ActivityFragment.this.getActivity(),
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
			if (CommonUtils.isWifiConnected(ActivityFragment.this.getActivity())) {
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
				Toast.makeText(ActivityFragment.this.getActivity(), "请检查网络", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			mPtrlv.onRefreshComplete();
		}

	}

}
