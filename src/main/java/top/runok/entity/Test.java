package top.runok.entity;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 21:21
 **/
public class Test {
    private Integer test_id;
    private Integer course_id;
    private String test_name;

    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(Integer test_id) {
        this.test_id = test_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "test_id=" + test_id +
                ", course_id=" + course_id +
                ", test_name='" + test_name + '\'' +
                '}';
    }
}
