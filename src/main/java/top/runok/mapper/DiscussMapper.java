package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Discuss;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 20:15
 **/
@Mapper
public interface DiscussMapper {

    //根据课程号查询所有的帖子
    public List<Discuss> selectByC_id(Integer c_id);
    //根据课程号学号查询
    public List<Discuss> selectByS_idC_id(Integer s_id,Integer c_id);
    //根据帖子ID号查询
    public Discuss selectByD_id(Integer d_id);
    //添加帖子
    public int insert(Discuss discuss);
}
