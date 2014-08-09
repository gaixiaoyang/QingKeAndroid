package com.bmob.im.demo.ui;

import android.os.*;

import com.bmob.im.demo.*;

/**
 * DetailActivity
 * 
 * @Title: DetailActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 ÏÂÎç5:52:14
 * @version V1.0
 */
public class DetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initTopBarForOnlyTitle("Çë¿ÍÏêÇéÒ³");
	}

	
}
