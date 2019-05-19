package com.wudi.model;

import java.io.File;
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
import com.wudi.util.Util;
/**
 * 
 * @author ljp
 *
 */

public class UserModel extends Model<UserModel>{

	private static final long serialVersionUID = 1L;
	public static final String tableName = "user";
	public static final UserModel dao = new UserModel();
	
	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}
	public String getUsername() {
		return get("username");
	}
	public void setUsername(String username) {
		set("username", username);
	}
	public int getSex() {
		return get("sex");
	}
	public void setSex(int sex) {
		set("sex", sex);
	}
	public String getPassword() {
		return get("password");
	}
	public void setPassword(String password) {
        set("password",password);
	}
	public String getBirth() {
		return get("birth");
	}
	public void setBirth(String birth) {
		set("birth",birth);
	}
	public int getType() {
		return get("type");
	}
	public void setType(int type) {
		set("type",type);
	}
	public int getStatus() {
		return get("status");
	}
	public void setStatus(int status) {
		set("status",status);
	}
	public String getRole_id() {
		return get("role_id");
	}
	public void setRole_id(String role_id) {
		set("role_id",role_id);
	}
	public String getImg() {
		return get("img");
	}
	public void setImg(String img) {
		set("img", img);
	}
	
	
	
	
	public static UserModel getById(String id) {

		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}
	/**
	 * 
	 */
	public static Page<UserModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select a.* ,b.name as role_id ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append(" from ").append(tableName).append(" a INNER JOIN ").append(RoleModel.tableName).append(" b on a.role_id=b.id");
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append("where  a.name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	public static List<UserModel>getXls(String role_id){
		List<UserModel> list;
		if(role_id.equals("001")) {
			list = dao.find("select * from "+tableName+" where role_id='001'");
		}else {
			list = dao.find("select * from "+tableName+" where role_id='2'");
		}
		return list;
	}
	
	
	public static boolean saveUserinfo(String username, int sex, String password, String birth, int type, String img) {
		UserModel m=new UserModel();
		m.setId(StringUtil.getId());
		m.setUsername(username);
		m.setSex(sex);
		m.setPassword(password);
		m.setBirth(birth);
		m.setType(type);
		m.setStatus(0);
		m.setRole_id("1555148650901");
		m.setImg(img);
		
		return save(m);
	}
	
	@Before(Tx.class)
	private static boolean save(final UserModel m){
		boolean succeed = Db.tx(new IAtom() {
					
					@Override
					public boolean run() throws SQLException {
						boolean re=false;
						boolean ree=false;
						HashMap<String, String> options = new HashMap<String, String>();
						String u=System.getProperty("user.dir");
						String url=u+"\\WebContent\\upload\\"+m.getImg();
					    String image =Util.GetImageStr(url);
						BaiduPlugin.face.addUser(image, "BASE64", "test", m.getId(), options);
						ree=m.save();
						if(m.getType()==1) {
							StudentModel s=new StudentModel();
							s.setId(StringUtil.getId());
							s.setUserid(m.getId());
							re=s.save();
						}else if(m.getType()==2) {
							TeacherModel s=new TeacherModel();
							s.setId(StringUtil.getId());
							s.setUserid(m.getId());
							re=s.save();
						}else if(m.getType()==3) {
							ParentsModel s=new ParentsModel();
							s.setId(StringUtil.getId());
							s.setUserid(m.getId());
							re=s.save();
						}
						if(ree&&re) {
							File f=new File(url);
							if(f.exists()) {
								f.delete();
							}
						}
						return ree&&re;
					}
					});
				return succeed;
	}
	
	/**
	 * @author ljp
	 * @param phone
	 * @return
	 */
	public static UserModel loginById(String id) {
		String sql = "select a.*,b.name as rolename,b.permission from "+tableName+" a LEFT JOIN "+RoleModel.tableName+" b ON a.role_id=b.id where a.id = ?";
		return dao.findFirst(sql,id);
	}
	/**
	 * ���ݺ�����ҿͻ�������Ϣ
	 * @param phone_no
	 * @return
	 */
	public List<UserModel> getUserAllInfo(String id) {
		UserModel m=new UserModel();
		String selectsql = "SELECT * FROM user WHERE id=?";
		List<UserModel> list = m.find(selectsql,id);
		return list;
	}
	
	/**
	 * ��ѯ����
	 * @param phone_no
	 * @return
	 */
	public static UserModel findById(String username) {
		String selectsql = "SELECT * FROM " + tableName + " WHERE username=?";
		return dao.findFirst(selectsql,username);
		
	}
	/**
	 * 审核用户
	 * @param id
	 * @return
	 */
	public static boolean checkUser(String id) {
		UserModel m=dao.findById(id);
		m.setStatus(1);//1:正常，0未审核,-1异常
		return m.update();
	}
	
	public static boolean upateUserRole(String id,String role_id) {
		UserModel m=dao.findById(id);
		m.setRole_id(role_id);
		return m.update();
	}
	/**
	 * 用户登录
	 * 把用户的基本信息，包括权限一起拿
	 * @param phone
	 * @return
	 */
	public static UserModel findByLogin(String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.permission from ").append(tableName).append(" a INNER JOIN ");
		sql.append(RoleModel.tableName).append(" b on a.role_id=b.id ");
		sql.append(" where a.id=?");
		return dao.findFirst(sql.toString(),username);
		
	}
	/**
	 *  功能：修改密码
	 *  修改时间：2019年3月20日22:47:23
	 *  作者： xiao
	 */
	public static boolean updatePassword(String id,String password){
		UserModel m=getById(id);
		m.setPassword(password);
		return m.update();
	}
//	public static boolean updateLevel(String id){
//		UserModel m=getById(id);
//		m.setLevel(1);
//		return m.update();
//	}
//	public static boolean updateLevel(String id,int level){
//		UserModel m=getById(id);
//		m.setLevel(level);
//		return m.update();
//	}
	

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
