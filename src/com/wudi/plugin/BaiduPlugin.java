/**
 * 
 */
package com.wudi.plugin;

import com.baidu.aip.face.AipFace;
import com.jfinal.plugin.IPlugin;

/**
 * @author Administrator
 *
 */
public class BaiduPlugin implements IPlugin {
	private String APP_ID;
	private String API_KEY;
	private String SECRET_KEY;
	public static AipFace face;
	public BaiduPlugin(String APP_ID,String API_KEY,String SECRET_KEY) {
		this.APP_ID=APP_ID;
		this.API_KEY=API_KEY;
		this.SECRET_KEY=SECRET_KEY;
	}

	@Override
	public boolean start() {
		boolean result=true;
		try {
			// 初始化一个AipFace
			face = new AipFace(APP_ID, API_KEY, SECRET_KEY);
			System.out.println("加载百度插件成功。。。。");
			// 可选：设置网络连接参数
			face.setConnectionTimeoutInMillis(2000);
			face.setSocketTimeoutInMillis(60000);
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
