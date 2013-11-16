package elong.Cube;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import elong.CrazyCube.CrazyCubeMain;
import elong.CrazyCube.R;
import elong.Cube.CrazyCubeDevice.E_DEVICE;
import elong.Cube.Cube3.CrazyCube;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_METHOD;
import elong.Cube.Cube3.CrazyCube.E_STUDY_STATUS;
import elong.Cube.Cube3.CrazyCube.ST_DreamCube;
import elong.Cube.Cube3.MethodBridge;
import elong.Cube.Cube3.MethodLayerFirst;

public class CrazyCubeShow {

	//对话框返回值
	public static final int DIALOG_OK = 0;
	public static final int DIALOG_IKNOW = 1;
	public static final int DIALOG_STUDY = 2;
	public static final int DIALOG_MORE = 3;
	public static final int DIALOG_LASTSTEP = 4;
	public static final int DIALOG_NEXTSTEP = 5;
	public static final int DIALOG_SUGGEST = 6;
	public static final int DIALOG_YES = 7;
	public static final int DIALOG_NO = 8;
	public static final int DIALOG_CAPTURE = 9;
	public static final int DIALOG_SHARE = 10;
	
	public static final int DIALOG_RETURN = 98;
	public static final int DIALOG_NOTSELECT = 99;

	//积分相关参数
	final int CUBE_CREDITS = 500;
	final int CLOCK_CREDITS = 50;
	final int STEP_CREDITS = 50;
	final int SHAKE_CREDITS = 80;
	final int STYLE1_CREDITS = 60;
	final int METHOD1_CREDITS = 200;
	

	
	//魔方参数设置

	
    public class DialogModel extends AlertDialog{
        int dialogResult;  
        Handler mMsgHandler ;
		public DialogModel(Context arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}
		
		public int getDialogResult()  
	    {  
	        return dialogResult;  
	    }  
	    public void setDialogResult(int dialogResult)  
	    {  
	        this.dialogResult = dialogResult;  
	    }  

	      
	    public void endDialog(int result)  
	    {  
	        setDialogResult(result);  
	        Message m = mMsgHandler.obtainMessage();  
	        mMsgHandler.sendMessage(m);  
	    }  
	      
		public int showDialog()  
	    {  
	        mMsgHandler = new Handler() {  
	            @Override  
	              public void handleMessage(Message mesg) {  
	           		throw new RuntimeException();
	              }  
	          };  
	        super.show();  
			
	        try {  
	            Looper.getMainLooper();
				Looper.loop();  
	        }  
	        catch(RuntimeException e2)  
	        {  
	        	System.out.println("ModelDialg Looper have exit, the reason is:" + e2.getCause());
	        }  
	        return dialogResult;  
	    }

		public int showDialog(float alpha)  
	    {  
	        mMsgHandler = new Handler() {  
	            @Override  
	              public void handleMessage(Message mesg) {  
	           		throw new RuntimeException();
	              }  
	          };  
	        super.show();  
			Window win = getWindow();
			LayoutParams params = new LayoutParams();
			params.alpha = alpha;
			win.setAttributes(params);
			
	        try {  
	            Looper.getMainLooper();
				Looper.loop();  
	        }  
	        catch(RuntimeException e2)  
	        {  
	        	System.out.println("ModelDialg Looper have exit, the reason is:" + e2.getCause());
	        }  
	        return dialogResult;  
	    }

		public int showDialog(int width, int height)  
	    {  
	        mMsgHandler = new Handler() {  
	            @Override  
	              public void handleMessage(Message mesg) {  
	                throw new RuntimeException();  
	              }  
	          };  
	        super.show();  
			Window win = getWindow();
			LayoutParams params = new LayoutParams();
			Display d = win.getWindowManager().getDefaultDisplay();
			if(d.getWidth() < width)
			{
				params.width = d.getWidth();
			}
			else
			{
				params.width = width;
			}
			if(d.getHeight() < height)
			{
				params.height = d.getHeight();
			}
			else
			{
				params.height = height;
			}
			//params.alpha = 0.9f;
			win.setAttributes(params);
			
	        try {  
	            Looper.getMainLooper();
				Looper.loop();  
	        }  
	        catch(RuntimeException e2)  
	        {
	        	System.out.println("ModelDialog Looper have exit");
	        }  
	        return dialogResult;  
	    }

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
				endDialog(DIALOG_RETURN);
			}
			return super.onKeyDown(keyCode, event);
		}

		@Override
		public void onAttachedToWindow() {
			// TODO Auto-generated method stub
			super.onAttachedToWindow();
		}
    
	    
    }
    
    CrazyCubeGL mCubeGL = null;
    Context mCon = null;
    Resources mRes = null;
    
	public CrazyCubeShow(CrazyCubeGL gl, Context con, Resources res) {
		super();
		mCubeGL = gl;
		mCon = con;
		mRes = res;
		// TODO Auto-generated constructor stub
	}

	public DialogModel DialogModel(Context context) {
		// TODO Auto-generated method stub
		return new DialogModel(context);
	}


	public void ShowMsg(int iTextId)
	{
		ShowMsg(iTextId, Toast.LENGTH_LONG);
	}
	
    protected void ShowMsg(int iTextId, int duration)
	{		   
		String szMsg = mRes.getString(iTextId);
        LayoutInflater factory = LayoutInflater.from(mCon);
		View layout = factory.inflate(R.layout.toast, null);
		ImageView image = (ImageView) layout.findViewById(R.id.toastImg);
		image.setImageResource(R.drawable.icon);
		TextView toastMsg = (TextView) layout.findViewById(R.id.toastMsg);
		toastMsg.setText(szMsg);
		Toast toast = new Toast(mCon);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}
	
    public void ShowMsg(String szMsg, int duration, int colors)
	{		   
        LayoutInflater factory = LayoutInflater.from(mCon);
		View layout = factory.inflate(R.layout.toast, null);
		ImageView image = (ImageView) layout.findViewById(R.id.toastImg);
		image.setImageResource(R.drawable.icon);
		TextView toastMsg = (TextView) layout.findViewById(R.id.toastMsg);
		toastMsg.setText(szMsg);
		toastMsg.setTextColor(colors);
		Toast toast = new Toast(mCon);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}

    public int ShowAbout()
    {
    	if(!CrazyCubeMain.isShowAdTime())
    	{
    		return DIALOG_OK;
    	}
    	String szMsg = "";
    	String szVer = "";
    	String szCopyright = "";
        try {   
            szVer = mCon.getPackageManager().getPackageInfo("elong.CrazyCube", 0).versionName;   
        } catch (NameNotFoundException e) {   
        	System.out.println(e.getMessage());   
        }   
        
		int iPoints = 0;
		
		CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, mCubeGL);
		iPoints = ccadp.GetADPoint(mCon);
    	szMsg += String.format(mRes.getString(R.string.szPoint), iPoints);
//    	
//    	if(iPoints >= CrazyCubeADPoints.NO_AD_POINT)
//    	{
//    		szMsg += "\r\n" + res.getString(R.string.szActived);
//    	}
//    	else
//    	{
//    		if(0 == iPoints)
//    		{
//    			szMsg += "\r\n" + res.getString(R.string.szIfnotConnect);
//    		}
//    		szMsg += "\r\n" + String.format(res.getString(R.string.szNotActive), CrazyCubeADPoints.NO_AD_POINT - iPoints);	
//    	}

    	szVer = String.format(mRes.getString(R.string.szVersion), szVer);
    	szVer += "\r\n" + String.format(mRes.getString(R.string.szSDKVersion), Build.VERSION.SDK);
    	szCopyright = mRes.getString(R.string.designer);
    	szCopyright += "\r\n" + mRes.getString(R.string.weibo);    	
    	szCopyright += "\r\n" + mRes.getString(R.string.szAbout);
    	
    	final DialogModel dialogBox = new DialogModel(mCon);
        dialogBox.setTitle(R.string.app_name);
        dialogBox.setIcon(R.drawable.about);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(R.layout.about, null);
        
        TextView txtMsg = (TextView)dialogView.findViewById(R.id.txtMsg);
        TextView txtVer = (TextView)dialogView.findViewById(R.id.txtVersion);
        TextView txtCopyright = (TextView)dialogView.findViewById(R.id.txtCopyRight);
        
        txtMsg.setText(szMsg);
        txtVer.setText(szVer);
        txtCopyright.setText(szCopyright);

        dialogBox.setView(dialogView);

        dialogBox.setButton(mCon.getString(R.string.dialogOK), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_OK);
			}
		});
//        dialogBox.setButton2(context.getString(R.string.dialogSuggest), new DialogInterface.OnClickListener() {
//			
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				dialogBox.endDialog(DIALOG_SUGGEST);
//			}
//		});

		dialogBox.showDialog();
		return dialogBox.dialogResult;
    }
    
    private String GetDeviceID()
    {
    	//与手机建立连接    	
    	TelephonyManager tm = (TelephonyManager)mCon.getSystemService(Context.TELEPHONY_SERVICE);

    	//获取手机IMEI
    	String szDeviceId = tm.getDeviceId();
    	return szDeviceId;
    }

    private String GetMacAddress() 
    {
        WifiManager wifi = (WifiManager) mCon.getSystemService(Context.WIFI_SERVICE);
    	WifiInfo info = wifi.getConnectionInfo();
    	return info.getMacAddress();
    } 

    
    private String GetDeviceKey()
    {
		String szDeviceId = GetDeviceID();
		if (szDeviceId == null)
		{
			szDeviceId = GetMacAddress();
		}
		return szDeviceId;
    }
    
    
    public void SetCubeStatus()
    {
		CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, mCubeGL);
		
		int iPoints = ccadp.GetADPoint(mCon);
		int iNeedPoint = CrazyCubeADPoints.NO_AD_POINT - iPoints;
		String szKey = CrazyCubeCfg.CUBE_KEY;
		if(iNeedPoint > 0)
		{
			mCubeGL.mCfg.SetAdvList(szKey, "");
			System.out.println("The app removed AD failure");
		}
		else
		{
			String szDeviceId = GetDeviceKey();
			if (szDeviceId != null)
			{
				String szMd5Key = GetMD5Key(szKey, szDeviceId);
				szMd5Key = GetMD5(szMd5Key); 
				mCubeGL.mCfg.SetAdvList(szKey, szMd5Key);
				System.out.println("The app have removed AD");
			}
			else
			{
				mCubeGL.mCfg.SetAdvList(szKey, "");
				System.out.println("The app removed AD failure");	
			}
		}

    }
    
    private String GetMD5Key(String szAdvName, String szDeviceId)
    {
    	String szKey = "";
    	szKey = szAdvName + szDeviceId + szAdvName.toLowerCase();
    	return szKey;
    }
    
    private String GetMD5(String szKey)
    {   
        MessageDigest md5;
        byte[] m = null;
		try 
		{
			md5 = MessageDigest.getInstance("MD5");
	        md5.update(szKey.getBytes());   
	        m = md5.digest();//加密    
	    } 
		catch (NoSuchAlgorithmException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

        return getString(m);   
    }   
    private static String getString(byte[] b)
    {   
    	if(null == b)
    	{
    		return "";
    	}
        StringBuffer sb = new StringBuffer();   
        for(int i = 0; i < b.length; i ++){   
        	sb.append(b[i]);   
        }   
        return sb.toString();   
    }

    
    public int ShowAdvance()
    {
    	
    	final DialogModel dialogBox = new DialogModel(mCon);
        dialogBox.setTitle(R.string.menuAdvance);
        dialogBox.setIcon(R.drawable.advance);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(R.layout.advance, null);
        
        String szMsg = "";
        TextView advText11 = (TextView)dialogView.findViewById(R.id.advText11);
        TextView advText12 = (TextView)dialogView.findViewById(R.id.advText12);
        final CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, mCubeGL);
		int iPoints = ccadp.GetADPoint(mCon);
        szMsg = String.format(mRes.getString(R.string.szAdvText11), iPoints);
        advText11.setText(szMsg);
    	if(iPoints >= CrazyCubeADPoints.NO_AD_POINT)
    	{
    		szMsg = mRes.getString(R.string.szAdvText11_1);
    	}
    	else
    	{
    		szMsg = mRes.getString(R.string.szAdvText11_2);	
    	}
    	advText12.setText(szMsg);
    	
    	mCubeGL.mCfg.loadAdvConfig();
    	
        TextView advClock = (TextView)dialogView.findViewById(R.id.advClock);
        final Button advBtnClock = (Button)dialogView.findViewById(R.id.advBtnClock);
        final TextView advClockActive = (TextView)dialogView.findViewById(R.id.advClockActive);
        final ImageView advImgClock = (ImageView)dialogView.findViewById(R.id.advImgClock);
        final TextView advTxtClock = (TextView)dialogView.findViewById(R.id.advClock);
        advClock.setText(R.string.szAdvClock);
        boolean bClockActive = mCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.CLOCK_KEY);
        if (bClockActive)
        {
        	advBtnClock.setText(R.string.szAdvBtnActived);
        	advBtnClock.setEnabled(false);
        	advClockActive.setText(R.string.szAdvActived);
        }
        else
        {
        	advBtnClock.setText(R.string.szAdvConsume);
        	szMsg = String.format(mRes.getString(R.string.szAdvActiveTip), CLOCK_CREDITS);
        	advClockActive.setText(szMsg);
        }

        TextView advStep = (TextView)dialogView.findViewById(R.id.advStep);
        final Button advBtnStep = (Button)dialogView.findViewById(R.id.advBtnStep);
        final TextView advStepActive = (TextView)dialogView.findViewById(R.id.advStepActive);
        final ImageView advImgStep = (ImageView)dialogView.findViewById(R.id.advImgStep);
        final TextView advTxtStep = (TextView)dialogView.findViewById(R.id.advStep);
        advStep.setText(R.string.szAdvStep);
        boolean bStepActive = mCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.STEP_KEY);
        if (bStepActive)
        {
        	advBtnStep.setText(R.string.szAdvBtnActived);
        	advBtnStep.setEnabled(false);
        	advStepActive.setText(R.string.szAdvActived);
        }
        else
        {
        	advBtnStep.setText(R.string.szAdvConsume);
        	szMsg = String.format(mRes.getString(R.string.szAdvActiveTip), STEP_CREDITS);
        	advStepActive.setText(szMsg);
        }

        TextView advShake = (TextView)dialogView.findViewById(R.id.advShake);
        final Button advBtnShake = (Button)dialogView.findViewById(R.id.advBtnShake);
        final TextView advShakeActive = (TextView)dialogView.findViewById(R.id.advShakeActive);
        final ImageView advImgShake = (ImageView)dialogView.findViewById(R.id.advImgShake);
        final TextView advTxtShake = (TextView)dialogView.findViewById(R.id.advShake);
        advShake.setText(R.string.szAdvShake);
        boolean bShakeActive = mCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.SHAKE_KEY);
        if (bShakeActive)
        {
        	advBtnShake.setText(R.string.szAdvBtnActived);
        	advBtnShake.setEnabled(false);
        	advShakeActive.setText(R.string.szAdvActived);
        }
        else
        {
        	advBtnShake.setText(R.string.szAdvConsume);
        	szMsg = String.format(mRes.getString(R.string.szAdvActiveTip), SHAKE_CREDITS);
        	advShakeActive.setText(szMsg);
        }
        /*
        TextView advStyle = (TextView)dialogView.findViewById(R.id.advStyle);
        final Button advBtnStyle = (Button)dialogView.findViewById(R.id.advBtnStyle);
        final TextView advStyleActive = (TextView)dialogView.findViewById(R.id.advStyleActive);
        advStyle.setText(R.string.szAdvStyle);
        boolean bStyleActive = GetAdvStatus(context, STYLE1_KEY);
        if (bStyleActive)
        {
        	advBtnStyle.setText(R.string.szAdvBtnActived);
        	advBtnStyle.setEnabled(false);
        	advStyleActive.setText(R.string.szAdvActived);
        }
        else
        {
        	advBtnStyle.setText(R.string.szAdvConsume);
        	szMsg = String.format(res.getString(R.string.szAdvActiveTip), STYLE1_CREDITS);
        	advStyleActive.setText(szMsg);
        }
        */

        TextView advMethod1 = (TextView)dialogView.findViewById(R.id.advMethod1);
        final Button advBtnMethod1 = (Button)dialogView.findViewById(R.id.advBtnMethod1);
        final TextView advMethod1Active = (TextView)dialogView.findViewById(R.id.advMethod1Active);
        advMethod1.setText(R.string.szAdvMethod1);
        boolean bMethod1Active = mCubeGL.mCfg.GetAdvStatus(CrazyCubeCfg.METHOD1_KEY);
        if (bMethod1Active)
        {
        	advBtnMethod1.setText(R.string.szAdvBtnActived);
        	advBtnMethod1.setEnabled(false);
        	advMethod1Active.setText(R.string.szAdvActived);
        }
        else
        {
        	advBtnMethod1.setText(R.string.szAdvConsume);
        	szMsg = String.format(mRes.getString(R.string.szAdvActiveTip), METHOD1_CREDITS);
        	advMethod1Active.setText(szMsg);
        }

        dialogBox.setView(dialogView);
        
        View.OnClickListener helpClickListener = null;
        helpClickListener = new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iRet = 0;
				switch(v.getId())
				{
				case R.id.advImgClock:
				case R.id.advClock:
					iRet = ShowHelp(mRes.getString(R.string.titleTimer), mRes.getString(R.string.helpTimer), R.drawable.time1);
					break;
				case R.id.advImgStep:
				case R.id.advStep:
					iRet = ShowHelp(mRes.getString(R.string.titleStep), mRes.getString(R.string.helpStep), R.drawable.icon);
					break;
				case R.id.advImgShake:
				case R.id.advShake:
					iRet = ShowHelp(mRes.getString(R.string.titleShake), mRes.getString(R.string.helpShake), R.drawable.icon);
					break;
				}
    			if(CrazyCubeShow.DIALOG_SHARE == iRet)
    			{
    					
    			}				
			}
		};
		
		advImgClock.setOnClickListener(helpClickListener);
		advImgStep.setOnClickListener(helpClickListener);
		advImgShake.setOnClickListener(helpClickListener);
		advTxtClock.setOnClickListener(helpClickListener);
		advTxtStep.setOnClickListener(helpClickListener);
		advTxtShake.setOnClickListener(helpClickListener);

        
        View.OnClickListener btnClickListener = null;
        btnClickListener = new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int iNeedCredits = 0;
				String szKey = "";
				Button btnPress = null;
				TextView txtShow = null;
				String szMsg = "";
				switch(v.getId())
				{
				case R.id.advBtnClock:
					iNeedCredits = CLOCK_CREDITS;
					szKey = CrazyCubeCfg.CLOCK_KEY;
					btnPress = advBtnClock;
					txtShow = advClockActive;
					break;
				case R.id.advBtnStep:
					iNeedCredits =  STEP_CREDITS;
					szKey = CrazyCubeCfg.STEP_KEY;
					btnPress = advBtnStep;
					txtShow = advStepActive;
					break;
				case R.id.advBtnShake:
					iNeedCredits =  SHAKE_CREDITS;
					szKey = CrazyCubeCfg.SHAKE_KEY;
					btnPress = advBtnShake;
					txtShow = advShakeActive;
					break;
				case R.id.advBtnMethod1:
					iNeedCredits =  METHOD1_CREDITS;
					szKey = CrazyCubeCfg.METHOD1_KEY;
					btnPress = advBtnMethod1;
					txtShow = advMethod1Active;
					break;
//				case R.id.advBtnStyle:
//					iNeedCredits = STYLE1_CREDITS;
//					szKey = STYLE1_KEY;
//					btnPress = advBtnStyle;
//					txtShow = advStyleActive;
//					break;
				}
				
				int iCurrentPoints = ccadp.GetADPoint(mCon);
				int iPoints = (iNeedCredits + CrazyCubeADPoints.NO_AD_POINT) - iCurrentPoints;
				if (iPoints > 0)
				{
					//积分不够
					szMsg = String.format(mRes.getString(R.string.szCreditsNotEnough), iPoints);
					ShowMsg(szMsg, Toast.LENGTH_LONG, Color.GREEN);
					return;
				}
				
				btnPress.setEnabled(false);
				String szDeviceId = GetDeviceKey();
				
				if (szDeviceId != null)
				{
					if(ccadp.SpendADPoint(iNeedCredits))
					{
						//消费成功
						String szMd5Key = GetMD5Key(szKey, szDeviceId);
						szMd5Key = GetMD5(szMd5Key);
						szMsg = szKey + "\r\n" + szMd5Key; 
						mCubeGL.mCfg.SetAdvList(szKey, szMd5Key);
						btnPress.setText(R.string.szAdvBtnActived);
						
						txtShow.setText(R.string.szAdvActived);
						szMsg = String.format(mRes.getString(R.string.szSpendSuccess), iPoints);
					}
					else
					{
						//消费失败
						btnPress.setEnabled(true);
						szMsg = String.format(mRes.getString(R.string.szSpendFailure), iPoints);
					}
					ShowMsg(szMsg, Toast.LENGTH_LONG, Color.WHITE);
				}
				else
				{
					btnPress.setEnabled(true);
					ShowMsg(mRes.getString(R.string.szCanGetKey), Toast.LENGTH_LONG, Color.GREEN);
					return;
				}
				mCubeGL.mCfg.loadAdvConfig();
			}
		};
		
        advBtnClock.setOnClickListener(btnClickListener);
        advBtnStep.setOnClickListener(btnClickListener);
        advBtnShake.setOnClickListener(btnClickListener);
        
//        advBtnStyle.setOnClickListener(btnClickListener);
        if(advBtnMethod1 != null)
        {
        	advBtnMethod1.setOnClickListener(btnClickListener);
        }


		dialogBox.showDialog(0.7f);
		return dialogBox.dialogResult;
    }
    

    public int ShowExit(String szMsg)
    {
		final DialogModel dialogBox = new DialogModel(mCon);
		dialogBox.setMessage(szMsg);
		dialogBox.setTitle(R.string.app_name);
		dialogBox.setIcon(R.drawable.icon);

        dialogBox.setButton(mCon.getString(R.string.dialogYES), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_YES);
			}
		});

		dialogBox.setButton2(mCon.getString(R.string.dialogNO), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_NO);
			}
		});

		try
		{
			dialogBox.showDialog();
		}
		catch(Exception e)
		{
			System.out.println("DialogBox show error:" + e.getMessage());
		}
		return dialogBox.dialogResult;
    }


    protected int ShowAppOffer(int iOfferNeed)
    {
    	String szMsg = String.format(mRes.getString(R.string.tipAppOffer), iOfferNeed);
		final DialogModel dialogBox = new DialogModel(mCon);
		dialogBox.setMessage(szMsg);
		dialogBox.setTitle(R.string.titleAppOffer);
		dialogBox.setIcon(R.drawable.android);

        dialogBox.setButton(mCon.getString(R.string.dialogIKnow), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_IKNOW);
			}
		});

        dialogBox.setButton2(mCon.getString(R.string.dialogSuggest), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_SUGGEST);
			}
		});
        dialogBox.showDialog();
		return dialogBox.dialogResult;
    }

    public int ShowSuggest()
    {
    	if(!CrazyCubeMain.isShowAdTime())
    	{
    		return DIALOG_IKNOW;
    	}
    	String szMsg = mRes.getString(R.string.tipSuggest);
		final DialogModel dialogBox = new DialogModel(mCon);
		dialogBox.setMessage(szMsg);
		dialogBox.setTitle(R.string.dialogSuggest);
		dialogBox.setIcon(R.drawable.icon);

        dialogBox.setButton(mCon.getString(R.string.dialogIKnow), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_IKNOW);
			}
		});

        dialogBox.showDialog(0.7f);
		return dialogBox.dialogResult;
    }

    
    protected int ShowHelp(String szTitle, String szMsg, int icon) {
		final DialogModel dialogBox = new DialogModel(mCon);
		LayoutInflater factory = LayoutInflater.from(mCon);        
		View dialogView  = factory.inflate(R.layout.helpmsg, null);
		//dialogBox.setTitle(mCon.getString(R.string.titleDailyAward));
		View TitleView = SetCustomTitle(dialogBox, icon, szTitle, 1);
		dialogBox.setCustomTitle(TitleView);        
		//dialogBox.setIcon(R.drawable.goldcoin);
		dialogBox.setView(dialogView);
		dialogBox.setCanceledOnTouchOutside(true);
		dialogBox.setOnCancelListener(new OnCancelListener(){

			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_OK);
				
			}});
		TextView txtHelp = (TextView)dialogView.findViewById(R.id.txtMsg);
		if(txtHelp != null)
		{
			txtHelp.setText(szMsg);
		}
		dialogBox.showDialog();
    	int iRet = dialogBox.dialogResult;		
    	factory = null;
    	dialogView.destroyDrawingCache();
    	TitleView.destroyDrawingCache();
    	dialogBox.dismiss();
    	return iRet;
    }

    
    public int ShowHelp()
    {
    	
    	DialogModel dialogBox = new DialogModel(mCon);
        //dialogBox.setTitle(R.string.menuHelp);
        //dialogBox.setIcon(R.drawable.icon);
        LayoutInflater factory = LayoutInflater.from(mCon);
        final View dialogView;
        if(CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
        {
        	dialogView  = factory.inflate(R.layout.help_method1, null);
        }
        else if(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
        {
        	dialogView  = factory.inflate(R.layout.help_method2, null);
        }
        else
        {
        	dialogView = null;
        	System.out.println("Cube method error");
        }
        
		View TitleView = SetCustomTitle(dialogBox, R.drawable.icon, mCon.getString(R.string.menuHelp), 1);
		dialogBox.setCustomTitle(TitleView);        

        dialogBox.setView(dialogView);

        
        Button btn1 = (Button)dialogView.findViewById(R.id.helpBtn1);
        Button btn2 = (Button)dialogView.findViewById(R.id.helpBtn2);
        Button btn3 = (Button)dialogView.findViewById(R.id.helpBtn3);
        Button btn4 = (Button)dialogView.findViewById(R.id.helpBtn4);
        Button btn5 = (Button)dialogView.findViewById(R.id.helpBtn5);
        Button btn6 = (Button)dialogView.findViewById(R.id.helpBtn6);
        Button btn7 = (Button)dialogView.findViewById(R.id.helpBtn7);
        
        android.view.View.OnClickListener clickListener = new android.view.View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iBtn = v.getId();
				int iTitle = 0;
				int iView = 0;
		        if(CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
		        {
		        	switch(iBtn)
					{
					case R.id.helpBtn1:
	        			iTitle = R.string.method1_step1;
	        			iView = R.layout.layerfirst_step1;
						break;
					case R.id.helpBtn2:
	        			iTitle = R.string.method1_step2;
	        			iView = R.layout.layerfirst_step2;
						break;
					case R.id.helpBtn3:
	        			iTitle = R.string.method1_step3;
	        			iView = R.layout.layerfirst_step3;
						break;
					case R.id.helpBtn4:
	        			iTitle = R.string.method1_step4;
	        			iView = R.layout.layerfirst_step4;
						break;
					case R.id.helpBtn5:
	        			iTitle = R.string.method1_step5;
	        			iView = R.layout.layerfirst_step5;
						break;
					case R.id.helpBtn6:
	        			iTitle = R.string.method1_step6;
	        			iView = R.layout.layerfirst_step6;
						break;
					case R.id.helpBtn7:
	        			iTitle = R.string.method1_step7;
	        			iView = R.layout.layerfirst_step7;
						break;
					default:
						return;
					}
		        }
		        else if(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
		        {
		        	switch(iBtn)
					{
					case R.id.helpBtn1:
	        			iTitle = R.string.method2_step1;
	        			iView = R.layout.bridge_step1;
						break;
					case R.id.helpBtn2:
	        			iTitle = R.string.method2_step2;
	        			iView = R.layout.bridge_step2;
						break;
					case R.id.helpBtn3:
	        			iTitle = R.string.method2_step3;
	        			iView = R.layout.bridge_step3;
						break;
					case R.id.helpBtn4:
	        			iTitle = R.string.method2_step4;
	        			iView = R.layout.bridge_step4;
						break;
					default:
						return;
					}
		        }
				
				int iRet = ShowHelp(iTitle, iView);
				if(CrazyCubeShow.DIALOG_SHARE == iRet)
				{
					mCubeGL.ShowShare(mCubeGL.mCon.getString(iTitle));
				}
			}
		};

		if(null != btn1)
		{
			btn1.setOnClickListener(clickListener);
		}
		if(null != btn2)
		{
	        btn2.setOnClickListener(clickListener);
		}
		if(null != btn3)
		{
			btn3.setOnClickListener(clickListener);
		}
		if(null != btn4)
		{
	        btn4.setOnClickListener(clickListener);
		}
		if(null != btn5)
		{
	        btn5.setOnClickListener(clickListener);
		}
		if(null != btn6)
		{ 
			btn6.setOnClickListener(clickListener);
		}
		if(null != btn7)
		{
	        btn7.setOnClickListener(clickListener);
		}
        
        dialogBox.showDialog(0.85f);
        int iRet = dialogBox.dialogResult;
        dialogView.destroyDrawingCache();
        TitleView.destroyDrawingCache();
        dialogBox.dismiss();
        factory = null;
        return iRet;
    }
    
    protected int ShowHelp(int iTitle, int iView)
	{
		DialogModel dialogBox = new DialogModel(mCon);
        //dialogBox.setTitle(iTitle);
        //dialogBox.setIcon(R.drawable.icon);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(iView, null);
		View TitleView = SetCustomTitle(dialogBox, R.drawable.icon, mCon.getString(iTitle), 1);
		dialogBox.setCustomTitle(TitleView);        
        
        dialogBox.setView(dialogView);
    	dialogBox.showDialog();
        int iRet = dialogBox.dialogResult;
        dialogView.destroyDrawingCache();
        TitleView.destroyDrawingCache();
        dialogBox.dismiss();
        factory = null;
        return iRet;
	}

  
    E_STUDY_STATUS eRtuStudyLevel = E_STUDY_STATUS.STUDY_NONE;
    protected int ShowStudy()
    {
 	
    	eRtuStudyLevel = E_STUDY_STATUS.STUDY_NONE;
    	final DialogModel dialogBox = new DialogModel(mCon);
        dialogBox.setTitle(R.string.study_select);
        dialogBox.setIcon(R.drawable.study);
        LayoutInflater factory = LayoutInflater.from(mCon);
        
        final View dialogView;
        if (CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
        {
        	dialogView  = factory.inflate(R.layout.study_method1, null);
        }
        else if(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
        {
        	dialogView  = factory.inflate(R.layout.study_method2, null);
        }
        else
        {
        	dialogView = null;
        	System.out.println("dialogView is null");
        }
        dialogBox.setView(dialogView);
        dialogBox.setButton(mCon.getString(R.string.dialogOK), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				E_STUDY_STATUS eSelectLevel = E_STUDY_STATUS.STUDY_NONE;
				
				if (CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
				{
					final RadioButton rdBtn1 = (RadioButton)dialogView.findViewById(R.id.rdStep1);
					final RadioButton rdBtn2 = (RadioButton)dialogView.findViewById(R.id.rdStep2);
					final RadioButton rdBtn3 = (RadioButton)dialogView.findViewById(R.id.rdStep3);
					final RadioButton rdBtn4 = (RadioButton)dialogView.findViewById(R.id.rdStep4);
					final RadioButton rdBtn5 = (RadioButton)dialogView.findViewById(R.id.rdStep5);
					final RadioButton rdBtn6 = (RadioButton)dialogView.findViewById(R.id.rdStep6);
					final RadioButton rdBtn7 = (RadioButton)dialogView.findViewById(R.id.rdStep7);
					
					if (rdBtn1.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP1;
					}
					else if (rdBtn2.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP2;
					}
					else if (rdBtn3.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP3;
					}
					else if (rdBtn4.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP4;
					}
					else if (rdBtn5.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP5;
					}
					else if (rdBtn6.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP6;
					}
					else if (rdBtn7.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP7;
					}
					else
					{
						System.out.println("Error Study Level");
					}					
				}
				else if (CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
				{
					final RadioButton rdBtn1 = (RadioButton)dialogView.findViewById(R.id.rdStep1);
					final RadioButton rdBtn2 = (RadioButton)dialogView.findViewById(R.id.rdStep2);
					final RadioButton rdBtn3 = (RadioButton)dialogView.findViewById(R.id.rdStep3);
					final RadioButton rdBtn4 = (RadioButton)dialogView.findViewById(R.id.rdStep4);
					
					if (rdBtn1.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP1;
					}
					else if (rdBtn2.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP2;
					}
					else if (rdBtn3.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP3;
					}
					else if (rdBtn4.isChecked())
					{
						eSelectLevel = E_STUDY_STATUS.STUDY_STEP4;
					}
					else
					{
						System.out.println("Error Study Level");
					}

				}
				if (eSelectLevel != E_STUDY_STATUS.STUDY_NONE)
				{
					eRtuStudyLevel = eSelectLevel;
				}
				dialogBox.endDialog(eRtuStudyLevel.ordinal());
			}
		});
		
		dialogBox.showDialog();
		factory = null;
		dialogView.destroyDrawingCache();
		int iRet = dialogBox.dialogResult;
		dialogBox.dismiss();
		return iRet;
    }
    
    
    public int ShowCongratulation(String szMsg)
    {
		final DialogModel dialogBox = new DialogModel(mCon);
		dialogBox.setMessage(szMsg);
		String szTitle = mRes.getString(R.string.titleCongratulation);
		dialogBox.setTitle(szTitle);
		dialogBox.setIcon(R.drawable.icon);

        dialogBox.setButton(mCon.getString(R.string.dialogIKnow), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_IKNOW);
			}
		});

        dialogBox.setButton2(mCon.getString(R.string.dialogShare), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_SHARE);
			}
		});
        
        dialogBox.showDialog();
		return dialogBox.dialogResult;
    }



	
    protected int ShowStudy(int iStudyLevel, boolean bShow, int iWidth, int iHeight)
	{
		if (0 == iStudyLevel)
		{
			//未选择，直接返回
			return DIALOG_NOTSELECT;
		}
		if(!bShow)
		{
			return DIALOG_STUDY;
		}
		if (!IsSupportVersion())      
		{
			//该SDK版本不支持持功能，直接返回继续执行
			return DIALOG_STUDY;
		}
		final DialogModel dialogBox = new DialogModel(mCon);
		dialogBox.setTitle(R.string.dialogDestination);
		dialogBox.setIcon(R.drawable.icon);

		CrazyCube studyCube = null;
		if (E_METHOD.LAYER_FIRST == CrazyCubeMain.eWhichMethod)
		{
			studyCube = new MethodLayerFirst();
		}
		else if (E_METHOD.BRIDGE == CrazyCubeMain.eWhichMethod)
		{
			studyCube = new MethodBridge();
		}
		else
		{
			System.out.println("Init Cube Method Failure");
		}

		CrazyCubeGL studyGL = new CrazyCubeGL(mCon, studyCube, E_GAMEMODE.STUDY_MODE);
        final GLSurfaceView dialogView  = new CrazyCubeGLView(mCon, studyGL);
        try {
        	switch(iStudyLevel)
        	{
        	case 1:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP1.clone();
        		break;
        	case 2:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP2.clone();
        		break;
        	case 3:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP3.clone();
        		break;
        	case 4:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP4.clone();
        		break;
        	case 5:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP5.clone();
        		break;
        	case 6:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP6.clone();
        		break;
        	case 7:
        		studyCube.DREAM_CUBE_CACULATE = studyCube.DREAM_CUBE_STEP7.clone();
        		break;
        	}
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        dialogBox.setView(dialogView);
		
        dialogBox.setButton(mCon.getString(R.string.dialogStudy), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_STUDY);
			}
		});
        
        dialogBox.setButton2(mCon.getString(R.string.dialogMore), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_MORE);
			}
		});
        
        dialogBox.setButton3(mCon.getString(R.string.dialogShare), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_CAPTURE);
			}
		});
        
        
		dialogBox.showDialog(iWidth, iHeight);
		studyGL.BitmapRecycle();
		studyCube = null;
		studyGL = null;
		int iRet = dialogBox.dialogResult;
		dialogBox.dismiss();
		return iRet;
	}
	
    protected int ShowStudy(String szTitle, int iMsg)
	{
		final DialogModel dialogBox = new DialogModel(mCon);
        dialogBox.setTitle(szTitle);
        dialogBox.setIcon(R.drawable.icon);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(iMsg, null);        
        dialogBox.setView(dialogView);
		dialogBox.setButton(mCon.getString(R.string.dialogStudy), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_STUDY);
			}
		});
        
        dialogBox.setButton2(mCon.getString(R.string.dialogLaststep), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_LASTSTEP);
			}
		});
		dialogBox.showDialog();
		dialogView.destroyDrawingCache();		
		int iRet = dialogBox.dialogResult;
		dialogBox.dismiss();
		return iRet;
	}
    
    protected int ShowCapture(ST_DreamCube cubeStart, final String szFormula, int iWidth, int iHeight)
	{
		final DialogModel dialogBox = new DialogModel(mCon);
        //dialogBox.setTitle(R.string.titleCapture);
        //dialogBox.setIcon(R.drawable.share);
        View TitleView = SetCustomTitle(dialogBox, R.drawable.share, mCon.getString(R.string.titleCapture), 0);
        dialogBox.setCustomTitle(TitleView);
		CrazyCube captureCube = null;
		if (E_METHOD.LAYER_FIRST == CrazyCubeMain.eWhichMethod)
		{
			captureCube = new MethodLayerFirst();
		}
		else if (E_METHOD.BRIDGE == CrazyCubeMain.eWhichMethod)
		{
			captureCube = new MethodBridge();
		}
		else
		{
			System.out.println("Init Cube Method Failure");
		}

		final CrazyCubeGL captureGL = new CrazyCubeGL(mCon, captureCube, E_GAMEMODE.CAPTURE_MODE);
        final GLSurfaceView dialogView  = new CrazyCubeGLView(mCon, captureGL);
        try {
        		captureCube.DREAM_CUBE_CACULATE = cubeStart.clone();			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        dialogBox.setView(dialogView);		        
		
		dialogBox.setButton(mCon.getString(R.string.dialogExport), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try
				{
					captureGL.mbCapture = true;
					int num = szFormula.length();
					if(num>0)
					{
						captureGL.mCcBmp = new CrazyCubeBmp(captureGL, num, captureGL.iScreenWidth, captureGL.iScreenHeight);
						if(captureGL.mCcBmp != null)
						{
							captureGL.mCube.RotationByOptimizeFormula(szFormula);
							captureGL.mCcBmp.Recycle();					
						}
						else
						{
							captureGL.SendMsg(mCon.getString(R.string.errCanvas));
						}
					}
					else
					{
						captureGL.mCcBmp = new CrazyCubeBmp(captureGL, 1, captureGL.iScreenWidth, captureGL.iScreenHeight);
						if(captureGL.mCcBmp != null)
						{
							((CrazyCubeGLView)dialogView).Capture();
							captureGL.mCcBmp.Recycle();					
						}
						else
						{
							captureGL.SendMsg(mCon.getString(R.string.errCanvas));
						}					
					}
				}
				catch(Exception e)
				{
					captureGL.SendMsg("Exception:" + e.getMessage());
				}
				captureGL.mbCapture = false;
				captureGL.mCcBmp = null;
				dialogBox.endDialog(DIALOG_CAPTURE);
			}
		});
        
		dialogBox.showDialog(iWidth, iHeight);
		captureGL.BitmapRecycle();
		captureCube = null;
		dialogView.destroyDrawingCache();
		TitleView.destroyDrawingCache();
		int iRet = dialogBox.dialogResult;
		dialogBox.dismiss();
		return iRet;
	}
    
    
    public int ShowSetup()
    {
    	final DialogModel dialogBox = new DialogModel(mCon);        
        //dialogBox.setTitle(R.string.szSetup);
        //dialogBox.setIcon(R.drawable.setup);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        View dialogView  = factory.inflate(R.layout.setup, null); 
		View TitleView = SetCustomTitle(dialogBox, R.drawable.setup, mCon.getString(R.string.szSetup), 1);
		dialogBox.setCustomTitle(TitleView);        
        
        dialogBox.setView(dialogView);
        
        final CheckBox chkShake = (CheckBox)dialogView.findViewById(R.id.chkShake);
        final CheckBox chk180Degree = (CheckBox)dialogView.findViewById(R.id.chk180Degree);
        final CheckBox chkCenterPlate = (CheckBox)dialogView.findViewById(R.id.chkCenterPlate);
        final CheckBox chkShowButton = (CheckBox)dialogView.findViewById(R.id.chkShowButton);
        final CheckBox chkShowUnseen = (CheckBox)dialogView.findViewById(R.id.chkShowUnseen);
        final CheckBox chkShowTime = (CheckBox)dialogView.findViewById(R.id.chkShowTime);
        final CheckBox chkShowStep = (CheckBox)dialogView.findViewById(R.id.chkShowStep);
        final CheckBox chkSoundRotate = (CheckBox)dialogView.findViewById(R.id.chkSoundRotate);
        final CheckBox chkShowStudy = (CheckBox)dialogView.findViewById(R.id.chkShowStudy);
        final CheckBox chkIntelligentTip = (CheckBox)dialogView.findViewById(R.id.chkIntelligentTip);
        final CheckBox chkIntelligentText = (CheckBox)dialogView.findViewById(R.id.chkIntelligentText);
        final CheckBox chkShowStar = (CheckBox)dialogView.findViewById(R.id.chkShowStar);
        final CheckBox chkTipGraph = (CheckBox)dialogView.findViewById(R.id.chkTipGraph);
        final CheckBox chkShowTip = (CheckBox)dialogView.findViewById(R.id.chkShowTip);
        
        final RadioButton rdMethod1 = (RadioButton)dialogView.findViewById(R.id.rdMethod1);
        final RadioButton rdMethod2 = (RadioButton)dialogView.findViewById(R.id.rdMethod2);

        if(CrazyCubeMain.bSupportMultiMethod)
        {
        	CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, mCubeGL);
     		int iPoints = ccadp.GetADPoint(mCon);

            CrazyCubeDevice ccd = new CrazyCubeDevice(iPoints);
            boolean bMethod1Active = ccd.IsActived(E_DEVICE.BRIDGE);
            if (!bMethod1Active)
            {
            	rdMethod1.setChecked(true);
            	rdMethod2.setChecked(false);
            	rdMethod2.setEnabled(false);
            }
            else
            {
        		rdMethod1.setEnabled(true);
        		rdMethod2.setEnabled(true);
            	if(mCubeGL.mCfg.cubeConfig.iCubeMethod == 1)
            	{
            		rdMethod1.setChecked(true);
            		rdMethod2.setChecked(false);
            	}
            	else if (mCubeGL.mCfg.cubeConfig.iCubeMethod == 2)
            	{
            		rdMethod1.setChecked(false);
            		rdMethod2.setChecked(true);        		
            	}
            	else
            	{}
            }        	
        }
        else
        {
        	rdMethod1.setEnabled(false);
    		rdMethod2.setEnabled(false);

    		if(CrazyCubeMain.eWhichMethod == E_METHOD.LAYER_FIRST)
    		{
    			rdMethod1.setChecked(true);
    			rdMethod2.setChecked(false);
    		}
    		else if(CrazyCubeMain.eWhichMethod == E_METHOD.BRIDGE)
    		{
    			rdMethod1.setChecked(false);
    			rdMethod2.setChecked(true);    			
    		}
    		else
    		{}
        }

        OnCheckedChangeListener checkListener = null;
        checkListener = new OnCheckedChangeListener() {
  			
  			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  				// TODO Auto-generated method stub
  				mCubeGL.mCfg.cubeConfig.bShake = chkShake.isChecked();
  				mCubeGL.mCfg.cubeConfig.bSupport180 = chk180Degree.isChecked();
  				mCubeGL.mCfg.cubeConfig.bCenterPlate = chkCenterPlate.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowButtonOnDesktop = chkShowButton.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowUnseenPlate = chkShowUnseen.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowTimer = chkShowTime.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowStep = chkShowStep.isChecked();
  				mCubeGL.mCfg.cubeConfig.bRotateSound = chkSoundRotate.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowStudy = chkShowStudy.isChecked();
  				mCubeGL.mCfg.cubeConfig.bIntelligentTip = chkIntelligentTip.isChecked();
  				mCubeGL.mCfg.cubeConfig.bIntelligentText = chkIntelligentText.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowStar = chkShowStar.isChecked(); 
  				mCubeGL.mCfg.cubeConfig.bTipGraph = chkTipGraph.isChecked();
  				mCubeGL.mCfg.cubeConfig.bShowTip = chkShowTip.isChecked();
  				
  				if(rdMethod1 != null && rdMethod2 != null)
  				{
  	  				if(rdMethod1.isChecked())
  	  				{
  	  					mCubeGL.mCfg.cubeConfig.iCubeMethod = 1;
  	  				}
  	  				else if (rdMethod2.isChecked())
  	  				{
  	  					mCubeGL.mCfg.cubeConfig.iCubeMethod = 2;
  	  				}
  	  				else
  	  				{}  					
  				}
  					
  				
  				ShowSetupHelp(buttonView.getId(), isChecked);
  			}
  		};
  		chkShake.setChecked(mCubeGL.mCfg.cubeConfig.bShake);
  		chk180Degree.setChecked(mCubeGL.mCfg.cubeConfig.bSupport180);
  		chkCenterPlate.setChecked(mCubeGL.mCfg.cubeConfig.bCenterPlate);
  		chkShowButton.setChecked(mCubeGL.mCfg.cubeConfig.bShowButtonOnDesktop);
  		chkShowUnseen.setChecked(mCubeGL.mCfg.cubeConfig.bShowUnseenPlate);
  		chkShowTime.setChecked(mCubeGL.mCfg.cubeConfig.bShowTimer);
  		chkShowStep.setChecked(mCubeGL.mCfg.cubeConfig.bShowStep);
  		chkSoundRotate.setChecked(mCubeGL.mCfg.cubeConfig.bRotateSound);
  		chkShowStudy.setChecked(mCubeGL.mCfg.cubeConfig.bShowStudy);
  		chkIntelligentTip.setChecked(mCubeGL.mCfg.cubeConfig.bIntelligentTip);
  		chkIntelligentText.setChecked(mCubeGL.mCfg.cubeConfig.bIntelligentText);
  		chkShowStar.setChecked(mCubeGL.mCfg.cubeConfig.bShowStar);
  		chkTipGraph.setChecked(mCubeGL.mCfg.cubeConfig.bTipGraph);
  		chkShowTip.setChecked(mCubeGL.mCfg.cubeConfig.bShowTip);

  		chkShake.setOnCheckedChangeListener(checkListener);
  		chk180Degree.setOnCheckedChangeListener(checkListener);
  		chkCenterPlate.setOnCheckedChangeListener(checkListener);
  		chkShowButton.setOnCheckedChangeListener(checkListener);
  		chkShowUnseen.setOnCheckedChangeListener(checkListener);
  		chkShowTime.setOnCheckedChangeListener(checkListener);
  		chkShowStep.setOnCheckedChangeListener(checkListener);
  		chkSoundRotate.setOnCheckedChangeListener(checkListener);
  		chkShowStudy.setOnCheckedChangeListener(checkListener);
  		chkIntelligentTip.setOnCheckedChangeListener(checkListener);
  		chkIntelligentText.setOnCheckedChangeListener(checkListener);
  		chkShowStar.setOnCheckedChangeListener(checkListener);
  		chkTipGraph.setOnCheckedChangeListener(checkListener);
  		chkShowTip.setOnCheckedChangeListener(checkListener);
  		
        if(rdMethod1 != null)
        {
        	rdMethod1.setOnCheckedChangeListener(checkListener);
        }
        if(rdMethod2 != null)
        {
        	rdMethod2.setOnCheckedChangeListener(checkListener);
        }
        
        
        dialogBox.showDialog(0.75f);
        mCubeGL.mCfg.saveConfig();
        mCubeGL.mCfg.loadConfig();      
        int iRet = dialogBox.dialogResult;
        dialogView.destroyDrawingCache();
        TitleView.destroyDrawingCache();
        factory = null;
        dialogBox.dismiss();
        return iRet;
    }
    
    private void ShowSetupHelp(int iChkId, boolean isChecked)
    {

    	switch(iChkId)
    	{
    	case R.id.chkShake:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpGravity);
    		}    		
    		break;
    	case R.id.chk180Degree:
    		if(isChecked)
    		{
    			ShowMsg(R.string.help180Degree);
    		}    		
    		break;
    	case R.id.chkCenterPlate:
    		if(!isChecked)
    		{
    			ShowMsg(R.string.helpCenterPlate);
    		}
    		break;
    	case R.id.chkShowButton:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpShowButton);
    		}
    		break;
    	case R.id.chkShowUnseen:
    		if(!isChecked)
    		{
    			ShowMsg(R.string.helpShowUnseen);
    		}
    		break;
//    	case  R.id.chkShowTime:
//    		break;
    	case R.id.chkSoundRotate:
    		if(!isChecked)
    		{
    			ShowMsg(R.string.helpSoundRotate);
    		}
    		break;
    	case R.id.chkShowStudy:
    		if(isChecked)
    		{
    			if(Build.VERSION.SDK_INT<=4)
    			{
    				ShowMsg(R.string.helpShowStudyOn);
    			}
    		}
    		else
    		{
    			ShowMsg(R.string.helpShowStudyOff);
    		}
    		break;
    	case R.id.chkIntelligentTip:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpIntelligentTipOn);
    		}
    		else
    		{
    			ShowMsg(R.string.helpIntelligentTipOff);
    		}    		
    		break;
    	case R.id.chkIntelligentText:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpIntelligentTextOn);
    		}
    		else
    		{
    			ShowMsg(R.string.helpIntelligentTextOff);
    		}
    		break;
    	case R.id.chkShowStar:
    		if(!isChecked)
    		{
    			ShowMsg(R.string.helpShowStar);
    		}
    		break;
    	case R.id.chkTipGraph:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpTipGraphOn);
    		}
    		else
    		{
    			ShowMsg(R.string.helpTipGraphOff);
    		}
    		break;
    	case R.id.chkShowTip:
    		if(!isChecked)
    		{
    			ShowMsg(R.string.helpShowTip);
    		}
    		break;
    	case R.id.chkShowTime:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpShowTime);
    		}
    		break;
    	case R.id.chkShowStep:
    		if(isChecked)
    		{
    			ShowMsg(R.string.helpShowStep);
    		}
    		break;
    	case R.id.rdMethod1:
    		ShowMsg(R.string.helpMustRestart);
    		break;
    	case R.id.rdMethod2:
    		ShowMsg(R.string.helpMustRestart);
    		break;
    	default:
    		return;
    	}
    }
    
    private boolean IsSupportVersion()
    {
    	if (Build.VERSION.SDK_INT >= 7)
    	{
    		return true;
    	}
    	else
    	{
    		System.out.println("Current SDK Version can't support this function");	
    		return false;
    	}
    }
	
	int ShowShare(final int num, String szTitle)
	{

    	final DialogModel dialogBox = new DialogModel(mCon);
        dialogBox.setTitle(R.string.titleShareWeibo);
        dialogBox.setIcon(R.drawable.share);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(R.layout.share, null);
		
		final EditText txtMsg = (EditText) dialogView.findViewById(R.id.txtMsg);
		if(txtMsg != null)
		{
			txtMsg.setText(mCubeGL.mRes.getString(R.string.szWeiboTopic) + szTitle);
		}
		
		final CheckBox chkWeibo = (CheckBox)dialogView.findViewById(R.id.chkCareWeibo);
		if(chkWeibo != null)
		{
			if(mCubeGL.mCfg.cubeConfig.bCareWeibo)
			{
				chkWeibo.setChecked(true);
				chkWeibo.setEnabled(false);
			}
			else
			{
				chkWeibo.setChecked(false);
				chkWeibo.setEnabled(true);				
			}
			chkWeibo.setOnCheckedChangeListener(new OnCheckedChangeListener() {	  			
	  			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	  				
	  				if(isChecked)
	  				{
	  					CrazyCubeShare.MakeFriend(mCon, mCubeGL);
	  					chkWeibo.setEnabled(false);
	  				}  				
	  			}
	  		});
		}
		
		
		Bitmap bmp = null;
		ImageView img1 = (ImageView)dialogView.findViewById(R.id.img1);
		if(img1 != null)
		{
			if(num >= 1)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube1.png");
				img1.setImageBitmap(bmp);
			}
			else
			{
				img1.setVisibility(View.INVISIBLE);
			}
		}
		ImageView img2 = (ImageView)dialogView.findViewById(R.id.img2);
		if(img2 != null)
		{
			if(num >= 2)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube2.png");
				img2.setImageBitmap(bmp);
			}
			else
			{
				img2.setVisibility(View.INVISIBLE);
			}
		}
		ImageView img3 = (ImageView)dialogView.findViewById(R.id.img3);
		if(img3 != null)
		{
			if(num >= 3)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube3.png");
				img3.setImageBitmap(bmp);
			}
			else
			{
				img3.setVisibility(View.INVISIBLE);
			}
		}
		ImageView img4 = (ImageView)dialogView.findViewById(R.id.img4);
		if(img4 != null)
		{
			if(num >= 4)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube4.png");
				img4.setImageBitmap(bmp);
			}
			else
			{
				img4.setVisibility(View.INVISIBLE);
			}
		}
		ImageView img5 = (ImageView)dialogView.findViewById(R.id.img5);
		if(img5 != null)
		{
			if(num >= 5)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube5.png");
				img5.setImageBitmap(bmp);
			}
			else
			{
				img5.setVisibility(View.INVISIBLE);
			}			
		}
		ImageView img6 = (ImageView)dialogView.findViewById(R.id.img6);
		if(img6 != null)
		{
			if(num >= 6)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube6.png");
				img6.setImageBitmap(bmp);
			}
			else
			{
				img6.setVisibility(View.INVISIBLE);
			}			
		}
		ImageView img7 = (ImageView)dialogView.findViewById(R.id.img7);
		if(img7 != null)
		{
			if(num >= 7)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube7.png");
				img7.setImageBitmap(bmp);
			}
			else
			{
				img7.setVisibility(View.INVISIBLE);
			}			
		}
		ImageView img8 = (ImageView)dialogView.findViewById(R.id.img8);
		if(img8 != null)
		{
			if(num >= 8)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube8.png");
				img8.setImageBitmap(bmp);
			}
			else
			{
				img8.setVisibility(View.INVISIBLE);
			}			
		}
		ImageView img9 = (ImageView)dialogView.findViewById(R.id.img9);
		if(img9 != null)
		{
			if(num >= 9)
			{
				bmp = CrazyCubeBmp.ReadBmpFromPic("supercube9.png");
				img9.setImageBitmap(bmp);
			}
			else
			{
				img9.setVisibility(View.INVISIBLE);
			}			
		}

        dialogBox.setView(dialogView);

        dialogBox.setButton(mCon.getString(R.string.dialogShare), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
        		Bundle b = new Bundle();
        		b.putInt("num", 1);
        		b.putInt("total", num);
        		b.putString("message", txtMsg.getText().toString());
        		Message message = new Message();   
        		message.what = CrazyCubeGL.CUBE_WEIBO;
        		message.setData(b);
        		mCubeGL.mHandler.sendMessage(message);	                    	
				dialogBox.endDialog(DIALOG_OK);
			}
		});

		dialogBox.showDialog();
		return dialogBox.dialogResult;
	}
	
	public void ShowFirstAward(int iPoints)
	{
		final DialogModel dialogBox = new DialogModel(mCon);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        final View dialogView  = factory.inflate(R.layout.firstaward, null);
        dialogBox.setTitle(mCon.getString(R.string.titleFirstAward));
        dialogBox.setIcon(R.drawable.goldcoin);
        dialogBox.setView(dialogView);
        dialogBox.setCanceledOnTouchOutside(true);
		dialogBox.setOnCancelListener(new OnCancelListener(){

				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					dialogBox.endDialog(DIALOG_OK);
					
		}});       
        TextView txtFirstAward = (TextView)dialogView.findViewById(R.id.txtFirstAward);
        if(txtFirstAward != null)
        {
        	txtFirstAward.setText(String.format(mCon.getString(R.string.szFirstAward), iPoints));
        }        	
        dialogBox.showDialog();
    	factory = null;
    	dialogView.destroyDrawingCache();
    	dialogBox.dismiss();		
	}
	

	View SetCustomTitle(final DialogModel dialogBox, int iIcon, String szTitle, int iFlag)
	{
		LayoutInflater factory = LayoutInflater.from(mCon);
		View TitleView = null;
		if(0 == iFlag)
		{
			TitleView = factory.inflate(R.layout.customtitle_nobutton, null);
		}
		else
		{
			TitleView = factory.inflate(R.layout.customtitle, null);
	        Button btnExit = (Button)TitleView.findViewById(R.id.btnExit);
	        Button btnShare = (Button)TitleView.findViewById(R.id.btnShare);
	        if(btnExit != null)
	        {
	        	btnExit.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						// TODO Auto-generated method stub					
						dialogBox.endDialog(DIALOG_OK);
					}});
	        }
	        
	        if(btnShare != null)
	        {
	        	btnShare.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						// TODO Auto-generated method stub					
						Bitmap bmp = CrazyCubeBmp.ScreenShot(dialogBox.getWindow().getDecorView());
						CrazyCubeBmp.Save("supercube1.png", bmp);
						
						dialogBox.endDialog(DIALOG_SHARE);
					}});        	
	        }
		}
        
        ImageView imgIcon = (ImageView)TitleView.findViewById(R.id.imgIcon);
        if(imgIcon != null)
        {
        	imgIcon.setImageDrawable(mCon.getResources().getDrawable(iIcon));
        }
        
        TextView txtTitle = (TextView)TitleView.findViewById(R.id.txtTitle);
        if(txtTitle != null)
        {
        	txtTitle.setText(szTitle);
        }
		return TitleView;
	}

	
	public int ShowDailyAward(int iDay, int iPoints)
	{
		final DialogModel dialogBox = new DialogModel(mCon);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        View dialogView  = factory.inflate(R.layout.dailyaward, null);
        //dialogBox.setTitle(mCon.getString(R.string.titleDailyAward));
        View TitleView = SetCustomTitle(dialogBox, R.drawable.goldcoin, mCon.getString(R.string.titleDailyAward), 1);
        dialogBox.setCustomTitle(TitleView);        
        //dialogBox.setIcon(R.drawable.goldcoin);
        dialogBox.setView(dialogView);
        
        int iTxt = 0;
        int iImg = 0;
        int iLayout = 0;
        switch (iDay)
        {
        case 1:
        	iTxt = R.id.day1;
        	iImg = R.id.img1;
        	iLayout = R.id.layout1;
        	break;
        case 2:
        	iTxt = R.id.day2;
        	iImg = R.id.img2;
        	iLayout = R.id.layout2;
        	break;
        case 3:
        	iTxt = R.id.day3;
        	iImg = R.id.img3;
        	iLayout = R.id.layout3;
        	break;
        case 4:
        	iTxt = R.id.day4;
        	iImg = R.id.img4;
        	iLayout = R.id.layout4;
        	break;
        case 5:
        	iTxt = R.id.day5;
        	iImg = R.id.img5;
        	iLayout = R.id.layout5;
        	break;
    	default:
        	iTxt = R.id.day5;
        	iImg = R.id.img5;
        	iLayout = R.id.layout5;
        	break;        		
        }
        TextView txtDay = (TextView)dialogView.findViewById(iTxt);
        TextView txtDayAward = (TextView)dialogView.findViewById(R.id.txtDailyAward);
        ImageView img = (ImageView)dialogView.findViewById(iImg);
        LinearLayout layout = (LinearLayout)dialogView.findViewById(iLayout);
        if(txtDay != null)
        {
        	txtDay.setText(mCon.getString(R.string.szToday));
        }
        if(txtDayAward != null)
        {
        	txtDayAward.setText(String.format(mCon.getString(R.string.szTodayAward), iPoints));
        }
        if(img != null)
        {
        	img.setVisibility(View.VISIBLE);
        }
        if(layout != null)
        {
        	layout.setBackgroundColor(Color.parseColor("#FFE090"));
        }

        dialogBox.setCanceledOnTouchOutside(true);
		dialogBox.setOnCancelListener(new OnCancelListener(){

			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_OK);
				
			}});
		
        dialogBox.showDialog();
        int iRet = dialogBox.dialogResult;		
    	factory = null;
    	dialogView.destroyDrawingCache();
    	TitleView.destroyDrawingCache();
    	dialogBox.dismiss();
    	return iRet;
	}
	
	void ShowTutorial()
	{
		AlertDialog dialogBox = new CrazyCubeFlipper(mCon);
        dialogBox.setTitle(mCon.getString(R.string.smenuTutorial));
        dialogBox.setIcon(R.drawable.icon_48);
		dialogBox.show();
	}
	
	int ShowExport()
	{
		final String[] szFormula = new String[1];
		szFormula[0] = "";
    	final DialogModel dialogBox = new DialogModel(mCon);        
        LayoutInflater factory = LayoutInflater.from(mCon);        
        View dialogView  = factory.inflate(R.layout.export, null); 
		View TitleView = SetCustomTitle(dialogBox, R.drawable.icon_48, mCon.getString(R.string.smenuExport), 1);
		dialogBox.setCustomTitle(TitleView);        
        
        dialogBox.setView(dialogView);
        
        final EditText txtMsg = (EditText)dialogView.findViewById(R.id.txtMsg);
        
        Button btn1 = (Button)dialogView.findViewById(R.id.btn1);
        Button btn2 = (Button)dialogView.findViewById(R.id.btn2);
        Button btn3 = (Button)dialogView.findViewById(R.id.btn3);
        Button btn4 = (Button)dialogView.findViewById(R.id.btn4);
        Button btn5 = (Button)dialogView.findViewById(R.id.btn5);
        Button btn6 = (Button)dialogView.findViewById(R.id.btn6);
        Button btn7 = (Button)dialogView.findViewById(R.id.btn7);
        Button btn8 = (Button)dialogView.findViewById(R.id.btn8);
        Button btn9 = (Button)dialogView.findViewById(R.id.btn9);
        Button btn10 = (Button)dialogView.findViewById(R.id.btn10);
        Button btn11 = (Button)dialogView.findViewById(R.id.btn11);
        Button btn12 = (Button)dialogView.findViewById(R.id.btn12);
                
        OnClickListener clickListener = null;
        clickListener = new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String szStep = "";
				szFormula[0] += arg0.getTag().toString();
				szStep = szFormula[0];
				txtMsg.setText(szStep);
			}}; 
        
		btn1.setOnClickListener(clickListener);
		btn2.setOnClickListener(clickListener);
		btn3.setOnClickListener(clickListener);
		btn4.setOnClickListener(clickListener);
		btn5.setOnClickListener(clickListener);
		btn6.setOnClickListener(clickListener);
		btn7.setOnClickListener(clickListener);
		btn8.setOnClickListener(clickListener);
		btn9.setOnClickListener(clickListener);
		btn10.setOnClickListener(clickListener);
		btn11.setOnClickListener(clickListener);
		btn12.setOnClickListener(clickListener);
		
		
        dialogBox.setButton(mCon.getString(R.string.smenuExport), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String szDisturpStep = "";
				szDisturpStep = szFormula[0];
        		Bundle b = new Bundle();
        		b.putString("step", szDisturpStep);
				Message message = new Message();
				message.what = CrazyCubeGL.CUBE_DISRUPTSTEP;  
				message.setData(b);
				mCubeGL.mHandler.sendMessage(message);
				dialogBox.endDialog(DIALOG_OK);
			}
		});

		
		dialogBox.showDialog();
        int iRet = dialogBox.dialogResult;
        dialogView.destroyDrawingCache();
        TitleView.destroyDrawingCache();
        factory = null;
        dialogBox.dismiss();
        return iRet;
	}
	
	public int ShowDevice()
	{
		final DialogModel dialogBox = new DialogModel(mCon);
        LayoutInflater factory = LayoutInflater.from(mCon);        
        View dialogView  = factory.inflate(R.layout.device, null);
        View TitleView = SetCustomTitle(dialogBox, R.drawable.device_menu, mCon.getString(R.string.menuDevice), 1);
        dialogBox.setCustomTitle(TitleView);        
        //dialogBox.setTitle(mCon.getString(R.string.menuDevice));
        //dialogBox.setIcon(R.drawable.device_menu);
        dialogBox.setView(dialogView);

		Drawable drawable = mCon.getResources().getDrawable(R.drawable.unlock);  
		drawable.setBounds(0, 0, 48, 48);
        
        CrazyCubeADPoints ccadp = new CrazyCubeADPoints(mCon, mCubeGL);
		int iPoints = ccadp.GetADPoint(mCon);

        CrazyCubeDevice ccd = new CrazyCubeDevice(iPoints);

        TextView txtTotalPoints = (TextView)dialogView.findViewById(R.id.txtTotalPoints);
        if(txtTotalPoints != null)
        {
        	txtTotalPoints.setText(String.format(mCon.getString(R.string.szTotalPoints), iPoints));
        }

        
        TextView txtDevice1 = (TextView)dialogView.findViewById(R.id.device1);
        if(txtDevice1 != null)
        {
        	if(ccd.IsActived(E_DEVICE.FORMULA_AREA))
        	{
        		txtDevice1.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip1 = (TextView)dialogView.findViewById(R.id.tip1);
        if(txtTip1 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.FORMULA_AREA))
        	{
        		txtTip1.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.FORMULA_AREA) - iPoints));
        		txtTip1.setTextColor(Color.RED);
        	}        	
        }        
        
        TextView txtDevice2 = (TextView)dialogView.findViewById(R.id.device2);
        if(txtDevice2 != null)
        {
        	if(ccd.IsActived(E_DEVICE.INTELLIGENT_TIP))
        	{
        		txtDevice2.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip2 = (TextView)dialogView.findViewById(R.id.tip2);
        if(txtTip2 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.INTELLIGENT_TIP))
        	{
        		txtTip2.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.INTELLIGENT_TIP) - iPoints));
        		txtTip2.setTextColor(Color.RED);
        	}        	
        }        
        
        TextView txtDevice3 = (TextView)dialogView.findViewById(R.id.device3);
        if(txtDevice3 != null)
        {
        	if(ccd.IsActived(E_DEVICE.COMPLETE_AREA))
        	{
        		txtDevice3.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip3 = (TextView)dialogView.findViewById(R.id.tip3);
        if(txtTip3 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.COMPLETE_AREA))
        	{
        		txtTip3.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.COMPLETE_AREA) - iPoints));
        		txtTip3.setTextColor(Color.RED);
        	}        	
        }        

        TextView txtDevice4 = (TextView)dialogView.findViewById(R.id.device4);
        if(txtDevice4 != null)
        {
        	if(ccd.IsActived(E_DEVICE.CLOCK_AREA))
        	{
        		txtDevice4.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip4 = (TextView)dialogView.findViewById(R.id.tip4);
        if(txtTip4 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.CLOCK_AREA))
        	{
        		txtTip4.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.CLOCK_AREA) - iPoints));
        		txtTip4.setTextColor(Color.RED);
        	}        	
        }        

        TextView txtDevice5 = (TextView)dialogView.findViewById(R.id.device5);
        if(txtDevice5 != null)
        {
        	if(ccd.IsActived(E_DEVICE.STEP_AREA))
        	{
        		txtDevice5.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip5 = (TextView)dialogView.findViewById(R.id.tip5);
        if(txtTip5 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.STEP_AREA))
        	{
        		txtTip5.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.STEP_AREA) - iPoints));
        		txtTip5.setTextColor(Color.RED);
        	}        	
        }        

        TextView txtDevice6 = (TextView)dialogView.findViewById(R.id.device6);
        if(txtDevice6 != null)
        {
        	if(ccd.IsActived(E_DEVICE.GRAVITY))
        	{
        		txtDevice6.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip6 = (TextView)dialogView.findViewById(R.id.tip6);
        if(txtTip6 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.GRAVITY))
        	{
        		txtTip6.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.GRAVITY) - iPoints));
        		txtTip6.setTextColor(Color.RED);
        	}        	
        }        

        TextView txtDevice7 = (TextView)dialogView.findViewById(R.id.device7);
        if(txtDevice7 != null)
        {
        	if(ccd.IsActived(E_DEVICE.BRIDGE))
        	{
        		txtDevice7.setCompoundDrawables(null, null, drawable, null);
        	}
        }
        TextView txtTip7 = (TextView)dialogView.findViewById(R.id.tip7);
        if(txtTip7 != null)
        {
        	if(!ccd.IsActived(E_DEVICE.BRIDGE))
        	{
        		txtTip7.setText(String.format(mCon.getString(R.string.szLock), ccd.GetTotalPoints(E_DEVICE.BRIDGE) - iPoints));
        		txtTip7.setTextColor(Color.RED);
        	}        	
        }        
        dialogBox.setCanceledOnTouchOutside(true);
		dialogBox.setOnCancelListener(new OnCancelListener(){

			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				dialogBox.endDialog(DIALOG_OK);
				
			}});
		
        dialogBox.showDialog();
        int iRet = dialogBox.dialogResult;		
    	factory = null;
    	dialogView.destroyDrawingCache();
    	TitleView.destroyDrawingCache();
    	dialogBox.dismiss();
		ccadp = null;
		ccd = null;
		return iRet;
	}
    
}

