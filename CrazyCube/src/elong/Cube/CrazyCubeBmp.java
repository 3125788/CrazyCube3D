package elong.Cube;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

public class CrazyCubeBmp {
	
	static CrazyCubeGL mGL;
	Canvas mCv;
	Bitmap mBmp;
	static int mColNum;
	int mRowNum, mWPixel, mHPixel;
	int miDrawNum = 0;
	int miPicNum = 0;
	static String mSavePath = "/mnt/sdcard/";
	int CntPerLine = 10;
	int miTextHeight = 80;
	int miFontSize = 48;
	
	
	
	//初始化CANVAS
	public CrazyCubeBmp(int colNum, int rowNum, int wPixel, int hPixel)
	{
		Init(colNum, rowNum, wPixel, hPixel);
	}
	
	public CrazyCubeBmp(CrazyCubeGL cubeGL, int Num, int wPixel, int hPixel)
	{
		int column;
		mGL = cubeGL;
		miPicNum = Num;
		if(Num <= CntPerLine)
		{
			column = Num;
		}
		else
		{
			column = CntPerLine;
		}
		int row = 2;
		if(Num <= 10)
		{
			row = 1;
		}
		Init(column, row, wPixel, hPixel);
	}

	void Init(int colNum, int rowNum, int wPixel, int hPixel)
	{
		mColNum = colNum;
		mRowNum = rowNum;
		mWPixel = wPixel;
		mHPixel = hPixel;
		miDrawNum = 0;
		
		if(Build.VERSION.SDK_INT < 8)
		{
			mSavePath = "/sdcard/";
		}

		try
		{
			mBmp = Bitmap.createBitmap(wPixel * colNum,hPixel * rowNum + miTextHeight, Config.RGB_565);//创建一个新的和SRC长度宽度一样的位图
			mCv = new Canvas(mBmp);
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);			
			mCv.drawRect(0, 0, mCv.getWidth(), mCv.getHeight(), paint);
			//mCv.save(Canvas.ALL_SAVE_FLAG);//保存
		}
		catch (Exception e)
		{
			mGL.SendMsg("CrazyCubeBmp Init Exception:" + e.getMessage());
		}
	}

	
	void Recycle()
	{
		mBmp.recycle();
	}
	
	//column = 0~(mColNum-1)
	//row = 0~(mRowNum-1)
	//在CANVAS的指定位置填充BMP
	void Draw(int column, int row, Bitmap bmp)
	{
		if(column < mColNum && row < mRowNum)
		{
			mCv.drawBitmap(bmp, column * mWPixel, row * mHPixel, null);
		}
	}
	
	void Draw(Bitmap bmp)
	{
		int column, row;
		column = miDrawNum % mColNum;
		row = (miDrawNum / mColNum) % mRowNum;
		if(bmp != null)
		{
			Draw(column, row, bmp);
		}
		miDrawNum++;
		
		if((0 == (miDrawNum % (mColNum * mRowNum))) || (miDrawNum >= miPicNum))
		{
			int iFileCnt = (miDrawNum + mColNum * mRowNum - 1) / (mColNum * mRowNum);
			String fileName = "supercube" + Integer.toString(iFileCnt) + ".png";
			Paint paint = new Paint();
			paint.setTextSize(miFontSize);
			paint.setColor(Color.BLUE);
			SimpleDateFormat  sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
			String date = sDateFormat.format(new java.util.Date());
			String str = "";
			if(miPicNum > 1)
			{
				str = "教你玩魔方    腾讯微博@supercube3d CopyRight ©2013 郑敏新设计    致力于魔方机器解法研究     " + date;
				mCv.drawText(str, 30, mCv.getHeight() - 20, paint);
			}
			else
			{
				paint.setColor(Color.WHITE);
				str = "教你玩魔方    腾讯微博@supercube3d";
				paint.setTextSize(16);
				mCv.drawText(str, 20, mCv.getHeight() - 60, paint);
				str = "致力于魔方机器解法研究";
				mCv.drawText(str, 20, mCv.getHeight() - 40, paint);
				str = "CopyRight ©2013 郑敏新设计";
				mCv.drawText(str, 20, mCv.getHeight() - 20, paint);
			}
			
			Save(fileName, mBmp);
			paint.setColor(Color.BLACK);			
			mCv.drawRect(0, 0, mCv.getWidth(), mCv.getHeight(), paint);
		}
	}
	


	//保存CANVAS,输出BMP格式	
	Bitmap GetBmp(GL10 gl)
    {
        int w = mWPixel; 
        int h = mHPixel;
        
        Bitmap bmp=SaveAsBmp(0,0,w,h,gl);
        return bmp;
	}


	//将OPENGL数据以BMP格式保存
	Bitmap SaveAsBmp(int x, int y, int w, int h, GL10 gl)
    {  
         int b[]=new int[w*h];
         int bt[]=new int[w*h];
         IntBuffer ib=IntBuffer.wrap(b);
         ib.position(0);
         gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
         for(int i=0; i<h; i++)
         { 
              for(int j=0; j<w; j++)
              {
                   int pix=b[i*w+j];
                   int pb=(pix>>16)&0xff;
                   int pr=(pix<<16)&0x00ff0000;
                   int pix1=(pix&0xff00ff00) | pr | pb;
                   bt[(h-i-1)*w+j]=pix1;
              }
         }                  
         try
         {
        	 Bitmap sb=Bitmap.createBitmap(bt, w, h, Bitmap.Config.RGB_565);
        	 return sb;
         }
         catch(Exception e)
         {
        	 mGL.SendMsg("Exception:" + e.getMessage());
        	 return null;
         }
         
    }

	//保存位图数据成PNG格式
	public static void Save(String fileName, Bitmap bmp)
    {
		CompressFormat cFormat = CompressFormat.PNG;
		if(null == bmp)
		{
			return;
		}
        try 
        {
        	if(fileName.indexOf(".png") > 0)
        	{
        		cFormat = CompressFormat.PNG;
        	}
        	else if(fileName.indexOf(".jpg") > 0)
        	{
        		cFormat = CompressFormat.JPEG;
        	}
            FileOutputStream fos=new FileOutputStream(mSavePath + fileName); //android123提示大家，2.2或更高的系统sdcard路径为/mnt/sdcard/
            bmp.compress(cFormat, 100, fos); //保存为png格式，质量100%
            try 
            {
                fos.flush();
            } 
            catch (IOException e) 
            {
            	mGL.SendMsg("Exception:" + e.getMessage());
            }
            try 
            {
            	fos.close();
            } 
            catch (IOException e) 
            {
            	mGL.SendMsg("Exception:" + e.getMessage());
            }
            
        } 
        catch (FileNotFoundException e) 
        {
            // TODO Auto-generated catch block
        	mGL.SendMsg("Exception:" + e.getMessage());
        }               
	}

	//保存OPENGL的位图数据成PNG格式
	public void Save(String fileName, GL10 gl)
    {
        int w = mWPixel; 
        int h = mHPixel;
        
        Bitmap bmp=SaveAsBmp(0,0,w,h,gl);
        Save(fileName, bmp);
	}

	//从PIC文件里读取位图数据
	static Bitmap ReadBmpFromPic(String fileName) {
		try
		{
			String path = mSavePath + fileName;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 设置为true只获取图片大小
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			// 返回为空
			BitmapFactory.decodeFile(path, opts);
					
			opts.inJustDecodeBounds = false;
			if(mColNum>= 5)
			{
				opts.inSampleSize = 10;
			}
			else if (1 == mColNum)
			{
				opts.inSampleSize = 2;
			}
			else
			{
				opts.inSampleSize = 4;
			}
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
			return Bitmap.createBitmap(weak.get());
		}
		catch(Exception e)
		{
			mGL.SendMsg("Exception:" + e.getMessage());
			return null;
		}
	}
	
	public  static Bitmap ScreenShot(View view){
		try
		{
	        //View是你需要截图的View    
	        view.setDrawingCacheEnabled(true);  
	        view.buildDrawingCache();  
	        Bitmap b1 = view.getDrawingCache();  
	           
	        int width = view.getWidth();
	        int height = view.getHeight();
	        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);  
	        view.destroyDrawingCache();  
	        return b;
		}
		catch(Exception e)
		{
			return null;
		}
    }	

}
