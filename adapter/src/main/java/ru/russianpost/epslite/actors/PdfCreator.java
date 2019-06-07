package ru.russianpost.epslite.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.russianpost.epslite.api.Letter;

import java.util.Set;

public class PdfCreator extends AbstractActor {

   private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

   public void createPdf(Set<Letter> letters) {
      System.out.println(letters);
   }

   @Override
   public Receive createReceive() {
      return receiveBuilder()
            .match(Set.class, this::createPdf)
            .matchAny(o -> log.info("received unknown message"))
            .build();
   }
}
