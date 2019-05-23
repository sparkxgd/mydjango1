package com.wudi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wudi.bean.FaceSeachModel;
import com.wudi.model.UserModel;
import com.wudi.plugin.BaiduHttpPlugin;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
//	/**
//	 * xiao
//	 * 人脸登录
//	 */
//	public void faceLogin3() {
//		int code = -1;
//		
//		UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
//		File file = upFile.getFile();
//       String extName = StringUtil.getFileExt(file.getName());
//       String filePath = upFile.getUploadPath();
//       String fileName = System.currentTimeMillis() + extName;
//       file.renameTo(new File(filePath+"\\"+fileName));        
//		
//       String url=filePath+"\\"+fileName;
//	    String image =Util.GetImageStr(url);
//	    file.delete();//文件删除   
//       
//	    // 传入可选参数调用接口
//	    HashMap<String, String> options = new HashMap<String, String>();
////	    options.put("quality_control", "NORMAL");
////	    options.put("liveness_control", "LOW");
//	    
//	    String imageType = "BASE64";
//	    String groupIdList = "test";
//	    
//	    // 人脸搜索
//	    JSONObject res = BaiduPlugin.face.search(image, imageType, groupIdList, options);
//	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
//	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("user_list").iterator();
//	    while(it.hasNext()) {
//	    	JSONObject jo=(JSONObject)it.next();
//	    	FaceSeachModel m=new FaceSeachModel();
//	    	m.setGroup_id(jo.getString("group_id"));
//	    	m.setUser_id(jo.getString("user_id"));
//	    	m.setUser_info(jo.getString("user_info"));
//	    	m.setScore(jo.getDouble("score"));
//	    	flist.add(m);
//	    }
//	    //找到匹配度最高的脸
//	    double max=0;
//	    String user_id="";
//	    for(FaceSeachModel k:flist) {
//	    	if(k.getScore()>max) {
//	    		max=k.getScore();
//	    		user_id=k.getUser_id();
//	    	}
//	    }
//	    
//	  //到数据库查找一下是否有这个人
//	    UserModel data =UserModel.findByLogin(user_id);
//	    if(data!=null) {
//	    	code = 0;//0成功
//	    }
//	    setAttr("data", data);
//		setAttr("code", code);
//	    renderJson();
//	}
//	/**
//	 * 任课老师拍照签到
//	 */
//	public void faceSignIn3() {
//		
//		UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
//		File file = upFile.getFile();
//       String extName = StringUtil.getFileExt(file.getName());
//       String filePath = upFile.getUploadPath();
//       String fileName = System.currentTimeMillis() + extName;
//       file.renameTo(new File(filePath+"\\"+fileName));
//		
//	    String url=filePath+"\\"+fileName;
//	    String image =Util.GetImageStr(url);
//	    file.delete();//文件删除        
//	    // 传入可选参数调用接口
//	    HashMap<String, String> options = new HashMap<String, String>();
//	    options.put("detect_top_num", "10");
//	    options.put("user_top_num", "10");
//	    String imageType = "BASE64";
//	    String groupIdList = "test";
//	    
//	    // 人脸搜索
//	    JSONObject res = BaiduPlugin.face.multiSearch(image, imageType, groupIdList, options);
//	    
//	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
//	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONArray("user_list").iterator();
//	    while(it.hasNext()) {
//	    	JSONObject jo=(JSONObject)it.next();
//	    	FaceSeachModel m=new FaceSeachModel();
//	    	m.setGroup_id(jo.getString("group_id"));
//	    	m.setUser_id(jo.getString("user_id"));
//	    	m.setUser_info(jo.getString("user_info"));
//	    	m.setScore(jo.getDouble("score"));
//	    	flist.add(m);
//	    }
//	    
//	    String classid=getPara("classid");
//	    String tcsuid=getPara("tcsuid");
//	    int week=getParaToInt("week");
//	    //将信息添加到数据库
//	    
//	    List<StuRegisterNewModel> list=StuRegisterNewModel.signIn(flist,tcsuid,classid,week);
//	    
//	    setAttr("data", list);
//		setAttr("code", 0);
//	    renderJson();
//	}
	public void testHttpFace() {
		int code = -1;
		String u=System.getProperty("user.dir");
		String imgurl=u+"\\WebContent\\upload\\xiao4.jpg";
	    String image =Util.GetImageStr(imgurl);
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", image);
        map.put("liveness_control", "NORMAL");
        map.put("group_id_list", "test");
        map.put("image_type", "BASE64");
        map.put("max_face_num", 10);
        map.put("quality_control", "LOW");
		
	    JSONObject res=BaiduHttpPlugin.face.multiSearch(map);
	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("face_list").iterator();
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
	    //找到匹配度最高的脸
	    double max=0;
	    String user_id="";
	    for(FaceSeachModel k:flist) {
	    	if(k.getScore()>max) {
	    		max=k.getScore();
	    		user_id=k.getUser_id();
	    	}
	    }
	    
	  //到数据库查找一下是否有这个人
	    UserModel data =UserModel.findByLogin(user_id);
	    if(data!=null) {
	    	code = 0;//0成功
	    }
	
	}
	public void testHttpFace2() {
		int code = -1;
		String u=System.getProperty("user.dir");
		String imgurl=u+"\\WebContent\\upload\\xiaobao.jpg";
	    String image =Util.GetImageStr(imgurl);
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", image);
        map.put("liveness_control", "NORMAL");
        map.put("group_id_list", "test");
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");
		
	    JSONObject res=BaiduHttpPlugin.face.search(map);
	    JSONArray jsar=res.getJSONObject("result").getJSONArray("user_list");
	    FaceSeachModel m=new FaceSeachModel();
	    if(!jsar.isNull(0)) {
	    	JSONObject jsa=jsar.getJSONObject(0);
	    	m.setGroup_id(jsa.getString("group_id"));
	    	m.setUser_id(jsa.getString("user_id"));
	    	m.setUser_info(jsa.getString("user_info"));
	    	m.setScore(jsa.getDouble("score"));
	    }
	  //到数据库查找一下是否有这个人
	    UserModel data =UserModel.findByLogin(m.getUser_id());
	    if(data!=null) {
	    	code = 0;//0成功
	    }
	
	}

}
