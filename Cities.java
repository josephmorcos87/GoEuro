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
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter("cities.csv");
		String jsonText  = readJson( bufferRead.readLine());
		String [] cities = jsonText.split("_id\":");
	      if(cities.length>1)
	      {
	    	  for(int i = 1;i<cities.length;i++)
	    	  {
	    		  String city = getCityCSV(cities[i]);
	    		  out.println(city);
	    		  System.out.println(city);
	    	  }
	      }
	      out.close();
    } 
	
	 public static String readJson(String cityName)throws IOException
	 {
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
		if(!cityJson.contains(attributeName))
			return "";
		String value = "";
		if(isString)
		{
			 value = cityJson.split("\""+attributeName+"\":\"")[1].split("\"")[0];
		}
		else
		{
			value = cityJson.split("\""+attributeName+"\":")[1].split(",|}")[0];
		}
		
		if(value.equals("null"))
			return "";
					
		return value;
	}
}
