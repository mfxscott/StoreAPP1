package com.xianhao365.o2o.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xianhao365.o2o.activity.MyApplication;
import com.xianhao365.o2o.activity.StartMainActivity;

import java.util.List;

/**
 * ***************************
 * 首页广告
 * @author mfx
 * ***************************
 */
public class BannerAdapter extends BaseAdapter {
	private String[] scroll_pic;
	private List<String> result;
	private final LayoutInflater mLayoutInflater;
	private Activity mContext;
	private final android.os.Handler handler;
	public BannerAdapter(Activity context, String[] scroll_pic, List<String> result, Handler handler) {
		mLayoutInflater = LayoutInflater.from(context);
		this.scroll_pic = scroll_pic;
		this.result = result;
		this.mContext = context;
		this.handler = handler;
	}
	public int getCount() {
		return Integer.MAX_VALUE;// 返回很大的值使得getView中的position不断增大来实现循环
	}
	public Object getItem(int position) {
		return position;
	}
	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
//			convertView = mLayoutInflater.inflate(R.layout.main_banner_gallery_item, null);
//			vh.image = (ImageView) convertView.findViewById(R.id.main_gallery_img);
//			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
//		Picasso.with(mContext)
//				.load(AppClient.HTTPIP+result.get(position
//						% result.size()).getContimg())
//				.into(vh.image);
		MyApplication	myApplication = (MyApplication) mContext.getApplicationContext();
//		Picasso
//				.with(mContext)
//				.load(myApplication.getHttpId()+result.get(position
//						% result.size()).getContimg())
//		.load(R.drawable.landing_screen) 资源图片
//		.load("file:///android_asset/DvpvklR.png") 本地图片
//		.load(new File(...)).into(imageView3);  内存图片
//				.resize(100, 100)//裁剪自定义图片尺寸大小
//				.placeholder(R.mipmap.ic_launcher)//默认图片
//				.error(R.mipmap.ic_launcher)//加载出错图片
//				.noFade()//淡入的效果
//				.fit()
//				.noPlaceholder()//防止列表活动在不显示界面 返回滑动加载图片
//				.into(vh.image);
		//加载本地文件  加载GIF图片
//		Picasso.with(context).load(new File("/images/oprah_bees.gif")).into(imageView2);
		vh.image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent banintent = new Intent();
				banintent.setClass(mContext, StartMainActivity.class);
				banintent.putExtra("tag","1");
//				String sourceurl = result.get(position
//						% result.size())
//						.getSourceurl();
//				int type = Integer.parseInt(result.get(position
//						% result.size()).getViewtype());
//				if(!TextUtils.isEmpty(sourceurl)){
//					int result=sourceurl.indexOf("http");
//					if(result>=0) {
//						banintent.putExtra("goUrl", sourceurl);
//						banintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						mContext.startActivity(banintent);
//						return;
//					}
//				}
			}
		});
		return convertView;
	}
	class ViewHolder{
		ImageView image;
	}
}