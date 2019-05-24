package ru.russianpost.epslite.api;

public class Mail {

   private int mailId;

   private String mailData;

   private String rawAddress;

   private String sender;

   private String receiver;

   public Mail() {
      this.mailId = 0;
      this. mailData = null;
      this.rawAddress = null;
      this. sender = null;
      this.receiver = null;
   }

   public int getMailId() {
      return mailId;
   }

   public void setMailId(int mailId) {
      this.mailId = mailId;
   }

   public String getMailData() {
      return mailData;
   }

   public void setMailData(String mailData) {
      this.mailData = mailData;
   }

   public String getRawAddress() {
      return rawAddress;
   }

   public void setRawAddress(String rawAddress) {
      this.rawAddress = rawAddress;
   }

   public String getSender() {
      return sender;
   }

   public void setSender(String sender) {
      this.sender = sender;
   }

   public String getReceiver() {
      return receiver;
   }

   public void setReceiver(String receiver) {
      this.receiver = receiver;
   }

   @Override
   public String toString() {
      return "Mail{" +
            "mailId=" + mailId +
            ", mailData='" + mailData + '\'' +
            ", rawAddress='" + rawAddress + '\'' +
            ", sender='" + sender + '\'' +
            ", receiver='" + receiver + '\'' +
            '}';
   }
}
