package in.solbox.adapter;

import in.solbox.Doctor;
import in.solbox.MainActivity;
import java.util.ArrayList;
import in.solbox.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.solbox.Entry_req;

public class ListAdapter_nonclick extends BaseAdapter {
	 
    private Context mContext;

    private LayoutInflater mLayoutInflater;      
    MainActivity activity;

    private ArrayList<Entry_req> mEntries = new ArrayList<Entry_req>();   
   // private final ImageDownloader mImageDownloader;    

    public ListAdapter_nonclick(Context context,LayoutInflater inflater,MainActivity a) {                        
       mContext = context;
       mLayoutInflater = (LayoutInflater)inflater;
       activity=a;
      // mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public int getCount() {
       return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
       return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(final int position, View convertView,
          ViewGroup parent) {                                           
       RelativeLayout itemView;
       if (convertView == null) {                                        
          itemView = (RelativeLayout) mLayoutInflater.inflate(
                   R.layout.feed_drawer_list_item, parent, false);

       } else {
          itemView = (RelativeLayout) convertView;
       }

       ImageView imageView = (ImageView)
          itemView.findViewById(R.id.listImage);                        
       TextView titleText = (TextView)
          itemView.findViewById(R.id.listTitle);                        
       TextView descriptionText = (TextView)
          itemView.findViewById(R.id.listDescription);                  

       //String imageUrl = mEntries.get(position).imgsrc;   
      // mImageDownloader.download(imageUrl, imageView);                   

       String title = mEntries.get(position).line1;
       titleText.setText(title);
       String description =mEntries.get(position).date;
       if (description.trim().length() == 0) {
          description = "";
       }
       descriptionText.setText(description);
       return itemView;
    }

    public void upDateEntries(ArrayList<Entry_req> entries) {
       mEntries = entries;
       notifyDataSetChanged();
    }
	}
