/**
 * 
 */
package com.wudi.plugin;

import com.jfinal.plugin.IPlugin;

/**
 * @author Administrator
 *
 */
public class BaiduHttpPlugin implements IPlugin {
	private String API_KEY;
	private String SECRET_KEY;
	public static BaiduClient face;
	public BaiduHttpPlugin(String API_KEY,String SECRET_KEY) {
		this.API_KEY=API_KEY;
		this.SECRET_KEY=SECRET_KEY;
	}

	@Override
	public boolean start() {
		boolean result=true;
		try {
			// 初始化一个AipFace
			face = new BaiduClient(API_KEY, SECRET_KEY);
			System.out.println("加载百度插件成功。。。。");
			// 可选：设置网络连接参数
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return true;
	}



}
