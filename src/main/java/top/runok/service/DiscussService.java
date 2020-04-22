package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Discuss;
import top.runok.mapper.DiscussMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 20:36
 **/
@Service
public class DiscussService {
    @Autowired
    DiscussMapper discussMapper;
    //根据课程号查询所有的帖子
    public List<Discuss> selectByC_id(Integer c_id) {
        return discussMapper.selectByC_id(c_id);
    }

    //根据课程号学号查询
    public List<Discuss> selectByS_idC_id(Integer s_id, Integer c_id) {
        return discussMapper.selectByS_idC_id(s_id, c_id);
    }

    //根据帖子ID号查询
    public Discuss selectByD_id(Integer d_id) {
        return discussMapper.selectByD_id(d_id);
    }

    //添加帖子
    public int insert(Discuss discuss) {
        return discussMapper.insert(discuss);
    }
}
