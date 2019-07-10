package com.hitales.national.ganzhou.syncdata.dao;


import com.hitales.national.ganzhou.syncdata.entity.GB2260;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GB2260Dao extends JpaRepository<GB2260, Long>, JpaSpecificationExecutor<GB2260> {

   List<GB2260> findByCanonicalCodeAndDepth(Long canonicalCode, Integer depth);

   List<GB2260> findByCanonicalCode(Long canonicalCode);

   List<GB2260> findByNameAndDepth(String name, Integer depth);

    List<GB2260> findByNameAndCanonicalCode(String name, Long canonicalCode);

   List<GB2260> findByNameAndCanonicalCodeAndDepth(String name, Long canonicalCode, Integer depth);

    @Query(value = "SELECT * from sys_gb2260 where  depth = ?1 and  canonical_code like ?2",nativeQuery = true)
    List<GB2260> findByDepthAndCanonicalCodeLike(int i, String s);

    @Query(value = "SELECT * from sys_gb2260 where  depth = ?1 and  canonical_code like ?2 and name = ?3",nativeQuery = true)
    List<GB2260> findByDepthAndCanonicalCodeLikeAndName(int i, String s, String name);

}
