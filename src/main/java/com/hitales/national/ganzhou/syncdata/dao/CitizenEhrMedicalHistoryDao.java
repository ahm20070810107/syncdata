package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenEhrMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CitizenEhrMedicalHistoryDao extends JpaRepository<CitizenEhrMedicalHistory, Long>, JpaSpecificationExecutor<CitizenEhrMedicalHistory> {

    @Modifying
    @Transactional
    @Query("delete from CitizenEhrMedicalHistory where ehrId=:ehrId")
    void deleteByEhrId(@Param("ehrId") Long ehrId);

    List<CitizenEhrMedicalHistory> findByEhrId(Long ehrId);

}
