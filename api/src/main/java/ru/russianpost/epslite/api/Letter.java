package ru.russianpost.epslite.api;

import java.util.Objects;

public class Letter {
   private String letterId;
   private int customerId;
   private String customerToken;
   private String fio;
   private String orgName;
   private String rawAddress;
   private String xml;

   public Letter(int customerId, String customerToken, String fio, String orgName, String rowAddress, String xml) {
      new Letter(null, customerId, customerToken, fio, orgName, rowAddress, xml);
   }

   public Letter(String letterId, int customerId, String customerToken, String fio, String orgName, String rowAddress, String xml) {
      this.letterId = letterId;
      this.customerId = customerId;
      this.customerToken = customerToken;
      this.fio = fio;
      this.orgName = orgName;
      this.rawAddress = rowAddress;
      this.xml = xml;
   }

   public Letter() {
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
      return rawAddress;
   }

   public void setRawAddress(String rawAddress) {
      this.rawAddress = rawAddress;
   }

   public String getXml() {
      return xml;
   }

   public void setXml(String xml) {
      this.xml = xml;
   }

   public String getLetterId() {
      return letterId;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Letter letter = (Letter) o;
      return customerId == letter.customerId &&
            Objects.equals(letterId, letter.letterId) &&
            Objects.equals(customerToken, letter.customerToken) &&
            Objects.equals(fio, letter.fio) &&
            Objects.equals(orgName, letter.orgName) &&
            Objects.equals(rawAddress, letter.rawAddress) &&
            Objects.equals(xml, letter.xml);
   }

   @Override
   public int hashCode() {
      return Objects.hash(letterId, customerId, customerToken, fio, orgName, rawAddress, xml);
   }

   @Override
   public String toString() {
      return "Letter{" +
            "letterId='" + letterId + '\'' +
            ", customerId=" + customerId +
            ", customerToken='" + customerToken + '\'' +
            ", fio='" + fio + '\'' +
            ", orgName='" + orgName + '\'' +
            ", rawAddress='" + rawAddress + '\'' +
            ", xml='" + xml + '\'' +
            '}';
   }
}
