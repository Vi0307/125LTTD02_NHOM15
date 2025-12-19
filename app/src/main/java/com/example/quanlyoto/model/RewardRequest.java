package com.example.quanlyoto.model;

import com.google.gson.annotations.SerializedName;

public class RewardRequest {
    @SerializedName("maND")
    private Integer maND;

    @SerializedName("correctAnswers")
    private Integer correctAnswers;

    public RewardRequest(Integer maND, Integer correctAnswers) {
        this.maND = maND;
        this.correctAnswers = correctAnswers;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
