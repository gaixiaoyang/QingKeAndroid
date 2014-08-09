package com.bmob.im.demo.ui.fragment;

import android.annotation.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.bmob.im.demo.*;
import com.bmob.im.demo.ui.*;

/**
 * 发起请客
 * 
 * @ClassName: LaunchFragment
 * @Description: TODO
 * @author smile
 * @date 2014-6-7 下午1:02:05
 */
@SuppressLint("DefaultLocale")
public class LaunchFragment extends FragmentBase implements OnItemClickListener, OnItemLongClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_launch, container, false);
	}
	
	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForOnlyTitle("发起请客");
		
		
	};

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

}
