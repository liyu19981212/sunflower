package top.runok.entity;

import java.util.Date;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 18:49
 **/
public class Discuss {
    private Integer d_id;
    private  String title;
    private String content;
    private String createtime;
    private Integer s_id;
    private Integer c_id;

    @Override
    public String toString() {
        return "Discuss{" +
                "d_id=" + d_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createtime='" + createtime + '\'' +
                ", s_id=" + s_id +
                ", c_id=" + c_id +
                '}';
    }

    public Integer getD_id() {
        return d_id;
    }

    public void setD_id(Integer d_id) {
        this.d_id = d_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
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
