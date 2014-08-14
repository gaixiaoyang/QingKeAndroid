package com.bmob.im.demo.adapter;

import java.util.*;

import android.content.*;
import android.text.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bmob.im.demo.*;
import com.bmob.im.demo.ui.*;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.*;


public class ActivityListAdapter extends BaseAdapter {
	
	private ImageLoader imageLoader = null;
	private	DisplayImageOptions options = null;
	private String from = "";
	
	static class ViewHolder {
		ImageView ivPreview;
		TextView tvTime;
		TextView tvContent;
		TextView tvReview;
		RelativeLayout ivLayout;
	}
	
	private final Context context;
	private final List<HashMap<String, String>> news;

	@SuppressWarnings("deprecation")
	public ActivityListAdapter(Context context,List<HashMap<String, String>> news,String from) {
		this.context = context;
		this.news = news;
		this.from = from;
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
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);
			holder.ivLayout = (RelativeLayout) convertView.findViewById(R.id.ivLayout);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String avatar = getItem(position).get("uri");
		if (!TextUtils.isEmpty(avatar)) {
			imageLoader.displayImage(avatar, holder.ivPreview, options);
		} else {
			holder.ivPreview.setImageDrawable(convertView.getResources().getDrawable(R.drawable.head));
		}
		holder.tvTime.setText(getItem(position).get("time"));
		holder.tvContent.setText(getItem(position).get("content"));
		holder.tvReview.setText(getItem(position).get("review"));
		
		holder.ivLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,DetailActivity.class);
				intent.putExtra("from", from);
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	
	public void addNews(List<HashMap<String, String>> addNews) {
		Collections.reverse(news);
		Collections.reverse(addNews);
		for(HashMap<String, String> hm:addNews) {
			news.add(hm);
		}
		Collections.reverse(news);
	}

}
