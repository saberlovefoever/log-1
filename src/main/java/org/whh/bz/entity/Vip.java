package org.whh.bz.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * vip
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("vip")
public class Vip implements Serializable {

    private String vxId;

    private String nickname;

    private LocalDateTime deadline;

    private Byte type;

    private static final long serialVersionUID = 1L;
}