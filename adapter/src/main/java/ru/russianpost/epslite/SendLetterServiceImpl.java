package ru.russianpost.epslite;

import com.russianpost.sendletter.RecipientType;
import com.russianpost.sendletter.SendLetterRequestType;
import com.russianpost.sendletter.SendLetterResponseType;
import com.russianpost.sendletter.SendLetterService;
import com.russianpost.sendletter.SenderType;

import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.dao.impl.CassandraLetterDaoImpl;


public class SendLetterServiceImpl implements SendLetterService {

    @Override
    public SendLetterResponseType sendLetter(final SendLetterRequestType sendLetterReq) {
        SendLetterResponseType response = new SendLetterResponseType();

        SenderType sender = sendLetterReq.getSender();
        RecipientType recipient = sendLetterReq.getRecipient();
        String xml = sendLetterReq.getXml();

        Letter letter = new Letter();
        letter.setCustomerId(sender.getCustomerId());
        letter.setCustomerToken(sender.getToken());
        letter.setFio(recipient.getFio());
        letter.setOrgName(recipient.getOrgName());
        letter.setRowAddress(recipient.getRawAddress());
        letter.setXml(xml);

        response.setLetterId(CassandraLetterDaoImpl.getInstance().saveLetter(letter));
        return response;
    }
}
