package com.wudi.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class Arrange_subjectModel extends Model<Arrange_subjectModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "arrange_subject";
	public static final Arrange_subjectModel dao = new Arrange_subjectModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
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
	public String getClass_time() {
		return get("class_time");
	}
	public void setClass_time(String class_time) {
		set("class_time", class_time);
	}
	public String getClassid() {
		return get("classid");
	}
	public void setClassid(String classid) {
		set("classid", classid);
	}
	public String getClassroom() {
		return get("classroom");
	}
	public void setClassroom(String classroom) {
		set("classroom", classroom);
	}
	
	public static Arrange_subjectModel getById(String id) {
		String sql = "select * from " + tableName + " where id = ?";
		return dao.findFirst(sql, id);
	}
	public static Page<Arrange_subjectModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.*,b.nickname as classid,c.nickname as classroom ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(ClassinfoModel.tableName).append(" as b right join ").append(tableName).append(" as a ").append(" on a.classid=b.id ");
		from_sql.append("left join ").append(ClassroomModel.tableName).append(" as c ").append(" on a.classroom=c.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where teacher like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static boolean save(String teacher, String subject, String class_time, String classid, String classroom) {
		Arrange_subjectModel m = new Arrange_subjectModel();
		m.setId(StringUtil.getId());
		m.setTeacher(teacher);
		m.setSubject(subject);
		m.setClass_time(class_time);
		m.setClassid(classid);
		m.setClassroom(classroom);
		return m.save();
	}
	public static boolean updata(String id, String teacher, String subject, String class_time, String classid, String classroom) {
		Arrange_subjectModel m = Arrange_subjectModel.getById(id);
		m.setId(id);
		m.setTeacher(teacher);
		m.setSubject(subject);
		m.setClass_time(class_time);
		m.setClassid(classid);
		m.setClassroom(classroom);
		return m.update();
	}
	public static boolean delArrang_sub(String id) {
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
