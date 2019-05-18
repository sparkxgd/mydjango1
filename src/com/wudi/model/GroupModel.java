package com.wudi.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wudi.plugin.BaiduPlugin;
import com.wudi.util.StringUtil;

/**
 * 
 * @xiao
 *
 */
public class GroupModel extends Model<GroupModel>{
	public static final long serialVersionUID = 1L;
	public static final String tableName = "baidugroup";
	public static final GroupModel dao = new GroupModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	
	public String getName() {
		return get("name");
	}
	public void setName(String name) {
		set("name", name);
	}
	
	@Before(Tx.class)
	private static boolean save(final GroupModel m){
		boolean succeed = Db.tx(new IAtom() {
					
					@Override
					public boolean run() throws SQLException {
						HashMap<String, String> options = new HashMap<String, String>();
						BaiduPlugin.face.groupAdd(m.getId(), options);
						return m.save();
					}
					});
				return succeed;
	}
	public static boolean save(String name) {
		GroupModel m = new GroupModel();
		String id=StringUtil.getId();
		m.setId(id);
		m.setName(name);
		return save(m);
	}
	
	@Before(Tx.class)
	public static boolean del(final String id){
		boolean succeed = Db.tx(new IAtom() {
					
					@Override
					public boolean run() throws SQLException {
						HashMap<String, String> options = new HashMap<String, String>();
						BaiduPlugin.face.groupDelete(id, options);
						String delsql = "DELETE FROM " + tableName + " WHERE id=?";
						int iRet = Db.update(delsql, id);
						if (iRet > 0) {
							return true;
						} else {
							return false;
						}
					}
					});
				return succeed;
	}
	public static Page<GroupModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where  name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	public static GroupModel getModelById(String id) {
		String sql="select * from "+tableName+" where id=?";
		return dao.findFirst(sql,id);
	}
	public static List<GroupModel> getList() {
		String sql="select * from "+tableName;
		return dao.find(sql);
	}
}
