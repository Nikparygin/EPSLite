package ru.russianpost.epslite.dao;

import ru.russianpost.epslite.api.Letter;

import java.util.List;

public interface LetterDao {

   String saveLetter(Letter letter);

   List<Letter> getLetters();

   List<Letter> getLettersByDate(int date);

   Letter getLetterById(String letterId);

   List<Letter> getLettersByCustomer(int customerId);
}
