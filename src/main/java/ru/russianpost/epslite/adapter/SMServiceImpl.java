package ru.russianpost.epslite.adapter;

import ru.russianpost.epslite.api.Mail;

import javax.jws.WebService;

@WebService(endpointInterface = "ru.russianpost.epslite.adapter.SMService")
public class SMServiceImpl implements SMService {
   @Override
   public void SendMail(Mail mail) {
      System.out.println(mail.toString());
   }
}
