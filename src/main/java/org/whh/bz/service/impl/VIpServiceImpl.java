package org.whh.bz.service.impl;

import org.springframework.stereotype.Service;
import org.whh.bz.enums.VIP_type;
import org.whh.bz.service.VIpService;

import java.sql.Timestamp;
@Service
public class VIpServiceImpl implements VIpService {

    @Override
    public Timestamp deadTime(String id) {
        return null;
    }

    @Override
    public boolean addVip(String id, VIP_type tp) {
        return false;
    }
}
