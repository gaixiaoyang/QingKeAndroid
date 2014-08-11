package com.bmob.im.demo.ui;

import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;

import com.bmob.im.demo.*;

/**
 * 申请加入人列表
 * 
 * @Title: ApplyListActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 下午5:52:14
 * @version V1.0
 */
public class ApplyListActivity extends ActivityBase implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_list);
		initTopBarForLeft("申请人列表");
	}

	@Override
	public void onClick(View v) {
		
	}
	
}
