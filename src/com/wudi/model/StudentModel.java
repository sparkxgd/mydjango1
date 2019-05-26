package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class StudentModel extends Model<StudentModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "student";
	
	
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
		set("no", no);
	}
	public String getClas() {
		return get("clas");
	}
	public void setClas(String clas) {
		set("clas", clas);
	}
	public int getType() {
		return get("type");
	}
	public void setType(int type) {
		set("type", type);
	}
	
	public static final StudentModel dao = new StudentModel();
	
	public static StudentModel getById(String id) {
		System.out.print(id);
		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}
	
	public static Page<StudentModel> getList(int pageNumber, int pageSize,String key) {
		String sele_sql = "select a.*,b.username,b.sex,c.nickname";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" as a left join ").append(UserModel.tableName).append(" as b");
		from_sql.append(" on a.userid=b.id ").append(" left join ").append(ClassinfoModel.tableName).append(" c on a.clas=c.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where b.username like '%"+key+"%'");
		}
		return dao.paginate(pageNumber,pageSize,sele_sql,from_sql.toString());
	}
	
	public static boolean save(String no, String userid,String clas, int type) {
		StudentModel m = new StudentModel();
		m.setId(StringUtil.getId());
		m.setNo(no);
		m.setClas(clas);
		m.setType(type);
		m.setUserid(userid);
		return m.save();
	}
	
	public static boolean updateStudent(String id, String no, String clas, int type) {
		StudentModel m = StudentModel.getById(id);
		if(m==null){
			return false;
		}
		m.setId(id);
		m.setNo(no);
		m.setClas(clas);
		m.setType(type);
		try {
			m.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean delStudent(String id) {
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
	
	public static List<StudentModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select b.username,a.*  from ").append(tableName).append(" as a");
		sql.append(" left join ").append(UserModel.tableName).append(" as b on a.userid=b.id");
		return dao.find(sql.toString());
	}
	public static List<StudentModel> getListbyClassid(String clid) {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName).append(" where clas=?");
		return dao.find(sql.toString(),clid);
	}
	public static List<StudentModel> getStu(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.username as parename,c.relation,d.no,d.clas as classid,f.username as stuname,e.class_time,i.username as teachername,j.nickname as subjname,k.addr ");
		sql.append(" from ").append(UserModel.tableName).append(" as a ");
		sql.append(" left join ").append(ParentsModel.tableName).append(" as b on a.id=b.userid");
		sql.append(" left join ").append(Stu_pareModel.tableName).append(" as c on b.id=c.pare_id");
		sql.append(" left join ").append(StudentModel.tableName).append(" as d on d.id=c.stu_id");
		sql.append(" left join ").append(UserModel.tableName).append(" as f on d.userid=f.id ");
		sql.append(" left join ").append(ArrangeSubjectModel.tableName).append(" as e on d.clas=e.classid ");
		sql.append(" left join ").append(TeacherModel.tableName).append(" as h on h.id=e.teacher ");
		sql.append(" left join ").append(UserModel.tableName).append(" as i on i.id=h.userid ");
		sql.append(" left join ").append(SubjectModel.tableName).append(" as j on e.subject=j.id ");
		sql.append(" left join ").append(ClassroomModel.tableName).append(" as k on k.id=e.classroom where a.id=?");
		return dao.find(sql.toString(), id);
	}
	
	public static StudentModel getStuid(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from ").append(tableName).append(" as a ");
		sql.append("left join ").append(Stu_pareModel.tableName).append(" as b on a.id=b.stu_id ");
		sql.append("left join ").append(ParentsModel.tableName).append(" as c on c.id=b.pare_id ");
		sql.append("left join ").append(UserModel.tableName).append(" as d on d.id=c.userid ");
		sql.append("left join ").append(ClassinfoModel.tableName).append(" as e on a.clas=e.id ");
		sql.append(" left join ").append(ArrangeSubjectModel.tableName).append(" as f on f.classid=e.id ");
		sql.append(" WHERE d.id=?");
		return dao.findFirst(sql.toString(), id);
	}
}
