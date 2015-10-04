import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;

public class Cities {
	
	public static void main (String args[])  throws IOException
    	{ 
		PrintWriter out = new PrintWriter("cities.csv");
		if(args.length>0){
			String jsonText  = readJson( args[0]);
			//Each id attribute represents a new City
			String [] cities = jsonText.split("_id\":");
			// if array is not empty go get CSV for each city
			if(cities.length>1)
			{
				for(int i = 1;i<cities.length;i++)
	    	  		{
					String city = getCityCSV(cities[i]);
					out.println(city);
					System.out.println(city);
	    	  		}
			}
	    	}
	    	out.close();
	  } 
	
	 public static String readJson(String cityName)throws IOException
	 {
		 //Takes City Name queries URL and returns Json String
		 InputStream is = new URL("http://api.goeuro.com/api/v2/position/suggest/en/"+cityName).openStream();
		 try 
		 {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      return rd.readLine();
		 }
		 catch(Exception e)
		 {
			 return "";
		 }
		 finally 
		 {
		      is.close();
		 }
	 }
	
	 public static String getCityCSV(String cityJson)
	 {
		//Takes one city from Json array and returns it as a comma seperated value
		String CityCSV = "";
		String [] cityAttributes = cityJson.split(",");
		
		CityCSV+= cityAttributes[0]+",";
		CityCSV+= getAttributeValue("name",true,cityJson) + ",";
		CityCSV+= getAttributeValue("type",true,cityJson)+",";
		CityCSV+= getAttributeValue("latitude",false,cityJson)+",";
		CityCSV+= getAttributeValue("longitude",false,cityJson);		
		
		return CityCSV;
	 }
	
	public static String getAttributeValue(String attributeName, boolean isString, String cityJson)
	{
		//Gets the value of a specific attribute from one city in json
		
		//if attribute doesn't exist return empty string
		if(!cityJson.contains(attributeName))
			return "";
		
		String value = "";
		//String attributes and numeric attributes are treated differently
		if(isString)
		{
			 value = cityJson.split("\""+attributeName+"\":\"")[1].split("\"")[0];
		}
		else
		{
			value = cityJson.split("\""+attributeName+"\":")[1].split(",|}")[0];
		}
		//if attribute value is null return an empty string
		if(value.equals("null"))
			return "";
					
		return value;
	}
}
