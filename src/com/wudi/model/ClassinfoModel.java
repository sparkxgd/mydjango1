package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class ClassinfoModel extends Model<ClassinfoModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "classinfo";
	public static final ClassinfoModel dao = new ClassinfoModel();
	
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
	public String getGrade() {
		return get("grade");
	}
	public void setGrade(String grade) {
		set("grade", grade);
	}
	public String getMajor_id() {
		return get("major_id");
	}
	public void setMajor_id(String major_id) {
		set("major_id", major_id);
	}
	public String getHeadmaster() {
		return get("headmaster");
	}
	public void setHeadmaster(String headmaster) {
		set("headmaster", headmaster);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	
	public static ClassinfoModel getById(String id) {
		String sql = "select * from " + tableName + " where id=?";
		return dao.findFirst(sql, id);
	}
	public static Page<ClassinfoModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.*,c.nickname as majorname,b.username ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append(" from ").append(tableName).append(" as a left join ").append(TeacherModel.tableName).append(" as d on a.headmaster=d.id left join ").append(UserModel.tableName).append(" as b ").append(" on d.userid=b.id ").append(" left join ").append(MajorModel.tableName).append(" as c ");
		from_sql.append(" on a.major_id=c.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where a.nickname like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static boolean save(String nickname, String grade, String major_id, String headmaster, String remark) {
		ClassinfoModel m = new ClassinfoModel();
		m.setId(StringUtil.getId());
		m.setNickname(nickname);
		m.setGrade(grade);
		m.setMajor_id(major_id);
		m.setHeadmaster(headmaster);
		m.setRemark(remark);
		return m.save();
	}
	public static boolean update(String id, String nickname, String grade, String major_id, String headmaster, String remark) {
		ClassinfoModel m = ClassinfoModel.getById(id);
		if(m==null){
			return false;
		}
		m.setNickname(nickname);
		m.setGrade(grade);
		m.setMajor_id(major_id);
		m.setHeadmaster(headmaster);
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
	public static List<ClassinfoModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
	
	public static List<ClassinfoModel> getClassId() {
		StringBuffer sql=new StringBuffer();
		sql.append("select id  from ").append(tableName);
		return dao.find(sql.toString());
	}
}
