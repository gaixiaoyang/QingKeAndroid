package com.bmob.im.demo.util;

import java.util.*;

public class NewsUtil {

	/**
	 * ��ȡN��ģ�����������<br>
	 * �����ArrayList����
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
			hm.put("time", "2014-07-11  �������");
			hm.put("content", "���ڳ�Ʒ�ͽ���ӭ���۴��ڣ�����Ԥ��ÿ������0.1Ԫ��");
			hm.put("review", i + "����");
			ret.add(hm);
		}
		return ret;
	}

}
