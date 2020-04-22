package top.runok.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.runok.interceptor.AdminLoginInterceptor;
import top.runok.interceptor.StudentLoginInterceptor;
import top.runok.interceptor.TeacherLoginInterceptor;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-01 00:16
 **/
/*
 * 文件上传路径映射配置类
 * 登陆拦截配置
 * WebMvcConfigurer 用来配置springmvc中的一些配置信息
 * */
@Configuration//配置类注解
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private StudentLoginInterceptor studentLoginInterceptor;
    @Autowired
    private TeacherLoginInterceptor teacherLoginInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/static/**");
        registry.addInterceptor(studentLoginInterceptor).addPathPatterns("/student/**").excludePathPatterns("/student/login").excludePathPatterns("/student/register");
        registry.addInterceptor(teacherLoginInterceptor).addPathPatterns("/teacher/**").excludePathPatterns("/teacher/register").excludePathPatterns("/teacher/login");
    }
    @Value("${uploadpath}")
    private String uploadpath;

    @Value("${vpath}")
    private String vpath;


    //添加资源映射
    /*
     * ResourceHandlers 相当于springmvc配置resource配置项
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(vpath + "**")//指定请求URL
                //"file:D:/upload"
                .addResourceLocations("file:" + uploadpath);//指定映射到的资源

    }


}
