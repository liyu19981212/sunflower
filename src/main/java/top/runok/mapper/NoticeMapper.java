package top.runok.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Notice;

import java.util.List;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-04-01 02:15
 **/
@Mapper
public interface NoticeMapper {
    //添加
    public int insert(Notice notice);
    //修改
    public int update(Notice notice);
    //删除
    public int delete(Integer n_id);
    //查找
    public List<Notice> select_All();
    //根据id号查找
    public Notice selectByN_id(Integer n_id);
}
