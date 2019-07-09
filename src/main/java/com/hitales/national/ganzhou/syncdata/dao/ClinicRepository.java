package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:27
 */
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByOrgCode(String  orgCode);
}
