package rasdev.brickdroid;

import rasdev.brickdroid.BrickdroidView.BrickdroidThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Brickdroid extends Activity implements View.OnTouchListener {
	
	BrickdroidView mBrickdroidView;
	BrickdroidThread mBrickdroidThread;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.main);
        
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        mBrickdroidView = (BrickdroidView) findViewById(R.id.brickdroid);
        mBrickdroidThread = mBrickdroidView.getThread();
        mBrickdroidThread.setSurfaceSize(dm.widthPixels, dm.heightPixels);

        if (savedInstanceState == null) {
        	mBrickdroidThread.setState(BrickdroidThread.STATE_READY);
        	Log.w(this.getClass().getName(), "SIS is null");
        }
        else {
        	mBrickdroidThread.restoreState(savedInstanceState);
        	Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        
        mBrickdroidView.setOnTouchListener(this);
    }
    
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mBrickdroidView.getThread().gamePause();
    }
    
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mBrickdroidThread.saveState(outState);
    	Log.w(this.getClass().getName(), "SIS called");
    }
    
    public boolean onTouch(View v, MotionEvent e) {
    	mBrickdroidThread.setTouchPos(e.getX(), e.getY());
    	return true;
    }
    
    
    public void setFullScreen() {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    
    
    public void setNoTitle() {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
}