package com.enjoy.mathero.ui.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CustomLobbyRequestModel {

    @NotNull(message = "Author ID cannot be null!")
    private String authorId;

    @NotNull(message = "Custom lobby must have questions!")
    @Size(min = 20, max = 20, message = "Custom lobby must have 20 questions!")
    @Valid
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
