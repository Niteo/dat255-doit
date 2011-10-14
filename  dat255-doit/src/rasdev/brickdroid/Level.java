package rasdev.brickdroid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rasdev.brickdroid.BrickdroidView.BrickdroidThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class Level {
	
	private SurfaceHolder mSurfaceHolder;	// Surface holder reference
	private Context mContext;	// Context reference
	private BrickdroidThread mThread;	// Thread reference
	public int level = 1;
	private List<Brick> bricks;
	public List<Brick> getBricks() {
		return bricks;
	}
	private Timer timer = new Timer();

	private HashMap<Integer,int[][]> levelMap = new HashMap();
	
	/**
	 * Level constructor
	 * @param surfaceHolder
	 * @param context
	 * @param thread
	 */
	public Level(SurfaceHolder surfaceHolder, Context context, BrickdroidThread thread) {
		// Store references
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		mThread = thread;
		int[][] lvl1 = {{1,1,1,1,1,1,1,1},
				 		{1,0,10,1,1,1,1,1},
				 		{2,3,4,5,1,1,1,1}};
		levelMap.put(1, lvl1);
		int[][] lvl2 = {{1,1,1,1,1,1,1,1},
		 				{1,1,1,1,1,1,1,1},
		 				{0,1,1,0,1,1,1,1}};
		levelMap.put(2, lvl2);
		int[][] lvl3 = {{1,2,2,1,1,1,1,1},
 						{2,1,1,2,1,1,1,1},
 						{0,1,1,0,1,1,1,1}};
		levelMap.put(3, lvl3);
		
		
		
		TimerTask task = new TimerTask() {
			public void run() {
				load(1);
			}
		};
		timer.schedule(task, 70);
	}
	
	/**
	 * Level loader
	 * @param lvlId
	 */
	public void load(int lvlId) {
		bricks = new LinkedList<Brick>();
		System.out.println(mThread.mCanvasWidth);
		int[][] lvl = levelMap.get(lvlId);
		for (int i = 0;i<3;i++) {
			for (int j = 0;j<8;j++) {
				if (lvl[i][j] >= 1) {
					bricks.add(
							new Brick(2+(2+((mThread.mCanvasWidth-2*9)/8))*(j), //left x-pos
										 4+((30)+4)*(i), //top y-pos
										 ((mThread.mCanvasWidth-2*9)/8),//width
										 30,							//height
										 lvl[i][j]));					//Lives
				}
			}
		}
	}
	
	/**
	 * Draw level onto canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        
        int lineY = mThread.mCanvasHeight - 80;
        
        canvas.drawLine(0, lineY, mThread.mCanvasWidth, lineY, p);
        //System.out.println("Resolution: " + canvas.getHeight() + "x" +canvas.getWidth());
        for(Brick brick : bricks) {
        	brick.draw(canvas, p);
        }
        
	}
	
	/**
	 * Update level physics
	 */
	public void update() {
		
	}
}
