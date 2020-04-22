package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.CourseFile;
import top.runok.mapper.CourseFileMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description 课程文件service
 * @author: liyu
 * @create: 2020-02-19 16:35
 **/
@Service
public class CourseFileService {
    @Autowired
    CourseFileMapper courseFileMapper;

    //查询所有的课件
    public List<CourseFile> select_all() {
        return courseFileMapper.select_all();
    }

    //根据课程号查询
    public List<CourseFile> selectByC_id(Integer c_id) {
        return courseFileMapper.selectByC_id(c_id);
    }

    //根据课程号查询
    public CourseFile selectByF_id(Integer f_id) {
        return courseFileMapper.selectByF_id(f_id);
    }

    //添加课件
    public int insert(CourseFile courseFile) {
        return courseFileMapper.insert(courseFile);
    }

    //修改课件
    public int update(CourseFile courseFile) {
        return courseFileMapper.update(courseFile);
    }

    //删除课件
    public int delete(Integer f_id) {
        return courseFileMapper.delete(f_id);
    }
}
