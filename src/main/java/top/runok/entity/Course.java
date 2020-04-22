package top.runok.entity;

/*
* 课程
* 2020年2月18日14:25:55
* */
public class Course {
    private Integer c_id;
    private String c_name;
    private String c_info;
    private String c_img;
    private Integer t_id;

    @Override
    public String toString() {
        return "Course{" +
                "c_id=" + c_id +
                ", c_name='" + c_name + '\'' +
                ", c_info='" + c_info + '\'' +
                ", c_img='" + c_img + '\'' +
                ", t_id=" + t_id +
                '}';
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_info() {
        return c_info;
    }

    public void setC_info(String c_info) {
        this.c_info = c_info;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }
}
