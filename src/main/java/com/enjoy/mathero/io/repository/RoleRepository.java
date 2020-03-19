package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByRoleName(String roleName);
}
