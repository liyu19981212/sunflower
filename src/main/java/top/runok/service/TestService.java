package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Test;
import top.runok.mapper.TestMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 22:08
 **/
@Service
public class TestService {
    @Autowired
    TestMapper testMapper;
    //根据课程号查找
    public List<Test> selectByC_id(Integer c_id) {
        return testMapper.selectByC_id(c_id);
    }

    //添加考试
    public int insert(Test test) {
        return testMapper.insert(test);
    }

    //删除考试
    public int delete(Integer test_id) {
        return testMapper.delete(test_id);
    }

    //修改考试
    public int update(Test test) {
        return testMapper.update(test);
    }
}
