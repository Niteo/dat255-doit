package rasdev.brickdroid;

import java.util.List;

import rasdev.brickdroid.BrickdroidView.BrickdroidThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.SurfaceHolder;

public class Ball {
	// State keys
	private static final String KEY_MX = "mX";
	private static final String KEY_MY = "mY";
	private static final String KEY_DX = "mDX";
	private static final String KEY_DY = "mDY";
	private static final String KEY_ANGLE = "mAngle";
	
	
	// Ball dynamic variables
	private Drawable mBallImg;	// Ball image
	
	private int mBallWidth;	// Ball image width
	private int mBallHeight;	// Ball image height
	
	private double mX;	// X position
	private double mY;	// Y position
	
	private int mXLeft;
	private int mYTop;
	
	private double mDX;	// X displacement
	private double mDY;	// Y displacement
	
	private double mAngle;	// Ball direction
	
	private double mSpeed;	// Ball speed
	
	private boolean mHit;	// Did the ball hit or not ?
	
	
	private SurfaceHolder mSurfaceHolder;
	private Context mContext;
	private BrickdroidThread mThread;
	
	
	/**
	 * Constructor
	 * @param surfaceHolder
	 * @param context
	 * @param thread
	 */
	public Ball(SurfaceHolder surfaceHolder, Context context, BrickdroidThread thread) {
		mSurfaceHolder	= surfaceHolder;
		mContext		= context;
		mThread			= thread;
		
		
		mBallImg = mContext.getResources().getDrawable(R.drawable.ball);
		
		
		mBallWidth = mBallImg.getIntrinsicWidth();
		mBallHeight = mBallImg.getIntrinsicHeight();
		
		
		init();
		
	}
	
	
	/**
	 * Initialize ball position, speed and angle
	 */
	public void init() {
		mX = mThread.mCanvasWidth / 2;
		mY = 120 - mThread.mCanvasHeight; //mCanvasSize initialized at this time? I think not.
		mAngle = 25;
		mSpeed = 340.0;
	}
	
	
	/**
	 * Save balls position, speed and angle
	 * @param data
	 * @return
	 */
	public Bundle saveState(Bundle data) {
		if (data != null) {
			data.putDouble(KEY_MX, mX);
			data.putDouble(KEY_MY, mY);
			data.putDouble(KEY_DX, mDX);
			data.putDouble(KEY_DY, mDY);
			data.putDouble(KEY_ANGLE, Double.valueOf(mAngle));
		}
		return data;
	}
	
	
	/**
	 * Restore balls position, speed and angle
	 * @param savedState
	 */
	public void restoreState(Bundle savedState) {
		mX = savedState.getDouble(KEY_MX);
		mY = savedState.getDouble(KEY_MY);
		mDX = savedState.getDouble(KEY_DX);
		mDY = savedState.getDouble(KEY_DY);
		mAngle = savedState.getDouble(KEY_ANGLE);
	}
	
	
	/**
	 * Draw balls onto canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		mBallImg.setBounds(mXLeft, mYTop, mXLeft + mBallWidth, mYTop + mBallHeight);
		mBallImg.draw(canvas);
	}
	
	
	/**
	 * Update ball physics
	 * @param canvas
	 * @param elapsed
	 */
	public void update(double elapsed) {
		// calculate step in pixels relative to how much time has passed since last move
		double diff = (mSpeed / 1000.0 * elapsed);
		
		// calculate and apply x, y vectors according to angle
		double radians = mAngle * (Math.PI / 180);
		
		mX += diff * Math.sin(radians);
		mY += diff * Math.cos(radians);
		
		
		// Check for side collisions
		if (mX > mThread.mCanvasWidth && !mHit) {
			mAngle = -mAngle;
			mX = mThread.mCanvasWidth;
			
			mHit = true;
		}
		else if (mX < 0 && !mHit) {
			mAngle = -mAngle;
			mX = 0;
			
			mHit = true;
		}
		else if (mY > mThread.mCanvasHeight && !mHit) {
			radians = Math.PI - radians;
			
			mAngle = radians * (180 / Math.PI);
			mY = mThread.mCanvasHeight;
			
			mHit = true;
			
		}
		else if (mY < 0 && !mHit) {
			radians = Math.PI - radians;
			
			mAngle = radians * (180 / Math.PI);
			mY = 0;
			
			mHit = true;
		}
		else if (mHit) {
			mHit = false;
		}
		
		
		// Check collision with the bat
		if (mX > mThread.mBat.mXLeft && mX < (mThread.mBat.mXLeft + mThread.mBat.mBatWidth) &&
				mY < mThread.mBat.mY && !mHit)
		{
			// Determine bounce off angle
			mAngle = (mX - mThread.mBat.mXLeft) - (mThread.mBat.mBatWidth / 2);
			
			// reposition ball above bat
			mY = mThread.mBat.mY + (mBallWidth / 2);
			
			// increase speed
			mSpeed += 5.0;
			
			mHit = true;
		}
		else if (mHit) {
			mHit = false;
		}
		
		
		//check collision with bricks
		Level level = mThread.getmLevel();
		List<Brick> bricks = level.getBricks();
		int collidedBrickIndex = -1;
		for(Brick brick : bricks) {
			if (brick.collision((int) (mX + 0.5),(int) (mY + 0.5), mThread.mCanvasHeight)) {
				brick.hit();
				collidedBrickIndex = bricks.indexOf(brick);
				
				
				if (true) { //TODO if ball hit the top or bottom of a brick.
					System.out.println("Top/Bottom!");
					radians = Math.PI - radians;
					mAngle = radians * (180 / Math.PI);
				}
				else { //hit the side of a brick
					System.out.println("Side!");
					mAngle = -mAngle;
				}
				mHit = true;
				break;
			}
		}
		if (collidedBrickIndex != -1) {
			if (bricks.get(collidedBrickIndex).getLives() == 0) {
				bricks.remove(collidedBrickIndex);
				if(bricks.size() == 0) {
					level.load(level.level+1);
				}
			}
		}
		
		// Store y-inverted coordinates
		mYTop = mThread.mCanvasHeight - ((int) mY + mBallHeight / 2);
		mXLeft = (int) mX - mBallWidth / 2;
	}
	
}
