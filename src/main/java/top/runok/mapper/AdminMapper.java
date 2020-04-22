package top.runok.mapper;


import org.apache.ibatis.annotations.Mapper;
import top.runok.entity.Admin;

import java.util.List;


@Mapper
public interface AdminMapper {
    Admin adminlogin(Admin admin);
    List<Admin> adminselect();
}
