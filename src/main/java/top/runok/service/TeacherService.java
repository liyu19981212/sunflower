package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Teacher;
import top.runok.mapper.TeacherMapper;

import java.util.List;

/*
* teacher
* service
* */
@Service
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    //查询所有学生
    public List<Teacher> select_all() {
        return teacherMapper.select_all();
    }

    //根据id查找
    public Teacher selectById(Integer t_id) {
        return teacherMapper.selectById(t_id);
    }

    //student login check
    public Teacher login(Teacher teacher) {
        return teacherMapper.login(teacher);
    }

    //检测用户是否存在
    public Teacher check(String username) {
        return teacherMapper.check(username);
    }

    //添加
    public int insert(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    //删除
    public int delete(Integer t_id) {
        return teacherMapper.delete(t_id);
    }

    //修改
    public int update(Teacher teacher) {
        return teacherMapper.update(teacher);
    }

}
