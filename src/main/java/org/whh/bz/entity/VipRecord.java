package org.whh.bz.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * vip_record
 * @author 
 */
@Data
@Alias("vipRecord")
public class VipRecord implements Serializable {
    private Integer id;

    private String recordVipId;

    private Date startTime;

    private Integer type;

    private Vip vip;

    private static final long serialVersionUID = 1L;
}