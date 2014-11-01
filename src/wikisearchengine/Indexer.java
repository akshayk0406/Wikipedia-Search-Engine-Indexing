/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author akshay
 */
public class Indexer
{
    public static TreeMap<String,Info> DocumentContent = new TreeMap<String,Info>();
    public static TreeMap<String,String> AllDocument = new TreeMap<String,String>();
    public static StopWordsRemoval stopWordRemoval = new StopWordsRemoval();
    public static Info info;
    public static final Pattern pattern = Pattern.compile("[a-z]+");
    public void Index(String res, int Idx, int DocumentId) {
        //  System.out.println(Str);
        if (res.isEmpty() == Boolean.TRUE) {
            return;
        }
        res = res.toLowerCase().trim();
        Stemmer stemmer = new Stemmer();
        res = stemmer.stem(res);
        if (stopWordRemoval.isStopWord(res) == Boolean.TRUE) {
            return;
        }
        if (DocumentContent.containsKey(res) == Boolean.FALSE) {
            info = new Info();
            info.setDocID(DocumentId);
            info.setFrequency(1);
            FindContent(info, Idx);
            DocumentContent.put(res, info);
        } else {
            Info tmp = DocumentContent.get(res);
            tmp.setFrequency(tmp.getFrequency() + 1);
            FindContent(tmp, Idx);
            DocumentContent.put(res, tmp);
        }
    }

    private void FindContent(Info info,int Idx)
    {
        if(Idx==1) info.setTitle();
        if(Idx==2) info.setCategory();
        if(Idx==3) info.setOutLink();
        if(Idx==4) info.setInfoBox();
        if(Idx==5) info.setContent();
    }

    void WriteInfo(int FileNo) throws FileNotFoundException, IOException
    {
        String Path="C:\\IRCourse_IIIT\\IRE2012\\Output\\";
        Path=Path.concat(Integer.toString(FileNo));
        Path=Path.concat(".txt");
        Iterator iterator = AllDocument.entrySet().iterator();
        BufferedWriter out = new BufferedWriter(new FileWriter(Path));
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            String Info = (String) entry.getValue();
            String Key = (String) entry.getKey();
            if(Key.isEmpty() == Boolean.TRUE) continue;
            out.write(Key);
            out.write("%");
            out.write(Info.concat("\n"));
        }
        out.close();
        AllDocument.clear();
    }

    private String gogo(String result,Document Doc,Info info,int Flag)
    {
        String Mask = Integer.toString(info.getMask());
        if(Flag==1)result=result.concat("#");
        result        = result.concat(Integer.toHexString(Doc.getDocId()));
        result        = result.concat(":");
        result        = result.concat(Integer.toHexString(Doc.getWordCount()));
        result        = result.concat(":");
        result        = result.concat(Integer.toString(info.getFrequency()));
        result        = result.concat(":");
        result        = result.concat(Mask);        
        return result;
    }
    void ProcessDocument(Document Doc)
    {
        Iterator iterator = DocumentContent.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            Info info = (Info) entry.getValue();
            String Key = (String) entry.getKey();
            if(AllDocument.containsKey(Key) == Boolean.FALSE)
            {
                String result = "";
                AllDocument.put(Key,gogo(result,Doc,info,0));
            }
            else
            {
                String result = AllDocument.get(Key);
                AllDocument.put(Key,gogo(result,Doc,info,1));
            }
        }
        DocumentContent.clear();
    }
}

