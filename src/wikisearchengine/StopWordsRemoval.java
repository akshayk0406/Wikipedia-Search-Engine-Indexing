/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akshay
 */
public class StopWordsRemoval
{
    public static final HashMap<String,Boolean> StopWords = new HashMap<String,Boolean>();
    public StopWordsRemoval()
    {
        FileReader inputStream = null;
        try {
            inputStream = new FileReader("D:\\SEM8\\IR\\IR_Mini_Project[Yash]\\src\\ir_mini_project\\stopwords.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StopWordsRemoval.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(inputStream);
        String str = "";
        try {
            while ((str = br.readLine()) != null) {
                StopWords.put(str, Boolean.TRUE);
            }
        } catch (IOException ex) {
            Logger.getLogger(StopWordsRemoval.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(StopWordsRemoval.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isStopWord(String Str)
    {
        if( StopWords.containsKey(Str) == Boolean.TRUE)
            return true;
        return false;
    }
}
