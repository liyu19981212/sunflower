package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Course;


import java.util.List;
//课程
/*
* 关于课程的一些操作
* */
@Mapper
public interface CourseMapper {
    //查询所有的课程
    public List<Course> select_all();
    //根据课程id查询
    public Course selectByC_id(Integer c_id);
    //根据教师id查询
    public List<Course> selectByT_id(Integer t_id);
    //根据课程名字查询
    public  Course selectByC_name(String c_name);
    //根据课程名字模糊查询
    public List<Course> selectbyname(String c_name);
    //添加课程
    public int insert(Course course);
    //根据课程id修改课程
    public  int update(Course course);
    //删除课程
    public int delete(Integer c_id);
}
