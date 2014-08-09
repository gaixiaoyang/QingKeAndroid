package com.bmob.im.demo.adapter;

import java.util.*;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.bmob.im.demo.*;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.*;


public class NewListAdapter extends BaseAdapter {
	
	private ImageLoader imageLoader = null;
	private	DisplayImageOptions options = null;
	
	static class ViewHolder {
		ImageView ivPreview;
		TextView tvTitle;
		TextView tvContent;
		TextView tvReview;
	}
	
	private final Context context;
	private final List<HashMap<String, String>> news;

	@SuppressWarnings("deprecation")
	public NewListAdapter(Context context,List<HashMap<String, String>> news) {
		this.context = context;
		this.news = news;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		
		options = new DisplayImageOptions.Builder()
		.displayer(new RoundedBitmapDisplayer(0xff000000, 10))
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}
	
	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public HashMap<String,String> getItem(int position) {
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
			holder = new ViewHolder();
			holder.ivPreview = (ImageView) convertView.findViewById(R.id.ivPreview);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		imageLoader.displayImage(getItem(position).get("uri"), holder.ivPreview, options);
		holder.tvTitle.setText(getItem(position).get("title"));
		holder.tvContent.setText(getItem(position).get("content"));
		holder.tvReview.setText(getItem(position).get("review"));
		
		return convertView;
	}
	
	public void addNews(List<HashMap<String, String>> addNews) {
		for(HashMap<String, String> hm:addNews) {
			news.add(hm);
		}
	}

}
