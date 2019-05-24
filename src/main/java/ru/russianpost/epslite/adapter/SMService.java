package ru.russianpost.epslite.adapter;

import ru.russianpost.epslite.api.Mail;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "SMService")
public interface SMService {

   @WebMethod
   void SendMail(Mail mail);
}
