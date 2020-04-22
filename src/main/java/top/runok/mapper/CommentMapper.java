package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Comment;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 23:45
 **/
@Mapper
public interface CommentMapper {
    //根据d_id查找
    public List<Comment> selectByd_id(Integer d_id);
    //添加
    public int insert(Comment comment);
}
