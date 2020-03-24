package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.CustomLobbyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomLobbyRepository extends CrudRepository<CustomLobbyEntity, Long> {
    CustomLobbyEntity findByLobbyId(String lobbyId);
}
