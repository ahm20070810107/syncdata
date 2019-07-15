package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenEhr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface CitizenEhrDao extends JpaRepository<CitizenEhr, Long>, JpaSpecificationExecutor<CitizenEhr> {


    Optional<CitizenEhr> findTopByCitizenId(Long citizenId);
}
