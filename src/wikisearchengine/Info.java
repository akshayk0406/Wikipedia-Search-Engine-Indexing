/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wikisearchengine;

/**
 *
 * @author akshay
 */
public class Info
{
    int docId;
    int Frequency;
    int Mask;

    public Info()
    {
        Mask=0;
        Frequency=0;
    }
    public int getDocID()
    {
        return docId;
    }
    public int getFrequency()
    {
        return Frequency;
    }
    public int getMask()
    {
        return Mask;
    }
    public void setDocID(int inp)
    {
        docId = inp;
    }
    public void setFrequency(int inp)
    {
        Frequency = inp;
    }
    public void setCategory()
    {
        Mask = Mask | (1<<1);
    }
    public void setTitle()
    {
        Mask = Mask | (1<<0);
        
    }
    public void setContent()
    {
        Mask = Mask | (1<<4);
    }
    public void setInfoBox()
    {
        Mask = Mask | (1<<3);
    }
    public void setOutLink()
    {
        Mask = Mask | (1<<2);
    }
    public int getCategory()
    {
        return Mask&(1<<1);
    }
    public int getTitle()
    {
        return Mask&(1<<0);
    }
    public int getContent()
    {
        return Mask&(1<<4);
    }
    public int getInfoBox()
    {
        return Mask&(1<<3);
    }
    public int getOutLink()
    {
        return Mask&(1<<2);
    }
}
