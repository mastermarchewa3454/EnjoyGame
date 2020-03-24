package com.enjoy.mathero.ui.model.request;

import javax.validation.constraints.NotNull;

public class QuestionRequestModel {

    @NotNull(message = "Question content cannot be null!")
    private String content;

    @NotNull(message = "Question answer cannot be null!")
    private String correctAnswer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
