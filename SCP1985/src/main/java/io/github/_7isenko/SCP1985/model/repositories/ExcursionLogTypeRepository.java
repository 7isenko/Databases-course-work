package io.github._7isenko.SCP1985.model.repositories;

import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcursionLogTypeRepository {

    @Procedure(value = "make_reports")
    List<ExcursionLogType> makeReports();
}
