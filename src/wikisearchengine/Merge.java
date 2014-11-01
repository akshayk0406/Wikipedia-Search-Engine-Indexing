/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author akshay
 */
public class Merge
{
    public void merge(String file1,String file2,String file3) throws FileNotFoundException, IOException
    {
        
        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        BufferedReader br2 = new BufferedReader(new FileReader(file2));
        BufferedWriter out = new BufferedWriter(new FileWriter(file3));

        String s1=br1.readLine();
        String s2=br2.readLine();
        int c=0;

        while( s1!=null && s2!=null )
        {
            c=s1.substring(0,s1.indexOf('%')).compareTo(s2.substring(0,s2.indexOf('%')));
            if(c==0)
            {
                s1 = s1.concat("#");
                out.write(s1.concat(s2.substring(s2.indexOf('%')+1).concat("\n")));
                s1=br1.readLine();
                s2=br2.readLine();
            }
            else if(c>0)
            {
                out.write(s2.concat("\n"));
                s2=br2.readLine();
            }
            else
            {
                out.write(s1.concat("\n"));
                s1=br1.readLine();
            }
        }
        while( s1 != null )
        {
            out.write(s1.concat("\n"));
            s1=br1.readLine();
        }
        while(s2!=null)
        {
            out.write(s2.concat("\n"));
            s2=br2.readLine();
        }
        out.close();
        br1.close();
        br2.close();
    }
}
