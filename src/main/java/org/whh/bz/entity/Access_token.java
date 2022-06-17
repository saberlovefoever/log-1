package org.whh.bz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Access_token {
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
