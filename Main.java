import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static ArrayList<Article> articles = new ArrayList<Article>();
	  
	private static ArrayList<String> wordsToSearch = new ArrayList<String>();
	
	 
	public static final String URL = "https://www.thedp.com/section/columns?page=1&per_page=1500";
	
	public static final String URL2 = "https://www.thedp.com/section/columns?page=1&per_page=50";
	
	private static Document doc;
	
	public static void main(String[] args) {
		wordsToSearch.add("stress");
		wordsToSearch.add("depression");
	    wordsToSearch.add("depressed");
	    wordsToSearch.add("anxiety");
	    wordsToSearch.add("anxious");
	    wordsToSearch.add("mental health");
	    wordsToSearch.add("caps");
	    wordsToSearch.add("ocd");
	    wordsToSearch.add("therapy");
	    wordsToSearch.add("self-care");
	    wordsToSearch.add("self-love");
	    wordsToSearch.add("eating disorder");
	    wordsToSearch.add("eating disorders");
	    wordsToSearch.add("penn face");
	    wordsToSearch.add("wellness");
	    
	    // array list with all articles scraped from webpage
	    ArrayList<Article> allArticles = new ArrayList<Article>();
	    
	    try {
	    	doc = Jsoup.connect(URL).get();
	    } catch (IOException e) {
	        System.out.println("Something went wrong");
	    }
	    
	    Element mainSection = doc.select("div.col-md-8").first();
	    Elements articleSections = mainSection.select("div.row");
//	    System.out.println(articleSections.size());
	    
	    for (Element e: articleSections) {
	    	int month = 0;
	    	int year = 0;
	    	if (e.select("div.timestamp").hasText()) {
	    		String s = e.select("div.timestamp").text();
	    		String template = "(\\d{2})/(\\d{2})/(\\d{2})";
	    		Pattern p = Pattern.compile(template);
	    		Matcher m = p.matcher(s);
	    		if (m.find()) {
	    			String monthString = m.group(1);
	    			month = Integer.parseInt(monthString);
	    			String yearString = m.group(3);
	    			year = Integer.parseInt(yearString);
	    		} else {
	    			// article will not be added since no date was found
	    			continue;
	    		}
	    	}
	    	String url = "";
	    	String title = "";
	    	if (!e.select("h3.standard-link").isEmpty()) {
	    		Element h3 = e.select("h3.standard-link").first();
	    		if (!h3.select("a").isEmpty()) {
	    			Element a = h3.select("a").first();
	    			url = a.attr("href");
	    		} else {
	    			// article will not be added since no link was found
	    			continue;
	    		}
	    		if (h3.hasText()) {
	    			title = h3.text().toLowerCase();
	    		} else {
	    			// article will not be added since no title was found
	    			continue;
	    		}
	    	} else {
	    		// article will not be added since no h3 was found
	    		continue;
	    	}
	    	
	    	String blurb = "";
	    	if (!e.select("div.col-md-8").isEmpty()) {
	    		Element div = e.select("div.col-md-8").first();
	    		if (div.hasText()) {
	    			blurb = div.text().toLowerCase();
	    		} else {
	    			// article will not be added since no div.col-md-8 was found
	    			continue;
	    		}
	    	} else {
	    		// article will not be added since no blurb was found
	    		continue;
	    	}
	    	
	    	Article articleToBeAdded = new Article(month, year, title, blurb, url);
	    	allArticles.add(articleToBeAdded);
	    }
	    
	    for (Article a : allArticles) {
	    	boolean addArticle = false;
	    	for (String word : wordsToSearch) {
	    		if (a.containsWord(word)) {
	    			addArticle = true;
	    			a.getContainsWords().add(word);
	    		}
	    	}
	    	if (addArticle) {
	    		articles.add(a);
	    	}
	    }
	    
	    // print to console
	    
//	    for (Article a : articles) {
//	    	System.out.println("Month: " + a.getMonth());
//	    	System.out.println("Year: " + a.getYear());
//	    	System.out.println("Title: " + a.getTitle());
//	    	System.out.println("Caption: " + a.getCaption());
//	    	System.out.println("URL: " + a.getURL());
//	    	System.out.print("Words: ");
//	    	for (String word: a.getContainsWords()) {
//	    		System.out.print(word + ", ");
//	    	}
//	    	System.out.println("");
//	    	System.out.println("***********************");
//	    }
	    
//	    System.out.println(articles.size());
	    
	    // get frequencies of articles per month and year
	    Updates a = new Updates(articles);
        HashMap<Integer, Integer> mapDates = a.updateDates();
        HashMap<Integer, Integer> mapMonths = a.updateMonths();
        HashMap<Integer, Integer> mapYears = a.updateYears();
        
        Set<Integer> dates = mapDates.keySet();
        Set<Integer> months = mapMonths.keySet();
        Set<Integer> years = mapYears.keySet();
        
        for (Integer year : years) {
            System.out.println(year + ": " + mapYears.get(year));
        }
        
        for (Integer month : months) {
            System.out.println(month + ": " + mapMonths.get(month));
        }
        
        for (Integer date : dates) {
            System.out.println(date + ": " + mapDates.get(date));
        }
	    
	    
	    // document search / vector space model
//	    ArrayList<String> articleFilenames = saveArticleContents(articles);
//	    for (String s : articleFilenames) {
//	    	System.out.println(s);
//	    }
//	    System.out.println("***********");
//	    
//	    VectorSpaceModelTester vs = new VectorSpaceModelTester(articleFilenames);
//	    vs.cosineSimilarity();
	    
	}
	
	
	/**
	 * @param articles: all articles that contained the words we were searching for
	 * @return list of filenames of txt files of the article contents
	 * 
	 * This function takes in the list of articles containing any of the keywords and uses their
	 * URLs to get the article text. Each of these articles is written to a txt file to be used in
	 * the Vector Space Model, and their filenames are returned in a list. 
	 */
	public static ArrayList<String> saveArticleContents(ArrayList<Article> articles) {
		ArrayList<String> filenames = new ArrayList<>();
		
		for (Article a : articles) {
			String url = a.getURL();
			String authorName = "";
			String template1 = "\\w+ (\\w+)";
			Pattern r1 = Pattern.compile(template1);
			Matcher m1 = r1.matcher(a.getTitle());
			if (m1.find()) {
				authorName = m1.group(1);
			}
			String filename = "" + a.getMonth() + "_" + a.getYear() + "_" + authorName + ".txt";
			System.out.println(filename);
			// if multiple articles from same author + month + year
			if (filenames.contains(filename)) {
				filename = "1_" + filename;
			} 
			filenames.add(filename);
			
			try {
		    	doc = Jsoup.connect(url).get();
		    } catch (IOException e) {
		        System.out.println("Something went wrong");
		    }
			
			Element e = doc.select("article").first();
			String text = e.text();
			
			// get rid of header/footer
			String template = "(\\d{2}\\/\\d{2}\\/\\d{2}\\s\\d{1,2}:\\d{2}\\w{2}\\s)(.*)"
					+ "(Sign up for our newsletter)?"
					+ "( \\w+ (\\w|Ò)+ is "
					+ "(a (rising )?(C|c)ollege|a Wharton|a Nursing|an Engineering))";

			Pattern r = Pattern.compile(template);
			Matcher m = r.matcher(text);
			if (m.find()) {
				text = m.group(2);
			}
			
			// write to txt files to use in vector space model
			File file = Paths.get(filename).toFile();
			BufferedWriter w = null;
			try {
				w = new BufferedWriter(new FileWriter(file));
				w.write(text);
				w.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			// delay each iteration to avoid being banned
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
		return filenames;
	}


}