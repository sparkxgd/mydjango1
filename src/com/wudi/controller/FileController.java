package com.wudi.controller;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wudi.bean.FaceDetectBean;
import com.wudi.plugin.BaiduHttpPlugin;
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
		
		FaceDetectBean b=BaiduHttpPlugin.face.detect(upFile);
		setAttr("code", b.getCode());
        setAttr("src", b.getSrc());
        setAttr("age", b.getAge());
        setAttr("beauty", b.getBeauty());
        setAttr("type", b.getType());
		renderJson();
	}

}
