package com.bmob.im.demo.ui;

import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;

import com.bmob.im.demo.*;

/**
 * ����������б�
 * 
 * @Title: ApplyListActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 ����5:52:14
 * @version V1.0
 */
public class ApplyListActivity extends ActivityBase implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_list);
		initTopBarForLeft("�������б�");
	}

	@Override
	public void onClick(View v) {
		
	}
	
}
