package com.wudi.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;


/**
 * 
 * @author lyp
 *
 */
public class Stu_pareModel extends Model<Stu_pareModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "stu_pare";
	public static final Stu_pareModel dao = new Stu_pareModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getPare_id() {
		return get("pare_id");
	}
	public void setPare_id(String pare_id) {
		set("pare_id", pare_id);
	}
	public String getStu_id() {
		return get("stu_id");
	}
	public void setStu_id(String stu_id) {
		set("stu_id", stu_id);
	}
	public String getRelation() {
		return get("relation");
	}
	public void setRelation(String relation) {
		set("relation", relation);
	}
	
	public static Stu_pareModel getById(String id) {
		String sql = "select * from " + tableName + " where id=?";
		return dao.findFirst(sql, id);
	}
	public static Page<Stu_pareModel> getList(int pageNumber, int pageSize,String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber,pageSize,sele_sql,from_sql.toString());
	}
	public static boolean save(String pare_id, String stu_id, String relation) {
		Stu_pareModel m = new Stu_pareModel();
		m.setId(StringUtil.getId());
		m.setPare_id(pare_id);
		m.setStu_id(stu_id);
		m.setRelation(relation);
		return m.save();
	}
	public static boolean update(String id, String pare_id, String stu_id, String relation) {
		Stu_pareModel m=Stu_pareModel.getById(id);
		if(m == null) {
			return false;
		}
		m.setPare_id(pare_id);
		m.setStu_id(stu_id);
		m.setRelation(relation);
		try {
			m.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static boolean delStu_pareByID(String id) {
		try {
			String delsql="DELETE FROM "+tableName+" WHERE id=?";
			int iRet=Db.update(delsql, id);
			if(iRet > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
