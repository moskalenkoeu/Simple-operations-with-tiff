import lotus.domino.*;

import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.*;
import javax.imageio.stream.FileImageOutputStream;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriter;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;

public class JavaAgent extends AgentBase {

    public void NotesMain() {

      try {
          Session session = getSession();
          AgentContext agentContext = session.getAgentContext();

          String pathtofile = "C:/testfiles/sample.tiff";
          
          FileSeekableStream ss = new FileSeekableStream(pathtofile);
          ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, null);
          int count = dec.getNumPages();          
          System.out.println("This TIF has " + count + " image(s)");
          
          File outputFile = new File("C:/testfiles/single.tif");
          
          RenderedImage page = dec.decodeAsRenderedImage(1);
                       
          final TIFFImageWriter writer = (TIFFImageWriter) new TIFFImageWriterSpi().createWriterInstance();
                                          
          writer.setOutput(new FileImageOutputStream(outputFile));
          writer.prepareWriteSequence(null);
          IIOImage iioim = new IIOImage(page, null, null);
          writer.writeToSequence(iioim, null);
              
          page = dec.decodeAsRenderedImage(10);
          iioim = new IIOImage(page, null, null);
          writer.writeToSequence(iioim, null);
              
          writer.endWriteSequence();
          writer.dispose();
              
      } catch(Exception e) {
          e.printStackTrace();
       }
       
   }  
    
}