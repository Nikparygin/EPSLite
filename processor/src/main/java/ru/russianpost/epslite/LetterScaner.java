package ru.russianpost.epslite;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.cassandra.CassandraConnector;
import ru.russianpost.epslite.cassandra.CassandraQuery;
//import ru.russianpost.epslite.dao.impl.CassandraLetterDaoImpl;

import java.util.HashSet;
import java.util.Set;

public class LetterScaner extends AbstractActor {

   private Set<Letter> letters = new HashSet<Letter>();

   private static final ActorSystem epslite = ActorSystem.create("epslite");

   private ActorRef pdfCreateActor = epslite.actorOf(Props.create(PdfCreator.class), "pdfCreateActor");

   private void scanStorage() {

      ResultSet resultSet = CassandraQuery.query("SELECT * FROM epslite.letter").execute();

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

         if (letters.add(letter)) {
            pdfCreateActor.tell(letter, getSelf());
         }
      }
   }

   public Set<Letter> getLetters() {
      return letters;
   }

   @Override
   public Receive createReceive() {
      return receiveBuilder().matchAny(m-> {this.scanStorage();}).build();
   }
}
