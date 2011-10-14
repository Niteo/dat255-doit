package rasdev.brickdroid;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Brick {
	private boolean alive = true;
	private int left; //x pos
	private int top;  //y pos
	private int right, bottom;
	private int width; 
	private int height;
	private int lives;
	public int getLives() {
		return lives;
	}
	private Timer timer = new Timer();
	private boolean lockLives = false;
	
	public Brick(int pos_x, int pos_y, int width, int height, int lives) {
		left = pos_x;
		top = pos_y;
		right = this.left + width;
		bottom = this.top + height;
		this.width = width;
		this.height = height;
		this.lives = lives;
	}
	
	public void draw(Canvas canvas, Paint p) {
		if (alive) {
			canvas.drawRect(new Rect(left,top,left+width,top+height), p);
			for(int n=lives-1;n>0;n--) {
				canvas.drawRect(new Rect(left+(lives-n),top+(lives-n),(left+width)-(lives-n),(top+height)-(lives-n)), p);
			}
		}
	}
	
	public void hit() {
		if (!lockLives) {
			lives--;
			System.out.println("Brick hurt! Remaining lives: " + lives);
			lock();
			TimerTask task = new TimerTask() {
				public void run() {
					unlock();
				}
			};
			timer.schedule(task, 100);
			if (lives == 0) {
				alive = false;
			}
		}
	}
	
	public void lock() {
		lockLives = true;
	}
	
	public void unlock() {
		lockLives = false;
	}
	
	public boolean collision(int x, int y, int canvasHeight) {
		y = canvasHeight - y;
		if (alive) {
			if (x < left) return false;
		    if (x > right) return false;
		    
		    if (y < top) return false;
		    if (y > bottom) return false;
		    return true;
		}
		return false;
	}
	
	public int[] getCenter() {
		int[] pos = {left + width / 2, top + height / 2};
		return pos;
	}
	
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}
}
