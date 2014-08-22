package com.bmob.im.demo.ui;

import java.util.*;

import android.app.*;
import android.os.*;
import android.widget.*;
import cn.bmob.im.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.*;
import com.bmob.im.demo.bean.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.*;

/**
 * 我的请客页面
 * 
 * @ClassName: UserInfoActivity
 * @author smile
 * @date 2014-6-10 下午2:55:19
 */
public class UserInfoActivity extends ActivityBase{

	public static final int HTTP_REQUEST_SUCCESS = -1;

	public static final int HTTP_REQUEST_ERROR = 0;

	private PullToRefreshListView pullToRefreshListView = null;

	private FeedListAdapter adapter = null;
	
	private BmobUserManager userManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userManager = BmobUserManager.getInstance(this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_set_user);
		initTopBarForLeft("我的请客");
		initData();
	}

	private void initPullToRefreshListView(PullToRefreshListView rtflv, FeedListAdapter adapter) {
		rtflv.setMode(Mode.DISABLED);
		rtflv.setAdapter(adapter);
	}

	private void initData() {
		final ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("正在加载列表...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		BmobQuery<Activitys> query = new BmobQuery<Activitys>();
		query.addWhereEqualTo("user_id", userManager.getCurrentUser().getObjectId());
		query.order("-timestamp");
		query.findObjects(this, new FindListener<Activitys>() {
			@Override
			public void onSuccess(List<Activitys> object) {
				ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < object.size(); i++) {
					HashMap<String, String> hm = new HashMap<String, String>();
					hm.put("avatar", object.get(i).getAvatar());
					hm.put("name", object.get(i).getAddress());
					hm.put("id", object.get(i).getUser_id());
					hm.put("objectId", object.get(i).getObjectId());
					hm.put("currentLat", object.get(i).getCurrentLat());
					hm.put("currentLong", object.get(i).getCurrentLong());
					hm.put("time", object.get(i).getTime());
					hm.put("content", object.get(i).getContent());
					hm.put("sex", object.get(i).getSex());
					ret.add(hm);
				}
				progress.dismiss();
				adapter = new FeedListAdapter(UserInfoActivity.this, ret, "activity");
				pullToRefreshListView = (PullToRefreshListView) UserInfoActivity.this.findViewById(R.id.list_activity);
				initPullToRefreshListView(pullToRefreshListView, adapter);
			}

			@Override
			public void onError(int code, String msg) {
				progress.dismiss();
				adapter = new FeedListAdapter(UserInfoActivity.this, new ArrayList<HashMap<String, String>>(),
						"activity");
				pullToRefreshListView = (PullToRefreshListView) UserInfoActivity.this.findViewById(R.id.list_activity);
				initPullToRefreshListView(pullToRefreshListView, adapter);
				Toast.makeText(UserInfoActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
				ShowLog(msg);
			}
		});
	}
}
