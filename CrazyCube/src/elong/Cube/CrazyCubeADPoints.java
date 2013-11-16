package elong.Cube;


import com.adsmogo.offers.MogoOffer;
import com.adsmogo.offers.MogoOfferPointCallBack;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import elong.CrazyCube.CrazyCubeMain;
import elong.Cube.Cube3.CrazyCube.E_SOUND;

public class CrazyCubeADPoints implements MogoOfferPointCallBack{
	
		public static final int NO_AD_POINT = 500;
		
		Handler mHandler = new Handler();
		int iPoints = 0;
		Context mCon;
		CrazyCubeGL mCubeGL = null;
		boolean bOperSucess = false;     //waps积分使用
	
		public CrazyCubeADPoints(Context context, CrazyCubeGL gl)
		{
			mCon = context;
			mCubeGL = gl;
			MogoOffer.addPointCallBack(this);
		}
	    

	    public int GetADPoint(Context context)
	    {	    	
	    	iPoints = MogoOffer.getPoints(context);
        	return iPoints;
	    }
		
		public void CheckCredits(Context context, Resources res, CrazyCubeShow ccs)
		{
	    	if(!CrazyCubeMain.isShowAdTime())
	    	{
	    		return;
	    	}

			int iPoints = GetADPoint(context);
			int iNeedPoint = NO_AD_POINT - iPoints;
			if(iNeedPoint > 0)
			{
				if (CrazyCubeShow.DIALOG_SUGGEST == ccs.ShowAppOffer(iNeedPoint))
				{
					ccs.ShowSuggest();
				}
			}
		}

		public boolean SpendADPoint(int amount)
	    {
	    	if (MogoOffer.spendPoints(mCon, amount))
	    	{
				int iPoints = GetADPoint(mCon);
				if(mCubeGL != null)
				{
					mCubeGL.mCubeDevice.Init(iPoints);
				}
				return true;
	    	}
	    	else
	    	{
	    		return false;
	    	}
	    }

		public void updatePoint(long point) {
			// TODO Auto-generated method stub
			iPoints = (int) point;			
		}
		
		public void AwardADPoints(int amount)
		{
			MogoOffer.addPoints(mCon, amount);
			if(mCubeGL != null)
			{
				mCubeGL.mSound.playSound(E_SOUND.AWARD, 0);
				int iPoints = GetADPoint(mCon);
				mCubeGL.mCubeDevice.Init(iPoints);
			}
		}

}
