package com.mob.moblink.demo.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mob.moblink.MobLink;
import com.mob.moblink.demo.R;
import com.mob.tools.utils.ResHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class CommonUtils {

	/**
	 * 标识, 连接是不是测试服
	 */
	public static final boolean DEBUGGABLE = MobLink.DEBUGGABLE;

	public static final String SHARE_URL;
	static {
		if (DEBUGGABLE) {
			SHARE_URL = "http://10.18.97.58:2121";
		} else {
			SHARE_URL = "http://f.moblink.mob.com/test";
		}
	}

	public static final String[] MAIN_PATH_ARR = {"/demo/a", "/demo/b", "/demo/c", "/demo/d"};

	public static final String NEWS_PATH = "/scene/news";
	public static final String NEWS_SOURCE = "MobLinkDemo-News";

	public static final String VIDEO_PATH = "/scene/video";
	public static final String VIDEO_SOURCE = "MobLinkDemo-Videos";

	public static final String SHOPPING_PATH = "/scene/shopping";
	public static final String SHOPPING_SOURCE = "MobLinkDemo-Shopping";

	public static final String INVITE_PATH = "/params/invite";
	public static final String INVITE_SOURCE = "MobLinkDemo-Invite";

	public static final String TICKET_PATH = "/params/ticket";

	/**
	 * 创建一个自定义dialog，场景还原的参数展示dialog
	 * @param context
	 * @param path
	 * @param source
	 * @param params
	 * @return
	 */
	public static Dialog getDialog(Context context, String path, String source, String params) {
		final Dialog dialog = new Dialog(context, R.style.Dialog);
		dialog.setContentView(R.layout.dialog);
		if (!TextUtils.isEmpty(path)) {
			((TextView) dialog.findViewById(R.id.tv_dialog_path)).append("\r\n" + path);
		}
		if (!TextUtils.isEmpty(source)) {
			((TextView) dialog.findViewById(R.id.tv_dialog_source)).append("\r\n" + source);
		}
		if (!TextUtils.isEmpty(params)) {
			((TextView) dialog.findViewById(R.id.tv_dialog_param)).append("\r\n" + params);
		}
		dialog.findViewById(R.id.tv_dialog_close).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	/**
	 * 是否恢复场景的对话框
	 * @param context
	 * @param runnable
	 * @return
	 */
	public static Dialog getRestoreSceneDialog(Context context, final Runnable runnable) {
		final Dialog dialog = new Dialog(context, R.style.Dialog);
		dialog.setContentView(R.layout.alert_dialog);
		dialog.findViewById(R.id.dialog_btn_no).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.findViewById(R.id.dialog_btn_yes).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (runnable != null) {
					runnable.run();
				}
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	/**
	 * 获取mobid后才能分享的对话框
	 * @param context
	 * @return
	 */
	public static Dialog getMobIdDialog(Context context) {
		final Dialog dialog = new Dialog(context, R.style.Dialog);
		dialog.setContentView(R.layout.alert_dialog);
		dialog.findViewById(R.id.dialog_btn_no).setVisibility(View.GONE);
		dialog.findViewById(R.id.dialog_v_line).setVisibility(View.GONE);
		TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
		tvTitle.setText(R.string.please_get_mobid);
		TextView tvYes = (TextView) dialog.findViewById(R.id.dialog_btn_yes);
		tvYes.setText(R.string.close);
		tvYes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	/**
	 * 获取新闻列表数据
	 * @param context
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> getNewsData(Context context) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int[] newsImg = {R.drawable.demo_news01, R.drawable.demo_news02, R.drawable.demo_news03,
				R.drawable.demo_news04, R.drawable.demo_news05, R.drawable.demo_news06, R.drawable.demo_news07};
		String[] newsTitle = context.getResources().getStringArray(R.array.news_titles);
		String[] newsCommments = context.getResources().getStringArray(R.array.news_commments);
		String[] newsDetail = context.getResources().getStringArray(R.array.news_details);
		String[] newsTime = context.getResources().getStringArray(R.array.news_time);
		for (int i = 0; i < newsImg.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("img", newsImg[i]);
			item.put("title", newsTitle[i]);
			item.put("comm", newsCommments[i]);
			if (i == 0) {
				item.put("top", true);
				item.put("opic", true);
			} else {
				item.put("top", false);
				item.put("opic", false);
			}
			item.put("time", newsTime[i]);
			item.put("detail", newsDetail[i]);
			list.add(item);
		}
		return list;
	}

	/**
	 * 获取视频列表数据
	 * @param context
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> getVideosData(Context context) {
		int[] videoIcon = {R.drawable.demo_video01, R.drawable.demo_video02, R.drawable.demo_video03,
				R.drawable.demo_video04, R.drawable.demo_video05, R.drawable.demo_video06,
				R.drawable.demo_video07, R.drawable.demo_video08, R.drawable.demo_video09,};
		String[] videoName = context.getResources().getStringArray(R.array.video_name);
		String[] videoUrl = context.getResources().getStringArray(R.array.video_url);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < videoIcon.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("videoIcon", videoIcon[i]);
			item.put("videoName", videoName[i]);
			item.put("videoUrl", videoUrl[i]);
			list.add(item);
		}
		return  list;
	}

	/**
	 * 获取商品列表数据
	 * @param context
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> getGoodsData(Context context) {
		int[] goodsIcon = {R.drawable.demo_ds01, R.drawable.demo_ds02, R.drawable.demo_ds03,
				R.drawable.demo_ds04, R.drawable.demo_ds05, R.drawable.demo_ds06,
				R.drawable.demo_ds07, R.drawable.demo_ds08, R.drawable.demo_ds09,};
		int[] goodsBigIcon = {R.drawable.demo_ds01_big, R.drawable.demo_ds02_big, R.drawable.demo_ds03_big,
				R.drawable.demo_ds04_big, R.drawable.demo_ds05_big, R.drawable.demo_ds06_big,
				R.drawable.demo_ds07_big, R.drawable.demo_ds08_big, R.drawable.demo_ds09_big,};
		String[] goodsName = context.getResources().getStringArray(R.array.goods_name);
		String[] goodsPrice = context.getResources().getStringArray(R.array.goods_price);
		String[] goodsSales = context.getResources().getStringArray(R.array.goods_sales);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < goodsIcon.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("goodsIcon", goodsIcon[i]);
			item.put("goodsBigIcon", goodsBigIcon[i]);
			item.put("goodsName", goodsName[i]);
			item.put("goodsPrice", goodsPrice[i]);
			item.put("goodsSales", goodsSales[i]);
			list.add(item);
		}
		return  list;
	}

	/**
	 * 获取飞机票列表数据
	 * @param context
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> getTicketsDate(Context context) {
		String[] fromTime = context.getResources().getStringArray(R.array.fly_from_time);
		String[] toTime = context.getResources().getStringArray(R.array.fly_to_time);
		String[] ticketPrice = context.getResources().getStringArray(R.array.ticket_price);
		String[] ticketDiscount = context.getResources().getStringArray(R.array.ticket_discount);
		String[] planeName = context.getResources().getStringArray(R.array.plane_name);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < fromTime.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("fromTime", fromTime[i]);
			item.put("ToTime", toTime[i]);
			item.put("ticketPrice", ticketPrice[i]);
			item.put("ticketDiscount", ticketDiscount[i]);
			item.put("planeName", planeName[i]);
			list.add(item);
		}
		return  list;
	}

	/**
	 * 复制res中的图片到sdcard中
	 * @param context
	 * @param imgID
	 * @param imgName
	 * @return
	 */
	public static String copyImgToSD(Context context, int imgID, String imgName) {
		String imgPaht = "";
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgID);
		if (bitmap != null && !bitmap.isRecycled()) {
			String path = ResHelper.getImageCachePath(context);
			if (TextUtils.isEmpty(imgName)) {
				imgName = String.valueOf(System.currentTimeMillis());
			}
			File file = new File(path, imgName + ".jpg");
			if (file.exists()) {
				return file.getAbsolutePath();
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
				fos.flush();
				fos.close();
				imgPaht = file.getAbsolutePath();
			} catch (Throwable t) {
			}
		}
		return imgPaht;
	}

	/**
	 * 分享
	 * @param context
	 * @param title
	 * @param text
	 * @param url
	 * @param imgPath
	 */
	public static void showShare(Context context, String title, String text, String url, String imgPath) {
		OnekeyShare oks = new OnekeyShare();
		oks.setTitle(title);
		oks.setText(text);
		oks.setUrl(url);
		oks.setTitleUrl(url);
		oks.setImagePath(imgPath);
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
				if ("SinaWeibo".endsWith(platform.getName())) {
					String text = paramsToShare.getText() + paramsToShare.getUrl();
					paramsToShare.setText(text);
				} else if ("ShortMessage".endsWith(platform.getName())) {
					paramsToShare.setImagePath(null);
					String value = paramsToShare.getText();
					value += "\n";
					String url = paramsToShare.getUrl();
					value += url;
					paramsToShare.setText(value);

				}
			}
		});
		oks.show(context);
	}

}
