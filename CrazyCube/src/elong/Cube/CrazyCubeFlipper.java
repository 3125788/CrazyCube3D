package elong.Cube;

import android.app.AlertDialog;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import elong.CrazyCube.R;

public class CrazyCubeFlipper extends AlertDialog implements OnGestureListener 
{

	private ViewFlipper viewFlipper;
	private GestureDetector detector; //手势检测
	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;
	Context mCon = null;
	int iViewIndex = 1;
	int iMaxView = 4;

	protected CrazyCubeFlipper(Context context) {
		super(context);
		mCon = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Window win = getWindow();
		LayoutParams params = new LayoutParams();
		Display d = win.getWindowManager().getDefaultDisplay();
		params.width = d.getWidth();
		params.height = d.getHeight();

		win.setAttributes(params);

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//LayoutInflater factory = LayoutInflater.from(mCon);        
        //View flipperView  = factory.inflate(R.layout.flipperview, null);
		//setView(flipperView);
		setContentView(R.layout.flipperview);
		detector = new GestureDetector(this);
		viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		//往viewFlipper添加View
		if(viewFlipper != null)
		{
			viewFlipper.addView(getImageView(R.drawable.tip_001));
			viewFlipper.addView(getImageView(R.drawable.tip_002));
			viewFlipper.addView(getImageView(R.drawable.tip_003));
			viewFlipper.addView(getImageView(R.drawable.tip_004));
		}
		//动画效果
		leftInAnimation = AnimationUtils.loadAnimation(mCon, R.anim.left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(mCon, R.anim.left_out);
		rightInAnimation = AnimationUtils.loadAnimation(mCon, R.anim.right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(mCon, R.anim.right_out);
	}
	
	private ImageView getImageView(int id)
	{
		ImageView imageView = new ImageView(mCon);
		imageView.setImageResource(id);
		return imageView;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		return this.detector.onTouchEvent(event); //touch事件交给手势处理。
	}
	
	public boolean onDown(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		//Log.i(TAG, "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));
		if(e1.getX()-e2.getX()>120) 
		{
			iViewIndex++;			
			if (iViewIndex <= iMaxView)
			{
				viewFlipper.setInAnimation(leftInAnimation);
				viewFlipper.setOutAnimation(leftOutAnimation);
				viewFlipper.showNext();//向右滑动
				return true;
			}
			else
			{
				dismiss();
			}
		}
		else if((e1.getX()-e2.getY()<-120) && (iViewIndex > 1))
		{
			iViewIndex--;
			viewFlipper.setInAnimation(rightInAnimation);
			viewFlipper.setOutAnimation(rightOutAnimation);
			viewFlipper.showPrevious();//向左滑动
			return true;
		}
		return false;
	}
	
	public void onLongPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	
	}
	
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onShowPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	
	}
	
	public boolean onSingleTapUp(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
}