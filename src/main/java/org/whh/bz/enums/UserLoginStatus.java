package org.whh.bz.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UserLoginStatus {
    NOT_LOGIN(0,"未登录") ,ALREADY_LOGIN(1,"已登录");
    UserLoginStatus(int i, String s) {
    }
}
