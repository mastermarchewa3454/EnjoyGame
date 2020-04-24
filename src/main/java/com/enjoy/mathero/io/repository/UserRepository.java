package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringBoot repository to query database for users.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findByUsername(String username);
}
