package com.wudi.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class StudyModel extends Model<StudyModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "study";
	public static final StudyModel dao = new StudyModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public Date getStart_time() {
		return get("start_time");
	}
	public void setStart_time(String start_time) {
		set("start_time", start_time);
	}
	public Date getEnd_time() {
		return get("end_time");
	}
	public void setEnd_time(String end_time) {
		set("end_time", end_time);
	}
	public String getTeacher() {
		return get("teacher");
	}
	public void setTeacher(String teacher) {
		set("teacher", teacher);
	}
	public String getSubject() {
		return get("subject");
	}
	public void setSubject(String subject) {
		set("subject", subject);
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
	
	public static StudyModel getById(String id) {
		String sql = "select * from " + tableName + " where id = ?";
		return dao.findFirst(sql, id);
	}
	public static Page<StudyModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.*,b*nickname as subject ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" as a left join ").append(SubjectModel.tableName).append(" as b ");
		from_sql.append(" on a.subject=b.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where teacher like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static boolean save(String start_time, String end_time, String teacher, String subject, int status, String remark) {
		StudyModel m = new StudyModel();
		m.setId(StringUtil.getId());
		m.setStart_time(start_time);
		m.setEnd_time(end_time);
		m.setTeacher(teacher);
		m.setSubject(subject);
		m.setStatus(status);
		m.setRemark(remark);
		return m.save();
	}
	public static boolean update(String id, String start_time, String end_time, String teacher, String subject, int status, String remark) {
		StudyModel m = StudyModel.getById(id);
		m.setId(id);
		m.setStart_time(start_time);
		m.setEnd_time(end_time);
		m.setTeacher(teacher);
		m.setSubject(subject);
		m.setStatus(status);
		m.setRemark(remark);
		return m.update();
	}
	public static boolean delStudy(String id) {
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
	public static List<StudyModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
}
