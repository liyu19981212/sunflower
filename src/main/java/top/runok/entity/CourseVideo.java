package top.runok.entity;

/*
* 课程视频
* 文件id号、文件路径、名字、所属课程
*
* */

public class CourseVideo {
    private Integer v_id;
    private String v_path;
    private String v_name;
    private Integer c_id;

    @Override
    public String toString() {
        return "CourseVideo{" +
                "v_id=" + v_id +
                ", v_path='" + v_path + '\'' +
                ", v_name='" + v_name + '\'' +
                ", c_id=" + c_id +
                '}';
    }

    public Integer getV_id() {
        return v_id;
    }

    public void setV_id(Integer v_id) {
        this.v_id = v_id;
    }

    public String getV_path() {
        return v_path;
    }

    public void setV_path(String v_path) {
        this.v_path = v_path;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }
}
