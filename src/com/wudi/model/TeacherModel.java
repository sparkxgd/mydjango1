package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class TeacherModel extends Model<TeacherModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "teacher";
	public static final TeacherModel dao = new TeacherModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getUserid() {
		return get("userid");
	}
	public void setUserid(String userid) {
		set("userid", userid);
	}
	public String getNo() {
		return get("no");
	}
	public void setNo(String no) {
		set("no",no);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	public String getContact() {
		return get("contact");
	}
	public void setContact(String contact) {
		set("contact", contact);
	}
	
	public static TeacherModel getById(String id) {
		return dao.findFirst("select *  from " + tableName + " where id = ?", id);
	}
	
	public static Page<TeacherModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where no like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	public static boolean save(String no, String remark, String contact) {
		TeacherModel m = new TeacherModel();
		m.setId(StringUtil.getId());
		m.setNo(no);
		m.setRemark(remark);
		m.setContact(contact);
		return m.save();
	}
	
	public static boolean updateTeacher(String id, String no, String remark, String contact) {
		TeacherModel m = TeacherModel.getById(id);
		if(m==null){
			return false;
		}
		m.setNo(no);
		m.setRemark(remark);
		m.setContact(contact);
		try {
			m.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean delTeacher(String id) {
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
	public static List<TeacherModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
}
