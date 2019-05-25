package com.wudi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;
import com.wudi.bean.FaceSeachModel;
import com.wudi.model.ArrangeSubjectModel;
import com.wudi.model.ClassinfoModel;
import com.wudi.model.NewsModel;
import com.wudi.model.ParentsModel;
import com.wudi.model.StuRegisterNewModel;
import com.wudi.model.SubjectModel;
import com.wudi.model.UserModel;
import com.wudi.plugin.BaiduHttpPlugin;
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
//		String url=u+"\\WebContent\\upload\\1558260092850.jpg";
		String url=u+"\\WebContent\\upload\\xiaoxiao.jpg";
	    String image =Util.GetImageStr(url);  
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_top_num", "10");
	    options.put("user_top_num", "10");
	    String imageType = "BASE64";
	    String groupIdList = "test";
	    
	    // 人脸搜索
	    JSONObject res = BaiduPlugin.face.multiSearch(image, imageType, groupIdList, options);
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
// 	    String classid=getPara("classid");
//	    String tcsuid=getPara("tcsuid");
//	    int week=getParaToInt("week");
//	    //将信息添加到数据库
//	    
//	    List<StuRegisterNewModel> list=StuRegisterNewModel.signIn(flist,tcsuid,classid,week);
	    
	    setAttr("data", res);
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
	    List<FaceSeachModel> flist=BaiduHttpPlugin.face.multiSearch(upFile);
 	    String classid=getPara("classid");
	    String tcsuid=getPara("tcsuid");
	    int week=getParaToInt("week");
	    //将信息添加到数据库
	    List<StuRegisterNewModel> data=StuRegisterNewModel.signIn(flist,tcsuid,classid,week);
	    
	    setAttr("data", data);
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
		FaceSeachModel m=BaiduHttpPlugin.face.search(upFile);
		//到数据库查找一下是否有这个人
	    UserModel data =UserModel.findByLogin(m.getUser_id());
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
	//签到信息接口
	public void stuReg() {
		String classid=getPara("classid");
	    String tcsuid=getPara("tcsuid");
	    int week=getParaToInt("week");
	    List<StuRegisterNewModel> list=StuRegisterNewModel.getListN(tcsuid, week, classid);
	    setAttr("list", list);
	    renderJson();
	    
	}
	//课表接口
	public void ArrSub() {
		String teacher = getPara("id");
		List<ArrangeSubjectModel> data = ArrangeSubjectModel.getArrSub(teacher);
		setAttr("data", data);
		renderJson();
	}
	//学生信息接口
	public void stuMage() {
		String id = getPara("id");
		List<ClassinfoModel> data = ClassinfoModel.getStuMage(id);
		setAttr("data", data);
		renderJson();
	}
	//学生课表接口
	public void stuSub() {
		String classid = getPara("classid");
		List<ArrangeSubjectModel> data = ArrangeSubjectModel.getStuSub(classid);
		setAttr("data", data);
		renderJson();
	}
	//消息接口
	public void massage() {
		List<NewsModel> data = NewsModel.getAll();
		setAttr("data", data);
		renderJson();
	}
	
	//家长信息
	public void pareMage() {
		String id = getPara("id");
		List<ParentsModel> data = ParentsModel.getPare(id);
		setAttr("data", data);
		renderJson();
	}
	//家长所对应的学生信息
	public void parStu() {
		
	}
	
}
