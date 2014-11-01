/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikisearchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author akshay
 */
public class Parse extends DefaultHandler {

    static int DocumentNumber=0;
    static boolean isTitle = false;
    static boolean isPage = false;
    static boolean isText = false;
    static boolean isId = false;
    static boolean isRevision = false;
    static String Title = "";
    static String Page = "";
    static String Text = "";
    static String Id="";
    Document Doc;
    static StringBuffer buf;
    public void run(String fname)
    {
        Id="";
        parseDocument(fname);
    }
    @Override
    public void startElement(String uri, String localName, String qname, Attributes attributes) {
        buf = new StringBuffer();
        if (qname.equalsIgnoreCase("page")) {
            DocumentNumber=DocumentNumber+1;
            isPage = true;
            Doc = new Document();
        } else if (qname.equalsIgnoreCase("title")) {
            isTitle = true;
        } else if (qname.equalsIgnoreCase("text")) {
            isText = true;
        }
        else if (qname.equalsIgnoreCase("revision")) {
            isRevision = true;
        }
        else if (qname.equalsIgnoreCase("id")) {
            isId = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qname) {
        if (qname.equalsIgnoreCase("page")) {
            isPage = false;
            Page =  buf.toString();
            Doc.setDocId(DocumentNumber);
            try {
                Main.out.write(DocumentNumber + "#" + Doc.getTitle().trim() + "&" +Id+"\n");
            } catch (IOException ex) {
                Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
            }
            PageParser pg = new PageParser();
            try {
                pg.Process(Doc);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (qname.equalsIgnoreCase("Title")) {
            isTitle = false;
            Title = buf.toString();
            Title=Title.trim();
            Title = Title.replaceAll("[^\\p{ASCII}]","");
            Doc.setTitle(Title);
        }
        if (qname.equalsIgnoreCase("Text")) {
            isText = false;
            Text = buf.toString();
            Doc.setContent(Text);
        }
        if (qname.equalsIgnoreCase("revision")) {
            isRevision=false;
        }
        if (qname.equalsIgnoreCase("id")) {
            isId = false;
            if(isRevision == Boolean.FALSE)
                Id = buf.toString();
        }
    }

    @Override
    public void characters(char[] ch, int st, int ed) {
        buf = buf.append(new String(ch,st,ed));
    }

    @Override
    public void endDocument ()
    {
	PageParser pageParser = new PageParser();
        try {
            pageParser.writeIndex();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseDocument(String fname) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            Id="";
            sp.parse(fname, this);
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
