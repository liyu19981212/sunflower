package top.runok.entity;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-31 18:49
 **/
public class Comment {
    private Integer comment_id;
    private Integer d_id;
    private Integer user_id;
    private  Integer user_type;
    private String content;
    private String createtime;

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", d_id=" + d_id +
                ", user_id=" + user_id +
                ", user_type=" + user_type +
                ", content='" + content + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public Integer getD_id() {
        return d_id;
    }

    public void setD_id(Integer d_id) {
        this.d_id = d_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
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
}
