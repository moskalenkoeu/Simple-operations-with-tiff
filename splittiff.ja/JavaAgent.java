import lotus.domino.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFEncodeParam;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.*;

public class JavaAgent extends AgentBase {

    public void NotesMain() {

      try {
          Session session = getSession();
          AgentContext agentContext = session.getAgentContext();

          String pathtofile="C:/testfiles/��� �. - ��������-��������������� ���������������� � ��������� ���������� - 1992 (2).tiff";
          
          FileSeekableStream ss = new FileSeekableStream(pathtofile);
          ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, null);
          int count = dec.getNumPages();          
          TIFFEncodeParam param = new TIFFEncodeParam();
          param.setCompression(32946);
          param.setLittleEndian(false); // Intel
          System.out.println("This TIF has " + count + " image(s)");
          for (int i = 0; i < count; i++) {
              RenderedImage page = dec.decodeAsRenderedImage(i);
              
              File f = new File("C:/testfiles/test/single_" + i + ".tif");
              System.out.println("Saving " + f.getCanonicalPath());
              ParameterBlock pb = new ParameterBlock();
              pb.addSource(page);
              pb.add(f.toString());
              pb.add("tiff");
              pb.add(param);
              RenderedOp r = JAI.create("filestore",pb);
              r.dispose();
          }
          // (Your code goes here)
      } catch(Exception e) {
          e.printStackTrace();
       }
       
   }  
    
}