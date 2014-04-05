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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity{
	Context mContext;
	EditText name,email,password;
	ImageView signup;
	ImageView signuplinkedin;
	Register reg;
	Handler myOffMainThreadHandler;
	ProgressDialog progress;
	WebView wv;
	AlertDialog.Builder alert;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        mContext = this.getApplicationContext();
        reg=this;
         myOffMainThreadHandler = new Handler();
        name=(EditText)findViewById(R.id.register_edittext_name);
        email=(EditText)findViewById(R.id.register_edittext_email);
        password=(EditText)findViewById(R.id.register_edittext_password);
        signup=(ImageView)findViewById(R.id.register_signup_button);
      //  signuplinkedin=(ImageView)findViewById(R.id.register_signup_linkedin_button);
        progress=new ProgressDialog(this);
    /*  alert = new AlertDialog.Builder(this); 
        alert.setTitle("NetEasy");

         wv = new WebView(this);
		wv.loadUrl("https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=75jrtxyi8c4dfo&scope=r_basicprofile%20r_emailaddress&state=t6M3YiSNF7zEpd5N&redirect_uri=http://www.ignus.org/devraj/linkedinaccepted.php");

		progress.setMessage("Loading... ");
	      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	      progress.setIndeterminate(true);
	      progress.show();
		wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
            public void onPageFinished(WebView view, String url) {
			      progress.cancel();
			    }
        });

		wv.setFocusable(true);
		wv.setFocusableInTouchMode(true);
		wv.requestFocus(View.FOCUS_DOWN);
	    wv.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                    }
                    break;
            }
				return false;
			}
	    });
	    
        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent myintent=new Intent(mContext,Splash.class);
		        startActivity(myintent);
            }
        }); */
       signup.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(name.getText().toString().compareTo("")==0 || email.getText().toString().compareTo("")==0 || password.getText().toString().compareTo("")==0){
				Toast.makeText(mContext, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
			}else{

				progress.setMessage("Signing Up... ");
			      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			      progress.setIndeterminate(true);
			      progress.show();
	        new DownloadWebpageTask(reg).execute("http://home.iitj.ac.in/~kuchanamaharsh/bus_app/inst_1.txt");
			}

		}
	});
       
   /*    signuplinkedin.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//alert.show();
			Intent myintent=new Intent(mContext,Linkedin_mainNetActivity.class);
			 myintent.putExtra("url", "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=75jrtxyi8c4dfo&scope=r_basicprofile%20r_emailaddress&state=t6M3YiSNF7zEpd5N&redirect_uri=http://www.ignus.org/devraj/linkedinaccepted.php");
			 
			 startActivity(myintent);
		}
	});
    	
*/	
         }
	public void showresults(String result){
		Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
		progress.cancel();
		new Thread() {
            public void run() {


                             try {
                            	 	Thread.sleep(5000);
                            	 	Intent myintent=new Intent(reg,Splash.class);
                            		startActivity(myintent);
                             	} catch (Exception e) {
                                 
                             } 
                        
              }
          }.start();
		
	}
}

class DownloadWebpageTask extends AsyncTask<String, Void, String> {

    Context myContext= Splash.mContext;
    Register j=null;

	String result = "";
	EditText name,email,pass;
    DownloadWebpageTask(Register m){
        j=m;
    }



    @Override
    protected String doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private String downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
        if(hasActiveInternetConnection(myContext)==true) {
        	//the year data to send
        	name=j.name;
        	email=j.email;
        	pass=j.password;
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://"+fullpath+"register.php?email="+email.getText().toString()+"&name="+name.getText().toString()+"&pass="+pass.getText().toString());
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
    	j.showresults(result);
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
