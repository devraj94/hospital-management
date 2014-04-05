package in.solbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends Activity{

	EditText email,password;
	ImageView signup;
	Context mContext;
	ProgressDialog progress;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext=this.getApplicationContext();
		email=(EditText)findViewById(R.id.loginpage_edittext_name);
		password=(EditText)findViewById(R.id.loginpage_edittext_password);
		signup=(ImageView)findViewById(R.id.loginpage_loginbutton);
		progress=new ProgressDialog(this);
		signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(email.getText().toString().compareTo("")==0 || password.getText().toString().compareTo("")==0){
					Toast.makeText(mContext, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
				}else{
					progress.setMessage("Logging in ... ");
				      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				      progress.setIndeterminate(true);
				      progress.show();
		        new DownloadWebpageTask2(Login.this).execute("http://home.iitj.ac.in/~kuchanamaharsh/bus_app/inst_1.txt");
				}
			}
		});
	}
	
	public void showresults(String result,String email){
		if(result.contains("yes")){
			Intent myintent=new Intent(this,MainActivity.class);
			myintent.putExtra("email", email);
			startActivity(myintent);
		}else{
		Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
		progress.cancel();
		}
	}
}
class DownloadWebpageTask2 extends AsyncTask<String, Void, String> {

    Context myContext= Splash.mContext;
    Login j=null;

	String result = "";
	EditText email,pass;
	String emailer="";
    DownloadWebpageTask2(Login m){
        j=m;
        email=j.email;
        pass=j.password;
    }



    @Override
    protected String doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private String downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
        if(hasActiveInternetConnection(myContext)==true) {
        	//the year data to send
        	email=j.email;
        	pass=j.password;
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        emailer=email.getText().toString();
        	        HttpPost httppost = new HttpPost("http://home.iitj.ac.in/~kuchanamaharsh/solbox/login.php?email="+emailer+"&pass="+pass.getText().toString());
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
        	                sb.append(line + "\n");
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
    	j.showresults(result,emailer);
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
