package com.bmob.im.demo.ui;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.im.bean.*;
import cn.bmob.im.db.*;
import cn.bmob.v3.listener.*;

import com.bmob.im.demo.*;
import com.bmob.im.demo.adapter.*;
import com.bmob.im.demo.util.*;
import com.bmob.im.demo.view.*;
import com.bmob.im.demo.view.dialog.*;

/**
 * 申请加入人列表
 * 
 * @Title: ApplyListActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 下午5:52:14
 * @version V1.0
 */
public class ApplyListActivity extends ActivityBase implements OnItemClickListener{
	
	ListView listview;
	BlackListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_list);
		initView();
	}
	
	private void initView() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.common_actionbar);
		initTopBarForLeft("申请人列表");
		adapter = new BlackListAdapter(this, BmobDB.create(this).getBlackList());
		listview = (ListView) findViewById(R.id.list_applylist);
		listview.setOnItemClickListener(this);
		listview.setAdapter(adapter);
	}

	/** 显示移除黑名单对话框
	  * @Title: showRemoveBlackDialog
	  * @Description: TODO
	  * @param @param position
	  * @param @param invite 
	  * @return void
	  * @throws
	  */
	public void showRemoveBlackDialog(final int position, final BmobChatUser user) {
		DialogTips dialog = new DialogTips(this, "移出黑名单",
				"你确定将"+user.getUsername()+"移出黑名单吗?", "确定", true, true);
		// 设置成功事件
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int userId) {
				adapter.remove(position);
				userManager.removeBlack(user.getUsername(),new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ShowToast("移出黑名单成功");
						//重新设置下内存中保存的好友列表
						CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(getApplicationContext()).getContactList()));	
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ShowToast("移出黑名单失败:"+arg1);
					}
				});
			
			}
		});
		// 显示确认对话框
		dialog.show();
		dialog = null;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		BmobChatUser invite = (BmobChatUser) adapter.getItem(arg2);
		showRemoveBlackDialog(arg2,invite);
	}

}
