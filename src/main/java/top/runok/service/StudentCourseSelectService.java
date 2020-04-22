package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.StudentCourseSelect;
import top.runok.mapper.StudentCourseSelectMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-29 13:23
 **/
@Service
public class StudentCourseSelectService {
    @Autowired
    StudentCourseSelectMapper studentCourseSelectMapper;
    //查询学生号查询
    public List<StudentCourseSelect> selectByS_id(Integer s_id) {
        return studentCourseSelectMapper.selectByS_id(s_id);
    }

    //根据学生号课程号查询
    public StudentCourseSelect selectByS_c(Integer s_id, Integer c_id) {
        return studentCourseSelectMapper.selectByS_c(s_id,c_id);
    }

    //根据课程号查询
    public List<StudentCourseSelect> selectByC_id(Integer c_id) {
        return studentCourseSelectMapper.selectByC_id(c_id);
    }

    //delete
    public int delete(Integer s_c_id) {
        return studentCourseSelectMapper.delete(s_c_id);
    }

    //delete
    public int deleteByS_id(Integer s_id) {
        return studentCourseSelectMapper.deleteByS_id(s_id);
    }

    //insert
    public int insert(StudentCourseSelect studentCourseSelect) {
        return studentCourseSelectMapper.insert(studentCourseSelect);
    }
}
