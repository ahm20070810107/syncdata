package com.hitales.national.ganzhou.syncdata.dao;


import com.hitales.national.ganzhou.syncdata.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CitizenDao extends JpaRepository<Citizen, Long>, JpaSpecificationExecutor<Citizen> {
    List<Citizen> findByIdNo(String idNo);
}
