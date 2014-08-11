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
 * ����������б�
 * 
 * @Title: ApplyListActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 ����5:52:14
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
		initTopBarForLeft("�������б�");
		adapter = new BlackListAdapter(this, BmobDB.create(this).getBlackList());
		listview = (ListView) findViewById(R.id.list_applylist);
		listview.setOnItemClickListener(this);
		listview.setAdapter(adapter);
	}

	/** ��ʾ�Ƴ��������Ի���
	  * @Title: showRemoveBlackDialog
	  * @Description: TODO
	  * @param @param position
	  * @param @param invite 
	  * @return void
	  * @throws
	  */
	public void showRemoveBlackDialog(final int position, final BmobChatUser user) {
		DialogTips dialog = new DialogTips(this, "�Ƴ�������",
				"��ȷ����"+user.getUsername()+"�Ƴ���������?", "ȷ��", true, true);
		// ���óɹ��¼�
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int userId) {
				adapter.remove(position);
				userManager.removeBlack(user.getUsername(),new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ShowToast("�Ƴ��������ɹ�");
						//�����������ڴ��б���ĺ����б�
						CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(getApplicationContext()).getContactList()));	
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ShowToast("�Ƴ�������ʧ��:"+arg1);
					}
				});
			
			}
		});
		// ��ʾȷ�϶Ի���
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
