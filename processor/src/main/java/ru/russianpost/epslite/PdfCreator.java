package ru.russianpost.epslite;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;
import ru.russianpost.epslite.api.Letter;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ru.russianpost.epslite.impl.CassandraLetterDaoImpl;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PdfCreator extends AbstractActor {

   private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

   @Override
   public Receive createReceive() {
      return receiveBuilder()
            .match(Letter.class, this::createPdf)
            .matchAny(o -> log.info("received unknown message"))
            .build();
   }

   private void createPdf(Letter letter) {

      File pdfDir = new File("./Letters");
      pdfDir.mkdirs();

      String fileName = letter.getLetterId() + ".pdf";
      File pdfFile = new File(pdfDir, fileName);
      if (!checkIfFileAllreadyExist(letter.getLetterId())) {
         try (OutputStream out = new java.io.BufferedOutputStream(new FileOutputStream(pdfFile))) {
            final FopFactory fopFactory = FopFactory.newInstance(new File("C:\\Dev\\!EPSLite\\EPSLite\\processor\\src\\main\\resources\\userconfig.xml"));
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File("C:\\Dev\\!EPSLite\\EPSLite\\processor\\src\\main\\resources\\style.xsl")));
            Source src = new StreamSource(new ByteArrayInputStream(letter.getXml().getBytes()));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            log.info(String.format("PDF Letter %1$s successfully created!", letter.getLetterId()));
            CassandraLetterDaoImpl.getInstance().updatePdfLink(letter.getLetterId(), pdfFile.getAbsolutePath());
         } catch (TransformerException | IOException | SAXException e) {
            e.printStackTrace();
         }
      }
   }


   private boolean checkIfFileAllreadyExist(String letterId) {
      String pdfLink = CassandraLetterDaoImpl.getInstance().findPdfLink(letterId);
      return pdfLink != null && !pdfLink.isEmpty();
   }
}
