package elong.Cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import elong.Cube.CrazyCubeGL.ButtonPressedEvent;
import elong.Cube.CrazyCubeGL.ButtonPressedEventListener;
import elong.Cube.CrazyCubeGL.ClockEvent;
import elong.Cube.CrazyCubeGL.ClockEventListener;
import elong.Cube.CrazyCubeGL.E_SHAKE;
import elong.Cube.CrazyCubeGL.ShakeEvent;
import elong.Cube.CrazyCubeGL.ShakeEventListener;
import elong.Cube.CrazyCubeGL.TouchActionEvent;
import elong.Cube.CrazyCubeGL.TouchActionEventListener;
import elong.Cube.CrazyCubeGL.E_SELECTION;
import elong.Cube.CrazyCubeGL.E_WORKMODE;
import elong.Cube.Cube3.CrazyCube.CubeCompledLevelEvent;
import elong.Cube.Cube3.CrazyCube.CubeCompledLevelEventListener;
import elong.Cube.Cube3.CrazyCube.CubeOperationChangedEventListener;
import elong.Cube.Cube3.CrazyCube.CubeOperationChangingEventListener;
import elong.Cube.Cube3.CrazyCube.CubeOperationEvent;
import elong.Cube.Cube3.CrazyCube.CubeOperationTipEventListener;
import elong.Cube.Cube3.CrazyCube.CubeSoundEvent;
import elong.Cube.Cube3.CrazyCube.CubeSoundEventListener;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_METHOD;
import elong.Cube.Cube3.CrazyCube.E_MOUSE_AREA;
import elong.Cube.Cube3.CrazyCube.E_MOUSE_DIRECTION;
import elong.Cube.Cube3.CrazyCube.E_PLATE;
import elong.Cube.Cube3.CrazyCube.E_ROTATIONEVENT;
import elong.Cube.Cube3.CrazyCube.E_SOUND;
import elong.Cube.Cube3.CrazyCube.ST_DreamCube;
import elong.CrazyCube.CrazyCubeMain;
import elong.CrazyCube.R;
import elong.CrazyCube.CrazyCubeMain.E_MENU;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class CrazyCubeGLView extends GLSurfaceView {
    private CrazyCubeRender mRenderer;
	CrazyCubeGL mCubeGL;
    private boolean mbHaveDirection = false;         //当前鼠标操作是否带有方向信息
    private int miStartX = 0;
    private int miStartY = 0;
    private int miEndX = 0;
    private int miEndY = 0;
    
	final static int TIME_USE1 = 50;
	final static int TIME_USE2 = 80;
    
    Object mSignal;
    private E_OPERATION meOperation = E_OPERATION.INVALID; 
	private Boolean mbCapture = false;
    private E_SELECTION meSelectedObj = E_SELECTION.SELE_INVALID;
    private E_PLATE meSelectedPlate = E_PLATE.NOPLATE;
    private E_ROTATIONEVENT meRotation = E_ROTATIONEVENT.NULL;
    
	public enum E_OPERATION
	{
		TOUCH,
		DISPLAY,
		ROTATE_CUBE,
		ROTATE_PLATE,
		SELECT_PLATE,
		CAPTURE,
		INVALID,
	}


    public CrazyCubeGLView(Context context, CrazyCubeGL cubeGL) {
		super(context);
		mCubeGL = cubeGL;
		InitEventCallbackFunc();
		this.setLongClickable(true);
        mRenderer = new CrazyCubeRender();
//        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        setRenderer(mRenderer);
//        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        RequestRender(E_OPERATION.DISPLAY);
//        requestRender();
        System.out.println("CrazyCubeGLView()");
	}

    
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCubeGL.StopClock();
		CrazyCubeAD.DestroyAD();
		super.surfaceDestroyed(holder);
	}
	
	void Capture()
	{
		RequestRender(E_OPERATION.CAPTURE, true);
	}



	void RequestRender(E_OPERATION eOper, Boolean bCapture)
	{
		meOperation = eOper;
		mbCapture = bCapture;
		requestRender();
		if(mSignal != null)
		{
			synchronized(mSignal) {
			    try {
					mSignal.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("RequestRender Error:" + e.getMessage());
				}
			}						
		}
	}
	
	private E_MOUSE_DIRECTION GetTouchDirection()
	{
		double dDegree = 0;
        E_MOUSE_DIRECTION TouchDirection = E_MOUSE_DIRECTION.NO_DIRECTION;
        
        dDegree = Math.atan2((miEndY - miStartY), (miEndX - miStartX)) * 180.0f / Math.PI;

        if (mbHaveDirection)
        {
            if (dDegree > -60.0 && dDegree <= 0.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.LEFT_RIGHT_UP;
            }
            else if (dDegree > 0.0 && dDegree <= 60.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.LEFT_RIGHT_DOWN;
            }
            else if (dDegree > 60.0 && dDegree <= 120.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.UP_DOWN;
            }
            else if (dDegree > 120.0 && dDegree <= 180.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.RIGHT_LEFT_DOWN;
            }
            else if (dDegree > -180.0 && dDegree <= -120.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.RIGHT_LEFT_UP;
            }
            else if (dDegree > -120.0 && dDegree <= -60.0)
            {
                TouchDirection = E_MOUSE_DIRECTION.DOWN_UP;
            }
        }

        return TouchDirection;
	}
	
	private void ObjSelection(E_SELECTION eObj)
	{
        if (eObj != E_SELECTION.SELE_INVALID)
        {
        	if (eObj.ordinal() <= E_SELECTION.SELE_BLK26.ordinal())
        	{
        		if(mCubeGL.mCube.eGameMode == E_GAMEMODE.AUTO_MODE)    //只有PLAYMODE时才处理魔方的触摸事件
        		{
        			mCubeGL.ShowAutoModeTouchTip();
        			return;
        		}
        		mCubeGL.SetGLMode(E_WORKMODE.SELECT_PLATE);
        		
        		E_MOUSE_DIRECTION eDirection = GetTouchDirection();
//        		ePlate = cubeGL.SelectPlate(eObj.ordinal(), iStartX, iStartY);
        		RequestRender(E_OPERATION.SELECT_PLATE, false);
        		System.out.println("OBJ:" + eObj + " Plate:" + meSelectedPlate + " Direction:" + eDirection + " Degree:" + mCubeGL.mCube.fYDegree);
        		mCubeGL.SetGLMode(E_WORKMODE.DRAW);
        		if (meSelectedPlate != E_PLATE.NOPLATE)
        		{
        			mCubeGL.StartClock();
        			mCubeGL.Rotate(eObj, meSelectedPlate, eDirection);
        			return;
        		}
        	}
        	else if (eObj == E_SELECTION.SELE_LEFT || eObj == E_SELECTION.SELE_RIGHT || eObj == E_SELECTION.SELE_DOWN)
        	{
        		E_MOUSE_AREA eMouseArea = E_MOUSE_AREA.NO_AREA;
        		System.out.println("OBJ:" + eObj);
        		switch(eObj)
        		{
        		case SELE_LEFT:
        			eMouseArea = E_MOUSE_AREA.MOUSE_LEFTUNSEEN;
        			break;
        		case SELE_RIGHT:
        			eMouseArea = E_MOUSE_AREA.MOUSE_RIGHTUNSEEN;
        			break;
        		case SELE_DOWN:
        			eMouseArea = E_MOUSE_AREA.MOUSE_DOWNUNSEEN;
        			break;
        		}
        		mCubeGL.bCubeClockRun = false;
        		mCubeGL.RaiseTouchActionEvent(E_MOUSE_DIRECTION.NO_DIRECTION, eMouseArea);
        		mCubeGL.bCubeClockRun = true;
        		return;
        	}
        	else if (eObj.ordinal() >= E_SELECTION.SELE_ZOOMOUT.ordinal()
        		&& eObj.ordinal() < E_SELECTION.TIP_0.ordinal())
        	{
        		if(eObj == E_SELECTION.SELE_HELP)
        		{
        			mCubeGL.mbHelpPressed = !mCubeGL.mbHelpPressed;
        		}
        		//触发按钮按下事件
        		mCubeGL.RaiseButtonPressedEvent(eObj, true);
        		RequestRender(E_OPERATION.DISPLAY, false);				//按钮恢复原来状态
        	}
        	else if (eObj.ordinal() >= E_SELECTION.TIP_0.ordinal()
            		&& eObj.ordinal() <= E_SELECTION.TIP_6.ordinal())
        	{
        		int iTipIndex = eObj.ordinal() - E_SELECTION.TIP_0.ordinal();
        		mCubeGL.TipButtonPressed(iTipIndex);        		
        	}        	
        }

	}


    @Override public boolean onTouchEvent(MotionEvent event) {
    	try
    	{
            if (mCubeGL.mCube.isTip)
            {
            	return super.onTouchEvent(event);
            }
            if (mCubeGL.mCube.isRotatting)
            {
            	return super.onTouchEvent(event);
            }
            if (mCubeGL.mCube.isAutoPlay)    //自动运行时不响应鼠标转动及键盘转动事件
            {
            	return super.onTouchEvent(event);
            }

            int iTouchX = (int) event.getX();
        	int iTouchY = (int) event.getY();
        	
        	switch (event.getAction())   
        	{  
        	//触摸屏幕时刻  
        	case MotionEvent.ACTION_DOWN:
        		miStartX = iTouchX;
        		miStartY = iTouchY;
        		mbHaveDirection = false;
//        		cubeGL.Mouse_Down(iTouchX, iTouchY);
        		break;  
        	//触摸并移动时刻  
        	case MotionEvent.ACTION_MOVE:
        		break;  
        	//终止触摸时刻  
        	case MotionEvent.ACTION_UP:
                if (mbHaveDirection)
                {
                	return super.onTouchEvent(event);
                }
        		miEndX = iTouchX;
        		miEndY = iTouchY;
                int iPathLen = (int)Math.sqrt(Math.pow((miEndX - miStartX), 2) + Math.pow((miEndY - miStartY), 2));
                if (iPathLen >= (int)(mCubeGL.miMousePrecision))
                {
                    mbHaveDirection = true;
                }

        		RequestRender(E_OPERATION.TOUCH, false);
        		ObjSelection(meSelectedObj);
//        		RequestRender(E_OPERATION.DISPLAY);
        		break;  
    		default:
    			System.out.println(event.getAction()+ " X:" + event.getX() + ", Y:" + event.getY());
    			break;
        	} 
    	}
    	catch(Exception e)
    	{
    		System.out.print("onTouchEvent Exception:" + e.getMessage());
    	}
    	return super.onTouchEvent(event);
    }
    
    //魔方放大缩小动作
    private void CubeZoomAction(int iDelta)
    {
    	if (iDelta == 0)
    	{
    		return;
    	}
        mCubeGL.mCube.XChgDreamCube4Display(mCubeGL.mCube.DREAM_CUBE_CACULATE);

        float fZoom = (float)iDelta / 5 / 100;
        float fOldZoom = (float)mCubeGL.mCfg.cubeConfig.iZoomFactor / 100;
        for(int i = 0; i < 4; i++)
        {
        	mCubeGL.mCfg.cubeConfig.fZoomFactor += fZoom;
//        	DisplayCube();
        	RequestRender(E_OPERATION.DISPLAY, false);
        }
        mCubeGL.mCfg.cubeConfig.fZoomFactor = fOldZoom + (float)iDelta / 100;
        mCubeGL.mCfg.cubeConfig.iZoomFactor = mCubeGL.mCfg.cubeConfig.iZoomFactor + iDelta;
    }

  	
    private void RotateCube(E_ROTATIONEVENT eRotate, Boolean bFlag)
    {
		int iCnt = 8;
		float fDeltaDegree = 10.0f; 
		meRotation = eRotate;
        mCubeGL.mCube.XChgDreamCube4Display(mCubeGL.mCube.DREAM_CUBE_CACULATE);
        mCubeGL.fRotate = 0.0f;

        if (eRotate == E_ROTATIONEVENT.Roll_x
            || eRotate == E_ROTATIONEVENT.Roll_X
            || eRotate == E_ROTATIONEVENT.Roll_y
            || eRotate == E_ROTATIONEVENT.Roll_Y
            || eRotate == E_ROTATIONEVENT.Roll_z
            || eRotate == E_ROTATIONEVENT.Roll_Z)
        { 
            /*整个旋转*/
    		if (mCubeGL.mlTimeUsed <= TIME_USE1)
    		{
    			iCnt = 17;
    			fDeltaDegree = 5.0f;         			
    		}
    		else
    		{
    			iCnt = 8;
    			fDeltaDegree = 10.0f; 
    		}
            for (int i = 0; i < iCnt; i++)
            {
            	mCubeGL.fRotate += fDeltaDegree;
            	RequestRender(E_OPERATION.ROTATE_CUBE, false);
//            	cubeGL.RotateWholeCube(eRotate);
            }
        }
        else
        {
            /*旋转单面*/
			if(mCubeGL.mbCapture)
			{
				iCnt = 2;
				fDeltaDegree = 30.0f; 
			}
			else
			{
				if(mCubeGL.bRotateFast)
				{
					if (mCubeGL.mlTimeUsed <= TIME_USE1)
					{
						iCnt = 8;
						fDeltaDegree = 10.0f;					
					}
					else
					{
						iCnt = 2;
						fDeltaDegree = 30.0f; 
					}
				}
				else
				{
					if (mCubeGL.mlTimeUsed <= TIME_USE1)
					{
						iCnt = 17;
						fDeltaDegree = 5.0f;					
					}
					else
					{
						iCnt = 8;
						fDeltaDegree = 10.0f; 
					}
				}
			}
            for (int i = 0; i < iCnt; i++)
            {
            	Boolean bCapture = false;
            	mCubeGL.fRotate += fDeltaDegree;
				if(mCubeGL.mbCapture && (1 == i) && bFlag)
				{
					//mbCapture=true  bFlag=true 时才捕获
					bCapture = true;
				}
            	RequestRender(E_OPERATION.ROTATE_PLATE, bCapture);
//            	cubeGL.RotateCubePlate(eRotate);
            }
        }
    }
    
    //智能提示
    private void IntelligentTip(E_ROTATIONEVENT eRotate, char cColorStep)
    {
		int iCnt = 2;
		float fDeltaDegree = 10.0f; 
		meRotation = eRotate;
        if (!mCubeGL.mCfg.cubeConfig.bIntelligentTip)
        {
            return;
        }
        
        mCubeGL.IntelligentTipText(cColorStep);
        mCubeGL.mCube.XChgDreamCube4Display(mCubeGL.mCube.DREAM_CUBE_CACULATE);
        mCubeGL.mCube.OnCubeSound(E_SOUND.TIP);
        mCubeGL.fRotate = 0.0f;
		if (mCubeGL.mlTimeUsed <= TIME_USE1)
		{
			iCnt = 10;
			fDeltaDegree = 3.0f;         			
		}
		else
		{
			iCnt = 3;
			fDeltaDegree = 10.0f; 
		}
        
        for (int i = 0; i < iCnt; i++)
        {
        	mCubeGL.fRotate += fDeltaDegree;
        	RequestRender(E_OPERATION.ROTATE_PLATE, false);
//            RotateCubePlate(eRotate);
            //Application.DoEvents();
        }
		if (mCubeGL.mlTimeUsed <= TIME_USE1)
		{
			iCnt = 9;
			fDeltaDegree = 3.0f;         			
		}
		else
		{
			iCnt = 2;
			fDeltaDegree = 10.0f; 
		}
        for (int i = 0; i < iCnt; i++)
        {
        	mCubeGL.fRotate -= fDeltaDegree;
//        	RotateCubePlate(eRotate);
        	RequestRender(E_OPERATION.ROTATE_PLATE, false);
            //Application.DoEvents();
        }
//        DisplayCube();
        RequestRender(E_OPERATION.DISPLAY, false);

        if (mCubeGL.mCube.rotateToVisible)
        {
        	mCubeGL.RotateToVisible(eRotate);
        }
       
    }



    private void InitEventCallbackFunc()
    {
    	
    	//
		mCubeGL.mCube.CubeOperationChangedFunc = new CubeOperationChangedEventListener() {
			
			public void event(CubeOperationEvent event) {
				// TODO Auto-generated method stub
				RequestRender(E_OPERATION.DISPLAY, false);
				mCubeGL.mCube.CheckCurrentStatus();
			}
		};
		
		mCubeGL.mCube.CubeOperationChangingFunc = new CubeOperationChangingEventListener() {
			
			public void event(CubeOperationEvent event) {
				// TODO Auto-generated method stub
				RotateCube(event.getEventState().eRotate, event.getEventState().bCapture);
			}
		};
		
		mCubeGL.mCube.CubeCompledLevelFunc = new CubeCompledLevelEventListener() {
			
			public void event(CubeCompledLevelEvent event) {
				// TODO Auto-generated method stub
				mCubeGL.mCube.GetAutoPlayStatus(mCubeGL.bStepStatus);      //刷新当前完成状态
		        
		        if(CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
		        {
		        	RequestRender(E_OPERATION.DISPLAY, false);       //桥式解法不能在这里刷新显示
		        }
		        
		        int iLevel = event.getEventState().eCompledLevel.ordinal();
		        
	            if (mCubeGL.mCube.eGameMode == E_GAMEMODE.STUDY_MODE)
	            {
	                if (mCubeGL.mCube.stTrainStatus.iStatus <= iLevel)
	                {
	                	mCubeGL.mCube.OnCubeSound(E_SOUND.FINISH);
	                	String szMsg = String.format(mCubeGL.mRes.getString(R.string.tipTrainStatus), iLevel);
	                	int iRet = mCubeGL.mCcs.ShowCongratulation(szMsg);
	                	mCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);            //退出自动(训练)模式
	                	mCubeGL.mCube.UpdateTipData();
	                    CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
//	                    DisplayCube();
	                    RequestRender(E_OPERATION.DISPLAY, false);
	                    if(CrazyCubeShow.DIALOG_SHARE == iRet)
	                	{
	                		ST_DreamCube cubeStart = mCubeGL.mCube.DREAM_CUBE_CACULATE;
							switch(iLevel)
	                		{
	                		case 1:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP1;
	                			break;
	                		case 2:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP2;
	                			break;
	                		case 3:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP3;
	                			break;
	                		case 4:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP4;
	                			break;
	                		case 5:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP5;
	                			break;
	                		case 6:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP6;
	                			break;
	                		case 7:
	                			cubeStart = mCubeGL.mCube.DREAM_CUBE_STEP7;
	                			break;
	                		}

	            			if(CrazyCubeShow.DIALOG_CAPTURE == mCubeGL.mCcs.ShowCapture(cubeStart, "", 360, 680))
	            			{
	            				szMsg = String.format(mCubeGL.mRes.getString(R.string.tipTrainFinish), iLevel);
	            				mCubeGL.SendShareMsg(1, szMsg);
	            			}
	            			System.gc();
	                	}

	                }
	            }
	            else 
	            {
	            	if((CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST 
	            			&& mCubeGL.bStepStatus[0] == true 
	            			&& mCubeGL.bStepStatus[1] == true 
	            			&& mCubeGL.bStepStatus[2] == true
	            			&& mCubeGL.bStepStatus[3] == true 
	            			&& mCubeGL.bStepStatus[4] == true 
	            			&& mCubeGL.bStepStatus[5] == true
	            			&& mCubeGL.bStepStatus[6] == true)
	            		||(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE 
	            			&& mCubeGL.bStepStatus[0] == true 
	            			&& mCubeGL.bStepStatus[1] == true 
	            			&& mCubeGL.bStepStatus[2] == true
	            			&& mCubeGL.bStepStatus[3] == true))	            			
	            	{
	            		if (mCubeGL.mCube.eGameMode == E_GAMEMODE.AUTO_MODE)
	            		{
	            			mCubeGL.mCcs.ShowMsg(R.string.tipExitAutoPlay);
	            			mCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);            //退出自动(训练)模式
	            			mCubeGL.mCube.ClearStep();
//		            		DisplayCube();
	            			RequestRender(E_OPERATION.DISPLAY, false);
	            		}
	            		else if(mCubeGL.mCube.eGameMode == E_GAMEMODE.PLAY_MODE)
	            		{
            				if(mCubeGL.mCube.IsFinish() && !mCubeGL.bCubeFinish && mCubeGL.mCube.manualPlayStep.length() > 5)      //避免出现翻转魔方也提示复原成功的提示
            				{
            					mCubeGL.bCubeFinish = true;
            					mCubeGL.mCube.OnCubeSound(E_SOUND.FINISH);								
								if(!mCubeGL.mbCapture)
								{
	            					mCubeGL.mCcs.ShowMsg(R.string.tipFinish);
								}
//	            		    	cube.ClearAutoPlayStep();
            					mCubeGL.mCube.UpdateTipData();
            					mCubeGL.StopClock();
//	            				DisplayCube();
            					RequestRender(E_OPERATION.DISPLAY, false);
            				}
	            		}
	            	}
	            }
			}
		};
		
    	mCubeGL.ShakeFunc = new ShakeEventListener() {
			
			public void event(ShakeEvent event) {
				// TODO Auto-generated method stub
				E_SHAKE eShake = event.getEventState().eShake;
				if(mCubeGL.mCube.isRotatting)
				{
					System.out.println("Receive unhandle SHAKE event:" + eShake.toString());
					return;
				}
				 
				if(eShake == E_SHAKE.SHAKE_DISRUPT || eShake == E_SHAKE.SHAKE_AUTO)
				{
					E_SELECTION	eObj;
					if(eShake == E_SHAKE.SHAKE_DISRUPT)
					{
						eObj = E_SELECTION.SELE_DISRUPT;
					}
					else if(eShake == E_SHAKE.SHAKE_AUTO) 
					{
						eObj = E_SELECTION.SELE_AUTOPLAY;
					}
					else
					{
						eObj = E_SELECTION.SELE_INVALID;
					}
					mCubeGL.RaiseButtonPressedEvent(eObj, false);
				}
				else if(eShake == E_SHAKE.SHAKE_NEXT)
				{
	        		mCubeGL.TipShake(true);
				}
				else if(eShake == E_SHAKE.SHAKE_PREV)
				{
	        		mCubeGL.TipShake(false);
				}
			}
		};

		
		mCubeGL.ClockFunc = new ClockEventListener() {
			
			public void event(ClockEvent event) {
				// TODO Auto-generated method stub
				RequestRender(E_OPERATION.DISPLAY, false);
			}
		};

		
		mCubeGL.TouchActionFunc = new TouchActionEventListener() {
			
			public void event(TouchActionEvent event) {
				// TODO Auto-generated method stub
				int iRet = 0;
				if(mCubeGL.mbHelpPressed)
				{
					switch (event.getEventState().eTouchArea)
		            { 
	                case MOUSE_LEFTUNSEEN:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleLeft),mCubeGL.mRes.getString(R.string.helpLeft), R.drawable.left);
				        break;
	                case MOUSE_RIGHTUNSEEN:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleRight),mCubeGL.mRes.getString(R.string.helpRight), R.drawable.right);
	                    break;
	                case MOUSE_DOWNUNSEEN:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleDown),mCubeGL.mRes.getString(R.string.helpDown), R.drawable.down);
	                    break;
	            	}
        			if(CrazyCubeShow.DIALOG_SHARE == iRet)
        			{
        				mCubeGL.ShowShare(mCubeGL.mCon.getString(R.string.szGetHelp));	
        			}
					mCubeGL.SetHelpStatus(false);
					return;
				}

				switch (event.getEventState().eTouchArea)
	            { 
                case MOUSE_LEFTUNSEEN:
                	mCubeGL.mCube.ROTATION(E_ROTATIONEVENT.Roll_y, mCubeGL.mCube.DREAM_CUBE_CACULATE, false);
			        break;
                case MOUSE_RIGHTUNSEEN:
                	mCubeGL.mCube.ROTATION(E_ROTATIONEVENT.Roll_Y, mCubeGL.mCube.DREAM_CUBE_CACULATE, false);
                    break;
                case MOUSE_DOWNUNSEEN:
                	mCubeGL.mCube.ROTATION(E_ROTATIONEVENT.Roll_z, mCubeGL.mCube.DREAM_CUBE_CACULATE, false);
                    break;
            	}
            
			}
		};

		mCubeGL.ButtonPressedFunc = new ButtonPressedEventListener() {
			
			public void event(ButtonPressedEvent event) {
				// TODO Auto-generated method stub
            	Message message = new Message(); 
        		if(mCubeGL.mbHelpPressed)
        		{
        			if((event.getEventState().eButtonPressed) == E_SELECTION.SELE_HELP)
        			{
        				mCubeGL.mCcs.ShowMsg(R.string.tipHelp);
        				return;
        			}
        			int iRet = 0;
        			switch (event.getEventState().eButtonPressed)
		            { 
	                case SELE_ZOOMOUT:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleZoomOut),mCubeGL.mRes.getString(R.string.helpZoomOut), R.drawable.zoomout);
				        break;
	                case SELE_ZOOMIN:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleZoomIn),mCubeGL.mRes.getString(R.string.helpZoomIn), R.drawable.zoomin);
	                    break;
	                case SELE_DISRUPT:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleDisrupt),mCubeGL.mRes.getString(R.string.helpDisrupt), R.drawable.disrupt);
	                	break;
	                case SELE_AUTOPLAY:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleAutoPlay),mCubeGL.mRes.getString(R.string.helpAutoPlay), R.drawable.autoplay);    	                	
	                	break;
	                case SELE_RESTART:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleRestart),mCubeGL.mRes.getString(R.string.helpRestart), R.drawable.restart);    	                	
	                	break;    	                	
	                case SELE_STUDY:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleStudy),mCubeGL.mRes.getString(R.string.helpStudy), R.drawable.study);    	                	
	                	break;    	                	
	                case SELE_SHARE:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleShare),mCubeGL.mRes.getString(R.string.helpShare), R.drawable.share);    	                	
	                	break;    	                	
	                case SELE_STAR:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleStar),mCubeGL.mRes.getString(R.string.helpStar), R.drawable.star_pressed);    	                	
	                	break;    	                	
	                case SELE_MENU:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleMenu),mCubeGL.mRes.getString(R.string.helpMenu), R.drawable.menu);    	                	
	                	break;    	                	
	                case SELE_SETUP:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleSetup),mCubeGL.mRes.getString(R.string.helpSetup), R.drawable.setup);    	                	
	                	break;    	                	
	                case SELE_TIMER:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleTimer),mCubeGL.mRes.getString(R.string.helpTimer), R.drawable.time1);    	                	
	                	break;    	                	
	                case SELE_STEP:
	                	iRet = mCubeGL.mCcs.ShowHelp(mCubeGL.mRes.getString(R.string.titleStep),mCubeGL.mRes.getString(R.string.helpStep), R.drawable.icon);    	                	
	                	break;    	                	
		            }
        			if(CrazyCubeShow.DIALOG_SHARE == iRet)
        			{
        				mCubeGL.ShowShare(mCubeGL.mCon.getString(R.string.szGetHelp));        				
        			}
        			mCubeGL.SetHelpStatus(false);
        			return;
        		}
        		else
        		{
					switch (event.getEventState().eButtonPressed)
		            { 
	                case SELE_ZOOMOUT:
        				if (mCubeGL.mCfg.cubeConfig.iZoomFactor < 130)
        				{
        					CubeZoomAction(10);
        				}
				        break;
	                case SELE_ZOOMIN:
        				if (mCubeGL.mCfg.cubeConfig.iZoomFactor > 60)
        				{
        					CubeZoomAction(-10);
        				}
	                    break;
	                case SELE_DISRUPT:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_DISRUPT;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;
	                case SELE_AUTOPLAY:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
	                	mCubeGL.ResetClock();
//	                	DisplayCube();
	                	RequestRender(E_OPERATION.DISPLAY, false);
	                	mCubeGL.mCube.AutoStep();
	                	mCubeGL.mCube.UpdateTipData();
	        			if(mCubeGL.mCube.trainPlayStep.length() > 1)
	        			{
	        				mCubeGL.mCcs.ShowMsg(R.string.tipAutoPlay);	
	        			}
	        			else
	        			{
	        				mCubeGL.mCcs.ShowMsg(R.string.tipNotDisturb);
	        				mCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);            //退出自动(训练)模式
	        				mCubeGL.mCube.ClearStep();
	        			}
//	        			DisplayCube();
	        			RequestRender(E_OPERATION.DISPLAY, false);
	                	break;
	                case SELE_RESTART:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
	                	mCubeGL.ResetClock();
//	                	DisplayCube();
	                	RequestRender(E_OPERATION.DISPLAY, false);
	                	mCubeGL.mCube.RestoreCube();
	                	mCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);
	                	mCubeGL.mCube.ClearStep();
	                	mCubeGL.mCube.ClearAutoPlayStatus();
	                	mCubeGL.mCube.GetAutoPlayStatus(mCubeGL.bStepStatus);      //刷新当前完成状态
	                	mCubeGL.mCube.CheckCurrentStatus();
	                	mCubeGL.mCube.UpdateTipData();
	                	mCubeGL.mCcs.ShowMsg(R.string.tipRestart);
	        			RequestRender(E_OPERATION.DISPLAY, false);
						break;    	
	                case SELE_PLAY:    //通过主菜单的开始游戏，可以进到这个流程。屏幕上并没有PLAY按钮
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
	                	mCubeGL.mCube.SetGameMode(E_GAMEMODE.PLAY_MODE);
	                	mCubeGL.mCube.ClearStep();
	                	mCubeGL.mCube.ClearAutoPlayStatus();
	                	mCubeGL.mCube.GetAutoPlayStatus(mCubeGL.bStepStatus);      //刷新当前完成状态
	                	mCubeGL.mCube.CheckCurrentStatus();               
	                	mCubeGL.mCube.UpdateTipData();
	                	break;
	                case SELE_STUDY:   
	                {
	                	int iStudySpend = 5;
	                	CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCubeGL.mCon, mCubeGL);
	                	int iPoints = ccadp.GetADPoint(mCubeGL.mCon);
	                	if(iPoints > iStudySpend)
	                	{
	                		if(ccadp.SpendADPoint(iStudySpend))
	                		{
	                			mCubeGL.SendMsg(String.format(mCubeGL.mCon.getString(R.string.tipStudySpend), iStudySpend));
	                		}
		                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_STUDY;
		                	mCubeGL.ResetClock();
		                	mCubeGL.ShowStudy();
		                	RequestRender(E_OPERATION.DISPLAY, false);
		                	System.gc();
	                	}
	                	else
	                	{
	                		mCubeGL.SendMsg(mCubeGL.mCon.getString(R.string.tipPointsNotEnough));
	                	}
	                	break;
	                }
	                case SELE_SHARE:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_CAPTURE;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;  
	                case SELE_TUTORIAL:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_TUTORIAL;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;
	                case SELE_EXPORT:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_EXPORT;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;
	                case SELE_USER:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_USER;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;    	                	
	                case SELE_DAILYAWARD:
	                	CrazyCubeMain.meActiveMenu = E_MENU.MAIN_PLAY;
						message.what = CrazyCubeGL.CUBE_DAILYAWARD;  
						mCubeGL.mHandler.sendMessage(message);
	                	break;    	                	
//	                case SELE_HELP:
//	                	ccs.ShowHelp(context,res.getString(R.string.titleHelp),res.getString(R.string.helpHelp), R.drawable.help);
//	                    break;
	                case SELE_MENU:
	                	mCubeGL.mCfg.cubeConfig.bShowButtonOnDesktop = !mCubeGL.mCfg.cubeConfig.bShowButtonOnDesktop;
	                	break;    	                	
	                case SELE_SETUP:
	                {
	                	mCubeGL.mCube.bAutoTip = false;
	                	int iRet = mCubeGL.mCcs.ShowSetup();   
	                	mCubeGL.mCube.bAutoTip = true;
	    				if(CrazyCubeShow.DIALOG_SHARE == iRet)
	        			{
	    					mCubeGL.ShowShare(mCubeGL.mCon.getString(R.string.szGetSetup));        				
	        			}
	                	break;
	                }
	                case SELE_HELP:
	                {
	                	int iRet = mCubeGL.mCcs.ShowHelp();
	                	if(CrazyCubeShow.DIALOG_SHARE == iRet)
	                	{
	                		mCubeGL.ShowShare(mCubeGL.mCon.getString(R.string.szGetHelp));	
	                	}
	                	break;
	                }
	                case SELE_ABOUT:
	                	if(CrazyCubeShow.DIALOG_SUGGEST == mCubeGL.mCcs.ShowAbout())
	                	{
	                		mCubeGL.mCcs.ShowSuggest();
	                	}
	                	break;    	                	
	                case SELE_ADVANCE:
	                	mCubeGL.mCcs.ShowAdvance();
	                	break;    	                	
	            	}
//					DisplayCube();
        		}
			}
		};
		

		mCubeGL.mCube.CubeSoundFunc = new CubeSoundEventListener() {
			
			public void event(CubeSoundEvent event) {
				// TODO Auto-generated method stub
				
				E_SOUND eSound = event.getSound();
				
				if (mCubeGL.mCfg.cubeConfig.bRotateSound && (!mCubeGL.mbCapture))
				{
					mCubeGL.mSound.playSound(eSound, 0);
				}
			}
		};
		
		mCubeGL.mCube.CubeOperationTipFunc = new CubeOperationTipEventListener() {
			
			public void event(CubeOperationEvent event) {
				// TODO Auto-generated method stub
				
				IntelligentTip(event.getEventState().eRotate, event.getEventState().cColorStep);
			}
		};

    }
    
    private void GetTouchObj()
    {
	        meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);
	        if(meSelectedObj == E_SELECTION.SELE_INVALID)
	        {
	        	mCubeGL.miSelectDelay = 10;
	        	meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);
	            if(meSelectedObj == E_SELECTION.SELE_INVALID)
	            {
	            	mCubeGL.miSelectDelay = 20;
	            	meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);	
	                if(meSelectedObj == E_SELECTION.SELE_INVALID)
	                {
	                	mCubeGL.miSelectDelay = 30;
	                	meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);
	                    if(meSelectedObj == E_SELECTION.SELE_INVALID)
	                    {
	                    	mCubeGL.miSelectDelay = 40;
	                    	meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);	
	                        if(meSelectedObj == E_SELECTION.SELE_INVALID)
	                        {
	                        	mCubeGL.miSelectDelay = 50;
	                        	meSelectedObj = mCubeGL.SelectObj(miStartX, miStartY);	
	                        }
	                    }
	                }
	            }
	        }
    }

  	
	private class CrazyCubeRender implements GLSurfaceView.Renderer {
	
	    
		public CrazyCubeRender()
		{
			System.out.println("CrazyCubeRender.CrazyCubeRender()");
		}
		
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//	        gl.glMatrixMode(GL10.GL_MODELVIEW);
//	        gl.glLoadIdentity();
			
			switch(meOperation)
			{
			case SELECT_PLATE:
        		meSelectedPlate = mCubeGL.SelectPlate(meSelectedObj.ordinal(), miStartX, miStartY);
    	        mCubeGL.SetGLMode(E_WORKMODE.DRAW);
    	        mCubeGL.DisplayCube();				
				break;
			case TOUCH:
    	        mCubeGL.SetGLMode(E_WORKMODE.PICK);
    	        GetTouchObj();
     	        mCubeGL.SetGLMode(E_WORKMODE.DRAW);
    	        mCubeGL.DisplayCube();				
 				break;
			case ROTATE_CUBE:
				mCubeGL.RotateWholeCube(meRotation);
				break;
			case ROTATE_PLATE:
				mCubeGL.RotateCubePlate(meRotation, mbCapture);				
				break;
			case DISPLAY:
    	        mCubeGL.SetGLMode(E_WORKMODE.DRAW);
    	        mCubeGL.DisplayCube();				
				break;
			case CAPTURE:
    	        mCubeGL.SetGLMode(E_WORKMODE.DRAW);
    	        mCubeGL.DisplayCube();				
    	        mCubeGL.Capture();
				break;
			default:
    	        mCubeGL.SetGLMode(E_WORKMODE.DRAW);
    	        mCubeGL.DisplayCube();				
				break;
			}
			if(mSignal != null)
			{
				synchronized(mSignal) {
	    	        mSignal.notify();
	    		}							
			}
		}
	
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			mCubeGL.iScreenWidth = width;
			mCubeGL.iScreenHeight = height;
			System.out.println("CrazyCubeRender.onSurfaceChanged");
		}
	
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			mSignal = new Object();
			mCubeGL.InitGlEnvirment(gl);
			System.out.println("CrazyCubeRender.onSurfaceCreated");
		}
	
	}
}

