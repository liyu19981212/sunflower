package top.runok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.runok.entity.*;
import top.runok.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-02-22 13:25
 **/
@Controller
@RequestMapping("/student")
public class StudentController {
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
    StudentCourseSelectService studentCourseSelectService;
    @Autowired
    DiscussService discussService;
    @Autowired
    CommentService commentService;
    @Autowired
    NoticeService noticeService;

    //测试用途
    @RequestMapping("/taolun")
    public  String taolun(){
        return "discuss";
    }

    //转到登录界面
    @GetMapping({"/login"})
    public String login(){return "student/login";}
    //转到注册界面
    @GetMapping({"/register"})
    public String register(){return "student/register";}
    //学生登录响应
    @PostMapping({"/login"})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpSession session,Model model){
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);
        System.out.println("从页面传过来的对象"+student);
        Student student1 = studentService.login(student);
        System.out.println("从数据库查询的"+student1);

        if(student1==null){
            session.setAttribute("errorMsgs", "登陆失败");
            return "student/login";
        }else{
            session.setAttribute("s_id",student1.getS_id());
            session.setAttribute("s_username",student1.getUsername());
            return "redirect:/student/indexaction";
        }
    }

    //学生注册响应
    @RequestMapping(value = {"/register"},method = RequestMethod.POST)
    public String studentregister(HttpSession session,@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("telphone")String telphone){
        Student s = studentService.check(username);

        if(s!=null){
            session.setAttribute("error","用户已存在");
            return "student/register";
        }else{
            Student student = new Student();
            student.setUsername(username);

            student.setPassword(password);
            student.setTelphone(telphone);
            int insert = studentService.insert(student);
            return "student/login";
        }


    }
    //显示学生信息
    @RequestMapping({"/info"})
    public String info(Model model,HttpServletRequest request){
        Integer s_id = (int) request.getSession().getAttribute("s_id");
        Student student = studentService.selectById(s_id);
        model.addAttribute("student",student);
        return "student/info";
    }
    //修改学生信息
    @RequestMapping({"/updateinfo"})
    public String updateinfo(Model model,HttpServletRequest request,@RequestParam("s_num")String s_num,@RequestParam("s_name")String s_name,@RequestParam("telphone")String telphone){
        Integer s_id = (int) request.getSession().getAttribute("s_id");
        Student student = studentService.selectById(s_id);
        student.setS_name(s_name);
        student.setTelphone(telphone);
        student.setS_num(s_num);

        int i = studentService.update(student);
        return "redirect:/student/info";
    }
    //退出登录，去掉session，返回登录首页
    @RequestMapping({"/logout"})
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("s_id");
        request.getSession().removeAttribute("s_username");
        request.getSession().removeAttribute("errorMsgs");
        request.getSession().removeAttribute("error");
        return "student/login";
    }
    //转到主页响应及页面信息加载
    @RequestMapping({"/indexaction"})
    public ModelAndView indexaction(){
        //实战推荐部分
        String sz = "实战";
        List<Course> shizhan = new ArrayList<>();
        shizhan = courseService.selectbyname(sz);
        //深入好课
        String  sr = "深入";
        List<Course> shenru = new ArrayList<>();
        shenru = courseService.selectbyname(sr);
        //web前端工程师
        String  web = "前端";
        List<Course> qianduan = new ArrayList<>();
        qianduan = courseService.selectbyname(web);
        //java工程师
        String  j = "java";
        List<Course> java = new ArrayList<>();
        java = courseService.selectbyname(j);
        //Android工程师
        String  a = "Android";
        List<Course> android = new ArrayList<>();
        android = courseService.selectbyname(a);
        //ios工程师
        String i = "ios";
        List<Course> ios = new ArrayList<>();
        ios = courseService.selectbyname(i);
        //php工程师
        String p ="php";
        List<Course> php = new ArrayList<>();
        php = courseService.selectbyname(p);
        ModelAndView mv = new ModelAndView();
        if(shizhan.size()>5){
            shizhan=shizhan.subList(0,5);
        }
        if(shenru.size()>5){
            shenru=shenru.subList(0,5);
        }
        mv.addObject("shizhan",shizhan);
        mv.addObject("shenru",shenru);
        mv.addObject("qianduan",qianduan);
        mv.addObject("java",java);
        mv.addObject("php",php);
        mv.addObject("android",android);
        mv.addObject("ios",ios);
        mv.setViewName("student/index");
        return mv;
    }
    //首页之我的课程 根据学生id号查找

    @RequestMapping({"/mycourse"})
    public String mycourse(Model model, HttpServletRequest request ){
        Integer s_id = (int) request.getSession().getAttribute("s_id");
        List<Course> courses = new ArrayList<>();
        List<StudentCourseSelect> s =studentCourseSelectService.selectByS_id(s_id);
        for (int i = 0; i<s.size();i++){
            StudentCourseSelect select = s.get(i);

            Course c = courseService.selectByC_id(select.getC_id());
            courses.add(c);
        }
        model.addAttribute("course",courses);
        return "student/mycourse";
    }
    //根据课程名字模糊查询
    @RequestMapping({"/search"})
    public String search(Model model,@RequestParam("c_name")String c_name){
        List<Course> courses = courseService.selectbyname(c_name);
        model.addAttribute("course",courses);
        return "student/mycourse";
    }
    //根据传过来的课程号检索课程信息
    @RequestMapping({"/select"})
    public String select(HttpServletRequest request,Model model,@RequestParam("c_id") String c_id){

        Integer s_id = (int) request.getSession().getAttribute("s_id");
        Integer cid = Integer.parseInt(c_id);
        StudentCourseSelect studentCourseSelect = studentCourseSelectService.selectByS_c(s_id,cid);
        if(studentCourseSelect==null){

            model.addAttribute("cid",c_id);
            return "student/selectcourse";
        }
        return "redirect:/student/courseindex?c_id="+c_id;
    }
    //学生选课
    @RequestMapping({"/addcourse"})
    public String addcourse(HttpServletRequest request,@RequestParam("c_id")String c_id){
        Integer cid = Integer.parseInt(c_id);
        StudentCourseSelect  s = new StudentCourseSelect();
        s.setC_id(cid);
        Integer s_id = (int) request.getSession().getAttribute("s_id");
        s.setS_id(s_id);
        int i =studentCourseSelectService.insert(s);
        if(i<1){

        }
        return "redirect:/student/courseindex?c_id="+c_id;
    }

    @RequestMapping({"/courseindex"})
    public ModelAndView courseindex(@RequestParam("c_id")String c_id){
        Course course = new Course();
        Integer cid = Integer.parseInt(c_id);
        course = courseService.selectByC_id(cid);
        List<CourseFile> courseFile = new ArrayList<>();
        courseFile = courseFileService.selectByC_id(cid);
        List<CourseVideo> courseVideo = new ArrayList<>();
        courseVideo = courseVideoService.selectByC_id(cid);
        List<Test> tests = testService.selectByC_id(cid);

        ModelAndView mv = new ModelAndView();
        mv.addObject("course",course);
        mv.addObject("coursefile",courseFile);
        mv.addObject("coursevideo",courseVideo);
        mv.addObject("test",tests);
        mv.setViewName("student/courseindexa");
        return mv;
    }
    @RequestMapping({"/testquestion"})
    public ModelAndView testquestion(@RequestParam("test_id") String test_id){
        Integer testid = Integer.parseInt(test_id);
        List<TestQuestion> testQuestions = testQuestionService.selectByTest_id(testid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testquestion",testQuestions);
        modelAndView.setViewName("student/testquestion");
        return modelAndView;
    }
    //课程信息
    @RequestMapping({"/courseinfoaction"})
    public ModelAndView courseinfo(@RequestParam("c_id") String c_id){
        Integer cid = Integer.parseInt(c_id);
        Course course = courseService.selectByC_id(cid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("c_info",cid);
        modelAndView.setViewName("student/courseinfo");
        return modelAndView;
    }
    //课程视频信息
    @RequestMapping({"/coursevideo"})
    public ModelAndView coursevideo(@RequestParam("c_id") String c_id){
        Integer cid = Integer.parseInt(c_id);
        List<CourseVideo> courseVideo = courseVideoService.selectByC_id(cid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("coursevideo",courseVideo);
        modelAndView.setViewName("student/coursevideo");
        return modelAndView;
    }

    //发布讨论处理情况
    @RequestMapping({"/discuss"})
    public String dicuss(Model model,@RequestParam("c_id") String c_id){
        Integer cid = Integer.parseInt(c_id);
        List<Discuss> discusses = discussService.selectByC_id(cid);
        System.out.println(discusses);
        //ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("discuss",discusses);
        model.addAttribute("c_id",c_id);
        //modelAndView.setViewName("student/discuss");
        return "student/discuss";
    }
    @RequestMapping({"/adddiscuss"})
    public String adddiscuss(HttpServletRequest req, @RequestParam("c_id") String c_id, @RequestParam("title")String title, @RequestParam("content")String content){
        System.out.println(c_id+title+content);
        Discuss discuss = new Discuss();
        Integer cid = Integer.parseInt(c_id);
        discuss.setC_id(cid);
        discuss.setTitle(title);
        discuss.setContent(content);
        Integer s_id = (int) req.getSession().getAttribute("s_id");
        discuss.setS_id(s_id);
        int i =discussService.insert(discuss);
        return "redirect:/student/discuss?c_id="+c_id;
    }
    @RequestMapping({"/discussshow"})
    public ModelAndView adddiscussshow(@RequestParam("d_id") String d_id){

        Discuss discuss = new Discuss();
        Integer did = Integer.parseInt(d_id);
        discuss = discussService.selectByD_id(did);
        List<Comment> comments = commentService.selectByd_id(did);
        ModelAndView mv = new ModelAndView();
        mv.addObject("discuss",discuss);
        mv.addObject("comment",comments);
        mv.setViewName("student/discussshow");
        return mv;
    }
    @RequestMapping({"/addcomment"})
    public String addcomment(HttpServletRequest req,@RequestParam("d_id")String d_id,@RequestParam("content")String content){
        Comment comment = new Comment();
        Integer did = Integer.parseInt(d_id);
        comment.setD_id(did);
        comment.setContent(content);
        Integer s_id = (int) req.getSession().getAttribute("s_id");
        comment.setUser_id(s_id);
        //学生为1，教师为0
        comment.setUser_type(1);
        int i = commentService.insert(comment);
        return "redirect:/student/discussshow?d_id="+d_id;

    }
    //公告
    @RequestMapping("/notice")
    public String notice(){
        return "student/notice";
    }
    @ResponseBody
    @RequestMapping(value = {"/noticelist"})
    public List<Notice> noticeList(){
        List<Notice> notices = noticeService.select_All();
        return notices;
    }

}
