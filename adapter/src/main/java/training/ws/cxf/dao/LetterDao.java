package training.ws.cxf.dao;

import training.ws.cxf.api.Letter;

import java.util.List;

public interface LetterDao {

   String saveLetter(Letter letter);

   List<Letter> getLetters();

   List<Letter> getLettersByDate(int date);

   Letter getLetterById(String letterId);

   List<Letter> getLettersByCustomer(int customerId);
}
