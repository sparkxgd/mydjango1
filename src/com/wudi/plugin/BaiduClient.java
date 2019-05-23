package com.wudi.plugin;


import java.util.Map;

import org.json.JSONObject;

import com.jfinal.kit.JsonKit;
import com.wudi.util.HttpUtil;

/**
* 人脸搜索
*/
public class BaiduClient {
	private AuthService auth;
	public BaiduClient(String API_KEY,String SECRET_KEY) {
		this.auth=new AuthService(API_KEY,SECRET_KEY);
	}
    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    private JSONObject baidu(String url,Map<String, Object> map) {
        // 请求url
        try {
            
            String param = JsonKit.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = auth.getAuth();
            String r = HttpUtil.post(url, accessToken, "application/json", param);
            JSONObject result= new JSONObject(r);
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 多人脸识别
     * @param map
     * @return
     */
    public JSONObject multiSearch(Map<String, Object> map) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/multi-search";
        try {
            JSONObject result=baidu(url,map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 单人脸识别
     * @param map
     * @return
     */
    public JSONObject search(Map<String, Object> map) {
    	String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            JSONObject result=baidu(url,map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 单人脸检测
     * @param map
     * @return
     */
    public JSONObject detect(Map<String, Object> map) {
    	String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            JSONObject result=baidu(url,map);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    	
    	
        /**
         * 添加人脸库
         * @param map
         * @return
         */
        public JSONObject userAdd(Map<String, Object> map) {
        	String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
            try {
                JSONObject result=baidu(url,map);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    public static void main(String[] args) {
//        BaiduClient.search();
    }
}
