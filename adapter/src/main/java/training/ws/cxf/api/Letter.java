package training.ws.cxf.api;

import com.russianpost.sendletter.SendLetterRequestType;

import java.util.Objects;

public class Letter {
   private int customerId;
   private String customerToken;
   private String fio;
   private String orgName;
   private String rowAddress;
   private String Xml;

   public Letter(int customerId, String customerToken, String fio, String orgName, String rowAddress, String xml) {
      this.customerId = customerId;
      this.customerToken = customerToken;
      this.fio = fio;
      this.orgName = orgName;
      this.rowAddress = rowAddress;
      Xml = xml;
   }

   public Letter(SendLetterRequestType requestType) {
      this(requestType.getSender().getCustomerId(),
            requestType.getSender().getToken(),
            requestType.getRecipient().getFio(),
            requestType.getRecipient().getOrgName(),
            requestType.getRecipient().getRawAddress(),
            requestType.getXml());
   }

   public int getCustomerId() {
      return customerId;
   }

   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }

   public String getCustomerToken() {
      return customerToken;
   }

   public void setCustomerToken(String customerToken) {
      this.customerToken = customerToken;
   }

   public String getFio() {
      return fio;
   }

   public void setFio(String fio) {
      this.fio = fio;
   }

   public String getOrgName() {
      return orgName;
   }

   public void setOrgName(String orgName) {
      this.orgName = orgName;
   }

   public String getRawAddress() {
      return rowAddress;
   }

   public void setRowAddress(String rowAddress) {
      this.rowAddress = rowAddress;
   }

   public String getXml() {
      return Xml;
   }

   public void setXml(String xml) {
      Xml = xml;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Letter letter = (Letter) o;
      return customerId == letter.customerId &&
            Objects.equals(customerToken, letter.customerToken) &&
            Objects.equals(fio, letter.fio) &&
            Objects.equals(orgName, letter.orgName) &&
            Objects.equals(rowAddress, letter.rowAddress) &&
            Objects.equals(Xml, letter.Xml);
   }

   @Override
   public int hashCode() {
      return Objects.hash(customerId, customerToken, fio, orgName, rowAddress, Xml);
   }
}
