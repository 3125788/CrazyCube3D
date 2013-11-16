package elong.Cube;

import com.adsmogo.adview.AdsMogoLayout;
import com.adsmogo.controller.listener.AdsMogoListener;
import com.adsmogo.offers.MogoOffer;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import elong.CrazyCube.CrazyCubeMain;


public class CrazyCubeAD {

	public static final int PUSH_AD_TIME = 300;
	public static final int CHECK_POINT_TIME = 150;
	final static boolean YOUMI_AD = true;
	static AdsMogoLayout vAD;


		public static void ShowAd(Activity activity, AdsMogoListener adListener, View vCube, boolean bShowAD)
		{
			if(!CrazyCubeMain.isShowAdTime())
			{
				bShowAD = false;
			}
			    	
	    	try{
				if (YOUMI_AD)
				{	
				    System.out.println("Create Mogo AD");
					//��ʼ�������ͼ			
					vAD = new AdsMogoLayout(activity,"8eeeb3ecba2e4af6b925ddd8f5da1c33");
				    FrameLayout.LayoutParams params = new 
						FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, 
						FrameLayout.LayoutParams.WRAP_CONTENT);
				    
				    vAD.setAdsMogoListener(adListener);

					if(bShowAD)
					{
						activity.setContentView(vCube);
					     
						//���ù����ֵ�λ��
						params.gravity=Gravity.TOP|Gravity.RIGHT;
						activity.addContentView(vAD, params);
					}
					else
					{
						activity.setContentView(vAD);				
					     
						//���ù����ֵ�λ��
						params.gravity=Gravity.BOTTOM|Gravity.RIGHT;
						activity.addContentView(vCube, params);
					}

				}
	    		
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("Show ad exception:" + e.getMessage());
	    	}

		}
				
		
		public static void InitOfferWall(Activity activity)
		{
			MogoOffer.init(activity,"8eeeb3ecba2e4af6b925ddd8f5da1c33");
			//����˳��չʾģʽ�£�ѡ�����ǽ��ڵĵ�������⣻
			MogoOffer.setOfferListTitle("��ȡ����");
			//����˳��չʾģʽ�£�ѡ�����ǽ��ڵĵ��������ǰ׺��
			MogoOffer.setOfferEntranceMsg("�̳�");
			//�����Ƿ���ʾâ������ǽ������ʾ��
			//���˴�ֻ�ܹ�����â������ǽ�� ������һ����ǽ��Ҫ������ƽ̨��վ���ã�
			MogoOffer.setMogoOfferScoreVisible(false);						
		}


		public static void GetPushAD(Context context)
		{
			if (YOUMI_AD)
			{
				//youmi ad
				//do nothing
//				System.out.println("youmi not support PUSH AD");
			}

		}


		public static void ShowOffers(Context context)
		{
			MogoOffer.showOffer(context);
		}
		
		
		public static void DestroyAD()
		{
			AdsMogoLayout.clear();
			vAD.clearThread();
			//youmi ad
			//do nothing 	
			System.out.println("destroy Mogo AD");
			
		}
		 
		
		public static void InitAD(Activity activity)
		{
					
			if(YOUMI_AD)
			{
//				AdMogoLayout adMogoLayoutCode = new AdMogoLayout((Activity) context,"8eeeb3ecba2e4af6b925ddd8f5da1c33");
//				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//				FrameLayout.LayoutParams.FILL_PARENT,
//				FrameLayout.LayoutParams.WRAP_CONTENT);
//				// ���ù����ֵ�λ��(�����ڵײ�)
//				params.bottomMargin = 0;
//				params.gravity = Gravity.TOP;				
				//youmi ad
//				net.youmi.android.AdManager.init("1f7f26f14f4bf5cd", "7cd51891c6773e6b",  30,  false); 	
//				System.out.println("youmi ad have init");
			}

			
		}
		
}
