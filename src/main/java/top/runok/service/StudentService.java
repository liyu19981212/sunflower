package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Student;
import top.runok.mapper.StudentMapper;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    //查询所有学生
    public List<Student> select_all() {
        return studentMapper.select_all();
    }

    //根据id查找
    public Student selectById(Integer s_id) {
        return studentMapper.selectById(s_id);
    }

    //student login check
    public Student login(Student student) {
        return studentMapper.login(student);
    }

    //根据用户名查找，看是否有此人
    public Student check(String username) {
        return studentMapper.check(username);
    }

    //添加
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    //删除
    public int delete(Integer s_id) {
        return studentMapper.delete(s_id);
    }

    //修改
    public int update(Student student) {
        return studentMapper.update(student);
    }
}
