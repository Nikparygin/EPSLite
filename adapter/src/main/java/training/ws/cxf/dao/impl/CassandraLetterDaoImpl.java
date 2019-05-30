package training.ws.cxf.dao.impl;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import training.ws.cxf.api.Letter;
import training.ws.cxf.config.Config;
import training.ws.cxf.dao.LetterDao;

import java.text.SimpleDateFormat;
import java.util.*;

public class CassandraLetterDaoImpl implements LetterDao {

   private static CassandraLetterDaoImpl instance;
   private final static List<String> SERVER_IP_LIST = Config.getInstance().getServerIpList();
   private final static String KEYSPACE = Config.getInstance().getKeyspace();
   private final static Cluster CLUSTER = Cluster.builder().addContactPoints(SERVER_IP_LIST.toArray(new String[0])).build();
   private final static Session SESSION = CLUSTER.connect(KEYSPACE);

   private CassandraLetterDaoImpl() {
   }

   public static CassandraLetterDaoImpl getInstance() {
      if (instance == null) {
         instance = new CassandraLetterDaoImpl();
      }
      return instance;
   }

   @Override
   public List<Letter> getLetters() {
      String getLettersCql = String.format("SELECT * FROM %1$s.letter", KEYSPACE);
      ResultSet resultSet = SESSION.execute(getLettersCql);

      List<Letter> letters = new ArrayList<Letter>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
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
   public String saveLetter(Letter letter) {
      String letterId = UUID.randomUUID().toString();
      Integer date = new Integer(new SimpleDateFormat("yyyyMMdd").format(new Date()));

      String saveLetterCql = String.format("INSERT INTO %1$s.letter (" +
            "letter_id, date, customer_id, customer_token, raw_address, fio, org_name, xml" +
            ") VALUES (" +
            "'%2$s',%3$d, %4$d, '%5$s', '%6$s','%7$s', '%8$s', '%9$s'" +
            ")",
            KEYSPACE,
            letterId,
            date,
            letter.getCustomerId(),
            letter.getCustomerToken(),
            letter.getRawAddress(),
            letter.getFio(),
            letter.getOrgName(),
            letter.getXml()
      );
      String saveLetterCql2 = String.format("INSERT INTO %1$s.letter_pk_date (" +
                  "letter_id, date, customer_id, customer_token, raw_address, fio, org_name, xml" +
                  ") VALUES (" +
                  "'%2$s',%3$d, %4$d, '%5$s', '%6$s','%7$s', '%8$s', '%9$s'" +
                  ")",
            KEYSPACE,
            letterId,
            date,
            letter.getCustomerId(),
            letter.getCustomerToken(),
            letter.getRawAddress(),
            letter.getFio(),
            letter.getOrgName(),
            letter.getXml()
      );

      SESSION.execute(saveLetterCql);
      SESSION.execute(saveLetterCql2);

      return letterId;
   }

   @Override
   public List<Letter> getLettersByDate(int date) {
      String getLettersCql = String.format("SELECT * FROM %1$s.letter_pk_date where date = %2$d allow filtering", KEYSPACE, date);
      ResultSet resultSet = SESSION.execute(getLettersCql);

      List<Letter> letters = new ArrayList<Letter>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
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
      String getLettersCql = String.format("SELECT * FROM %1$s.letter where letter_id = %2$s", KEYSPACE, letterId);
      ResultSet resultSet = SESSION.execute(getLettersCql);

         Letter letter = new Letter(
               resultSet.one().getInt("customer_id"),
               resultSet.one().getString("customer_token"),
               resultSet.one().getString("fio"),
               resultSet.one().getString("org_name"),
               resultSet.one().getString("raw_address"),
               resultSet.one().getString("xml")
         );

      return letter;
   }

   @Override
   public List<Letter> getLettersByCustomer(int customerId) {
      String getLettersCql = String.format("SELECT * FROM %1$s.letter_pk_date where customer_id = %2$d allow filtering", KEYSPACE, customerId);
      ResultSet resultSet = SESSION.execute(getLettersCql);
      List<Letter> letters = new ArrayList<Letter>();

      for (Row row : resultSet.all()) {
         Letter letter = new Letter(
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
}
