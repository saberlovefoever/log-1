package org.whh.bz.dao;

import org.springframework.stereotype.Repository;
import org.whh.bz.entity.VipRecord;

public interface VipRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRecord record);

    int insertSelective(VipRecord record);

    VipRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipRecord record);

    int updateByPrimaryKey(VipRecord record);
}