package top.runok.entity;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-04-01 02:13
 **/
public class Notice {
    private Integer n_id;
    private String content;
    private String createtime;

    @Override
    public String toString() {
        return "Notice{" +
                "n_id=" + n_id +
                ", content='" + content + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }

    public Integer getN_id() {
        return n_id;
    }

    public void setN_id(Integer n_id) {
        this.n_id = n_id;
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
