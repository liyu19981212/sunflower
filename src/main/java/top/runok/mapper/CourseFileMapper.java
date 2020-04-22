package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.CourseFile;

import java.util.List;

//标注@Mapper 课件操作接口定义
@Mapper
public interface CourseFileMapper {

    //查询所有的课件
    public List<CourseFile> select_all();
    //根据课程号查询
    public List<CourseFile> selectByC_id(Integer c_id);
    //根据课程文件号查询
    public CourseFile selectByF_id(Integer f_id);
    //添加课件
    public  int insert(CourseFile courseFile);
    //修改课件
    public int update(CourseFile courseFile);
    //删除课件
    public  int delete(Integer f_id);
}


