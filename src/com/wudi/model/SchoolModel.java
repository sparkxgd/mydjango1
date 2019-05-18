package com.wudi.model;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wudi.util.StringUtil;
/**
 * 学校信息 
 *
 */
public class SchoolModel extends Model<SchoolModel> {
	
	private static final long serialVersionUID = 1L;
	public static final String tableName = "school";
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
	
	public String getSchool() {
		return get("school");
	}
	public void setSchool(String school) {
		set("school",school);
	}
	
	public String getReamrk() {
		return ("remark");
	}
	public void setReamrk(String remark) {
		set("remark",remark);
	}
	//因为经常用他，所以干脆给他一个静态的，让他一直存在，免得我们每次new
	public static final SchoolModel dao = new SchoolModel();
	
	/**
	 * 分页查询显示，就是查找
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public static Page<SchoolModel> getList(int pageNumber, int pageSize,String key) {
		String sele_sql="select * ";
		StringBuffer from_sql=new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where schoolname like '%"+key+"%'");
		}
		return dao.paginate(pageNumber,pageSize,sele_sql,from_sql.toString());
	}
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public static SchoolModel getById(Object id){
		return dao.findFirst("select *  from " + tableName + " where id = ? " , id);
	}
	public static boolean save(String nickname, String school, String remark) {
	SchoolModel s=new SchoolModel();
	s.setId(StringUtil.getId());
	s.setNickname(nickname);
	s.setSchool(school);
	s.setReamrk(remark);
	return s.save();
	}
	/**
	 * 保存，这个保存时事务的保存，关于支付，关于钱的保存，我们一般都用这样的保存方法，现在我们暂时不用这个，因为不好调试
	 * @param name
	 * @param sex
	 * @return
	 */
	@Before(Tx.class)
	public static boolean save(final SchoolModel student){
		boolean succeed = Db.tx(new IAtom() {
					
					@Override
					public boolean run() throws SQLException {
						student.save();
						return true;
					}
					});
				return succeed;
	}
	/**
	 * 更新
	 */
	public static boolean update(String id, String nickname, String school, String remark){
		
		SchoolModel s=SchoolModel.getById(id);
		if(s==null){
			return false;
		}
		s.setId(id);
		s.setNickname(nickname);
		s.setSchool(school);
		s.setReamrk(remark);
		try {
			s.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public static boolean delSchoolById(String id) {
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
	/**
	 * TODO:XIAO 查找所有信息
	* @Title: getListAll
	* @Description:???
	* @param @return    参数
	* @return List<SchoolZoneModel>    返回类型
	* @throws
	 */
	public static List<SchoolModel> getListAll() {
		StringBuffer sql=new StringBuffer();
		sql.append("select *  from ").append(tableName);
		return dao.find(sql.toString());
	}
}

