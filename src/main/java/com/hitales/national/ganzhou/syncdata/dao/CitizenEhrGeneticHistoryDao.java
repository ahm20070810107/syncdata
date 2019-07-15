package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenEhrGeneticHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


public interface CitizenEhrGeneticHistoryDao extends JpaRepository<CitizenEhrGeneticHistory, Long>, JpaSpecificationExecutor<CitizenEhrGeneticHistory> {


    @Modifying
    @Transactional
    @Query("delete from CitizenEhrGeneticHistory where ehrId=:ehrId")
    void deleteByEhrId(@Param("ehrId") Long ehrId);

    List<CitizenEhrGeneticHistory> findByEhrId(Long ehrId);

    @Query(value = "select distinct(name) from citizen_ehr_genetic_history where ehr_id in (select id from citizen_ehr where citizen_id in (select id from citizen where location in (?1) ) ) ", nativeQuery = true)
    List<String> queryNamesByLocation(Set<Long> locations);
}
