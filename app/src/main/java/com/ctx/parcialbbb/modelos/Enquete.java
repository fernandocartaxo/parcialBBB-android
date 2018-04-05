package com.ctx.parcialbbb.modelos;

import java.util.List;

public class Enquete {

    private Long id;
    private String status;
    private Long votes;
    private String question;
    private List<Participante> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Participante> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Participante> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Enquete{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", votes=" + votes +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
