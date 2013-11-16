package elong.Cube;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_SOUND;
import elong.CrazyCube.R;

public class CrazyCubeSound {
	
	//音效的音量   
	int streamVolume;   
	  
	//定义SoundPool 对象   
	public SoundPool mSoundPool;    
	  
	//定义HASH表   
	public HashMap<Integer, Integer> mSoundPoolMap;
	
	E_GAMEMODE meCubeMode = E_GAMEMODE.PLAY_MODE;       //该模式决定魔方的初始化方法
	Context mContext;
	
	public CrazyCubeSound(Context c, E_GAMEMODE eCubeMode){

		mContext = c;
		System.out.println("CrazyCubeSound()");
		this.meCubeMode = eCubeMode;
		InitSounds();
	}
	private void InitSounds()
	{
		if (meCubeMode != E_GAMEMODE.PLAY_MODE)
		{
			return;
		}
		System.out.println("CrazyCubeGL.InitSounds");
		initSounds();
		loadSfx(R.raw.sound_buttom, E_SOUND.BUTTON.ordinal());
		loadSfx(R.raw.sound_rotate, E_SOUND.ROTATE.ordinal());
		loadSfx(R.raw.sound_tip, E_SOUND.TIP.ordinal());
		loadSfx(R.raw.sound_undo, E_SOUND.UNDO.ordinal());
		loadSfx(R.raw.sound_abnormal, E_SOUND.ABNORMAL.ordinal());
		loadSfx(R.raw.sound_finish, E_SOUND.FINISH.ordinal());
		loadSfx(R.raw.sound_award, E_SOUND.AWARD.ordinal());
	}
	

	private void initSounds() 
	{    
		//初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质   
		mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);    
		  
		//初始化HASH表   
		mSoundPoolMap = new HashMap<Integer, Integer>();    
		      
		//获得声音设备和设备音量   
		AudioManager mgr = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);   
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	}   
	     
//加载音效资源  
	private void loadSfx(int raw, int ID) {   
	   //把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)   
		mSoundPoolMap.put(ID, mSoundPool.load(mContext, raw, ID));    
	}       
	  
	//sound:要播放的音效的ID, loop:循环次数  
	public void playSound(E_SOUND eSound, int uLoop) {
		int ID = eSound.ordinal();
		mSoundPool.play(mSoundPoolMap.get(ID), streamVolume, streamVolume, 1, uLoop, 1f);    
	}


}
