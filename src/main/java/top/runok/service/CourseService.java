package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Course;
import top.runok.mapper.CourseMapper;

import java.util.List;

@Service
public class CourseService {
    //注入依赖
    @Autowired
    CourseMapper courseMapper;
    //查询所有的课程
    public List<Course> select_all() {
        return courseMapper.select_all();
    }

    //根据课程id查询
    public Course selectByC_id(Integer c_id) {
        return courseMapper.selectByC_id(c_id);
    }

    //根据教师id查询
    public List<Course> selectByT_id(Integer t_id) {
        return courseMapper.selectByT_id(t_id);
    }

    //根据课程名字查询
    public Course selectByC_name(String c_name) {
        return courseMapper.selectByC_name(c_name);
    }

    //根据课程名字模糊查询
    public List<Course> selectbyname(String c_name) {
        return courseMapper.selectbyname((c_name));
    }

    //添加课程
    public int insert(Course course) {
        return courseMapper.insert(course);
    }

    //根据课程id修改课程
    public int update(Course course) {
        return courseMapper.update(course);
    }

    //删除课程
    public int delete(Integer c_id) {
        return courseMapper.delete(c_id);
    }
}
