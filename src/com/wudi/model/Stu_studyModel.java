package com.wudi.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class Stu_studyModel extends Model<Stu_studyModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "stu_study";
	public static final Stu_studyModel dao = new Stu_studyModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getStudey_id() {
		return get("studey_id");
	}
	public void setStudey_id(String studey_id) {
		set("studey_id", studey_id);
	}
	public int getStatus() {
		return get("status");
	}
	public void setStatus(int status) {
		set("status", status);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	
	public static Stu_studyModel getById(String id) {
		String sql = "select * from "+tableName+" where id = ?";
		return dao.findFirst(sql, id);
	}
	
	public static Page<Stu_studyModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where studey_id like '%"+key+"%' ");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static boolean save(String studey_id, int status, String remark) {
		Stu_studyModel m = new Stu_studyModel();
		m.setId(StringUtil.getId());
		m.setStudey_id(studey_id);
		m.setStatus(status);
		m.setRemark(remark);
		return m.save();
	}
	public static boolean update(String id, String studey_id, int status, String remark) {
		Stu_studyModel m = Stu_studyModel.getById(id);
		m.setId(StringUtil.getId());
		m.setStudey_id(studey_id);
		m.setStatus(status);
		m.setRemark(remark);
		return m.update();
	}
	public static boolean delStu_study(String id) {
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
}
