package org.whh.bz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public enum VIP_type {
    ONE_DAY(1,"1天","20"),
    ONE_MONTH(10,"30天","600"),
    ONE_YEAR(50,"永久","NO_LIMIT");
    VIP_type(int i, String s, String no_limit) {
    }
}
