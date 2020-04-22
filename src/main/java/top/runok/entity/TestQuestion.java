package top.runok.entity;

/**
 * @program: sunflower
 * @description
 * @author: liyu
 * @create: 2020-03-19 21:24
 **/
public class TestQuestion {
    private Integer id;
    private String question;
    private String choice_a;
    private String choice_b;
    private String choice_c;
    private String choice_d;
    private String answer;
    private Integer test_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice_a() {
        return choice_a;
    }

    public void setChoice_a(String choice_a) {
        this.choice_a = choice_a;
    }

    public String getChoice_b() {
        return choice_b;
    }

    public void setChoice_b(String choice_b) {
        this.choice_b = choice_b;
    }

    public String getChoice_c() {
        return choice_c;
    }

    public void setChoice_c(String choice_c) {
        this.choice_c = choice_c;
    }

    public String getChoice_d() {
        return choice_d;
    }

    public void setChoice_d(String choice_d) {
        this.choice_d = choice_d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(Integer test_id) {
        this.test_id = test_id;
    }

    @Override
    public String toString() {
        return "TestQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", choice_a='" + choice_a + '\'' +
                ", choice_b='" + choice_b + '\'' +
                ", choice_c='" + choice_c + '\'' +
                ", choice_d='" + choice_d + '\'' +
                ", answer='" + answer + '\'' +
                ", test_id=" + test_id +
                '}';
    }
}
