package com.sanchez.david.mysong.resouces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class HttpManager {
	//Attributes
	private final static String GRACE_USER = "40067785806581441-FBB6A302A3C0AE8667E329F268F1F55F";
	private final static String GRACE_ID = "82148243-843F858AAFCEB983BAB55C90F5E12963";
	
	public static String getData(String uri, String title, String artist){
		OutputStream out = null;
		BufferedReader in = null;
		HttpURLConnection connection = null;
		String data = "";
		String auth =  "<QUERIES>\n\t<LANG>eng</LANG>\n\t<AUTH>\n\t\t<CLIENT>"+GRACE_ID+"</CLIENT>\n\t\t<USER>"+GRACE_USER+"</USER>\n\t</AUTH>";
		String query = "\n\t<QUERY CMD=\"ALBUM_SEARCH\">\n\t\t<TEXT TYPE=\"ARTIST\">"+artist+"</TEXT>\n\t\t<TEXT TYPE=\"TRACK_TITLE\">"+title+"</TEXT>"
				+ "\n\t\t<MODE>SINGLE_BEST_COVER</MODE>\n\t\t<OPTION>\n\t\t\t<PARAMETER>COVER_SIZE</PARAMETER>\n\t\t\t<VALUE>MEDIUM</VALUE>\n\t\t</OPTION>\n\t</QUERY>\n</QUERIES>";
		String body = auth + query;
		try {
			URL url = new URL(uri);
			connection = (HttpURLConnection) url.openConnection();
			connection.setAllowUserInteraction(true);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");
			out = new BufferedOutputStream(connection.getOutputStream());
			out.write(body.getBytes());
			out.flush();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null){
				data += line + "\n";
			}
			
		} catch (MalformedURLException ex){
			data = "ERROR 001. Malformed URL Exception";
		} catch (IOException ex){
			data = "ERROR 002. IO Exception received when try to open/send connection/data";
		} finally{
			try{
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
				if(connection != null){
					connection.disconnect();
				}
			} catch (IOException ex){
				data = "ERROR 003. IO Exception received when try to close connection";
			}
		}
		
		return data;
	}

}
