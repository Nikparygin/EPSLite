package ru.russianpost.epslite.dao;

import ru.russianpost.epslite.api.Letter;

import java.util.List;

/**
 * LetterDao interface produce all the functionality required for saving and getting Letter objects
 */
public interface LetterDao {

   /**
    * Save letter
    * @param letter letter that need to be saved
    * @return id of the saved letter, null if letter could'nt be saved
    */
   String saveLetter(Letter letter);

   /**
    * Return letters from Cassandra database
    * @return letters
    */
   List<Letter> getLetters();

   /**
    * Return letters saved at the specified date
    * @param date specified date
    * @return letters
    */
   List<Letter> getLettersByDate(int date);

   /**
    * Return letters saved at the specified Id
    * @param letterId specified id
    * @return letters
    */
   Letter getLetterById(String letterId);

   /**
    * Return letters saved at the specified customerId
    * @param customerId specified customerId
    * @return letters
    */
   List<Letter> getLettersByCustomer(int customerId);

   void updatePdfLink(String letterId, String pdfLink);

   int getDateByLetterId(String letterId);

   String findPdfLink(String letterId);
}
