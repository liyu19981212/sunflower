package top.runok.controller;

import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.ApiImplicitParam;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-02-21 21:51
 **/
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseFileService courseFileService;
    @Autowired
    CourseVideoService courseVideoService;
    @Autowired
    TestService testService;
    @Autowired
    TestQuestionService testQuestionService;
    @Autowired
    DiscussService discussService;
    @Autowired
    CommentService commentService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    StudentCourseSelectService studentCourseSelectService;


    @Value("${uploadpath}")
    private String uploadpath;
    @Value("${vpath}")
    public String vpath;
    @GetMapping({"/login"})
    public String login() {
        return "teacher/login";
    }



    //显示学生信息
    @RequestMapping({"/info"})
    public String info(Model model,HttpServletRequest request){
        Integer t_id = (int) request.getSession().getAttribute("t_id");
        Teacher teacher = teacherService.selectById(t_id);
        model.addAttribute("teacher",teacher);
        return "teacher/info";
    }
    //修改学生信息
    @RequestMapping({"/updateinfo"})
    public String updateinfo(Model model,HttpServletRequest request,@RequestParam("t_num")String t_num,@RequestParam("t_name")String t_name,@RequestParam("telphone")String telphone){
        Integer t_id = (int) request.getSession().getAttribute("t_id");
        Teacher teacher = teacherService.selectById(t_id);

        teacher.setTelphone(telphone);
        teacher.setT_num(t_num);
        teacher.setT_name(t_name);

        int i = teacherService.update(teacher);
        return "redirect:/teacher/info";
    }



    @GetMapping({"/logout"})
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("t_username");
        request.getSession().removeAttribute("t_id");
        request.getSession().removeAttribute("errorMsg");
        request.getSession().removeAttribute("errorT");

        return "teacher/login";
    }


    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public String teacherlogin(@RequestParam("username") String username,
                               @RequestParam("password") String password, HttpSession session) {
        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setPassword(password);


        System.out.println("从页面传进来的数据" + teacher);

        Teacher teacher1 = teacherService.login(teacher);

        System.out.println("从数据库中查询出来的" + teacher1);
        if (teacher1 == null) {
            session.setAttribute("errorMsg", "登陆失败");
            return "teacher/login";
        } else {
            session.setAttribute("t_username", teacher1.getUsername());
            session.setAttribute("t_id", teacher1.getT_id());

            return "teacher/index";
        }
    }
    //转到注册页面
    @GetMapping({"/register"})
    public String register(){
        return "teacher/register";
    }
    //注册响应
    @RequestMapping(value = {"/register"},method = RequestMethod.POST)
    public String teacherregister(HttpSession session,@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("telphone")String telphone){
        Teacher t = teacherService.check(username);

        if(t!=null){
            session.setAttribute("errorT","用户已存在");
            return "teacher/register";
        }else{
            Teacher teacher = new Teacher();
            teacher.setUsername(username);

            teacher.setPassword(password);
            teacher.setTelphone(telphone);
            int insert = teacherService.insert(teacher);
            return "teacher/login";
        }
    }
//课程管理
    @GetMapping({"/addcourse"})
    public String addcourse() {
    return "teacher/addcourse";
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
            Integer t_id = (int) req.getSession().getAttribute("t_id");
            course.setT_id(t_id);
            course.setC_name(c_name);
            course.setC_info(c_info);
            course.setC_img(filePath);
            System.out.println("要添加的课程为"+course);
            int i = courseService.insert(course);
            System.out.println("成功插入"+i+"条数据");
            return "redirect:/teacher/coursemanager.action";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";

    }
    @RequestMapping({"/coursemanager.action"})
    public ModelAndView coursemanager(HttpServletRequest request){
        //Course course = new Course();
        Integer t_id = (int) request.getSession().getAttribute("t_id");
        List<Course> courseList = new ArrayList<>();
        courseList = courseService.selectByT_id(t_id);
        System.out.println(courseList);
        ModelAndView mv = new ModelAndView();
        mv.addObject("courses",courseList);
        mv.setViewName("teacher/coursemanager");
        return mv;
    }
    @RequestMapping({"/deletecourse"})
    public String deletecourse(@RequestParam("c_id") String c_id){
        System.out.println("传过来的"+c_id);
        Integer id =  Integer.parseInt(c_id);
        int i = courseService.delete(id);
        System.out.println("删除了"+i+"条数据");
        return "redirect:/teacher/coursemanager.action";
    }
    @GetMapping({"/updatecourse"})
    public ModelAndView updatecourse(@RequestParam("c_id") String c_id){
        System.out.println("传过来的"+c_id);
        Integer id =  Integer.parseInt(c_id);
        Course course = courseService.selectByC_id(id);
        System.out.println("需要更改的数据为："+course);
        ModelAndView mv = new ModelAndView();
        mv.addObject("course",course);
        mv.setViewName("teacher/updatecourse");
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
                return "redirect:/teacher/coursemanager.action";
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
            return "redirect:/teacher/coursemanager.action";
        }
    }


   //课程文件
    @RequestMapping({"/coursefileaction"})
    public ModelAndView coursefile(@RequestParam("c_id") String c_id){
        //Course course = new Course();
        System.out.println("运行到这里了，传过来的c_id:"+c_id);
        Integer id = Integer.parseInt(c_id);
        List<CourseFile> courseFileList = new ArrayList<>();
        courseFileList = courseFileService.selectByC_id(id);
        System.out.println(courseFileList);
        if(courseFileList.isEmpty()){
            System.out.println("集合为空");

            ModelAndView mv = new ModelAndView();
            mv.addObject("c_id",id);
            mv.setViewName("teacher/coursefile");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("coursefiles",courseFileList);
        mv.setViewName("teacher/coursefile");
        return mv;
    }
    @RequestMapping({"/addcoursefile"})
    public ModelAndView addcoursefile(@RequestParam("c_id") String c_id ){
        Integer id = Integer.parseInt(c_id);
        CourseFile courseFile = new CourseFile();
        courseFile.setC_id(id);
        System.out.println(courseFile);
        ModelAndView mv = new ModelAndView();
        mv.addObject("coursefile",courseFile);
        mv.setViewName("teacher/addcoursefile");
        return mv;
    }
    @RequestMapping({"/addcoursefileaction"})
    public String addcoursefileaction( @RequestParam("f_name") String f_name,@RequestParam("c_id") String c_id, MultipartFile uploadFile, HttpServletRequest req) {
        System.out.println(f_name+c_id);
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
            CourseFile courseFile = new CourseFile();
            Integer id = Integer.parseInt(c_id);
            courseFile.setF_name(f_name);
            courseFile.setF_path(filePath);
            courseFile.setC_id(id);
            System.out.println("要添加的课程文件为"+courseFile);
            int i = courseFileService.insert(courseFile);
            System.out.println("成功插入"+i+"条数据");
            return "redirect:/teacher/coursefileaction?c_id="+c_id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";

    }
    @GetMapping({"/deletecoursefile"})
    public String deletecoursefile(@RequestParam("f_id") String f_id,@RequestParam("c_id") String c_id){
        System.out.println("传过来的"+f_id);
        Integer id =  Integer.parseInt(f_id);
        int i = courseFileService.delete(id);
        System.out.println("删除了"+i+"条数据");
        return "redirect:/teacher/coursefileaction?c_id="+c_id;
    }
    @GetMapping({"/updatecoursefile"})
    public ModelAndView updatecoursefile(@RequestParam("f_id") String f_id){
        System.out.println("传过来的"+f_id);
        Integer id =  Integer.parseInt(f_id);
        CourseFile courseFile = courseFileService.selectByF_id(id);
        System.out.println("需要更改的数据为："+courseFile);
        ModelAndView mv = new ModelAndView();
        mv.addObject("coursefile",courseFile);
        mv.setViewName("teacher/updatecoursefile");
        //return "teacher/updatecourse";
        return mv;
    }
    @RequestMapping({"/updatecoursefileaction"})
    public String updatecoursefileaction(@RequestParam("c_id") String c_id,@RequestParam("f_id") String f_id,@RequestParam("f_name") String f_name,  MultipartFile uploadFile, HttpServletRequest req) {
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
                CourseFile courseFile = new CourseFile();
                Integer id = Integer.parseInt(f_id);
                courseFile.setF_id(id);
                courseFile.setF_name(f_name);
                courseFile.setF_path(filePath);

                System.out.println("要修改的课程数据" + courseFile);
                int i = courseFileService.update(courseFile);
                System.out.println("成功修改" + i + "条数据");
                return "redirect:/teacher/coursefileaction?c_id="+c_id;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";
        } else {
            CourseFile courseFile = new CourseFile();
            Integer id = Integer.parseInt(f_id);
            courseFile.setF_id(id);
            courseFile.setF_name(f_name);
            ;

            System.out.println("要修改的课程数据" + courseFile);
            int i = courseFileService.update(courseFile);
            System.out.println("成功修改" + i + "条数据");
            return "redirect:/teacher/coursefileaction?c_id="+c_id;
        }
    }


    //课程视频
    @GetMapping({"/coursevideoaction"})
    public ModelAndView coursevideo(@RequestParam("c_id") String c_id){
        //Course course = new Course();
        Integer id = Integer.parseInt(c_id);
        List<CourseVideo> courseVideoList = new ArrayList<>();
        courseVideoList = courseVideoService.selectByC_id(id);
        System.out.println(courseVideoList);
        if(courseVideoList.isEmpty()){
            System.out.println("集合为空");
            System.out.println("集合为空");

            ModelAndView mv = new ModelAndView();
            mv.addObject("c_id",id);
            mv.setViewName("teacher/coursevideo");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("coursevideos",courseVideoList);
        mv.setViewName("teacher/coursevideo");
        return mv;
    }
    @GetMapping({"/addcoursevideo"})
    public ModelAndView addcoursevideo(@RequestParam("c_id") String c_id) {
        Integer id = Integer.parseInt(c_id);
        CourseVideo courseVideo = new CourseVideo();
        courseVideo.setC_id(id);
        System.out.println(courseVideo);
        ModelAndView mv = new ModelAndView();
        mv.addObject("courseVideo",courseVideo);
        mv.setViewName("teacher/addcoursevideo");
        return mv;

    }
    @RequestMapping({"/addcoursevideoaction"})
    public String addcoursevideoaction(@RequestParam("v_name") String v_name, @RequestParam("c_id") String c_id, MultipartFile uploadFile, HttpServletRequest req) {
        System.out.println(v_name+c_id);
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
            CourseVideo courseVideo = new CourseVideo();
            Integer id = Integer.parseInt(c_id);
            courseVideo.setV_name(v_name);
            courseVideo.setV_path(filePath);
            courseVideo.setC_id(id);
            System.out.println("要添加的课程为"+courseVideo);
            int i = courseVideoService.insert(courseVideo);
            System.out.println("成功插入"+i+"条数据");
            return "redirect:/teacher/coursevideoaction?c_id="+c_id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";

    }

    @GetMapping({"/deletecoursevideo"})
    public String deletecoursevideo(@RequestParam("c_id") String c_id,@RequestParam("v_id") String v_id){
        System.out.println("传过来的"+v_id);
        Integer id =  Integer.parseInt(v_id);
        int i = courseVideoService.delete(id);
        System.out.println("删除了"+i+"条数据");
        return "redirect:/teacher/coursevideoaction?c_id="+c_id;
    }
    @GetMapping({"/updatecoursevideo"})
    public ModelAndView updatecoursevideo(@RequestParam("v_id") String v_id){
        System.out.println("传过来的"+v_id);
        Integer id =  Integer.parseInt(v_id);
        CourseVideo courseVideo = courseVideoService.selectByV_id(id);
        System.out.println("需要更改的数据为："+courseVideo);
        ModelAndView mv = new ModelAndView();
        mv.addObject("courseVideo",courseVideo);
        mv.setViewName("teacher/updatecoursevideo");
        //return "teacher/updatecourse";
        return mv;
    }
    @RequestMapping({"/updatecoursevideoaction"})
    public String updatecoursevideoaction(@RequestParam("c_id") String c_id,@RequestParam("v_id") String v_id,@RequestParam("v_name") String v_name,  MultipartFile uploadFile, HttpServletRequest req) {
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
                CourseVideo courseVideo = new CourseVideo();
                Integer id = Integer.parseInt(v_id);
                courseVideo.setV_id(id);
                courseVideo.setV_name(v_name);
                courseVideo.setV_path(filePath);

                System.out.println("要修改的课程数据" + courseVideo);
                int i = courseVideoService.update(courseVideo);
                System.out.println("成功修改" + i + "条数据");
                return "redirect:/teacher/coursevideoaction?c_id="+c_id;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";
        } else {
            CourseVideo courseVideo = new CourseVideo();
            Integer id = Integer.parseInt(v_id);
            courseVideo.setV_id(id);
            courseVideo.setV_name(v_name);


            System.out.println("要修改的课程数据" + courseVideo);
            int i = courseVideoService.update(courseVideo);
            System.out.println("成功修改" + i + "条数据");
            return "redirect:/teacher/coursevideoaction?c_id="+c_id;
        }
    }

    //考试列表
    @RequestMapping({"/testaction"})
    public ModelAndView testaction(@RequestParam("c_id") String c_id){
        Integer id =  Integer.parseInt(c_id);
        List<Test> t = testService.selectByC_id(id);
        System.out.println("查找出的数据为："+t);
        if(t.isEmpty()){
            System.out.println("集合为空");
            System.out.println("集合为空");

            ModelAndView mv = new ModelAndView();
            mv.addObject("c_id",id);
            mv.setViewName("teacher/coursetest");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("test",t);
        mv.setViewName("teacher/coursetest");
        return mv;
    }
    @RequestMapping({"/addtest"})
    public ModelAndView addtest(@RequestParam("c_id") String c_id) {
        Integer id = Integer.parseInt(c_id);
        Test t = new Test();
        t.setCourse_id(id);
        System.out.println(t);
        ModelAndView mv = new ModelAndView();
        mv.addObject("test",t);
        mv.setViewName("teacher/addtest");
        return mv;

    }
    @RequestMapping({"/addtestaction"})
    public String addtestaction(@RequestParam("c_id") String c_id, @RequestParam("test_name") String test_name){
        Integer id = Integer.parseInt(c_id);
        Test t = new Test();
        t.setCourse_id(id);
        t.setTest_name(test_name);
        int i = testService.insert(t);
        System.out.println("成功插入数据"+i+"条");
        return "redirect:/teacher/testaction?c_id="+id;
    }
    @RequestMapping({"/deletetest"})
    public String deletetest(@RequestParam("test_id") String test_id,@RequestParam("c_id") String c_id){
        Integer id = Integer.parseInt(test_id);
        int i = testService.delete(id);
        System.out.println("删除了"+i+"条数据");
        return "redirect:/teacher/testaction?c_id="+c_id;

    }
    @RequestMapping({"/updatetest"})
    public ModelAndView updatetest(@RequestParam("test_id") String test_id,@RequestParam("test_name") String test_name,@RequestParam("c_id") String c_id){
        Test t = new Test();
        Integer id = Integer.parseInt(test_id);
        Integer cid = Integer.parseInt(c_id);
        t.setTest_name(test_name);
        t.setTest_id(id);
        t.setCourse_id(cid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("test",t);
        mv.setViewName("teacher/updatetest");
        return mv;
    }
    @RequestMapping({"/updatetestaction"})
    public String updatetestaction(@RequestParam("test_id") String test_id,@RequestParam("test_name") String test_name,@RequestParam("c_id") String c_id) {
        Integer id = Integer.parseInt(test_id);
        Test test = new Test();
        test.setTest_id(id);
        test.setTest_name(test_name);
        int i = testService.update(test);
        return "redirect:/teacher/testaction?c_id="+c_id;
    }

    //考试问题
    @RequestMapping({"/testquestionaction"})
    public ModelAndView testquestionaction(@RequestParam("test_id")String test_id){
        Integer testid =  Integer.parseInt(test_id);
        List<TestQuestion> t = testQuestionService.selectByTest_id(testid);
        System.out.println("查找出的数据为："+t);
        if(t.isEmpty()){
            System.out.println("集合为空");
            System.out.println("集合为空");

            ModelAndView mv = new ModelAndView();
            mv.addObject("test_id",test_id);
            mv.setViewName("teacher/testquestion");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("testquestion",t);
        mv.setViewName("teacher/testquestion");
        return mv;
    }
    @RequestMapping({"/addtestquestion"})
    public ModelAndView addtestquestion(@RequestParam("test_id")String test_id){
        Integer testid = Integer.parseInt(test_id);
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setTest_id(testid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testquestion",testQuestion);
        modelAndView.setViewName("teacher/addtestquestion");
        return modelAndView;
    }
    @RequestMapping({"/addtestquestionaction"})
    public String addtestquestionaction(@RequestParam("question")String question,@RequestParam("choice_a")String choice_a,@RequestParam("choice_b")String choice_b,@RequestParam("choice_c")String choice_c,@RequestParam("choice_d")String choice_d,@RequestParam("answer")String answer,@RequestParam("test_id")String test_id){
        Integer testid = Integer.parseInt(test_id);
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setTest_id(testid);
        testQuestion.setQuestion(question);
        testQuestion.setChoice_a(choice_a);
        testQuestion.setChoice_b(choice_b);
        testQuestion.setChoice_c(choice_c);
        testQuestion.setChoice_d(choice_d);
        testQuestion.setAnswer(answer);
        int i = testQuestionService.insert(testQuestion);
        System.out.println("成功加入"+i+"条数据");
        return "redirect:/teacher/testquestionaction?test_id="+test_id;
    }
    @RequestMapping({"/deletetestquestion"})
    public String deletetestquestion(@RequestParam("id")String id,@RequestParam("test_id")String test_id){
        Integer id_q = Integer.parseInt(id);
        int i = testQuestionService.delete(id_q);
        System.out.println("成功删除"+i+"条数据");
        return "redirect:/teacher/testquestionaction?test_id="+test_id;
    }
    @RequestMapping({"/updatetestquestion"})
    public ModelAndView updatetestquestion(@RequestParam("test_id")String test_id,@RequestParam("id")String id){
        Integer id_t = Integer.parseInt(id);
        TestQuestion testQuestion = new TestQuestion();
        testQuestion = testQuestionService.selectById(id_t);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testquestion",testQuestion);
        modelAndView.setViewName("teacher/updatetestquestion");
        return modelAndView;
    }
    @RequestMapping({"/updatetestquestionaction"})
    public String updatetestquestionaction(@RequestParam("test_id")String test_id,@RequestParam("question")String question,@RequestParam("choice_a")String choice_a,@RequestParam("choice_b")String choice_b,@RequestParam("choice_c")String choice_c,@RequestParam("choice_d")String choice_d,@RequestParam("answer")String answer,@RequestParam("id")String id){
        Integer id_t = Integer.parseInt(id);
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setId(id_t);
        testQuestion.setQuestion(question);
        testQuestion.setChoice_a(choice_a);
        testQuestion.setChoice_b(choice_b);
        testQuestion.setChoice_c(choice_c);
        testQuestion.setChoice_d(choice_d);
        testQuestion.setAnswer(answer);
        int i = testQuestionService.update(testQuestion);
        System.out.println("成功修改"+i+"条数据");
        return "redirect:/teacher/testquestionaction?test_id="+test_id;
    }
    //发布讨论处理情况
    @RequestMapping({"/discuss"})
    public ModelAndView dicuss(@RequestParam("c_id") String c_id){
        Integer cid = Integer.parseInt(c_id);
        List<Discuss> discusses = discussService.selectByC_id(cid);
        System.out.println(discusses);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("discuss",discusses);
        modelAndView.addObject("c_id",c_id);
        modelAndView.setViewName("teacher/discuss");
        return modelAndView;
    }
//    @RequestMapping({"/adddiscuss"})
//    public String adddiscuss(HttpServletRequest req, @RequestParam("c_id") String c_id, @RequestParam("title")String title, @RequestParam("content")String content){
//        System.out.println(c_id+title+content);
//        Discuss discuss = new Discuss();
//        Integer cid = Integer.parseInt(c_id);
//        discuss.setC_id(cid);
//        discuss.setTitle(title);
//        discuss.setContent(content);
//        Integer s_id = (int) req.getSession().getAttribute("s_id");
//        discuss.setS_id(s_id);
//        int i =discussService.insert(discuss);
//        return "redirect:/student/discuss?c_id="+c_id;
//    }
   @RequestMapping({"/discussshow"})
    public ModelAndView adddiscussshow(@RequestParam("d_id") String d_id){

        Discuss discuss = new Discuss();
        Integer did = Integer.parseInt(d_id);
        discuss = discussService.selectByD_id(did);
        List<Comment> comments = commentService.selectByd_id(did);
        ModelAndView mv = new ModelAndView();
        mv.addObject("discuss",discuss);
        mv.addObject("comment",comments);
        mv.setViewName("teacher/discussshow");
        return mv;
    }
    @RequestMapping({"/addcomment"})
    public String addcomment(HttpServletRequest req,@RequestParam("d_id")String d_id,@RequestParam("content")String content){
        Comment comment = new Comment();
        Integer did = Integer.parseInt(d_id);
        comment.setD_id(did);
        comment.setContent(content);
        Integer t_id = (int) req.getSession().getAttribute("t_id");
        comment.setUser_id(t_id);
        //学生为1，教师为0
        comment.setUser_type(0);
        int i = commentService.insert(comment);
        return "redirect:/teacher/discussshow?d_id="+d_id;

    }

    //公告
    @RequestMapping("/notice")
    public String notice(){
        return "teacher/notice";
    }
    @ResponseBody
    @RequestMapping(value = {"/noticelist"})
    public List<Notice> noticeList(){
        List<Notice> notices = noticeService.select_All();
        return notices;
    }
    @RequestMapping("/studentManagement")
    public String studentManagement(Model model,@RequestParam("c_id")String c_id){
        model.addAttribute("c_id",c_id);
        return "teacher/studentManagement";
    }
    @ResponseBody
    @ApiOperation(value="查找学生", notes="根据课程id查找学生用户")
    @ApiImplicitParam(name = "c_id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/getStudentByC_id/{c_id}", method = RequestMethod.GET)
    public List<Student> selectStudent (@PathVariable(value = "c_id") Integer c_id) {


        List<StudentCourseSelect> studentCourseSelects = studentCourseSelectService.selectByC_id(c_id);
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < studentCourseSelects.size(); i++) {
            StudentCourseSelect select = studentCourseSelects.get(i);
            Student student = studentService.selectById(select.getS_id());
            studentList.add(student);
        }
        System.out.println(studentList);

        return studentList;
    }
    /**
     * 根据s_id删除选课表中学生选课信息
     */
    @ApiOperation(value="删除用户", notes="根据id删除指定用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/deleteS_c/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> deleteById (@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        try {
            int i = studentCourseSelectService.deleteByS_id(id);
            result.setResult(id);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setResult("服务异常");
            result.setStatus("500");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }




}
