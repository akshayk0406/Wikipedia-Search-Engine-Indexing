/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Main
{
    static BufferedWriter out ;
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        out = new BufferedWriter(new FileWriter("C:\\IRCourse_IIIT\\IRE2012\\TitleIndex.txt"));
        Parse parse = new Parse();
        File dir = new File("C:\\IRCourse_IIIT\\IRE2012\\Wiki_Split_23GB_to_100MB\\");
        String [] files = dir.list();
        String fname = "C:\\IRCourse_IIIT\\IRE2012\\Wiki_Split_23GB_to_100MB\\";
        String res="";
        for(int i = 0 ; i < files.length ; i++)
        {
            System.out.println("Indexing... " + files[i]);
            res = files[i];
            res = fname.concat(res);
            parse.run(res);
        }
        out.close();
        Merge m= new Merge();
        File Dir = new File("C:\\IRCourse_IIIT\\IRE2012\\Output\\");
	int num;
	num = Dir.listFiles().length;
	int start = 1;
	int last = Dir.listFiles().length;
        String Path ="C:\\IRCourse_IIIT\\IRE2012\\Output\\";
	while ( num != 1 )
        {
            m.merge(Path+Integer.toString(start)+(".txt"), Path+Integer.toString(start+1)+(".txt"), Path+(Integer.toString(last+1))+(".txt"));
            File f = new File(Path+(Integer.toString(start))+(".txt"));
            f.delete();
            f = new File(Path+(Integer.toString(start+1))+(".txt"));
	    f.delete();
            start = start + 2;
            last = last + 1;
            num = Dir.listFiles().length;
	}
        Dir = new File("C:\\IRCourse_IIIT\\IRE2012\\Output\\");
        files = Dir.list();
        res = files[0];
        res = "C:\\IRCourse_IIIT\\IRE2012\\Output\\".concat(res);
        BufferedReader br1 = new BufferedReader(new FileReader("C:\\IRCourse_IIIT\\IRE2012\\FinalIndex23.txt"));
        BufferedWriter fout = new BufferedWriter(new FileWriter("C:\\IRCourse_IIIT\\IRE2012\\".concat("SecondaryIndex23.txt")));
        String str="";
        int lineNumber=0;
        long totalLength=0;
        StringBuffer buf = new StringBuffer();
        while( (str = br1.readLine())!=null )
        {
            if(lineNumber%100==0) fout.write(str.substring(0,str.indexOf('#'))+"#"+totalLength+"\n");
            lineNumber = lineNumber+1;
            totalLength = totalLength + 1 + str.length();
            str="";
        }
        br1.close();
        fout.close();
        br1 = new BufferedReader(new FileReader("C:\\IRCourse_IIIT\\IRE2012\\TitleIndex23.txt"));
        fout = new BufferedWriter(new FileWriter("C:\\IRCourse_IIIT\\IRE2012\\SecondaryTitleIndex23.txt"));
        lineNumber=0;
        totalLength=0;
        str="";
        while( (str = br1.readLine())!=null )
        {
            if(lineNumber%100==0) fout.write(Integer.parseInt(str.substring(0,str.indexOf('#')))+"#"+totalLength+"\n");
            lineNumber = lineNumber+1;
            totalLength = totalLength + 1 + str.length();
            str="";
        }
        br1.close();
        fout.close();
        return;
    }
}