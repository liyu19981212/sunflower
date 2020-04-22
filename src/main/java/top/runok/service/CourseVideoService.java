package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.CourseVideo;
import top.runok.mapper.CourseVideoMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-02-19 16:37
 **/
@Service
public class CourseVideoService {
    @Autowired
    CourseVideoMapper courseVideoMapper;

    //查询所有的视频
    public List<CourseVideo> select_all() {
        return courseVideoMapper.select_all();
    }

    //根据课程号查询
    public List<CourseVideo> selectByC_id(Integer c_id) {
        return courseVideoMapper.selectByC_id(c_id);
    }

    //根据课程视频号查询
    public CourseVideo selectByV_id(Integer v_id) {
        return courseVideoMapper.selectByV_id(v_id);
    }

    //添加视频
    public int insert(CourseVideo courseVideo) {
        return courseVideoMapper.insert(courseVideo);
    }

    //修改视频
    public int update(CourseVideo courseVideo) {
        return courseVideoMapper.update(courseVideo);
    }

    //删除视频
    public int delete(Integer v_id) {
        return courseVideoMapper.delete(v_id);
    }
}
