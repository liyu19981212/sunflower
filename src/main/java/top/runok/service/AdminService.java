package top.runok.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.runok.entity.Admin;
import top.runok.mapper.AdminMapper;

import java.util.List;
@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    //管理员登录


    public Admin adminlogin(Admin admin) {
        return adminMapper.adminlogin(admin);
    }


    public List<Admin> adminselect() {
        return adminMapper.adminselect();
    }
}
