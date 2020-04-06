package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.DuoResultEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuoResultRepository extends CrudRepository<DuoResultEntity, Long> {
}
