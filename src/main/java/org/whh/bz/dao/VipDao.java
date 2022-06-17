package org.whh.bz.dao;


import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Vip;
@Repository
public interface VipDao {
    int deleteByPrimaryKey(String vxId);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(String vxId);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}