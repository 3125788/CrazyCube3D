package elong.Cube;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import elong.Cube.Cube3.CrazyCube.E_GAMEMODE;
import elong.Cube.Cube3.CrazyCube.E_SOUND;
import elong.CrazyCube.R;

public class CrazyCubeSound {
	
	//��Ч������   
	int streamVolume;   
	  
	//����SoundPool ����   
	public SoundPool mSoundPool;    
	  
	//����HASH��   
	public HashMap<Integer, Integer> mSoundPoolMap;
	
	E_GAMEMODE meCubeMode = E_GAMEMODE.PLAY_MODE;       //��ģʽ����ħ���ĳ�ʼ������
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
		//��ʼ��soundPool ����,��һ�������������ж��ٸ�������ͬʱ����,��2����������������,������������������Ʒ��   
		mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);    
		  
		//��ʼ��HASH��   
		mSoundPoolMap = new HashMap<Integer, Integer>();    
		      
		//��������豸���豸����   
		AudioManager mgr = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);   
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	}   
	     
//������Ч��Դ  
	private void loadSfx(int raw, int ID) {   
	   //����Դ�е���Ч���ص�ָ����ID(���ŵ�ʱ��Ͷ�Ӧ�����ID���ž�����)   
		mSoundPoolMap.put(ID, mSoundPool.load(mContext, raw, ID));    
	}       
	  
	//sound:Ҫ���ŵ���Ч��ID, loop:ѭ������  
	public void playSound(E_SOUND eSound, int uLoop) {
		int ID = eSound.ordinal();
		mSoundPool.play(mSoundPoolMap.get(ID), streamVolume, streamVolume, 1, uLoop, 1f);    
	}


}
