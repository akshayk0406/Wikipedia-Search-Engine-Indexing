package wikisearchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser
{
    Indexer index = new Indexer();;
    int DocumentId        = 0 ;
    String DocumentTitle="";
    static int DocumentProcessed = 0;
    static int FileNo            = 1;
    public static final Pattern pPattern = Pattern.compile("[A-Za-z]+",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p1 = Pattern.compile("\\[\\[Category:(.*?)\\]\\]", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p2 = Pattern.compile("\\{\\{[a-z_]*?box_?(.*?)\\|.*?\\}\\}",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p3 = Pattern.compile("(\\[http:)(.*?) (.*?)(\\])",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p4 = Pattern.compile("\\{\\{cite(.*?)\\}\\}",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p5 = Pattern.compile("\\{\\{citation(.*?)\\}\\}",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p6 = Pattern.compile("(&lt;|<)(ref)(.*?)(/ref)(&gt;|>)",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p7 = Pattern.compile("(&lt;|<)(.*?)(/?)(&gt;|>)",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p8 = Pattern.compile("\\b(httpd?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern p9 = Pattern.compile("\\[\\[(.*?)\\]\\]",Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    Matcher Match;
    int Words=0;
    public String Modify(String str)
    {
        String Category = extractCategory(str);   
        String Links    = extractExternalLink(Category);
        String cites    = extractcitation(Links);
        String Cites    = extractOutLinks(cites);
        String Tags     = extractTags(Cites);
        String result   = extractURLinks(Tags);
        String Output   = extractInfoBox(result);
        return Output;
    }
    public void Process(Document Doc) throws FileNotFoundException, IOException
    {
        Words=0;
        Doc.setWordCount(0);
        DocumentId = Doc.getDocId();
        DocumentTitle=Doc.getTitle().trim();
        int x=0;
        Match = pPattern.matcher(Doc.getTitle());
        while(Match.find())
        {
            index.Index(Match.group(), 1, DocumentId);
            Words++;
        }
        Doc.setContent(Modify(Doc.getContent()));
        String res = Doc.getContent();
        int y = 0;
        Match = pPattern.matcher(res);
        y=0;
        while(Match.find())
        {
            index.Index(Match.group(), 5, DocumentId);
            Words++;
        }
        Doc.setWordCount(Words);
        index.ProcessDocument(Doc);
        ///////////DOCUMENT FULLY INDEXED
        DocumentProcessed = DocumentProcessed+1;
        if(DocumentProcessed%2500==0)
        {
            DocumentProcessed=0;
            index.WriteInfo(FileNo);
            FileNo = FileNo+1;
        }
    }

    private String extractCategory(String str)
    {
	Matcher match = p1.matcher(str);
        String res = "";
	while ( match.find() )
        {
            res = match.group(1).toString();
            Match = pPattern.matcher(res);
            while(Match.find())
            {
                index.Index(Match.group(),2,DocumentId);
                Words++;
            }
        }
        return match.replaceAll("");
    }
    private String extractInfoBox(String str)
    {
	Matcher match = p2.matcher(str);
        String res = "";
	while ( match.find() )
        {
            res = match.group(1).toString();
            Match = pPattern.matcher(res);
            while(Match.find())
            {
                index.Index(Match.group(),4,DocumentId);
                Words++;
            }
        }
        return match.replaceAll("");
    }

    private String extractExternalLink(String Category)
    {
        Matcher match = p3.matcher(Category);
        String res="";
        while(match.find())
        {
            res = match.group(3).toString();
            Match = pPattern.matcher(res);
            while(Match.find())
            {
                index.Index(Match.group(),5,DocumentId);
                Words++;
            }
        }
        return match.replaceAll("");
    }

    private String extractcitation(String Links)
    {
        Matcher match = p4.matcher(Links);
        Links = match.replaceAll("");
        Matcher Mmatch = p5.matcher(Links);
        return Mmatch.replaceAll("");
    }

    private String extractTags(String Cites)
    {
        Matcher match = p6.matcher(Cites);
        Cites = match.replaceAll("");
        Matcher Mmatch = p7.matcher(Cites);
        return Mmatch.replaceAll("");
    }
    private String extractURLinks(String Tags)
    {
        Matcher match = p8.matcher(Tags);
        return match.replaceAll("");
    }
    private String extractOutLinks(String cites)
    {
        Matcher match = p9.matcher(cites);
        String res = "";
        while( match.find() )
        {
            res = match.group(1).toString();
            Match = pPattern.matcher(res);
            while(Match.find())
            {
                index.Index(Match.group(),3,DocumentId);
                Words++;
            }
        }
        return match.replaceAll("");
    }
    void writeIndex() throws FileNotFoundException, IOException
    {
        index = new Indexer();
        index.WriteInfo(FileNo);
    }
}
