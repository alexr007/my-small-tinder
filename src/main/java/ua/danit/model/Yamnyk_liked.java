package ua.danit.model;

public class Yamnyk_liked {
  private Long like_id;
  private Long who;
  private Long whom;

  public Long getLike_id() {
    return like_id;
  }

  public void setLike_id(Long like_id) {
    this.like_id = like_id;
  }

  public Long getWho() {
    return who;
  }

  public void setWho(Long who) {
    this.who = who;
  }

  public Long getWhom() {
    return whom;
  }

  public void setWhom(Long whom) {
    this.whom = whom;
  }
}
