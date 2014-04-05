package in.solbox;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
public class Splash extends Activity{
	
	
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    public static Context mContext;
    ImageView signup,login;
    Splash spl;
    int image_int=10;
 
    private GestureDetector detector;
 
    
   RefreshHandler refreshHandler=new RefreshHandler();
    
    class RefreshHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Splash.this.updateUI();
        }
        public void sleep(long delayMillis){
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    public void updateUI(){
            refreshHandler.sleep(5000);
            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));                  
            mViewFlipper.showNext();
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mContext = this;
        spl=this;
        signup=(ImageView)findViewById(R.id.join_button_splash);
        login=(ImageView)findViewById(R.id.signin_button_splash);
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        detector = new GestureDetector(new SwipeGestureDetector());
        mViewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
        
     signup.setOnClickListener(new View.OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent slideactivity = new Intent(Splash.this, Register.class);
				 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
				  Bundle bndlanimation =ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim1_activity,R.anim.anim2_activity).toBundle();
				  startActivity(slideactivity, bndlanimation);
				 }else{
					 startActivity(slideactivity);
				 }
			}
		});
        
        login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent slideactivity = new Intent(Splash.this, Login.class);
				 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
				  Bundle bndlanimation =ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim1_activity,R.anim.anim2_activity).toBundle();
				  startActivity(slideactivity, bndlanimation);
				 }else{
					 startActivity(slideactivity);
				 }
			}
		}); 
        
        updateUI();
         }
 
    class SwipeGestureDetector extends SimpleOnGestureListener implements OnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));                  
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }
 
            } catch (Exception e) {
                e.printStackTrace();
            }
 
            return false;
        }

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
}



