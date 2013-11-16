package elong.Cube;

import java.util.HashMap;

import android.content.Context;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import elong.CrazyCube.R;

public class CrazyCubeShare {
	
	static String mszAccount = "SuperCube3D";
	static Boolean mbSendingWeibo = false;   	//正在发送微博	
	static int miWeiBoTick = 0;
	static int miWeiBoTotal = 0;
	static int miWeiBoNum = 0;
	static String mszWeiBoMsg = "";
	static int WEIBO_DELAY = 10;

	
	static void Share(final Context con, final CrazyCubeGL cubeGL)
	{
		
    	TencentWeibo.ShareParams sp = new TencentWeibo.ShareParams(); 
    	AbstractWeibo weibo = AbstractWeibo.getWeibo(con, TencentWeibo.NAME); 
		weibo.setWeiboActionListener(new WeiboActionListener(){

			public void onCancel(AbstractWeibo arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onError(AbstractWeibo arg0, int arg1,
					Throwable arg2) {
				// TODO Auto-generated method stub
				cubeGL.SendMsg("分享失败了！" + arg2.getMessage());
			}

			public void onComplete(AbstractWeibo arg0, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				CrazyCubeCfg cubeCfg = new CrazyCubeCfg(con);	//刷新数据
				if(cubeCfg.IsShareAward())
				{
					cubeGL.AwardADPoints(10, cubeGL.mCon.getString(R.string.tipShareSuccess));
					cubeCfg.IncreaseShareCnt();
				}
				else
				{
					cubeGL.SendMsg(cubeGL.mCon.getString(R.string.tipShareSuccess));
				}
				//only support send msg				
			}			
		}); 
		
		String szTitle = "";
		if(miWeiBoTotal > 1)
		{
			szTitle = String.format("[%1$d/%2$d]", miWeiBoNum, miWeiBoTotal);
		}
    	sp.text = szTitle + mszWeiBoMsg; 
    	sp.imagePath = "/mnt/sdcard/supercube" + Integer.toString(miWeiBoNum) + ".png";
    	weibo.share(sp);
    	
    	if(miWeiBoNum < miWeiBoTotal)
    	{
    		mbSendingWeibo = true;
    		miWeiBoTick = 0;
    	}
    	else
    	{
    		mbSendingWeibo = false;
    	}
	    	
	}
	
	public static void Authen(Context con, final CrazyCubeGL cubeGL)
	{
    	AbstractWeibo weibo = AbstractWeibo.getWeibo(con, TencentWeibo.NAME); 
		weibo.setWeiboActionListener(new WeiboActionListener(){

			public void onCancel(AbstractWeibo arg0, int arg1) {
				// TODO Auto-generated method stub
			}

			public void onError(AbstractWeibo arg0, int arg1,
					Throwable arg2) {
				// TODO Auto-generated method stub
			}

			public void onComplete(AbstractWeibo arg0, int arg1,
					HashMap<String, Object> res) {
				// TODO Auto-generated method stub
			}			
		});
		weibo.authorize();
		return;
	}
	
	public static void MakeFriend(Context con, final CrazyCubeGL cubeGL)
	{
    	AbstractWeibo weibo = AbstractWeibo.getWeibo(con, TencentWeibo.NAME); 
		weibo.setWeiboActionListener(new WeiboActionListener(){

			public void onCancel(AbstractWeibo arg0, int arg1) {
				// TODO Auto-generated method stub
			}

			public void onError(AbstractWeibo arg0, int arg1,
					Throwable arg2) {
				// TODO Auto-generated method stub
				if(0 == arg2.getMessage().compareTo("user is in idollist(80103)"))
				{
					cubeGL.AwardADPoints(50, cubeGL.mCon.getString(R.string.tipCareSuccess));
					cubeGL.mCfg.cubeConfig.bCareWeibo = true;
					cubeGL.mCfg.saveConfig();
				}
				else
				{
					cubeGL.SendMsg(arg2.getMessage());
				}
			}

			public void onComplete(AbstractWeibo arg0, int arg1,
					HashMap<String, Object> res) {
				// TODO Auto-generated method stub
				cubeGL.AwardADPoints(50, cubeGL.mCon.getString(R.string.tipCareSuccess));
				cubeGL.mCfg.cubeConfig.bCareWeibo = true;
				cubeGL.mCfg.saveConfig();
			}			
		});
		weibo.followFriend(mszAccount);
		return;		
	}
	
    

}
