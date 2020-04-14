package com.dwg.repository;


import com.dwg.model.entity.Shop;
import com.dwg.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ShopRepository extends BaseRepository<Shop, Long> {

    Page<Shop> findAllByGameTypeAndGamePriceBetweenAndStatusAndUserIdNotAndIsDelete(String gameType, BigDecimal min, BigDecimal max, Integer status, Long userId, Boolean isDelete, Pageable pageable);

    Page<Shop> findAllByGameTypeAndStatusAndUserIdNotAndIsDelete(String gameType, Integer status, Long userId, Boolean isDelete, Pageable pageable);

    Page<Shop> findAllByUserIdAndIsDelete(Long userId, Boolean isDelete, Pageable pageable);

    Page<Shop> findAllByIdInAndIsDelete(List<Long> ids, Boolean isDelete, Pageable pageable);

    List<Shop> findAllByIdIn(List<Long> ids);
}