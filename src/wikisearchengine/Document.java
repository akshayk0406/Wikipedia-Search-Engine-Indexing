/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

/**
 *
 * @author akshay
 */
public class Document
{
    int docId;
    String Title;
    String Content;
    int WordCount;
    public int getDocId()
    {
        return docId;
    }
    public int getWordCount()
    {
        return WordCount;
    }
    public void setWordCount(int p)
    {
        WordCount = p;
    }
    public String getTitle()
    {
        return Title;
    }
    public String getContent()
    {
        return Content;
    }
    public void setDocId(int Id)
    {
        docId = Id;
    }
    public void setTitle(String title)
    {
        Title = title;
    }
    public void setContent( String content )
    {
        Content = content;
    }
}
