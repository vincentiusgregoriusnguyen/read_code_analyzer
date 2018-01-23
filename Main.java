import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Main {
	
	public static void main(String[] args){
		try {
			group("nhshcdpp.csv","nhshcdpp_grouped.csv");
			countByStudy("nhshcdpp.csv","nhshcdpp_count.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");
	}
	
	public static void countByStudy(String in, String out) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(in), ',' , '"' , 1);
		
		HashMap<String,Integer> hmap = new HashMap<String,Integer>();
	    String[] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	         if (nextLine != null) {
	        	 if (!hmap.containsKey(nextLine[0])) {
	                 hmap.put(nextLine[0], 1);
	             } else {
	                 hmap.put(nextLine[0], (Integer) hmap.get(nextLine[0]) + 1);
	             }
	         }
	      }
	    CSVWriter writer = new CSVWriter(new FileWriter(out));
	    for (String word : hmap.keySet()){
	    	String[] record = new String[2];
	    	record[0] = word;
	    	record[1] = String.valueOf(hmap.get(word));
	    	writer.writeNext(record);
        }
	    writer.close();
	}
	
	
	
	public static void group(String in, String out) throws IOException{
	     CSVReader reader = new CSVReader(new FileReader(in), ',' , '"' , 1);
	       
	     MultiMap hmap = new MultiValueMap();
	     HashMap<String,String> caliber = new HashMap<String,String>();
	     HashMap<String,String> codeDescription = new HashMap<String,String>();
	     
	      //Read CSV line by line and use the string array as you want
	      String[] nextLine;
	      while ((nextLine = reader.readNext()) != null) {
	         if (nextLine != null) {
	            //Verifying the read data here
	            hmap.put(nextLine[1],nextLine[0]);
	            codeDescription.put(nextLine[1], nextLine[2]);
	         }
	      }
	      
	      reader.close();
	      reader = new CSVReader(new FileReader("caliber.csv"), ',' , '"' , 1);
	      while ((nextLine = reader.readNext()) != null) {
		         if (nextLine != null) {
		            //Verifying the read data here
		            caliber.put(nextLine[0],nextLine[1]);
		         }
		  }
	      
	      
	      Set<String> keys = hmap.keySet();
	      CSVWriter writer = new CSVWriter(new FileWriter(out));

	      HashMap<String,Integer> countingMap = new HashMap<String,Integer>();
	       for (String key : keys) {
	    	  ArrayList<String> studies= (ArrayList<String>)  hmap.get(key);
	    	  ArrayList<String> temp = new ArrayList<String>();	 
	    	  
	    	  for(String study: studies){
	    		  if(!temp.contains(study)){
		    		  temp.add(study); 
	    		  }
	    	  }
	    	  
	    	  String description = caliber.get(key);
	    	  if(description == null){
	    		  description = codeDescription.get(key);
	    	  }
	    	  
	    	  temp.add(0,description);
	    	  
	    	  temp.add(0,key);
	    	  temp.add(0,String.valueOf(temp.size() - 2));
	    	  
	    	  
	    	  String[] record = temp.toArray(new String[temp.size()]);
	    	  writer.writeNext(record);
	       }
	       
	       writer.close();
	       	       
	       System.out.println("Done");
	}

}
