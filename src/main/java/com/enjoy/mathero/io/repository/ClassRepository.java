package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.ClassEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringBoot repository to query database for classes.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Repository
public interface ClassRepository extends PagingAndSortingRepository<ClassEntity, Long> {
    ClassEntity findByClassName(String className);
    ClassEntity findByClassId(String classId);
}
