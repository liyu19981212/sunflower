package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Notice;
import top.runok.mapper.NoticeMapper;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-04-01 02:16
 **/
@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    //添加
    public int insert(Notice notice) {
        return noticeMapper.insert(notice);
    }

    //修改
    public int update(Notice notice) {
        return noticeMapper.update(notice);
    }

    //删除
    public int delete(Integer n_id) {
        return noticeMapper.delete(n_id);
    }

    //查找
    public List<Notice> select_All() {
        return noticeMapper.select_All();
    }

    //根据id号查找
    public Notice selectByN_id(Integer n_id) {
        return noticeMapper.selectByN_id(n_id);
    }
}
