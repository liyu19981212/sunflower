package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Test;
import top.runok.entity.TestQuestion;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 21:56
 **/
@Mapper
public interface TestQuestionMapper {
    //根据课程考试号查找
    public List<TestQuestion> selectByTest_id(Integer test_id);
    //根据课程考试试卷号查找
    public TestQuestion selectById(Integer id);
    //添加考试
    public int insert(TestQuestion testQuestion);
    //删除考试
    public int delete(Integer id);
    //修改考试
    public int update(TestQuestion testQuestion);
}
