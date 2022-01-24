package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.object_types.LogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ExcursionLogTypeRepository extends JpaRepository<ExcursionLogType, Integer> {

    @Procedure(value = "make_reports")
    List<ExcursionLogType> makeReports();

    @Transactional
    @Modifying
    @Query(value = "update excursion_log SET reality_description = :descr, log_status = :status, " +
            "note = :note WHERE id = :id", nativeQuery = true)
    void updateExcursionLog(@Param("id") int excursion_log_id, @Param("descr") String reality_description,
                            @Param("status") String log_status, @Param("note") String note);

    @Transactional
    @Modifying
    @Query(value = "update retrieval SET return_to_foundation = :time WHERE id = :id", nativeQuery = true)
    void updateTimeBackToFoundation(@Param("time") Timestamp return_to_foundation, @Param("id") int retrieval_id);
}
