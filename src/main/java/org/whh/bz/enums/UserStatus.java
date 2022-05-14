package org.whh.bz.enums;


import lombok.Getter;
import lombok.ToString;
@ToString
@Getter
public enum UserStatus {
    NOT_LOGIN(0,"未登录请登录"),
    NORMAL_USER(1,"普通用户每日只限一张"),
    VIP_DAY_USER(2,"日会员每日20张"),
    VIP_MONTH_USER_(3,"月会员每日30张"),
    VIP_FOREVER_USER(10,"永久会员无限下载");
    private int code;
    private String msg;

    UserStatus(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

}
