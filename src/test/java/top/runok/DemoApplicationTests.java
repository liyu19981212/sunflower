package top.runok;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.runok.entity.Student;
import top.runok.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    StudentService studentService;
    @Test
    public void contextLoads() {
        Student s = new Student();
        s.setS_name("test");
        s.setUsername("test");
        s.setPassword("test");
        Integer i = studentService.insert(s);
        System.out.println("插入一条数据"+i);
    }

}
