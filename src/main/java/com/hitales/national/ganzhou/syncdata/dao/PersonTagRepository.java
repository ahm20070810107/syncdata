package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.PersonTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:27
 */
public interface PersonTagRepository extends JpaRepository<PersonTag, Long> {
    Optional<PersonTag> findByPersonid(String personid);
}
