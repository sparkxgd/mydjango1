package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class MajorModel extends Model<MajorModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "major";
	public static final MajorModel dao = new MajorModel();
	
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
	public String getDepartment() {
		return get("department");
	}
	public void setDepartment(String department) {
		set("department", department);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	
	
	public static Page<MajorModel> getList(int pageNumber, int pageSize,String key) {
		String sele_sql="select a.*,b.nickname as department ";
		StringBuffer from_sql=new StringBuffer();
		from_sql.append("from ").append(tableName).append(" as a left join ").append(DepartmentModel.tableName).append(" as b ");
		from_sql.append(" on a.department=b.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber,pageSize,sele_sql,from_sql.toString());
	} 
	public static MajorModel getById(Object id){
		return dao.findFirst("select *  from " + tableName + " where id = ? " , id);
	}
	public static boolean save(String nickname,String remark,String department) {
		MajorModel s=new MajorModel();
		s.setId(StringUtil.getId());
		s.setNickname(nickname);
		s.setDepartment(department);
		s.setRemark(remark);
		return s.save();
	}
	public static boolean update(String id,String nickname,String remark,String department){
		MajorModel model=MajorModel.getById(id);
		if(model==null){
			return false;
		}
		model.setId(id);
		model.setNickname(nickname);
		model.setDepartment(department);
		model.setRemark(remark);
		try {
			model.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static boolean delMajorByID(String id) {
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
	public static List<MajorModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
}
