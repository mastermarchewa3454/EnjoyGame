package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "questions")
public class QuestionEntity implements Serializable {
    private static final long serialVersionUID = 8588490449536644867L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private CustomLobbyEntity lobbyDetails;

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

    public CustomLobbyEntity getLobbyDetails() {
        return lobbyDetails;
    }

    public void setLobbyDetails(CustomLobbyEntity lobbyDetails) {
        this.lobbyDetails = lobbyDetails;
    }
}
