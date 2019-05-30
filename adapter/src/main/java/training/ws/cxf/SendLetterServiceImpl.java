package training.ws.cxf;

import com.russianpost.sendletter.SendLetterRequestType;
import com.russianpost.sendletter.SendLetterResponseType;
import com.russianpost.sendletter.SendLetterService;
import training.ws.cxf.api.Letter;
import training.ws.cxf.dao.impl.CassandraLetterDaoImpl;


public class SendLetterServiceImpl implements SendLetterService {

    @Override
    public SendLetterResponseType sendLetter(final SendLetterRequestType sendLetterReq) {
        SendLetterResponseType response = new SendLetterResponseType();
        Letter letter = new Letter(sendLetterReq);
        response.setLetterId(CassandraLetterDaoImpl.getInstance().saveLetter(letter));
        return response;
    }
}
