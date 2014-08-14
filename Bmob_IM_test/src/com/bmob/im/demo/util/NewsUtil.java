package com.bmob.im.demo.util;

import java.util.*;

public class NewsUtil {

	/**
	 * 获取N条模拟的新闻数据<br>
	 * 打包成ArrayList返回
	 * 
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> getSimulationNews(int n) {
		ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hm;
		for (int i = 0; i < n; i++) {
			hm = new HashMap<String, String>();
			if (i % 2 == 0) {
				hm.put("uri",
						"http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
			} else {
				hm.put("uri",
						"http://photocdn.sohu.com/20131101/Img389373139.jpg");
			}
			hm.put("time", "2014-07-11  南锣鼓巷");
			hm.put("content", "国内成品油今日迎调价窗口，机构预计每升降价0.1元。");
			hm.put("review", i + "跟帖");
			ret.add(hm);
		}
		return ret;
	}

}
