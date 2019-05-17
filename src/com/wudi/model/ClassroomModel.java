package com.wudi.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class ClassroomModel extends Model<ClassroomModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "classroom";
	public static final ClassroomModel dao = new ClassroomModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getNickname() {
		return get("nickname");
	}
	public void setNickname(String nickname) {
		set("nickname", nickname);
	}
	public String getAddr() {
		return get("addr");
	}
	public void setAddr(String addr) {
		set("addr", addr);
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
	public static ClassroomModel getById(String id) {
		String sql = "select * from " + tableName +" where id = ?";
		return dao.findFirst(sql, id);
	}
	public static Page<ClassroomModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select *";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static boolean save(String nickname, String addr, int status, String remark) {
		ClassroomModel m = new ClassroomModel();
		m.setId(StringUtil.getId());
		m.setNickname(nickname);
		m.setAddr(addr);
		m.setStatus(status);
		m.setRemark(remark);
		return m.save();
	}
	public static boolean update(String id, String nickname, String addr, int status, String remark) {
		ClassroomModel m = ClassroomModel.getById(id);
		m.setId(id);
		m.setNickname(nickname);
		m.setAddr(addr);
		m.setStatus(status);
		m.setRemark(remark);
		return m.update();
	}
	public static boolean delClassInfo(String id) {
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
