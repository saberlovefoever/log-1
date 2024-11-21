package org.whh.bz.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.boot.context.properties.ConstructorBinding;
import reactor.util.annotation.Nullable;

import java.io.Serializable;
import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("img")
public class Img implements Serializable {
  @Nullable
  private int imgId;
  private Date imgUpdate;
  private String imgKeywords;
  private String imgHash;
@ConstructorBinding
  public Img( String imgKeywords,String imgHash) {
    this.imgKeywords = imgKeywords;
    this.imgHash = imgHash;
  }
}
