package elong.Cube;

public class CrazyCubeDevice {
	
	public enum E_DEVICE
	{
		FORMULA_AREA,		//公式提示器
		INTELLIGENT_TIP,	//智能提示器
		COMPLETE_AREA,		//进度提示器
		CLOCK_AREA,      	//计时器
		STEP_AREA,			//计步器
		GRAVITY,			//重力感应装置
		BRIDGE,				//桥式解法
		SECOND_CLOCK,		//秒表
		MAX_DEVICE
	}
	
	class CubeDevice
	{
		int miNeedPoints;
		int miTotalPoints;
		Boolean mbActived;
		
		public CubeDevice(int iNeedPoints, int iTotalPoints, Boolean bActived)
		{
			miNeedPoints = iNeedPoints;
			miTotalPoints = iTotalPoints;
			mbActived = bActived;
		}
	}
	
	CubeDevice[] mCD = null;
	
	
	public CrazyCubeDevice(int points)
	{
		mCD = new CubeDevice[E_DEVICE.MAX_DEVICE.ordinal()];
		Init(points);
	}
	
	void Init(int points)
	{
		mCD[E_DEVICE.FORMULA_AREA.ordinal()] = new CubeDevice(50, 50, false);
		mCD[E_DEVICE.INTELLIGENT_TIP.ordinal()] = new CubeDevice(50, 100, false);
		mCD[E_DEVICE.COMPLETE_AREA.ordinal()] = new CubeDevice(50, 150, false);
		mCD[E_DEVICE.CLOCK_AREA.ordinal()] = new CubeDevice(50, 200, false);
		mCD[E_DEVICE.STEP_AREA.ordinal()] = new CubeDevice(50, 250, false);
		mCD[E_DEVICE.GRAVITY.ordinal()] = new CubeDevice(100, 350, false);
		mCD[E_DEVICE.BRIDGE.ordinal()] = new CubeDevice(200, 550, false);
		mCD[E_DEVICE.SECOND_CLOCK.ordinal()] = new CubeDevice(300, 850, false);
		for(int i = 0; i<E_DEVICE.MAX_DEVICE.ordinal(); i++)
		{
			if (mCD[i].miTotalPoints <= points)
			{
				mCD[i].mbActived = true;
			}
		}
	}
	
	public Boolean IsActived(E_DEVICE eDevice)
	{
		return mCD[eDevice.ordinal()].mbActived;
	}
	
	public int GetTotalPoints(E_DEVICE eDevice)
	{
		return mCD[eDevice.ordinal()].miTotalPoints;
	}

}
