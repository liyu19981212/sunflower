package top.runok.entity;

/*
* 课程文件
* 2020年2月18日14:28:58
*
* */

public class CourseFile {
    private Integer f_id;
    private String f_path;
    private String f_name;
    private Integer c_id;

    @Override
    public String toString() {
        return "CourseFile{" +
                "f_id=" + f_id +
                ", f_path='" + f_path + '\'' +
                ", f_name='" + f_name + '\'' +
                ", c_id=" + c_id +
                '}';
    }

    public Integer getF_id() {
        return f_id;
    }

    public void setF_id(Integer f_id) {
        this.f_id = f_id;
    }

    public String getF_path() {
        return f_path;
    }

    public void setF_path(String f_path) {
        this.f_path = f_path;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }
}
