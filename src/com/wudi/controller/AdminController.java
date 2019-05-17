package com.wudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.interceptor.AdminInterceptor;
import com.wudi.model.ClassinfoModel;
import com.wudi.model.GroupModel;
import com.wudi.model.MajorModel;
import com.wudi.model.ParentsModel;
import com.wudi.model.RoleModel;
import com.wudi.model.Stu_pareModel;
import com.wudi.model.StudentModel;
import com.wudi.model.TeacherModel;
import com.wudi.model.UserFaceModel;
import com.wudi.model.UserModel;
/**
 * 
 * @author ljp
 *
 */

@Before(AdminInterceptor.class)
public class AdminController extends Controller {
	/**
	 *  功能：登录
	 *  修改时间：2019年3月20日22:47:23
	 *  作者： xiao
	 */
	@Clear(AdminInterceptor.class)
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		// 如果不正确，就提示什么不正确？
		// 如果正确，就正常显示系统页面
		UserModel m = UserModel.findByLogin(username);
		// 判断用户名和密码是否正确
		if (m != null) {
			if (m.getPassword().equals(password)) {
				setAttr("result", 0);// 可以登录
				setCookie("cname",m.getUsername(), 36000);
				setSessionAttr("user", m);
			} else {
				setAttr("result", 1);// 密码错误
			}
		} else {
			setAttr("result", 2);// 用户名不存在
		}
		renderJson();
	}

	/**
	 *  功能：退出系统
	 *  修改时间：2019年3月20日22:47:23
	 *  作者： xiao
	 */
	@Clear(AdminInterceptor.class)
	public void outLogin() {
		removeCookie("username");
		removeSessionAttr("user");
		redirect("/admin");
	}

	/**
	 *  功能：主页
	 *  修改时间：2019年3月20日22:47:23
	 *  作者： xiao
	 */
	public void index() {
		setAttr("user", getSessionAttr("user"));
		renderFreeMarker("index.html");
	}

	/**
	 *  功能：首页
	 *  修改时间：2019年3月20日22:47:23
	 *  作者： xiao
	 */
	public void main() {
		render("main.html");
	}
	/**
	 *  功能：打开修改密码页面
	 *  修改时间：2019年3月21日20:47:23
	 *  作者： xiao
	 */
	public void openUppassword() {
		setAttr("user", getSessionAttr("user"));
		renderFreeMarker("userinfo/uppassword.html");
	}
	/**
	 *  功能：打开修改用户密码页面
	 *  修改时间：2019年3月22日22:47:23
	 *  作者： xiao
	 */
	public void openUpdateUserPassword() {
		String id = getPara("id");
		UserModel m = UserModel.getById(id);
		setAttr("user", m);
		renderFreeMarker("userinfo/uppassword.html");
	}
	/**
	 *  功能：保存修改密码
	 *  修改时间：2019年3月21日20:47:23
	 *  作者： xiao
	 */
	public void updatePassword() {
		String id=getPara("id");
		String password=getPara("password");
		boolean result=UserModel.updatePassword(id, password);
		setAttr("result", result);
		//情况cookie
		removeCookie("username");
		removeSessionAttr("user");
		renderJson();
	}
	/**
	 * 获取用户权限
	 */
	public void getPermission() {
		setAttr("user", getSessionAttr("user"));
		renderJson();
	}
	
	public void openUserinfoAdd() {
		render("userinfo/userinfoAdd.html");
	}
	
	public void saveUser() {
		String username = getPara("username");
		int sex = getParaToInt("sex");
		String password = getPara("password");
		String birth = getPara("birth");
		int type = getParaToInt("type");
		String role_id = getPara("role_id");
		String img = getPara("img");
		boolean result = UserModel.saveUserinfo(username, sex, password, birth, type, img);
		setAttr("result", result);
		renderJson();
	}
	
	/**
	 * 打开用户信息界面
	 */
	public void userinfo() {
		render("userinfo/userinfoInfo.html");
	}
	/**
	 * 获取用户信息
	 */
	public void getUserInfoList() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<UserModel> list = UserModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}
	/**
	 * 审核用户
	 */
	public void checkUser() {
		String id=getPara("id");
		boolean result = UserModel.checkUser(id);
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 修改用户角色
	 */
	public void openUpateUserRole() {
		String id=getPara("id");
		UserModel user=UserModel.getById(id);
		setAttr("user", user);
		renderFreeMarker("userinfo/updUserRole.html");
	}
	/**
	 * 保存修改用户角色
	 */
	public void upateUserRole() {
		String id=getPara("id");
		String role_id=getPara("role_id");
		boolean result = UserModel.upateUserRole(id,role_id);
		setAttr("result", result);
		renderJson();
	}
	public void getRoleListForSelect() {
		List<RoleModel> list=RoleModel.getList();
		setAttr("list", list);
		renderJson();
	}
	
	public void delUserModel() {
		String id= getPara("id");
		boolean result = UserModel.delById(id);
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: openRoles
	* @Description:打开角色列表页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openRoles() {
		render("role/roleinfo.html");
	}
	/**
	 * 
	* @Title: getRoleList
	* @Description:获取角色信息列表
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getRoleList() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<RoleModel> list = RoleModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}
	/**
	 * 
	* @Title: openRoleAdd
	* @Description:打开添加角色页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openRoleAdd() {
		render("role/roleAdd.html");
	}
	/**
	 * 
	* @Title: saveRole
	* @Description:添加保存数据
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void saveRole() {
		Map<String,Integer> p=new HashMap<String,Integer>();
		
		String name=getPara("name");
		
		for(int i=100;i<105;i++) {
			String k="c"+i;
			String v=getPara(k);
			if(v==null) {
				p.put(k, 0);
			}else {
				p.put(k, 1);
			}
		}
		boolean result =RoleModel.save(name,JsonKit.toJson(p));
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: openRoleEdit
	* @Description:打开编辑页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openRoleEdit() {
		String id=getPara("id");
		setAttr("id", id);
		renderFreeMarker("role/roleEdit.html");
		
	}
	/**
	 * 
	* @Title: getRoleModel
	* @Description:获取编辑页面数据
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getRoleModel() {
		String id=getPara("id");
		RoleModel m=RoleModel.getModelById(id);
		setAttr("m", m);
		renderJson();
	}
	/**
	 * 
	* @Title: saveRole
	* @Description:添加保存数据
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void updateRole() {
		Map<String,Integer> p=new HashMap<String,Integer>();
		
		String id=getPara("id");
		
		for(int i=100;i<105;i++) {
			String k="c"+i;
			String v=getPara(k);
			if(v==null) {
				p.put(k, 0);
			}else {
				p.put(k, 1);
			}
		}
		boolean result =RoleModel.updatePermission(id,JsonKit.toJson(p));
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
//	/**
//	 * 打开团队信息页面
//	 */
//	public void openTeams() {
//		render("team/teaminfo.html");
//	}
//	public void getTeamsList() {
//		String key = getPara("key");
//        int limit=getParaToInt("limit");
//        int page=getParaToInt("page");
//        Page<TeamModel> list = TeamModel.getList(page, limit, key);
//        setAttr("code", 0);
//        setAttr("msg", "你好！");
//        setAttr("count", list.getTotalRow());
//        setAttr("data", list.getList());
//        renderJson();
//	}
//	/**
//	 * 打开团队成员信息页面
//	 */
//	public void openTeamDetail() {
//		String id=getPara("id");
//		setAttr("id", id);
//		renderFreeMarker("team/teamDetail.html");
//	}
//	public void getTeamDetailList() {
//		String team_id=getPara("id");
//		String key = getPara("key");
//        int limit=getParaToInt("limit");
//        int page=getParaToInt("page");
//        Page<TeamersModel> list = TeamersModel.getList(page, limit, team_id,key);
//        setAttr("code", 0);
//        setAttr("msg", "你好！");
//        setAttr("count", list.getTotalRow());
//        setAttr("data", list.getList());
//        renderJson();
//	}
	
	
	/**
	 * 学生表
	 */
	public void openStudentInfo() {
		render("student/studentInfo.html");
	}
	public void openStudentAdd() {
		render("student/studentAdd.html");
	}
	public void openStuentEdit() {
		render("student/studentEdit.html");
	}
	public void getStudentModel() {
		String id=getPara("id");
		StudentModel m=StudentModel.getById(id);
		setAttr("m", m);
		renderJson();
	}
	public void saveStudent() {
		String no = getPara("no");
		String clas = getPara("clas");
		int type = getParaToInt("type");
		boolean result = StudentModel.save(no, clas, type);
		setAttr("result", result);
		renderJson();
	}
	public void updateStudent() {
		String id = getPara("id");
		String no = getPara("no");
		String clas = getPara("clas");
		int type = getParaToInt("type");
		boolean result = StudentModel.updateStudent(id, no, clas, type);
		setAttr("result", result);
		renderJson();
	}
	public void delStudent() {
		String id = getPara("id");
		boolean result = StudentModel.delStudent(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryStudent() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<StudentModel> c = StudentModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	public void getStudentlist() {
		List<StudentModel> list = StudentModel.getListAll();
		setAttr("dp", list);
		renderJson();
	}
	
	
	/**
	 * 教师表
	 */
	public void openTeacherInfo() {
		render("teacher/teacherInfo.html");
	}
	public void openTeacherAdd() {
		render("teacher/teacherAdd.html");
	}
	public void openTeacherEdit() {
		render("teacher/teacherEdit.html");
	}
	public void getTeacherModel() {
		String id=getPara("id");
		TeacherModel m=TeacherModel.getById(id);
		setAttr("m", m);
		renderJson();
	}
	public void saveTeacher() {
		String no = getPara("no");
		String remark = getPara("remark");
		String contact = getPara("contact");
		boolean result = TeacherModel.save(no, remark, contact);
		setAttr("result", result);
		renderJson();
	}
	public void updateTeacher() {
		String id = getPara("id");
		String no = getPara("no");
		String remark = getPara("remark");
		String contact = getPara("contact");
		boolean result = TeacherModel.updateTeacher(id, no, remark, contact);
		setAttr("result", result);
		renderJson();
	}
	public void delTeacher() {
		String id = getPara("id");
		boolean result = TeacherModel.delTeacher(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryTeacher() {
		String key = getPara("key");
        int limit=getParaToInt("limit");
        int page=getParaToInt("page");
        Page<TeacherModel> list = TeacherModel.getList(page, limit, key);
        setAttr("code", 0);
        setAttr("msg", "你好！");
        setAttr("count", list.getTotalRow());
        setAttr("data", list.getList());
        renderJson();
	}
	public void getTeacherlist() {
		List<TeacherModel> list = TeacherModel.getListAll();
		setAttr("dp", list);
		renderJson();
	}
	

	/**
	 * 家长表
	 */
	public void openParentInfo() {
		render("parent/parentInfo.html");
	}
	public void openParentAdd() {
		render("parent/parentAdd.html");
	}
	public void openParentEdit() {
		render("parent/parentEdit.html");
	}
	public void getParentModel() {
		String id=getPara("id");
		ParentsModel m=ParentsModel.getById(id);
		setAttr("m", m);
		renderJson();
	}
	public void saveParent() {
		String home_addr = getPara("home_addr");
		String remark = getPara("remark");
		String contact = getPara("contact");
		boolean result = ParentsModel.save(home_addr, remark, contact);
		setAttr("result", result);
		renderJson();
	}
	public void updateParent() {
		String id = getPara("id");
		String home_addr = getPara("home_addr");
		String remark = getPara("remark");
		String contact = getPara("contact");
		boolean result = ParentsModel.update(id, home_addr, remark, contact);
		setAttr("result", result);
		renderJson();
	}
	public void delParent() {
		String id = getPara("id");
		boolean result = ParentsModel.delParent(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryParent() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<ParentsModel> c = ParentsModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	public void getParentlist() {
		List<ParentsModel> list = ParentsModel.getListAll();
		setAttr("dp", list);
		renderJson();
	}
	
	/**
	 * 专业表
	 */
	public void openMajorInfo() {
		render("major/majorInfo.html");
	}
	public void openMajorAdd() {
		render("major/majorAdd.html");
	}
	public void openMajorEdit() {
		render("major/majorEdit.html");
	}
	public void getMajorModel() {
		String id=getPara("id");
		ParentsModel m=ParentsModel.getById(id);
		setAttr("m", m);
		renderJson();
	}
	public void saveMajor() {
		String nickname = getPara("nickname");
		String remark = getPara("remark");
		String department = getPara("department");
		boolean result = MajorModel.save(nickname, remark, department);
		setAttr("result", result);
		renderJson();
	}
	public void updateMajor() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String remark = getPara("remark");
		String department = getPara("department");
		boolean result = MajorModel.update(id, nickname, remark, department);
		setAttr("result", result);
		renderJson();
	}
	public void delMajor() {
		String id = getPara("id");
		boolean result = MajorModel.delMajorByID(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryMajor() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<MajorModel> c = MajorModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	public void getMajorlist() {
		List<MajorModel> list = MajorModel.getListAll();
		setAttr("dp", list);
		renderJson();
	}
	
	/**
	 * 学生关联表
	 */
	public void openStu_pareInfo() {
		render("stu_pare/stu_pareInfo.html");
	}
	public void openStu_pareAdd() {
		render("stu_pare/stu_pareAdd.html");
	}
	public void openStu_pareEdit() {
		render("stu_pare/stu_pareEdit.html");
	}
	public void getStu_preModel() {
		 String id=getPara("id");
		 Stu_pareModel result = Stu_pareModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveStu_pare() {
		String pare_id = getPara("pare_id");
		String stu_id = getPara("stu_id");
		String relation = getPara("relation");
		boolean result = Stu_pareModel.save(pare_id, stu_id, relation);
		setAttr("result", result);
		renderJson();
	}
	public void updateStu_pare() {
		String id = getPara("id");
		String pare_id = getPara("pare_id");
		String stu_id = getPara("stu_id");
		String relation = getPara("relation");
		boolean result = Stu_pareModel.update(id, pare_id, stu_id, relation);
		setAttr("result", result);
		renderJson();
	}
	public void delStu_pare() {
		String id = getPara("id");
		boolean result = Stu_pareModel.delStu_pareByID(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryStu_pare() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<Stu_pareModel> c = Stu_pareModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	/**
	 * 班级表
	 */
	public void openClassinfoInfo() {
		render("classinfo/classinfoInfo.html");
	}
	public void openClassinfoAdd() {
		render("classinfo/classinfoAdd.html");
	}
	public void openClassinfoEdit() {
		render("classinfo/classinfoEdit.html");
	}
	public void getClassinfoModel() {
		 String id=getPara("id");
		 ClassinfoModel result = ClassinfoModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveClassinfo() {
		String nickname = getPara("nickname");
		String grade = getPara("grade");
		String major_id = getPara("major_id");
		String headmaster = getPara("headmaster");
		String remark = getPara("remark");
		boolean result = ClassinfoModel.save(nickname, grade, major_id, headmaster, remark);
		setAttr("result", result);
		renderJson();
	}
	public void updateClassinfo() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String grade = getPara("grade");
		String major_id = getPara("major_id");
		String headmaster = getPara("headmaster");
		String remark = getPara("remark");
		boolean result = ClassinfoModel.update(id, nickname, grade, major_id, headmaster, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delClassinfo() {
		String id = getPara("id");
		boolean result = ClassinfoModel.delClassInfo(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryClassinfo() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<ClassinfoModel> c = ClassinfoModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	//=====================这里是对人脸库进行管理页面===============================//
	/**
	 * 打开人脸列表页面
	 */
	public void openFaceList() {
		render("userface/userfaceInfo.html");
	}
	/**
	 * 获取用户人脸列表
	 */
	public void getFaceList() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<UserFaceModel> c = UserFaceModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	/**
	 * 打开注册人脸页面
	 */
	public void openFaceAdd() {
		render("userface/userfaceAdd.html");
	}
	/**
	 * 注册人脸
	 */
	public void faceAdd() {
		
	}
	/**
	 * 更新人脸
	 */
	public void openFaceEdit() {
		String id=getPara("id");
		setAttr("id", id);
		renderFreeMarker("userface/userfaceEdit.html");
	}
	/**
	 * 更新人脸
	 */
	public void faceUpdate() {
		
	}
	/**
	 * 删除人脸
	 */
	public void faceDel() {
		
	}

	/**
	 * 打开组列表页面
	 */
	public void openGroupList() {
		render("group/groupinfo.html");
	}
	/**
	 * 查询组
	 */
	public void getGroupList() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<GroupModel> c = GroupModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	/**
	 * 打开添加组页面
	 */
	public void OpenGroupAdd() {
		render("group/groupAdd.html");
	}
	/**
	 * 添加组
	 */
	public void groupAdd() {
		String name = getPara("name");
		boolean result = GroupModel.save(name);
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 删除组
	 */
	public void groupDel() {
		String id = getPara("id");
		boolean result = GroupModel.del(id);
		setAttr("result", result);
		renderJson();
	}
	//======================这里是对人脸库进行管理页面end==============================//
	
}
