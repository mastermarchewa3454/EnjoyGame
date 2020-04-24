package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.CustomLobbyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringBoot repository to query database for custom lobbies.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Repository
public interface CustomLobbyRepository extends CrudRepository<CustomLobbyEntity, Long> {
    CustomLobbyEntity findByLobbyId(String lobbyId);
}
