import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataCollector {
	
	private static String mailContent = "<html><head><style type=\"text/css\">.title_banner{background-color: DodgerBlue;color: white;padding-top: 1px;padding-bottom: 1px;text-align:center;text-shadow: 1px 1px 2px black;}.footer{background-color: Orange;color: black;padding-top: 1px;padding-bottom: 1px;text-align: center;}</style></head><body><div class=\"title_banner\">";
	
	public static void CoronaBangladesh() throws Exception
	{
		String url_IEDCR = "https://www.iedcr.gov.bd";
	    Document documentBd = Jsoup.connect(url_IEDCR).get();
		System.out.println("## COVID-19 Status Bangladesh");
	      System.out.println();
	      mailContent += "<h4>## COVID-19 Status <i>Bangladesh</i></h4>"+"<ul>";
	      for(Element table : documentBd.select("table"))
	      {
	    	  for(Element table_row:table.select("tr"))
	    	  {
	    		  Elements dataElements = table_row.select("td");
	    		  if(dataElements.size()==4) {
	    			  System.out.println("\t"+dataElements.get(0).text()+" : "+dataElements.get(3).text());
	    		  mailContent += "<li>"+dataElements.get(0).text()+" : "+dataElements.get(3).text()+"</li>";
	    		  }
	    	  }
	      }
	      mailContent += "</ul><br>";
	}

	public static void CoronaWorld() throws Exception
	{
		Date date = new Date();
		String url_webmeater = "https://www.worldometers.info/coronavirus/";
	      Document documentWorld = Jsoup.connect(url_webmeater).get();
	      Elements dataElements = documentWorld.getElementsByClass("maincounter-number");
	      System.out.println("## COVID-19 Coronavirus Pandemic Update World on "+date.toString()+"\n");
	      System.out.println("\tCoronavirus Cases : "+dataElements.get(0).text());
	      System.out.println("\tTotal Deaths : "+dataElements.get(1).text());
	      System.out.println("\tTotal Recovered : "+dataElements.get(2).text());
	      
	      mailContent += "<h4>## COVID-19 Coronavirus Pandemic Update <i>World</i> on "+date.toString()+"</h4><ul>";
	      mailContent += "<li>Coronavirus Cases : "+dataElements.get(0).text()+"</li>";
	      mailContent += "<li>Total Deaths : "+dataElements.get(1).text()+"</li>";
	      mailContent += "<li>Total Recovered : "+dataElements.get(2).text()+"</li></ul><br>";
	}
	
	public static void Initialize() throws IOException
	{
   
		System.out.println("COVID-19 Update");
		System.out.println("Date : "+LocalDate.now());
		System.out.println("\n\n");
		
		mailContent += "<h1>"+"COVID-19 Update"+"</h1>"+"<h3>"+"Date : "+LocalDate.now()+"</h3>"+"<br>"+"<br></div>";
		
		try {
			CoronaBangladesh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println();
		try {
			CoronaWorld();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailContent += "<div class=\"footer\"><i>N:B: Those data are automaticaly collected from IEDCR and WORLDMETERS website. This mail is automaticaly  generated and sent by a java program. Thank you. Stay home, stay safe.</i></div></body></html>";
   }
	public String getMailContent() throws Exception
	{
		Initialize();
		return mailContent;
	}
}