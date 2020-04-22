package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {
    //查询所有学生
    List<Student> select_all();
    //根据id查找
    Student selectById(Integer s_id);
    //student login check
    Student login(Student student);
    //根据用户名查找，看是否有此人
    Student check(String username);
    //添加
    int insert(Student student);
    //删除
    int delete(Integer s_id);
    //修改
    int update(Student student);

}
