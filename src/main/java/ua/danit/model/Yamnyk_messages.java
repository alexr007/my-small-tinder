package ua.danit.model;


public class Yamnyk_messages {

  private long messageId;
  private long sender;
  private long recipient;
  private String text;
  private java.sql.Timestamp messageTime;


  public long getMessageId() {
    return messageId;
  }

  public void setMessageId(long messageId) {
    this.messageId = messageId;
  }


  public long getSender() {
    return sender;
  }

  public void setSender(long sender) {
    this.sender = sender;
  }


  public long getRecipient() {
    return recipient;
  }

  public void setRecipient(long recipient) {
    this.recipient = recipient;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public java.sql.Timestamp getMessageTime() {
    return messageTime;
  }

  public void setMessageTime(java.sql.Timestamp messageTime) {
    this.messageTime = messageTime;
  }

}
