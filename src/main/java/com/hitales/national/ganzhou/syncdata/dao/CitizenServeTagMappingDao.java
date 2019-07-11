package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenServeTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;


public interface CitizenServeTagMappingDao extends JpaRepository<CitizenServeTagMapping, Long> {


    /**
     * 找出具有指定ID的居民标签
     *
     * @param tagIds 标签集合
     * @return 记录ID
     */
    Collection<CitizenServeTagMapping> findByTagIdIn(Collection<Long> tagIds);


    /**
     * 找出居民的服务标签
     * @param citizenId 居民ID
     * @return 映射记录集合
     */
    Collection<CitizenServeTagMapping> findByCitizenId(Long citizenId);

    /**
     * 找到
     * @param citizenIdIn
     * @return
     */
    Collection<CitizenServeTagMapping> findByCitizenIdIn(Collection<Long> citizenIdIn);


    /**
     * 删除某个居民的某些标签
     * @param citizenId 居民
     * @param tagIdIn 标签ID集合
     */
    @Modifying
    @Transactional
    void deleteByCitizenIdAndTagIdIn(Long citizenId, Collection<Long> tagIdIn);

    /**
     * 通过服务包ID删除
     * @param packageId 服务包ID
     */
    @Modifying
    @Transactional
    void deleteByPackageId(Long packageId);
}

