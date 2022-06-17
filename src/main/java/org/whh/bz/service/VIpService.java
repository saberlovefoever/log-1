package org.whh.bz.service;

import org.whh.bz.entity.Vip;
import org.whh.bz.enums.VIP_type;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public interface VIpService {
    Timestamp deadTime(String id);

    boolean addVip(String id, VIP_type tp);

}
