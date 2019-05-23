package com.wudi.controller;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wudi.plugin.BaiduHttpPlugin;
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
	    String url=filePath+"\\"+fileName;
	    String image =Util.GetImageStr(url);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", image);
        map.put("face_field", "age,beauty,gender");
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");
		
	    JSONObject res=BaiduHttpPlugin.face.detect(map);

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
        }
		renderJson();
	}

}
