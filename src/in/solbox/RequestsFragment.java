package in.solbox;

import java.util.ArrayList;

import android.app.Fragment;
import in.solbox.adapter.ListAdapter_nonclick;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class RequestsFragment extends Fragment {
	MainActivity main;
	String email="";
	public RequestsFragment(MainActivity s){
		main=s;
	}
	ProgressDialog progress;
	Activity a;
	ListAdapter_nonclick adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
         a=this.getActivity();
         ListView list= (ListView)rootView.findViewById(R.id.list_main_feed);
	         adapter = new ListAdapter_nonclick(this.getActivity(),inflater,main);
	        list.setAdapter(adapter); 
	        email=this.getArguments().getString("email");
				 progress=new ProgressDialog(a);
					progress.setMessage("Loading requests ... ");
				      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				      progress.setIndeterminate(true);
				      progress.show();
			        LoadReqData loadFeedData = new LoadReqData(adapter,a.getApplicationContext(),RequestsFragment.this);
			   
			            loadFeedData.execute("http://home.iitj.ac.in/~kuchanamaharsh/solbox/requests.php?email="+email);
			      
			            
        return rootView; 
    }
	
	public void closeprogress(){
		progress.cancel();
	}
}

