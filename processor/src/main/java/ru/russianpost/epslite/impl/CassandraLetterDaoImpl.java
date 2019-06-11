package ru.russianpost.epslite.impl;

import ru.russianpost.epslite.Config;
import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.cassandra.CassandraQuery;
import ru.russianpost.epslite.dao.LetterDao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CassandraLetterDaoImpl implements LetterDao {

   private static CassandraLetterDaoImpl instance;

   private final static String KEYSPACE = Config.getInstance().getKeyspace();
   private final static String LETTER_TABLE_NAME = "letter";
   private final static String LETTER_PK_DATE_TABLE_NAME = "letter_pk_date";
   private final static String LETTER_PK_CUSTOMER_TABLE_NAME = "letter_pk_customer";


   private CassandraLetterDaoImpl() {
   }

   public static CassandraLetterDaoImpl getInstance() {
      if (instance == null) {
         instance = new CassandraLetterDaoImpl();
      }
      return instance;
   }

   @Override
   public String saveLetter(Letter letter) {
      String letterId = UUID.randomUUID().toString();
      Integer date = new Integer(new SimpleDateFormat("yyyyMMdd").format(new Date()));

      for (String table: getTableLetterNames()) {
         String saveLetterIntoLetter = String.format("INSERT INTO %1$s.%2$s (" +
                     "letter_id, date, customer_id, customer_token, raw_address, fio, org_name, xml" +
                     ") VALUES (" +
                     "?,?,?,?,?,?,?,?" +
                     ")",
               Config.getInstance().getKeyspace(),
               table
         );
         CassandraQuery.query(saveLetterIntoLetter).execute(
               letterId,
               date,
               letter.getCustomerId(),
               letter.getCustomerToken(),
               letter.getRawAddress(),
               letter.getFio(),
               letter.getOrgName(),
               letter.getXml());
      }

      return letterId;
   }

   @Override
   public List<Letter> getLetters() {
      String getLettersCql = String.format("SELECT * FROM %1$s.%2$s", KEYSPACE, LETTER_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getLettersCql).execute();

      List<Letter> letters = new LinkedList<>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
               row.getString("letter_id"),
               row.getInt("customer_id"),
               row.getString("customer_token"),
               row.getString("fio"),
               row.getString("org_name"),
               row.getString("raw_address"),
               row.getString("xml")
         );
         letters.add(letter);
      }

      return letters;
   }

   @Override
   public List<Letter> getLettersByDate(int date) {
      String getLettersCql = String.format("SELECT * FROM %1$s.%2$s where date = ?", KEYSPACE, LETTER_PK_DATE_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getLettersCql).execute(date);

      List<Letter> letters = new LinkedList<>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
               row.getString("letter_id"),
               row.getInt("customer_id"),
               row.getString("customer_token"),
               row.getString("fio"),
               row.getString("org_name"),
               row.getString("raw_address"),
               row.getString("xml")
         );
         letters.add(letter);
      }

      return letters;
   }

   @Override
   public Letter getLetterById(String letterId) {
      String getLettersCql = String.format("SELECT * FROM %1$s.%2$s where letter_id = ?", KEYSPACE, LETTER_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getLettersCql).execute(letterId);
      Row row = resultSet.one();
      Letter letter = new Letter(
            row.getString("letter_id"),
            row.getInt("customer_id"),
            row.getString("customer_token"),
            row.getString("fio"),
            row.getString("org_name"),
            row.getString("raw_address"),
            row.getString("xml")
      );

      return letter;
   }

   @Override
   public List<Letter> getLettersByCustomer(int customerId) {
      String getLettersCql = String.format("SELECT * FROM %1$s.%2$s where customer_id = ?", KEYSPACE, LETTER_PK_CUSTOMER_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getLettersCql).execute(customerId);
      List<Letter> letters = new LinkedList<>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
               row.getString("letter_id"),
               row.getInt("customer_id"),
               row.getString("customer_token"),
               row.getString("fio"),
               row.getString("org_name"),
               row.getString("raw_address"),
               row.getString("xml")
         );
         letters.add(letter);
      }

      return letters;
   }

   @Override
   public void updatePdfLink(String letterId, String pdfLink) {
      String updatePdfLink;
      for (String table: getTableLetterNames()) {
         switch (table){
            case LETTER_TABLE_NAME:
               updatePdfLink = String.format("UPDATE %1$s.%2$s SET pdf_link = ? where letter_id = ?",
                     Config.getInstance().getKeyspace(),
                     table
               );
               CassandraQuery.query(updatePdfLink).execute(pdfLink, letterId);
               break;
            case LETTER_PK_DATE_TABLE_NAME:
            case LETTER_PK_CUSTOMER_TABLE_NAME:
               Letter letter = getLetterById(letterId);
               updatePdfLink = String.format("UPDATE %1$s.%2$s SET pdf_link = ? where customer_id = ? AND date = ? AND letter_id = ?",
                     Config.getInstance().getKeyspace(),
                     table
               );
               CassandraQuery.query(updatePdfLink).execute(pdfLink, letter.getCustomerId(), getDateByLetterId(letterId), letterId);
               break;
         }
      }
   }

   @Override
   public int getDateByLetterId(String letterId) {
      String getDateCql = String.format("select date from %1$s.%2$s WHERE letter_id =?",KEYSPACE, LETTER_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getDateCql).execute(letterId);
      return resultSet.one().getInt("date");
   }

   @Override
   public String findPdfLink(String letterId) {
      String getDateCql = String.format("select pdf_link from %1$s.%2$s WHERE letter_id =?",KEYSPACE, LETTER_TABLE_NAME);
      ResultSet resultSet = CassandraQuery.query(getDateCql).execute(letterId);
      return resultSet.one().getString("pdf_link");
   }

   private List<String> getTableLetterNames() {
      return new ArrayList<>(Arrays.asList(LETTER_TABLE_NAME, LETTER_PK_DATE_TABLE_NAME, LETTER_PK_CUSTOMER_TABLE_NAME));
   }
}
