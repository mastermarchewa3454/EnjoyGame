package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.CustomLobbyDto;

public interface CustomLobbyService {
    CustomLobbyDto createCustomLobby(CustomLobbyDto customLobbyDto);
    CustomLobbyDto getCustomLobbyByLobbyId(String lobbyId);
}
