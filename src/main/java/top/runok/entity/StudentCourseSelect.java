package top.runok.entity;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-29 12:17
 **/
public class StudentCourseSelect{
    private Integer s_c_id;
    private  Integer s_id;
    private  Integer c_id;

    @Override
    public String toString() {
        return "StudentCourseSelect{" +
                "s_c_id=" + s_c_id +
                ", s_id=" + s_id +
                ", c_id=" + c_id +
                '}';
    }

    public Integer getS_c_id() {
        return s_c_id;
    }

    public void setS_c_id(Integer s_c_id) {
        this.s_c_id = s_c_id;
    }

    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }
}
