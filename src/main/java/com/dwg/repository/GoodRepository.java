package com.dwg.repository;


import com.dwg.model.entity.Good;
import com.dwg.repository.base.BaseRepository;

import java.util.List;

public interface GoodRepository extends BaseRepository<Good, Integer> {

    List<Good> findAllByBuyUserIdAndEndTimeGreaterThan(Long buyUserId, Long endTime);

    List<Good> findAllByEndTimeLessThan(Long endTime);
}