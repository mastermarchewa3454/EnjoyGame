package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.FriendshipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<FriendshipEntity, Long> {
}
