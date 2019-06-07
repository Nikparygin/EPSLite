package ru.russianpost.epslite;

import ru.russianpost.epslite.api.Letter;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PdfCreator extends AbstractActor {

   private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

   public void createPdf(Letter letter) {
      System.out.println(letter);
   }

   @Override
   public Receive createReceive() {
      return receiveBuilder()
            .match(Letter.class, this::createPdf)
            .matchAny(o -> log.info("received unknown message"))
            .build();
   }
}
