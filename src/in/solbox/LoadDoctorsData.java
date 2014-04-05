package in.solbox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import in.solbox.adapter.ListAdapter;

public class LoadDoctorsData extends AsyncTask<String, Void, ArrayList<Entry_people>> {

	Context myContext;
	DoctorsFragment HF;
   String mUrl="";
	ListAdapter mAdapter;
    LoadDoctorsData(ListAdapter m,Context hai,DoctorsFragment h){
        mAdapter=m;
        myContext=hai;
        HF=h;
    }



    @Override
    protected ArrayList<Entry_people> doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private ArrayList<Entry_people> downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
    	mUrl=myUrl;
    	ArrayList<Entry_people> entries=new ArrayList<Entry_people>();
        if(hasActiveInternetConnection(myContext)==true) {
        	//the year data to send
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost(mUrl);
        	        HttpResponse response = httpclient.execute(httppost);
        	        HttpEntity entity = response.getEntity();
        	        is= entity.getContent();
        	}catch(Exception e){
        	        Log.e("log_tag", "Error in http connection "+e.toString());
        	}
        	//convert response to string
        	try{
        	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
        	        StringBuilder sb = new StringBuilder();
        	        String line = null;
        	        while ((line = reader.readLine()) != null) {
        	        	 if(line.compareTo("")!=0){
        	                Entry_people ent=new Entry_people();
        	                ent.email=line.toString();
        	                line = reader.readLine();
        	                ent.name=line.toString();
        	                line = reader.readLine();
        	                ent.speciality=line.toString();
        	                entries.add(ent);
        	                }
        	        }
        	        is.close();
        	        
        	}catch(Exception e){
        	        Log.e("log_tag", "Error converting result "+e.toString());
        	}
        	 
        	//parse json data
        /*	try{
        	        JSONArray jArray = new JSONArray(result);
        	        for(int i=0;i<jArray.length();i++){
        	                JSONObject json_data = jArray.getJSONObject(i);
        	               System.out.println(json_data.getString("name"));
        	                
        	        }
        	}
        	catch(JSONException e){
        	        Log.e("log_tag", "Error parsing data "+e.toString());
        	} */
        }
        return entries;
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(ArrayList<Entry_people> entries) {
    	mAdapter.upDateEntries(entries); 
    	HF.closeprogress();
    }

    public static boolean hasActiveInternetConnection(Context myContext) {
        if (isNetworkAvailable(myContext)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://home.iitj.ac.in/~kuchanamaharsh/bus_app/").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
        }
        return false;
    }

    private static boolean isNetworkAvailable(Context myContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

