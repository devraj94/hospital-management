package in.solbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Doctor extends Activity implements DatePicker.OnDateChangedListener{
	String email="",selfemail="",name_doc="",special_doc="";
	TextView name,special,slot1text,slot2text,slot3text;
	ImageView appoint;
	RadioButton slot1,slot2,slot3;
	String date="";
	String sent="";
	ProgressDialog progress,progress_set;
	Calendar cal=null;
	public TextView tvDisplayDate;
	private DatePicker dpResult;
 
	int one=0,two=0,three=0;
	private int year;
	private int month;
	private int day;
 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_profile);
		Bundle incoming=this.getIntent().getExtras();
		email=incoming.getString("email");
		selfemail=incoming.getString("selfemail");
		name_doc=incoming.getString("name");
		special_doc=incoming.getString("special");
		cal=Calendar.getInstance();
		name=(TextView)findViewById(R.id.others_profile_name);
		special=(TextView)findViewById(R.id.others_oneline_description);
		appoint=(ImageView)findViewById(R.id.others_connect_button);
		slot1=(RadioButton)findViewById(R.id.radio_slot1);
		slot2=(RadioButton)findViewById(R.id.radioslot2);
		slot3=(RadioButton)findViewById(R.id.radioslot3);
		slot1text=(TextView)findViewById(R.id.slot1_text);
		slot2text=(TextView)findViewById(R.id.slot2_text);
		slot3text=(TextView)findViewById(R.id.slot3_text);
		java.util.Date d=cal.getTime();
		 
		tvDisplayDate = (TextView) findViewById(R.id.date_text);
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		tvDisplayDate.setText(String.valueOf(d.getDate())+"/"+String.valueOf(d.getMonth()+1)+"/"+String.valueOf(d.getYear()+1900));
		dpResult.init(dpResult.getYear(),dpResult.getMonth(),dpResult.getDayOfMonth(),this);
		name.setText(name_doc);
		special.setText(special_doc);
		 progress=new ProgressDialog(this);
			progress.setMessage("Loading results ... ");
		      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		      progress.setIndeterminate(true);
		      progress.show();
		new Asynctask_getavailslots(this).execute("");
		
		appoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if((!slot1.isChecked() && one==0) && (!slot2.isChecked() && two==0) && (!slot3.isChecked() && three==0) ){
					Toast.makeText(Doctor.this.getApplicationContext(), "Please select atleast one slot", Toast.LENGTH_LONG).show();
				}else if(one==1 && two==1 & three==1){
					Toast.makeText(Doctor.this.getApplicationContext(), "Please select another date", Toast.LENGTH_LONG).show();
				}else{
					sent="";
					if(one ==0 && slot1.isChecked()){
						sent=sent+"1";
					}
					if(two==0 && slot2.isChecked()){
						sent=sent+"2";
					}
					if(three==0 && slot3.isChecked()){
						sent=sent+"3";
					}
					progress_set=new ProgressDialog(Doctor.this);
					progress_set.setMessage("Requesting ... ");
				      progress_set.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				      progress_set.setIndeterminate(true);
				      progress_set.show();
					new Asynctask_setslot(Doctor.this).execute("");
					progress.show();
					new Asynctask_getavailslots(Doctor.this).execute("");
				}
			}
		});
		
	}
	public void updateslots(String result) {
		// TODO Auto-generated method stub
		progress.cancel();
		if(!result.contains("1")){

			slot1.setClickable(false);
			slot1text.setText("Slot1 Taken");
			one=1;
		}
		if(!result.contains("2")){
			slot2.setClickable(false);
			slot2text.setText("Slot2 Taken");
			two=1;
			
		}
		if(!result.contains("3")){
			slot3.setClickable(false);
			slot3text.setText("Slot3 Taken");
			three=1;
		}
	}
	@Override
	public void onDateChanged(DatePicker view, int selectedYear, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		year = selectedYear;
		month = monthOfYear;
		day = dayOfMonth;

		cal.set(year,monthOfYear, dayOfMonth);
		java.util.Date d=cal.getTime();
		tvDisplayDate.setText(String.valueOf(d.getDate())+"/"+String.valueOf(d.getMonth()+1)+"/"+String.valueOf(d.getYear()+1900));

		progress.show();
		new Asynctask_getavailslots(this).execute("");
	}
	public void showres(String result) {
		// TODO Auto-generated method stub
		progress_set.cancel();
		Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_LONG).show();
	}
}


class Asynctask_getavailslots extends AsyncTask<String, Void, String> {

    Context myContext;
    Doctor j=null;
    String result="";
    Asynctask_getavailslots(Doctor m){
        j=m;
        myContext=m.getApplicationContext();
    }



    @Override
    protected String doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private String downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
        if(hasActiveInternetConnection(myContext)==true) {
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://home.iitj.ac.in/~kuchanamaharsh/solbox/getavailslots.php?email="+j.email+"&date="+j.tvDisplayDate.getText().toString());
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
        	                sb.append(line);
        	        }
        	        is.close();
        	 
        	        result=sb.toString();
        	        
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
        return result;
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
    	j.updateslots(result);
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

class Asynctask_setslot extends AsyncTask<String, Void, String> {

    Context myContext;
    Doctor j=null;
    String result="";
    Asynctask_setslot(Doctor m){
        j=m;
        myContext=m.getApplicationContext();
    }



    @Override
    protected String doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private String downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
        if(hasActiveInternetConnection(myContext)==true) {
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://"+fullpath+"/setslot.php?email="+j.email+"&date="+j.tvDisplayDate.getText().toString()+"&self="+j.selfemail+"&str="+j.sent);
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
        	                sb.append(line);
        	        }
        	        is.close();
        	 
        	        result=sb.toString();
        	        
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
        return result;
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
    	j.showres(result);
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
