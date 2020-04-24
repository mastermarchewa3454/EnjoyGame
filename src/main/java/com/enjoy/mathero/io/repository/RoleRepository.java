package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringBoot repository to query database for roles.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByRoleName(String roleName);
}
