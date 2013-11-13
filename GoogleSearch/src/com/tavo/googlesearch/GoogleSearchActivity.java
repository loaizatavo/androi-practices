package com.tavo.googlesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GoogleSearchActivity extends Activity {
	private TextView tv1;
	private EditText ed1;
	private Button bt1;
	static String url = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_search);
		
		tv1 = (TextView)findViewById(R.id.display);
		ed1 = (EditText)findViewById(R.id.editText);
		bt1 = (Button)findViewById(R.id.submit);
		
		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ed1.getText().toString() != null) {
						try {
							searchRequest(ed1.getText().toString());
						}
						catch (Exception e) {
							Log.v("Exception Google search", "Exception: "+e.getMessage());
						}
				}
				ed1.setText("");
			}
		});
	}
	
	public void searchRequest(String searchString) throws MalformedURLException, IOException {
		String newFeed = url + searchString;
		Log.v("gsearch", "gsearch url: "+newFeed);
		  
		URL searchUrl = new URL(newFeed);
		String result = new googleSearch().execute(searchUrl).toString();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_search, menu);
		return true;
	}
	
	private class googleSearch extends AsyncTask<URL, Void, String> {
	     protected void onProgressUpdate(Integer... progress) {
	     
	     }

	     protected void onPostExecute(String result) {
	    	 // esto se ejecuta cuando termina la tarea
	    	 Log.v("gsearch", "el resultado es: " + result);
	    	 tv1.setText(result);
	     }

		@Override
		protected String doInBackground(URL... params) {
			StringBuilder response = new StringBuilder();
			URL newUrl = (URL)params[0];
			try {
				HttpURLConnection httpconn = (HttpURLConnection)newUrl.openConnection();
				httpconn.getResponseCode();
						
				if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader input = new BufferedReader(
												new InputStreamReader(httpconn.getInputStream()), 8192);
					
					String strLine = null;
					/*while ((strLine = input.readLine().toString()) != null) {
						response.append(strLine);
					}*/
					response.append(input.readLine());
					input.close();
				}
			}
			catch (IOException e) {
				Log.d("gsearch", "error");
			}
			
			String result = "";
			try {
				result = procesResponse(response.toString()).toString();
			} catch (IOException e) {
				Log.d("gsearch", "error");
			} catch (JSONException e) {
				Log.d("gsearch", "error");
			} catch (NoSuchAlgorithmException e) {
				Log.d("gsearch", "error");
			}
			
			return result;
		}
		
		private String procesResponse(String resp) 
				throws IllegalStateException, IOException, JSONException, NoSuchAlgorithmException {
			
			StringBuilder sb = new StringBuilder();
			Log.v("gsearch", "gsearch result: " + resp);
			
			JSONObject mResponseObject = new JSONObject(resp);
			
			JSONObject responseObject = mResponseObject.getJSONObject("responseData");
			
			JSONArray array = responseObject.getJSONArray("results");
			Log.v("gsearch", "gsearch number of results: "+ array.length());
			
			for (int i = 0; i < array.length(); i++) {
				Log.v("gsearch", i + "]" + array.get(i).toString());
				String title = array.getJSONObject(i).getString("title");
				String urlLink = array.getJSONObject(i).getString("visibleUrl");
				
				sb.append(title);
				sb.append("\n");
				sb.append(urlLink);
				sb.append("\n");
			}
			
			return sb.toString();
		}
	 }


}
