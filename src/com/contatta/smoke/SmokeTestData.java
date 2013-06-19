package com.contatta.smoke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SmokeTestData {
	
	public ArrayList<List<String>> getData(String file)  { 
    	ArrayList<List<String>> test = new ArrayList<List<String>>();
    	ArrayList<String> tokens; 
    	try{
	        BufferedReader br = new BufferedReader(new FileReader(file));
	
	        String line;
	        while ((line = br.readLine()) != null)
	        {
	        	//new tokens = line.split(",",-1);
	        	//tokens.add(line.split(",",-1));
	        	StringTokenizer st = new StringTokenizer(line, ",");
	        	tokens = new ArrayList<String>();
	 		    while (st.hasMoreElements()) {
	 		    	tokens.add(st.nextToken());
	 		    }
	 		    System.out.println("tokens has " 
	 		    		+ tokens.size() 
	 		    		+ " elements");
	        	System.out.println(line + "\n");
	        	test.add(tokens);
	        	
	        }
	        
	        br.close();
	        
    	}
    	
		
        catch(IOException e){
        	System.out.println("ioexception");
        	e.printStackTrace();
        	
        }
    	
    	return test; 			
            
	}


}
