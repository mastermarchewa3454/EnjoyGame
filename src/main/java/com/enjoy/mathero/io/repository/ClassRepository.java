package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.ClassEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
    ClassEntity findByClassName(String className);
}
