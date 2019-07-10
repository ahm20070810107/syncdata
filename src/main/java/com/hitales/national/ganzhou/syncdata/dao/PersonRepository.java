package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:27
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdno(String idno);
    List<Person> findByDistrictCodeIn(List<String> districtCodes);
}
