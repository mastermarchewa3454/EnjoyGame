package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.SoloResultEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoloResultRepository extends CrudRepository<SoloResultEntity, Long> {
    List<SoloResultEntity> findAllByUserDetails(UserEntity userEntity);
}
