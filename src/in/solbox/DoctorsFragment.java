package in.solbox;

import android.app.Fragment;
import in.solbox.adapter.ListAdapter;
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

public class DoctorsFragment extends Fragment {
	MainActivity main;
	String email="";
	public DoctorsFragment(MainActivity s){
		main=s;
	}
	ProgressDialog progress;
	Activity a;
	ListAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
         a=this.getActivity();
         ListView list= (ListView)rootView.findViewById(R.id.list_main_feed);
	         adapter = new ListAdapter(this.getActivity(),inflater,main);
	        list.setAdapter(adapter); 
	        email=this.getArguments().getString("email");
				 progress=new ProgressDialog(a);
					progress.setMessage("Loading results ... ");
				      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				      progress.setIndeterminate(true);
				      progress.show();
			        LoadDoctorsData loadFeedData = new LoadDoctorsData(adapter,a.getApplicationContext(),DoctorsFragment.this);
			   
			            loadFeedData.execute("http://"+fullpath+"/doctors.php");
			        
        return rootView; 
    }
	
	public void closeprogress(){
		progress.cancel();
	}
}
