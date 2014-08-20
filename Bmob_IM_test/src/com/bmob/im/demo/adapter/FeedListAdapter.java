package com.bmob.im.demo.adapter;

import java.util.*;

import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.text.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;

import com.bmob.im.demo.*;
import com.bmob.im.demo.bean.*;
import com.bmob.im.demo.ui.*;
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
		LinearLayout more;
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
			holder.more = (LinearLayout) convertView.findViewById(R.id.feed_item_ib_more);
			holder.more.setTag(getItem(position).get("id"));
			holder.comment = (LinearLayout) convertView.findViewById(R.id.feed_item_layout_comment);
			holder.comment.setTag(getItem(position).get("id"));
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
		holder.avatar.setTag(avatar);
		holder.name.setText(getItem(position).get("name"));
		holder.time.setText("请客时间：" + getItem(position).get("time"));
		holder.content.setText(getItem(position).get("content"));
		// if (feed.getContentImage() == null) {
		holder.contentImage.setVisibility(View.GONE);
		// } else {
		// holder.contentImage.setVisibility(View.VISIBLE);
		// holder.contentImage.setImageBitmap(mApplication.getStatusPhoto(feed.getContentImage()));
		// }
		String sex = getItem(position).get("sex");
		StringBuffer text = new StringBuffer();
		if (sex.equals("male")) {
			text.append("   女士勿扰");
		} else if (sex.equals("female")) {
			text.append("   男士勿扰");
		}
		holder.site2.setText(text.toString());
		String launchLat = getItem(position).get("currentLat");
		String launchLong = getItem(position).get("currentLong");
		String currentLat = CustomApplcation.getInstance().getLatitude();
		String currentLong = CustomApplcation.getInstance().getLongtitude();
		if (launchLong != null && launchLat != null && !launchLong.equals("") && !launchLat.equals("")
				&& !currentLat.equals("") && !currentLong.equals("")) {
			double distance = DistanceOfTwoPoints(Double.parseDouble(currentLat), Double.parseDouble(currentLong),
					Double.parseDouble(launchLat), Double.parseDouble(launchLong));
			holder.site1.setText(String.valueOf(distance) + "米   ");
		} else {
			holder.site1.setText("   ");
		}
		// 点击头像放大
		holder.avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
			}
		});
		// 添加好友
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				final ProgressDialog progress = new ProgressDialog(FeedListAdapter.this.context);
				progress.setMessage("正在添加好友...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				LinearLayout nameView = (LinearLayout) view;
				String id = nameView.getTag().toString();
				BmobQuery<User> query = new BmobQuery<User>();
				query.getObject(FeedListAdapter.this.context, id, new GetListener<User>() {
					@Override
					public void onSuccess(User user) {
						Intent intent = new Intent(FeedListAdapter.this.context, SetMyInfoActivity.class);
						intent.putExtra("from", "add");
						intent.putExtra("username", user.getUsername());
						progress.dismiss();
						FeedListAdapter.this.context.startActivity(intent);
					}

					@Override
					public void onFailure(int code, String msg) {
						Toast.makeText(FeedListAdapter.this.context, "请检查网络", Toast.LENGTH_SHORT).show();
						progress.dismiss();
					}
				});
			}
		});
		// 申请加入
		holder.more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				LinearLayout nameView = (LinearLayout) view;
				String id = nameView.getTag().toString();
				AlertDialog.Builder builder = new Builder(FeedListAdapter.this.context);
				builder.setMessage("申请成功加入'" + id + "'的请客成功！");
				builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
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

	public void addReserveNews(List<HashMap<String, String>> addNews) {
		for (HashMap<String, String> hm : addNews) {
			news.add(hm);
		}
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
