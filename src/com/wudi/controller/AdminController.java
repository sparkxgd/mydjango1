package com.wudi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.bean.TubiaoBean;
import com.wudi.interceptor.AdminInterceptor;
import com.wudi.model.Arrange_subjectModel;
import com.wudi.model.ClassinfoModel;
import com.wudi.model.ClassroomModel;
import com.wudi.model.DepartmentModel;
import com.wudi.model.MajorModel;
import com.wudi.model.ParentsModel;
import com.wudi.model.RoleModel;
import com.wudi.model.SchoolModel;
import com.wudi.model.Stu_pareModel;
import com.wudi.model.Stu_registerModel;
import com.wudi.model.Stu_studyModel;
import com.wudi.model.StudentModel;
import com.wudi.model.StudyModel;
import com.wudi.model.SubjectModel;
import com.wudi.model.TeacherModel;
import com.wudi.model.TeamModel;
import com.wudi.model.TeamersModel;
import com.wudi.model.UserModel;
import com.wudi.util.StringUtil;
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
//		String check = m.getRole_id();
//		RoleModel pp = RoleModel.getModelById(check);
//		String right = pp.getName();
		// 判断用户名和密码是否正确
		if (m != null/* && (right.equals("超级管理员")||right.equals("客服人员"))*/) {
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
	public void openStudentEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("student/studentEdit.html");
	}
	public void getStudentModel() {
		String id = getPara("id");
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
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("teacher/teacherEdit.html");
	}
	public void getTeacherModel() {
		String id = getPara("id");
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
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("parent/parentEdit.html");
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
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("major/majorEdit.html");
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
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("stu_pare/stu_pareEdit.html");
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
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("classinfo/classinfoEdit.html");
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
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	
	/**
	 * 分院表
	 */
	public void openDepartmentInfo() {
		render("department/departmentinfo.html");
	}
	public void openDepartmentAdd() {
		render("department/departmentAdd.html");
	}
	public void openDepartmentEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("department/departmentEdit.html");
	}
	public void getDepartmentModel() {
		 String id=getPara("id");
		 DepartmentModel result = DepartmentModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveDepartment() {
		String nickname = getPara("nickname");
		String school = getPara("school");
		String remark = getPara("remark");
		boolean result = DepartmentModel.save(nickname, school, remark);
		setAttr("result", result);
		renderJson();
	}
	public void updateDepartment() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String school = getPara("school");
		String remark = getPara("remark");
		boolean result = DepartmentModel.update(id, nickname, school, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delDepartment() {
		String id = getPara("id");
		boolean result = DepartmentModel.delDepartmentByID(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryDepartment() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<DepartmentModel> c = DepartmentModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	/**
	 * 学校表
	 */
	public void openSchoolInfo() {
		render("school/schoolinfo.html");
	}
	public void openSchoolAdd() {
		render("school/schoolAdd.html");
	}
	public void openSchoolEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("school/schoolEdit.html");
	}
	public void getSchoolModel() {
		 String id=getPara("id");
		 SchoolModel result = SchoolModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveSchool() {
		String nickname = getPara("nickname");
		String school = getPara("school");
		String remark = getPara("remark");
		boolean result = SchoolModel.save(nickname, school, remark);
		setAttr("result", result);
		renderJson();
	}
	public void updateSchool() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String school = getPara("school");
		String remark = getPara("remark");
		boolean result = SchoolModel.update(id, nickname, school, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delSchool() {
		String id = getPara("id");
		boolean result = SchoolModel.delSchoolById(id);
		setAttr("result", result);
		renderJson();
	}
	public void querySchool() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<SchoolModel> c = SchoolModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}

	public void getSchoolModels() {
		List<SchoolModel> list = SchoolModel.getListAll();
		setAttr("ml", list);
		renderJson();
	}
	
	
	/**
	 * 上课教室表
	 */
	public void openClassroomInfo() {
		render("classroom/classroominfo.html");
	}
	public void openClassroomAdd() {
		render("classroom/classroomAdd.html");
	}
	public void openClassroomEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("classroom/classroomEdit.html");
	}
	public void getClassroomModel() {
		 String id=getPara("id");
		 ClassroomModel result = ClassroomModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveClassroom() {
		String nickname = getPara("nickname");
		String addr = getPara("addr");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = ClassroomModel.save(nickname, addr, status, remark);
		setAttr("result", result);
		renderJson();
	}
	public void updateClassroom() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String addr = getPara("addr");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = ClassroomModel.update(id, nickname, addr, status, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delClassroom() {
		String id = getPara("id");
		boolean result = ClassroomModel.delClassInfo(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryClassroom() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<ClassroomModel> c = ClassroomModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	/**
	 * 科目表
	 */
	public void openSubjectInfo() {
		render("subject/subjectinfo.html");
	}
	public void openSubjectAdd() {
		render("subject/subjectAdd.html");
	}
	public void openSubjectEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("subject/subjectEdit.html");
	}
	public void getSubjectModel() {
		 String id=getPara("id");
		 SubjectModel result = SubjectModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveSubject() {
		String nickname = getPara("nickname");
		String remark = getPara("remark");
		boolean result = SubjectModel.save(nickname, remark);
		setAttr("result", result);
		renderJson();
	}
	public void updateSubject() {
		String id = getPara("id");
		String nickname = getPara("nickname");
		String remark = getPara("remark");
		boolean result = SubjectModel.update(id, nickname, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delSubject() {
		String id = getPara("id");
		boolean result = SubjectModel.delSuject(id);
		setAttr("result", result);
		renderJson();
	}
	public void querySubject() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<SubjectModel> c = SubjectModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	/**
	 * 教师课表
	 */
	public void openArr_SubjectInfo() {
		render("arr_sub/arr_subinfo.html");
	}
	public void openArr_SubjectAdd() {
		render("arr_sub/arr_subAdd.html");
	}
	public void openArr_SubjectEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("arr_sub/arr_subEdit.html");
	}
	public void getArr_SubjectModel() {
		 String id=getPara("id");
		 Arrange_subjectModel result = Arrange_subjectModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveArr_Subject() {
		String teacher = getPara("teacher");
		String subject = getPara("subject");
		String class_time = getPara("class_time");
		String classid = getPara("classid");
		String classroom = getPara("classroom");
		boolean result = Arrange_subjectModel.save(teacher, subject, class_time, classid, classroom);
		setAttr("result", result);
		renderJson();
	}

	public void updateArr_Subject() {
		String id = getPara("id");
		String teacher = getPara("teacher");
		String subject = getPara("subject");
		String class_time = getPara("class_time");
		String classid = getPara("classid");
		String classroom = getPara("classroom");
		boolean result = Arrange_subjectModel.updata(id, teacher, subject, class_time, classid, classroom);
		setAttr("result", result);
		renderJson();
	}
	public void delArr_Subject() {
		String id = getPara("id");
		boolean result = Arrange_subjectModel.delArrang_sub(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryArr_Subject() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<Arrange_subjectModel> c = Arrange_subjectModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	
	/**
	 * 上课记录表
	 */
	public void openStudyInfo() {
		render("study/studyinfo.html");
	}
	public void openStudyAdd() {
		render("study/studyAdd.html");
	}
	public void openStudyEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("study/studyEdit.html");
	}
	public void getStudyModel() {
		 String id=getPara("id");
		 StudyModel result = StudyModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveStudy() {
		String start_time = getPara("start_time");
		String end_time = getPara("end_time");
		String teacher = getPara("teacher");
		String subject = getPara("subject");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = StudyModel.save(start_time, end_time, teacher, subject, status, remark);
		setAttr("result", result);
		renderJson();
	}

	public void updateStudy() {
		String id = getPara("id");
		String start_time = getPara("start_time");
		String end_time = getPara("end_time");
		String teacher = getPara("teacher");
		String subject = getPara("subject");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = StudyModel.update(id, start_time, end_time, teacher, subject, status, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delStudy() {
		String id = getPara("id");
		boolean result = StudyModel.delStudy(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryStudy() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<StudyModel> c = StudyModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	/**
	 * 学生上课记录表
	 */
	public void openStu_studyInfo() {
		render("stu_study/stu_studyinfo.html");
	}
	public void openStu_studyAdd() {
		render("stu_study/stu_studyAdd.html");
	}
	public void openStu_studyEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("stu_study/stu_studyEdit.html");
	}
	public void getStu_studyModel() {
		 String id=getPara("id");
		 Stu_studyModel result = Stu_studyModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveStu_study() {
		String studey_id = getPara("studey_id");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = Stu_studyModel.save(studey_id, status, remark);
		setAttr("result", result);
		renderJson();
	}

	public void updateStu_study() {
		String id = getPara("id");
		String studey_id = getPara("studey_id");
		int status = getParaToInt("status");
		String remark = getPara("remark");
		boolean result = Stu_studyModel.update(id, studey_id, status, remark);
		setAttr("result", result);
		renderJson();
	}
	public void delStu_study() {
		String id = getPara("id");
		boolean result = Stu_studyModel.delStu_study(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryStu_study() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<Stu_studyModel> c = Stu_studyModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
	/**
	 * 学生签到表
	 */
	public void openStu_registerInfo() {
		render("stu_reg/stu_reginfo.html");
	}
	public void openStu_registerAdd() {
		render("stu_reg/stu_regAdd.html");
	}
	public void openStu_registerEdit() {
		String id = getPara("id");
		setAttr("id", id);
		renderFreeMarker("stu_reg/stu_regEdit.html");
	}
	public void getStu_registerModel() {
		 String id=getPara("id");
		 Stu_registerModel result = Stu_registerModel.getById(id);
		 setAttr("result", result);
		 renderJson();
	}
	public void saveStu_register() {
		String reg_time = getPara("reg_time");
		String addr = getPara("addr");
		int type = getParaToInt("type");
		String remark = getPara("remark");
		String studey_id = getPara("studey_id");
		boolean result = Stu_registerModel.save(reg_time, addr, type, remark, studey_id);
		setAttr("result", result);
		renderJson();
	}

	public void updateStu_register() {
		String id = getPara("id");
		String reg_time = getPara("reg_time");
		String addr = getPara("addr");
		int type = getParaToInt("type");
		String remark = getPara("remark");
		String studey_id = getPara("studey_id");
		boolean result = Stu_registerModel.update(id, reg_time, addr, type, remark, studey_id);
		setAttr("result", result);
		renderJson();
	}
	public void delStu_register() {
		String id = getPara("id");
		boolean result = Stu_registerModel.delStu_register(id);
		setAttr("result", result);
		renderJson();
	}
	public void queryStu_register() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<Stu_registerModel> c = Stu_registerModel.getList(page, limit, key);
		setAttr("infos", c);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", c.getTotalRow());
		setAttr("data", c.getList());
		renderJson();
	}
}
