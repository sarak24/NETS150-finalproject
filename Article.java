import java.util.ArrayList;

public class Article {
    private int month;
    private int year;
    private String title;
    private String caption; 
    private String url;
    private ArrayList<String> containsWords;
    
    public Article(int month, int year, String title, String caption, String url) {
       this.month = month;
        this.year = year;
        this.title = title;
        this.caption = caption;
        this.url = url; 
        this.containsWords = new ArrayList<String>();
    }
  
    public int getMonth() {
      return this.month;
    }
  
    public int getYear() {
      return this.year;
    }
  
    public String getTitle() {
      return this.title;
    }
  
    public String getCaption() {
      return this.caption;
    }
    
    public String getURL() {
      return this.url;
    }
    
    public ArrayList<String> getContainsWords() {
        return this.containsWords;
      }
    
    public boolean containsWord(String word) {
		return title.contains(word) || caption.contains(word);
	}


}