package com.wudi.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class Stu_registerModel extends Model<Stu_registerModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "stu_register";
	public static final Stu_registerModel dao = new Stu_registerModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public Date getReg_time() {
		return get("reg_time");
	}
	public void setReg_time(String reg_time) {
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
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	public String getStustudyid() {
		return get("studey_id");
	}
	public void setStustudyid(String stu_study_id) {
		set("stu_study_id", stu_study_id);
	}
	
	public static Stu_registerModel getById(String id) {
		String sql = "select * from " + tableName +" where id = ?";
		return dao.findFirst(sql, id);
	}
	public static Page<Stu_registerModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select *";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
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
	
}
