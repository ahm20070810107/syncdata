package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenEhrFamilyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CitizenEhrFamilyHistoryDao extends JpaRepository<CitizenEhrFamilyHistory, Long>, JpaSpecificationExecutor<CitizenEhrFamilyHistory> {


    @Modifying
    @Transactional
    @Query("delete from CitizenEhrFamilyHistory where ehrId=:ehrId")
    void deleteByEhrId(@Param("ehrId") Long ehrId);

    List<CitizenEhrFamilyHistory> findByEhrId(Long ehrId);
}
