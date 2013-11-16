package elong.Cube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.opengles.GL10;

import elong.Cube.CrazyCubeDevice.E_DEVICE;
import elong.Cube.Cube3.CrazyCube;
import elong.Cube.Cube3.CrazyCube.E_COLOR;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_METHOD;
import elong.Cube.Cube3.CrazyCube.E_MOUSE_AREA;
import elong.Cube.Cube3.CrazyCube.E_MOUSE_DIRECTION;
import elong.Cube.Cube3.CrazyCube.E_PLATE;
import elong.Cube.Cube3.CrazyCube.E_ROTATIONEVENT;
import elong.Cube.Cube3.CrazyCube.E_SOUND;
import elong.Cube.Cube3.CrazyCube.E_STUDY_STATUS;
import elong.Cube.Cube3.CrazyCube.E_TEXTURE;
import elong.Cube.Cube3.CrazyCube.ST_DreamCube;
import elong.CrazyCube.CrazyCubeMain;

import elong.CrazyCube.CrazyCubeMain.E_MENU;
import elong.CrazyCube.CrazyCubeMain.E_POPMENU;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.widget.Toast;
import elong.CrazyCube.R;

public class CrazyCubeGL{
	
	public Context mCon;
	Resources mRes;
	CrazyCubeSound mSound;
	CrazyCubeBmp mCcBmp;
	public CrazyCubeDevice mCubeDevice;
	Boolean mbCapture = false;
	
	public CrazyCubeGL(Context c, CrazyCube crazyCube, E_GAMEMODE eCubeMode){
		Init(c, crazyCube, eCubeMode);
	}

	void Init(Context c, CrazyCube crazyCube, E_GAMEMODE eCubeMode){

		mCon = c;
		mRes = c.getResources();
		mCcs = new CrazyCubeShow(this, mCon, mRes);
		mCfg = new CrazyCubeCfg(c);
		mSound = new CrazyCubeSound(c, eCubeMode);
		System.out.println("CrazyCubeGL.CrazyCubeGL()");
		this.eCubeMode = eCubeMode;
		LoadBitmap(c);
		InitTimer();
		cubeBuff = makeFloatBuffer(cubeMain);
		cubetexBuff = makeFloatBuffer(texCoords);
		tipBuff = makeFloatBuffer(cubeTip);
		tiptexBuff = makeFloatBuffer(tiptexCoords);

		mCube = crazyCube;

		mCube.InitTip();
		mCfg.loadConfig();		//加载配置
		mCfg.loadAdvConfig();    //加载高级功能
		
        CrazyCubeADPoints ccadp = new CrazyCubeADPoints(c, this);
		int iPoints = ccadp.GetADPoint(c);

        mCubeDevice = new CrazyCubeDevice(iPoints);		
	}

	
	public CrazyCubeShow mCcs = null;
	public CrazyCubeCfg mCfg = null;
	
	
	public E_STUDY_STATUS eRtuStudyLevel = E_STUDY_STATUS.STUDY_NONE;
	
	public static boolean isDisrupting = false;		//当前是否正在执行打乱魔方操作，如果是，在退出时要延时3秒
	
	boolean mbHelpPressed = false;      //帮助按钮是否按下
	boolean bRotateFast = false;       //旋转魔方的速度是否是快速，用于快速打乱魔方
	boolean bCubeFinish = false;	   //魔方是否已经完成。完成后置为TRUE,防止重复提示魔方完成
	
	
	public E_GAMEMODE eCubeMode = E_GAMEMODE.PLAY_MODE;       //该模式决定魔方的初始化方法
	
	Timer timer;
	TimerTask task;
	
	static int miToastTick = 0;
	
	
	int miSelectDelay = 0;		//选择物体是延时时间
	
	int miCubeClockSec = 0;            //计时器
	int miClockPic = 1;                //计时器的图片
	boolean mbClockRun = false;

		
	long mlTimeUsed = 200;       //旋转时显示一次用的时间，初始值设为200ms
	final static int TIME_USE1 = 50;
	final static int TIME_USE2 = 80;
    
	//触发魔方动画的类型
	public static final int MENU_CLICK = 1; 	//处理CrazyCubePlay的菜单事件
	public static final int TIP_TIMER = 2;		//智能提示定时器
	public static final int APPOFFER_TIMER = 3;	//广告显示定时器
	public static final int CUBE_CLOCK = 4;		//计时器
	public static final int CUBE_CAPTURE = 5;	//魔方截屏
	public static final int CUBE_SHARE = 6;		//共享消息
	public static final int CUBE_MSG = 7;		//TOAST
	public static final int CUBE_DISRUPT = 8;	//打乱魔方
	public static final int CUBE_AWARD = 9;		//积分奖励
	public static final int CUBE_WEIBO = 10;	//微博
	public static final int CUBE_USER = 11;		//切换用户
	public static final int CUBE_DAILYAWARD = 12;		//每日奖励
	public static final int CUBE_TUTORIAL = 13;
	public static final int CUBE_EXPORT = 14;
	public static final int CUBE_DISRUPTSTEP = 15;	//按步骤打乱魔方

    boolean[] bStepStatus = new boolean[7];    //魔方当前已完成的状态
	
	
	private E_SELECTION eButtonSelected = E_SELECTION.SELE_INVALID;    //识别哪一个按钮被按下  
	
//	protected MethodBridge cube;
	public CrazyCube mCube;
	
	int iScreenWidth;
	int iScreenHeight;
	int mFps;
	
	int miCenterPlateCnt = 0;     //禁止中间面旋转时，中间面被触摸的次数
	int miAutoModeCnt = 0;		//在自动模式时，魔方体被触摸的次数
	int miUndoTrainCnt = 0;       //学习模式时，没有按照提示操作的计数器
	
	int iAppOfferTimerCnt = 0;       //积分墙计数器
	boolean bHaveShowAppOffer = false;     //是否已经显示过积分墙提示
	
	public GL10 mGl;
	
	//光影及纹理处理
	float[] LightAmbient = new float[] { 0.5f, 0.5f, 0.5f, 1.0f };
    float[] LightDiffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    float[] LightPosition = new float[] { 0.0f, 0.0f, 2.0f, 1.0f };

    int[] texture = new int[E_TEXTURE.MAX_TEXTURE.ordinal()];			// Storage For 3 Textures
    Bitmap[] bitmap = new Bitmap[E_TEXTURE.MAX_TEXTURE.ordinal()];  
    float[] lightAmbient = new float[] { 0.8f, 0.8f, 0.8f, 1.0f };
    float[] matAmbient = new float[] { 0.8f, 0.8f, 0.8f, 1.0f };

	float lightDiffuse[] = new float[] { 1f, 1f, 1f, 1.0f };
	float[] lightPos = new float[] {0,0,3,1};	
	float matDiffuse[] = new float[] { 1f, 1f, 1f, 1.0f };

	//缓冲区
	FloatBuffer cubeBuff;
	FloatBuffer cubetexBuff;
	FloatBuffer tipBuff;
	FloatBuffer tiptexBuff;

    float fRotate = 0.0f;


	
	//屏幕相关处理
	int miTouchX = 0;
	int miTouchY = 0;
    int miStartX;     //鼠标操作的坐标
	int miStartY;
	int miEndX;
	int miEndY;
	Boolean mbHaveDirection = false;         //当前鼠标操作是否带有方向信息
    int miMousePrecision = 40;               //鼠标动作精度
    E_WORKMODE eGLMode = E_WORKMODE.DRAW;			//渲染模式
    
    //消息处理
    public Handler mHandler = new Handler(){   
        @Override  
		public void handleMessage(Message msg) 
		{
			if(eCubeMode == E_GAMEMODE.PLAY_MODE)
			{
			    // process incoming messages here
				switch(msg.what)
				{
				case MENU_CLICK:
					MenuItemClick();
					break;
				case TIP_TIMER:
					if(mCubeDevice.IsActived(E_DEVICE.INTELLIGENT_TIP))
					{
						mCube.AutoTip();
					}										
					break;
				case APPOFFER_TIMER:
//					CrazyCubeADPoints ccadp = new CrazyCubeADPoints(con);
//					ccadp.CheckCredits(con, res, ccs);
					
					mCcs.SetCubeStatus();        //保存魔方的激活状态，该状态决定下次启动时是否显示广告
					break;
				case CUBE_CLOCK:
					RaiseClockEvent();
//					DisplayCube();
					break;
				case CUBE_CAPTURE:
                	mCube.bAutoTip = false;
                	StopClock();
                	ShowShare();   
					break;
				case CUBE_SHARE:
				{
					mCube.bAutoTip = false;
					Bundle b = msg.getData();
					int num = b.getInt("picnum");
					String szTitle = b.getString("title");
			    	mCcs.ShowShare(num, szTitle);
					break;
				}
				case CUBE_USER:
					CrazyCubeShare.Authen(mCon, CrazyCubeGL.this);
					break;				
				case CUBE_DAILYAWARD:
				{
					int iDay = mCfg.GetDay();
					int iPoints = mCfg.GetAwardPoints();
					int iRet = mCcs.ShowDailyAward(iDay, iPoints);
        			if(CrazyCubeShow.DIALOG_SHARE == iRet)
        			{
        				ShowShare(String.format(mCon.getString(R.string.szGetDailyAward), iPoints));
        			}					
					break;
				}
				case CUBE_TUTORIAL:
					mCcs.ShowTutorial();
					break;
				case CUBE_EXPORT:
				{
					int iRet = mCcs.ShowExport();
					if(CrazyCubeShow.DIALOG_SHARE == iRet)
					{
						ShowShare(mCon.getString(R.string.smenuExport));
					}
					break;
				}	
				case CUBE_MSG:
				{
					Bundle b = msg.getData();
					String szMsg = b.getString("msg");
					mCcs.ShowMsg(szMsg, Toast.LENGTH_LONG, Color.GREEN);
					break;
				}
				case CUBE_AWARD:
				{
					Bundle b = msg.getData();
					int points = b.getInt("points");
					String szMsg = b.getString("message");
					int totalPoints = 0;
					CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, CrazyCubeGL.this);
					ccadp.AwardADPoints(points);
					totalPoints = ccadp.GetADPoint(mCon);
					String szAwardMsg = String.format(mRes.getString(R.string.tipAwardSuccess), points, totalPoints);
					szMsg = szMsg + "\r\n" + szAwardMsg;
					SendMsg(szMsg);
					break;
				}
				case CUBE_DISRUPT:
				{
                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
                	ResetClock();
                	isDisrupting = true;
                	bRotateFast = true;
                	mCube.InitCubeRandom(30);
                	mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);
                	mCube.ClearAutoPlayStatus();
                	mCube.GetAutoPlayStatus(bStepStatus);      //刷新当前完成状态
                	mCube.UpdateTipData();
                	bRotateFast = false;
                	isDisrupting = false;
                	//CrazyCubeMain.vMain.RequestRender(E_OPERATION.DISPLAY, false);
					break;
				}
				case CUBE_DISRUPTSTEP:
				{
					Bundle b = msg.getData();
					String szStep = b.getString("step");
                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
                	ResetClock();
                	isDisrupting = true;
                	bRotateFast = true;
                	Boolean bEvent = mCube.GetEventStatus();
                	mCube.SetEventStatus(false);
                	mCube.InitCubeByStep(szStep);
                	mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);
                	mCube.ClearAutoPlayStatus();
                	mCube.GetAutoPlayStatus(bStepStatus);      //刷新当前完成状态
                	mCube.SetEventStatus(bEvent);

                	mCube.AutoStep();
                	mCube.UpdateTipData();
                	bRotateFast = false;
                	isDisrupting = false;
                	//CrazyCubeMain.vMain.RequestRender(E_OPERATION.DISPLAY, false);
                	Message message = new Message();
					message.what = CrazyCubeGL.CUBE_CAPTURE;  
					mHandler.sendMessage(message);                	
					break;
				}
				case CUBE_WEIBO:
				{
					Bundle b = msg.getData();
					CrazyCubeShare.miWeiBoNum = b.getInt("num");
					CrazyCubeShare.miWeiBoTotal = b.getInt("total");
					CrazyCubeShare.mszWeiBoMsg = b.getString("message");
					CrazyCubeShare.Share(mCon, CrazyCubeGL.this);
					break;
				}
//				case EXIT_THREAD:
//					throw new RuntimeException();
				}
			}
			else
			{}
			super.handleMessage(msg); 
		}
  
    };
	

    
	protected void InitGlEnvirment(GL10 gl10)
	{
		System.out.println("CrazyCubeGL.InitEnvirment");
	
		mGl = gl10;
		BindTexture();
		mCube.SetEventStatus(true);   //使能事件处理
	}

	
	boolean bCubeClockRun = true;		//计时器是否在运行
	
	private void InitTimer()
	{
		if (eCubeMode == E_GAMEMODE.PLAY_MODE)
		{
			timer = new Timer();

			task = new TimerTask(){   
				public void run() {                      //这是一个一秒定时器
					
					
					//如果iToastTick不为0，则递减；
					if(miToastTick > 0)
					{
						miToastTick--;          //显示TOAST 是会设为4，在退出时根据该值来设定DELAY时间，以便TOAST能够被正确释放。
					}
			    	if(mCube.eGameMode == E_GAMEMODE.PLAY_MODE)
			    	{
			    		//只有PLAY模式下才显示计时器
						if(bCubeClockRun)
						{
							if (mbClockRun)
							{
								miCubeClockSec ++;
								miClockPic++;
								if(miClockPic > 8)
								{
									miClockPic = 1;
								}
								Message message = new Message();   
								message.what = CUBE_CLOCK;
								mHandler.sendMessage(message);
							}
						}
			    	}
					
					mCube.iTipTimerCnt++;
					if(mCube.iTipTimerCnt >= 10 && mCube.bAutoTip)        //TIP定时器十秒超时
					{
						mCube.iTipTimerCnt = 0;
						Message message = new Message();   
						message.what = TIP_TIMER;   
						mHandler.sendMessage(message);
					}
					if(!bHaveShowAppOffer)
					{
						iAppOfferTimerCnt++;
						if(iAppOfferTimerCnt >= CrazyCubeAD.CHECK_POINT_TIME)     //150秒检查，每次运行程序仅检查一次
						{
							bHaveShowAppOffer = true;
							Message message = new Message();   
							message.what = APPOFFER_TIMER;   
							mHandler.sendMessage(message);
							
						}
					}
					
					CrazyCubeShare.miWeiBoTick++;
					if((CrazyCubeShare.mbSendingWeibo) && (CrazyCubeShare.miWeiBoTick > CrazyCubeShare.WEIBO_DELAY))
					{
						CrazyCubeShare.miWeiBoNum++;
						
	            		Bundle b = new Bundle();
	            		b.putInt("num", CrazyCubeShare.miWeiBoNum);
	            		b.putInt("total", CrazyCubeShare.miWeiBoTotal);
	            		b.putString("message", CrazyCubeShare.mszWeiBoMsg);
	            		Message message = new Message();   
	            		message.what = CrazyCubeGL.CUBE_WEIBO;
	            		message.setData(b);
	            		mHandler.sendMessage(message);						
					}
				}
			};

			timer.schedule(task, 1000, 1000); 			
		}
	}
	
	//晃动事件
	public enum E_SHAKE
	{
		SHAKE_DISRUPT,
		SHAKE_AUTO,
		SHAKE_NEXT,
		SHAKE_PREV,
		SHAKE_NEXT3,
		SHAKE_PREV3,
	}

		
	
	//GL的渲染模式
	public enum E_WORKMODE
	{
		DRAW,
		PICK,
		SELECT_PLATE,
	}
	
	//与E_TEXTURE一起修改
	public enum E_SELECTION
	{
		SELE_BLK0,
		SELE_BLK1,
		SELE_BLK2,
		SELE_BLK3,
		SELE_BLK4,
		SELE_BLK5,
		SELE_BLK6,
		SELE_BLK7,
		SELE_BLK8,
		SELE_BLK9,
		SELE_BLK10,
		SELE_BLK11,
		SELE_BLK12,
		SELE_BLK13,
		SELE_BLK14,
		SELE_BLK15,
		SELE_BLK16,
		SELE_BLK17,
		SELE_BLK18,
		SELE_BLK19,
		SELE_BLK20,
		SELE_BLK21,
		SELE_BLK22,
		SELE_BLK23,
		SELE_BLK24,
		SELE_BLK25,
		SELE_BLK26,
		SELE_LEFT,
		SELE_RIGHT,
		SELE_DOWN,
		SELE_ZOOMOUT,
		SELE_ZOOMOUT_Pressed,
		SELE_ZOOMIN,
		SELE_ZOOMIN_Pressed,
		SELE_DISRUPT,
		SELE_DISRUPT_Pressed,
		SELE_AUTOPLAY,
		SELE_AUTOPLAY_Pressed,
		SELE_RESTART,
		SELE_RESTART_Pressed,
		SELE_PLAY,
		SELE_PLAY_Pressed,
		SELE_STUDY,
		SELE_STUDY_Pressed,
		SELE_SHARE,
		SELE_SHARE_Pressed,
		SELE_USER,
		SELE_USER_Pressed,
		SELE_DAILYAWARD,
		SELE_DAILYAWARD_Pressed,
		SELE_TUTORIAL,
		SELE_TUTORIAL_Pressed,
		SELE_EXPORT,
		SELE_EXPORT_Pressed,
		SELE_STAR,
		SELE_STAR_Pressed,
		SELE_MENU,
		SELE_MENU_Pressed,
		SELE_SETUP,
		SELE_SETUP_Pressed,
		SELE_HELP,
		SELE_HELP_Pressed,
		SELE_ABOUT,
		SELE_ABOUT_Pressed,
		SELE_ADVANCE,
		SELE_ADVANCE_Pressed,
		SELE_EXIT,
		SELE_EXIT_Pressed,
		SELE_TIMER,
		SELE_TIMER_Pressed,
		SELE_STEP,
		SELE_STEP_Pressed,		
		TIP_0,
		TIP_1,
		TIP_2,
		TIP_3,
		TIP_4,
		TIP_5,
		TIP_6,
		SELE_INVALID,
	}

	
	


    float[] cubeMain = new float[] 
    {
        // FRONT
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,
        // BACK
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
        // LEFT
        -0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
        // RIGHT
         0.5f, -0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,
        // TOP
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,
         -0.5f,  0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
        // BOTTOM
        -0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,
         
         // OTHER
         -0.55f, -0.55f,  0.5f,
          0.55f, -0.55f,  0.5f,
         -0.55f,  0.55f,  0.5f,
          0.55f,  0.55f,  0.5f,
          // FRONT
          -0.333f, -0.5f,  0.5f,
           0.333f, -0.5f,  0.5f,
          -0.333f,  0.5f,  0.5f,
           0.333f,  0.5f,  0.5f,

    };

    float[] cubeTip = new float[] 
	 {
	     // FRONT
		 -1.5f, -1.5f,  0.5f,
		  1.5f, -1.5f,  0.5f,
		 -1.5f,  1.5f,  0.5f,
		  1.5f,  1.5f,  0.5f,
		 // BACK
		 -1.5f, -1.5f, -0.5f,
		 -1.5f,  1.5f, -0.5f,
		  1.5f, -1.5f, -0.5f,
		  1.5f,  1.5f, -0.5f,
		 // LEFT
		 -1.5f, -1.5f,  0.5f,
		 -1.5f,  1.5f,  0.5f,
		 -1.5f, -1.5f, -0.5f,
		 -1.5f,  1.5f, -0.5f,
		 // RIGHT
		  1.5f, -1.5f, -0.5f,
		  1.5f,  1.5f, -0.5f,
		  1.5f, -1.5f,  0.5f,
		  1.5f,  1.5f,  0.5f,
		 // TOP
		 -1.5f,  1.5f,  0.5f,
		  1.5f,  1.5f,  0.5f,
		 -1.5f,  1.5f, -0.5f,
		  1.5f,  1.5f, -0.5f,
		 // BOTTOM
		 -1.5f, -1.5f,  0.5f,
		 -1.5f, -1.5f, -0.5f,
		  1.5f, -1.5f,  0.5f,
		  1.5f, -1.5f, -0.5f,
	 };

    
    float[] texCoords = new float[] 
    {
         // FRONT
         0.0f, 1.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         1.0f, 0.0f,
        // BACK
         1.0f, 0.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         0.0f, 1.0f,
        // LEFT
         1.0f, 0.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         0.0f, 1.0f,
        // RIGHT
         1.0f, 0.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         0.0f, 1.0f,
        // TOP
         0.0f, 0.0f,
         1.0f, 0.0f,
         0.0f, 1.0f,
         1.0f, 1.0f,
        // BOTTOM
         1.0f, 0.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         0.0f, 1.0f,
         // OTHER
         0.0f, 1.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         1.0f, 0.0f,
         // FRONT
         0.0f, 1.0f,
         1.0f, 1.0f,
         0.0f, 0.0f,
         1.0f, 0.0f,
    };

    float[] tiptexCoords = new float[] 
	  {
	  // FRONT
	   0.0f, 1.0f,
	   1.0f, 1.0f,
	   0.0f, 0.0f,
	   1.0f, 0.0f,
	  // BACK
	   1.0f, 0.0f,
	   1.0f, 1.0f,
	   0.0f, 0.0f,
	   0.0f, 1.0f,
	  // LEFT
	   1.0f, 0.0f,
	   1.0f, 1.0f,
	   0.0f, 0.0f,
	   0.0f, 1.0f,
	  // RIGHT
	   1.0f, 0.0f,
	   1.0f, 1.0f,
	   0.0f, 0.0f,
	   0.0f, 1.0f,
	  // TOP
	   0.0f, 0.0f,
	   1.0f, 0.0f,
	   0.0f, 1.0f,
	   1.0f, 1.0f,
	  // BOTTOM
	   1.0f, 0.0f,
	   1.0f, 1.0f,
	   0.0f, 0.0f,
	   0.0f, 1.0f,
	  };

	
	
	//加载位图
	private void LoadBitmap(Context c)
	{
		try
		{
			bitmap[E_TEXTURE.NOCOLOR.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.nocolor1);
			bitmap[E_TEXTURE.RED.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.red1);
			bitmap[E_TEXTURE.GREEN.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.green1);
			bitmap[E_TEXTURE.BLUE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.blue1);
			bitmap[E_TEXTURE.YELLOW.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow1);
			bitmap[E_TEXTURE.ORANGE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.orange1);
			bitmap[E_TEXTURE.WHITE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.white1);
			bitmap[E_TEXTURE.HIGHTLIGHT.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.highlight1);
			bitmap[E_TEXTURE.UNDESIGNATE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.undesignate1);
			bitmap[E_TEXTURE.ZOOMOUT.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.zoomout);
			bitmap[E_TEXTURE.ZOOMOUT_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.zoomout_pressed);
			bitmap[E_TEXTURE.ZOOMIN.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.zoomin);
			bitmap[E_TEXTURE.ZOOMIN_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.zoomin_pressed);
			bitmap[E_TEXTURE.DISRUPT.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.disrupt);
			bitmap[E_TEXTURE.DISRUPT_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.disrupt_pressed);
			bitmap[E_TEXTURE.AUTOPLAY.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.autoplay);
			bitmap[E_TEXTURE.AUTOPLAY_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.autoplay_pressed);
			bitmap[E_TEXTURE.STAR.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.star);
			bitmap[E_TEXTURE.STAR_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.star_pressed);
			bitmap[E_TEXTURE.MENU.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.menu);
			bitmap[E_TEXTURE.MENU_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.menu_pressed);		
			bitmap[E_TEXTURE.SETUP.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.setup);
			bitmap[E_TEXTURE.SETUP_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.setup_pressed);		
			bitmap[E_TEXTURE.RESTART.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.restart);
			bitmap[E_TEXTURE.RESTART_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.restart_pressed);
			bitmap[E_TEXTURE.PLAY.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.restart);
			bitmap[E_TEXTURE.PLAY_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.restart_pressed);
			bitmap[E_TEXTURE.STUDY.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.study);
			bitmap[E_TEXTURE.STUDY_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.study_pressed);
			bitmap[E_TEXTURE.HELP.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.help);
			bitmap[E_TEXTURE.HELP_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.help_pressed);
			bitmap[E_TEXTURE.ABOUT.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.about);
			bitmap[E_TEXTURE.ABOUT_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.about);
			bitmap[E_TEXTURE.ADVANCE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.advance);
			bitmap[E_TEXTURE.ADVANCE_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.advance);
			bitmap[E_TEXTURE.EXIT.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.exit);
			bitmap[E_TEXTURE.EXIT_Pressed.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.exit_pressed);
			
			bitmap[E_TEXTURE.ROTATE_U.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.u);
			bitmap[E_TEXTURE.ROTATE_u.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uu);
			bitmap[E_TEXTURE.ROTATE_D.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.d);
			bitmap[E_TEXTURE.ROTATE_d.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ud);
			bitmap[E_TEXTURE.ROTATE_F.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.f);
			bitmap[E_TEXTURE.ROTATE_f.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uf);
			bitmap[E_TEXTURE.ROTATE_B.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.b);
			bitmap[E_TEXTURE.ROTATE_b.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ub);
			bitmap[E_TEXTURE.ROTATE_L.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.l);
			bitmap[E_TEXTURE.ROTATE_l.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ul);
			bitmap[E_TEXTURE.ROTATE_R.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.r);
			bitmap[E_TEXTURE.ROTATE_r.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ur);
			bitmap[E_TEXTURE.ROTATE_Q.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.q);
			bitmap[E_TEXTURE.ROTATE_q.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uq);
			bitmap[E_TEXTURE.ROTATE_W.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.w);
			bitmap[E_TEXTURE.ROTATE_w.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uw);
			bitmap[E_TEXTURE.ROTATE_G.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.g);
			bitmap[E_TEXTURE.ROTATE_g.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ug);
			bitmap[E_TEXTURE.ROTATE_H.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.h);
			bitmap[E_TEXTURE.ROTATE_h.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uh);
			bitmap[E_TEXTURE.ROTATE_O.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.o);
			bitmap[E_TEXTURE.ROTATE_o.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uo);
			bitmap[E_TEXTURE.ROTATE_P.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.p);
			bitmap[E_TEXTURE.ROTATE_p.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.up);
			bitmap[E_TEXTURE.ROTATE_X.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.x);
			bitmap[E_TEXTURE.ROTATE_x.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ux);
			bitmap[E_TEXTURE.ROTATE_Y.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.y);
			bitmap[E_TEXTURE.ROTATE_y.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uy);
			bitmap[E_TEXTURE.ROTATE_Z.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.z);
			bitmap[E_TEXTURE.ROTATE_z.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uz);
			bitmap[E_TEXTURE.ROTATE_A.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.a);
			bitmap[E_TEXTURE.ROTATE_a.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ua);
			bitmap[E_TEXTURE.ROTATE_C.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.c);
			bitmap[E_TEXTURE.ROTATE_c.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uc);
			bitmap[E_TEXTURE.ROTATE_E.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.e);
			bitmap[E_TEXTURE.ROTATE_e.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ue);
			bitmap[E_TEXTURE.ROTATE_I.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.i);
			bitmap[E_TEXTURE.ROTATE_i.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ui);
			bitmap[E_TEXTURE.ROTATE_J.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.j);
			bitmap[E_TEXTURE.ROTATE_j.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uj);
			bitmap[E_TEXTURE.ROTATE_K.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.k);
			bitmap[E_TEXTURE.ROTATE_k.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.uk);
			
			bitmap[E_TEXTURE.TIP_START.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.start);
			bitmap[E_TEXTURE.TIP_SELE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.tip_sele);
			bitmap[E_TEXTURE.BUTTON_SELE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.button_sele);
			
			bitmap[E_TEXTURE.NUMBER_0.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n0);
			bitmap[E_TEXTURE.NUMBER_1.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n1);
			bitmap[E_TEXTURE.NUMBER_2.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n2);
			bitmap[E_TEXTURE.NUMBER_3.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n3);
			bitmap[E_TEXTURE.NUMBER_4.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n4);
			bitmap[E_TEXTURE.NUMBER_5.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n5);
			bitmap[E_TEXTURE.NUMBER_6.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n6);
			bitmap[E_TEXTURE.NUMBER_7.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n7);
			bitmap[E_TEXTURE.NUMBER_8.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n8);
			bitmap[E_TEXTURE.NUMBER_9.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.n9);
			bitmap[E_TEXTURE.CLOCK1.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time1);
			bitmap[E_TEXTURE.CLOCK2.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time2);
			bitmap[E_TEXTURE.CLOCK3.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time3);
			bitmap[E_TEXTURE.CLOCK4.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time4);
			bitmap[E_TEXTURE.CLOCK5.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time5);
			bitmap[E_TEXTURE.CLOCK6.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time6);
			bitmap[E_TEXTURE.CLOCK7.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time7);
			bitmap[E_TEXTURE.CLOCK8.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.time8);
			bitmap[E_TEXTURE.MINUTE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.nminute);
			bitmap[E_TEXTURE.DIVIDE.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.ndivide);
			bitmap[E_TEXTURE.STEP.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.step);
			bitmap[E_TEXTURE.ICON.ordinal()] = BitmapFactory.decodeResource(c.getResources(), R.drawable.icon);
		}
		catch(Exception e)
		{
			SendMsg("LoadBitmap Exception:" + e.getMessage());
		}
		return;
	}
	
	void BitmapRecycle()
	{
		for(int i = 0; i < E_TEXTURE.MAX_TEXTURE.ordinal(); i++)
		{
			if(bitmap[1] != null)
			{
				bitmap[i].recycle();
				bitmap[i] = null;
			}
		}
	}
	
	private void BindTexture()
	{
		// 绑定纹理  
		IntBuffer t_b = IntBuffer.allocate(E_TEXTURE.MAX_TEXTURE.ordinal());  
		mGl.glGenTextures(E_TEXTURE.MAX_TEXTURE.ordinal(), t_b);// 设置纹理的个数  
		texture = t_b.array();		

		for(int i = 0; i < E_TEXTURE.MAX_TEXTURE.ordinal(); i++)
		{
			mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[i]);  
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[i], 0);  
			mGl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);  
			mGl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);  
		}
	}
	
	protected static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		synchronized (this) {
			this.iScreenWidth = width;
			this.iScreenHeight = height;
			float fWidth = width;
			miMousePrecision = (int)(50 * (fWidth / 240));
			System.out.println("CrazyCubeGL.surfaceChanged");
		}
	}


	public static void Delay(int iDelay)
	{
    	try {
    		System.out.println("Delay " + iDelay + "ms");
			Thread.sleep(iDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

				
    //初始化主ViewPort，用于显示魔方及相关按钮
    private void InitViewPortMain() 
    {
        mGl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mGl.glDepthFunc(GL10.GL_LEQUAL);
        mGl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        
		mGl.glMatrixMode(GL10.GL_PROJECTION);
		mGl.glLoadIdentity();
		if(eCubeMode == E_GAMEMODE.PLAY_MODE)
		{
			mGl.glOrthof(0, 0, iScreenWidth, iScreenWidth, -10, 10);
			mGl.glViewport(0, (iScreenHeight - iScreenWidth)/2, iScreenWidth, iScreenWidth);	
		}
		else
		{
			//E_GAMEMODE.STUDY_MODE
			if (iScreenWidth < iScreenHeight)
			{
				mGl.glOrthof(0, 0, iScreenWidth, iScreenWidth, -10, 10);
				mGl.glViewport(0, (iScreenHeight-iScreenWidth)/2, iScreenWidth, iScreenWidth);
			}
			else
			{
				mGl.glOrthof(0, 0, iScreenHeight, iScreenHeight, -10, 10);
				mGl.glViewport((iScreenWidth - iScreenHeight)/2, 0, iScreenHeight, iScreenHeight);
			}
		}
		
		GLU.gluPerspective(mGl, 45.0f, 1f, 1f, 200f);
        
        mGl.glMatrixMode(GL10.GL_MODELVIEW);
        mGl.glLoadIdentity();                      //涓嶈兘灏?

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glEnable(GL10.GL_TEXTURE_2D);
        	mGl.glDisable(GL10.GL_COLOR_MATERIAL);
        	mGl.glColor4f(1, 1, 1, 1);
        }
        else
        {
        	mGl.glDisable(GL10.GL_TEXTURE_2D);
            mGl.glDisable(GL10.GL_LIGHTING);
            mGl.glDisable(GL10.GL_LIGHT0);
        }

		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
		
        mGl.glEnable(GL10.GL_DEPTH_TEST);

       	mGl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mGl.glClearDepthf(1.0f);

        mGl.glEnable(GL10.GL_CULL_FACE);
        mGl.glShadeModel(GL10.GL_SMOOTH);
    }

    //初始化公式提示区
    private void InitTipViewPort(int iIndex) 
    {
        mGl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        mGl.glClearDepthf(1.0f);
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mGl.glDepthFunc(GL10.GL_LEQUAL);
        mGl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        
		mGl.glMatrixMode(GL10.GL_PROJECTION);
		mGl.glLoadIdentity();
		mGl.glOrthof(0, 0, 30, 30, -10, 10);
		
		int iDelta = iScreenWidth / 8;
		mGl.glViewport(iDelta/2 + iDelta * iIndex, ((iScreenHeight - iScreenWidth)/2 - iDelta)/2, iDelta, iDelta);
		GLU.gluPerspective(mGl, 45.0f, 1f, 1f, 400f);
        
        mGl.glMatrixMode(GL10.GL_MODELVIEW);
        mGl.glLoadIdentity();                      

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glEnable(GL10.GL_TEXTURE_2D);
        	mGl.glColor4f(1, 1, 1, 1);
        }
        else
        {
        	mGl.glDisable(GL10.GL_TEXTURE_2D);
        }

		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
		
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glDepthFunc(GL10.GL_LEQUAL);

        mGl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        mGl.glClearDepthf(1.0f);

        mGl.glEnable(GL10.GL_CULL_FACE);
        mGl.glShadeModel(GL10.GL_SMOOTH);
    }


    //初始化计时器显示区
    private void InitTimeViewPort(int iIndex) 
    {
    	if (iIndex > 5)
    	{
    		return;
    	}
    	E_WORKMODE eMode = GetGLMode();
        mGl.glShadeModel(GL10.GL_SMOOTH);
        mGl.glClearDepthf(1.0f);
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mGl.glDepthFunc(GL10.GL_LEQUAL);
        mGl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        
		mGl.glMatrixMode(GL10.GL_PROJECTION);
		mGl.glLoadIdentity();
		float fDelta = iScreenWidth / 240.0f;
		int iLeft = 10;
		if (0 == iIndex)
		{
			mGl.glOrthof(0, 0, (int)(18.0f * fDelta), (int)(18.0f * fDelta), -10, 10);
			mGl.glViewport(0 + (int)(iLeft * fDelta), (iScreenHeight + iScreenWidth)/2 , (int)(18.0f * fDelta), (int)(18.0f * fDelta));
			GLU.gluPerspective(mGl, 45.0f, 1f, 1f, 400f);
		}
		else
		{
			mGl.glOrthof(0, 0, (int)(12.0f * fDelta), (int)(18.0f * fDelta), -10, 10);
			mGl.glViewport((int)(iLeft * fDelta) + (int)(24.0f * fDelta) + (iIndex-1) * (int)(12.0f * fDelta), (iScreenHeight + iScreenWidth)/2 , (int)(12.0f * fDelta), (int)(18.0f * fDelta));
			GLU.gluPerspective(mGl, 45.0f, 2.0f/3.0f, 1f, 400f);
		}
        
        mGl.glMatrixMode(GL10.GL_MODELVIEW);
        mGl.glLoadIdentity();                      

        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glEnable(GL10.GL_TEXTURE_2D);
        	mGl.glColor4f(1, 1, 1, 1);
        }
        else
        {
        	mGl.glDisable(GL10.GL_TEXTURE_2D);
        }

		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
		
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glDepthFunc(GL10.GL_LEQUAL);

        mGl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        mGl.glClearDepthf(1.0f);

        mGl.glEnable(GL10.GL_CULL_FACE);
        mGl.glShadeModel(GL10.GL_SMOOTH);
    }


    //初始步骤显示区
    private void InitStepViewPort(int iIndex) 
    {
    	E_WORKMODE eGLMode = GetGLMode();
        mGl.glShadeModel(GL10.GL_SMOOTH);
        mGl.glClearDepthf(1.0f);
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mGl.glDepthFunc(GL10.GL_LEQUAL);
        mGl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        
		mGl.glMatrixMode(GL10.GL_PROJECTION);
		mGl.glLoadIdentity();
		float fDelta = iScreenWidth / 240.0f;
		int iLeft = 130;
		if (0 == iIndex)
		{
			mGl.glOrthof(0, 0, (int)(18.0f * fDelta), (int)(18.0f * fDelta), -10, 10);
			mGl.glViewport((int)(iLeft * fDelta), (iScreenHeight + iScreenWidth)/2 , (int)(18.0f * fDelta), (int)(18.0f * fDelta));
			GLU.gluPerspective(mGl, 45.0f, 1f, 1f, 400f);
		}
		else
		{
			mGl.glOrthof(0, 0, (int)(12.0f * fDelta), (int)(18.0f * fDelta), -10, 10);
			mGl.glViewport((int)(iLeft * fDelta) + (int)(24.0f * fDelta) + (iIndex-1) * (int)(12.0f * fDelta), (iScreenHeight + iScreenWidth)/2 , (int)(12.0f * fDelta), (int)(18.0f * fDelta));
			GLU.gluPerspective(mGl, 45.0f, 2.0f/3.0f, 1f, 400f);
		}
        
        mGl.glMatrixMode(GL10.GL_MODELVIEW);
        mGl.glLoadIdentity();                      

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glEnable(GL10.GL_TEXTURE_2D);
        	mGl.glColor4f(1, 1, 1, 1);
        }
        else
        {
        	mGl.glDisable(GL10.GL_TEXTURE_2D);
        }

		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		mGl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
		
        mGl.glEnable(GL10.GL_DEPTH_TEST);
        mGl.glDepthFunc(GL10.GL_LEQUAL);

        mGl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        mGl.glClearDepthf(1.0f);

        mGl.glEnable(GL10.GL_CULL_FACE);
        mGl.glShadeModel(GL10.GL_SMOOTH);
    }

    
    //绑定纹理
    private void BindVertexAndTexture(FloatBuffer boxbuf, FloatBuffer texbuf)
    {
		mGl.glVertexPointer(3, GL10.GL_FLOAT, 0, boxbuf);
		mGl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		mGl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texbuf);
		mGl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		return;
    }


	//显示
    public void DisplayCube()
    {
    	if(mGl == null)
    	{
    		return;
    	}
    	ShowCube();
        ShowUnseenPlate(mCube.DREAM_CUBE_CACULATE);
        ShowLButton();
        ShowRButton();
        ShowProgressStars();
    	ShowTime(miCubeClockSec);
    	ShowStep(mCube.GetStepBar());
    	ShowCubeTip();
		//SavePNG2("opengl.png", (Activity)con, gl);
		return;
    }
    
    
    private void ShowCube()
    {
        InitViewPortMain();
        mCube.XChgDreamCube4Display(mCube.DREAM_CUBE_CACULATE);
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, - 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);
        
    }
    
    private E_TEXTURE GetClockTexture()
    {
    	E_TEXTURE eTexture = E_TEXTURE.CLOCK1;
    	switch(miClockPic)
    	{
    	case 1:
    		eTexture = E_TEXTURE.CLOCK1;
    		break;
    	case 2:
    		eTexture = E_TEXTURE.CLOCK2;
    		break;
    	case 3:
    		eTexture = E_TEXTURE.CLOCK3;
    		break;
    	case 4:
    		eTexture = E_TEXTURE.CLOCK4;
    		break;
    	case 5:
    		eTexture = E_TEXTURE.CLOCK5;
    		break;
    	case 6:
    		eTexture = E_TEXTURE.CLOCK6;
    		break;
    	case 7:
    		eTexture = E_TEXTURE.CLOCK7;
    		break;
    	case 8:
    		eTexture = E_TEXTURE.CLOCK8;
    		break;
    	default:
        	eTexture = E_TEXTURE.CLOCK1;
        	break;

    	}
    	return eTexture;
    }
    
    private E_TEXTURE GetClockTexture(int iIndex, int iNum)
    {
    	E_TEXTURE eTexture = E_TEXTURE.NOCOLOR;
    	
    	if(0 == iIndex)
    	{
    		return GetClockTexture();
    	}
    	if(3 == iIndex)
    	{
    		return E_TEXTURE.MINUTE;
    	}
    	
    	switch(iNum)
    	{
    	case 0:
    		eTexture = E_TEXTURE.NUMBER_0;
    		break;
    	case 1:
    		eTexture = E_TEXTURE.NUMBER_1;
    		break;
    	case 2:
    		eTexture = E_TEXTURE.NUMBER_2;
    		break;
    	case 3:
    		eTexture = E_TEXTURE.NUMBER_3;
    		break;
    	case 4:
    		eTexture = E_TEXTURE.NUMBER_4;
    		break;
    	case 5:
    		eTexture = E_TEXTURE.NUMBER_5;
    		break;
    	case 6:
    		eTexture = E_TEXTURE.NUMBER_6;
    		break;
    	case 7:
    		eTexture = E_TEXTURE.NUMBER_7;
    		break;
    	case 8:
    		eTexture = E_TEXTURE.NUMBER_8;
    		break;
    	case 9:
    		eTexture = E_TEXTURE.NUMBER_9;
    		break;
    	default:
    		eTexture = E_TEXTURE.NOCOLOR;
    		break;
    	}
    	return eTexture;
    }
    
    private E_TEXTURE GetStepTexture(int iIndex, char cNum)
    {
    	E_TEXTURE eTexture = E_TEXTURE.NOCOLOR;
    	
    	if(0 == iIndex)
    	{
    		return E_TEXTURE.STEP;
    	}
    	
    	switch(cNum)
    	{
    	case '0':
    		eTexture = E_TEXTURE.NUMBER_0;
    		break;
    	case '1':
    		eTexture = E_TEXTURE.NUMBER_1;
    		break;
    	case '2':
    		eTexture = E_TEXTURE.NUMBER_2;
    		break;
    	case '3':
    		eTexture = E_TEXTURE.NUMBER_3;
    		break;
    	case '4':
    		eTexture = E_TEXTURE.NUMBER_4;
    		break;
    	case '5':
    		eTexture = E_TEXTURE.NUMBER_5;
    		break;
    	case '6':
    		eTexture = E_TEXTURE.NUMBER_6;
    		break;
    	case '7':
    		eTexture = E_TEXTURE.NUMBER_7;
    		break;
    	case '8':
    		eTexture = E_TEXTURE.NUMBER_8;
    		break;
    	case '9':
    		eTexture = E_TEXTURE.NUMBER_9;
    		break;
    	case '/':
    		eTexture = E_TEXTURE.DIVIDE;
    		break;
    	default:
    		eTexture = E_TEXTURE.NOCOLOR;
    		break;
    	}
    	return eTexture;
    }

    
    private void ShowTime(int iSecond)
    {
    	if(!mCfg.cubeConfig.bShowTimer)
    	{
    		return;
    	}
		if(!mCubeDevice.IsActived(E_DEVICE.CLOCK_AREA))
		{
			return;
		}
    	if (!mCfg.GetAdvStatus(CrazyCubeCfg.CLOCK_KEY))
    	{
    		return;
    	}
    	if(eCubeMode != E_GAMEMODE.PLAY_MODE)
    	{
    		//只有PLAY模式下才显示计时器
    		return;
    	}
    	iSecond = iSecond % 3600;
    	int iMin = 0;
    	int iSec = 0;
    	int iMin0 = 0;
    	int iMin1 = 0;
    	int iSec0 = 0;
    	int iSec1 = 0;
    	
    	iMin = iSecond / 60;
    	iSec = iSecond % 60;
    	
    	iMin0 = iMin / 10;
    	iMin1 = iMin % 10;
    	
    	iSec0 = iSec / 10;
    	iSec1 = iSec % 10;
    	
    	if(eGLMode == E_WORKMODE.PICK)
    	{
    		mGl.glColor4f(0, 0, 1, 1);
    	}
    	ShowTime(0,0);
    	ShowTime(1,iMin0);
    	ShowTime(2,iMin1);
    	ShowTime(3,2);
    	ShowTime(4,iSec0);
    	ShowTime(5,iSec1);

    }
    
    private void ShowTime(int iIndex, int iNum)
    {
    	InitTimeViewPort(iIndex);
    	
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
	             0.0f, 0.0f, 5.0f,
	             0.0f, 0.0f, 0.0f,
	             0.0f, 1.0f, 0.0f);        	

        mGl.glScalef(3f, 3f, 3f);
       
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	E_TEXTURE eTexture = GetClockTexture(iIndex, iNum);
        	if(eTexture != E_TEXTURE.NOCOLOR)
        	{
        		mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eTexture.ordinal()]);
        	}
        }
        
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        if (0 == iIndex)
        {
        	mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        }
        else
        {
            mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 28, 4);
        }

    	return;
    }

    private void ShowStep(String szStep)
    {
    	if(!mCfg.cubeConfig.bShowStep)
    	{
    		return;
    	}
		if(!mCubeDevice.IsActived(E_DEVICE.STEP_AREA))
		{
			return;
		}
    	if (!mCfg.GetAdvStatus(CrazyCubeCfg.STEP_KEY))
    	{
    		//return;
    	}
    	if(eCubeMode != E_GAMEMODE.PLAY_MODE && eCubeMode != E_GAMEMODE.CAPTURE_MODE)
    	{
    		//只有PLAY模式下才显示计步器
    		return;
    	}

    	if(eGLMode == E_WORKMODE.PICK)
    	{
    		mGl.glColor4f(0, 0, 1, 1);
    	}
    	ShowStep(0,'0');
    	for(int i = 0; i < szStep.length(); i++)
    	{
    		char cNum = szStep.charAt(i);
    		ShowStep(i + 1,cNum);	
    	}
    }
    
    private void ShowStep(int iIndex, char cNum)
    {
    	InitStepViewPort(iIndex);
    	
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
	             0.0f, 0.0f, 5.0f,
	             0.0f, 0.0f, 0.0f,
	             0.0f, 1.0f, 0.0f);        	

        mGl.glScalef(3f, 3f, 3f);
       
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	E_TEXTURE eTexture = GetStepTexture(iIndex, cNum);
        	if(eTexture != E_TEXTURE.NOCOLOR)
        	{
        		mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eTexture.ordinal()]);
        	}
        }
        
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        if (0 == iIndex)
        {
        	mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        }
        else
        {
            mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 28, 4);
        }

    	return;
    }

    
    
    //对象选择操作
    E_SELECTION SelectObj(int x, int y)
    {
    	E_SELECTION eObj = E_SELECTION.SELE_INVALID;
    	int iObj = 0;
    	InitViewPortMain();    	
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        
//        if(eCubeMode != E_GAMEMODE.STUDY_MODE)
    	{
        	mCube.XChgDreamCube4Display(mCube.DREAM_CUBE_CACULATE);
    		mGl.glColor4f(0, 0, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
    		mGl.glColor4f(0, 1, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
    		mGl.glColor4f(0, 1, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);
    		mGl.glColor4f(1, 0, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
    		mGl.glColor4f(1, 0, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
    		mGl.glColor4f(1, 1, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);
    		iObj = GetObj(x, y);
    		if(iObj != 0)
    		{
    			switch(iObj)
    			{
    			case 1:
    				eObj = E_SELECTION.SELE_BLK24;
    				break;
    			case 2:
    				eObj = E_SELECTION.SELE_BLK25;
    				break;
    			case 3:
    				eObj = E_SELECTION.SELE_BLK26;
    				break;
    			case 4:
    				eObj = E_SELECTION.SELE_BLK15;
    				break;
    			case 5:
    				eObj = E_SELECTION.SELE_BLK16;
    				break;
    			case 6:
    				eObj = E_SELECTION.SELE_BLK17;
    				break;
//    			default:
//    				eObj = -1;
//    				break;
    			}
    			return eObj;
    		}

    		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    		
            mGl.glColor4f(0, 0, 1, 1);
    	    DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
    	    mGl.glColor4f(0, 1, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
    		mGl.glColor4f(0, 1, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);
    		mGl.glColor4f(1, 0, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);
    		mGl.glColor4f(1, 0, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);
    	    mGl.glColor4f(1, 1, 0, 1);
    	    DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);
    		iObj = GetObj(x, y);
    		if(iObj != 0)
    		{
    			switch(iObj)
    			{
    			case 1:
    				eObj = E_SELECTION.SELE_BLK6;
    				break;
    			case 2:
    				eObj = E_SELECTION.SELE_BLK7;
    				break;
    			case 3:
    				eObj = E_SELECTION.SELE_BLK8;
    				break;
    			case 4:
    				eObj = E_SELECTION.SELE_BLK23;
    				break;
    			case 5:
    				eObj = E_SELECTION.SELE_BLK14;
    				break;
    			case 6:
    				eObj = E_SELECTION.SELE_BLK5;
    				break;
//    			default:
//    				eObj = -1;
//    				break;
    			}
    			return eObj;
    		}

    		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    		
    		mGl.glColor4f(0, 0, 1, 1);
    	    DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);
    		mGl.glColor4f(0, 1, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);
    		mGl.glColor4f(0, 1, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
    		mGl.glColor4f(1, 0, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
    		mGl.glColor4f(1, 0, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);
    		mGl.glColor4f(1, 1, 0, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
    		mGl.glColor4f(1, 1, 1, 1);
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
    		iObj = GetObj(x, y);
    		if(iObj != 0)
    		{
    			switch(iObj)
    			{
    			case 1:
    				eObj = E_SELECTION.SELE_BLK2;
    				break;
    			case 2:
    				eObj = E_SELECTION.SELE_BLK11;
    				break;
    			case 3:
    				eObj = E_SELECTION.SELE_BLK18;
    				break;
    			case 4:
    				eObj = E_SELECTION.SELE_BLK19;
    				break;
    			case 5:
    				eObj = E_SELECTION.SELE_BLK20;
    				break;
    			case 6:
    				eObj = E_SELECTION.SELE_BLK21;
    				break;
    			case 7:
    				eObj = E_SELECTION.SELE_BLK22;
    				break;
//    			default:
//    				eObj = -1;
//    				break;
    			}
    			return eObj;
    		}    		
    	}
    	
    	mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		ShowUnseenPlate(mCube.DREAM_CUBE_CACULATE);        	
		
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_LEFT;
				break;
			case 2:
				eObj = E_SELECTION.SELE_RIGHT;
				break;
			case 3:
				eObj = E_SELECTION.SELE_DOWN;
				break;
//			default:
//				eObj = E_Selection.SELE_INVALID;
//				break;
			}
			return eObj;
		}

		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        ShowLButton();
        	
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_MENU;
				break;
			case 2:
				eObj = E_SELECTION.SELE_HELP;
				break;
			case 3:
				eObj = E_SELECTION.SELE_ZOOMOUT;
				break;
			case 4:
				eObj = E_SELECTION.SELE_DISRUPT;
				break;
			case 5:
				eObj = E_SELECTION.SELE_AUTOPLAY;
				break;
			case 6:
				eObj = E_SELECTION.SELE_RESTART;
				break;
			case 7:
				eObj = E_SELECTION.SELE_STUDY;
				break;
			}
			return eObj;
		}
		
		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        ShowRButton();
    	
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_ZOOMIN;
				break;
			case 2:
				eObj = E_SELECTION.SELE_SETUP;
				break;
			}
			return eObj;
		}

		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		ShowProgressStars();        	
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_STAR;
				break;
			}
			return eObj;
		}
		
		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		ShowCubeTip();
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.TIP_0;
				break;
			case 2:
				eObj = E_SELECTION.TIP_1;
				break;
			case 3:
				eObj = E_SELECTION.TIP_2;
				break;
			case 4:
				eObj = E_SELECTION.TIP_3;
				break;
			case 5:
				eObj = E_SELECTION.TIP_4;
				break;
			case 6:
				eObj = E_SELECTION.TIP_5;
				break;
			case 7:
				eObj = E_SELECTION.TIP_6;
				break;
			}
			return eObj;
		}

		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	ShowTime(miCubeClockSec);
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_TIMER;
				break;
			}
			return eObj;
		}
		
		mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	ShowStep(mCube.GetStepBar());
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				eObj = E_SELECTION.SELE_STEP;
				break;
			}
			return eObj;
		}


		return eObj;
		
    }
    
    void ShowAutoModeTouchTip()
    {
        if (mCube.eGameMode == E_GAMEMODE.AUTO_MODE)    //如果是自动模式
        {
        	miAutoModeCnt ++;
        	if (miAutoModeCnt > 3)
        	{
	        	miAutoModeCnt = 0;
				mCcs.ShowMsg(R.string.tipAutoModeTouch);
        	}
        }
    }

    //进一步在魔方中选择操作的面
    E_PLATE SelectPlate(int iBlock, int x, int y)
    {
    	E_PLATE ePlate = E_PLATE.NOPLATE;
    	int iObj = 0;
		mCube.XChgDreamCube4Display(mCube.DREAM_CUBE_CACULATE);
		InitViewPortMain();
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        switch(iBlock)
        {
        case 2:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);	
        	break;
        case 5:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);
        	break;
        case 6:
    	    DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        	break;
        case 7:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
    		break;
        case 8:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);
    		break;
        case 11:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);
        	break;
        case 14:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);
        	break;
        case 15:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
    		break;
        case 16:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
    		break;
        case 17:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);
    		break;
        case 18:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
    		break;
        case 19:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
    		break;
        case 20:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);
    		break;
        case 21:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
    		break;
        case 22:
    		DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
    		break;
        case 23:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);
        	break;
        case 24:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        	break;
        case 25:
			DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
			break;
        case 26:
        	DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);
        	break;
        }
        if(miSelectDelay >= 10)
        {
        	miSelectDelay = 200;
        }
		iObj = GetObj(x, y);
		if(iObj != 0)
		{
			switch(iObj)
			{
			case 1:
				ePlate = E_PLATE.FRONT;
				break;
			case 2:
				ePlate = E_PLATE.BACK;
				break;
			case 3:
				ePlate = E_PLATE.LEFT;
				break;
			case 4:
				ePlate = E_PLATE.RIGHT;
				break;
			case 5:
				ePlate = E_PLATE.UP;
				break;
			case 6:
				ePlate = E_PLATE.DOWN;
				break;
//			default:
//				eObj = -1;
//				break;
			}
		}
		return ePlate;
    }
    
    //获取对象
    private int GetObj(int x, int y)
    {
    	mGl.glFinish();
    	mGl.glFinish();
//    	gl.glFlush();
//    	egl.eglSwapBuffers(dpy, surface);
    	Delay(miSelectDelay);
     	ByteBuffer pixel = ByteBuffer.allocate(4);
    	byte data[] = new byte[4];
    	mGl.glReadPixels(x , iScreenHeight - y, 1, 1, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixel);
    	data = pixel.array();

		data[0] = (byte) Math.abs(data[0]);
		data[1] = (byte) Math.abs(data[1]);
		data[2] = (byte) Math.abs(data[2]);
		data[0] = (byte) (data[0] << 2);
		data[1] = (byte) (data[1] << 1);
		
		int iObj = data[0] + data[1] + data[2];
		return iObj;
    }
    
   
    //显示单个方块
    private void DisplaySingleCube(ST_DreamCube dreamcube, float fX, float fY,float fZ,float fRX,float fRY,float fRZ, int cubeIndex)
    {
    	E_WORKMODE eMode = GetGLMode();
        E_COLOR cU, cD, cF, cB, cL, cR;
        cU = dreamcube.cube_status[cubeIndex].Color.U;
        cD = dreamcube.cube_status[cubeIndex].Color.D;
        cF = dreamcube.cube_status[cubeIndex].Color.F;
        cB = dreamcube.cube_status[cubeIndex].Color.B;
        cL = dreamcube.cube_status[cubeIndex].Color.L;
        cR = dreamcube.cube_status[cubeIndex].Color.R;
        
        BindVertexAndTexture(cubeBuff, cubetexBuff);

        mGl.glLoadIdentity();                //可不要
        //gluLookAtf(-5.0f, 5.0f, 5.0f,0.0f, 0.0f, 0.0f,0.0f, 1.0f, 0.0f);
        GLU.gluLookAt(mGl,
                -5.0f, 5.0f, 5.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);

        //gl.glTranslatef((float)0.3, (float)0.8, (float)-0.3);   //原来240*240时的效果
        mGl.glScalef(mCfg.cubeConfig.fZoomFactor, mCfg.cubeConfig.fZoomFactor, mCfg.cubeConfig.fZoomFactor);
        mGl.glTranslatef((float)0.3, (float)0.3-1, (float)-0.3);
        mGl.glFrontFace(GL10.GL_CCW);
        if (mCube.fYDegree != 0)
        {
            mGl.glRotatef(mCube.fYDegree, 0.0f, 1.0f, 0.0f);
        }
        if (fRX != 0 || fRY != 0 || fRZ != 0)
        {
            mGl.glRotatef(fRotate, fRX, fRY, fRZ);
        }

        //gl.Rotatef(yrot, 0.0f, 1.0f, 0.0f);
        //gl.Translatef(fX + (float)0.3, fY + (float)0.8, fZ - (float)0.3);
        mGl.glTranslatef(fX, fY, fZ);
       
        // FRONT AND BACK
        
                
        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cF.ordinal()]);
        }
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(0, 0, 1, 1);
        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cB.ordinal()]);
        }
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(0, 1, 0, 1);
        }
        mGl.glNormal3f(0.0f, 0.0f, -1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

        // LEFT AND RIGHT

        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cL.ordinal()]);
        }       
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(0, 1, 1, 1);
        }

        mGl.glNormal3f(-1.0f, 0.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cR.ordinal()]);
        }
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(1, 0, 0, 1);
        }

        mGl.glNormal3f(1.0f, 0.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

        // TOP AND BOTTOM

        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cU.ordinal()]);
        }
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(1, 0, 1, 1);
        }

        mGl.glNormal3f(0.0f, 1.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[cD.ordinal()]);
        }
        else if (eMode == E_WORKMODE.SELECT_PLATE)
        {
        	mGl.glColor4f(1, 1, 0, 1);
        }

        mGl.glNormal3f(0.0f, -1.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
    }


    
    
    
    //TIP BUTTON按下后执行的动作
    void TipButtonPressed(int iTipIndex)
    {
		if(mbHelpPressed)
		{
			int iRet = 0;
	    	iRet = mCcs.ShowHelp(mRes.getString(R.string.titleTip),mRes.getString(R.string.helpTip), R.drawable.start);
			if(CrazyCubeShow.DIALOG_SHARE == iRet)
			{
				ShowShare(mCon.getString(R.string.szGetHelp));
			}	    	
			SetHelpStatus(false);
			return;
		}

    	char cStep;
    	String szStep = "";
    	boolean bDirection = false;
    	
    	if (iTipIndex > 6)
    	{
    		return;
    	}

//    	ResetTipCurrentIndex();
    	
    	if (iTipIndex == mCube.iCurrentIndex)
    	{
    		return;
    	}
    	if (iTipIndex < mCube.iCurrentIndex)
    	{
    		bDirection = false;     //向后
    		//向后操作
    		for(int i = mCube.iCurrentIndex; i > iTipIndex; i--)
    		{
    	    	cStep = mCube.acTip[i].cColorStep;
	    		if (Character.isLowerCase(cStep))
	    		{
	    			cStep = Character.toUpperCase(cStep);
	    		}
	    		else
	    		{
	    			cStep = Character.toLowerCase(cStep);
	    		}
	    		szStep += cStep;
    		}
    	}
    	else
    	{
    		//向前操作
    		bDirection = true;
    		for(int i = mCube.iCurrentIndex + 1; i <= iTipIndex; i++)
    		{
    			cStep = mCube.acTip[i].cColorStep;
    			szStep += cStep;
    		}
    	}

		mCube.RotationByColorFormula(szStep, bDirection);    //公式是按色面来记录的
    }
    
    //晃动魔方后执行TIP操作
    void TipShake(boolean bDirection)
    {

    	char cStep;
    	String szStep = "";
    	
    	if (!bDirection)
    	{
    		//向后操作
	    	cStep = mCube.acTip[mCube.iCurrentIndex].cColorStep;
	    	if(cStep == '@' || cStep == '0')
	    	{
	    		return;
	    	}
    		if (Character.isLowerCase(cStep))
    		{
    			cStep = Character.toUpperCase(cStep);
    		}
    		else
    		{
    			cStep = Character.toLowerCase(cStep);
    		}
    		szStep += cStep;
    	}
    	else
    	{
    		if(mCube.iCurrentIndex > 5)
    		{
    			return;
    		}
    		//向前操作
			cStep = mCube.acTip[mCube.iCurrentIndex + 1].cColorStep;
			if(cStep == '0')
			{
				return;
			}
			szStep += cStep;
    	}

		mCube.RotationByColorFormula(szStep, bDirection);    //公式是按色面来记录的
    }

    
    void IntelligentTipText(char cColorStep)
    {
        if (!mCfg.cubeConfig.bIntelligentText)
        {
            return;
        }

    	String szMsg = "";
    	int colors = Color.WHITE;
    	switch (cColorStep)
    	{
    	case 'U':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szU);
    		colors = Color.rgb(250, 110, 14);
    		break;
    	case 'u':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szU);
    		colors = Color.rgb(250, 110, 14);
    		break;
    	case 'D':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szD);
    		colors = Color.RED;
    		break;
    	case 'd':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szD);
    		colors = Color.RED;
    		break;
    	case 'F':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szF);
    		colors = Color.BLUE;
    		break;
    	case 'f':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szF);
    		colors = Color.BLUE;
    		break;
    	case 'B':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szB);
    		colors = Color.GREEN;
    		break;
    	case 'b':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szB);
    		colors = Color.GREEN;
    		break;
    	case 'L':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szL);
    		colors = Color.WHITE;
    		break;
    	case 'l':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szL);
    		colors = Color.WHITE;
    		break;
    	case 'R':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szR);
    		colors = Color.YELLOW;
    		break;
    	case 'r':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szR);
    		colors = Color.YELLOW;
    		break;

    	case 'Q':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDU);
    		colors = Color.rgb(250, 110, 14);
    		break;
    	case 'q':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDU);
    		colors = Color.rgb(250, 110, 14);
    		break;
    	case 'W':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDD);
    		colors = Color.RED;
    		break;
    	case 'w':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDD);
    		colors = Color.RED;
    		break;
    	case 'G':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDF);
    		colors = Color.BLUE;
    		break;
    	case 'g':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDF);
    		colors = Color.BLUE;
    		break;
    	case 'H':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDB);
    		colors = Color.GREEN;
    		break;
    	case 'h':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDB);
    		colors = Color.GREEN;
    		break;
    	case 'O':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDL);
    		colors = Color.WHITE;
    		break;
    	case 'o':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDL);
    		colors = Color.WHITE;
    		break;
    	case 'P':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szDR);
    		colors = Color.YELLOW;
    		break;
    	case 'p':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szDR);
    		colors = Color.YELLOW;
    		break;
    	case 'X':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szX);
    		colors = Color.WHITE;
    		break;
    	case 'x':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szX);
    		colors = Color.WHITE;
    		break;
    	case 'Y':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szY);
    		colors = Color.WHITE;
    		break;
    	case 'y':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szY);
    		colors = Color.WHITE;
    		break;
    	case 'Z':
    		szMsg = mRes.getString(R.string.szClockWise) + mRes.getString(R.string.szZ);
    		colors = Color.WHITE;
    		break;
    	case 'z':
    		szMsg = mRes.getString(R.string.szUnClockWise) + mRes.getString(R.string.szZ);
    		colors = Color.WHITE;
    		break;
    	default:
    		return;
    	}
    	mCcs.ShowMsg(szMsg, Toast.LENGTH_LONG, colors);
    }


    public void RotateToVisible(E_ROTATIONEVENT enumRotate)
    {
        float fDegree = 0.0f;
        if (enumRotate == E_ROTATIONEVENT.NULL)
        {
            return;
        }
        
        fDegree = GetTipRotateDegree(enumRotate);
        switch ((int)fDegree)
        { 
            case 0:
                break;
            case 90:
                mCube.ROTATION(E_ROTATIONEVENT.Roll_Y, mCube.DREAM_CUBE_CACULATE, false);
                break;
            case 180:
                mCube.ROTATION(E_ROTATIONEVENT.Roll_y, mCube.DREAM_CUBE_CACULATE, false);
                break;
            case 270:
                mCube.ROTATION(E_ROTATIONEVENT.Roll_y, mCube.DREAM_CUBE_CACULATE, false);
                break;
        }
    
    }

    /*获取旋转角度，只要是可见面就返回0，不需要旋转*/
    private float GetTipRotateDegree(E_ROTATIONEVENT eRotate)
    {
        float fDegree = 0.0f;
        switch (eRotate)
        {
            case F:
                fDegree = mCube.fYDegree - 0.0f;
                break;
            case f:
                fDegree = mCube.fYDegree - 0.0f;
                break;
            case B:
                fDegree = mCube.fYDegree - 180.0f;
                break;
            case b:
                fDegree = mCube.fYDegree - 180.0f;
                break;
            case R:
                fDegree = mCube.fYDegree - 270.0f;
                break;
            case r:
                fDegree = mCube.fYDegree - 270.0f;
                break;
            case L:
                fDegree = mCube.fYDegree - 90.0f;
                break;
            case l:
                fDegree = mCube.fYDegree - 90.0f;
                break;
            case DF:
                fDegree = mCube.fYDegree - 0.0f;
                break;
            case Df:
                fDegree = mCube.fYDegree - 0.0f;
                break;
            case DB:
                fDegree = mCube.fYDegree - 180.0f;
                break;
            case Db:
                fDegree = mCube.fYDegree - 180.0f;
                break;
            case DR:
                fDegree = mCube.fYDegree - 270.0f;
                break;
            case Dr:
                fDegree = mCube.fYDegree - 270.0f;
                break;
            case DL:
                fDegree = mCube.fYDegree - 90.0f;
                break;
            case Dl:
                fDegree = mCube.fYDegree - 90.0f;
                break;
        }
        fDegree += 360.0f;
        if (fDegree >= 360.0f)
        {
            fDegree -= 360.0f;
        }

        if (fDegree == 270)                 //转换一下，保证只要在可见面就不需要旋转
        {
            fDegree = 0.0f;
        }

        return fDegree;
    }

    //转动整个魔方
    void RotateWholeCube(E_ROTATIONEVENT eRotate)
    {
    	InitViewPortMain();
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        switch (eRotate)
        {
            case Roll_X:
                RotateCubeX(1.0f);
                break;
            case Roll_x:
                RotateCubeX(-1.0f);
                break;
            case Roll_Y:
                RotateCubeY(1.0f);
                break;
            case Roll_y:
                RotateCubeY(-1.0f);
                break;
            case Roll_Z:
                RotateCubeZ(1.0f);
                break;
            case Roll_z:
                RotateCubeZ(-1.0f);
                break;
        }
        ShowUnseenPlate(mCube.DREAM_CUBE_CACULATE);
        ShowLButton();
        ShowRButton();
        ShowProgressStars();
        ShowTime(miCubeClockSec);
        ShowStep(mCube.GetStepBar());
        ShowCubeTip();
    }

    //旋转魔方的一个面
    void RotateCubePlate(E_ROTATIONEVENT eRotate, Boolean bCapture)
    {
    	long lTime = 0;
    	lTime = System.currentTimeMillis();
    	InitViewPortMain();
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        switch (eRotate)
        { 
            case U:
                RotateSinglePlateUP(1.0f);
                break;
            case u:
                RotateSinglePlateUP(-1.0f);
                break;
            case D:
                RotateSinglePlateDOWN(1.0f);
                break;
            case d:
                RotateSinglePlateDOWN(-1.0f);
                break;
            case F:
                RotateSinglePlateFRONT(1.0f);
                break;
            case f:
                RotateSinglePlateFRONT(-1.0f);
                break;
            case B:
                RotateSinglePlateBACK(1.0f);
                break;
            case b:
                RotateSinglePlateBACK(-1.0f);
                break;
            case L:
                RotateSinglePlateLEFT(1.0f);
                break;
            case l:
                RotateSinglePlateLEFT(-1.0f);
                break;
            case R:
                RotateSinglePlateRIGHT(1.0f);
                break;
            case r:
                RotateSinglePlateRIGHT(-1.0f);
                break;
            case DU:
                RotateDoublePlateUP(1.0f);
                break;
            case Du:
                RotateDoublePlateUP(-1.0f);
                break;
            case DD:
                RotateDoublePlateDOWN(1.0f);
                break;
            case Dd:
                RotateDoublePlateDOWN(-1.0f);
                break;
            case DF:
                RotateDoublePlateFRONT(1.0f);
                break;
            case Df:
                RotateDoublePlateFRONT(-1.0f);
                break;
            case DB:
                RotateDoublePlateBACK(1.0f);
                break;
            case Db:
                RotateDoublePlateBACK(-1.0f);
                break;
            case DL:
                RotateDoublePlateLEFT(1.0f);
                break;
            case Dl:
                RotateDoublePlateLEFT(-1.0f);
                break;
            case DR:
                RotateDoublePlateRIGHT(1.0f);
                break;
            case Dr:
                RotateDoublePlateRIGHT(-1.0f);
                break;
            case X:
                RotateSinglePlateX(1.0f);
                break;
            case x:
                RotateSinglePlateX(-1.0f);
                break;
            case Y:
                RotateSinglePlateY(1.0f);
                break;
            case y:
                RotateSinglePlateY(-1.0f);
                break;
            case Z:
                RotateOnePlateZ(-1.0f);
                break;
            case z:
                RotateOnePlateZ(1.0f);
                break;
        }
        ShowUnseenPlate(mCube.DREAM_CUBE_CACULATE);
        ShowLButton();
        ShowRButton();
        ShowProgressStars();
        ShowTime(miCubeClockSec);
        ShowStep(mCube.GetStepBar());
        ShowCubeTip();
		lTime = System.currentTimeMillis() - lTime;
		if(mlTimeUsed == 200)
		{
			mlTimeUsed = lTime;
		}
		if(bCapture && mbCapture)
		{
			Bitmap bmp = mCcBmp.GetBmp(mGl);
			mCcBmp.Draw(bmp);
		}
    }

    private void RotateSinglePlateUP(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 26);

    }

    private void RotateSinglePlateDOWN(float fRoate)
    {

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateDoublePlateUP(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 26);

    }

    private void RotateDoublePlateDOWN(float fRoate)
    {

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateSinglePlateY(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateSinglePlateFRONT(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 26);

    }

    private void RotateSinglePlateBACK(float fRoate)
    {
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateDoublePlateFRONT(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 26);

    }

    private void RotateDoublePlateBACK(float fRoate)
    {
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateSinglePlateX(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateSinglePlateLEFT(float fRoate)
    {
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateSinglePlateRIGHT(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 26);

    }

    private void RotateDoublePlateLEFT(float fRoate)
    {
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    private void RotateDoublePlateRIGHT(float fRoate)
    {
        fRoate = 0 - fRoate;
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 26);

    }


    private void RotateOnePlateZ(float fRoate)
    {
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 26);

    }

    //X轴旋转
    private void RotateCubeX(float fRoate)
    {
        fRoate = 0 - fRoate;
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, fRoate, 0.0f, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, fRoate, 0.0f, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, fRoate, 0.0f, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, fRoate, 0.0f, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, fRoate, 0.0f, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, fRoate, 0.0f, 0.0f, 26);
    }

    //Y轴旋转
    private void RotateCubeY(float fRoate)
    {
        fRoate = 0 - fRoate;
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, fRoate, 0.0f, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0.0f, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, fRoate, 0.0f, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, fRoate, 0.0f, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 0.0f, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, fRoate, 0.0f, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, fRoate, 0.0f, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 0.0f, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, fRoate, 0.0f, 26);
    }

    //Z轴旋转
    private void RotateCubeZ(float fRoate)
    {
        fRoate = 0 - fRoate;
        mGl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 0);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 1);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, fRoate, 2);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 3);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 4);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, fRoate, 5);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 6);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 7);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, fRoate, 8);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 9);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 10);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, fRoate, 11);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 12);
        //DisplaySingleCube(0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, cube, 13);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, fRoate, 14);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 15);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 16);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, fRoate, 17);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 18);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 19);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, fRoate, 20);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 21);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 22);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, fRoate, 23);

        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 24);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 25);
        DisplaySingleCube(mCube.DREAM_CUBE_DISPLAY, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, fRoate, 26);
    }

    private E_COLOR GetUnseenLeftPlateColor(ST_DreamCube dreamcube, int iBlock)
    {
        E_COLOR iColor = E_COLOR.NOCOLOR;
        if (mCube.fYDegree == 0)
        {
            iColor = dreamcube.cube_status[iBlock].Color.B;
        }
        else if (mCube.fYDegree == 90)
        {
            iColor = dreamcube.cube_status[iBlock].Color.R;
        }
        else if (mCube.fYDegree == 180)
        {
            iColor = dreamcube.cube_status[iBlock].Color.F;
        }
        else if (mCube.fYDegree == 270)
        {
            iColor = dreamcube.cube_status[iBlock].Color.L;
        }
        return iColor;
    }

    private E_COLOR GetUnseenRightPlateColor(ST_DreamCube dreamcube, int iBlock)
    {
        E_COLOR iColor = E_COLOR.NOCOLOR;
        if (mCube.fYDegree == 0)
        {
            iColor = dreamcube.cube_status[iBlock].Color.R;
        }
        else if (mCube.fYDegree == 90)
        {
            iColor = dreamcube.cube_status[iBlock].Color.F;
        }
        else if (mCube.fYDegree == 180)
        {
            iColor = dreamcube.cube_status[iBlock].Color.L;
        }
        else if (mCube.fYDegree == 270)
        {
            iColor = dreamcube.cube_status[iBlock].Color.B;
        }
        return iColor;
    }

    private E_COLOR GetUnseenDownPlateColor(ST_DreamCube dreamcube, int iBlock)
    {
        E_COLOR iColor = E_COLOR.NOCOLOR;
        iColor = dreamcube.cube_status[iBlock].Color.D;
        return iColor;
    }

    private int GetUnseenLeftPlateBlock(ST_DreamCube dreamcube, int iX, int iY)
    {
        int iBlock = 0;
        if (mCube.fYDegree == 0)
        {
            iBlock = dreamcube.Plate.B[iX][iY];
        }
        else if (mCube.fYDegree == 90)
        {
            iBlock = dreamcube.Plate.R[iX][iY];
        }
        else if (mCube.fYDegree == 180)
        {
            iBlock = dreamcube.Plate.F[iX][iY];
        }
        else if (mCube.fYDegree == 270)
        {
            iBlock = dreamcube.Plate.L[iX][iY];
        }
        return iBlock;
    }

    private int GetUnseenRightPlateBlock(ST_DreamCube dreamcube, int iX, int iY)
    {
        int iBlock = 0;
        if (mCube.fYDegree == 0)
        {
            iBlock = dreamcube.Plate.R[iX][iY];
        }
        else if (mCube.fYDegree == 90)
        {
            iBlock = dreamcube.Plate.F[iX][iY];
        }
        else if (mCube.fYDegree == 180)
        {
            iBlock = dreamcube.Plate.L[iX][iY];
        }
        else if (mCube.fYDegree == 270)
        {
            iBlock = dreamcube.Plate.B[iX][iY];
        }
        return iBlock;
    }

    private int GetUnseenDownPlateBlock(ST_DreamCube dreamcube, int iX, int iY)
    {
        int iBlock = 0;
        iBlock = dreamcube.Plate.D[iX][iY];
        return iBlock;
    }
    
    private void ShowLButton()
    {
    	if(eCubeMode == E_GAMEMODE.STUDY_MODE || eCubeMode == E_GAMEMODE.CAPTURE_MODE)
    	{
    		return;
    	}
    	
    	boolean bZoomOutPressed = false;
    	boolean bDisruptPressed = false;
    	boolean bAutoPressed = false;
    	boolean bRestartPressed = false;
    	boolean bStudyPressed = false;
    	
    	switch(eButtonSelected)
    	{
    		case SELE_ZOOMOUT:
    			bZoomOutPressed = true;
    			break;
    		case SELE_DISRUPT:
    			bDisruptPressed = true;
    			break;
    		case SELE_AUTOPLAY:
    			bAutoPressed = true;
    			break;
    		case SELE_RESTART:
    			bRestartPressed = true;
    			break;
    		case SELE_STUDY:
    			bStudyPressed = true;
    			break;
    		case SELE_HELP:
    		case SELE_MENU:
    			break;
    	}

    	if(eGLMode == E_WORKMODE.DRAW)
    	{
        	ShowLeftButton(0, -7.5f, mCfg.cubeConfig.bShowButtonOnDesktop, E_TEXTURE.MENU, E_TEXTURE.MENU_Pressed);	//help
        	ShowLeftButton(1.2f, -7.5f, mbHelpPressed, E_TEXTURE.HELP, E_TEXTURE.HELP_Pressed);	//help    	
        	if (mCfg.cubeConfig.bShowButtonOnDesktop)
        	{
            	ShowLeftButton(0, 0, bZoomOutPressed, E_TEXTURE.ZOOMOUT, E_TEXTURE.ZOOMOUT_Pressed);   //zoomout
            	ShowLeftButton(0, -1.5f, bDisruptPressed, E_TEXTURE.DISRUPT, E_TEXTURE.DISRUPT_Pressed);   //disrupt
            	ShowLeftButton(0, -3.0f, bAutoPressed, E_TEXTURE.AUTOPLAY, E_TEXTURE.AUTOPLAY_Pressed);   //autoplay
            	ShowLeftButton(0, -4.5f, bRestartPressed, E_TEXTURE.RESTART, E_TEXTURE.RESTART_Pressed);   //reset
            	ShowLeftButton(0, -6.0f, bStudyPressed, E_TEXTURE.STUDY, E_TEXTURE.STUDY_Pressed);   //reset
        	}
    	}
    	else if(eGLMode == E_WORKMODE.PICK)
    	{
        	mGl.glColor4f(0, 0, 1, 1);
        	ShowLeftButton(0, -7.5f, mCfg.cubeConfig.bShowButtonOnDesktop, E_TEXTURE.MENU, E_TEXTURE.MENU_Pressed);	//help

        	mGl.glColor4f(0, 1, 0, 1);
        	ShowLeftButton(1.2f, -7.5f, mbHelpPressed, E_TEXTURE.HELP, E_TEXTURE.HELP_Pressed);	//help    	

        	if (mCfg.cubeConfig.bShowButtonOnDesktop)
        	{
        		mGl.glColor4f(0, 1, 1, 1);
            	ShowLeftButton(0, 0, bZoomOutPressed, E_TEXTURE.ZOOMOUT, E_TEXTURE.ZOOMOUT_Pressed);   //zoomout
            	
            	mGl.glColor4f(1, 0, 0, 1);
            	ShowLeftButton(0, -1.5f, bDisruptPressed, E_TEXTURE.DISRUPT, E_TEXTURE.DISRUPT_Pressed);   //disrupt
            	
            	mGl.glColor4f(1, 0, 1, 1);
            	ShowLeftButton(0, -3.0f, bAutoPressed, E_TEXTURE.AUTOPLAY, E_TEXTURE.AUTOPLAY_Pressed);   //autoplay
            	
            	mGl.glColor4f(1, 1, 0, 1);
            	ShowLeftButton(0, -4.5f, bRestartPressed, E_TEXTURE.RESTART, E_TEXTURE.RESTART_Pressed);   //reset

            	mGl.glColor4f(1, 1, 1, 1);
            	ShowLeftButton(0, -6.0f, bStudyPressed, E_TEXTURE.STUDY, E_TEXTURE.STUDY_Pressed);   //reset
        	}
    	}
    	else
    	{}
    }
    
    private void ShowRButton()
    {
    	if(eCubeMode == E_GAMEMODE.STUDY_MODE || eCubeMode == E_GAMEMODE.CAPTURE_MODE)
    	{
    		return;
    	}
    	
    	boolean bZoomInPressed = false;
    	boolean bSetupPressed = false;
    	
    	switch(eButtonSelected)
    	{
    		case SELE_ZOOMIN:
    			bZoomInPressed = true;
    			break;
    		case SELE_SETUP:
    			bSetupPressed = true;
    			break;
    	}

    	if(eGLMode == E_WORKMODE.DRAW)
    	{
        	if (mCfg.cubeConfig.bShowButtonOnDesktop)
        	{
            	ShowRightButton(0, 0, bZoomInPressed, E_TEXTURE.ZOOMIN, E_TEXTURE.ZOOMIN_Pressed);	//zoomin
            	ShowRightButton(0, -1.5f, bSetupPressed, E_TEXTURE.SETUP, E_TEXTURE.SETUP_Pressed);	//setup    	
        	}
    	}
    	else if(eGLMode == E_WORKMODE.PICK)
    	{
        	if (mCfg.cubeConfig.bShowButtonOnDesktop)
        	{
            	mGl.glColor4f(0, 0, 1, 1);
            	ShowRightButton(0, 0, bZoomInPressed, E_TEXTURE.ZOOMIN, E_TEXTURE.ZOOMIN_Pressed);	//zoomin
            	
            	mGl.glColor4f(0, 1, 0, 1);
            	ShowRightButton(0, -1.5f, bSetupPressed, E_TEXTURE.SETUP, E_TEXTURE.SETUP_Pressed);	//setup    	
        	}
    	}
    	else
    	{}
    }
    
    private void ShowLeftButton(float fX, float fY, Boolean bPressed, E_TEXTURE texBut, E_TEXTURE texButPressed)
    {
    	E_WORKMODE eMode = GetGLMode();
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
             0f, 0, 4.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(0.4f, 0.4f, 0.4f);

        mGl.glTranslatef(fX -4.5f, fY + 4.0f, -3.0f);

        if (eMode == E_WORKMODE.DRAW)
        {
            if (bPressed)
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texButPressed.ordinal()]);
            }
            else
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texBut.ordinal()]);
            }

        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

    }

    private void ShowRightButton(float fX, float fY, Boolean bPressed, E_TEXTURE texBut, E_TEXTURE texButPressed)
    {
    	E_WORKMODE eMode = GetGLMode();
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
             0f, 0, 4.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(0.4f, 0.4f, 0.4f);
        mGl.glTranslatef(fX + 4.5f, fY + 4.0f, -3.0f);

        if (eMode == E_WORKMODE.DRAW)
        {
            if (bPressed)
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texButPressed.ordinal()]);
            }
            else
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texBut.ordinal()]);
            }
        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

    }
    
    private void ShowProgressStars()
    {
    	if(eCubeMode == E_GAMEMODE.STUDY_MODE)
    	{
    		return;
    	}
		if(!mCubeDevice.IsActived(E_DEVICE.COMPLETE_AREA))
		{
			return;
		}
    	
    	if(mCfg.cubeConfig.bShowStar)
    	{
        	if(eGLMode == E_WORKMODE.PICK)
        	{
        		mGl.glColor4f(0, 0, 1, 1);
        	}
        	if (CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
        	{
            	ShowProgressStar(0.0f, bStepStatus[0], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(1.05f, bStepStatus[1], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(2.10f, bStepStatus[2], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(3.15f, bStepStatus[3], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(4.20f, bStepStatus[4], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(5.25f, bStepStatus[5], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(6.30f, bStepStatus[6], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);    		        		
        	}
        	else if(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
        	{
            	ShowProgressStar(0.0f, bStepStatus[0], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(1.05f, bStepStatus[1], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(2.10f, bStepStatus[2], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
            	ShowProgressStar(3.15f, bStepStatus[3], E_TEXTURE.STAR, E_TEXTURE.STAR_Pressed);
        	}
        	else
        	{
        		System.out.println("Error Cube Method");
        	}
    	}
    }
    
    //显示完成进度
    private void ShowProgressStar(float fX, Boolean bPressed, E_TEXTURE texBut, E_TEXTURE texButPressed)
    {
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
             0f, 0, 8.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(0.4f, 0.4f, 0.4f);

        mGl.glTranslatef( fX -8.5f, -8.5f, -3.0f);

        if (eGLMode == E_WORKMODE.DRAW)
        {
            if (bPressed)
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texButPressed.ordinal()]);
            }
            else
            {
            	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[texBut.ordinal()]);
            }
        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

    }

    //显示公式提示区
    private void ShowCubeTip()
    {
    	if(eCubeMode == E_GAMEMODE.STUDY_MODE)     //对话框打开的情况
    	{
    		return;
    	}
    	
    	if(!mCfg.cubeConfig.bShowTip && (mCube.eGameMode == E_GAMEMODE.PLAY_MODE || eCubeMode == E_GAMEMODE.CAPTURE_MODE))
    	{
    		//PLAY模式下，才可以不显示公式区
    		return;
    	}
		if(!mCubeDevice.IsActived(E_DEVICE.FORMULA_AREA))
		{
			return;
		}
    	
    	if (!mCfg.cubeConfig.bTipGraph)
    	{
	    	ShowCubeTextTip(0);
	       	ShowCubeTextTip(1);    	
	    	ShowCubeTextTip(2);
	    	ShowCubeTextTip(3);
	    	ShowCubeTextTip(4);
	    	ShowCubeTextTip(5);
	    	ShowCubeTextTip(6);
    	}
    	else
    	{
	    	ShowCubeGraphTip(0);
	    	ShowCubeGraphTip(1);
	    	ShowCubeGraphTip(2);
	    	ShowCubeGraphTip(3);
	    	ShowCubeGraphTip(4);
	    	ShowCubeGraphTip(5);
	    	ShowCubeGraphTip(6);
    	}
    }
    
    //显示文本公式提示
    private void ShowCubeTextTip(int iPosition)
    {
    	InitTipViewPort(iPosition);
//    	ResetTipCurrentIndex();
    	
    	if(eGLMode == E_WORKMODE.PICK)
    	{
	    	switch(iPosition)     //设置不同颜色以供触摸选择，目前仅支持同时7种颜色
	    	{
	    		case 0:
	    			mGl.glColor4f(0, 0, 1, 1);
	    			break;
	    		case 1:
	    			mGl.glColor4f(0, 1, 0, 1);
	    			break;
	    		case 2:
	    			mGl.glColor4f(0, 1, 1, 1);
	    			break;
	    		case 3:
	    			mGl.glColor4f(1, 0, 0, 1);
	    			break;
	    		case 4:
	    			mGl.glColor4f(1, 0, 1, 1);
	    			break;
	    		case 5:
	    			mGl.glColor4f(1, 1, 0, 1);
	    			break;
	    		case 6:
	    			mGl.glColor4f(1, 1, 1, 1);
	    			break;    		
	    	}
    	}    	
    	char cColorStep = mCube.acTip[iPosition].cColorStep; 
    	if(cColorStep == '0')
    	{
    		return;     //不显示
    	}
    	ShowTextTipByStep(cColorStep, iPosition);
    	
    	return;
    }

    //显示图形公式提示
    private void ShowCubeGraphTip(int iPosition)
    {
    	InitTipViewPort(iPosition);
//    	ResetTipCurrentIndex();
    	if(eGLMode == E_WORKMODE.PICK)
    	{
	    	switch(iPosition)
	    	{
	    		case 0:
	    			mGl.glColor4f(0, 0, 1, 1);
	    			break;
	    		case 1:
	    			mGl.glColor4f(0, 1, 0, 1);
	    			break;
	    		case 2:
	    			mGl.glColor4f(0, 1, 1, 1);
	    			break;
	    		case 3:
	    			mGl.glColor4f(1, 0, 0, 1);
	    			break;
	    		case 4:
	    			mGl.glColor4f(1, 0, 1, 1);
	    			break;
	    		case 5:
	    			mGl.glColor4f(1, 1, 0, 1);
	    			break;
	    		case 6:
	    			mGl.glColor4f(1, 1, 1, 1);
	    			break;    		
	    	}
    	}    
    	char cColorStep = mCube.acTip[iPosition].cColorStep;
    	if(cColorStep == '0')
    	{
    		return;     //不显示
    	}
    	
    	ShowGraphyTipByStep(cColorStep, iPosition);
    	
    	return;
    }

    
    //显示不可见面
    private void ShowUnseenPlate(ST_DreamCube dreamcube)
    {
        if (mCfg.cubeConfig.bShowUnseenPlate || (eCubeMode == E_GAMEMODE.STUDY_MODE) || (eCubeMode == E_GAMEMODE.CAPTURE_MODE))
        {
        	if(eGLMode == E_WORKMODE.DRAW)
        	{
                DisplayLeftUnseenPlate(dreamcube);
                DisplayRightUnseenPlate(dreamcube);
                DisplayDownUnseenPlate(dreamcube);
        	}
        	else if(eGLMode == E_WORKMODE.PICK)
        	{
	        	mGl.glColor4f(0, 0, 1, 1); 
	            DisplayLeftUnseenPlate(dreamcube);
	            mGl.glColor4f(0, 1, 0, 1);
	            DisplayRightUnseenPlate(dreamcube);
	            mGl.glColor4f(0, 1, 1, 1);
	            DisplayDownUnseenPlate(dreamcube);
        	}
        	else
        	{}
        }
    }

    private void DisplayLeftUnseenPlate(ST_DreamCube dreamcube)
    {
        E_COLOR iColor;
        int iBlock;

        iBlock = GetUnseenLeftPlateBlock(dreamcube, 0, 0);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(1, 1, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 0, 1);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(0, 1, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 0, 2);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(-1, 1, 2, iColor);

        iBlock = GetUnseenLeftPlateBlock(dreamcube, 1, 0);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(1, 0, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 1, 1);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(0, 0, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 1, 2);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(-1, 0, 2, iColor);

        iBlock = GetUnseenLeftPlateBlock(dreamcube, 2, 0);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(1, -1, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 2, 1);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(0, -1, 2, iColor);
        iBlock = GetUnseenLeftPlateBlock(dreamcube, 2, 2);
        iColor = GetUnseenLeftPlateColor(dreamcube, iBlock);
        DisplayLeftSquare(-1, -1, 2, iColor);
    }

    private void DisplayRightUnseenPlate(ST_DreamCube dreamcube)
    {
        E_COLOR iColor;
        int iBlock;

        iBlock = GetUnseenRightPlateBlock(dreamcube, 0, 0);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 1, 1, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 0, 1);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 1, 0, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 0, 2);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 1, -1, iColor);

        iBlock = GetUnseenRightPlateBlock(dreamcube, 1, 0);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 0, 1, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 1, 1);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 0, 0, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 1, 2);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, 0, -1, iColor);

        iBlock = GetUnseenRightPlateBlock(dreamcube, 2, 0);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, -1, 1, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 2, 1);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, -1, 0, iColor);
        iBlock = GetUnseenRightPlateBlock(dreamcube, 2, 2);
        iColor = GetUnseenRightPlateColor(dreamcube, iBlock);
        DisplayRightSquare(2, -1, -1, iColor);
    }

    private void DisplayDownUnseenPlate(ST_DreamCube dreamcube)
    {
        E_COLOR iColor;
        int iBlock;

        iBlock = GetUnseenDownPlateBlock(dreamcube, 0, 0);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(-1, 0, 1, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 0, 1);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(0, 0, 1, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 0, 2);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(1, 0, 1, iColor);

        iBlock = GetUnseenDownPlateBlock(dreamcube, 1, 0);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(-1, 0, 0, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 1, 1);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(0, 0, 0, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 1, 2);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(1, 0, 0, iColor);

        iBlock = GetUnseenDownPlateBlock(dreamcube, 2, 0);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(-1, 0, -1, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 2, 1);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(0, 0, -1, iColor);
        iBlock = GetUnseenDownPlateBlock(dreamcube, 2, 2);
        iColor = GetUnseenDownPlateColor(dreamcube, iBlock);
        DisplayDownSquare(1, 0, -1, iColor);
    }

    private void DisplayLeftSquare(float fX, float fY, float fZ, E_COLOR iColor)
    {
    	E_WORKMODE eMode = GetGLMode();
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
             -5.0f, 5.0f, 5.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(0.4f, 0.4f, 1.0f);
        mGl.glTranslatef(fX + 2.5f-2, fY + 6.0f-2, fZ - 7.0f+1);
//        gl.glTranslatef(2.5f, 6, -7);

        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[iColor.ordinal()]);
        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

    }

    private void DisplayRightSquare(float fX, float fY, float fZ, E_COLOR iColor)
    {
    	E_WORKMODE eMode = GetGLMode();
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
             -5.0f, 5.0f, 5.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(1.0f, 0.4f, 0.4f);
        mGl.glTranslatef(fX + 3.5f-1, fY + 5.5f-2, fZ - 3.0f+2);
//        gl.glTranslatef(3.5f, 5.5f, -3);

        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[iColor.ordinal()]);
        }
        mGl.glNormal3f(-1.0f, 0.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        

    }

    private void DisplayDownSquare(float fX, float fY, float fZ, E_COLOR iColor)
    {
    	E_WORKMODE eMode = GetGLMode();
        mGl.glLoadIdentity();

        GLU.gluLookAt(mGl,
             -5.0f, 5.0f, 5.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);

        mGl.glScalef(0.4f, 1.0f, 0.4f);
        mGl.glTranslatef(7-3, -4.0f-1, 3+2);
        mGl.glRotatef(mCube.fYDegree, 0.0f, 1.0f, 0.0f);
        mGl.glTranslatef(fX, fY, fZ);
        
        if (eMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[iColor.ordinal()]);
        }
        mGl.glNormal3f(0.0f, 1.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
    }
    
    private void TipU(boolean bClockWise, boolean bDouble, int iPosition)
    {
        DisplayTipCube(E_PLATE.UP, -1, true, bClockWise, 90, 0, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.UP, 0, true, bClockWise, 90, 0, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 90, 0, iPosition);
        }
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 90, 0, iPosition);
    }

    private void TipD(boolean bClockWise, boolean bDouble, int iPosition)
    {
    	bClockWise = !bClockWise;
        DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 90, 0, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.DOWN, 0, true, bClockWise, 90, 0, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 90, 0, iPosition);
        }
        DisplayTipCube(E_PLATE.DOWN, 1, true, bClockWise, 90, 0, iPosition);
    }

    private void TipF(boolean bClockWise, boolean bDouble, int iPosition)
    {
    	bClockWise = !bClockWise;
        DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 0, 0, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.FRONT, 0, true, bClockWise, 0, 0, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 0, 0, iPosition);
        }
        DisplayTipCube(E_PLATE.FRONT, 1, true, bClockWise, 0, 0, iPosition);
    }

    private void TipB(boolean bClockWise, boolean bDouble, int iPosition)
    {
    	DisplayTipCube(E_PLATE.BACK, -1, true, bClockWise, 0, 0, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.BACK, 0, true, bClockWise, 0, 0, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 0, 0, iPosition);
        }
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 0, 0, iPosition);
    }
    
    private void TipL(boolean bClockWise, boolean bDouble, int iPosition)
    {
        DisplayTipCube(E_PLATE.LEFT, -1, true, bClockWise, 0, 90, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.LEFT, 0, true, bClockWise, 0, 90, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 0, 90, iPosition);
        }
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 0, 90, iPosition);
    }
    
    private void TipR(boolean bClockWise, boolean bDouble, int iPosition)
    {
    	bClockWise = !bClockWise;
        DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 0, 90, iPosition);
        if(bDouble)
        {
        	DisplayTipCube(E_PLATE.RIGHT, 0, true, bClockWise, 0, 90, iPosition);
        }
        else
        {
        	DisplayTipCube(E_PLATE.NOPLATE, 0, false, false, 0, 90, iPosition);
        }
        DisplayTipCube(E_PLATE.RIGHT, 1, true, bClockWise, 0, 90, iPosition);
    }

    private void TipX(boolean bClockWise, int iPosition)
    {
        DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 0, 90, iPosition);
        DisplayTipCube(E_PLATE.X, 0, true, bClockWise, 0, 90, iPosition);
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 0, 90, iPosition);
    }
    
    private void TipY(boolean bClockWise, int iPosition)
    {
    	bClockWise = !bClockWise;
        DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 90, 0, iPosition);
        DisplayTipCube(E_PLATE.Y, 0, true, bClockWise, 90, 0, iPosition);
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 90, 0, iPosition);
    }

    private void TipZ(boolean bClockWise, int iPosition)
    {
    	DisplayTipCube(E_PLATE.NOPLATE, -1, false, false, 0, 0, iPosition);
        DisplayTipCube(E_PLATE.Z, 0, true, bClockWise, 0, 0, iPosition);
        DisplayTipCube(E_PLATE.NOPLATE, 1, false, false, 0, 0, iPosition);
    }

    private void DisplayTipCube(E_PLATE ePlate, float fZ, boolean bTurn, boolean bClockWise, float fRotateX, float fRotateY, int iPosition)
    {
    	E_COLOR eU, eD, eF, eB, eL, eR;
    	eU = eD = eF = eB = eL = eR = E_COLOR.NOCOLOR;
    	
    	switch(ePlate)
    	{
    		case UP:
    			eU = eD = eF = eB = eL = eR = E_COLOR.ORANGE;
    			break;
    		case DOWN:
    			eU = eD = eF = eB = eL = eR = E_COLOR.RED;
    			break;
    		case FRONT:
    			eU = eD = eF = eB = eL = eR = E_COLOR.BLUE;
    			break;
    		case BACK:
    			eU = eD = eF = eB = eL = eR = E_COLOR.GREEN;
    			break;
    		case LEFT:
    			eU = eD = eF = eB = eL = eR = E_COLOR.WHITE;
    			break;
    		case RIGHT:
    			eU = eD = eF = eB = eL = eR = E_COLOR.YELLOW;
    			break;
    		case X:
    			eU = E_COLOR.ORANGE; 
    			eD = E_COLOR.BLUE;
    			eF = E_COLOR.ORANGE;
    			eB = E_COLOR.BLUE;
    			eL = E_COLOR.BLUE;
    			eR = E_COLOR.ORANGE;
    			break;
    		case Y:
    			eU = E_COLOR.BLUE; 
    			eD = E_COLOR.YELLOW;
    			eF = E_COLOR.BLUE;
    			eB = E_COLOR.YELLOW;
    			eL = E_COLOR.BLUE; 
    			eR = E_COLOR.YELLOW;
    			break;
    		case Z:
    			eU = E_COLOR.ORANGE; 
    			eD = E_COLOR.YELLOW;
    			eF = E_COLOR.ORANGE;
    			eB = E_COLOR.YELLOW;
    			eL = E_COLOR.ORANGE; 
    			eR = E_COLOR.YELLOW;
    			break;
    		case NOPLATE:
    	        if (iPosition == mCube.iCurrentIndex)
    	        {
    	        	//突出显示
	    	        eU = E_COLOR.HIGHTLIGHT; 
	    			eD = E_COLOR.HIGHTLIGHT;
	    			eF = E_COLOR.HIGHTLIGHT;
	    			eB = E_COLOR.HIGHTLIGHT;
	    			eL = E_COLOR.HIGHTLIGHT; 
	    			eR = E_COLOR.HIGHTLIGHT;
    	        }
    	        else
    	        {
	    	        eU = E_COLOR.NOCOLOR; 
	    			eD = E_COLOR.NOCOLOR;
	    			eF = E_COLOR.NOCOLOR;
	    			eB = E_COLOR.NOCOLOR;
	    			eL = E_COLOR.NOCOLOR; 
	    			eR = E_COLOR.NOCOLOR;
    	        }
    			break;
    	}

        BindVertexAndTexture(tipBuff, tiptexBuff);
        mGl.glLoadIdentity();                //可不要

        GLU.gluLookAt(mGl,
                -50, 50, 50,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);
        
        mGl.glScalef(13, 13, 13);

        if (mCube.fYDegree != 0)
        {
            mGl.glRotatef(mCube.fYDegree, 0.0f, 1.0f, 0.0f);
        }
        mGl.glRotatef(fRotateX, 1.0f, 0.0f, 0.0f);
        mGl.glRotatef(fRotateY, 0.0f, 1.0f, 0.0f);

        if (bTurn)
        {
        	if (bClockWise)
        	{
        		mGl.glRotatef(-15, 0, 0, 1);
        	}
        	else
        	{
        		mGl.glRotatef(15, 0, 0, 1);
        	}
        }
        else
        {
        	mGl.glScalef(0.93f, 0.93f, 0.93f);	
        }
        if (iPosition == mCube.iCurrentIndex)
        {
        	mGl.glScalef(1.15f, 1.15f, 1.15f);
        }

        
        mGl.glTranslatef(0, 0, fZ);
        
        
        // FRONT AND BACK
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eF.ordinal()]);
        }
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eB.ordinal()]);
        }
        mGl.glNormal3f(0.0f, 0.0f, -1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

        // LEFT AND RIGHT

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eL.ordinal()]);
        }
        mGl.glNormal3f(-1.0f, 0.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eR.ordinal()]);
        }
        mGl.glNormal3f(1.0f, 0.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

        // TOP AND BOTTOM

        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eU.ordinal()]);
        }
        mGl.glNormal3f(0.0f, 1.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eD.ordinal()]);
        }
        mGl.glNormal3f(0.0f, -1.0f, 0.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
        
        return;
    }

    
    
    
    private E_POPMENU eMenuItem = E_POPMENU.NONE;
    
    private E_POPMENU GetMenuItemId()
    {
  	
    	return eMenuItem;
    }
    
    public void SetMenuItemId(int iValue)
    {
    	E_POPMENU ePopMenu = E_POPMENU.NONE;
    	if(iValue == E_POPMENU.AUTOPLAY_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.AUTOPLAY_ID;
    	}
    	else if(iValue == E_POPMENU.DISRUPT_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.DISRUPT_ID;
    	}
    	else if(iValue == E_POPMENU.RESTART_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.RESTART_ID;
    	}
    	else if(iValue == E_POPMENU.STUDY_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.STUDY_ID;
    	}
    	else if(iValue == E_POPMENU.SHARE_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.SHARE_ID;
    	}
    	else if(iValue == E_POPMENU.SETUP_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.SETUP_ID;
    	}
    	else if(iValue == E_POPMENU.USER_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.USER_ID;
    	}
    	else if(iValue == E_POPMENU.DAILYAWARD_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.DAILYAWARD_ID;
    	}
    	else if(iValue == E_POPMENU.EXPORT_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.EXPORT_ID;
    	}
    	else if(iValue == E_POPMENU.TUTORIAL_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.TUTORIAL_ID;
    	}
    	else if(iValue == E_POPMENU.EXIT_ID.ordinal())
    	{
    		ePopMenu = E_POPMENU.EXIT_ID;
    	}
    	else
    	{}
    	eMenuItem = ePopMenu;
    }
    
    
    private void MenuItemClick()
    {
    	E_SELECTION eObj = E_SELECTION.SELE_INVALID;
    	E_POPMENU eMenuItem = GetMenuItemId();
        switch (eMenuItem) {
        case AUTOPLAY_ID:
        	eObj = E_SELECTION.SELE_AUTOPLAY;
        	break;
        case DISRUPT_ID:
        	eObj = E_SELECTION.SELE_DISRUPT;
        	break;
        case RESTART_ID:
        	eObj = E_SELECTION.SELE_RESTART;
        	break;
        case STUDY_ID:
        	eObj = E_SELECTION.SELE_STUDY;
        	break;
        case SHARE_ID:
        	eObj = E_SELECTION.SELE_SHARE;
        	break;
        case USER_ID:
        	eObj = E_SELECTION.SELE_USER;
        	break;
        case DAILYAWARD_ID:
        	eObj = E_SELECTION.SELE_DAILYAWARD;
        	break;
        case TUTORIAL_ID:
        	eObj = E_SELECTION.SELE_TUTORIAL;
        	break;
        case EXPORT_ID:
        	eObj = E_SELECTION.SELE_EXPORT;
        	break;
        case SETUP_ID:
        	eObj = E_SELECTION.SELE_SETUP;
        	break;
        }  	
		
		//触发按钮按下事件
		RaiseButtonPressedEvent(eObj, true);

    }

    
    
    //根据选定的方块、操作面、方向来决定如何旋转魔方
    void Rotate(E_SELECTION eObj, E_PLATE ePlate, E_MOUSE_DIRECTION eDirection)
    {
		if(mbHelpPressed)
		{
			int iRet = 0;
	    	iRet = mCcs.ShowHelp(mRes.getString(R.string.titleCube),mRes.getString(R.string.helpCube), R.drawable.cube);
			if(CrazyCubeShow.DIALOG_SHARE == iRet)
			{
				ShowShare(mCon.getString(R.string.szGetHelp));
			}
			SetHelpStatus(false);
			return;
		}

    	
    	E_PLATE eRotatePlate = E_PLATE.NOPLATE;
    	Boolean bClockWise = true;
    	switch(eObj)
    	{
    		case SELE_BLK2:
    			if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = false;
    			}
    			break;
    		case SELE_BLK5:
    			if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
                   	eRotatePlate = E_PLATE.Z;
                    bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
                   	eRotatePlate = E_PLATE.Z;
                    bClockWise = true;
    			}
    			break;
    		case SELE_BLK6:
    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = true;
    			}    			
    			break;
    		case SELE_BLK7:
    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(4);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
                   	eRotatePlate = E_PLATE.X;
                    bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
                   	eRotatePlate = E_PLATE.X;
                    bClockWise = false;
    			}
    			break;
    		case SELE_BLK8:
    			if (ePlate == E_PLATE.FRONT)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(4);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(4);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.RIGHT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(4);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(4);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
    			}
    			break;
    		case SELE_BLK11:
    			if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = E_PLATE.Y;
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Y;
    				bClockWise = true;
    			}    			
    			break;
    		case SELE_BLK14:
    			if(eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = E_PLATE.Z;
                    bClockWise = false;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = E_PLATE.Z;
                    bClockWise = true;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = E_PLATE.Y;
                    bClockWise = false;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Y;
                    bClockWise = true;
    			}
    			break;
    		case SELE_BLK15:
    			if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Y;
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = E_PLATE.Y;
    				bClockWise = true;
    			}    			
    			break;
    		case SELE_BLK16:
    			if(eDirection == E_MOUSE_DIRECTION.DOWN_UP)
    			{
    				eRotatePlate = E_PLATE.X;
                    bClockWise = true;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.UP_DOWN)
    			{
    				eRotatePlate = E_PLATE.X;
                    bClockWise = false;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Y;
                    bClockWise = false;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = E_PLATE.Y;
                    bClockWise = true;
    			}
    			break;
    		case SELE_BLK17:
    			if (ePlate == E_PLATE.FRONT)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.Y;
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = E_PLATE.Y;
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.RIGHT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
    					eRotatePlate = E_PLATE.Y;
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.Y;
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
    			}
    			break;
    		case SELE_BLK18:
    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = true;
    			}
    			break;
    		case SELE_BLK19:
    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(10);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = E_PLATE.X;
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = E_PLATE.X;
    				bClockWise = false;
    			}    			
    			break;
    		case SELE_BLK20:
    			if (ePlate == E_PLATE.UP)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(10);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(10);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.RIGHT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(10);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(10);
	    				bClockWise = false;
	    			}
    			}
    			break;
    		case SELE_BLK21:
    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = mCube.GetOperPlate(12);
    				bClockWise = true;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = E_PLATE.Z;
    				bClockWise = false;
    			}
    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Z;
    				bClockWise = true;
    			}    			
    			break;
    		case SELE_BLK22:
    			if(eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
    			{
    				eRotatePlate = E_PLATE.X;
                    bClockWise = true;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
    			{
    				eRotatePlate = E_PLATE.X;
                    bClockWise = false;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
    			{
    				eRotatePlate = E_PLATE.Z;
                    bClockWise = true;
    			}
    			else if(eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
    			{
    				eRotatePlate = E_PLATE.Z;
                    bClockWise = false;
    			}
    			break;
    		case SELE_BLK23:
    			if (ePlate == E_PLATE.UP)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.Z;
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = E_PLATE.Z;
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.RIGHT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = E_PLATE.Z;
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.Z;
	    				bClockWise = true;
	    			}
    			}
    			break;
    		case SELE_BLK24:
    			if (ePlate == E_PLATE.UP)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(12);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(12);
	    				bClockWise = true;
	    			}
    			}
    			else if (ePlate == E_PLATE.FRONT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(12);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(12);
	    				bClockWise = true;
	    			}
    			}
    			break;
    		case SELE_BLK25:
    			if (ePlate == E_PLATE.UP)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = E_PLATE.X;
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.X;
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.FRONT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = E_PLATE.X;
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = E_PLATE.X;
	    				bClockWise = false;
	    			}
    			}
    			break;
    		case SELE_BLK26:
    			if (ePlate == E_PLATE.UP)
    			{
	    			if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.FRONT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(14);
	    				bClockWise = false;
	    			}
    			}
    			else if (ePlate == E_PLATE.RIGHT)
    			{
    				if (eDirection == E_MOUSE_DIRECTION.LEFT_RIGHT_UP)
	    			{
    					eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(22);
	    				bClockWise = true;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.DOWN_UP)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = false;
	    			}
	    			else if (eDirection == E_MOUSE_DIRECTION.UP_DOWN)
	    			{
	    				eRotatePlate = mCube.GetOperPlate(16);
	    				bClockWise = true;
	    			}
    			}
    			break;
    	}
    	
    	if (mCfg.cubeConfig.bCenterPlate)    //禁止中间面旋转
    	{
    		if (eRotatePlate == E_PLATE.X || eRotatePlate == E_PLATE.Y || eRotatePlate ==E_PLATE.Z )
    		{
    			miCenterPlateCnt ++;
    			if (miCenterPlateCnt > 10)
    			{
    				miCenterPlateCnt = 0;
    				mCcs.ShowMsg(R.string.tipCenterPlate);
    			}
    			return;
    		}
    	}
    	
        //cPhysicalStep = GetXYZColorStep(ePlate, bClockWise);           //物理步骤
        char cPhysicalStep = mCube.GetStepByPlate(eRotatePlate, bClockWise, false, false);     //物理步骤
        if (cPhysicalStep == ' ')
        {
            return;
        }

        char cColorStep = mCube.PhysicalPlate2ColorPlate(cPhysicalStep);

        Boolean bMatch = mCube.CheckIfMatchTrainStep(cColorStep);

        E_ROTATIONEVENT eRotation = mCube.GetRotationEvent(cColorStep);
        mCube.Rotation(eRotation);

        if (!bMatch)
        {
            mCube.UndoTrainStep(eRotation);                     //反向操作一遍
            miUndoTrainCnt++;
            if(miUndoTrainCnt >3)	//为按照公式提示操作时给出提示
            {
            	miUndoTrainCnt = 0;
            	mCcs.ShowMsg(R.string.tipUnMatch);
            }
            return;
        }
        
        if(mCfg.cubeConfig.bSupport180)
        {
            int iPathLen = (int)Math.sqrt(Math.pow((miEndX - miStartX), 2) + Math.pow((miEndY - miStartY), 2));
            if (iPathLen >= (int)(miMousePrecision) * 2)	//一次转180°
            {
                bMatch = mCube.CheckIfMatchTrainStep(cColorStep);
            	mCube.Rotation(eRotation); 
                if (!bMatch)
                {
                    mCube.UndoTrainStep(eRotation);                     //反向操作一遍
                }
            }	
        }   	
    }
    
	void SetGLMode(E_WORKMODE eValue)
	{
		eGLMode = eValue;
	}

	private E_WORKMODE GetGLMode()
	{
		return eGLMode;
//		return E_WORKMODE.PICK;
	}

    ButtonPressedEventListener ButtonPressedFunc = null;
    TouchActionEventListener TouchActionFunc = null;
    ClockEventListener ClockFunc = null;
    ShakeEventListener ShakeFunc = null;
	
    //ButtonPressed自定义事件处理
    public class ButtonPressedEventArgs
    {
        public E_SELECTION eButtonPressed;
    }

    @SuppressWarnings("serial")
	public class ButtonPressedEvent extends EventObject 
    {
        public ButtonPressedEventArgs event = new ButtonPressedEventArgs();

    	public ButtonPressedEvent(Object source, ButtonPressedEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(ButtonPressedEventArgs e) 
    	{
    		this.event = e;
    	}

    	public ButtonPressedEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface ButtonPressedEventListener extends EventListener {

    	public void event(ButtonPressedEvent event);

	}
    
	
    protected void OnButtonPressed(ButtonPressedEventArgs e)
    {
        ButtonPressedEventListener listener = ButtonPressedFunc;
        if (listener != null)
        {
            ButtonPressedEvent event = new ButtonPressedEvent(this, e); 
        	listener.event(event);
        }
    }

	
    public void RaiseButtonPressedEvent(E_SELECTION eButtonPressed, boolean bButton)
    {
    	if(eButtonPressed == E_SELECTION.SELE_INVALID)
    	{
    		return;
    	}
    	bCubeClockRun = false;
    	if(bButton)
    	{
    		mCube.OnCubeSound(E_SOUND.BUTTON);
    	}
        ButtonPressedEventArgs e = new ButtonPressedEventArgs();
        e.eButtonPressed = eButtonPressed;

        OnButtonPressed(e);
        bCubeClockRun = true;
    }
    
    //TouchAction自定义事件处理
    public class TouchActionEventArgs
    {
        public E_MOUSE_DIRECTION eTouchDirection;
        public E_MOUSE_AREA eTouchArea;
    }

    @SuppressWarnings("serial")
	public class TouchActionEvent extends EventObject 
    {
        private TouchActionEventArgs event = new TouchActionEventArgs();

    	public TouchActionEvent(Object source, TouchActionEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(TouchActionEventArgs e) 
    	{
    		this.event = e;
    	}

    	public TouchActionEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface TouchActionEventListener extends EventListener {

    	public void event(TouchActionEvent event);

	}


    
    
    protected void OnTouchAction(TouchActionEventArgs e)
    {
        TouchActionEventListener listener = TouchActionFunc;
        if (listener != null)
        {
            TouchActionEvent event = new TouchActionEvent(this, e); 
        	listener.event(event);
        }
    }

    public void RaiseTouchActionEvent(E_MOUSE_DIRECTION eMouseDirection, E_MOUSE_AREA eMouseArea)
    {
       
        TouchActionEventArgs e = new TouchActionEventArgs();
        e.eTouchDirection = eMouseDirection;
        e.eTouchArea = eMouseArea;

        OnTouchAction(e);
    }
   
    
    //ClockEvent自定义事件处理
    public class ClockEventArgs
    {
        public int iClockTime;
    }

    @SuppressWarnings("serial")
	public class ClockEvent extends EventObject 
    {
        private ClockEventArgs event = new ClockEventArgs();

    	public ClockEvent(Object source, ClockEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(ClockEventArgs e) 
    	{
    		this.event = e;
    	}

    	public ClockEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface ClockEventListener extends EventListener {

    	public void event(ClockEvent event);

	}


    
    
    protected void OnClockEvent(ClockEventArgs e)
    {
        ClockEventListener listener = ClockFunc;
        if (listener != null)
        {
            ClockEvent event = new ClockEvent(this, e); 
        	listener.event(event);
        }
    }

    public void RaiseClockEvent()
    {
        ClockEventArgs e = new ClockEventArgs();
        OnClockEvent(e);
    }
    
    
    public class ShakeEventArgs
    {
        public E_SHAKE eShake;
    }

    @SuppressWarnings("serial")
	public class ShakeEvent extends EventObject 
    {
        public ShakeEventArgs event = new ShakeEventArgs();

    	public ShakeEvent(Object source, ShakeEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(ShakeEventArgs e) 
    	{
    		this.event = e;
    	}

    	public ShakeEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface ShakeEventListener extends EventListener {

    	public void event(ShakeEvent event);

	}
    
	
    protected void OnShake(ShakeEventArgs e)
    {
    	ShakeEventListener listener = ShakeFunc;
        if (listener != null)
        {
        	ShakeEvent event = new ShakeEvent(this, e); 
        	listener.event(event);
        }
    }

	
    public void RaiseShakeEvent(E_SHAKE eShake)
    {
    	ShakeEventArgs e = new ShakeEventArgs();
        e.eShake = eShake;
        OnShake(e);
    }


    /*将步骤转换成需要显示的TEXTURE，现在支持HarrisENG表示法*/
    private E_TEXTURE GetTextureByStep(char cStep)
    {
    	E_TEXTURE eTexture = E_TEXTURE.NOCOLOR;
        switch (cStep)
        {
        	case '@':
	        	eTexture = E_TEXTURE.TIP_START;
	            break;        
            case 'U':
            	eTexture = E_TEXTURE.ROTATE_U;
                break;
            case 'u':
            	eTexture = E_TEXTURE.ROTATE_u;
                break;
            case 'D':
            	eTexture = E_TEXTURE.ROTATE_D;
                break;
            case 'd':
            	eTexture = E_TEXTURE.ROTATE_d;
                break;
            case 'F':
            	eTexture = E_TEXTURE.ROTATE_F;
                break;
            case 'f':
            	eTexture = E_TEXTURE.ROTATE_f;
                break;
            case 'B':
            	eTexture = E_TEXTURE.ROTATE_B;
                break;
            case 'b':
            	eTexture = E_TEXTURE.ROTATE_b;
                break;
            case 'L':
            	eTexture = E_TEXTURE.ROTATE_L;
                break;
            case 'l':
            	eTexture = E_TEXTURE.ROTATE_l;
                break;
            case 'R':
            	eTexture = E_TEXTURE.ROTATE_R;
                break;
            case 'r':
            	eTexture = E_TEXTURE.ROTATE_r;
                break;
            case 'Q':
            	eTexture = E_TEXTURE.ROTATE_Q;
                break;
            case 'q':
            	eTexture = E_TEXTURE.ROTATE_q;
                break;
            case 'W':
            	eTexture = E_TEXTURE.ROTATE_W;
                break;
            case 'w':
            	eTexture = E_TEXTURE.ROTATE_w;
                break;
            case 'G':
            	eTexture = E_TEXTURE.ROTATE_G;
                break;
            case 'g':
            	eTexture = E_TEXTURE.ROTATE_g;
                break;
            case 'H':
            	eTexture = E_TEXTURE.ROTATE_H;
                break;
            case 'h':
            	eTexture = E_TEXTURE.ROTATE_h;
                break;
            case 'O':
            	eTexture = E_TEXTURE.ROTATE_O;
                break;
            case 'o':
            	eTexture = E_TEXTURE.ROTATE_o;
                break;
            case 'P':
            	eTexture = E_TEXTURE.ROTATE_P;
                break;
            case 'p':
            	eTexture = E_TEXTURE.ROTATE_p;
                break;
            case 'X':
            	eTexture = E_TEXTURE.ROTATE_X;
                break;
            case 'x':
            	eTexture = E_TEXTURE.ROTATE_x;
                break;
            case 'Y':
            	eTexture = E_TEXTURE.ROTATE_Y;
                break;
            case 'y':
            	eTexture = E_TEXTURE.ROTATE_y;
                break;
            case 'Z':
            	eTexture = E_TEXTURE.ROTATE_Z;
                break;
            case 'z':
            	eTexture = E_TEXTURE.ROTATE_z;
				break;
			case 'A':
				eTexture = E_TEXTURE.ROTATE_A;
				break;
			case 'a':
				eTexture = E_TEXTURE.ROTATE_a;
				break;
			case 'C':
				eTexture = E_TEXTURE.ROTATE_C;
				break;
			case 'c':
				eTexture = E_TEXTURE.ROTATE_c;
				break;
			case 'E':
				eTexture = E_TEXTURE.ROTATE_E;
				break;
			case 'e':
				eTexture = E_TEXTURE.ROTATE_e;
				break;
			case 'I':
				eTexture = E_TEXTURE.ROTATE_I;
				break;
			case 'i':
				eTexture = E_TEXTURE.ROTATE_i;
				break;
			case 'J':
				eTexture = E_TEXTURE.ROTATE_J;
				break;
			case 'j':
				eTexture = E_TEXTURE.ROTATE_j;
				break;
			case 'K':
				eTexture = E_TEXTURE.ROTATE_K;
				break;
			case 'k':
				eTexture = E_TEXTURE.ROTATE_k;
                break;

        }
        return eTexture;
    }
    
    private void ShowTipSelection(int iPosition)
    {

        if (iPosition == mCube.iCurrentIndex)
        {
            mGl.glLoadIdentity();               
            GLU.gluLookAt(mGl,
    	             0.0f, 0.0f, 6.0f,
    	             0.0f, 0.0f, 0.0f,
    	             0.0f, 1.0f, 0.0f);        	

            mGl.glScalef(3f, 3f, 3f);
        	mGl.glBindTexture(GL10.GL_TEXTURE_2D, E_TEXTURE.BUTTON_SELE.ordinal());
        	mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        	mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 24, 4);
        }
    }
    
    private void ShowTextTipByStep(char cColorStep, int iPosition)
    {
    	ShowTipSelection(iPosition);
    	
        mGl.glLoadIdentity();               
        GLU.gluLookAt(mGl,
	             0.0f, 0.0f, 6.0f,
	             0.0f, 0.0f, 0.0f,
	             0.0f, 1.0f, 0.0f);        	

        mGl.glScalef(3f, 3f, 3f);
       
        if (eGLMode == E_WORKMODE.DRAW)
        {
        	E_TEXTURE eTexture = GetTextureByStep(cColorStep);
        	if(eTexture != E_TEXTURE.NOCOLOR)
        	{
        		mGl.glBindTexture(GL10.GL_TEXTURE_2D, texture[eTexture.ordinal()]);
        	}
        }
        
        mGl.glNormal3f(0.0f, 0.0f, 1.0f);
        mGl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        return;
    }


    private void ShowGraphyTipByStep(char cColorStep, int iPosition)
    {
    	
        switch (cColorStep)
        {
        	case '@':
	        	ShowTextTipByStep(cColorStep, iPosition);
	            break;        
            case 'U':
            	TipU(true, false, iPosition);
                break;
            case 'u':
            	TipU(false, false, iPosition);
                break;
            case 'D':
            	TipD(true, false, iPosition);
                break;
            case 'd':
            	TipD(false, false, iPosition);
                break;
            case 'F':
            	TipF(true, false, iPosition);
                break;
            case 'f':
            	TipF(false, false, iPosition);
                break;
            case 'B':
            	TipB(true, false, iPosition);
                break;
            case 'b':
            	TipB(false, false, iPosition);
                break;
            case 'L':
            	TipL(true, false, iPosition);
                break;
            case 'l':
            	TipL(false, false, iPosition);
                break;
            case 'R':
            	TipR(true, false, iPosition);
                break;
            case 'r':
            	TipR(false, false, iPosition);
                break;
            case 'Q':
            	TipU(true, true, iPosition);
                break;
            case 'q':
            	TipU(false, true, iPosition);
                break;
            case 'W':
            	TipD(true, true, iPosition);
                break;
            case 'w':
            	TipD(false, true, iPosition);
                break;
            case 'G':
            	TipF(true, true, iPosition);
                break;
            case 'g':
            	TipF(false, true, iPosition);
                break;
            case 'H':
            	TipB(true, true, iPosition);
                break;
            case 'h':
            	TipB(false, true, iPosition);
                break;
            case 'O':
            	TipL(true, true, iPosition);
                break;
            case 'o':
            	TipL(false, true, iPosition);
                break;
            case 'P':
            	TipR(true, true, iPosition);
                break;
            case 'p':
            	TipR(false, true, iPosition);
                break;
            case 'X':
            	TipX(true, iPosition);
                break;
            case 'x':
            	TipX(false, iPosition);
                break;
            case 'Y':
            	TipY(true, iPosition);
                break;
            case 'y':
            	TipY(false, iPosition);
                break;
            case 'Z':
            	TipZ(true, iPosition);
                break;
            case 'z':
            	TipZ(false, iPosition);
                break;
        }
        return;
    }
    
    void SetHelpStatus(boolean bValue)
    {
    	mbHelpPressed = bValue;
    	if(!bValue)
    	{
			DisplayCube();
    	}
    }
    

    
	
	
	void ShowStudy()
	{
    	mCube.bAutoTip = false;
    	int iStudyLevel = mCcs.ShowStudy();
    	if (iStudyLevel >= mCube.GetMinLevel() && iStudyLevel <= mCube.GetMaxLevel())
    	{
    		
    		int iRtn = CrazyCubeShow.DIALOG_OK;
    		do
    		{
        		iRtn = mCcs.ShowStudy(iStudyLevel, mCfg.cubeConfig.bShowStudy, (int)(iScreenWidth * 0.9f), iScreenHeight);
        		
        		if ((CrazyCubeShow.DIALOG_MORE == iRtn) || (CrazyCubeShow.DIALOG_CAPTURE == iRtn))
        		{
            		int iTitle = 0;
            		int iMethod = 0;
            		int iMsg = 0;
            		ST_DreamCube cubeStart = mCube.DREAM_CUBE_CACULATE;
            		if (E_METHOD.LAYER_FIRST == CrazyCubeMain.eWhichMethod)
            		{            	
            			iMethod = R.string.method_layerfirst;
						switch(iStudyLevel)
                		{
                		case 1:
                			iTitle = R.string.method1_step1;
                			iMsg = R.layout.layerfirst_step1;
                			cubeStart = mCube.DREAM_CUBE_STEP1;
                			break;
                		case 2:
                			iTitle = R.string.method1_step2;
                			iMsg = R.layout.layerfirst_step2;
                			cubeStart = mCube.DREAM_CUBE_STEP2;
                			break;
                		case 3:
                			iTitle = R.string.method1_step3;
                			iMsg = R.layout.layerfirst_step3;
                			cubeStart = mCube.DREAM_CUBE_STEP3;
                			break;
                		case 4:
                			iTitle = R.string.method1_step4;
                			iMsg = R.layout.layerfirst_step4;
                			cubeStart = mCube.DREAM_CUBE_STEP4;
                			break;
                		case 5:
                			iTitle = R.string.method1_step5;
                			iMsg = R.layout.layerfirst_step5;
                			cubeStart = mCube.DREAM_CUBE_STEP5;
                			break;
                		case 6:
                			iTitle = R.string.method1_step6;
                			iMsg = R.layout.layerfirst_step6;
                			cubeStart = mCube.DREAM_CUBE_STEP6;
                			break;
                		case 7:
                			iTitle = R.string.method1_step7;
                			iMsg = R.layout.layerfirst_step7;
                			cubeStart = mCube.DREAM_CUBE_STEP7;
                			break;
                		}
						
            		}
            		else if (E_METHOD.BRIDGE == CrazyCubeMain.eWhichMethod)
            		{
            			iMethod = R.string.method_bridge;
                		switch(iStudyLevel)
                		{
                		case 1:
                			iTitle = R.string.method2_step1;
                			iMsg = R.layout.bridge_step1;
                			cubeStart = mCube.DREAM_CUBE_STEP1;
                			break;
                		case 2:
                			iTitle = R.string.method2_step2;
                			iMsg = R.layout.bridge_step2;
                			cubeStart = mCube.DREAM_CUBE_STEP2;
                			break;
                		case 3:
                			iTitle = R.string.method2_step3;
                			iMsg = R.layout.bridge_step3;
                			cubeStart = mCube.DREAM_CUBE_STEP3;
                			break;
                		case 4:
                			iTitle = R.string.method2_step4;
                			iMsg = R.layout.bridge_step4;
                			cubeStart = mCube.DREAM_CUBE_STEP4;
                			break;
                		}
            		}
            		else
            		{}
            		if(CrazyCubeShow.DIALOG_MORE == iRtn)
            		{            			
            			String szTitle = mRes.getString(iTitle);
            			iRtn = mCcs.ShowStudy(szTitle, iMsg);
            		}
            		else
            		{
            			if(CrazyCubeShow.DIALOG_CAPTURE == mCcs.ShowCapture(cubeStart, "", 360, 680))
            			{
            				String szTitle = mRes.getString(iMethod) + ":" + mRes.getString(iTitle);
            				SendShareMsg(1, szTitle);
            			}
            			System.gc();
            		}
        		}
    			
    		}while((iRtn != CrazyCubeShow.DIALOG_STUDY) && (iRtn != CrazyCubeShow.DIALOG_RETURN));
//    		DisplayCube();
            mCube.SetGameMode(E_GAMEMODE.INIT_MODE);  
            mCube.SetTrainningLevel(iStudyLevel);      
            mCube.GetTrainningStep(iStudyLevel);       
            mCube.SetGameMode(E_GAMEMODE.STUDY_MODE); 
            mCube.ClearAutoPlayStatus();
            mCube.GetAutoPlayStatus(bStepStatus);      //刷新当前完成状态
            mCube.CheckCurrentStatus();               
            mCube.UpdateTipData();
            DisplayCube();
    	}
    	else
    	{
			mCcs.ShowMsg(R.string.tipNotSelectStudy);
			CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
    	}
    	mCube.bAutoTip = true;
        mCube.ResetAutoTipTimer();		
    	return;
	}
	
	void ShowShare()
	{
		String szFormula = mCube.autoPlayStep;
		szFormula = mCube.GetFullOptimizeStep(szFormula, false);
		szFormula = mCube.GetFullOptimizeStep(szFormula, false);
		szFormula = mCube.GetFullOptimizeStep(szFormula, false);			
		szFormula = mCube.GetFullOptimizeStep(szFormula, false);			
		szFormula = mCube.GetFullOptimizeStep(szFormula, true);
		if(szFormula.length()>1)
		{
			szFormula = szFormula.substring(1);
		}
		else
		{
			szFormula = "";
		}
			
		//mCcs.ShowCapture(mCon, mCube.DREAM_CUBE_CACULATE, szFormula, 220, 460);
		if(CrazyCubeShow.DIALOG_CAPTURE == mCcs.ShowCapture(mCube.DREAM_CUBE_CACULATE, szFormula, 360, 680))
		{
			int num = (szFormula.length() + 19)/20;
			if(0 == num)
			{
				num = 1;
			}
			SendShareMsg(num, mRes.getString(R.string.tipWeibo));
		}
		System.gc();
		return;
	}
	
	public void ShowShare(String szMsg)
	{
		SendShareMsg(1, szMsg);
	}
	
	void SendShareMsg(int num, String szMsg)
	{		
		Bundle b = new Bundle();
		b.putInt("picnum", num);
		b.putString("title", szMsg);
		Message message = new Message();   
		message.what = CUBE_SHARE;
		message.setData(b);
		mHandler.sendMessage(message);

	}

	void ResetClock()
	{
    	miCubeClockSec = 0;
    	mbClockRun = false;
    	bCubeFinish = false;          //将魔方置为未完成状态
	}
	
	void StartClock()
	{
    	if(mCube.eGameMode == E_GAMEMODE.PLAY_MODE)
    	{
			mbClockRun = true;
    	}		
	}
	
	void StopClock()
	{
		mbClockRun = false;
	}
	
	void SendMsg(String szMsg)
	{
		Bundle b = new Bundle();
		b.putString("msg", szMsg);
		Message message = new Message();   
		message.what = CUBE_MSG;
		message.setData(b);
		mHandler.sendMessage(message);
	}
	
	public void AwardADPoints(int points, String szMsg)
	{
		Bundle b = new Bundle();
		b.putInt("points", points);
		b.putString("message", szMsg);
		Message message = new Message();   
		message.what = CUBE_AWARD;
		message.setData(b);
		mHandler.sendMessage(message);		
	}

	public void Capture()
	{
		Bitmap bmp = mCcBmp.GetBmp(mGl);
		mCcBmp.Draw(bmp);
	}
	
		
}



