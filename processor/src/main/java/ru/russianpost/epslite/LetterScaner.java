package ru.russianpost.epslite;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import ru.russianpost.epslite.api.Letter;
import ru.russianpost.epslite.impl.CassandraLetterDaoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LetterScaner extends AbstractActor {

   private Set<Letter> letters = new HashSet<>();

   private static final ActorSystem epslite = ActorSystem.create("epslite");

   private ActorRef pdfCreateActor = epslite.actorOf(Props.create(PdfCreator.class), "pdfCreateActor");

   private void scanStorage() {
      List<Letter> letters = CassandraLetterDaoImpl.getInstance().getLetters();
      for (Letter letter: letters) {
         if (this.letters.add(letter)) {
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
