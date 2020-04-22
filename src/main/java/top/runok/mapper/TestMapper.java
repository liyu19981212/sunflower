package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Test;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 21:55
 **/
@Mapper
public interface TestMapper {
    //根据课程号查找
    public List<Test> selectByC_id(Integer c_id);
    //添加考试
    public int insert(Test test);
    //删除考试
    public int delete(Integer test_id);
    //修改考试
    public int update(Test test);
}
