package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.DuoResultEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuoResultRepository extends CrudRepository<DuoResultEntity, Long> {
    List<DuoResultEntity> findTop10ByOrderByScoreDesc();
    List<DuoResultEntity> findTop10ByStageNumberOrderByScoreDesc(int stageNumber);
}
