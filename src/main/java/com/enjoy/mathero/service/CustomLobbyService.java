package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.CustomLobbyDto;

/**
 * Business logic to deal with custom lobbies.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public interface CustomLobbyService {
    CustomLobbyDto createCustomLobby(CustomLobbyDto customLobbyDto);
    CustomLobbyDto getCustomLobbyByLobbyId(String lobbyId);
}
