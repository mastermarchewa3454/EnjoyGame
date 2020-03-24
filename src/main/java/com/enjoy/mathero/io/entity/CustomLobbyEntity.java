package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "custom_lobbies")
public class CustomLobbyEntity implements Serializable {
    private static final long serialVersionUID = -7041407542997346950L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String lobbyId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity authorDetails;

    @OneToMany(mappedBy = "lobbyDetails", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public UserEntity getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(UserEntity authorDetails) {
        this.authorDetails = authorDetails;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}
