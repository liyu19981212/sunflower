package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Comment;
import top.runok.mapper.CommentMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 23:52
 **/
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    //根据d_id查找
    public List<Comment> selectByd_id(Integer d_id) {
        return commentMapper.selectByd_id(d_id);
    }

    //添加
    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }
}
