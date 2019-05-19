package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

public class ParentsModel extends Model<ParentsModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "parents";
	public static final ParentsModel dao = new ParentsModel();
	
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
	public String getContact() {
		return get("contact");
	}
	public void setContact(String contact) {
		set("contact", contact);
	}
	public String getHome_addr() {
		return get("home_addr");
	}
	public void setHome_addr(String home_addr) {
		set("home_addr", home_addr);
	}
	public String getRemark() {
		return get("remark");
	}
	public void setRemark(String remark) {
		set("remark", remark);
	}
	
	public static ParentsModel getById(String id) {
		String sql = "select * from " + tableName + " where id=?";
		return dao.findFirst(sql, id);
	}
	
	public static Page<ParentsModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.*,b.username as userid ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" as a left join ").append(UserModel.tableName).append(" as b");
		from_sql.append(" on a.userid=b.id ");
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where home_addr like '%"+key+"%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	public static boolean save(String home_addr, String remark, String contact) {
		ParentsModel m = new ParentsModel();
		m.setId(StringUtil.getId());
		m.setRemark(remark);
		m.setHome_addr(home_addr);
		m.setContact(contact);
		return m.save();
	}
	
	public static boolean update(String id, String home_addr, String remark, String contact, String userid) {
		ParentsModel m = dao.getById(id);
		m.setRemark(remark);
		m.setHome_addr(home_addr);
		m.setContact(contact);
		m.setUserid(userid);
		return m.update();
	}
	
	public static boolean delParent(String id) {
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
	public static List<ParentsModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
}
