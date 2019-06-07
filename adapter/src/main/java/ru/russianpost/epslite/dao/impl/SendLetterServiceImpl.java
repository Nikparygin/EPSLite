package ru.russianpost.epslite.dao.impl;

import com.russianpost.sendletter.SendLetterRequestType;
import com.russianpost.sendletter.SendLetterResponseType;
import com.russianpost.sendletter.SendLetterService;

import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.dao.impl.CassandraLetterDaoImpl;


public class SendLetterServiceImpl implements SendLetterService {

    @Override
    public SendLetterResponseType sendLetter(final SendLetterRequestType sendLetterReq) {
        SendLetterResponseType response = new SendLetterResponseType();
        Letter letter = new Letter(sendLetterReq);
        response.setLetterId(CassandraLetterDaoImpl.getInstance().saveLetter(letter));
        return response;
    }
}
