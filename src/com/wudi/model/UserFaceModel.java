package com.wudi.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

/**
* 功能：
* 作者：xiao
* 修改时间：2019年5月18日上午12:04:01
 */
public class UserFaceModel extends Model<UserFaceModel>{

	private static final long serialVersionUID = 1L;
	public static final String tableName = "userface";
	public static final UserFaceModel dao = new UserFaceModel();
	
	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}
	public String getGroup_id() {
		return get("group_id");
	}
	public void setGroup_id(String group_id) {
		set("group_id", group_id);
	}
	public String getUserInfo() {
		return get("user_info");
	}
	public void setUserInfo(String user_info) {
        set("user_info",user_info);
	}
	
	public String getImg() {
		return get("img");
	}
	public void setImg(String img) {
		set("img", img);
	}
	
	public static Page<UserFaceModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.* ,b.name";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName).append(" as a ").append(" left join ").append(GroupModel.tableName).append(" as b ");
		from_sql.append(" on a.group_id=b.id ");
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where  b.name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	public static UserFaceModel getById(String id) {

		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}

	public static boolean delById(String id) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE id=?";
			int iRet = Db.update(delsql, id);
			if (iRet > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
