package com.enjoy.mathero.shared.dto;

import java.util.List;

/**
 * Class used to transfer custom lobby data between layers
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class CustomLobbyDto {
    private long id;
    private String lobbyId;
    private UserDto authorDetails;
    private List<QuestionDto> questions;

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

    public UserDto getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(UserDto authorDetails) {
        this.authorDetails = authorDetails;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
