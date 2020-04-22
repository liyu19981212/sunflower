package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.TestQuestion;
import top.runok.mapper.TestQuestionMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 22:09
 **/
@Service
public class TestQuestionService {
    @Autowired
    TestQuestionMapper testQuestionMapper;

    //根据课程考试号查找
    public List<TestQuestion> selectByTest_id(Integer test_id) {
        return testQuestionMapper.selectByTest_id(test_id);
    }

    //根据课程考试试卷号查找
    public TestQuestion selectById(Integer id) {
        return testQuestionMapper.selectById(id);
    }

    //添加考试
    public int insert(TestQuestion testQuestion) {
        return testQuestionMapper.insert(testQuestion);
    }

    //删除考试
    public int delete(Integer id) {
        return testQuestionMapper.delete(id);
    }

    //修改考试
    public int update(TestQuestion testQuestion) {
        return testQuestionMapper.update(testQuestion);
    }
}
