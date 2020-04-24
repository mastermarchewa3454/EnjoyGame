package com.enjoy.mathero.ui.model.response;

/**
 * Response model for question
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class QuestionRest {

    private String content;
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
