package com.bmob.im.demo.adapter;

import java.util.*;

import android.content.*;
import android.text.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bmob.im.demo.*;
import com.bmob.im.demo.view.*;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.*;

/**
 * 首页展示，另一种形式
 * 
 * @Title: FeedListAdapter.java
 * @Package com.bmob.im.demo.adapter
 * @author xiaoyang
 * @date 2014-8-18 下午2:31:56
 */
public class FeedListAdapter extends BaseAdapter {

	private ImageLoader imageLoader = null;
	private DisplayImageOptions options = null;

	static class ViewHolder {
		RelativeLayout root;
		ImageView avatar;
		HandyTextView time;
		HandyTextView launch;
		HandyTextView name;
		EmoticonsTextView content;
		ImageView contentImage;
		ImageButton more;
		LinearLayout comment;
		HandyTextView commentCount;
		HandyTextView site1;
		HandyTextView site2;
	}

	private final Context context;
	private final List<HashMap<String, String>> news;

	@SuppressWarnings("deprecation")
	public FeedListAdapter(Context context, List<HashMap<String, String>> news, String from) {
		this.context = context;
		this.news = news;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(0xff000000, 10))
				.cacheInMemory().cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_feedlist, null);
			holder = new ViewHolder();
			holder.root = (RelativeLayout) convertView.findViewById(R.id.feed_item_layout_root);
			holder.avatar = (ImageView) convertView.findViewById(R.id.feed_item_iv_avatar);
			holder.time = (HandyTextView) convertView.findViewById(R.id.feed_item_htv_time);
			holder.name = (HandyTextView) convertView.findViewById(R.id.feed_item_htv_name);
			holder.content = (EmoticonsTextView) convertView.findViewById(R.id.feed_item_etv_content);
			holder.contentImage = (ImageView) convertView.findViewById(R.id.feed_item_iv_content);
			holder.more = (ImageButton) convertView.findViewById(R.id.feed_item_ib_more);
			holder.comment = (LinearLayout) convertView.findViewById(R.id.feed_item_layout_comment);
			holder.commentCount = (HandyTextView) convertView.findViewById(R.id.feed_item_htv_commentcount);
			holder.site1 = (HandyTextView) convertView.findViewById(R.id.feed_item_htv_site1);
			holder.site2 = (HandyTextView) convertView.findViewById(R.id.feed_item_htv_site2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String avatar = getItem(position).get("avatar");
		if (!TextUtils.isEmpty(avatar)) {
			imageLoader.displayImage(avatar, holder.avatar, options);
		} else {
			holder.avatar.setImageDrawable(convertView.getResources().getDrawable(R.drawable.head));
		}
		holder.name.setText(getItem(position).get("name"));
		holder.time.setText(getItem(position).get("time"));
		holder.content.setText(getItem(position).get("content"));
		//if (feed.getContentImage() == null) {
			holder.contentImage.setVisibility(View.GONE);
		//} else {
		//	holder.contentImage.setVisibility(View.VISIBLE);
		//	holder.contentImage.setImageBitmap(mApplication.getStatusPhoto(feed.getContentImage()));
		//}
		String sex = getItem(position).get("sex");
		StringBuffer text = new StringBuffer();
		text.append("邀请：");
		if(sex.equals("male")){
			text.append("男士   ");
		}else if(sex.equals("female")){
			text.append("女士   ");
		}
		holder.site1.setText(text.toString());
		String launchLat = getItem(position).get("currentLat");
		String launchLong = getItem(position).get("currentLong");
		String currentLat = CustomApplcation.getInstance().getLatitude();
		String currentLong = CustomApplcation.getInstance().getLongtitude();
		if (launchLong != null && launchLat != null && !currentLat.equals("") && !currentLong.equals("")) {
			double distance = DistanceOfTwoPoints(Double.parseDouble(currentLat), Double.parseDouble(currentLong),
					Double.parseDouble(launchLat), Double.parseDouble(launchLong));
			holder.site2.setText("   距离：" + String.valueOf(distance) + "米");
		} else {
			holder.site2.setText("   距离：" + "未知");
		}

		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		holder.more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		/*
		 * holder.root.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override
		 * public void onClick(View v) {
		 * mPosition = position;
		 * Intent intent = new Intent(mContext, FeedProfileActivity.class);
		 * intent.putExtra("entity_profile", mProfile);
		 * intent.putExtra("entity_people", mPeople);
		 * intent.putExtra("entity_feed", (Feed) getItem(mPosition));
		 * mContext.startActivity(intent);
		 * }
		 * });
		 */
		return convertView;
	}

	public void addNews(List<HashMap<String, String>> addNews) {
		Collections.reverse(news);
		Collections.reverse(addNews);
		for (HashMap<String, String> hm : addNews) {
			news.add(hm);
		}
		Collections.reverse(news);
	}

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离：单位为米
	 */
	public static double DistanceOfTwoPoints(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}
