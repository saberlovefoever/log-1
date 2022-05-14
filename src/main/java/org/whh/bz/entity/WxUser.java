package org.whh.bz.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WxUser implements Serializable {
@Nullable
  private String wxName;
@Nullable
  private String wxOpenid;

  public WxUser() {
  }

  public WxUser(String wxName, String wxOpenid) {
    this.wxName = wxName;
    this.wxOpenid = wxOpenid;
  }

}
