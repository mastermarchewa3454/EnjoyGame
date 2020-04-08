package com.enjoy.mathero.io.repository;

import com.enjoy.mathero.io.entity.ClassStageSummaryEntity;
import com.enjoy.mathero.io.entity.SoloResultEntity;
import com.enjoy.mathero.io.entity.StageSummaryReportEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoloResultRepository extends CrudRepository<SoloResultEntity, Long> {
    List<SoloResultEntity> findAllByUserDetails(UserEntity userEntity);
    List<SoloResultEntity> findTop10ByOrderByScoreDesc();
    List<SoloResultEntity> findTop10ByStageNumberOrderByScoreDesc(int stageNumber);

    @Query(value = "SELECT u.user_id AS userId, r.stage_number AS stageNumber, SUM(r.easy_correct) AS easyCorrect, SUM(r.medium_correct) AS mediumCorrect, SUM(r.hard_correct) AS hardCorrect, SUM(r.easy_total) AS easyTotal, SUM(r.medium_total) AS mediumTotal, SUM(r.hard_total) AS hardTotal FROM users u INNER JOIN solo_results r ON u.id=r.user_id WHERE u.user_id = :userId AND r.stage_number = :stageNumber GROUP BY u.user_id, r.stage_number;", nativeQuery = true)
    StageSummaryReportEntity getStageSummaryReport(@Param("userId") String userId, @Param("stageNumber") int stageNumber);

    @Query(value = "SELECT u.user_id AS userId, r.stage_number AS stageNumber, SUM(r.easy_correct) AS easyCorrect, SUM(r.medium_correct) AS mediumCorrect, SUM(r.hard_correct) AS hardCorrect, SUM(r.easy_total) AS easyTotal, SUM(r.medium_total) AS mediumTotal, SUM(r.hard_total) AS hardTotal FROM users u INNER JOIN solo_results r ON u.id=r.user_id WHERE u.user_id = :userId GROUP BY u.user_id, r.stage_number;", nativeQuery = true)
    List<StageSummaryReportEntity> getAllStageSummaryReports(@Param("userId") String userId);

    @Query(value = "SELECT c.class_id AS classId, c.class_name AS className, r.stage_number AS stageNumber, SUM(r.easy_correct) AS easyCorrect, SUM(r.medium_correct) AS mediumCorrect, SUM(r.hard_correct) AS hardCorrect, SUM(r.easy_total) AS easyTotal, SUM(r.medium_total) AS mediumTotal, SUM(r.hard_total) AS hardTotal FROM users u INNER JOIN solo_results r ON u.id=r.user_id INNER JOIN classes c ON c.id = u.class_id WHERE c.class_id = :classId AND r.stage_number = :stageNumber GROUP BY c.class_id, c.class_name, r.stage_number;", nativeQuery = true)
    ClassStageSummaryEntity getClassStageSummary(@Param("classId") String classId, @Param("stageNumber") int stageNumber);

    @Query(value = "SELECT c.class_id AS classId, c.class_name as className, r.stage_number as stageNumber, SUM(r.easy_correct) AS easyCorrect, SUM(r.medium_correct) AS mediumCorrect, SUM(r.hard_correct) AS hardCorrect, SUM(r.easy_total) AS easyTotal, SUM(r.medium_total) AS mediumTotal, SUM(r.hard_total) AS hardTotal FROM users u INNER JOIN solo_results r ON u.id=r.user_id INNER JOIN classes c ON c.id = u.class_id WHERE c.class_id = :classId GROUP BY c.class_id, c.class_name, r.stage_number;", nativeQuery = true)
    List<ClassStageSummaryEntity> getAllClassStageSummary(@Param("classId") String classId);
}
