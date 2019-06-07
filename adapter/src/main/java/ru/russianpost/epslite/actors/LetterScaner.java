package ru.russianpost.epslite.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.dao.impl.CassandraLetterDaoImpl;

import java.util.HashSet;
import java.util.Set;

public class LetterScaner extends AbstractActor {
   private Set<Letter> letters = new HashSet<Letter>();
   private static final ActorSystem epslite = ActorSystem.create("epslite");
   ActorRef restSrviceActor = epslite.actorOf(Props.create(PdfCreator.class), "pdfCreatorActor");

   public void scanStorage() {

      if (letters.addAll(CassandraLetterDaoImpl.getInstance().getLetters())) {
         restSrviceActor.tell(letters, getSelf());
      }

//      letters.addAll(CassandraLetterDaoImpl.getInstance().getLetters());
//      restSrviceActor.tell(letters, getSelf());
   }

   public Set<Letter> getLetters() {
      return letters;
   }

   @Override
   public Receive createReceive() {
      return receiveBuilder().matchAny(m-> {this.scanStorage();}).build();
   }
}
