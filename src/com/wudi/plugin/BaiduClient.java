package com.wudi.plugin;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;
import com.wudi.bean.FaceDetectBean;
import com.wudi.bean.FaceSeachModel;
import com.wudi.util.HttpUtil;
import com.wudi.util.StringUtil;
import com.wudi.util.Util;

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
    public List<FaceSeachModel>  multiSearch(UploadFile imgFile) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/multi-search";
        try {
        	String image=getImg64(imgFile);     
    	    // 传入可选参数调用接口
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", image);
            map.put("group_id_list", "test");
            map.put("image_type", "BASE64");
            map.put("max_face_num", 10);
            map.put("quality_control", "LOW");
            
            JSONObject res=baidu(url,map);
            
    	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
    	    JSONObject ob=res.getJSONObject("result");
    	    if(ob!=null) {
    	    	Iterator<Object> it=ob.getJSONArray("face_list").iterator();
    		    while(it.hasNext()) {
    		    	JSONObject jsa=(JSONObject)it.next();
    		    	JSONArray jsb=jsa.getJSONArray("user_list");
    		    	if(!jsb.isNull(0)) {
    		    		JSONObject jsc=jsb.getJSONObject(0);
    		    		FaceSeachModel m=new FaceSeachModel();
    			    	m.setGroup_id(jsc.getString("group_id"));
    			    	m.setUser_id(jsc.getString("user_id"));
    			    	m.setUser_info(jsc.getString("user_info"));
    			    	m.setScore(jsc.getDouble("score"));
    			    	flist.add(m);
    		    	}
    		    }
    		    
    	    }
            return flist;
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
    public FaceSeachModel search(UploadFile imgFile) {
    	String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
        	String image=getImg64(imgFile); 
        	 Map<String, Object> map = new HashMap<String, Object>();
             map.put("image", image);
             map.put("liveness_control", "NORMAL");
             map.put("group_id_list", "test");
             map.put("image_type", "BASE64");
             map.put("quality_control", "LOW");
        	
            JSONObject res=baidu(url,map);
            
    	    JSONArray jsar=res.getJSONObject("result").getJSONArray("user_list");
    	    FaceSeachModel m=new FaceSeachModel();
    	    if(!jsar.isNull(0)) {
    	    	JSONObject jsa=jsar.getJSONObject(0);
    	    	m.setGroup_id(jsa.getString("group_id"));
    	    	m.setUser_id(jsa.getString("user_id"));
    	    	m.setUser_info(jsa.getString("user_info"));
    	    	m.setScore(jsa.getDouble("score"));
    	    }
            return m;
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
    public FaceDetectBean detect(UploadFile imgFile) {
    	String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
        	File file = imgFile.getFile();
            String extName = StringUtil.getFileExt(file.getName());
            String filePath = imgFile.getUploadPath();
            String fileName = System.currentTimeMillis() + extName;
            file.renameTo(new File(filePath+"\\"+fileName));
    		
    	    String imgurl=filePath+"\\"+fileName;
    	    String image =Util.GetImageStr(imgurl); 
        	Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", image);
            map.put("face_field", "age,beauty,gender");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
        	
            JSONObject res=baidu(url,map);
            FaceDetectBean b=new FaceDetectBean();

            //检测到的图片中的人脸数量
            int face_num=res.getJSONObject("result").getInt("face_num");
            //人脸置信度，范围【0~1】，代表这是一张人脸的概率，0最小、1最大。
            JSONArray js=res.getJSONObject("result").getJSONArray("face_list");
            if(!js.isNull(0)) {
                JSONObject jsa=js.getJSONObject(0);
                double face_probability=jsa.optDouble("face_probability");
                double age=jsa.optDouble("age");
                double beauty=jsa.optDouble("beauty");
                String type=jsa.getJSONObject("gender").getString("type");
                
                if(face_num<1||face_probability<0.8) {
                    file.delete();
                	b.setCode(1);
                	b.setMsg("没有检查到人脸或者，图片太模糊！！请重新上传");
                	
                }else {
                	b.setCode(0);
                	b.setMsg("上传成功");
                	b.setSrc(fileName);
                	b.setAge(age);
                	b.setBeauty(beauty);
                	b.setType(type);
                }
            }
            
            return b;
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
        

        private String getImg64(UploadFile imgFile) {
        	File file = imgFile.getFile();
            String extName = StringUtil.getFileExt(file.getName());
            String filePath = imgFile.getUploadPath();
            String fileName = System.currentTimeMillis() + extName;
            file.renameTo(new File(filePath+"\\"+fileName));
    		
    	    String imgurl=filePath+"\\"+fileName;
    	    String image =Util.GetImageStr(imgurl);
    	    file.delete();//文件删除  
    	    return image;
        }
    public static void main(String[] args) {
//        BaiduClient.search();
    }
}
