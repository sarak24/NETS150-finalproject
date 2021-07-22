# NETS150-finalproject
Names: Kelly Chen, Sara Kenefick, Rebekah Varghese

Description: Scaping the headlines and subheadings of opinion columns from The Daily Pennsylvanian, 
we counted the frequency of articles containing words related to mental health published each year 
from 2014-2020. From this data we hoped to conclude that the discussion around mental health and 
mental disorders at Penn has increased in the recent years. Additional analysis pointed to more 
conversation about this subject during high-stress time frames (i.e. midterms/finals). Finally, 
comparing the cosine similarities between pairs of articles indicated similar topics rather than the
same author contributed to higher cosine similarity. 

Categories: physical networks, information networks, document search

Work Breakdown:
Sara- wrote the java program to retrieve the articles from The Daily Pennsylvanian. This step 
involved crawling the web page, scraping the headlines/subheadings, and extracting the desired 
information (i.e. title, date, caption, url). I implemented JSoup to parse the Document, and I used 
regex to only add the relevant articles to the array list.

Rebekah - Used articles collected by Sara to implement the Updates class, which created TreeMaps for
each of the 3 categories (date, month, year) and updated their frequencies appropriately. Created 
all of the graphs in Excel and wrote the hypotheses section as well as hypothesis section 2 of the 
report.

Kelly - Used the articles collected by Sara and extracted the article text using JSoup/Regex, and 
wrote each of the articles to a .txt file to be used in the Vector Space Model. Altered the Vector 
Space Model in order to compare each of the written .txt files and rank their cosine similarities. 
Wrote the hypothesis 3 section of the report and provided the data for Table 1.

Running the code: Our Main method contains all three of our parts: (1) extracting the relevant 
articles, (2) updating the frequencies for the graphs, and (3) using the vector space model. The 
print statements for (1) and (3) are currently commented out, but can be uncommented to view the
data that they print. 
