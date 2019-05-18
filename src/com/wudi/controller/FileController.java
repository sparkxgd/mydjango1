package com.wudi.controller;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wudi.plugin.BaiduPlugin;
import com.wudi.util.StringUtil;
import com.wudi.util.Util;
/**
* TODO:肖，文件上传下载控制类
* @Description: ???
* @author xiao
* @date 2018年11月21日下午5:52:58
 */
public class FileController extends Controller {
	public void index() {
		setAttr("info", "这是文件上传下载控制器，你确定要找这个？？");
		renderJson();
	}
	/**
	 *  TODO:文件上传
	* @Title: upload
	* @Description:上传头像
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void uploadImg() {
		UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
		File file = upFile.getFile();
        String extName = StringUtil.getFileExt(file.getName());
        String filePath = upFile.getUploadPath();
        String fileName = System.currentTimeMillis() + extName;
        file.renameTo(new File(filePath+"\\"+fileName));
        
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "gender,age,beauty");
	    String url=filePath+"\\"+fileName;
	    String image =Util.GetImageStr(url);
        String imageType = "BASE64";
        
        // 人脸检测
        JSONObject res = BaiduPlugin.face.detect(image, imageType, options);
        System.out.println(res);
        //检测到的图片中的人脸数量
        int face_num=res.getJSONObject("result").getInt("face_num");
        //人脸置信度，范围【0~1】，代表这是一张人脸的概率，0最小、1最大。
        double face_probability=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).optDouble("face_probability");
        double age=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).optDouble("age");
        double beauty=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).optDouble("beauty");
        String type=res.getJSONObject("result").getJSONArray("face_list").getJSONObject(0).getJSONObject("gender").getString("type");

        if(face_num<1||face_probability<0.8) {
            file.delete();
        	setAttr("code", 1);
        	setAttr("msg", "没有检查到人脸或者，图片太模糊！！请重新上传");
        	setAttr("src", "");
        }else {
        	setAttr("code", 0);
        	setAttr("src", fileName);
        	setAttr("age", age);
        	setAttr("beauty", beauty);
        	setAttr("type", type);
        }
        
		renderJson();
	}

}
