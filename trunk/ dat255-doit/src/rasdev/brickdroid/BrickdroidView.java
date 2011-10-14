package rasdev.brickdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BrickdroidView extends SurfaceView implements SurfaceHolder.Callback {
	
	// handle to the thread that actually draws the animation
	private BrickdroidThread mBrickdroidThread;
	
	
	
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public BrickdroidView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// register interest in surface changes
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		// Create Brickdroid thread
		mBrickdroidThread = new BrickdroidThread(holder, context, null);
		
		setFocusable(true);
	}
	
	
	/**
	 * Retrieve the brickdroid animation thread
	 */
	public BrickdroidThread getThread() {
		return mBrickdroidThread;
	}
	
	
	
	
	
	
	
	/**
	 * SurfaceView & SurfaceHolder.Callback Overrides
	 */
	// Callback invoked upon window focus change
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus) mBrickdroidThread.gamePause();
	}
	
	// Callback invoked when the surface dimensions change
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mBrickdroidThread.setSurfaceSize(width, height);
	}
	
	// Callback invoked when the surface has been created and is ready to be used
	public void surfaceCreated(SurfaceHolder holder) {
		mBrickdroidThread.setState(BrickdroidThread.STATE_READY);
		mBrickdroidThread.start();
	}
	
	// Callback invoked upon exit of the application
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		mBrickdroidThread.setState(BrickdroidThread.STATE_STOPPED);
		
		while (retry) {
			try {
				mBrickdroidThread.join();
				retry = false;
			}
			catch (InterruptedException e) {
			}
		}
	}
	
	
	
	
	
	
	/**
	 * Brickdroid Thread Class
	 */
	class BrickdroidThread extends Thread {
		
		// Game state constants
		public static final int STATE_READY = 1;
		public static final int STATE_RUNNING = 2;
		public static final int STATE_PAUSED = 3;
		public static final int STATE_STOPPED = 4;
		
		
		
		
		/**
		 * Game variables
		 */
		private int mState = 1;				// Game state
		
		public int mCanvasWidth;	// Screen width
		public int mCanvasHeight;	// Screen height
		
		private double mTouchX;	// Touch X position
		private double mTouchY;	// Touch Y position
		
		
		private Level mLevel;
		public Ball mBall;
		public Bat mBat;
		
		
		private long mLastTime;
		
		
		private SurfaceHolder mSurfaceHolder;	// Surface holder
		private Context mContext;				// Application context
		private Handler mHandler;				// Handler
		
		
		
		
		/**
		 * Constructor
		 * @param surfaceHolder
		 * @param context
		 * @param handler
		 */
		public BrickdroidThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
			mSurfaceHolder	= surfaceHolder;
			mContext		= context;
			mHandler		= handler;			
			
			mLevel = new Level(surfaceHolder, context, this);
			mBat = new Bat(surfaceHolder, context, this);
			mBall = new Ball(surfaceHolder, context, this);
			
			setState(STATE_READY);
			
		}
		
		public Level getmLevel() {
			return mLevel;
		}
		
		
		/**
		 * Start the game
		 */
		public void gameStart() {
			synchronized (mSurfaceHolder) {
				mBall.init();
				
				mLastTime = System.currentTimeMillis();
			}
			setState(STATE_RUNNING);
		}
		
		
		/**
		 * Pause the game
		 */
		public void gamePause() {
			synchronized (mSurfaceHolder) {
				if (mState == STATE_RUNNING) setState(STATE_STOPPED);
			}
		}
		
		/**
		 * Unpause the game
		 */
		public void gameResume() {
			synchronized (mSurfaceHolder) {
				mLastTime = System.currentTimeMillis() + 100;
			}
			setState(STATE_RUNNING);
		}
		
		
		
		/**
		 * Stores the current state of the application
		 * 
		 * @param data
		 * @return Bundle with this view's state
		 */
		public Bundle saveState(Bundle data) {
			synchronized (mSurfaceHolder) {
				if (data != null) {
					
					data = mBall.saveState(data);
					
				}
			}
			return data;
		}
		
		/**
		 * Restores the stored state of the application
		 * 
		 * @param savedState
		 */
		public synchronized void restoreState(Bundle savedState) {
			synchronized (mSurfaceHolder) {
				setState(STATE_RUNNING);
				
				mBall.restoreState(savedState);
			}
		}
		
		
		/**
		 * Set game state
		 * @param mode
		 */
		public void setState(int mode) {
			synchronized (mSurfaceHolder) {
				mState = mode;
			}
		}
		
		
		public void setSurfaceSize(int width, int height) {
			synchronized (mSurfaceHolder) {
				mCanvasWidth = width;
				mCanvasHeight = height;
			}
		}
		
		public void setTouchPos(double x, double y) {
			synchronized (mSurfaceHolder) {
				mTouchX = x;
				mTouchY = y;
				
				if (mState != STATE_RUNNING) {
					gameStart();
				}
			}
		}
		
		
		
		/**
		 * Main loop
		 */
		@Override
		public void run() {
			while (mState != STATE_STOPPED) {
				Canvas canvas = null;
				try {
					canvas = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						if (mState == STATE_RUNNING) {
							update();
						}
						draw(canvas);
					}
				}
				finally {
					if (canvas != null) {
						mSurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
		
		
		public void update() {
			long now = System.currentTimeMillis();
			
			if (mLastTime > now) return;
			
			double elapsed = now - mLastTime;
			
			mBall.update(elapsed);
			mLevel.update();
			mBat.update(mTouchX, mTouchY);
			
			mLastTime = now;
		}
		
		public void draw(Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			
			mLevel.draw(canvas);
			mBall.draw(canvas);
			mBat.draw(canvas);
		}
		
	}
	
	
	
}
