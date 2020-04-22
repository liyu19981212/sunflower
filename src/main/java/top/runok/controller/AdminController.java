package top.runok.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.runok.entity.*;
import top.runok.service.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    NoticeService noticeService;

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }
    @PostMapping({"/login"})
    public String login(HttpSession session, @RequestParam("username") String username,
                        @RequestParam("password") String password) {

//         List<Admin> ad = adminService.adminselect();
//        System.out.println(ad);
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        System.out.println("从页面传进来的数据"+admin);
        Admin dmin = adminService.adminlogin(admin);
        System.out.println("从数据库中查询出来的"+dmin);
        if (dmin == null) {
            session.setAttribute("errorMsgAdmin","登录失败");
            return "admin/login";
        } else {
            session.setAttribute("admin_name",admin.getUsername());
            return "redirect:/admin/index";
        }
    }
    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }
    @RequestMapping({"/logout"})
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("errorMsgAdmin");
        request.getSession().removeAttribute("admin_name");
        return "admin/login";
    }
    @RequestMapping({"student"})
    public String student(Model model){
        List<Student> students = studentService.select_all();
        model.addAttribute("student",students);
        return "admin/student";
    }
    @RequestMapping({"teacher"})
    public String teacher(Model model){
        List<Teacher> teachers = teacherService.select_all();
        model.addAttribute("teacher",teachers);
        return "admin/teacher";
    }
    @RequestMapping({"course"})
    public String course(Model model){
        List<Course> courses = courseService.select_all();
        model.addAttribute("course",courses);
        return "admin/course";
    }
    @Value("${uploadpath}")
    private String uploadpath;
    @Value("${vpath}")
    public String vpath;
    //课程管理
    @GetMapping({"/addcourse"})
    public String addcourse() {
        return "admin/addcourse";
    }
    @RequestMapping({"/addcourseaction"})
    public String addcourseaction(@RequestParam("c_name") String c_name, @RequestParam("c_info") String c_info, MultipartFile uploadFile, HttpServletRequest req) {
        System.out.println(c_name+c_info);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String realPath = uploadpath;
        System.out.println(realPath);
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{
            uploadFile.transferTo(new File(folder,newName));
            String filePath = req.getScheme() + "://" + req.getServerName() + ":" +req.getServerPort() + vpath + format + newName;
            Course course = new Course();


            course.setC_name(c_name);
            course.setC_info(c_info);
            course.setC_img(filePath);
            System.out.println("要添加的课程为"+course);
            int i = courseService.insert(course);
            System.out.println("成功插入"+i+"条数据");
            return "redirect:/admin/course";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";

    }

    @RequestMapping({"/deletecourse"})
    public String deletecourse(@RequestParam("c_id") String c_id){
        System.out.println("传过来的"+c_id);
        Integer id =  Integer.parseInt(c_id);
        int i = courseService.delete(id);
        System.out.println("删除了"+i+"条数据");
        return "redirect:/admin/course";
    }
    @GetMapping({"/updatecourse"})
    public ModelAndView updatecourse(@RequestParam("c_id") String c_id){
        System.out.println("传过来的"+c_id);
        Integer id =  Integer.parseInt(c_id);
        Course course = courseService.selectByC_id(id);
        System.out.println("需要更改的数据为："+course);
        ModelAndView mv = new ModelAndView();
        mv.addObject("course",course);
        mv.setViewName("admin/updatecourse");
        //return "teacher/updatecourse";
        return mv;
    }
    @RequestMapping({"/updatecourseaction"})
    public String updatecourseaction(@RequestParam("c_id") String c_id,@RequestParam("c_name") String c_name, @RequestParam("c_info") String c_info, MultipartFile uploadFile, HttpServletRequest req) {
        System.out.println(uploadFile);
        if (null != uploadFile) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String realPath = uploadpath;
            System.out.println(realPath);
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {

                uploadFile.transferTo(new File(folder, newName));
                String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + vpath + format + newName;
                Course course = new Course();
                Integer id = Integer.parseInt(c_id);
                course.setC_id(id);
                course.setC_name(c_name);
                course.setC_info(c_info);
                course.setC_img(filePath);
                System.out.println("要修改的课程数据" + course);
                int i = courseService.update(course);
                System.out.println("成功修改" + i + "条数据");
                return "redirect:/admin/course";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";
        } else {
            Course course = new Course();
            Integer id = Integer.parseInt(c_id);
            course.setC_id(id);
            course.setC_name(c_name);
            course.setC_info(c_info);

            System.out.println("要修改的课程数据" + course);
            int i = courseService.update(course);
            System.out.println("成功修改" + i + "条数据");
            return "redirect:/admin/course";
        }
    }
    //公告.................
    //响应请求，返回到公告管理
    @RequestMapping({"/notice"})
    public String notice(Model model){
        List<Notice> notices = noticeService.select_All();
        model.addAttribute("notice",notices);
        return "admin/notice";
    }

    @RequestMapping({"/addnoticeaction"})
    public String addnoticeaction(@RequestParam("content")String content){
        Notice notice = new Notice();
        notice.setContent(content);
        int i = noticeService.insert(notice);
        return "redirect:/admin/notice";
    }
    @RequestMapping({"/deletenotice"})
    public String deletenotice(@RequestParam("n_id") Integer n_id){
        int i = noticeService.delete(n_id);
        return "redirect:/admin/notice";
    }
    @RequestMapping({"/table"})
    public String table(){ return "admin/table";}
    @RequestMapping({"/noticeManagement"})
    public String not(){ return "admin/noticeManagement";}
    @ResponseBody
    @RequestMapping(value = {"/noticelist"})
    public List<Notice> noticeList(){
        List<Notice> notices = noticeService.select_All();
        return notices;
    }
    // 创建线程安全的Map
    static Map<Integer, Notice> notices = Collections.synchronizedMap(new HashMap<Integer, Notice>());
    /**
     * 添加公告
     */
    @ResponseBody
    @ApiOperation(value="添加公告", notes="创建新公告")
    @ApiImplicitParam(name = "notice", value = "公告详细实体notice", required = true, dataType = "Notice")
    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> addNotice (@RequestBody Notice notice){
        JsonResult result = new JsonResult();
        try {
            notices.put(notice.getN_id(), notice);
            result.setResult(notice.getN_id());
            int i = noticeService.insert(notice);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    /**
     * 根据ID修改用户信息
     */
    @ApiOperation(value="更新公告", notes="根据Id更新公告")
    @ApiImplicitParams({
           // @ApiImplicitParam(name = "n_id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "notice", value = "用户对象Notice", required = true, dataType = "Notice")
    })
    @RequestMapping(value = "/updateNotice", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> updateById ( @RequestBody Notice notice){
        JsonResult result = new JsonResult();
        try {
            System.out.println(notice);

            int i = noticeService.update(notice);
            result.setResult("success");

            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**

    /**
     * 根据id删除用户
     */
    @ResponseBody
    @ApiOperation(value="删除用户", notes="根据id删除指定用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> deleteById (@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        try {
            int i = noticeService.delete(id);
            result.setResult("success");
            result.setStatus("success");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    //教师管理开始了
    @GetMapping({"/teacherManagement"})
    public String teacherManagement(){
        return "admin/teacherManagement";
    }
    // 创建线程安全的Map
    static Map<Integer, Teacher> teachers = Collections.synchronizedMap(new HashMap<Integer, Teacher>());
    //生成学生列表
    /**
     * 查询用户列表
     */
    @ResponseBody
    @ApiOperation(value="教师列表", notes="查询教师列表")
    @RequestMapping(value = "/getTeacherList", method = RequestMethod.GET)
    public List<Teacher> getTeacherList (){
//        JsonResult result = new JsonResult();
//        try {
//            List<Student> studentList = studentService.select_all();
//            result.setResult(studentList);
//            result.setStatus("200");
//        } catch (Exception e) {
//            result.setResult("服务异常");
//            result.setStatus("500");
//            e.printStackTrace();
//        }
        List<Teacher> teacherList = teacherService.select_all();
        return teacherList;
    }
    /**
     * 根据id删除用户
     */
    @ResponseBody
    @ApiOperation(value="删除用户", notes="根据id删除指定用户")
    @ApiImplicitParam(name = "t_id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteTeacher/{t_id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> deleteTeacher (@PathVariable(value = "t_id") Integer t_id){
        JsonResult result = new JsonResult();
        try {
            int i = teacherService.delete(t_id);
            result.setResult("success");
            result.setStatus("success");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加公告
     */
    @ResponseBody
    @ApiOperation(value="添加教师", notes="创建新教师")
    @ApiImplicitParam(name = "teacher", value = "教师详细实体teacher", required = true, dataType = "Teacher")
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> addTeacher (@RequestBody Teacher teacher){
        JsonResult result = new JsonResult();
        try {
            System.out.println(teacher);
            teachers.put(teacher.getT_id(), teacher);
            result.setResult(teacher.getT_id());
            int i = teacherService.insert(teacher);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    /**
     * 根据ID修改用户信息
     */
    @ApiOperation(value="更新教师", notes="根据Id更新教师")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "n_id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "teacher", value = "用户对象Teacher", required = true, dataType = "Teacher")
    })
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> updateTeacher ( @RequestBody Teacher teacher){
        JsonResult result = new JsonResult();
        try {
            System.out.println(teacher);

            int i = teacherService.update(teacher);
            result.setResult("success");

            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    //学生管理开始了
    @GetMapping({"/studentManagement"})
    public String studentManagemenr(){
        return "admin/studentManagement";
    }
    // 创建线程安全的Map
    static Map<Integer, Student> students = Collections.synchronizedMap(new HashMap<Integer, Student>());
    //生成学生列表
    /**
     * 查询用户列表
     */
    @ResponseBody
    @ApiOperation(value="学生列表", notes="查询学生列表")
    @RequestMapping(value = "/getStudentList", method = RequestMethod.GET)
    public List<Student> getStudentList (){
//        JsonResult result = new JsonResult();
//        try {
//            List<Student> studentList = studentService.select_all();
//            result.setResult(studentList);
//            result.setStatus("200");
//        } catch (Exception e) {
//            result.setResult("服务异常");
//            result.setStatus("500");
//            e.printStackTrace();
//        }
        List<Student> studentList = studentService.select_all();
        return studentList;
    }
    /**
     * 根据id删除用户
     */
    @ResponseBody
    @ApiOperation(value="删除用户", notes="根据id删除指定用户")
    @ApiImplicitParam(name = "s_id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteStudent/{s_id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> deleteStudent (@PathVariable(value = "s_id") Integer s_id){
        JsonResult result = new JsonResult();
        try {
            int i = studentService.delete(s_id);
            result.setResult("success");
            result.setStatus("success");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加公告
     */
    @ResponseBody
    @ApiOperation(value="添加学生", notes="创建新学生")
    @ApiImplicitParam(name = "student", value = "学生详细实体student", required = true, dataType = "Student")
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> addStudent (@RequestBody Student student){
        JsonResult result = new JsonResult();
        try {
            System.out.println(student);
            students.put(student.getS_id(), student);
            result.setResult(student.getS_id());
            int i = studentService.insert(student);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    /**
     * 根据ID修改用户信息
     */
    @ApiOperation(value="更新学生", notes="根据Id更新学生")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "n_id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "student", value = "用户对象Student", required = true, dataType = "Student")
    })
    @RequestMapping(value = "/updateStudent", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> updateStudent ( @RequestBody Student student){
        JsonResult result = new JsonResult();
        try {
            System.out.println(student);

            int i = studentService.update(student);
            result.setResult("success");

            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
}
