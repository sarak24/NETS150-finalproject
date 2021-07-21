import java.util.ArrayList;
import java.util.HashMap;

public class Updates {
    
    private HashMap<Integer, Integer> mapMonth;
    private HashMap<Integer, Integer> mapYear;
    private HashMap<Integer, Integer> mapDate;
    private ArrayList<Article> articles;
    
    public Updates(ArrayList<Article> articles) {
        this.articles = articles;
        mapMonth = new HashMap<Integer, Integer>();
        mapYear = new HashMap<Integer, Integer>();
        mapDate = new HashMap<Integer, Integer>();
    }
    
    public HashMap<Integer, Integer> updateDates() {
        for (Article article : articles) {
            int date = article.getMonth() * 10000 + 2000 + article.getYear();
            if (mapDate.containsKey(date)) {
                int freq = mapDate.get(date);
                mapDate.put(date, freq + 1);
            } else {
                mapDate.put(date, 1);
            }
        }
        return mapDate;
    }
    
    public HashMap<Integer, Integer> updateMonths() {
        for (Article article : articles) {
            int month = article.getMonth();
            if (mapMonth.containsKey(month)) {
                int freq = mapMonth.get(month);
                mapMonth.put(month, freq + 1);
            } else {
                mapMonth.put(month, 1);
            }
        }
        return mapMonth;
    }
    
    public HashMap<Integer, Integer> updateYears() {
        for (Article article : articles) {
            int year = article.getYear();
            if (mapYear.containsKey(year)) {
                int freq = mapYear.get(year);
                mapYear.put(year, freq + 1);
            } else {
                mapYear.put(year, 1);
            }
        }
        return mapYear;
    }

}