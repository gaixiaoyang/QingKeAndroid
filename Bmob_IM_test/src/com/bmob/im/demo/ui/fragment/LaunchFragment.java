package com.bmob.im.demo.ui.fragment;

import java.util.*;

import android.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

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
public class LaunchFragment extends FragmentBase implements OnClickListener {

	private EditText tv_name;
	private TextView tv_time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_launch, container, false);
	}

	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForOnlyTitle("发起请客");
		tv_name = (EditText) findViewById(R.id.tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_name.setOnClickListener(this);
		tv_time.setOnClickListener(this);
         
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_name:
			break;
		case R.id.tv_time:
			showTimeDialog();
			break;
		};
	}
	
	private void showTimeDialog(){
		 Calendar c = Calendar.getInstance();  
		 new DatePickerDialog(LaunchFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {  
		     @Override  
		     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
		   	  tv_time.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);  
		     }  
		 }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();  
	}
}
