package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.StudentCourseSelect;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-29 12:24
 **/
@Mapper
public interface StudentCourseSelectMapper {
    //查询学生号查询
    public List<StudentCourseSelect> selectByS_id(Integer s_id);
    //根据学生号课程号查询
    public StudentCourseSelect selectByS_c(Integer s_id, Integer c_id);
    //根据课程号查询
    public List<StudentCourseSelect> selectByC_id(Integer c_id);
    //delete
    public int delete(Integer s_c_id);
    //delete
    public int deleteByS_id(Integer s_id);
    //insert
    public int insert(StudentCourseSelect studentCourseSelect);
}
