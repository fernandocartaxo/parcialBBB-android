package com.ctx.parcialbbb.modelos;

import android.net.Uri;

import java.math.BigDecimal;
import java.net.URL;

public class Participante {

    private Long id;
    private String name;
    private BigDecimal percent;
    private Long votes;
    private String urlImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", percent='" + percent + '\'' +
                ", votes=" + votes +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
