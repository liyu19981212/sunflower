package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;

import top.runok.entity.CourseVideo;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-02-19 01:58
 **/
@Mapper
public interface CourseVideoMapper {
    //查询所有的视频
    public List<CourseVideo> select_all();
    //根据课程号查询
    public List<CourseVideo> selectByC_id(Integer c_id);
    //根据课程视频id号查询
    public CourseVideo selectByV_id(Integer v_id);
    //添加视频
    public  int insert(CourseVideo courseVideo);
    //修改视频
    public int update(CourseVideo courseVideo);
    //删除视频
    public  int delete(Integer v_id);
}
