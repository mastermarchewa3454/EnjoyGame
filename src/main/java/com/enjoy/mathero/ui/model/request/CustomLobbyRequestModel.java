package com.enjoy.mathero.ui.model.request;

import java.util.List;

public class CustomLobbyRequestModel {

    private String authorId;
    private List<QuestionRequestModel> questions;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<QuestionRequestModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequestModel> questions) {
        this.questions = questions;
    }
}
