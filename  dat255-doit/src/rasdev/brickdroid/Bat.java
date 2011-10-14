package rasdev.brickdroid;

import rasdev.brickdroid.BrickdroidView.BrickdroidThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;

public class Bat {
	
	public double mX;	// Bat center X
	public double mY;	// Bat center Y
	
	public int mXLeft;	// Bat left X
	public int mYTop;	// Bat top Y
	
	private double mDX;	// Bat X step
	private double mDY;	// Bat Y step
	
	private double mSpeed;	// Bat speed (px/s)
	
	private Drawable mBatImg;	// Bat image
	
	public int mBatWidth;	// Bat width
	public int mBatHeight;	// Bat height
	
	private SurfaceHolder mSurfaceHolder;	// Surface holder reference
	private Context mContext;	// Context reference
	private BrickdroidThread mThread;	// Thread reference
	
	/**
	 * Bat constructor
	 * @param surfaceHolder
	 * @param context
	 * @param thread
	 */
	public Bat(SurfaceHolder surfaceHolder, Context context, BrickdroidThread thread) {
		// Store references
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		mThread = thread;
		
		// Load image resources
		mBatImg = mContext.getResources().getDrawable(R.drawable.bat);
		
		// Determine image width and height
		mBatWidth = mBatImg.getIntrinsicWidth();
		mBatHeight = mBatImg.getIntrinsicHeight();
		
		// Initialize bat
		init();
		
	}
	
	/**
	 * Initialize bat
	 */
	public void init() {
		// Set bat X, Y positions
		mX = 100.0;
		mY = 100.0;
		
		// determine left X, top Y positions
		mXLeft = (int) mX - mBatWidth / 2;
		mYTop = mThread.mCanvasHeight - 100;
	}
	
	/**
	 * Draw bat onto canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		mBatImg.setBounds(mXLeft, mYTop, mXLeft + mBatWidth, mYTop + mBatHeight);
		mBatImg.draw(canvas);
	}
	
	/**
	 * Update bat physics
	 * @param touchX
	 * @param touchY
	 */
	public void update(double touchX, double touchY) {
		// Store touch positions
		mX = touchX;
		//mY = touchY;
		
		// Determine left X, top Y positions
		mYTop = mThread.mCanvasHeight - ((int) mY + mBatHeight / 2);
		mXLeft = (int) mX - mBatWidth / 2;
		
		// Limit positions so that the bat doesn't move outside the screen
		if (mXLeft < 0) { mXLeft = 0; }
		if (mXLeft + mBatWidth > mThread.mCanvasWidth) { mXLeft = mThread.mCanvasWidth - mBatWidth; } 
	}
	
}
