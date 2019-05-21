package com.wudi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wudi.bean.FaceSeachModel;
import com.wudi.model.StuRegisterNewModel;
import com.wudi.model.UserModel;
import com.wudi.plugin.BaiduPlugin;
import com.wudi.util.StringUtil;
import com.wudi.util.Util;
/**
 * 
 * @author xiao
 *
 */
public class WeixinController extends Controller{
	/**
	 * 微信路径
	 */
	public void index() {
		setAttr("result", "你好无敌小团队！");
		renderJson();
	}
	
	/**
	 * 测试百度人脸识别
	 */
	public void testface() {
		
		String u=System.getProperty("user.dir");
		String url=u+"\\WebContent\\upload\\liang.jpg";
	    String image =Util.GetImageStr(url);  
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_top_num", "10");
	    options.put("user_top_num", "10");
	    String imageType = "BASE64";
	    String groupIdList = "test";
	    
	    // 人脸搜索
	    JSONObject res = BaiduPlugin.face.multiSearch(image, imageType, groupIdList, options);
	    
	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONArray("user_list").iterator();
	    while(it.hasNext()) {
	    	JSONObject jo=(JSONObject)it.next();
	    	FaceSeachModel m=new FaceSeachModel();
	    	m.setGroup_id(jo.getString("group_id"));
	    	m.setUser_id(jo.getString("user_id"));
	    	m.setUser_info(jo.getString("user_info"));
	    	m.setScore(jo.getDouble("score"));
	    	flist.add(m);
	    }
	    
 	    String classid=getPara("classid");
	    String tcsuid=getPara("tcsuid");
	    int week=getParaToInt("week");
	    //将信息添加到数据库
	    
	    List<StuRegisterNewModel> list=StuRegisterNewModel.signIn(flist,tcsuid,classid,week);
	    
	    setAttr("data", list);
		setAttr("code", 0);
	    renderJson();
	}
	/**
	 * 人脸识别搜索
	 */
	public void faceSearchT() {
		String u=System.getProperty("user.dir");
		String url=u+"\\WebContent\\upload\\1557462025819.jpg";
	    String image =Util.GetImageStr(url);

	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_top_num", "10");
	    options.put("user_top_num", "10");
	    String imageType = "BASE64";
	    String groupIdList = "test";
	    // 多人脸识别搜索
	    JSONObject res = BaiduPlugin.face.multiSearch(image, imageType, groupIdList, options);
	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONArray("user_list").iterator();
	    while(it.hasNext()) {
	    	JSONObject jo=(JSONObject)it.next();
	    	FaceSeachModel m=new FaceSeachModel();
	    	m.setGroup_id(jo.getString("group_id"));
	    	m.setUser_id(jo.getString("user_id"));
	    	m.setUser_info(jo.getString("user_info"));
	    	m.setScore(jo.getDouble("score"));
	    	flist.add(m);
	    }
	    
	    setAttr("data", flist);
		setAttr("code", 0);
	    renderJson();
	
	}
	/**
	 * 任课老师拍照签到
	 */
	public void faceSignIn() {
		
		UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
		File file = upFile.getFile();
        String extName = StringUtil.getFileExt(file.getName());
        String filePath = upFile.getUploadPath();
        String fileName = System.currentTimeMillis() + extName;
        file.renameTo(new File(filePath+"\\"+fileName));
		
	    String url=filePath+"\\"+fileName;
	    String image =Util.GetImageStr(url);
	    file.delete();//文件删除        
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_top_num", "10");
	    options.put("user_top_num", "10");
	    String imageType = "BASE64";
	    String groupIdList = "test";
	    
	    // 人脸搜索
	    JSONObject res = BaiduPlugin.face.multiSearch(image, imageType, groupIdList, options);
	    
	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONArray("user_list").iterator();
	    while(it.hasNext()) {
	    	JSONObject jo=(JSONObject)it.next();
	    	FaceSeachModel m=new FaceSeachModel();
	    	m.setGroup_id(jo.getString("group_id"));
	    	m.setUser_id(jo.getString("user_id"));
	    	m.setUser_info(jo.getString("user_info"));
	    	m.setScore(jo.getDouble("score"));
	    	flist.add(m);
	    }
	    
 	    String classid=getPara("classid");
	    String tcsuid=getPara("tcsuid");
	    int week=getParaToInt("week");
	    //将信息添加到数据库
	    
	    List<StuRegisterNewModel> list=StuRegisterNewModel.signIn(flist,tcsuid,classid,week);
	    
	    setAttr("data", list);
		setAttr("code", 0);
	    renderJson();
	}
	/**
	 * xiao
	 * 人脸登录
	 */
	public void faceLogin() {
		int code = -1;
		
		UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
		File file = upFile.getFile();
        String extName = StringUtil.getFileExt(file.getName());
        String filePath = upFile.getUploadPath();
        String fileName = System.currentTimeMillis() + extName;
        file.renameTo(new File(filePath+"\\"+fileName));        
		
        String url=filePath+"\\"+fileName;
	    String image =Util.GetImageStr(url);
	    file.delete();//文件删除   
        
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
//	    options.put("quality_control", "NORMAL");
//	    options.put("liveness_control", "LOW");
	    
	    String imageType = "BASE64";
	    String groupIdList = "test";
	    
	    // 人脸搜索
	    JSONObject res = BaiduPlugin.face.search(image, imageType, groupIdList, options);
	    List<FaceSeachModel> flist=new ArrayList<FaceSeachModel>();
	    Iterator<Object> it=res.getJSONObject("result").getJSONArray("user_list").iterator();
	    while(it.hasNext()) {
	    	JSONObject jo=(JSONObject)it.next();
	    	FaceSeachModel m=new FaceSeachModel();
	    	m.setGroup_id(jo.getString("group_id"));
	    	m.setUser_id(jo.getString("user_id"));
	    	m.setUser_info(jo.getString("user_info"));
	    	m.setScore(jo.getDouble("score"));
	    	flist.add(m);
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
	    setAttr("data", data);
		setAttr("code", code);
	    renderJson();
	}
	/**
	 * @author xiao
	 * @TODO 用戶登录
	 */
	public void login() {
		String id = getPara("username");
		String password = getPara("password");	
		int code = -1;
		UserModel data =UserModel.findByLogin(id);
		if(data!=null) {
			if(password.equals(data.getPassword())) {
				code = 0;//0成功
			}else {
				code = 2;//2密码错
			}
		}else {
			code = 1;//1用户不存在
		}
		setAttr("data", data);
		setAttr("code", code);
		renderJson();
}
}
