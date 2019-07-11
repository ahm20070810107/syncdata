package com.hitales.national.ganzhou.syncdata.dao;

import com.hitales.national.ganzhou.syncdata.entity.CitizenServeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface CitizenServeTagDao extends JpaRepository<CitizenServeTag, Long> {

    /**
     * 统计某县下 特定服务标签的数量，用于去重判断
     * @param countyId 县ID
     * @param tagName 标签名
     * @return 该标签的数量
     */
    long countByCountyIdAndTagName(Long countyId, String tagName);

    /**
     * 找到某县下的所有服务标签
     * @param countyId 县ID
     * @return 服务标签集合
     */
    Collection<CitizenServeTag> findByCountyId(Long countyId);

    /**
     * 在县下找到特定的服务标签名称
     * @param countyId 县ID
     * @param tagName 服务标签名称
     * @return
     */
    Optional<CitizenServeTag> findTopByCountyIdAndTagName(Long countyId, String tagName);


    /**
     * 删除指定ID的标签
     * @param idIn ID集合
     */
    @Modifying
    @Transactional
    void deleteByIdIn(Collection<Long> idIn);

    /**
     * 模糊查询服务标签
     * @param tagName
     * @return
     */
    List<CitizenServeTag> findAllByTagNameLike(String tagName);
}

