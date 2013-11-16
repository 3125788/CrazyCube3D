package elong.CrazyCube;


import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import cn.sharesdk.framework.AbstractWeibo;

import com.adsmogo.adapters.AdsMogoCustomEventPlatformEnum;
import com.adsmogo.controller.listener.AdsMogoListener;

import dalvik.system.VMRuntime;
import elong.Cube.CrazyCubeAD;
import elong.Cube.CrazyCubeADPoints;
import elong.Cube.CrazyCubeCfg;
import elong.Cube.CrazyCubeDevice;
import elong.Cube.CrazyCubeDevice.E_DEVICE;
import elong.Cube.CrazyCubeGL;
import elong.Cube.CrazyCubeGL.E_SELECTION;
import elong.Cube.CrazyCubeGL.E_SHAKE;
import elong.Cube.CrazyCubeGLView;
import elong.Cube.CrazyCubeShow;
import elong.Cube.Cube3.CrazyCube;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_METHOD;
import elong.Cube.Cube3.MethodBridge;
import elong.Cube.Cube3.MethodLayerFirst;

public class CrazyCubeMain extends Activity implements SensorEventListener, AdsMogoListener{
	
	public static E_METHOD eWhichMethod = E_METHOD.LAYER_FIRST;               //使用哪一种解法
	public static boolean bSupportMultiMethod = true;                    //支持多种解法
	
	private GLSurfaceView vMain = null;
	private CrazyCubeGL crazyCubeGL = null;
	public CrazyCube cubeMain = null;
		
	private long lBackLastTime = 0;      //上次按下BACK的时间
	
	private boolean bClickAD = false;
	
	SensorManager sm;
	protected long curTime;
	protected long lastTime;
	protected float last_x;
	protected float last_y;
	protected float last_z;
	protected long initTime;
	protected float totalShake;
	protected float shake;
	
	public enum E_MENU
	{
		MAIN_NONE,
		MAIN_PLAY,
		MAIN_STUDY,
		MAIN_SETUP,
		MAIN_ADVANCE,
		MAIN_HELP,
		MAIN_ABOUT,
		MAIN_APP,
		MAIN_EXIT,
	}

	public enum E_POPMENU
	{
		NONE,
		AUTOPLAY_ID,
		DISRUPT_ID,
		RESTART_ID,
		STUDY_ID,
		SHARE_ID,
		SETUP_ID,
		USER_ID,
		DAILYAWARD_ID,
		TUTORIAL_ID,
		EXPORT_ID,
		EXIT_ID,
	}

	public static E_MENU meActiveMenu = E_MENU.MAIN_NONE; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		VMRuntime.getRuntime().setTargetHeapUtilization(0.750f); // 设置堆内存的利用率为75%		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
						
		CrazyCubeAD.InitAD(this);

		//初始化积分墙
		CrazyCubeAD.InitOfferWall(CrazyCubeMain.this);
		
		System.out.println("CrazyCubeMain.onCreate");
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);		
//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		CrazyCubeCfg cCfg = new CrazyCubeCfg(this);

		cCfg.loadConfig();		//加载配置
		cCfg.loadDaily();
		cCfg.loadAdvConfig();    //加载高级功能
		
		
		if(bSupportMultiMethod)
		{
			if(cCfg.cubeConfig.iCubeMethod == 2)
			{
	        	CrazyCubeADPoints ccadp = new CrazyCubeADPoints(CrazyCubeMain.this, null);
	     		int iPoints = ccadp.GetADPoint(CrazyCubeMain.this);

	            CrazyCubeDevice ccd = new CrazyCubeDevice(iPoints);
	            boolean bMethod1Active = ccd.IsActived(E_DEVICE.BRIDGE);				
				if(bMethod1Active)
				{
					eWhichMethod = E_METHOD.BRIDGE;
				}
				else
				{
					eWhichMethod = E_METHOD.LAYER_FIRST;
				}
			}
			else if(cCfg.cubeConfig.iCubeMethod == 1)
			{
				eWhichMethod = E_METHOD.LAYER_FIRST;
			}
			else
			{}
		}
		
        if(E_METHOD.LAYER_FIRST == eWhichMethod)
        {
        	cubeMain = new MethodLayerFirst();
        }
        else if (E_METHOD.BRIDGE == eWhichMethod)
        {
        	cubeMain = new MethodBridge();
        }
        else
        {
        	System.out.println("Init Cube Method failure");
        }

//		vMain = new CrazyCubeGL(this, cubeMain, E_GAMEMODE.PLAY_MODE);
        crazyCubeGL = new CrazyCubeGL(this, cubeMain, E_GAMEMODE.PLAY_MODE);
	

//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		ShowMainMenu(this);
		
		//启动SHAKE
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//		int sensorType = android.hardware.Sensor.TYPE_ACCELEROMETER;
		sm.registerListener(AcceleratorSensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		    SensorManager.SENSOR_DELAY_FASTEST);
		sm.registerListener(ProximitySensorEventListener, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),
			    SensorManager.SENSOR_DELAY_NORMAL);
		
		AbstractWeibo.initSDK(this);
		
		if(cCfg.IsFirstUse())
		{
			//显示首次奖励
			crazyCubeGL.mCcs.ShowFirstAward(100);
			crazyCubeGL.AwardADPoints(100, this.getString(R.string.tipFirstAward));
			cCfg.SetFirstUse(false);
			
			//显示操作指南
			Message message = new Message();
			message.what = CrazyCubeGL.CUBE_TUTORIAL;  
			crazyCubeGL.mHandler.sendMessage(message);			
		}
		else
		{		
			if(cCfg.IsFirstUsedToday())
			{
				//显示每日奖励
				int iDay = cCfg.GetDay();
				int iPoints = cCfg.GetAwardPoints();
				int iRet = crazyCubeGL.mCcs.ShowDailyAward(iDay, iPoints);
    			if(CrazyCubeShow.DIALOG_SHARE == iRet)
    			{
    				crazyCubeGL.ShowShare(String.format(crazyCubeGL.mCon.getString(R.string.szGetDailyAward), iPoints));
    			}
				crazyCubeGL.AwardADPoints(iPoints, this.getString(R.string.tipDayliyAward));
			}
		}
		return;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onDestroy");
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onPause");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onResume");
			
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		System.out.println("CrazyCubeMain.onStop");
		super.onStop();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(crazyCubeGL.mCube.isRotatting)
		{
			//操作还未完成，此时退出会出现异常
			return false;
		}

		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			long lNow = System.currentTimeMillis()/1000;
			if(lNow - lBackLastTime < 3)
			{
				System.out.println("Press BACK, wait...");
				return false;
			}
			lBackLastTime = lNow;
			
			if(meActiveMenu == E_MENU.MAIN_NONE)
			{
				if (CrazyCubeShow.DIALOG_YES == crazyCubeGL.mCcs.ShowExit(getResources().getString(R.string.tipExit)))
				{
					crazyCubeGL.mCcs.SetCubeStatus();		
					CrazyCubeAD.DestroyAD();
					IncreaseRunTimes(CrazyCubeMain.this);
					finish();
					System.exit(0);     //彻底退出程序
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if(CrazyCubeGL.isDisrupting)
				{
					System.out.println("Cube Disrupting, wait...");
					return false;	
				}
				else
				{
					if(meActiveMenu == E_MENU.MAIN_STUDY)
					{
						if (CrazyCubeShow.DIALOG_YES == crazyCubeGL.mCcs.ShowExit(getResources().getString(R.string.tipExitStudy)))
						{
							crazyCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);            //退出自动(训练)模式
							crazyCubeGL.mCube.UpdateTipData();
		                    meActiveMenu = E_MENU.MAIN_PLAY;
							ShowMainMenu(this);
						}
						else
						{
							return false;
						}
					}
					else
					{
						ShowMainMenu(this);
					}
				}
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void ShowMainMenu(final Context context)
	{
		System.out.println("Show Main menu");
		
		if(isShowAdTime())
		{
			setContentView(R.layout.menu);
		}
		else
		{
			setContentView(R.layout.menu_noad);	
		}
		
		//主页面显示广告
		LayoutInflater factory = LayoutInflater.from(this);
		View vMainAD = factory.inflate(R.layout.menu, null);	
    	boolean bShowAD = crazyCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.CUBE_KEY);
    	bShowAD = !bShowAD;
    	bShowAD = true;
    	CrazyCubeAD.ShowAd(CrazyCubeMain.this, CrazyCubeMain.this, vMainAD, bShowAD);
		
        meActiveMenu = E_MENU.MAIN_NONE;
        
        Button btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				meActiveMenu = E_MENU.MAIN_PLAY;
				if(vMain != null)
				{
					vMain.destroyDrawingCache();
					vMain = null;
				}
				CrazyCubeAD.DestroyAD();
	        	boolean bShowAD = crazyCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.CUBE_KEY);
	        	bShowAD = !bShowAD;
	        	bShowAD = true;
	        	if(bShowAD)
	        	{
	        		if(crazyCubeGL.mCfg.cubeConfig.iRunTimes > 20)
	        		{
	        			if(!bClickAD)
	        			{
		        			//crazyCubeGL.mCcs.ShowMsg(context, res, R.string.tipRunOver);
		        			//return;
	        			}
	        		}
	        	}
	        	
	        	vMain = new CrazyCubeGLView(CrazyCubeMain.this, crazyCubeGL);
	        	
				CrazyCubeAD.ShowAd(CrazyCubeMain.this, CrazyCubeMain.this, vMain, bShowAD);
//				crazyCubeMain.DisplayCube();
				E_SELECTION	eObj = E_SELECTION.SELE_PLAY;
				crazyCubeGL.RaiseButtonPressedEvent(eObj, false);
			}
		});

        Button btnStudy = (Button)findViewById(R.id.btnStudy);
        btnStudy.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				meActiveMenu = E_MENU.MAIN_STUDY;
				if(vMain != null)
				{
					vMain.destroyDrawingCache();
					vMain = null;
				}
				CrazyCubeAD.DestroyAD();
	        	boolean bShowAD = crazyCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.CUBE_KEY);
	        	bShowAD = !bShowAD;
	        	bShowAD = true;
	        	if(bShowAD)
	        	{
	        		if(crazyCubeGL.mCfg.cubeConfig.iRunTimes > 20)
	        		{
	        			if(!bClickAD)
	        			{
		        			//crazyCubeGL.mCcs.ShowMsg(context, res, R.string.tipRunOver);
		        			//return;
	        			}
	        		}
	        	}
	        	vMain = new CrazyCubeGLView(CrazyCubeMain.this, crazyCubeGL);

				CrazyCubeAD.ShowAd(CrazyCubeMain.this, CrazyCubeMain.this, vMain, bShowAD);
				E_SELECTION	eObj = E_SELECTION.SELE_STUDY;
				crazyCubeGL.RaiseButtonPressedEvent(eObj, false);

			}
		});

        
        Button btnSetup = (Button)findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CrazyCubeAD.DestroyAD();
				meActiveMenu = E_MENU.MAIN_SETUP;
				setContentView(R.layout.background_setup);
				int iRet = crazyCubeGL.mCcs.ShowSetup();
				if(CrazyCubeShow.DIALOG_SHARE == iRet)
    			{
					crazyCubeGL.ShowShare(crazyCubeGL.mCon.getString(R.string.szGetSetup));        				
    			}
            	ShowMainMenu(CrazyCubeMain.this);
			}
		});

        Button btnHelp = (Button)findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//		        ccs.ShowHelp(CrazyCubeMain.this);
				CrazyCubeAD.DestroyAD();
				meActiveMenu = E_MENU.MAIN_HELP;
				setContentView(R.layout.background_help);
				int iRet = crazyCubeGL.mCcs.ShowHelp();
            	ShowMainMenu(CrazyCubeMain.this);
				if(CrazyCubeShow.DIALOG_SHARE == iRet)
            	{
					crazyCubeGL.ShowShare(crazyCubeGL.mCon.getString(R.string.szGetHelp));
            	}            	
			}
		});
        
        Button btnAbout = (Button)findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CrazyCubeAD.DestroyAD();
 				meActiveMenu = E_MENU.MAIN_ABOUT;
				setContentView(R.layout.background_about);
            	if(CrazyCubeShow.DIALOG_SUGGEST == crazyCubeGL.mCcs.ShowAbout())
            	{
            		crazyCubeGL.mCcs.ShowSuggest();
            	}
            	ShowMainMenu(CrazyCubeMain.this);
			}
		});
        

        Button btnMore = (Button)findViewById(R.id.btnMore);
        if(btnMore != null)
        {
	        btnMore.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//crazyCubeGL.aaa();					
					CrazyCubeAD.ShowOffers(CrazyCubeMain.this);
	
				}
			});
        }
        
        Button btnDeviceList = (Button)findViewById(R.id.btnDevice);
        if(btnDeviceList != null)
        {
	        btnDeviceList.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//crazyCubeGL.aaa();

					int iRet = crazyCubeGL.mCcs.ShowDevice();
        			if(CrazyCubeShow.DIALOG_SHARE == iRet)
        			{
        				crazyCubeGL.ShowShare(crazyCubeGL.mCon.getString(R.string.szGetDevice));
        			}					
				}
			});
        }

        
        //        Button btnAdvance = (Button)findViewById(R.id.btnAdvance);
//        if(btnAdvance != null)
//        {
//            btnAdvance.setOnClickListener(new OnClickListener() {
//    			
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub
//    			
//    				meActiveMenu = E_MENU.MAIN_ADVANCE;
//    				setContentView(R.layout.background_advance);
//    				crazyCubeGL.ccs.ShowAdvance(context, res);
//                	ShowMainMenu(CrazyCubeMain.this);
//
//    			}
//    		});        	
//        }

        Button btnExit = (Button)findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				crazyCubeGL.mCcs.SetCubeStatus();		
				CrazyCubeAD.DestroyAD();
				IncreaseRunTimes(CrazyCubeMain.this);
				finish();
				System.exit(0);     //彻底退出程序
		    }
		});
        
        TextView tvCopyRight = (TextView)findViewById(R.id.txtCopyRight);
        String szCopyRight = getResources().getString(R.string.copyright);
        String szCopyLink = getResources().getString(R.string.copylink);
        if(szCopyLink.length() > 0)
        {
            SpannableString ss = new SpannableString(szCopyRight);
            ss.setSpan(new URLSpan(szCopyLink), 0, szCopyRight.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置文本内容到textView    
            tvCopyRight.setText(ss);    
            //不添加这一句，拨号，http，发短信的超链接不能执行.    
            tvCopyRight.setMovementMethod(LinkMovementMethod.getInstance());        	
        }
	}
	
	private void IncreaseRunTimes(Context context)
	{
		crazyCubeGL.mCfg.loadConfig();
		crazyCubeGL.mCfg.cubeConfig.iRunTimes++;
		crazyCubeGL.mCfg.saveConfig();
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	//创建菜单
    	boolean res =  super.onCreateOptionsMenu(menu);
    	menu.add(0, E_POPMENU.AUTOPLAY_ID.ordinal(), Menu.NONE, R.string.smenuAuto).setIcon(R.drawable.autoplay_32);
    	menu.add(0, E_POPMENU.DISRUPT_ID.ordinal(), Menu.NONE, R.string.smenuDisrupt).setIcon(R.drawable.disrupt_32);
    	menu.add(0, E_POPMENU.RESTART_ID.ordinal(), Menu.NONE, R.string.smenuRestart).setIcon(R.drawable.restart_32);
    	menu.add(0, E_POPMENU.STUDY_ID.ordinal(), Menu.NONE, R.string.smenuStudy).setIcon(R.drawable.study_32);
    	menu.add(0, E_POPMENU.SHARE_ID.ordinal(), Menu.NONE, R.string.smenuShare).setIcon(R.drawable.share_32);
    	menu.add(0, E_POPMENU.SETUP_ID.ordinal(), Menu.NONE, R.string.smenuSetup).setIcon(R.drawable.setup_32);
    	menu.add(0, E_POPMENU.USER_ID.ordinal(), Menu.NONE, R.string.smenuUser).setIcon(R.drawable.user_32);
    	menu.add(0, E_POPMENU.DAILYAWARD_ID.ordinal(), Menu.NONE, R.string.smenuDaily).setIcon(R.drawable.goldcoin_32);
    	menu.add(0, E_POPMENU.EXPORT_ID.ordinal(), Menu.NONE, R.string.smenuExport).setIcon(R.drawable.icon_48);
    	menu.add(0, E_POPMENU.TUTORIAL_ID.ordinal(), Menu.NONE, R.string.smenuTutorial).setIcon(R.drawable.help_32);    	
    	menu.add(0, E_POPMENU.EXIT_ID.ordinal(), Menu.NONE, R.string.smenuExit).setIcon(R.drawable.exit_32);
    	return res;
    }
    
    @Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
    	if(meActiveMenu == E_MENU.MAIN_PLAY || meActiveMenu == E_MENU.MAIN_STUDY)
    	{
    		return super.onMenuOpened(featureId, menu);
    	}
    	else
    	{
    		return false;                 //主菜单界面不显示OPTION MENU
    	}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
    	//响应菜单事件
    	//将其他菜单操作发送到thread.run中的消息队列中处理
		if(item.getItemId() == E_POPMENU.EXIT_ID.ordinal())
    	{
			crazyCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);            //退出自动(训练)模式
			crazyCubeGL.mCube.UpdateTipData();
            meActiveMenu = E_MENU.MAIN_PLAY;
			ShowMainMenu(this);
    	}
    	else
    	{
        	crazyCubeGL.SetMenuItemId(item.getItemId());  		
    		Message msg = new Message();
    	    msg.what = CrazyCubeGL.MENU_CLICK;
    	    crazyCubeGL.mHandler.sendMessage(msg);        	    		
    	}
    	return super.onOptionsItemSelected(item);
    }
	
	
	public static boolean isShowAdTime()
	{
		Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间
		Time t2 = new Time();		
		t2.set(0, 0, 12, 24, 10, 2011);     //2011-11-1 12:00:00    月份要加1
		
		if(Time.compare(t, t2) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	//事件触发
	public final SensorEventListener AcceleratorSensorEventListener = new SensorEventListener() {

		public void onAccuracyChanged(android.hardware.Sensor sensor,
				int accuracy) {
			// TODO Auto-generated method stub

		}
		
		boolean bShakeZDown = false;
		long lShakeZDownTime = 0;
		
		long lDisruptCurrentTime = 0;
		long lDisruptLastTime = 0;
		long lDisruptLastTime2 = 0;
		float fMaxX = 0;
		float fMinX = 0;
		
		long lTipCurrentTime = 0;
		long lTipLastTime = 0;

		public void onSensorChanged(SensorEvent event) 
		{
//			if(meActiveMenu != E_MENU.MAIN_PLAY && meActiveMenu != E_MENU.MAIN_STUDY)
//			{
//				return;
//			}
			// TODO Auto-generated method stub
			if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) 
			{
				// 获取加速度传感器的三个参数
				float x = event.values[SensorManager.DATA_X];
//				float y = event.values[SensorManager.DATA_Y];
				float z = event.values[SensorManager.DATA_Z];
				
//				TextView tv = (TextView)CrazyCubeMain.this.findViewById(R.id.txtCopyRight);
//				if(tv != null)
//				{
//					tv.setText("x:" + (int)x + "\r\ny:" + (int)y + "\r\nz:" + (int)z);
//				}

				if(meActiveMenu != E_MENU.MAIN_PLAY && meActiveMenu != E_MENU.MAIN_STUDY)
				{
					return;
				}
				
				if(!crazyCubeGL.mCubeDevice.IsActived(E_DEVICE.GRAVITY))
				{
					return;
				}
				if(!crazyCubeGL.mCfg.cubeConfig.bShake)
				{
					return;		//设置没有打开重力感应
				}

				if(z <= -5)
				{
					if(!bShakeZDown)
					{
						bShakeZDown = true;
						lShakeZDownTime = System.currentTimeMillis();
					}
				}
				else if(z >= 5)
				{
					if(bShakeZDown)
					{
						if(curTime - lShakeZDownTime <= 1000)  
						{
							crazyCubeGL.RaiseShakeEvent(E_SHAKE.SHAKE_AUTO);
//							crazyCubeGL.ccs.ShowMsg(CrazyCubeMain.this, "SHAKE_AUTO", Toast.LENGTH_LONG, Color.GREEN);
							bShakeZDown = false;
							return;
						}
						bShakeZDown = false;
					}
				}
				
				lDisruptCurrentTime = System.currentTimeMillis();
				if(lDisruptCurrentTime - lDisruptLastTime < 600)
				{
					if(x > fMaxX)
					{
						fMaxX = x;
					}
					if (x < fMinX)
					{
						fMinX = x;
					}
					if(fMaxX >= 9.0f && fMinX <= -9.0f)
					{
						if(lDisruptCurrentTime - lDisruptLastTime2 > 10000)
						{
							fMaxX = 0;
							fMinX = 0;
							lDisruptLastTime = lDisruptCurrentTime;
							lDisruptLastTime2 = lDisruptCurrentTime;
							crazyCubeGL.RaiseShakeEvent(E_SHAKE.SHAKE_DISRUPT);
							return;							
						}
					}
				}
				else
				{
					fMaxX = 0;
					fMinX = 0;
					lDisruptLastTime = lDisruptCurrentTime;
				}

				
				
				lTipCurrentTime = System.currentTimeMillis();
				if(lTipCurrentTime - lTipLastTime < 800)
				{
					return;
				}
				lTipLastTime = lTipCurrentTime;
				
				//每800ms进入下面的检测一次
				if(z > 0)
				{
					if( x >= 2)
					{
						crazyCubeGL.RaiseShakeEvent(E_SHAKE.SHAKE_PREV);
						return;
					}
					else if(x <= -2)
					{
						crazyCubeGL.RaiseShakeEvent(E_SHAKE.SHAKE_NEXT);
						return;
					}
					else
					{}
				}				
			}
		}
	};

	
	public final SensorEventListener ProximitySensorEventListener = new SensorEventListener() {

		public void onAccuracyChanged(android.hardware.Sensor sensor,
				int accuracy) {
			// TODO Auto-generated method stub

		}

		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (event.sensor.getType() == android.hardware.Sensor.TYPE_PROXIMITY) 
			{
				// 获取加速度传感器的三个参数
				//float x = event.values[SensorManager.DATA_X];
				//float y = event.values[SensorManager.DATA_Y];
			}
		}
	};
	
	public void onClickAd() {
		// TODO Auto-generated method stub
		bClickAD = true;
		System.out.println("AD clicked");
		
	}

	public void onCloseMogoDialog() {
		// TODO Auto-generated method stub
		
	}

	public void onFailedReceiveAd() {
		// TODO Auto-generated method stub
		
	}

	public void onReceiveAd() {
		// TODO Auto-generated method stub
		
	}


	@SuppressWarnings("rawtypes")
	public Class getCustomEvemtPlatformAdapterClass(
			AdsMogoCustomEventPlatformEnum arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onClickAd(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onRealClickAd() {
		// TODO Auto-generated method stub
		
	}

	public void onReceiveAd(ViewGroup arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onRequestAd(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean onCloseAd() {
		// TODO Auto-generated method stub
		return false;
	}


	
}
