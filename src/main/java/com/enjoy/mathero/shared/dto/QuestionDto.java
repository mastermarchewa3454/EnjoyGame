package com.enjoy.mathero.shared.dto;

/**
 * Class used to transfer question data between layers
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class QuestionDto {
    private long id;
    private String content;
    private String correctAnswer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
