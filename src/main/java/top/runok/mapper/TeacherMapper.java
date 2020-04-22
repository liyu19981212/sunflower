package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Student;
import top.runok.entity.Teacher;

import java.util.List;

@Mapper
public interface TeacherMapper {
    //查询所有学生
    public List<Teacher> select_all();
    //根据id查找
    public Teacher selectById(Integer t_id);
    //student login check
    public Teacher login(Teacher teacher);
    //检测用户是否存在
    public Teacher check(String username);
    //添加
    public int insert(Teacher teacher);
    //删除
    public int delete(Integer t_id);
    //修改
    public int update(Teacher teacher);
}
