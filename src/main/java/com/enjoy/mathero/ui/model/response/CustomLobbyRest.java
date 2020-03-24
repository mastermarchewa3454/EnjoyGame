package com.enjoy.mathero.ui.model.response;

import java.util.List;

public class CustomLobbyRest {

    private String authorId;
    private List<QuestionRest> questions;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<QuestionRest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRest> questions) {
        this.questions = questions;
    }
}
