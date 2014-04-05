package in.solbox;

import android.app.Fragment;

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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingsFragment extends Fragment {
	MainActivity mainactivity;
	public SettingsFragment(MainActivity m){
		mainactivity=m;
		}
	ImageView logout,change_pass;
	EditText old,newone;
	Context hai;
	ProgressDialog progress;
	String email;
	String oldpassword="",newpassword="";
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        change_pass=(ImageView)rootView.findViewById(R.id.change_password_button);
        old=(EditText)rootView.findViewById(R.id.old_pass_edit_text);
        newone=(EditText)rootView.findViewById(R.id.new_pass_edit_text);
        email=this.getArguments().getString("email");

        hai=this.getActivity().getApplicationContext();
        
        change_pass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String oldone,newpass;
				oldone=old.getText().toString();
				newpass=newone.getText().toString();
				if(oldone.compareTo("")==0 || newpass.compareTo("")==0){
					Toast.makeText(hai, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
					
				}else{
					progress=new ProgressDialog(mainactivity);
					progress.setMessage("Updating Settings ... ");
				      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				      progress.setIndeterminate(true);
				      progress.show();
				      oldpassword=old.getText().toString();
				      newpassword=newone.getText().toString();
						new Asynctask_editpass(SettingsFragment.this).execute("http://home.iitj.ac.in/~kuchanamaharsh/bus_app/inst_1.txt");

				}
			}
		});
        logout=(ImageView)rootView.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent=new Intent(SettingsFragment.this.getActivity(),Splash.class);
				SettingsFragment.this.getActivity().finish();
				startActivity(myintent);
			}
		});
       
         
        return rootView;
    }
	public void showresults(String result) {
		// TODO Auto-generated method stub
		progress.cancel();
		Toast.makeText(hai, result, Toast.LENGTH_LONG).show();
	}
}


class Asynctask_editpass extends AsyncTask<String, Void, String> {

    Context myContext= Splash.mContext;
    SettingsFragment j=null;

	String result = "";
	String email="";
    Asynctask_editpass(SettingsFragment m){
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
        	email=j.email;
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://home.iitj.ac.in/~kuchanamaharsh/solbox/changepass.php?email="+email+"&old="+j.oldpassword+"&new="+j.newpassword);
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
        	                sb.append(line );
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

