package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.PersonList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:27
 */
public interface PersonListRepository extends JpaRepository<PersonList, Long> {
    Optional<PersonList> findByPersonid(String personid);

    Page<PersonList> findBySyncState(Integer syncState, Pageable pageable);
}
