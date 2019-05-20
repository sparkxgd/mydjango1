package com.wudi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.bean.FaceSeachModel;
import com.wudi.controller.WeixinController;
import com.wudi.util.StringUtil;

public class StuRegisterNewModel extends Model<StuRegisterNewModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "stu_registernews";
	public static final StuRegisterNewModel dao = new StuRegisterNewModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public Date getReg_time() {
		return get("reg_time");
	}
	public void setReg_time(Date reg_time) {
		set("reg_time", reg_time);
	}
	public String getAddr() {
		return get("addr");
	}
	public void setAddr(String addr) {
		set("addr", addr);
	}
	public int getType() {
		return get("type");
	}
	public void setType(int type) {
		set("type", type);
	}
	public int getWeek() {
		return get("week");
	}
	public void setWeek(int week) {
		set("week", week);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	public String gettcsuid() {
		return get("tcsuid");
	}
	public void settcsuid(String tcsuid) {
		set("tcsuid", tcsuid);
	}
	public String getstuid() {
		return get("stuid");
	}
	public void setstuid(String stuid) {
		set("stuid", stuid);
	}
	public int getstatus() {
		return get("status");
	}
	public void setstatus(int status) {
		set("status", status);
	}
	public String getUserid() {
		return get("userid");
	}
	public String getClassid() {
		return get("classid");
	}
	public void setClassid(String classid) {
		set("classid", classid);
	}
	
	public static StuRegisterNewModel getById(String id) {
		String sql = "select * from " + tableName +" where id = ?";
		return dao.findFirst(sql, id);
	}
	public static StuRegisterNewModel getBys(String tcsuid,int week, String classid) {
		String sql = "select * from " + tableName +" where week =? and tcsuid=? and classid = ?";
		return dao.findFirst(sql, week,tcsuid,classid);
	}
	public static StuRegisterNewModel getByStuid(String id) {
		String sql = "select a.*,b.userid from " + tableName +" as a left join "+StudentModel.tableName+" as b on a.stuid=b.id where stuid = ?";
		return dao.findFirst(sql, id);
	}
	public static Page<StuRegisterNewModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select *";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	/**
	 * 老师开始签到的时候，先给学生签到表生成记录，后面再更新状态
	 * @param claid
	 * @return
	 */
	public static boolean addStuRegByClass(String classid,String tcsuid,int week) {
		boolean result=true;
		ClassinfoModel c=ClassinfoModel.getById(classid);
		if(c!=null) {
			List<StudentModel> li=StudentModel.getListbyClassid(classid);
			for( StudentModel m:li) {
				StuRegisterNewModel s=new StuRegisterNewModel();
				s.setId(StringUtil.getId());
				s.setstatus(0);//0未签到，1已签到，-1其他，2旷课，3事假，4病假
				s.setstuid(m.getId());
				s.settcsuid(tcsuid);
				s.setWeek(week);
				s.setClassid(classid);
				s.save();
			}
		}else {
			 result=false;
		}
		return result;
	}
	
	public static List<StuRegisterNewModel> signIn(List<FaceSeachModel> list,String tcsuid,String classid,int week) {
		
		StuRegisterNewModel mm=StuRegisterNewModel.getBys(tcsuid,week,classid);
		if(mm==null) {
			addStuRegByClass(classid,tcsuid,week);
//			StuRegisterNewModel.signIn(list,tcsuid,classid,week);
		}else {
			for(FaceSeachModel m:list) {
				StuRegisterNewModel s=getByStuid(m.getUser_id());
				s.setstatus(1);//0未签到，1已签到，-1其他，2旷课，3事假，4病假
				s.setType(0);//0上课，1下课
				s.setReg_time(new Date());
				s.update();
			}
		}
		return getListN();
	}
	
	
	public static boolean delStu_register(String id) {
		try {
			String delsql = "delete from " + tableName + " where id=?";
			int iRet = Db.update(delsql, id);
			if(iRet > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static List<StuRegisterNewModel> getListN() {
		String sql = "SELECT c.username ,b.`no`,a.`status` from stu_registernews as a LEFT JOIN student as b on a.stuid=b.id LEFT JOIN `user` as c on b.userid=c.id";
		return dao.find(sql);
	}
}
