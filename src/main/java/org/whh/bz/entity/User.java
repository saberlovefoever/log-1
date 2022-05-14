package org.whh.bz.entity;


public class User {

  private String userName;
  private String userPwd;
  private long userLevel;
  private String userId;
  private long userPhone;
  private String userEmail;



  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }


  public long getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(long userLevel) {
    this.userLevel = userLevel;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public long getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(long userPhone) {
    this.userPhone = userPhone;
  }


  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

}
