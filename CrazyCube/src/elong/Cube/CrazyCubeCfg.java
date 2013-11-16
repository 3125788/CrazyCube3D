package elong.Cube;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import android.content.Context;

public class CrazyCubeCfg {
	
	Context mCon;
	int SHARE_CNT = 5;		//每日分享奖励次数
	
	public class CubeConfig
	{
		public boolean bShake = true;		//重力感应
		public boolean bSupport180 = true;		//支持一次旋转180°
		public boolean bCenterPlate = true;    //禁止中间面旋转
		public boolean bShowButtonOnDesktop = true;	//支持在桌面显示
		public boolean bShowUnseenPlate = true;          //是否显示不可见面的提示
	    public boolean bShowTimer = true;          //显示计时器
	    public boolean bShowStep = true;          //显示计步器
		public boolean bBackgroundMusic = true;          //背景音乐
		public boolean bRotateSound = true;          //旋转发出声音
		public boolean bShowStar = true;          //显示魔方完成进度
		public boolean bTipGraph = false;            //图形方式显示提示
		public boolean bShowTip = true;			//显示公式提示区
		public boolean bShowStudy = true;			//提示训练内容
		public boolean bIntelligentTip = true;		//智能提示
		public boolean bIntelligentText = true;		//智能提示时显示文本消息
		public boolean bCareWeibo = false;			//关注微博
	    
		public int iZoomFactor = 100;
		public float fZoomFactor = 1.0f;						   //魔方的缩放比例
		
		public int iCubeMethod = 1;						//魔方解法
		public int iRunTimes = 0;                         //运行次数
	}

	public class CubeDaily
	{
		public int iContinueDay = 0;			//连续登陆的天数
		public int iShareCnt = 0;				//分享奖励，每天5次
		public String szAwardDate = "";			//领取奖励的日期
		public Boolean bIs1stUse = true;		//是否是首次使用，是奖励100积分
	}

	class AdvItem
	{
		String name;
		String key;
	}
	
	class AdvList
	{
		AdvItem cube = new AdvItem();
		AdvItem clock = new AdvItem();
		AdvItem step = new AdvItem();
		AdvItem shake = new AdvItem();
		AdvItem style1 = new AdvItem();
		AdvItem method1 = new AdvItem();
	}

	
	
	public CrazyCubeCfg(Context con)
	{
		cubeConfig = new CubeConfig();
		cubeDaily = new CubeDaily();
		mCon = con;
		loadConfig();
		loadDaily();
	}
	
	public CubeConfig cubeConfig = null;
	CubeDaily cubeDaily = null;
    //保存魔方配置
	public void saveConfig() 
	{
		Properties prop = new Properties();
		prop.put("Shake", Boolean.toString(cubeConfig.bShake));
		prop.put("Support180", Boolean.toString(cubeConfig.bSupport180));
		prop.put("CenterPlate", Boolean.toString(cubeConfig.bCenterPlate));
		prop.put("ShowDesktopButtom", Boolean.toString(cubeConfig.bShowButtonOnDesktop));
		prop.put("ShowUnseenPlate", Boolean.toString(cubeConfig.bShowUnseenPlate));
		prop.put("ShowTimer", Boolean.toString(cubeConfig.bShowTimer));
		prop.put("ShowStep", Boolean.toString(cubeConfig.bShowStep));
		prop.put("BackgroundMusic", Boolean.toString(cubeConfig.bBackgroundMusic));
		prop.put("RotateSound", Boolean.toString(cubeConfig.bRotateSound));
		prop.put("ShowStudy", Boolean.toString(cubeConfig.bShowStudy));
		prop.put("IntelligentTip", Boolean.toString(cubeConfig.bIntelligentTip));
		prop.put("IntelligentText", Boolean.toString(cubeConfig.bIntelligentText));
		prop.put("CareWeibo", Boolean.toString(cubeConfig.bCareWeibo));
		prop.put("ShowStar", Boolean.toString(cubeConfig.bShowStar));
		prop.put("ShowTip", Boolean.toString(cubeConfig.bShowTip));
		prop.put("TipGraph", Boolean.toString(cubeConfig.bTipGraph));
		prop.put("ZoomFactor", Integer.toString(cubeConfig.iZoomFactor));
		prop.put("CubeMethod", Integer.toString(cubeConfig.iCubeMethod));
		prop.put("RunTimes", Integer.toString(cubeConfig.iRunTimes));

		FileOutputStream s;
		try {
			s = mCon.openFileOutput("CrazyCube.cfg", 0);
			try {
				prop.store(s, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	//加载魔方配置
	public void loadConfig() 
	{
		Properties prop = new Properties();
		
		FileInputStream s;
		try {
			s = mCon.openFileInput("CrazyCube.cfg");
			try {
				prop.load(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			saveConfig();
			e.printStackTrace();
//			ShowMsg(context, e.getMessage(), Toast.LENGTH_LONG);
			System.out.println("Loadconfig:" + e.getMessage());
			return;
		}
		if (prop.entrySet().isEmpty())
		{
			saveConfig();
			return;
		}
		if (prop.containsKey("Shake"))
		{
			cubeConfig.bShake =  Boolean.parseBoolean(prop.get("Shake").toString());
		}		
		if (prop.containsKey("Support180"))
		{
			cubeConfig.bSupport180 =  Boolean.parseBoolean(prop.get("Support180").toString());
		}		
		if (prop.containsKey("CenterPlate"))
		{
			cubeConfig.bCenterPlate =  Boolean.parseBoolean(prop.get("CenterPlate").toString());
		}		
		if (prop.containsKey("ShowDesktopButtom"))
		{
			cubeConfig.bShowButtonOnDesktop = Boolean.parseBoolean(prop.get("ShowDesktopButtom").toString());
		}
		if (prop.containsKey("ShowUnseenPlate"))
		{
			cubeConfig.bShowUnseenPlate = Boolean.parseBoolean(prop.get("ShowUnseenPlate").toString());
		}
		if (prop.containsKey("ShowTimer"))
		{
			cubeConfig.bShowTimer = Boolean.parseBoolean(prop.get("ShowTimer").toString());
		}
		if (prop.containsKey("ShowStep"))
		{
			cubeConfig.bShowStep = Boolean.parseBoolean(prop.get("ShowStep").toString());
		}
		if (prop.containsKey("BackgroundMusic"))
		{
			cubeConfig.bBackgroundMusic = Boolean.parseBoolean(prop.get("BackgroundMusic").toString());
		}
		if (prop.containsKey("RotateSound"))
		{
			cubeConfig.bRotateSound = Boolean.parseBoolean(prop.get("RotateSound").toString());
		}
		if (prop.containsKey("ShowStudy"))
		{
			cubeConfig.bShowStudy = Boolean.parseBoolean(prop.get("ShowStudy").toString());
		}
		if (prop.containsKey("IntelligentTip"))
		{
			cubeConfig.bIntelligentTip = Boolean.parseBoolean(prop.get("IntelligentTip").toString());
		}
		if (prop.containsKey("IntelligentText"))
		{
			cubeConfig.bIntelligentText = Boolean.parseBoolean(prop.get("IntelligentText").toString());
		}
		if (prop.containsKey("CareWeibo"))
		{
			cubeConfig.bCareWeibo = Boolean.parseBoolean(prop.get("CareWeibo").toString());
		}		
		if (prop.containsKey("ShowStar"))
		{
			cubeConfig.bShowStar = Boolean.parseBoolean(prop.get("ShowStar").toString());
		}
		if (prop.containsKey("ShowTip"))
		{
			cubeConfig.bShowTip = Boolean.parseBoolean(prop.get("ShowTip").toString());
		}
		if (prop.containsKey("TipGraph"))
		{
			cubeConfig.bTipGraph = Boolean.parseBoolean(prop.get("TipGraph").toString());
		}
		if (prop.containsKey("ZoomFactor"))
		{
			cubeConfig.iZoomFactor = Integer.parseInt(prop.get("ZoomFactor").toString());
			cubeConfig.fZoomFactor = (float)cubeConfig.iZoomFactor / 100;
		}
		if (prop.containsKey("CubeMethod"))
		{
			cubeConfig.iCubeMethod = Integer.parseInt(prop.get("CubeMethod").toString());
		}
		if (prop.containsKey("RunTimes"))
		{
			cubeConfig.iRunTimes = Integer.parseInt(prop.get("RunTimes").toString());
		}
		return;
	}
	
	
    //保存每日数据
	void saveDaily() 
	{
		Properties prop = new Properties();
		prop.put("ContinueDay", Integer.toString(cubeDaily.iContinueDay));
		prop.put("ShareCnt", Integer.toString(cubeDaily.iShareCnt));
		prop.put("AwardDate", cubeDaily.szAwardDate);
		prop.put("Is1stUse", Boolean.toString(cubeDaily.bIs1stUse));

		FileOutputStream s;
		try {
			s = mCon.openFileOutput("CrazyDaily.cfg", 0);
			try {
				prop.store(s, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void loadDaily() 
	{
		Properties prop = new Properties();
		
		FileInputStream s;
		try {
			s = mCon.openFileInput("CrazyDaily.cfg");
			try {
				prop.load(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			saveConfig();
			e.printStackTrace();
//			ShowMsg(context, e.getMessage(), Toast.LENGTH_LONG);
			System.out.println("Loadconfig:" + e.getMessage());
			return;
		}
		if (prop.entrySet().isEmpty())
		{
			saveDaily();
			return;
		}
		if (prop.containsKey("ContinueDay"))
		{
			cubeDaily.iContinueDay  =  Integer.parseInt(prop.get("ContinueDay").toString());
		}		
		if (prop.containsKey("ShareCnt"))
		{
			cubeDaily.iShareCnt  =  Integer.parseInt(prop.get("ShareCnt").toString());
		}		
		if (prop.containsKey("AwardDate"))
		{
			cubeDaily.szAwardDate =  prop.get("AwardDate").toString();
		}		
		if (prop.containsKey("Is1stUse"))
		{
			cubeDaily.bIs1stUse  =  Boolean.parseBoolean(prop.get("Is1stUse").toString());
		}		
		return;
	}
	
	public int GetDay()
	{
		loadDaily();
		return cubeDaily.iContinueDay;
	}
	
	public int GetAwardPoints()
	{
		int iPoints = 0;
		loadDaily();
        switch (cubeDaily.iContinueDay)
        {
        case 1:
        	iPoints = 10;
        	break;
        case 2:
        	iPoints = 20;
        	break;
        case 3:
        	iPoints = 30;
        	break;
        case 4:
        	iPoints = 40;
        	break;
        case 5:
        	iPoints = 50;
        	break;
    	default:
        	iPoints = 50;
        	break;        		
        }
        return iPoints;
	}
	
	public Boolean IsFirstUse()
	{
		return cubeDaily.bIs1stUse;
	}
	
	public Boolean IsShareAward()
	{
		if(cubeDaily.iShareCnt < SHARE_CNT)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void IncreaseShareCnt()
	{
		cubeDaily.iShareCnt++;
		saveDaily();
	}
	
	public void SetFirstUse(Boolean bUse)
	{
		cubeDaily.bIs1stUse = bUse;
		saveDaily();
	}
	
	//今天是否是首次使用
	public Boolean IsFirstUsedToday()
	{
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date date = c.getTime();
		String szDate = formatDate.format(date);
		loadDaily();
		if(0 == cubeDaily.szAwardDate.compareTo(""))
		{
			cubeDaily.szAwardDate = szDate;
		}
		if(0 == szDate.compareTo(cubeDaily.szAwardDate))
		{
			cubeDaily.iContinueDay++;
			cubeDaily.iShareCnt = 0;
			c.add(Calendar.DAY_OF_MONTH, 1);
			date = c.getTime();
			cubeDaily.szAwardDate = formatDate.format(date);
			saveDaily();
			return true;
		}
		else
		{
			c.add(Calendar.DAY_OF_MONTH, 1);
			date = c.getTime();
			szDate = formatDate.format(date);
			if(0 != szDate.compareTo(cubeDaily.szAwardDate))
			{
				cubeDaily.iContinueDay = 1;
				cubeDaily.iShareCnt = 0;
				//c.add(Calendar.DAY_OF_MONTH, -1);
				//date = c.getTime();
				cubeDaily.szAwardDate = formatDate.format(date);
				saveDaily();
				return true;
			}
			return false;
		}
	}

	
	public final static String CUBE_KEY = "RUBIK_CUBE";
	public final static String CLOCK_KEY = "RUBIK_CLOCK";
	public final static String STEP_KEY = "STEP_COUNTER";
	public final static String SHAKE_KEY = "GRAVITY_CUBE";
	public final static String STYLE1_KEY = "STYLE1";
	public final static String METHOD1_KEY = "METHOD1_SOLVES";
	
	AdvList advList = new AdvList();
	
	private void InitAdvList()
	{
		advList.cube.name = CUBE_KEY;
		advList.cube.key = "";
		advList.clock.name = CLOCK_KEY;
		advList.clock.key = "";
		advList.step.name = STEP_KEY;
		advList.step.key = "";
		advList.shake.name = SHAKE_KEY;
		advList.shake.key = "";
		advList.style1.name = STYLE1_KEY;
		advList.style1.key = "";
		advList.method1.name = METHOD1_KEY;
		advList.method1.key = "";
		return;
	}
	
	void SetAdvList(String szAdvName, String szKey)
	{
		if(szAdvName.compareTo(CUBE_KEY) == 0)
		{
			advList.cube.key = szKey;
		}
		else if(szAdvName.compareTo(CLOCK_KEY) == 0)
		{
			advList.clock.key = szKey;
		}
		else if(szAdvName.compareTo(STEP_KEY) == 0)
		{
			advList.step.key = szKey;
		}
		else if(szAdvName.compareTo(SHAKE_KEY) == 0)
		{
			advList.shake.key = szKey;
		}
		else if(szAdvName.compareTo(STYLE1_KEY) == 0)
		{
			advList.style1.key = szKey;
		}
		else if(szAdvName.compareTo(METHOD1_KEY) == 0)
		{
			advList.method1.key = szKey;
		}
		else
		{}
		saveAdvConfig();
		return;
	}


    public boolean GetAdvStatus(String szAdvName)
    {
//		String szDeviceId = GetDeviceKey(context);
//		
//		if (szDeviceId == null)
//		{
//			return false;
//		}
//		
//		String szKey = GetMD5Key(szAdvName, szDeviceId);
//		String szMD5 = GetMD5(szKey);
//				
//		if (szAdvName.compareTo(CUBE_KEY) == 0)
//		{
//			szKey = advList.cube.key;
//		}
//		else if (szAdvName.compareTo(CLOCK_KEY) == 0)
//		{
//			szKey = advList.clock.key;
//		}
//		else if (szAdvName.compareTo(STEP_KEY) == 0)
//		{
//			szKey = advList.step.key;
//		}
//		else if (szAdvName.compareTo(SHAKE_KEY) == 0)
//		{
//			szKey = advList.shake.key;
//		}
//		else if (szAdvName.compareTo(STYLE1_KEY) == 0)
//		{
//			szKey = advList.style1.key;
//		}
//		else if (szAdvName.compareTo(METHOD1_KEY) == 0)
//		{
//			szKey = advList.method1.key;
//		}
//		else
//		{
//			return false;
//		}
//		if (szKey.compareTo(szMD5) == 0)
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
    	
    	//不用积分功能了，将全部功能打开
    	return true;
    }

	//加载高级配置
	public void loadAdvConfig() 
	{
		Properties prop = new Properties();
		
		FileInputStream s;
		try {
			s = mCon.openFileInput("Advance.cfg");
			try {
				prop.load(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InitAdvList();
			saveAdvConfig();
			e.printStackTrace();
//			ShowMsg(context, e.getMessage(), Toast.LENGTH_LONG);
			System.out.println("Load adv config:" + e.getMessage());
			return;
		}
		if (prop.entrySet().isEmpty())
		{
			InitAdvList();
			saveAdvConfig();
			return;
		}
		advList.cube.name = CUBE_KEY;
		if (prop.containsKey(CUBE_KEY))
		{
			advList.cube.key = prop.getProperty(CUBE_KEY);
		}	
		else
		{
			advList.cube.key = "";			
		}
		advList.clock.name = CLOCK_KEY;
		if (prop.containsKey(CLOCK_KEY))
		{
			advList.clock.key = prop.getProperty(CLOCK_KEY);
		}		
		else
		{
			advList.clock.key = "";
		}
		advList.step.name = STEP_KEY;
		if (prop.containsKey(STEP_KEY))
		{
			advList.step.key = prop.getProperty(STEP_KEY);
		}
		else
		{
			advList.step.key = "";
		}
		advList.shake.name = SHAKE_KEY;
		if (prop.containsKey(SHAKE_KEY))
		{
			advList.shake.key = prop.getProperty(SHAKE_KEY);
		}
		else
		{
			advList.shake.key = "";
		}
		advList.style1.name = STYLE1_KEY;
		if (prop.containsKey(STYLE1_KEY))
		{
			advList.style1.key = prop.getProperty(STYLE1_KEY);
		}
		else
		{
			advList.style1.key = "";
		}
		advList.method1.name = METHOD1_KEY;
		if (prop.containsKey(METHOD1_KEY))
		{
			advList.method1.key = prop.getProperty(METHOD1_KEY);
		}
		else
		{
			advList.method1.key = "";
		}
		return;
	}

    //保存高级功能配置
	private void saveAdvConfig() 
	{
		Properties prop = new Properties();
		prop.put(advList.cube.name, advList.cube.key);
		prop.put(advList.clock.name, advList.clock.key);
		prop.put(advList.step.name, advList.step.key);
		prop.put(advList.shake.name, advList.shake.key);
		prop.put(advList.style1.name, advList.style1.key);
		prop.put(advList.method1.name, advList.method1.key);

		FileOutputStream s;
		try {
			s = mCon.openFileOutput("Advance.cfg", 0);
			try {
				prop.store(s, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	
}
