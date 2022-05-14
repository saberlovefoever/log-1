package org.whh.bz.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("img")
public class Img implements Serializable {
  private int imgId;
  private Date imgUpdate;
  private String imgKeywords;
  private String imgType;
  private String imgSize;

  public Img(int imgId, String imgKeywords, String imgType, String imgSize) {
    this.imgId = imgId;
    this.imgKeywords = imgKeywords;
    this.imgType = imgType;
    this.imgSize = imgSize;
  }
}
