package elong.Cube.Cube3;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Random;

import elong.Cube.CrazyCubeGL;

public class CrazyCube {
	
	

    /***************************************/
    /*定义结构及枚举*/
	
	//魔方解法
	public enum E_METHOD
	{
		LAYER_FIRST,
		BRIDGE,
	}
	

    /*魔方的六种颜色*/
    public enum E_COLOR
    {
        NOCOLOR,
        RED,
        GREEN, 
        BLUE,
        YELLOW, 
        ORANGE,
        WHITE,
        HIGHTLIGHT,		//突出显示
        UNDESIGNATE       //不指定
    }
    
    /*定义OPENGL的TEXTURE*/
    //请与E_SELECTION一起修改，两个结构请保持一致
    public enum E_TEXTURE
    {
    	NOCOLOR,
        RED,
        GREEN, 
        BLUE,
        YELLOW, 
        ORANGE,
        WHITE,  
        HIGHTLIGHT,
        UNDESIGNATE,/*以上是魔方的配色方案，以下是其他控件的纹理*/
        ZOOMOUT,
        ZOOMOUT_Pressed,
        ZOOMIN,
        ZOOMIN_Pressed,
        DISRUPT,
        DISRUPT_Pressed,
        AUTOPLAY,
        AUTOPLAY_Pressed,
        RESTART,
        RESTART_Pressed,
        PLAY,
        PLAY_Pressed,
        STUDY,
        STUDY_Pressed,
        STAR,
        STAR_Pressed,
        MENU,
        MENU_Pressed,
        SETUP,
        SETUP_Pressed,
        HELP,
        HELP_Pressed,
        ABOUT,
        ABOUT_Pressed,
        ADVANCE,
        ADVANCE_Pressed,
        EXIT,
        EXIT_Pressed,
        ROTATE_U,
        ROTATE_u,
        ROTATE_D,
        ROTATE_d,
        ROTATE_F,
        ROTATE_f,
        ROTATE_B,
        ROTATE_b,
        ROTATE_L,
        ROTATE_l,
        ROTATE_R,
        ROTATE_r,
        ROTATE_Q,
        ROTATE_q,
        ROTATE_W,
        ROTATE_w,
        ROTATE_G,
        ROTATE_g,
        ROTATE_H,
        ROTATE_h,
        ROTATE_O,
        ROTATE_o,
        ROTATE_P,
        ROTATE_p,
        ROTATE_X,
        ROTATE_x,
        ROTATE_Y,
        ROTATE_y,
        ROTATE_Z,
        ROTATE_z,
        ROTATE_A,
        ROTATE_a,
        ROTATE_C,
        ROTATE_c,
        ROTATE_E,
        ROTATE_e,
        ROTATE_I,
        ROTATE_i,
        ROTATE_J,
        ROTATE_j,
        ROTATE_K,
        ROTATE_k,
        TIP_START,
        TIP_SELE,
        BUTTON_SELE,
        NUMBER_0,
        NUMBER_1,
        NUMBER_2,
        NUMBER_3,
        NUMBER_4,
        NUMBER_5,
        NUMBER_6,
        NUMBER_7,
        NUMBER_8,
        NUMBER_9,
        CLOCK1,
        CLOCK2,
        CLOCK3,
        CLOCK4,
        CLOCK5,
        CLOCK6,
        CLOCK7,
        CLOCK8,
        MINUTE,
        DIVIDE,
        STEP,
        ICON,
        MAX_TEXTURE,        
    }
    
	public enum E_SOUND
	{
		BUTTON,
		ROTATE,
		TIP,
		UNDO,
		ABNORMAL,
		FINISH,
		AWARD,
		BACKGROUD,
	}


    
    /*定义了魔方的九个面*/
    public enum E_PLATE
    {
        NOPLATE,
        UP,
        DOWN,
        FRONT,
        BACK,
        LEFT,
        RIGHT,
        X,
        Y,
        Z;
        
        protected static E_PLATE ToPlate(int value)
        {
        	E_PLATE ePlate = E_PLATE.NOPLATE;
        	switch(value){
        	case 0:
        		ePlate = E_PLATE.NOPLATE;
        		break;
        	case 1:
        		ePlate = E_PLATE.UP;
        		break;
        	case 2:
        		ePlate = E_PLATE.DOWN;
        		break;
        	case 3:
        		ePlate = E_PLATE.FRONT;
        		break;
        	case 4:
        		ePlate = E_PLATE.BACK;
        		break;
        	case 5:
        		ePlate = E_PLATE.LEFT;
        		break;
        	case 6:
        		ePlate = E_PLATE.RIGHT;
        		break;
        	case 7:
        		ePlate = E_PLATE.X;
        		break;
        	case 8:
        		ePlate = E_PLATE.Y;
        		break;
        	case 9:
        		ePlate = E_PLATE.Z;
        		break;        		
        	default:
        		ePlate = E_PLATE.NOPLATE;
        		break;
        	}
        	
        	return ePlate;
        }
    }
    


    public enum E_AXIS
    {
        NOAXIS,
        X,
        x,
        Y,
        y,
        Z,
        z
    }

    /*转动方向事件*/
    public enum E_ROTATIONEVENT
    {
        NULL,
        U,
        u,
        D,
        d,
        F,
        f,
        B,
        b,
        L,
        l,
        R,
        r,
        X,
        x,
        Y,
        y,
        Z,
        z,
        DU,
        Du,
        DD,
        Dd,
        DF,
        Df,
        DB,
        Db,
        DL,
        Dl,
        DR,
        Dr,
        Roll_X,
        Roll_x,
        Roll_Y,
        Roll_y,
        Roll_Z,
        Roll_z
    }

    /*游戏模式*/
    public enum E_GAMEMODE
    {
        INIT_MODE,
        PLAY_MODE,
        STUDY_MODE,		//对话框打开的情况
        AUTO_MODE,
        CAPTURE_MODE,
    }

    /*返回值*/
    public enum E_RET
    {
        CUBE_OK,
        CUBE_ERR_NOCOLOR,
        CUBE_ERR_COLOR,
        CUBE_ERR_PLATE,
        CUBE_ERR_OVERAROUND,     //转了一圈没有结果
        CUBE_ERR_NOT_IN_Y1,
        CUBE_ERR_NOT_IN_Y2,
        CUBE_ERR_NOT_IN_Y3,
        CUBE_ERR_UNHANDLE,
        CUBE_ERR_OPERATEBLOCK,
        CUBE_ERR_DIRECTION,
        CUBE_ERR_STATUS,
        CUBE_ERR_FINDFRIEND,
        CUBE_ERR_OPENFILE,
        CUBE_ERR_BLOCKINDEX,
        CUBE_ERR
    }
    
    

    /*魔方旋转翻滚后面的对应关系*/
    public class ST_Rotate_Plate implements Cloneable
    {
        public E_PLATE U;
        public E_PLATE D;
        public E_PLATE F;
        public E_PLATE B;
        public E_PLATE L;
        public E_PLATE R;
		@Override
		public ST_Rotate_Plate clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return (ST_Rotate_Plate) super.clone();
		}
    }

    public class ST_Rotate_Axis implements Cloneable
    { 
        public E_AXIS X;
        public E_AXIS Y;
        public E_AXIS Z;
		@Override
		public ST_Rotate_Axis clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return (ST_Rotate_Axis) super.clone();
		}
    }

    /*定义魔方九个面上的方块信息*/
    public class ST_Plate implements Cloneable
    {
        public int[][] U = new int[3][3];  //顶
        public int[][] D = new int[3][3];  //底
        public int[][] F = new int[3][3];  //前
        public int[][] B = new int[3][3];  //后
        public int[][] L = new int[3][3];  //左
        public int[][] R = new int[3][3];  //右
		@Override
		public ST_Plate clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			ST_Plate o = (ST_Plate) super.clone();
			o.U = U.clone();
			o.D = D.clone();
			o.F = F.clone();
			o.B = B.clone();
			o.L = L.clone();
			o.R = R.clone();
			o.U[0] = U[0].clone();
			o.U[1] = U[1].clone();
			o.U[2] = U[2].clone();
			o.D[0] = D[0].clone();
			o.D[1] = D[1].clone();
			o.D[2] = D[2].clone();
			o.F[0] = F[0].clone();
			o.F[1] = F[1].clone();
			o.F[2] = F[2].clone();
			o.B[0] = B[0].clone();
			o.B[1] = B[1].clone();
			o.B[2] = B[2].clone();
			o.L[0] = L[0].clone();
			o.L[1] = L[1].clone();
			o.L[2] = L[2].clone();
			o.R[0] = R[0].clone();
			o.R[1] = R[1].clone();
			o.R[2] = R[2].clone();
			return o;
		}
    }

    /*魔方每个方块的坐标*/
    public class ST_Position implements Cloneable
    {
		public int x;
        public int y;
        public int z;

        public Boolean Equals(ST_Position o)
        {
            return this.x == o.x && this.y == o.y && this.z == o.z;
        }
        @Override
        public ST_Position clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return (ST_Position) super.clone();
		}
    }
    /*魔方每个方块6个方向的颜色，无颜色时用NOCOLOR表示*/
    public class ST_Color implements Cloneable
    {
        public E_COLOR U;
        public E_COLOR D;
        public E_COLOR F;
        public E_COLOR B;
        public E_COLOR L;
        public E_COLOR R;

        @Override
		public ST_Color clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
        	ST_Color o = (ST_Color)super.clone(); 
			return o;
		}

		public boolean Equals(ST_Color o) {
			// TODO Auto-generated method stub
			return this.U == o.U && this.D == o.D && this.F == o.F 
				&& this.B == o.B && this.L == o.L && this.R == o.R;
		}

    }

    /*魔方每个方块的状态*/
    public class ST_Status implements Cloneable
    {
        public ST_Position Position = new ST_Position();
        public ST_Color Color = new ST_Color();
		@Override
		public ST_Status clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			ST_Status o = (ST_Status) super.clone();
			o.Position = Position.clone();
			o.Color = Color.clone();
			return o;
		}
    }

    public class ST_FormulaChar implements Cloneable
    {
        public char SP;     //单面
        public char DP;     //双面
        public char HP;     //转180°
		@Override
		public ST_FormulaChar clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return (ST_FormulaChar) super.clone();
		}
    }

    public class ST_DreamCube implements Cloneable
    {
        public ST_Status[] cube_status = new ST_Status[27];    //代表当前魔方状态，所以操作都是对此成员进行
        public ST_Plate Plate = new ST_Plate();             //每个面的包含的方块
        
        public ST_DreamCube()
        {
        	int i = 0;
        	for (i = 0; i< 27; i++)
        	{
        		cube_status[i] = new ST_Status();
        	}
        }

		@Override
		public ST_DreamCube clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			ST_DreamCube o = (ST_DreamCube) super.clone();
			o.cube_status = cube_status.clone();
			o.Plate = Plate.clone();
        	int i = 0;
        	for (i = 0; i< 27; i++)
        	{
        		o.cube_status[i] = cube_status[i].clone();
        	}			
			return o;
		}
    }


    /*鼠标方向*/
    public enum E_MOUSE_DIRECTION
    {
        NO_DIRECTION,
        LEFT_RIGHT,
        DOWN_UP,
        RIGHT_LEFT,
        UP_DOWN,
        LEFT_RIGHT_UP,
        RIGHT_LEFT_UP,
        RIGHT_LEFT_DOWN,
        LEFT_RIGHT_DOWN
    }

    /*发生鼠标事件的区域*/
    public enum E_MOUSE_AREA
    {
        NO_AREA,
        MOUSE_UP,
        MOUSE_DOWN,
        MOUSE_FRONT,
        MOUSE_BACK,
        MOUSE_LEFT,
        MOUSE_RIGHT,
        MOUSE_X,
        MOUSE_Y,
        MOUSE_Z,
        MOUSE_LEFTUNSEEN,
        MOUSE_RIGHTUNSEEN,
        MOUSE_DOWNUNSEEN
    }

    /*魔方训练状态*/
    public enum E_STUDY_STATUS
    {
        STUDY_NONE,
        STUDY_STEP1,
        STUDY_STEP2,
        STUDY_STEP3,
        STUDY_STEP4,
        STUDY_STEP5,
        STUDY_STEP6,
        STUDY_STEP7,
        STUDY_FINISH
    }

    /*魔方的完成状态*/
    public enum E_COMPLED_STATUS
    {
        COMPLED_NONE,
        COMPLED_STATUS1,
        COMPLED_STATUS2,
        COMPLED_STATUS3,
        COMPLED_STATUS4,
        COMPLED_STATUS5,
        COMPLED_STATUS6,
        COMPLED_STATUS7
    }

    /*训练状态*/
    public class ST_Train_Status
    {
        public int iStudyLevel;
        public int iStatus;
    }


    public enum E_Message_Type
    { 
        TYPE_NONE,
        TYPE_INFOMATION,
        TYPE_ERROR,
        TYPE_TRAIN_START,
        TYPE_TRAIN_FINISH,
        TYPE_TRAIN_EXIT
    }

    


    /***************************************/
    /*定义变量*/
    Random randObj = new Random();
    
    public int iTipTimerCnt = 0;     //TIP定时器计数器
    public boolean bAutoTip = true;

	public String szStepBar = "000";           //显示步骤计数条
	
    public ST_DreamCube DREAM_CUBE_CACULATE = new ST_DreamCube();               //用于运算的魔方
    /*表示的是实际的空间坐标，坐标轴不随魔方转动而改变。
        魔方的六个面相对于坐标系是固定的，不随魔方转动而改变。
        整个魔方旋转时，每个面上的方块会相应改变*/

    public ST_DreamCube DREAM_CUBE_DISPLAY = new ST_DreamCube();                //用于显示的魔方
    public ST_DreamCube DREAM_CUBE_CHECKSTATUS = null;				            //用于检测魔方状态
    /*保持与MAGIC_CUBE_CACULATE数据一致。魔方整个旋转时数据不修改。
        魔方的某个面旋转时实时同步与MAGIC_CUBE_CACULATE的数据。
        该数据的作用为：给魔方状态检测提供数据。*/

    protected ST_DreamCube DREAM_CUBE_LASTPLAY = null;               //保存魔方的前一状态，用于重复练习
    public ST_DreamCube DREAM_CUBE_INITSTATUS = new ST_DreamCube();            //保存魔方的初始状态（或叫做完成态）

    //以下CUBE用于显示
    public ST_DreamCube DREAM_CUBE_STEP1 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP2 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP3 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP4 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP5 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP6 = new ST_DreamCube();
    public ST_DreamCube DREAM_CUBE_STEP7 = new ST_DreamCube();


    public ST_Train_Status stTrainStatus = new ST_Train_Status();         //指示训练级别


    public E_GAMEMODE eGameMode = E_GAMEMODE.PLAY_MODE;  //游戏模式

    public Boolean isTip = false;                         //提示状态

    public Boolean isAutoPlay = false;                             //true:autoplay   false:manualpaly

  

    public float fYDegree = 0.0f;                 //标明魔方的方向，魔方当前状态与初始状态在Y方向的角度

    public Boolean bEventEnable = true;              //是否触发事件

	public int iCurrentIndex = 0;	      //高亮的当前显示的提示信息索引
	public int iCurrentStepIndex = 0;				//指向公式的当前步骤索引

    public String autoPlayStep = "@";                      //自动完成步骤记录
    public String manualPlayStep = "@";                    //手工操作步骤记录
    public String trainPlayStep = "@";                     //训练步骤
    
    public class TipShow{
    	public char cColorStep = '0';
    	public char cPhysicalStep = '0';
    	public E_COLOR eColor = E_COLOR.NOCOLOR;
    }
    
    public TipShow acTip[] = new TipShow[7];
    
    public int manualPlayIndex;                      //手工操作步骤的指针，用于操作回放


    private Boolean isTrainUndo = false;                //如果优化操作与训练部匹配，将此步骤设为TRUE，表示需要UNDO

    public Boolean[] abAutoPlayStatus = new Boolean[7];       //自动完成状态
    public Boolean[] abManualPlayStatus = new Boolean[7];     //手工完成状态

    private ST_FormulaChar[] astFormulaChar = new ST_FormulaChar[10];


	public E_PLATE GetOperPlate(int iBlock)
	{
		E_PLATE ePlate = E_PLATE.NOPLATE;
		if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.U != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.UP;
		}
		else if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.D != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.DOWN;
		}
		else if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.FRONT;
		}
		else if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.BACK;
		}
		else if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.LEFT;
		}
		else if (DREAM_CUBE_DISPLAY.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
		{
			ePlate = E_PLATE.RIGHT;
		}
		return ePlate;
	}
	

    /*特定颜色iColor出现在哪个面上，获取的是物理面*/
    public E_PLATE GetPlateWithColor(int iBlock, E_COLOR eColor)
    {
        E_PLATE eColorPlate = E_PLATE.NOPLATE;
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == eColor)
        {
            eColorPlate = E_PLATE.UP;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D == eColor)
        {
            eColorPlate = E_PLATE.DOWN;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == eColor)
        {
            eColorPlate = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == eColor)
        {
            eColorPlate = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == eColor)
        {
            eColorPlate = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == eColor)
        {
            eColorPlate = E_PLATE.RIGHT;
        }
        else
        {
            eColorPlate = E_PLATE.NOPLATE;
        }
        return eColorPlate;      //此面是否需要转换成物理面？
    }

    

    /*面与颜色的对应该关系*/
    protected E_PLATE GetColorPlatebyStandarColor(E_COLOR eColor)
    {
        E_PLATE eColorPlate = E_PLATE.NOPLATE;
        switch (eColor)
        {
            case BLUE:
                eColorPlate = E_PLATE.FRONT;
		        break;
            case ORANGE:
                eColorPlate = E_PLATE.UP;
		        break;
            case RED:
                eColorPlate = E_PLATE.DOWN;
		        break;				
            case WHITE:
                eColorPlate = E_PLATE.LEFT;
		        break;				
            case GREEN:
                eColorPlate = E_PLATE.BACK;
		        break;				
            case YELLOW:
                eColorPlate = E_PLATE.RIGHT;
		        break;				
            default:
                eColorPlate = E_PLATE.NOPLATE;
                break;
        }
        return eColorPlate;
    }


    /*对DreamCube方块的坐标进行设置*/
    private void SetCoordinate(ST_DreamCube dreamcube, int iPosition, int iX, int iY, int iZ)
    {
        dreamcube.cube_status[iPosition].Position.x = iX;
        dreamcube.cube_status[iPosition].Position.y = iY;
        dreamcube.cube_status[iPosition].Position.z = iZ;
    }


    /*对Magic_Cube方块的颜色进行设置*/
    protected void SetColor(ST_DreamCube dreamcube, int iIndex, E_COLOR eColorU, E_COLOR eColorD, E_COLOR eColorF, E_COLOR eColorB, E_COLOR eColorL, E_COLOR eColorR)
    {
        dreamcube.cube_status[iIndex].Color.U = eColorU;
        dreamcube.cube_status[iIndex].Color.D = eColorD;
        dreamcube.cube_status[iIndex].Color.F = eColorF;
        dreamcube.cube_status[iIndex].Color.B = eColorB;
        dreamcube.cube_status[iIndex].Color.L = eColorL;
        dreamcube.cube_status[iIndex].Color.R = eColorR;
    }

    /*随机走一步*/
    private E_ROTATIONEVENT GetRandomStep()
    {
//        Random randObj = new Random();

        double dRandom;
        int iDirection;
        dRandom = randObj.nextDouble() * 12;
        iDirection = (int)dRandom;
        E_ROTATIONEVENT eRotate = E_ROTATIONEVENT.NULL;

        switch (iDirection)
        { 
            case 0:
            	eRotate = E_ROTATIONEVENT.U;
                break;
            case 1:
            	eRotate = E_ROTATIONEVENT.u;
                break;
            case 2:
            	eRotate = E_ROTATIONEVENT.D;
                break;
            case 3:
            	eRotate = E_ROTATIONEVENT.d;
                break;
            case 4:
            	eRotate = E_ROTATIONEVENT.F;
                break;
            case 5:
            	eRotate = E_ROTATIONEVENT.f;
                break;
            case 6:
            	eRotate = E_ROTATIONEVENT.B;
                break;
            case 7:
            	eRotate = E_ROTATIONEVENT.b;
                break;
            case 8:
            	eRotate = E_ROTATIONEVENT.L;
                break;
            case 9:
            	eRotate = E_ROTATIONEVENT.l;
                break;
            case 10:
            	eRotate = E_ROTATIONEVENT.R;
                break;
            case 11:
            	eRotate = E_ROTATIONEVENT.r;
                break;
            default:
                break;
        }
        return eRotate;
    }

    /*初始化魔方方块的坐标*/
    protected void InitCoordinate(ST_DreamCube dreamcube)
    {
        SetCoordinate(dreamcube, 0, -1, -1, -1);
        SetCoordinate(dreamcube, 1, 0, -1, -1);
        SetCoordinate(dreamcube, 2, 1, -1, -1);
        SetCoordinate(dreamcube, 3, -1, -1, 0);
        SetCoordinate(dreamcube, 4, 0, -1, 0);
        SetCoordinate(dreamcube, 5, 1, -1, 0);
        SetCoordinate(dreamcube, 6, -1, -1, 1);
        SetCoordinate(dreamcube, 7, 0, -1, 1);
        SetCoordinate(dreamcube, 8, 1, -1, 1);
        SetCoordinate(dreamcube, 9, -1, 0, -1);
        SetCoordinate(dreamcube, 10, 0, 0, -1);
        SetCoordinate(dreamcube, 11, 1, 0, -1);
        SetCoordinate(dreamcube, 12, -1, 0, 0);
        SetCoordinate(dreamcube, 13, 0, 0, 0);
        SetCoordinate(dreamcube, 14, 1, 0, 0);
        SetCoordinate(dreamcube, 15, -1, 0, 1);
        SetCoordinate(dreamcube, 16, 0, 0, 1);
        SetCoordinate(dreamcube, 17, 1, 0, 1);
        SetCoordinate(dreamcube, 18, -1, 1, -1);
        SetCoordinate(dreamcube, 19, 0, 1, -1);
        SetCoordinate(dreamcube, 20, 1, 1, -1);
        SetCoordinate(dreamcube, 21, -1, 1, 0);
        SetCoordinate(dreamcube, 22, 0, 1, 0);
        SetCoordinate(dreamcube, 23, 1, 1, 0);
        SetCoordinate(dreamcube, 24, -1, 1, 1);
        SetCoordinate(dreamcube, 25, 0, 1, 1);
        SetCoordinate(dreamcube, 26, 1, 1, 1);
    }

    /*初始化魔方方块的颜色*/
    protected void InitColor(ST_DreamCube dreamcube)
    {
        SetColor(dreamcube, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 13, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 18, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 19, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 20, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 21, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 22, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 23, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(dreamcube, 24, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 25, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(dreamcube, 26, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
    }


    /*初始化各个面的数据*/
    protected void InitPlate(ST_DreamCube dreamcube)
    {
        dreamcube.Plate.U[0][0] = 18;
        dreamcube.Plate.U[0][1] = 19;
        dreamcube.Plate.U[0][2] = 20;
        dreamcube.Plate.U[1][0] = 21;
        dreamcube.Plate.U[1][1] = 22;
        dreamcube.Plate.U[1][2] = 23;
        dreamcube.Plate.U[2][0] = 24;
        dreamcube.Plate.U[2][1] = 25;
        dreamcube.Plate.U[2][2] = 26;

        dreamcube.Plate.D[0][0] = 6;
        dreamcube.Plate.D[0][1] = 7;
        dreamcube.Plate.D[0][2] = 8;
        dreamcube.Plate.D[1][0] = 3;
        dreamcube.Plate.D[1][1] = 4;
        dreamcube.Plate.D[1][2] = 5;
        dreamcube.Plate.D[2][0] = 0;
        dreamcube.Plate.D[2][1] = 1;
        dreamcube.Plate.D[2][2] = 2;

        dreamcube.Plate.F[0][0] = 24;
        dreamcube.Plate.F[0][1] = 25;
        dreamcube.Plate.F[0][2] = 26;
        dreamcube.Plate.F[1][0] = 15;
        dreamcube.Plate.F[1][1] = 16;
        dreamcube.Plate.F[1][2] = 17;
        dreamcube.Plate.F[2][0] = 6;
        dreamcube.Plate.F[2][1] = 7;
        dreamcube.Plate.F[2][2] = 8;

        dreamcube.Plate.B[0][0] = 20;
        dreamcube.Plate.B[0][1] = 19;
        dreamcube.Plate.B[0][2] = 18;
        dreamcube.Plate.B[1][0] = 11;
        dreamcube.Plate.B[1][1] = 10;
        dreamcube.Plate.B[1][2] = 9;
        dreamcube.Plate.B[2][0] = 2;
        dreamcube.Plate.B[2][1] = 1;
        dreamcube.Plate.B[2][2] = 0;

        dreamcube.Plate.L[0][0] = 18;
        dreamcube.Plate.L[0][1] = 21;
        dreamcube.Plate.L[0][2] = 24;
        dreamcube.Plate.L[1][0] = 9;
        dreamcube.Plate.L[1][1] = 12;
        dreamcube.Plate.L[1][2] = 15;
        dreamcube.Plate.L[2][0] = 0;
        dreamcube.Plate.L[2][1] = 3;
        dreamcube.Plate.L[2][2] = 6;

        dreamcube.Plate.R[0][0] = 26;
        dreamcube.Plate.R[0][1] = 23;
        dreamcube.Plate.R[0][2] = 20;
        dreamcube.Plate.R[1][0] = 17;
        dreamcube.Plate.R[1][1] = 14;
        dreamcube.Plate.R[1][2] = 11;
        dreamcube.Plate.R[2][0] = 8;
        dreamcube.Plate.R[2][1] = 5;
        dreamcube.Plate.R[2][2] = 2;

    }



    /*初始化魔方为随机状态*/
    public void InitDreamCubeRandom(int iStep)
    {
        int i = 0;
        E_ROTATIONEVENT eRotate = E_ROTATIONEVENT.NULL;
        E_ROTATIONEVENT eLastRotate = E_ROTATIONEVENT.NULL;

        InitCubeStatus();

        isAutoPlay = true;         //初始化也是一直自动操作

        for (i = 0; i < iStep; i++)
        {
        	eRotate = GetRandomStep();
        	if (eRotate != E_ROTATIONEVENT.NULL)
        	{
        		char cRotate = eRotate.toString().charAt(0);
        		char cLastRotate = eLastRotate.toString().charAt(0);
        		if (Character.isLowerCase(cLastRotate))
        		{
        			cLastRotate = Character.toUpperCase(cLastRotate);
        		}
        		else
        		{
        			cLastRotate = Character.toLowerCase(cLastRotate);
        		}
        		if (cRotate != cLastRotate)
        		{
        			eLastRotate = eRotate;
        			Rotation(eRotate, DREAM_CUBE_CACULATE, true);
        			CrazyCubeGL.Delay(1);
        		}
        	}
        }

        isAutoPlay = false;

    }

    public void InitDreamCubeByStep(String szStep)
    {
        int i = 0;
        int iStep = szStep.length();
        E_ROTATIONEVENT eRotate = E_ROTATIONEVENT.NULL;
        E_ROTATIONEVENT eLastRotate = E_ROTATIONEVENT.NULL;

        InitCubeStatus();

        isAutoPlay = true;         //初始化也是一直自动操作
        

        for (i = 0; i < iStep; i++)
        {
        	eRotate = GetRotationEvent(szStep.charAt(i));
        	if (eRotate != E_ROTATIONEVENT.NULL)
        	{
        		char cRotate = eRotate.toString().charAt(0);
        		char cLastRotate = eLastRotate.toString().charAt(0);
        		if (Character.isLowerCase(cLastRotate))
        		{
        			cLastRotate = Character.toUpperCase(cLastRotate);
        		}
        		else
        		{
        			cLastRotate = Character.toLowerCase(cLastRotate);
        		}
        		if (cRotate != cLastRotate)
        		{
        			eLastRotate = eRotate;
        			Rotation(eRotate, DREAM_CUBE_CACULATE, true);
        		}
        	}
        }

        isAutoPlay = false;

    }

    
    /*初始化魔方为初始状态*/
    private void InitCubeStatus()
    {
        SetAutoPlayStep("@");
        ManualPlayStep("@");

        ClearAutoPlayStatus();
        InitCoordinate(DREAM_CUBE_CACULATE);
        InitColor(DREAM_CUBE_CACULATE);
        InitPlate(DREAM_CUBE_CACULATE);
        CloneCube();

        try {
			DREAM_CUBE_CHECKSTATUS = DREAM_CUBE_CACULATE.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public void InitCubeStartStatus()
    {
        InitCoordinate(DREAM_CUBE_INITSTATUS);
        InitColor(DREAM_CUBE_INITSTATUS);		
    }

    //初始化公式表达用到的字符
    private void InitFormulaChar()
    {
    	int i = 0;
    	for (i = 0; i < 10; i++)
    	{
    		astFormulaChar[i] = new ST_FormulaChar();
    	}
        astFormulaChar[E_PLATE.NOPLATE.ordinal()].SP = ' ';
        astFormulaChar[E_PLATE.NOPLATE.ordinal()].DP = ' ';
        astFormulaChar[E_PLATE.NOPLATE.ordinal()].HP = ' ';

        astFormulaChar[E_PLATE.UP.ordinal()].SP = 'U';
        astFormulaChar[E_PLATE.DOWN.ordinal()].SP = 'D';
        astFormulaChar[E_PLATE.FRONT.ordinal()].SP = 'F';
        astFormulaChar[E_PLATE.BACK.ordinal()].SP = 'B';
        astFormulaChar[E_PLATE.LEFT.ordinal()].SP = 'L';
        astFormulaChar[E_PLATE.RIGHT.ordinal()].SP = 'R';

        astFormulaChar[E_PLATE.UP.ordinal()].DP = 'Q';
        astFormulaChar[E_PLATE.DOWN.ordinal()].DP = 'W';
        astFormulaChar[E_PLATE.FRONT.ordinal()].DP = 'G';
        astFormulaChar[E_PLATE.BACK.ordinal()].DP = 'H';
        astFormulaChar[E_PLATE.LEFT.ordinal()].DP = 'O';
        astFormulaChar[E_PLATE.RIGHT.ordinal()].DP = 'P';

        astFormulaChar[E_PLATE.UP.ordinal()].HP = 'A';
        astFormulaChar[E_PLATE.DOWN.ordinal()].HP = 'C';
        astFormulaChar[E_PLATE.FRONT.ordinal()].HP = 'E';
        astFormulaChar[E_PLATE.BACK.ordinal()].HP = 'I';
        astFormulaChar[E_PLATE.LEFT.ordinal()].HP = 'J';
        astFormulaChar[E_PLATE.RIGHT.ordinal()].HP = 'K';
        
        astFormulaChar[E_PLATE.X.ordinal()].SP = 'X';
        astFormulaChar[E_PLATE.Y.ordinal()].SP = 'Y';
        astFormulaChar[E_PLATE.Z.ordinal()].SP = 'Z';

        astFormulaChar[E_PLATE.X.ordinal()].DP = ' ';
        astFormulaChar[E_PLATE.Y.ordinal()].DP = ' ';
        astFormulaChar[E_PLATE.Z.ordinal()].DP = ' ';

        astFormulaChar[E_PLATE.X.ordinal()].HP = ' ';
        astFormulaChar[E_PLATE.Y.ordinal()].HP = ' ';
        astFormulaChar[E_PLATE.Z.ordinal()].HP = ' ';
    }


    /*构造函数*/
    public CrazyCube()
    {
        InitCubeStatus();

        InitCubeStartStatus();

        InitFormulaChar();
    }

    
    public void CloneCube()
    {
    	try {
			DREAM_CUBE_LASTPLAY = DREAM_CUBE_CACULATE.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void RestoreCube()
    {
        try {
			DREAM_CUBE_CACULATE = DREAM_CUBE_LASTPLAY.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XChgDreamCube4Check();
    }

   
    private int intelligentTipDelay = 10;      //智能提示延时
    public int colorSet = 0;                  //颜色集
    public Boolean rotateToVisible = true;   //自动旋转使不可见面可见					


    public void SaveDreamCubeData()
    {
    	/*
        CrazyCube.ST_DreamCube dreamcube = DREAM_CUBE_CACULATE;
        String szFile = "";
        SaveFileDialog saveFileDialog1 = new SaveFileDialog();

        saveFileDialog1.Filter = "梦幻魔方数据 (*.dcf)|*.dcf";
        saveFileDialog1.FilterIndex = 1;

        if (saveFileDialog1.ShowDialog() == DialogResult.OK)
        {
            szFile = saveFileDialog1.FileName;
            XmlDocument xmlDoc = new XmlDocument();
            //初始化配置文件
            String szItem = String.Format("<DreamCube Method='{0}' GenerateDate='{1}'>" +
                        "</DreamCube>", DreamCubeInfo.CubeMethod[DreamCubeInfo.MethodIndex], DateTime.Now.ToString());
            xmlDoc.LoadXml(szItem);

            XmlElement elem;

            XChgDreamCube4Display(dreamcube);           //变换后数据保存在MAGIC_CUBE_DISPLAY
            dreamcube = DREAM_CUBE_DISPLAY;

            for (int i = 0; i < 27; i++)
            {
                elem = xmlDoc.CreateElement(String.Format("Block{0}", i));
                elem.SetAttribute("U", Convert.ToInt16(dreamcube.cube_status[i].Color.U).ToString());
                elem.SetAttribute("D", Convert.ToInt16(dreamcube.cube_status[i].Color.D).ToString());
                elem.SetAttribute("F", Convert.ToInt16(dreamcube.cube_status[i].Color.F).ToString());
                elem.SetAttribute("B", Convert.ToInt16(dreamcube.cube_status[i].Color.B).ToString());
                elem.SetAttribute("L", Convert.ToInt16(dreamcube.cube_status[i].Color.L).ToString());
                elem.SetAttribute("R", Convert.ToInt16(dreamcube.cube_status[i].Color.R).ToString());

                xmlDoc.DocumentElement.AppendChild(elem);
            }
            xmlDoc.Save(szFile);
        }
        */
    }

    public void LoadDreamCubeData()
    {
    	/*
        String szFile = "";
        Cube.ST_DreamCube dreamcube = DREAM_CUBE_DISPLAY;
        OpenFileDialog openFileDialog1 = new OpenFileDialog();

        openFileDialog1.Filter = "梦幻魔方数据 (*.dcf)|*.dcf";
        openFileDialog1.FilterIndex = 1;

        if (openFileDialog1.ShowDialog() == DialogResult.OK)
        {
            szFile = openFileDialog1.FileName;

            ClearAutoPlayStep();                           //清除自动步骤
            ClearManualPlayStep();                         //清除手工步骤
            ClearManualPlayStatus();                       //清除手工状态

            XmlDocument xmlDoc = new XmlDocument(); ;
            XmlNodeList nodeList;

            xmlDoc.Load(szFile);
            nodeList = xmlDoc.SelectSingleNode("DreamCube").ChildNodes;

            foreach (XmlNode xn in nodeList)  //遍历所有子节点 
            {
                XmlElement xe = (XmlElement)xn;//将子节点类型转换为XmlElement类型 
                int i = Convert.ToInt16(xe.Name.Substring(5, xe.Name.Length - 5));
                dreamcube.cube_status[i].Color.U = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("U")));
                dreamcube.cube_status[i].Color.D = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("D")));
                dreamcube.cube_status[i].Color.F = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("F")));
                dreamcube.cube_status[i].Color.B = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("B")));
                dreamcube.cube_status[i].Color.L = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("L")));
                dreamcube.cube_status[i].Color.R = (Cube.E_COLOR)(Convert.ToInt16(xe.GetAttribute("R")));
            }


            XChgDreamCube4Caculate(dreamcube);

            CloneDreamCube();

            //RefreshMagicStepStatus();                               //主动刷新魔方状态
            XChgDreamCube4Check();
            //cubeOperation.DisplayDreamCube();
        }
        */
    }


    protected void SetAutoPlayStatus(E_COMPLED_STATUS eLevel)
    {
        int i = E_COMPLED_STATUS.COMPLED_NONE.ordinal();
        if (eLevel.ordinal() <= E_COMPLED_STATUS.COMPLED_STATUS7.ordinal())
        {
            for (i = 0; i < eLevel.ordinal(); i++)
            {
                abAutoPlayStatus[i] = true;
            }
        }
    }

    protected E_COMPLED_STATUS GetHighestStatus()
    {
        int i = 0;
        E_COMPLED_STATUS eStatus = E_COMPLED_STATUS.COMPLED_NONE;
        for (i = 6; i >= 0; i--)
        {
            if (abAutoPlayStatus[i])
            {
            	switch(i+1){
            	case 0:
            		eStatus = E_COMPLED_STATUS.COMPLED_NONE;
            		break;
            	case 1:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS1;
            		break;
            	case 2:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS2;
            		break;
            	case 3:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS3;
            		break;
            	case 4:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS4;
            		break;
            	case 5:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS5;
            		break;
            	case 6:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS6;
            		break;
            	case 7:
            		eStatus = E_COMPLED_STATUS.COMPLED_STATUS7;
            		break;
        		default:
        			break;
            	}
            }
        }
        
        return eStatus;
    }

    public void ClearAutoPlayStatus()
    {
        int i = 0;
        for (i = 0; i < abAutoPlayStatus.length; i++)
        {
            abAutoPlayStatus[i] = false;
        }
    }

    public void GetAutoPlayStatus(boolean[] bStepStatus)
    {
        for (int i = 0; i < 7; i++)
        {
            bStepStatus[i] = abAutoPlayStatus[i];
        }
    }


    public void ClearManualPlayStatus()
    {
        for (int i = 0; i < abManualPlayStatus.length; i++)
        {
            abManualPlayStatus[i] = false;
        }
    }
    
    public void ClearStep()
    {
    	ClearAutoPlayStep();
    	ClearManualPlayStep();
    	UpdateTipData();
    }

    private void ClearManualPlayStep()
    {
        ManualPlayStep("@");
    }

    private void ClearAutoPlayStep()
    {
        autoPlayStep = "@";
        TrainPlayStep("@");
        manualPlayIndex = 0;
    }

    private void SetAutoPlayStep(String szValue)
    {
        autoPlayStep = szValue;
        TrainPlayStep(szValue);
    }



    public void ManualPlayStep(String value)
    {
        manualPlayStep = GetFullOptimizeStep(value, false);
        manualPlayStep = GetFullOptimizeStep(manualPlayStep, false);
        manualPlayStep = GetFullOptimizeStep(manualPlayStep, false);			
		manualPlayStep = GetFullOptimizeStep(manualPlayStep, false);	
        manualPlayStep = GetFullOptimizeStep(manualPlayStep, true);
    }

    public void TrainPlayStep(String value)
    {
        trainPlayStep = GetFullOptimizeStep(value, false);
        trainPlayStep = GetFullOptimizeStep(trainPlayStep, false);
		trainPlayStep = GetFullOptimizeStep(trainPlayStep, false);
		trainPlayStep = GetFullOptimizeStep(trainPlayStep, false);
    	trainPlayStep = GetFullOptimizeStep(trainPlayStep, true);
    }



    //将优化后的步骤在优化，两个90°变成一个180°，优化结果只用来计算步骤长度。
    private String GetRealOptimizeStep(String szStep)
    {
        String szOptimizeBefore;
        String szOptimizeAfter;

        szOptimizeBefore = szStep;
        szOptimizeAfter = szOptimizeBefore;
        szOptimizeAfter = szOptimizeAfter.replace("UU", "U");
        szOptimizeAfter = szOptimizeAfter.replace("DD", "D");
        szOptimizeAfter = szOptimizeAfter.replace("FF", "F");
        szOptimizeAfter = szOptimizeAfter.replace("BB", "B");
        szOptimizeAfter = szOptimizeAfter.replace("LL", "L");
        szOptimizeAfter = szOptimizeAfter.replace("RR", "R");
        szOptimizeAfter = szOptimizeAfter.replace("XX", "X");
        szOptimizeAfter = szOptimizeAfter.replace("YY", "Y");
        szOptimizeAfter = szOptimizeAfter.replace("ZZ", "Z");

        szOptimizeAfter = szOptimizeAfter.replace("QQ", "Q");
        szOptimizeAfter = szOptimizeAfter.replace("WW", "W");
        szOptimizeAfter = szOptimizeAfter.replace("GG", "G");
        szOptimizeAfter = szOptimizeAfter.replace("HH", "H");
        szOptimizeAfter = szOptimizeAfter.replace("OO", "O");
        szOptimizeAfter = szOptimizeAfter.replace("PP", "P");

        szOptimizeAfter = szOptimizeAfter.replace("uu", "u");
        szOptimizeAfter = szOptimizeAfter.replace("dd", "d");
        szOptimizeAfter = szOptimizeAfter.replace("ff", "f");
        szOptimizeAfter = szOptimizeAfter.replace("bb", "b");
        szOptimizeAfter = szOptimizeAfter.replace("ll", "l");
        szOptimizeAfter = szOptimizeAfter.replace("rr", "r");
        szOptimizeAfter = szOptimizeAfter.replace("xx", "x");
        szOptimizeAfter = szOptimizeAfter.replace("yy", "y");
        szOptimizeAfter = szOptimizeAfter.replace("zz", "z");

        szOptimizeAfter = szOptimizeAfter.replace("qq", "q");
        szOptimizeAfter = szOptimizeAfter.replace("ww", "w");
        szOptimizeAfter = szOptimizeAfter.replace("gg", "g");
        szOptimizeAfter = szOptimizeAfter.replace("hh", "h");
        szOptimizeAfter = szOptimizeAfter.replace("oo", "o");
        szOptimizeAfter = szOptimizeAfter.replace("pp", "p");

        return szOptimizeAfter;
    }


    /*获取完全优化后的操作步骤，该优化会使算法失真*/
    public String GetFullOptimizeStep(String szStep, Boolean bFlag)
    {
        String szOptimizeBefore;
        String szOptimizeAfter;

        szOptimizeBefore = szStep;
        szOptimizeAfter = szOptimizeBefore;
        szOptimizeAfter = szOptimizeAfter.replace("UUUU", "");
        szOptimizeAfter = szOptimizeAfter.replace("DDDD", "");
        szOptimizeAfter = szOptimizeAfter.replace("FFFF", "");
        szOptimizeAfter = szOptimizeAfter.replace("BBBB", "");
        szOptimizeAfter = szOptimizeAfter.replace("LLLL", "");
        szOptimizeAfter = szOptimizeAfter.replace("RRRR", "");
        szOptimizeAfter = szOptimizeAfter.replace("XXXX", "");
        szOptimizeAfter = szOptimizeAfter.replace("YYYY", "");
        szOptimizeAfter = szOptimizeAfter.replace("ZZZZ", "");

        szOptimizeAfter = szOptimizeAfter.replace("QQQQ", "");
        szOptimizeAfter = szOptimizeAfter.replace("WWWW", "");
        szOptimizeAfter = szOptimizeAfter.replace("GGGG", "");
        szOptimizeAfter = szOptimizeAfter.replace("HHHH", "");
        szOptimizeAfter = szOptimizeAfter.replace("OOOO", "");
        szOptimizeAfter = szOptimizeAfter.replace("PPPP", "");

        szOptimizeAfter = szOptimizeAfter.replace("uuuu", "");
        szOptimizeAfter = szOptimizeAfter.replace("dddd", "");
        szOptimizeAfter = szOptimizeAfter.replace("ffff", "");
        szOptimizeAfter = szOptimizeAfter.replace("bbbb", "");
        szOptimizeAfter = szOptimizeAfter.replace("llll", "");
        szOptimizeAfter = szOptimizeAfter.replace("rrrr", "");
        szOptimizeAfter = szOptimizeAfter.replace("xxxx", "");
        szOptimizeAfter = szOptimizeAfter.replace("yyyy", "");
        szOptimizeAfter = szOptimizeAfter.replace("zzzz", "");

        szOptimizeAfter = szOptimizeAfter.replace("qqqq", "");
        szOptimizeAfter = szOptimizeAfter.replace("wwww", "");
        szOptimizeAfter = szOptimizeAfter.replace("gggg", "");
        szOptimizeAfter = szOptimizeAfter.replace("hhhh", "");
        szOptimizeAfter = szOptimizeAfter.replace("oooo", "");
        szOptimizeAfter = szOptimizeAfter.replace("pppp", "");

        szOptimizeAfter = szOptimizeAfter.replace("UUU", "u");
        szOptimizeAfter = szOptimizeAfter.replace("DDD", "d");
        szOptimizeAfter = szOptimizeAfter.replace("FFF", "f");
        szOptimizeAfter = szOptimizeAfter.replace("BBB", "b");
        szOptimizeAfter = szOptimizeAfter.replace("LLL", "l");
        szOptimizeAfter = szOptimizeAfter.replace("RRR", "r");
        szOptimizeAfter = szOptimizeAfter.replace("XXX", "x");
        szOptimizeAfter = szOptimizeAfter.replace("YYY", "y");
        szOptimizeAfter = szOptimizeAfter.replace("ZZZ", "z");

        szOptimizeAfter = szOptimizeAfter.replace("QQQ", "q");
        szOptimizeAfter = szOptimizeAfter.replace("WWW", "w");
        szOptimizeAfter = szOptimizeAfter.replace("GGG", "g");
        szOptimizeAfter = szOptimizeAfter.replace("HHH", "h");
        szOptimizeAfter = szOptimizeAfter.replace("OOO", "o");
        szOptimizeAfter = szOptimizeAfter.replace("PPP", "p");

        szOptimizeAfter = szOptimizeAfter.replace("uuu", "U");
        szOptimizeAfter = szOptimizeAfter.replace("ddd", "D");
        szOptimizeAfter = szOptimizeAfter.replace("fff", "F");
        szOptimizeAfter = szOptimizeAfter.replace("bbb", "B");
        szOptimizeAfter = szOptimizeAfter.replace("lll", "L");
        szOptimizeAfter = szOptimizeAfter.replace("rrr", "R");
        szOptimizeAfter = szOptimizeAfter.replace("xxx", "X");
        szOptimizeAfter = szOptimizeAfter.replace("yyy", "Y");
        szOptimizeAfter = szOptimizeAfter.replace("zzz", "Z");

        szOptimizeAfter = szOptimizeAfter.replace("qqq", "Q");
        szOptimizeAfter = szOptimizeAfter.replace("www", "W");
        szOptimizeAfter = szOptimizeAfter.replace("ggg", "G");
        szOptimizeAfter = szOptimizeAfter.replace("hhh", "H");
        szOptimizeAfter = szOptimizeAfter.replace("ooo", "O");
        szOptimizeAfter = szOptimizeAfter.replace("ppp", "P");

        szOptimizeAfter = szOptimizeAfter.replace("Uu", "");
        szOptimizeAfter = szOptimizeAfter.replace("Dd", "");
        szOptimizeAfter = szOptimizeAfter.replace("Ff", "");
        szOptimizeAfter = szOptimizeAfter.replace("Bb", "");
        szOptimizeAfter = szOptimizeAfter.replace("Ll", "");
        szOptimizeAfter = szOptimizeAfter.replace("Rr", "");
        szOptimizeAfter = szOptimizeAfter.replace("Xx", "");
        szOptimizeAfter = szOptimizeAfter.replace("Yy", "");
        szOptimizeAfter = szOptimizeAfter.replace("Zz", "");

        szOptimizeAfter = szOptimizeAfter.replace("Qq", "");
        szOptimizeAfter = szOptimizeAfter.replace("Ww", "");
        szOptimizeAfter = szOptimizeAfter.replace("Gg", "");
        szOptimizeAfter = szOptimizeAfter.replace("Hh", "");
        szOptimizeAfter = szOptimizeAfter.replace("Oo", "");
        szOptimizeAfter = szOptimizeAfter.replace("Pp", "");

        szOptimizeAfter = szOptimizeAfter.replace("uU", "");
        szOptimizeAfter = szOptimizeAfter.replace("dD", "");
        szOptimizeAfter = szOptimizeAfter.replace("fF", "");
        szOptimizeAfter = szOptimizeAfter.replace("bB", "");
        szOptimizeAfter = szOptimizeAfter.replace("lL", "");
        szOptimizeAfter = szOptimizeAfter.replace("rR", "");
        szOptimizeAfter = szOptimizeAfter.replace("xX", "");
        szOptimizeAfter = szOptimizeAfter.replace("yY", "");
        szOptimizeAfter = szOptimizeAfter.replace("zZ", "");

        szOptimizeAfter = szOptimizeAfter.replace("qQ", "");
        szOptimizeAfter = szOptimizeAfter.replace("wW", "");
        szOptimizeAfter = szOptimizeAfter.replace("gG", "");
        szOptimizeAfter = szOptimizeAfter.replace("hH", "");
        szOptimizeAfter = szOptimizeAfter.replace("oO", "");
        szOptimizeAfter = szOptimizeAfter.replace("pP", "");

		if(bFlag)
		{
	        szOptimizeAfter = szOptimizeAfter.replace("UU", "A");
	        szOptimizeAfter = szOptimizeAfter.replace("DD", "C");
	        szOptimizeAfter = szOptimizeAfter.replace("FF", "E");
	        szOptimizeAfter = szOptimizeAfter.replace("BB", "I");
	        szOptimizeAfter = szOptimizeAfter.replace("LL", "J");
	        szOptimizeAfter = szOptimizeAfter.replace("RR", "K");

			szOptimizeAfter = szOptimizeAfter.replace("uu", "a");
			szOptimizeAfter = szOptimizeAfter.replace("dd", "c");
			szOptimizeAfter = szOptimizeAfter.replace("ff", "e");
			szOptimizeAfter = szOptimizeAfter.replace("bb", "i");
			szOptimizeAfter = szOptimizeAfter.replace("ll", "j");
			szOptimizeAfter = szOptimizeAfter.replace("rr", "k");

			szOptimizeAfter = szOptimizeAfter.replace("AU", "u");
			szOptimizeAfter = szOptimizeAfter.replace("CD", "d");
			szOptimizeAfter = szOptimizeAfter.replace("EF", "f");
			szOptimizeAfter = szOptimizeAfter.replace("IB", "b");
			szOptimizeAfter = szOptimizeAfter.replace("JL", "l");
			szOptimizeAfter = szOptimizeAfter.replace("KR", "r");
			
			szOptimizeAfter = szOptimizeAfter.replace("Au", "U");
			szOptimizeAfter = szOptimizeAfter.replace("Cd", "D");
			szOptimizeAfter = szOptimizeAfter.replace("Ef", "F");
			szOptimizeAfter = szOptimizeAfter.replace("Ib", "B");
			szOptimizeAfter = szOptimizeAfter.replace("Jl", "L");
			szOptimizeAfter = szOptimizeAfter.replace("Kr", "R");

			szOptimizeAfter = szOptimizeAfter.replace("aa", "");
			szOptimizeAfter = szOptimizeAfter.replace("cc", "");
			szOptimizeAfter = szOptimizeAfter.replace("ee", "");
			szOptimizeAfter = szOptimizeAfter.replace("ii", "");
			szOptimizeAfter = szOptimizeAfter.replace("jj", "");
			szOptimizeAfter = szOptimizeAfter.replace("kk", "");
			
			szOptimizeAfter = szOptimizeAfter.replace("AA", "");
			szOptimizeAfter = szOptimizeAfter.replace("CC", "");
			szOptimizeAfter = szOptimizeAfter.replace("EE", "");
			szOptimizeAfter = szOptimizeAfter.replace("II", "");
			szOptimizeAfter = szOptimizeAfter.replace("JJ", "");
			szOptimizeAfter = szOptimizeAfter.replace("KK", "");

			szOptimizeAfter = szOptimizeAfter.replace("aU", "u");
			szOptimizeAfter = szOptimizeAfter.replace("cD", "d");
			szOptimizeAfter = szOptimizeAfter.replace("eF", "f");
			szOptimizeAfter = szOptimizeAfter.replace("iB", "b");
			szOptimizeAfter = szOptimizeAfter.replace("jL", "l");
			szOptimizeAfter = szOptimizeAfter.replace("kR", "r");
			
			szOptimizeAfter = szOptimizeAfter.replace("au", "U");
			szOptimizeAfter = szOptimizeAfter.replace("cd", "D");
			szOptimizeAfter = szOptimizeAfter.replace("ef", "F");
			szOptimizeAfter = szOptimizeAfter.replace("ib", "B");
			szOptimizeAfter = szOptimizeAfter.replace("jl", "L");
			szOptimizeAfter = szOptimizeAfter.replace("kr", "R");

			szOptimizeAfter = szOptimizeAfter.replace("Aa", "");
			szOptimizeAfter = szOptimizeAfter.replace("Cc", "");
			szOptimizeAfter = szOptimizeAfter.replace("Ee", "");
			szOptimizeAfter = szOptimizeAfter.replace("Ii", "");
			szOptimizeAfter = szOptimizeAfter.replace("Jj", "");
			szOptimizeAfter = szOptimizeAfter.replace("Kk", "");

			szOptimizeAfter = szOptimizeAfter.replace("aA", "");
			szOptimizeAfter = szOptimizeAfter.replace("cC", "");
			szOptimizeAfter = szOptimizeAfter.replace("eE", "");
			szOptimizeAfter = szOptimizeAfter.replace("iI", "");
			szOptimizeAfter = szOptimizeAfter.replace("jJ", "");
			szOptimizeAfter = szOptimizeAfter.replace("kK", "");
		}

        return szOptimizeAfter;
    }


    public void RecordStep(String szDirection)
    {
        if (isTip)
        {
            return;
        }
        if (isAutoPlay)
        {
            autoPlayStep = autoPlayStep + szDirection;
            TrainPlayStep(trainPlayStep + szDirection);
        }
        else
        {
            if (eGameMode == E_GAMEMODE.PLAY_MODE)
            {
                /*每次都从ManualPlayIndex开始记录，当回放操作至中间某个INDEX时，
                 * 如果用户此时直接操作魔方，会从当前记录位置开始记录*/
                ManualPlayStep(manualPlayStep + szDirection);
             
            }        	
        }
        UpdateTipData();
    }
    
    //初始化TIP,最大显示7个TIP（目前的selection操作也只能识别7个物体）
    public void InitTip()
    {
    	for(int i = 0; i < 7; i++)
    	{
    		acTip[i] = new TipShow();
    		acTip[i].cColorStep = '0';
    		acTip[i].cPhysicalStep = '0';
    		acTip[i].eColor = E_COLOR.NOCOLOR;
    	}
    }
    
    private void ResetTipCurrentIndex()
    {
    	int iStepLen = 0;
    	if (eGameMode == E_GAMEMODE.STUDY_MODE 
    			|| eGameMode == E_GAMEMODE.AUTO_MODE)
    	{
    		if(iCurrentStepIndex > 3)
    		{
    			iStepLen = trainPlayStep.length() - 1;
    			if((iStepLen - iCurrentStepIndex) >= 3)
    			{
    				iCurrentIndex = 3;
    			}
    			else
    			{
    				iCurrentIndex = 6 - (iStepLen - iCurrentStepIndex);
    			}
    		}
    		else
    		{
    			iCurrentIndex = iCurrentStepIndex;
    		}
    	}
    	else if(eGameMode == E_GAMEMODE.PLAY_MODE)
    	{
    		iCurrentIndex = 6;
    		iStepLen = manualPlayStep.length() - 1;
    		if (iStepLen < iCurrentIndex)
    		{
    			iCurrentIndex = iStepLen;
    		} 
    	}
    	else
    	{
    	}
		return;
    }

    
    //更新TIP的STEP数据，用于刷新TIP
    public void UpdateTipData()
    {
    	int iStepLen = 0;
    	
    	if(isAutoPlay)
    	{
    		return;
    	}
    	ResetTipCurrentIndex();
        if (eGameMode == E_GAMEMODE.PLAY_MODE)
        {
            for(int i = 1; i <=7; i++)
            {
            	iStepLen = manualPlayStep.length();
            	if(i > iStepLen)
            	{
            		acTip[i - 1].cColorStep = '0';
            	}
            	else
            	{
            		if (iStepLen <= 7)
            		{
            			acTip[i - 1].cColorStep = manualPlayStep.charAt(i - 1);
            		}
            		else
            		{
            			acTip[i - 1].cColorStep = manualPlayStep.charAt(iStepLen - (8 - i));
            		}
            	}
            }
            String szStep = GetFullOptimizeStep(manualPlayStep, false);
			szStep = GetFullOptimizeStep(szStep, false);
			szStep = GetFullOptimizeStep(szStep, false);
			szStep = GetFullOptimizeStep(szStep, false);
			szStep = GetFullOptimizeStep(szStep, true);
            szStep = GetRealOptimizeStep(szStep);
            SetStepBar(szStep.length() - 1);
        }
        else
        {
        	String szTipStep = "";
        	iStepLen = trainPlayStep.length();
        	if(iStepLen == 0)
        	{
        		return;
        	}
        	if (iStepLen > 7)
        	{
        		szTipStep = trainPlayStep.substring(iCurrentStepIndex - iCurrentIndex, iStepLen);
        	}
        	else
        	{
        		szTipStep = trainPlayStep.substring(0, iStepLen);
        	}
        	
        	for(int i = 0; i<7; i++)
        	{
        		if(i > szTipStep.length() - 1)
        		{
        			acTip[i].cColorStep = '0';
        		}
        		else
        		{
        			acTip[i].cColorStep = szTipStep.charAt(i);
        		}
        	}
        	SetStepBar(iCurrentStepIndex, trainPlayStep.length() - 1);
        }
    }


    private Boolean IfMatchTrainStep(char cStep)
    {
        if (E_GAMEMODE.STUDY_MODE != eGameMode || isTip)
        {
            return true;
        }

        if (trainPlayStep.length() > 0)
        {

            if (iCurrentStepIndex >= trainPlayStep.length())
            {
                /*退出TRAIN_MODE*/
                eGameMode = E_GAMEMODE.PLAY_MODE;
//                RaiseCubeMessageEvent("当前已经退出训练模式，要继续训练，请在训练模式中重新选择训练级别！", "提示", E_Message_Type.TYPE_TRAIN_EXIT);
                return true;
            }
            else
            {
            	char cTrainStep = trainPlayStep.charAt(iCurrentStepIndex + 1);
                if (cStep == cTrainStep)
                {
                    iCurrentStepIndex++;
                    UpdateTipData();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return true;
        }

    
    }

    /*检查是否匹配训练状态,，匹配根据颜色步骤来进行*/
    public Boolean CheckIfMatchTrainStep(char cColorStep)
    {
    	RecordStep(Character.toString(cColorStep));

        if (isTrainUndo)                     //如果是训练的UNDO状态，不需要再次检查
        {
            return true;
        }

        if (!IfMatchTrainStep(cColorStep))
        {
            //Sound(E_SOUND.SOUND_ABNORMAL);
            isTrainUndo = true;              //不匹配，需要UNDO
            return false;
        }
        else
        {
            return true;
        }
    }

    public void UndoTrainStep(E_ROTATIONEVENT eRotate)
    {
        if (isTrainUndo)
        {
            Rotation(GetRotationReverseEvent(eRotate));
        }
        isTrainUndo = false;
    }



    /*设置游戏模式*/
    public void SetGameMode(E_GAMEMODE eMode)
    {
        eGameMode = eMode;
        if (eGameMode == E_GAMEMODE.STUDY_MODE)
        {
            //trmAutoTip.Enabled = true;
            if (intelligentTipDelay < 5)
            {
                intelligentTipDelay = 5;
            }
            //trmAutoTip.Interval = intelligentTipDelay * 1000;
            //trmAutoTip.Tick += trmAutoTip_Tick;
        }
        else
        {
            //trmAutoTip.Enabled = false;
            //trmAutoTip.Tick -= trmAutoTip_Tick;
        }
    }

    /*复位自动提示定时器，重新开始计时*/
    public void ResetAutoTipTimer()
    {
        if (eGameMode == E_GAMEMODE.STUDY_MODE)
        {
        	iTipTimerCnt = 0;
        }
    }
    
    //根据色面获得XYZ的物理存在面
    private char GetXYZPhysicalStep(char cColorStep, Boolean bClockWise)
    {
    	int iBlockIndex = 0;
    	E_COLOR eColor = E_COLOR.NOCOLOR;
    	char cPhysicalStep = ' ';
    	
    	
    	
    	switch(cColorStep)
    	{
    		case 'X':
    			do
    			{
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.L[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.L;
        			if (eColor == E_COLOR.WHITE)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'X';
        				}
        				else
        				{
        					cPhysicalStep = 'x';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.YELLOW)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'x';
        				}
        				else
        				{
        					cPhysicalStep = 'X';
        				}    
        				break;
        			}
        			else
        			{}
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.F[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.F;

        			if (eColor == E_COLOR.WHITE)
        			{    	
        				if (bClockWise)
        				{
        					cPhysicalStep = 'z';
        				}
        				else
        				{
        					cPhysicalStep = 'Z';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.YELLOW)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Z';
        				}
        				else
        				{
        					cPhysicalStep = 'z';
        				}
        				break;
        			}
        			else
        			{}
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.U[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.U;

            		if(eColor == E_COLOR.WHITE)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'y';
        				}
        				else
        				{
        					cPhysicalStep = 'Y';
        				}     
        				break;
        			}
            		else if(eColor == E_COLOR.YELLOW)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Y';
        				}
        				else
        				{
        					cPhysicalStep = 'y';
        				}        				
        				break;
        			}
            		else
            		{}
    			}while(false);
    			break;    			
    		case 'Y':
    			do 
    			{
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.U[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.U;
        			if (eColor == E_COLOR.ORANGE)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Y';
        				}
        				else
        				{
        					cPhysicalStep = 'y';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.RED)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'y';
        				}
        				else
        				{
        					cPhysicalStep = 'Y';
        				}
        				break;
        			}
        			else
        			{}
        			
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.L[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.L;
        			
        			if (eColor == E_COLOR.ORANGE)
        			{    	
        				if (bClockWise)
        				{
        					cPhysicalStep = 'x';
        				}
        				else
        				{
        					cPhysicalStep = 'X';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.RED)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'X';
        				}
        				else
        				{
        					cPhysicalStep = 'x';
        				}     
        				break;
        			}
        			else
        			{}
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.F[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.F;
        			
            		if(eColor == E_COLOR.ORANGE)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Z';
        				}
        				else
        				{
        					cPhysicalStep = 'z';
        				}        				
        				break;
        			}
            		else if(eColor == E_COLOR.RED)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'z';
        				}
        				else
        				{
        					cPhysicalStep = 'Z';
        				}     
        				break;
        			}
            		else
            		{}
    			}while(false);
    			break;
    		case 'Z':
    			do
    			{
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.F[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.F;
        			if (eColor == E_COLOR.BLUE)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Z';
        				}
        				else
        				{
        					cPhysicalStep = 'z';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.GREEN)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'z';
        				}
        				else
        				{
        					cPhysicalStep = 'Z';
        				}    
        				break;
        			}
        			else
        			{}
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.L[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.L;

        			if (eColor == E_COLOR.BLUE)
        			{    	
        				if (bClockWise)
        				{
        					cPhysicalStep = 'x';
        				}
        				else
        				{
        					cPhysicalStep = 'X';
        				}
        				break;
        			}
        			else if (eColor == E_COLOR.GREEN)
        			{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'X';
        				}
        				else
        				{
        					cPhysicalStep = 'x';
        				}     
        				break;
        			}
        			else
        			{}
        			iBlockIndex = DREAM_CUBE_CACULATE.Plate.U[1][1];
        			eColor = DREAM_CUBE_CACULATE.cube_status[iBlockIndex].Color.U;
            		if(eColor == E_COLOR.BLUE)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'Y';
        				}
        				else
        				{
        					cPhysicalStep = 'y';
        				}        			
        				break;
        			}
            		else if(eColor == E_COLOR.GREEN)
            		{
        				if (bClockWise)
        				{
        					cPhysicalStep = 'y';
        				}
        				else
        				{
        					cPhysicalStep = 'Y';
        				}        				
        				break;
        			}
            		else
            		{}
    			}while(false);
    			break;
    	}
    	return cPhysicalStep;
    }


    //根据物理面得到XYZ面的色面
    private char GetXYZColorStep(char cPhysicalStep, Boolean bClockWise)
    {
        int iBlockIndex = 0;
        E_PLATE eRealPlate = E_PLATE.NOPLATE;
        char cStep = ' ';

        switch (cPhysicalStep)
        {
            case 'X':   //R
                iBlockIndex = DREAM_CUBE_CACULATE.Plate.R[1][1];
                break;
            case 'Y':   //U
                iBlockIndex = DREAM_CUBE_CACULATE.Plate.U[1][1];
                break;
            case 'Z':   //F
                iBlockIndex = DREAM_CUBE_CACULATE.Plate.F[1][1];
                break;
            default:
                return ' ';
        }

        if (iBlockIndex == 22)
        {
            eRealPlate = E_PLATE.UP;
        }
        else if (iBlockIndex == 4)
        {
            eRealPlate = E_PLATE.DOWN;
        }
        else if (iBlockIndex == 16)
        {
            eRealPlate = E_PLATE.FRONT;
        }
        else if (iBlockIndex == 10)
        {
            eRealPlate = E_PLATE.BACK;
        }
        else if (iBlockIndex == 12)
        {
            eRealPlate = E_PLATE.LEFT;
        }
        else if (iBlockIndex == 14)
        {
            eRealPlate = E_PLATE.RIGHT;
        }

        switch (eRealPlate)
        {
            case UP:
                if (bClockWise)
                {
                    cStep = 'Y';
                }
                else
                {
                    cStep = 'y';
                }
                break;
            case DOWN:
                if (bClockWise)
                {
                    cStep = 'y';
                }
                else
                {
                    cStep = 'Y';
                }
                break;
            case FRONT:
                if (bClockWise)
                {
                    cStep = 'Z';
                }
                else
                {
                    cStep = 'z';
                }
                break;
            case BACK:
                if (bClockWise)
                {
                    cStep = 'z';
                }
                else
                {
                    cStep = 'Z';
                }
                break;
            case LEFT:
                if (bClockWise)
                {
                    cStep = 'x';
                }
                else
                {
                    cStep = 'X';
                }
                break;
            case RIGHT:
                if (bClockWise)
                {
                    cStep = 'X';
                }
                else
                {
                    cStep = 'x';
                }
                break;
        }
        return cStep;
    }


    public E_ROTATIONEVENT GetRotationEvent(char cStep)
    {
        int iBlockIndex = 0;
        int iBlockIndex2 = 0;
        int i = 1;
        Boolean bDoublePlate = false;

        char cStepUpcase = Character.toUpperCase(cStep);

        E_PLATE ePlate = E_PLATE.NOPLATE;
        E_ROTATIONEVENT eDirection = E_ROTATIONEVENT.NULL;

        switch (cStepUpcase)
        {
            case 'U':
                iBlockIndex = 22;
                break;
            case 'D':
                iBlockIndex = 4;
                break;
            case 'F':
                iBlockIndex = 16;
                break;
            case 'B':
                iBlockIndex = 10;
                break;
            case 'L':
                iBlockIndex = 12;
                break;
            case 'R':
                iBlockIndex = 14;
                break;
            case 'Q':
                iBlockIndex = 22;
                bDoublePlate = true;
                break;
            case 'W':
                iBlockIndex = 4;
                bDoublePlate = true;
                break;
            case 'G':
                iBlockIndex = 16;
                bDoublePlate = true;
                break;
            case 'H':
                iBlockIndex = 10;
                bDoublePlate = true;
                break;
            case 'O':
                iBlockIndex = 12;
                bDoublePlate = true;
                break;
            case 'P':
                iBlockIndex = 14;
                bDoublePlate = true;
                break;
            case 'X':
                iBlockIndex = 14;   //R
                break;
            case 'Y':
                iBlockIndex = 22;   //U
                break;
            case 'Z':
                iBlockIndex = 16;   //F
                break;
            default:
                return E_ROTATIONEVENT.NULL;
        }

        if (cStepUpcase == 'X' || cStepUpcase == 'Y' || cStepUpcase == 'Z')
        {
            for (i = 1; i <= 6; i++)
            {
                ePlate = E_PLATE.ToPlate(i);

                switch (ePlate)
                {
                    case UP:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.U[1][1];
                        break;
                    case DOWN:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.D[1][1];
                        break;
                    case FRONT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.F[1][1];
                        break;
                    case BACK:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.B[1][1];
                        break;
                    case LEFT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.L[1][1];
                        break;
                    case RIGHT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.R[1][1];
                        break;
                }
                if (iBlockIndex == iBlockIndex2)
                {
                    break;
                }
            }

            if (i > 6)
            {
                return E_ROTATIONEVENT.NULL;
            }

            switch (ePlate)
            {
                case UP:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.Y;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.y;
                    }
                    break;
                case DOWN:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.y;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.Y;
                    }
                    break;
                case FRONT:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.Z;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.z;
                    }
                    break;
                case BACK:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.z;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.Z;
                    }
                    break;
                case LEFT:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.x;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.X;
                    }
                    break;
                case RIGHT:
                    if (cStep == cStepUpcase)
                    {
                        eDirection = E_ROTATIONEVENT.X;
                    }
                    else
                    {
                        eDirection = E_ROTATIONEVENT.x;
                    }
                    break;
            }
            return eDirection;
        }
        else
        {
            for (i = 1; i <= 6; i++)
            {
                ePlate = E_PLATE.ToPlate(i);

                switch (ePlate)
                {
                    case UP:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.U[1][1];
                        break;
                    case DOWN:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.D[1][1];
                        break;
                    case FRONT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.F[1][1];
                        break;
                    case BACK:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.B[1][1];
                        break;
                    case LEFT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.L[1][1];
                        break;
                    case RIGHT:
                        iBlockIndex2 = DREAM_CUBE_CACULATE.Plate.R[1][1];
                        break;
                }
                if (iBlockIndex == iBlockIndex2)
                {
                    break;
                }
            }

            if (i > 6)
            {
                return E_ROTATIONEVENT.NULL;
            }

            switch (ePlate)
            {
                case UP:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DU;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.U;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Du;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.u;
                        }
                    }
                    break;
                case DOWN:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DD;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.D;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Dd;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.d;
                        }
                    }
                    break;
                case FRONT:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DF;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.F;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Df;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.f;
                        }
                    }
                    break;
                case BACK:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DB;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.B;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Db;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.b;
                        }
                    }
                    break;
                case LEFT:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DL;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.L;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Dl;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.l;
                        }
                    }
                    break;
                case RIGHT:
                    if (cStep == cStepUpcase)
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.DR;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.R;
                        }
                    }
                    else
                    {
                        if (bDoublePlate)
                        {
                            eDirection = E_ROTATIONEVENT.Dr;
                        }
                        else
                        {
                            eDirection = E_ROTATIONEVENT.r;
                        }
                    }
                    break;
            }
            return eDirection;
        }

    }


    private E_ROTATIONEVENT GetRotationReverseEvent(E_ROTATIONEVENT eRotationEvent)
    {
        E_ROTATIONEVENT eRotation = E_ROTATIONEVENT.NULL;


        switch (eRotationEvent)
        { 
            case U:
                eRotation = E_ROTATIONEVENT.u;
                break;
            case u:
                eRotation = E_ROTATIONEVENT.U;
                break;
            case D:
                eRotation = E_ROTATIONEVENT.d;
                break;
            case d:
                eRotation = E_ROTATIONEVENT.D;
                break;
            case F:
                eRotation = E_ROTATIONEVENT.f;
                break;
            case f:
                eRotation = E_ROTATIONEVENT.F;
                break;
            case B:
                eRotation = E_ROTATIONEVENT.b;
                break;
            case b:
                eRotation = E_ROTATIONEVENT.B;
                break;
            case L:
                eRotation = E_ROTATIONEVENT.l;
                break;
            case l:
                eRotation = E_ROTATIONEVENT.L;
                break;
            case R:
                eRotation = E_ROTATIONEVENT.r;
                break;
            case r:
                eRotation = E_ROTATIONEVENT.R;
                break;
            case DU:
                eRotation = E_ROTATIONEVENT.Du;
                break;
            case Du:
                eRotation = E_ROTATIONEVENT.DU;
                break;
            case DD:
                eRotation = E_ROTATIONEVENT.Dd;
                break;
            case Dd:
                eRotation = E_ROTATIONEVENT.DD;
                break;
            case DF:
                eRotation = E_ROTATIONEVENT.Df;
                break;
            case Df:
                eRotation = E_ROTATIONEVENT.DF;
                break;
            case DB:
                eRotation = E_ROTATIONEVENT.Db;
                break;
            case Db:
                eRotation = E_ROTATIONEVENT.DB;
                break;
            case DL:
                eRotation = E_ROTATIONEVENT.Dl;
                break;
            case Dl:
                eRotation = E_ROTATIONEVENT.DL;
                break;
            case DR:
                eRotation = E_ROTATIONEVENT.Dr;
                break;
            case Dr:
                eRotation = E_ROTATIONEVENT.DR;
                break;
            case X:
                eRotation = E_ROTATIONEVENT.x;
                break;
            case x:
                eRotation = E_ROTATIONEVENT.X;
                break;
            case Y:
                eRotation = E_ROTATIONEVENT.y;
                break;
            case y:
                eRotation = E_ROTATIONEVENT.Y;
                break;
            case Z:
                eRotation = E_ROTATIONEVENT.z;
                break;
            case z:
                eRotation = E_ROTATIONEVENT.Z;
                break;
            case Roll_X:
                eRotation = E_ROTATIONEVENT.Roll_x;
                break;
            case Roll_x:
                eRotation = E_ROTATIONEVENT.Roll_X;
                break;
            case Roll_Y:
                eRotation = E_ROTATIONEVENT.Roll_y;
                break;
            case Roll_y:
                eRotation = E_ROTATIONEVENT.Roll_Y;
                break;
            case Roll_Z:
                eRotation = E_ROTATIONEVENT.Roll_z;
                break;
            case Roll_z:
                eRotation = E_ROTATIONEVENT.Roll_Z;
                break;
            default:
                eRotation = E_ROTATIONEVENT.NULL;
                break;
        }
        return eRotation;
    }

    /*要进行转换，找到对应的操作面*/
    public E_ROTATIONEVENT GetFirstTrainStep()
    { 
        char cColorStep;

        if (trainPlayStep.length() > 0)
        {
        	
        	cColorStep = trainPlayStep.charAt(0);

            return GetRotationEvent(cColorStep);
        }
        return E_ROTATIONEVENT.NULL;
    }

    /*提示当前步骤，要进行转换，找到对应的操作面*/
    public void AutoTip()
    {
        if (eGameMode != E_GAMEMODE.STUDY_MODE)
        {
            return;
        }
        if (!bAutoTip)
        {
        	return;
        }

        char cColorStep;
        E_ROTATIONEVENT eRotate = E_ROTATIONEVENT.NULL;

        if (trainPlayStep.length() > 0)
        {
            if (iCurrentStepIndex < trainPlayStep.length())
            {
                cColorStep = trainPlayStep.charAt(iCurrentStepIndex + 1);

                eRotate = GetRotationEvent(cColorStep);

                if (eRotate != E_ROTATIONEVENT.NULL)
                {
                    RaiseCubeOperationTipEvent(eRotate, cColorStep);
                }
            }
        }
    }


    /*将魔方旋转使他进入标准状态*/
    protected void TurnIntoStandarCube(ST_DreamCube dreamcube, Boolean bRaiseEvent, Boolean bCheck)
    {
        int[][] aiPlate = new int[3][3];

        E_PLATE ePlate = E_PLATE.NOPLATE;

        Boolean bOldEventStatus = true;

        if (!bRaiseEvent)
        {
            bOldEventStatus = GetEventStatus();            //保存原来的事件处理状态
            SetEventStatus(false);                              //设置成不使用事件
        }

        int i = 0;
        /*下面这个循环处理L面*/
        for (i = 1; i <= 6; i++)
        {
            switch (E_PLATE.ToPlate(i))
            {
                case UP:
                    aiPlate = dreamcube.Plate.U;
                    break;
                case DOWN:
                    aiPlate = dreamcube.Plate.D;
                    break;
                case FRONT:
                    aiPlate = dreamcube.Plate.F;
                    break;
                case BACK:
                    aiPlate = dreamcube.Plate.B;
                    break;
                case LEFT:
                    aiPlate = dreamcube.Plate.L;
                    break;
                case RIGHT:
                    aiPlate = dreamcube.Plate.R;
                    break;
            }

            if (aiPlate[1][1] == 12)    //L
            {
                ePlate = E_PLATE.ToPlate(i);
                break;
            }
        }

        switch (ePlate)
        {
            case UP:
                ROTATION(E_ROTATIONEVENT.Roll_z, dreamcube, bCheck);
                break;
            case RIGHT:
                ROTATION(E_ROTATIONEVENT.Roll_z, dreamcube, bCheck);
                ROTATION(E_ROTATIONEVENT.Roll_z, dreamcube, bCheck);
                break;
            case DOWN:
                ROTATION(E_ROTATIONEVENT.Roll_Z, dreamcube, bCheck);
                break;
            case FRONT:
                ROTATION(E_ROTATIONEVENT.Roll_Y, dreamcube, bCheck);
                break;
            case BACK:
                ROTATION(E_ROTATIONEVENT.Roll_y, dreamcube, bCheck);
                break;
        }


        /*下面这个循环处理D面*/
        for (i = 1; i <= 6; i++)
        {
            switch (E_PLATE.ToPlate(i))
            {
                case UP:
                    aiPlate = dreamcube.Plate.U;
                    break;
                case DOWN:
                    aiPlate = dreamcube.Plate.D;
                    break;
                case FRONT:
                    aiPlate = dreamcube.Plate.F;
                    break;
                case BACK:
                    aiPlate = dreamcube.Plate.B;
                    break;
                case LEFT:
                    aiPlate = dreamcube.Plate.L;
                    break;
                case RIGHT:
                    aiPlate = dreamcube.Plate.R;
                    break;
            }

            if (aiPlate[1][1] == 4)
            {
                ePlate = E_PLATE.ToPlate(i);
                break;
            }
        }

        switch (ePlate)
        {
            case UP:
                ROTATION(E_ROTATIONEVENT.Roll_x, dreamcube, bCheck);
                ROTATION(E_ROTATIONEVENT.Roll_x, dreamcube, bCheck);
                break;
            case FRONT:
                ROTATION(E_ROTATIONEVENT.Roll_x, dreamcube, bCheck);
                break;
            case BACK:
                ROTATION(E_ROTATIONEVENT.Roll_X, dreamcube, bCheck);
                break;
            case LEFT:
                ROTATION(E_ROTATIONEVENT.Roll_z, dreamcube, bCheck);
                break;
            case RIGHT:
                ROTATION(E_ROTATIONEVENT.Roll_Z, dreamcube, bCheck);
                break;
        }

        if (!bRaiseEvent)
        {
            SetEventStatus(bOldEventStatus);
        }

    }



    
    public float YDegree(float value)
    {
        fYDegree = value;
        if (fYDegree < 0.0f)
        {
            fYDegree += 360.0f;
        }
        if (fYDegree >= 360.0f)
        {
            fYDegree = 0.0f;
        }
        return fYDegree;
    }





    /*方块是否不在默认规定的位置（要判断坐标及颜色）,用MAGIC_CUBE_CHECKSTATUS与MAGIC_CUBE_INITSTATUS进行比较*/
    protected Boolean IsBlockNotInCorrectPostion(int iBlockIndex)
    {
        if (!DREAM_CUBE_CHECKSTATUS.cube_status[iBlockIndex].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[iBlockIndex].Position))
        {
            return true;
        }
        else
        {
            if (!DREAM_CUBE_CHECKSTATUS.cube_status[iBlockIndex].Color.Equals(DREAM_CUBE_INITSTATUS.cube_status[iBlockIndex].Color))
            {

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /*方块是否在规定的位置（要判断坐标及颜色）,用MAGIC_CUBE_CHECKSTATUS与MAGIC_CUBE_INITSTATUS进行比较*/
    protected Boolean IsBlockInCorrectPostion(int iBlockIndex)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockIndex].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[iBlockIndex].Position))
        {
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockIndex].Color.Equals(DREAM_CUBE_INITSTATUS.cube_status[iBlockIndex].Color))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /*方块是否在指定的位置(注意：只判断坐标，不判断颜色)，用MAGIC_CUBE_CHECKSTATUS与MAGIC_CUBE_INITSTATUS进行比较*/
    protected Boolean IsBlockInSpecificPosition(int iBlockIndex, int iPosition)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockIndex].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[iPosition].Position))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*方块的指定颜色是否出现在指定面上*/
    protected Boolean IsXColorInYPlate(int iBlock, E_COLOR eXColor, E_PLATE eYPlate)
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        ePlate = GetPlateWithColor(iBlock, eXColor);
        if (ePlate == eYPlate)
        {
            return true;
        }
        else
        {
            return false;
        }
    }




    public Boolean isRotatting = false;

    //根据面信息，获得用字母表示的操作面信息。
    public char GetStepByPlate(E_PLATE ePlate, Boolean bClockWise, Boolean bDoublePlate, Boolean b180)
    {
        char cStep = ' ';

        if(b180)
        {
        	cStep = astFormulaChar[ePlate.ordinal()].HP;
        }
        else
        {
	        if (bDoublePlate)
	        {
	            cStep = astFormulaChar[ePlate.ordinal()].DP;
	        }
	        else
	        {
	            cStep = astFormulaChar[ePlate.ordinal()].SP;
	        }
        }
        if (!bClockWise)
        {
            cStep = Character.toLowerCase(cStep);
        }
        return cStep;
    }

    
    //根据色面获取物理面
    public char ColorPlate2PhysicalPlate(char cColorStep)
    {
    	int iBlock = 0;
    	char cPhysicalStep = ' ';
    	E_COLOR eColor = E_COLOR.NOCOLOR;
    	E_PLATE ePlate = E_PLATE.NOPLATE;
    	Boolean bDoublePlate = false;
        Boolean bClockWise = false;
        Boolean b180 = false;
        if (Character.isUpperCase(cColorStep))
        {
            bClockWise = true;
        }

    	cColorStep = Character.toUpperCase(cColorStep);
    	
    	if (cColorStep == 'X' || cColorStep == 'Y' || cColorStep == 'Z')
        {
    		cPhysicalStep = GetXYZPhysicalStep(cColorStep, bClockWise);
        }
    	else
    	{
	    	if (cColorStep == 'Q' 
	            || cColorStep == 'W' 
	            || cColorStep == 'G' 
	            || cColorStep == 'H' 
	            || cColorStep == 'O' 
	            || cColorStep == 'P')
	        {
	            bDoublePlate = true;
	        }
	    	else if(cColorStep == 'A'
	    		|| cColorStep == 'C'
	    		|| cColorStep == 'E'
	    		|| cColorStep == 'I'
	    		|| cColorStep == 'J'
	    		|| cColorStep == 'K')
	    	{
	    		b180 = true;
	    	}
	    	switch(cColorStep)   //色面对应的颜色
	    	{
	    		case 'Q':
	    		case 'U':
				case 'A':
	    			eColor = E_COLOR.ORANGE;
	    			break;
	    		case 'W':
	    		case 'D':
				case 'C':
	    			eColor = E_COLOR.RED;
	    			break;
	    		case 'G':
	    		case 'F':
				case 'E':
	    			eColor = E_COLOR.BLUE;
	    			break;
	    		case 'H':
	    		case 'B':
				case 'I':
	    			eColor = E_COLOR.GREEN;
	    			break;
	    		case 'O':
	    		case 'L':
				case 'J':
	    			eColor = E_COLOR.WHITE;
	    			break;
	    		case 'P':
	    		case 'R':
				case 'K':
	    			eColor = E_COLOR.YELLOW;
	    			break;    			
	    	}
	    	
	    	//匹配该颜色出现在哪一个物理面上
	    	do
	    	{
	        	iBlock = DREAM_CUBE_CACULATE.Plate.U[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.U == eColor)
	        	{
	        		ePlate = E_PLATE.UP;
	        		break;
	        	}
	        	iBlock = DREAM_CUBE_CACULATE.Plate.D[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.D == eColor)
	        	{
	        		ePlate = E_PLATE.DOWN;
	        		break;
	        	}
	        	iBlock = DREAM_CUBE_CACULATE.Plate.F[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.F == eColor)
	        	{
	        		ePlate = E_PLATE.FRONT;
	        		break;
	        	}
	        	iBlock = DREAM_CUBE_CACULATE.Plate.B[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.B == eColor)
	        	{
	        		ePlate = E_PLATE.BACK;
	        		break;
	        	}
	        	iBlock = DREAM_CUBE_CACULATE.Plate.L[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.L == eColor)
	        	{
	        		ePlate = E_PLATE.LEFT;
	        		break;
	        	}
	        	iBlock = DREAM_CUBE_CACULATE.Plate.R[1][1];
	        	if (DREAM_CUBE_CACULATE.cube_status[iBlock].Color.R == eColor)
	        	{
	        		ePlate = E_PLATE.RIGHT;
	        		break;
	        	}
	    	}while(false);
	    	
	    	//查表得到对应的步骤
	    	cPhysicalStep = GetStepByPlate(ePlate, bClockWise, bDoublePlate, b180);
    	}
    	return cPhysicalStep;
    }


    /*获取实际的操作面，根据公式进行操作，需要先翻译成实际的操作面（色面）。
     如果有MES操作，相当于魔方整体进行了旋转，
     * 需要根据物理操作面对应的颜色找到实际的操作面（色面）
     cPhysicalStep物理步骤，是对色面进行的操作，物理面--》色面
     */
    public char PhysicalPlate2ColorPlate(char cPhysicalStep)
    {
        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_PLATE ePlate = E_PLATE.NOPLATE;
        char cColorStep = ' ';
        int iBlock = 0;
        Boolean bClockWise = false;
        Boolean bDoublePlate = false;
        Boolean b180 = false;
        if (Character.isUpperCase(cPhysicalStep))
        {
            bClockWise = true;
        }

        cPhysicalStep = Character.toUpperCase(cPhysicalStep);

        if (cPhysicalStep == 'X' || cPhysicalStep == 'Y' || cPhysicalStep == 'Z')
        {
            cColorStep = GetXYZColorStep(cPhysicalStep, bClockWise);
        }
        else
        {
            if (cPhysicalStep == 'Q' 
                || cPhysicalStep == 'W' 
                || cPhysicalStep == 'G' 
                || cPhysicalStep == 'H' 
                || cPhysicalStep == 'O' 
                || cPhysicalStep == 'P')
            {
                bDoublePlate = true;
            }
	    	else if(cColorStep == 'A'
	    		|| cColorStep == 'C'
	    		|| cColorStep == 'E'
	    		|| cColorStep == 'I'
	    		|| cColorStep == 'J'
	    		|| cColorStep == 'K')
	    	{
	    		b180 = true;
	    	}
            
            //根据物理面，可以直接得到对应方块及颜色
            switch (cPhysicalStep)
            {
                case 'U':
                case 'Q':
                case 'A':
                    iBlock = DREAM_CUBE_CACULATE.Plate.U[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.U;
                    break;
                case 'D':
                case 'W':
                case 'C':
                    iBlock = DREAM_CUBE_CACULATE.Plate.D[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.D;
                    break;
                case 'F':
                case 'G':
                case 'E':
                    iBlock = DREAM_CUBE_CACULATE.Plate.F[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.F;
                    break;
                case 'B':
                case 'H':
                case 'I':
                    iBlock = DREAM_CUBE_CACULATE.Plate.B[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.B;
                    break;
                case 'L':
                case 'O':
                case 'J':
                    iBlock = DREAM_CUBE_CACULATE.Plate.L[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.L;
                    break;
                case 'R':
                case 'P':
                case 'K':
                    iBlock = DREAM_CUBE_CACULATE.Plate.R[1][1];
                    eColor = DREAM_CUBE_CACULATE.cube_status[iBlock].Color.R;
                    break;
            }
            
            //根据颜色来获取对应的面信息
            ePlate = GetColorPlatebyStandarColor(eColor);
            if (ePlate == E_PLATE.NOPLATE)
            {
                return ' ';
            }
            //查表得到字符形式表示的步骤
            cColorStep = GetStepByPlate(ePlate, bClockWise, bDoublePlate, b180);
        }
        return cColorStep;
    }
    
    private void Rotation(char cPhysicalStep)
    {
    	OnCubeSound(E_SOUND.ROTATE);
        //旋转用的是物理面，公式记录的是色面。魔方变换算法目前是基于物理面的。
        switch (cPhysicalStep)          //旋转计算用物理面
        { 
            case 'U':
                Rotation(E_ROTATIONEVENT.U, DREAM_CUBE_CACULATE, true);
                break;
            case 'u':
                Rotation(E_ROTATIONEVENT.u, DREAM_CUBE_CACULATE, true);
                break;
            case 'D':
                Rotation(E_ROTATIONEVENT.D, DREAM_CUBE_CACULATE, true);
                break;
            case 'd':
                Rotation(E_ROTATIONEVENT.d, DREAM_CUBE_CACULATE, true);
                break;
            case 'F':
                Rotation(E_ROTATIONEVENT.F, DREAM_CUBE_CACULATE, true);
                break;
            case 'f':
                Rotation(E_ROTATIONEVENT.f, DREAM_CUBE_CACULATE, true);
                break;
            case 'B':
                Rotation(E_ROTATIONEVENT.B, DREAM_CUBE_CACULATE, true);
                break;
            case 'b':
                Rotation(E_ROTATIONEVENT.b, DREAM_CUBE_CACULATE, true);
                break;
            case 'L':
                Rotation(E_ROTATIONEVENT.L, DREAM_CUBE_CACULATE, true);
                break;
            case 'l':
                Rotation(E_ROTATIONEVENT.l, DREAM_CUBE_CACULATE, true);
                break;
            case 'R':
                Rotation(E_ROTATIONEVENT.R, DREAM_CUBE_CACULATE, true);
                break;
            case 'r':
                Rotation(E_ROTATIONEVENT.r, DREAM_CUBE_CACULATE, true);
                break;
            case 'Q':
                Rotation(E_ROTATIONEVENT.DU, DREAM_CUBE_CACULATE, true);
                break;
            case 'q':
                Rotation(E_ROTATIONEVENT.Du, DREAM_CUBE_CACULATE, true);
                break;
            case 'W':
                Rotation(E_ROTATIONEVENT.DD, DREAM_CUBE_CACULATE, true);
                break;
            case 'w':
                Rotation(E_ROTATIONEVENT.Dd, DREAM_CUBE_CACULATE, true);
                break;
            case 'G':
                Rotation(E_ROTATIONEVENT.DF, DREAM_CUBE_CACULATE, true);
                break;
            case 'g':
                Rotation(E_ROTATIONEVENT.Df, DREAM_CUBE_CACULATE, true);
                break;
            case 'H':
                Rotation(E_ROTATIONEVENT.DB, DREAM_CUBE_CACULATE, true);
                break;
            case 'h':
                Rotation(E_ROTATIONEVENT.Db, DREAM_CUBE_CACULATE, true);
                break;
            case 'O':
                Rotation(E_ROTATIONEVENT.DL, DREAM_CUBE_CACULATE, true);
                break;
            case 'o':
                Rotation(E_ROTATIONEVENT.Dl, DREAM_CUBE_CACULATE, true);
                break;
            case 'P':
                Rotation(E_ROTATIONEVENT.DR, DREAM_CUBE_CACULATE, true);
                break;
            case 'p':
                Rotation(E_ROTATIONEVENT.Dr, DREAM_CUBE_CACULATE, true);
                break;
            case 'X':
                Rotation(E_ROTATIONEVENT.X, DREAM_CUBE_CACULATE, true);
                break;
            case 'x':
                Rotation(E_ROTATIONEVENT.x, DREAM_CUBE_CACULATE, true);
                break;
            case 'Y':
                Rotation(E_ROTATIONEVENT.Y, DREAM_CUBE_CACULATE, true);
                break;
            case 'y':
                Rotation(E_ROTATIONEVENT.y, DREAM_CUBE_CACULATE, true);
                break;
            case 'Z':
                Rotation(E_ROTATIONEVENT.Z, DREAM_CUBE_CACULATE, true);
                break;
            case 'z':
                Rotation(E_ROTATIONEVENT.z, DREAM_CUBE_CACULATE, true);
                break;
            case 'A':
                Rotation(E_ROTATIONEVENT.U, DREAM_CUBE_CACULATE, false);
                Rotation(E_ROTATIONEVENT.U, DREAM_CUBE_CACULATE, true);				
                break;
            case 'a':
                Rotation(E_ROTATIONEVENT.u, DREAM_CUBE_CACULATE, false);
                Rotation(E_ROTATIONEVENT.u, DREAM_CUBE_CACULATE, true);
                break;
            case 'C':
                Rotation(E_ROTATIONEVENT.D, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.D, DREAM_CUBE_CACULATE, true);
                break;
            case 'c':
                Rotation(E_ROTATIONEVENT.d, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.d, DREAM_CUBE_CACULATE, true);
                break;
            case 'E':
                Rotation(E_ROTATIONEVENT.F, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.F, DREAM_CUBE_CACULATE, true);
                break;
            case 'e':
                Rotation(E_ROTATIONEVENT.f, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.f, DREAM_CUBE_CACULATE, true);
                break;
            case 'I':
                Rotation(E_ROTATIONEVENT.B, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.B, DREAM_CUBE_CACULATE, true);
                break;
            case 'i':
                Rotation(E_ROTATIONEVENT.b, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.b, DREAM_CUBE_CACULATE, true);
                break;				
            case 'J':
                Rotation(E_ROTATIONEVENT.L, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.L, DREAM_CUBE_CACULATE, true);
                break;
            case 'j':
                Rotation(E_ROTATIONEVENT.l, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.l, DREAM_CUBE_CACULATE, true);
                break;
            case 'K':
                Rotation(E_ROTATIONEVENT.R, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.R, DREAM_CUBE_CACULATE, true);
                break;
            case 'k':
                Rotation(E_ROTATIONEVENT.r, DREAM_CUBE_CACULATE, false);
				Rotation(E_ROTATIONEVENT.r, DREAM_CUBE_CACULATE, true);
                break;				
				
		}
        return;
    }
    
    
    //根据公式进行魔方变换。
    //公式是基于物理面的，旋转也是基于物理面进行旋转
    public void RotationByPhysicalFormula(String szFormula)
    {
        int i = 0;
        char cPhysicalStep = ' ';
        char cColorStep = ' ';

        for (i = 0; i < szFormula.length(); i++)
        {
            cPhysicalStep = szFormula.charAt(i);
            cColorStep = PhysicalPlate2ColorPlate(cPhysicalStep);    //物理面--》色面

            RecordStep(Character.toString(cColorStep));          //记录色面)

            Rotation(cPhysicalStep);
        }
    }

    public void RotationByOptimizeFormula(String szFormula)
    {
        int i = 0;
        char cPhysicalStep = ' ';

        for (i = 0; i < szFormula.length(); i++)
        {
            cPhysicalStep = szFormula.charAt(i);            
            RecordStep(Character.toString(cPhysicalStep));          //记录色面)

            Rotation(cPhysicalStep);
        }
    }
	

    //根据色面公式来旋转
    public void RotationByColorFormula(String szFormula, boolean bDirection)
    {
        int i = 0;
        char cPhysicalStep = ' ';
        char cColorStep = ' ';

        for (i = 0; i < szFormula.length(); i++)
        {
        	if (eGameMode == E_GAMEMODE.AUTO_MODE || eGameMode == E_GAMEMODE.STUDY_MODE)
        	{
            	if (bDirection)
            	{
            		iCurrentStepIndex += 1;
            	}
            	else
            	{
            		iCurrentStepIndex -= 1;
            	}        		
        	}
        	cColorStep = szFormula.charAt(i);
        	cPhysicalStep = ColorPlate2PhysicalPlate(cColorStep);    //色面-->物理面

            RecordStep(Character.toString(cColorStep));          //记录色面

            Rotation(cPhysicalStep);
        }
    }

    /*旋转魔方的基础函数*/
    protected void Rotation(E_ROTATIONEVENT eRotation, ST_DreamCube dreamcube, Boolean bCapture)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(eRotation, bCapture);

		XChg(dreamcube, eRotation);
		XChgDreamCube4Check();

        RaiseCubeOperationChangedEvent();

        //CheckCurrentStatus();         //这里不需要检查
        isRotatting = false;
    }

    //旋转时不产生事件，用于检测魔方的状态
    protected void RotationNoEvent(E_ROTATIONEVENT eRotation, ST_DreamCube dreamcube)
    {
		XChg(dreamcube, eRotation);
		XChgDreamCube4Check();
    }

    
    /*魔方整个旋转*/
    private void ROTATE_X(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_X, false);

        XChg(dreamcube, E_ROTATIONEVENT.R);
        XChg(dreamcube, E_ROTATIONEVENT.X);
        XChg(dreamcube, E_ROTATIONEVENT.l);

        XChgStepStatusData(E_ROTATIONEVENT.X, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    /*魔方整个旋转*/
    private void ROTATE_x(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_x, false);

        XChg(dreamcube, E_ROTATIONEVENT.r);
        XChg(dreamcube, E_ROTATIONEVENT.x);
        XChg(dreamcube, E_ROTATIONEVENT.L);

        XChgStepStatusData(E_ROTATIONEVENT.x, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    /*魔方整个旋转*/
    private void ROTATE_Y(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_Y, false);

        XChg(dreamcube, E_ROTATIONEVENT.U);
        XChg(dreamcube, E_ROTATIONEVENT.Y);
        XChg(dreamcube, E_ROTATIONEVENT.d);

        XChgStepStatusData(E_ROTATIONEVENT.Y, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    /*魔方整个旋转*/
    private void ROTATE_y(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_y, false);

        XChg(dreamcube, E_ROTATIONEVENT.u);
        XChg(dreamcube, E_ROTATIONEVENT.y);
        XChg(dreamcube, E_ROTATIONEVENT.D);

        XChgStepStatusData(E_ROTATIONEVENT.y, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    /*魔方整个旋转*/
    private void ROTATE_Z(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_Z, false);

        XChg(dreamcube, E_ROTATIONEVENT.F);
        XChg(dreamcube, E_ROTATIONEVENT.Z);
        XChg(dreamcube, E_ROTATIONEVENT.b);

        XChgStepStatusData(E_ROTATIONEVENT.Z, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    /*魔方整个旋转*/
    private void ROTATE_z(ST_DreamCube dreamcube, Boolean bCheck)
    {
        isRotatting = true;
        ResetAutoTipTimer();
        RaiseCubeOperationChangingEvent(E_ROTATIONEVENT.Roll_z, false);

        XChg(dreamcube, E_ROTATIONEVENT.f);
        XChg(dreamcube, E_ROTATIONEVENT.z);
        XChg(dreamcube, E_ROTATIONEVENT.B);

        XChgStepStatusData(E_ROTATIONEVENT.z, bCheck);

        RaiseCubeOperationChangedEvent();

        isRotatting = false;
    }

    //整个魔方旋转
    public void ROTATION(E_ROTATIONEVENT eRotation, ST_DreamCube dreamcube, Boolean bCheck)
    {
        switch (eRotation)
        {
            case Roll_X:
                ROTATE_X(dreamcube, bCheck);
                break;
            case Roll_x:
                ROTATE_x(dreamcube, bCheck);
                break;
            case Roll_Y:
                ROTATE_Y(dreamcube, bCheck);
                break;
            case Roll_y:
                ROTATE_y(dreamcube, bCheck);
                break;
            case Roll_Z:
                ROTATE_Z(dreamcube, bCheck);
                break;
            case Roll_z:
                ROTATE_z(dreamcube, bCheck);
                break;
        }
    }

    //旋转魔方的面
    public void Rotation(E_ROTATIONEVENT eRotation)
    {
        if (eRotation.ordinal() < E_ROTATIONEVENT.Roll_X.ordinal() && !isTrainUndo)
        {
            OnCubeSound(E_SOUND.ROTATE);
        }
        if (isTrainUndo)
        {
            OnCubeSound(E_SOUND.UNDO);
        }

		Rotation(eRotation, DREAM_CUBE_CACULATE, false);
		
    }


    
    /*根据索引获取坐标值，依据是标准魔方*/
    private void GetCoordinateByIndex(int iBlockIndex, ST_Position poi)
    {
        switch (iBlockIndex)
        {
        case 0:
            poi.x = -1;
            poi.y = -1;
            poi.z = -1;
            break;
        case 1:
        	poi.x = 0;
        	poi.y = -1;
        	poi.z = -1;
            break;
        case 2:
        	poi.x = 1;
        	poi.y = -1;
        	poi.z = -1;
            break;
        case 3:
        	poi.x = -1;
        	poi.y = -1;
        	poi.z = 0;
            break;
        case 4:
        	poi.x = 0;
        	poi.y = -1;
        	poi.z = 0;
            break;
        case 5:
        	poi.x = 1;
        	poi.y = -1;
        	poi.z = 0;
            break;
        case 6:
        	poi.x = -1;
        	poi.y = -1;
        	poi.z = 1;
            break;
        case 7:
        	poi.x = 0;
        	poi.y = -1;
        	poi.z = 1;
            break;
        case 8:
        	poi.x = 1;
        	poi.y = -1; 
        	poi.z = 1;
            break;
        case 9:
        	poi.x = -1;
        	poi.y = 0;
        	poi.z = -1;
            break;
        case 10:
        	poi.x = 0;
        	poi.y = 0;
        	poi.z = -1;
            break;
        case 11:
        	poi.x = 1;
        	poi.y = 0;
        	poi.z = -1;
            break;
        case 12:
        	poi.x = -1;
        	poi.y = 0;
        	poi.z = 0;
            break;
        case 13:
        	poi.x = 0;
        	poi.y = 0;
        	poi.z = 0;
            break;
        case 14:
        	poi.x = 1;
        	poi.y = 0;
        	poi.z = 0;
            break;
        case 15:
        	poi.x = -1;
        	poi.y = 0;
        	poi.z = 1;
            break;
        case 16:
        	poi.x = 0;
        	poi.y = 0;
        	poi.z = 1;
            break;
        case 17:
        	poi.x = 1;
        	poi.y = 0;
        	poi.z = 1;
            break;
        case 18:
        	poi.x = -1;
        	poi.y = 1;
        	poi.z = -1;
            break;
        case 19:
        	poi.x = 0;
        	poi.y = 1;
        	poi.z = -1;
            break;
        case 20:
        	poi.x = 1;
        	poi.y = 1;
        	poi.z = -1;
            break;
        case 21:
        	poi.x = -1; 
        	poi.y = 1;
        	poi.z = 0;
            break;
        case 22:
        	poi.x = 0;
        	poi.y = 1;
        	poi.z = 0;
            break;
        case 23:
        	poi.x = 1;
        	poi.y = 1;
        	poi.z =0;
            break;
        case 24:
        	poi.x = -1;
        	poi.y = 1;
        	poi.z = 1;
            break;
        case 25:
        	poi.x = 0;
        	poi.y = 1;
        	poi.z =1;
            break;
        case 26:
        	poi.x = 1;
        	poi.y = 1;
        	poi.z = 1;
            break;
    }

    
    }

    
    private int GetBlockIndexFromCube(ST_DreamCube dreamcube, int iIndex)
    {
    	ST_Position poi = new ST_Position();

        int i = 0;

        GetCoordinateByIndex(iIndex, poi);

        for (i = 0; i <= 26; i++)
        {
        	if (poi.Equals(dreamcube.cube_status[i].Position))
        	{
        		return i;
        	}
        	/*
            if (dreamcube.cube_status[i].Position.x == poi.x
                && dreamcube.cube_status[i].Position.y == poi.y
                && dreamcube.cube_status[i].Position.z == poi.z)
            {
                return i;
            }
            */
        }
        System.out.println("Get index failure in GetBlockIndexFromCube()");
        return i;
    }

    private int GetBlockIndexByColor(E_COLOR eColor0, E_COLOR eColor1, E_COLOR eColor2, E_COLOR eColor3, E_COLOR eColor4, E_COLOR eColor5)
    {
        int iBlockIndex = 0;
        int iColorIndex = 0;
        E_COLOR eColor = E_COLOR.NOCOLOR;

        int i = 0;

        for (i = 0; i < 6; i++)
        {
            switch (i)
            { 
                case 0:
                    eColor = eColor0;
                    break;
                case 1:
                    eColor = eColor1;
                    break;
                case 2:
                    eColor = eColor2;
                    break;
                case 3:
                    eColor = eColor3;
                    break;
                case 4:
                    eColor = eColor4;
                    break;
                case 5:
                    eColor = eColor5;
                    break;
            }
            if (eColor == E_COLOR.WHITE)
            {
                iColorIndex += 1;
            }
            else if (eColor == E_COLOR.ORANGE)
            {
                iColorIndex += 2;
            }
            else if (eColor == E_COLOR.YELLOW)
            {
                iColorIndex += 4;
            }
            else if (eColor == E_COLOR.BLUE)
            {
                iColorIndex += 8;
            }
            else if (eColor == E_COLOR.GREEN)
            {
                iColorIndex += 16;
            }
            else if (eColor == E_COLOR.RED)
            {
                iColorIndex += 32;
            }
        }

        if (iColorIndex == 49)
        {
            iBlockIndex = 0;
        }
        else if (iColorIndex == 48)
        {
            iBlockIndex = 1;
        }
        else if (iColorIndex == 52)
        {
            iBlockIndex = 2;
        }
        else if (iColorIndex == 33)
        {
            iBlockIndex = 3;
        }
        else if (iColorIndex == 32)
        {
            iBlockIndex = 4;
        }
        else if (iColorIndex == 36)
        {
            iBlockIndex = 5;
        }
        else if (iColorIndex == 41)
        {
            iBlockIndex = 6;
        }
        else if (iColorIndex == 40)
        {
            iBlockIndex = 7;
        }
        else if (iColorIndex == 44)
        {
            iBlockIndex = 8;
        }
        else if (iColorIndex == 17)
        {
            iBlockIndex = 9;
        }
        else if (iColorIndex == 16)
        {
            iBlockIndex = 10;
        }
        else if (iColorIndex == 20)
        {
            iBlockIndex = 11;
        }
        else if (iColorIndex == 1)
        {
            iBlockIndex = 12;
        }
        else if (iColorIndex == 0)
        {
            iBlockIndex = 13;
        }
        else if (iColorIndex == 4)
        {
            iBlockIndex = 14;
        }
        else if (iColorIndex == 9)
        {
            iBlockIndex = 15;
        }
        else if (iColorIndex == 8)
        {
            iBlockIndex = 16;
        }
        else if (iColorIndex == 12)
        {
            iBlockIndex = 17;
        }
        else if (iColorIndex == 19)
        {
            iBlockIndex = 18;
        }
        else if (iColorIndex == 18)
        {
            iBlockIndex = 19;
        }
        else if (iColorIndex == 22)
        {
            iBlockIndex = 20;
        }
        else if (iColorIndex == 3)
        {
            iBlockIndex = 21;
        }
        else if (iColorIndex == 2)
        {
            iBlockIndex = 22;
        }
        else if (iColorIndex == 6)
        {
            iBlockIndex = 23;
        }
        else if (iColorIndex == 11)
        {
            iBlockIndex = 24;
        }
        else if (iColorIndex == 10)
        {
            iBlockIndex = 25;
        }
        else if (iColorIndex == 14)
        {
            iBlockIndex = 26;
        }
        else
        {
            System.out.println("Get index failure in GetBlockIndexByColor()");
        }

        return iBlockIndex;
    }
    
    protected int GetBlockIndexByCoordinate(int iX, int iY, int iZ, ST_DreamCube dreamcube)
    { 
    	int i = 0;
    	for(i = 0; i <= 26; i++)
    	{
    		if((dreamcube.cube_status[i].Position.x == iX)
	    		&& (dreamcube.cube_status[i].Position.y == iY)
	    		&& (dreamcube.cube_status[i].Position.z == iZ))
    		{
    			break;
    		}
    	}
    	return i;
    }

    /*根据标准魔方的坐标获取方块索引*/
    protected int GetBlockIndexByCoordinate(int iX, int iY, int iZ)
    {
        int iIndex = 0;
        if (iX == -1 && iY == -1 && iZ == -1)
        {
            iIndex = 0;
        }
        else if (iX == 0 && iY == -1 && iZ == -1)
        {
            iIndex = 1;
        }
        else if (iX == 1 && iY == -1 && iZ == -1)
        {
            iIndex = 2;
        }
        else if (iX == -1 && iY == -1 && iZ == 0)
        {
            iIndex = 3;
        }
        else if (iX == 0 && iY == -1 && iZ == 0)
        {
            iIndex = 4;
        }
        else if (iX == 1 && iY == -1 && iZ == 0)
        {
            iIndex = 5;
        }
        else if (iX == -1 && iY == -1 && iZ == 1)
        {
            iIndex = 6;
        }
        else if (iX == 0 && iY == -1 && iZ == 1)
        {
            iIndex = 7;
        }
        else if (iX == 1 && iY == -1 && iZ == 1)
        {
            iIndex = 8;
        }
        else if (iX == -1 && iY == 0 && iZ == -1)
        {
            iIndex = 9;
        }
        else if (iX == 0 && iY == 0 && iZ == -1)
        {
            iIndex = 10;
        }
        else if (iX == 1 && iY == 0 && iZ == -1)
        {
            iIndex = 11;
        }
        else if (iX == -1 && iY == 0 && iZ == 0)
        {
            iIndex = 12;
        }
        else if (iX == 0 && iY == 0 && iZ == 0)
        {
            iIndex = 13;
        }
        else if (iX == 1 && iY == 0 && iZ == 0)
        {
            iIndex = 14;
        }
        else if (iX == -1 && iY == 0 && iZ == 1)
        {
            iIndex = 15;
        }
        else if (iX == 0 && iY == 0 && iZ == 1)
        {
            iIndex = 16;
        }
        else if (iX == 1 && iY == 0 && iZ == 1)
        {
            iIndex = 17;
        }
        else if (iX == -1 && iY == 1 && iZ == -1)
        {
            iIndex = 18;
        }
        else if (iX == 0 && iY == 1 && iZ == -1)
        {
            iIndex = 19;
        }
        else if (iX == 1 && iY == 1 && iZ == -1)
        {
            iIndex = 20;
        }
        else if (iX == -1 && iY == 1 && iZ == 0)
        {
            iIndex = 21;
        }
        else if (iX == 0 && iY == 1 && iZ == 0)
        {
            iIndex = 22;
        }
        else if (iX == 1 && iY == 1 && iZ == 0)
        {
            iIndex = 23;
        }
        else if (iX == -1 && iY == 1 && iZ == 1)
        {
            iIndex = 24;
        }
        else if (iX == 0 && iY == 1 && iZ == 1)
        {
            iIndex = 25;
        }
        else if (iX == 1 && iY == 1 && iZ == 1)
        {
            iIndex = 26;
        }
        return iIndex;
    }

    public void XChgDreamCube4Caculate(ST_DreamCube dreamcube) throws CloneNotSupportedException
    {
        int iBlock = 0;
        ST_Color stColor = new ST_Color();        
        ST_Position poi = new ST_Position();

        for (int i = 0; i <= 26; i++)
        {
            stColor = dreamcube.cube_status[i].Color.clone();
            iBlock = GetBlockIndexByColor(stColor.U, stColor.D, stColor.F, stColor.B, stColor.L, stColor.R);
            GetCoordinateByIndex(i, poi);
            DREAM_CUBE_CACULATE.cube_status[iBlock].Color = stColor.clone();
            DREAM_CUBE_CACULATE.cube_status[iBlock].Position = poi.clone();
//            DREAM_CUBE_CACULATE.cube_status[iBlock].Position.x = poi.x;
//            DREAM_CUBE_CACULATE.cube_status[iBlock].Position.y = poi.y;
//            DREAM_CUBE_CACULATE.cube_status[iBlock].Position.z = poi.z;

            switch (i)
            { 
                case 0:
                    DREAM_CUBE_CACULATE.Plate.D[2][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[2][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[2][2] = iBlock;
                    break;
                case 1:
                    DREAM_CUBE_CACULATE.Plate.D[2][1] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[2][1] = iBlock;
                    break;
                case 2:
                    DREAM_CUBE_CACULATE.Plate.D[2][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[2][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[2][0] = iBlock;
                    break;
                case 3:
                    DREAM_CUBE_CACULATE.Plate.D[1][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[2][1] = iBlock;
                    break;
                case 4:
                    DREAM_CUBE_CACULATE.Plate.D[1][1] = iBlock;
                    break;
                case 5:
                    DREAM_CUBE_CACULATE.Plate.D[1][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[2][1] = iBlock;
                    break;
                case 6:
                    DREAM_CUBE_CACULATE.Plate.D[0][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[2][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[2][0] = iBlock;
                    break;
                case 7:
                    DREAM_CUBE_CACULATE.Plate.D[0][1] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[2][1] = iBlock;
                    break;
                case 8:
                    DREAM_CUBE_CACULATE.Plate.D[0][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[2][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[2][0] = iBlock;
                    break;
                case 9:
                    DREAM_CUBE_CACULATE.Plate.L[1][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[1][2] = iBlock;
                    break;
                case 10:
                    DREAM_CUBE_CACULATE.Plate.B[1][1] = iBlock;
                    break;
                case 11:
                    DREAM_CUBE_CACULATE.Plate.R[1][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[1][0] = iBlock;
                    break;
                case 12:
                    DREAM_CUBE_CACULATE.Plate.L[1][1] = iBlock;
                    break;
                case 13:
                    break;
                case 14:
                    DREAM_CUBE_CACULATE.Plate.R[1][1] = iBlock;
                    break;
                case 15:
                    DREAM_CUBE_CACULATE.Plate.L[1][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[1][0] = iBlock;
                    break;
                case 16:
                    DREAM_CUBE_CACULATE.Plate.F[1][1] = iBlock;
                    break;
                case 17:
                    DREAM_CUBE_CACULATE.Plate.R[1][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[1][2] = iBlock;
                    break;
                case 18:
                    DREAM_CUBE_CACULATE.Plate.U[0][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[0][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[0][2] = iBlock;
                    break;
                case 19:
                    DREAM_CUBE_CACULATE.Plate.U[0][1] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[0][1] = iBlock;
                    break;
                case 20:
                    DREAM_CUBE_CACULATE.Plate.U[0][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[0][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.B[0][0] = iBlock;
                    break;
                case 21:
                    DREAM_CUBE_CACULATE.Plate.U[1][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[0][1] = iBlock;
                    break;
                case 22:
                    DREAM_CUBE_CACULATE.Plate.U[1][1] = iBlock;
                    break;
                case 23:
                    DREAM_CUBE_CACULATE.Plate.U[1][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[0][1] = iBlock;
                    break;
                case 24:
                    DREAM_CUBE_CACULATE.Plate.U[2][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.L[0][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[0][0] = iBlock;
                    break;
                case 25:
                    DREAM_CUBE_CACULATE.Plate.U[2][1] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[0][1] = iBlock;
                    break;
                case 26:
                    DREAM_CUBE_CACULATE.Plate.U[2][2] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.R[0][0] = iBlock;
                    DREAM_CUBE_CACULATE.Plate.F[0][2] = iBlock;
                    break;
            }
        }
    }

    
    
    /*根据每个方块的坐标值，得到当前魔方的各种属性的数据，用来检测用*/
    protected void XChgDreamCube4Check()
    {
    	try {
			DREAM_CUBE_CHECKSTATUS = DREAM_CUBE_CACULATE.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    /*根据每个方块的坐标值，得到当前魔方的各种属性的数据，用来显示*/
    public void XChgDreamCube4Display(ST_DreamCube dreamcube) 
    {
        int i = 0;
        int iBlock = 0;
        int iX = 0;
        int iY = 0;
        int iZ = 0;

        for (i = 0; i < 27; i++)
        {
            iX = dreamcube.cube_status[i].Position.x;
            iY = dreamcube.cube_status[i].Position.y;
            iZ = dreamcube.cube_status[i].Position.z;

            iBlock = GetBlockIndexByCoordinate(iX, iY, iZ);

            /*MAGIC_CUBE按index保存方块的信息，不管魔方如何变化，MAGIC_CUBE按索引查到的颜色不会变
             但颜色所在面会改变，坐标也会变化，面数据上保存了当前面所拥有的方块
             
             MAGIC_CUBE_DISPLAY保存了变化后的数据，主要用来显示。
             对应索引位置上的方块已经不是初始的方块，而是魔方变换后的方块
             */
            DREAM_CUBE_DISPLAY.cube_status[iBlock] = dreamcube.cube_status[i];
        }


        /*某个面所拥有的方块，如D面，可能已经不是计算所用的D面*/
        //显示时没有用到面数据
        //MAGIC_CUBE_DISPLAY.Plate = dreamcube.Plate;     //这样赋值其实是引用了dreamcube的数据
    }


    /*变换面上的方块索引*/
    private void XChgBlockIndex(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate, Boolean bClockWise)
    {
        if (bClockWise)
        {
            XChgBlockIndex_ClockWise(dreamcube, ePlate, aiPlate);
        }
        else
        {
            XChgBlockIndex_UnClockWise(dreamcube, ePlate, aiPlate);
        }

    }

    /*变换面上的方块索引*/
    private void XChgBlockIndex_ClockWise(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate)
    {
        int iBlock = 0;
        iBlock = aiPlate[0][0];

        aiPlate[0][0] = aiPlate[2][0];
        aiPlate[2][0] = aiPlate[2][2];
        aiPlate[2][2] = aiPlate[0][2];
        aiPlate[0][2] = iBlock;

        iBlock = aiPlate[0][1];
        aiPlate[0][1] = aiPlate[1][0];
        aiPlate[1][0] = aiPlate[2][1];
        aiPlate[2][1] = aiPlate[1][2];
        aiPlate[1][2] = iBlock;

        XChgOtherBlockIndex(dreamcube, ePlate);
    }


    private void XChgBlockIndex_UnClockWise(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate)
    {
        int iBlock = 0;
        iBlock = aiPlate[0][0];

        aiPlate[0][0] = aiPlate[0][2];
        aiPlate[0][2] = aiPlate[2][2];
        aiPlate[2][2] = aiPlate[2][0];
        aiPlate[2][0] = iBlock;

        iBlock = aiPlate[0][1];
        aiPlate[0][1] = aiPlate[1][2];
        aiPlate[1][2] = aiPlate[2][1];
        aiPlate[2][1] = aiPlate[1][0];
        aiPlate[1][0] = iBlock;

        XChgOtherBlockIndex(dreamcube, ePlate);

    }


    private void XChgBlockIndex(ST_DreamCube dreamcube, E_PLATE ePlate, Boolean bClockWise)
    {
        int iIndex;
        switch (ePlate)
        {
            case X:
                if (bClockWise)
                {
                    iIndex = dreamcube.Plate.F[0][1];
                    dreamcube.Plate.F[0][1] = dreamcube.Plate.U[2][1] = dreamcube.Plate.D[0][1];
                    dreamcube.Plate.D[0][1] = dreamcube.Plate.F[2][1] = dreamcube.Plate.B[2][1];
                    dreamcube.Plate.B[2][1] = dreamcube.Plate.D[2][1] = dreamcube.Plate.U[0][1];
                    dreamcube.Plate.U[0][1] = dreamcube.Plate.B[0][1] = iIndex;

                    iIndex = dreamcube.Plate.F[1][1];
                    dreamcube.Plate.F[1][1] = dreamcube.Plate.D[1][1];
                    dreamcube.Plate.D[1][1] = dreamcube.Plate.B[1][1];
                    dreamcube.Plate.B[1][1] = dreamcube.Plate.U[1][1];
                    dreamcube.Plate.U[1][1] = iIndex;
                }
                else
                {
                    iIndex = dreamcube.Plate.F[0][1];
                    dreamcube.Plate.F[0][1] = dreamcube.Plate.U[2][1] = dreamcube.Plate.U[0][1];
                    dreamcube.Plate.U[0][1] = dreamcube.Plate.B[0][1] = dreamcube.Plate.B[2][1];
                    dreamcube.Plate.B[2][1] = dreamcube.Plate.D[2][1] = dreamcube.Plate.D[0][1];
                    dreamcube.Plate.D[0][1] = dreamcube.Plate.F[2][1] = iIndex;

                    iIndex = dreamcube.Plate.F[1][1];
                    dreamcube.Plate.F[1][1] = dreamcube.Plate.U[1][1];
                    dreamcube.Plate.U[1][1] = dreamcube.Plate.B[1][1];
                    dreamcube.Plate.B[1][1] = dreamcube.Plate.D[1][1];
                    dreamcube.Plate.D[1][1] = iIndex;
                }
                break;
            case Y:
                if (bClockWise)
                {
                    iIndex = dreamcube.Plate.F[1][0];
                    dreamcube.Plate.F[1][0] = dreamcube.Plate.L[1][2] = dreamcube.Plate.R[1][0];
                    dreamcube.Plate.R[1][0] = dreamcube.Plate.F[1][2] = dreamcube.Plate.B[1][0];
                    dreamcube.Plate.B[1][0] = dreamcube.Plate.R[1][2] = dreamcube.Plate.L[1][0];
                    dreamcube.Plate.L[1][0] = dreamcube.Plate.B[1][2] = iIndex;

                    iIndex = dreamcube.Plate.F[1][1];
                    dreamcube.Plate.F[1][1] = dreamcube.Plate.R[1][1];
                    dreamcube.Plate.R[1][1] = dreamcube.Plate.B[1][1];
                    dreamcube.Plate.B[1][1] = dreamcube.Plate.L[1][1];
                    dreamcube.Plate.L[1][1] = iIndex;
                }
                else
                {
                    iIndex = dreamcube.Plate.F[1][0];
                    dreamcube.Plate.F[1][0] = dreamcube.Plate.L[1][2] = dreamcube.Plate.L[1][0];
                    dreamcube.Plate.L[1][0] = dreamcube.Plate.B[1][2] = dreamcube.Plate.B[1][0];
                    dreamcube.Plate.B[1][0] = dreamcube.Plate.R[1][2] = dreamcube.Plate.R[1][0];
                    dreamcube.Plate.R[1][0] = dreamcube.Plate.F[1][2] = iIndex;

                    iIndex = dreamcube.Plate.F[1][1];
                    dreamcube.Plate.F[1][1] = dreamcube.Plate.L[1][1];
                    dreamcube.Plate.L[1][1] = dreamcube.Plate.B[1][1];
                    dreamcube.Plate.B[1][1] = dreamcube.Plate.R[1][1];
                    dreamcube.Plate.R[1][1] = iIndex;                    
                }
                break;
            case Z:
                if (bClockWise)
                {
                    iIndex = dreamcube.Plate.R[0][1];
                    dreamcube.Plate.R[0][1] = dreamcube.Plate.U[1][2] = dreamcube.Plate.U[1][0];
                    dreamcube.Plate.U[1][0] = dreamcube.Plate.L[0][1] = dreamcube.Plate.L[2][1];
                    dreamcube.Plate.L[2][1] = dreamcube.Plate.D[1][0] = dreamcube.Plate.D[1][2];
                    dreamcube.Plate.D[1][2] = dreamcube.Plate.R[2][1] = iIndex;

                    iIndex = dreamcube.Plate.R[1][1];
                    dreamcube.Plate.R[1][1] = dreamcube.Plate.U[1][1];
                    dreamcube.Plate.U[1][1] = dreamcube.Plate.L[1][1];
                    dreamcube.Plate.L[1][1] = dreamcube.Plate.D[1][1];
                    dreamcube.Plate.D[1][1] = iIndex;
                }
                else
                {
                    iIndex = dreamcube.Plate.R[0][1];
                    dreamcube.Plate.R[0][1] = dreamcube.Plate.U[1][2] = dreamcube.Plate.D[1][2];
                    dreamcube.Plate.D[1][2] = dreamcube.Plate.R[2][1] = dreamcube.Plate.L[2][1];
                    dreamcube.Plate.L[2][1] = dreamcube.Plate.D[1][0] = dreamcube.Plate.U[1][0];
                    dreamcube.Plate.U[1][0] = dreamcube.Plate.L[0][1] = iIndex;

                    iIndex = dreamcube.Plate.R[1][1];
                    dreamcube.Plate.R[1][1] = dreamcube.Plate.D[1][1];
                    dreamcube.Plate.D[1][1] = dreamcube.Plate.L[1][1];
                    dreamcube.Plate.L[1][1] = dreamcube.Plate.U[1][1];
                    dreamcube.Plate.U[1][1] = iIndex;
                }
                break;
        }
    }


    /*将新位置的坐标赋给老位置坐标*/
    private void XChgCoordinate(ST_DreamCube dreamcube, int iOld, int iNew) throws CloneNotSupportedException
    {
        dreamcube.cube_status[iOld].Position = dreamcube.cube_status[iNew].Position.clone();
        //dreamcube.cube_status[iOld].Position.y = dreamcube.cube_status[iNew].Position.y;
        //dreamcube.cube_status[iOld].Position.z = dreamcube.cube_status[iNew].Position.z;
    }

    private void XChgCoordinate(ST_DreamCube dreamcube, int iOld, ST_Position stCB) throws CloneNotSupportedException
    {
        dreamcube.cube_status[iOld].Position = stCB.clone();
//        dreamcube.cube_status[iOld].Position.y = stCB.y;
//        dreamcube.cube_status[iOld].Position.z = stCB.z;
    }

    /*这里对某个面上的方块的坐标进行变换*/
    private void XChgPlateCoordinate(ST_DreamCube dreamcube, int[][] aiPlate, Boolean bClockWise)
    {
        if (bClockWise)
        {
            XChgPlateCoordinate_ClockWise(dreamcube, aiPlate);
        }
        else
        {
            XChgPlateCoordinate_UnClockWise(dreamcube, aiPlate);
        }
    }

    private void XChgPlateCoordinate_ClockWise(ST_DreamCube dreamcube, int[][] aiPlate)
    {
    	try {
    	ST_Position stCb = null;
        int i = 0;

        i = aiPlate[0][0];
        stCb = dreamcube.cube_status[i].Position.clone();
        XChgCoordinate(dreamcube, aiPlate[0][0], aiPlate[0][2]);
        XChgCoordinate(dreamcube, aiPlate[0][2], aiPlate[2][2]);
        XChgCoordinate(dreamcube, aiPlate[2][2], aiPlate[2][0]);
        XChgCoordinate(dreamcube, aiPlate[2][0], stCb);

        i = aiPlate[0][1];
        stCb = dreamcube.cube_status[i].Position.clone();
        XChgCoordinate(dreamcube, aiPlate[0][1], aiPlate[1][2]);
        XChgCoordinate(dreamcube, aiPlate[1][2], aiPlate[2][1]);
        XChgCoordinate(dreamcube, aiPlate[2][1], aiPlate[1][0]);
        XChgCoordinate(dreamcube, aiPlate[1][0], stCb);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private void XChgPlateCoordinate_UnClockWise(ST_DreamCube dreamcube, int[][] aiPlate)
    {
    	try {
    	ST_Position stCb = null;
        int i = 0;

        i = aiPlate[0][0];
        stCb = dreamcube.cube_status[i].Position.clone();
        XChgCoordinate(dreamcube, aiPlate[0][0], aiPlate[2][0]);
        XChgCoordinate(dreamcube, aiPlate[2][0], aiPlate[2][2]);
        XChgCoordinate(dreamcube, aiPlate[2][2], aiPlate[0][2]);
        XChgCoordinate(dreamcube, aiPlate[0][2], stCb);

        i = aiPlate[0][1];
        stCb = dreamcube.cube_status[i].Position.clone();
        XChgCoordinate(dreamcube, aiPlate[0][1], aiPlate[1][0]);
        XChgCoordinate(dreamcube, aiPlate[1][0], aiPlate[2][1]);
        XChgCoordinate(dreamcube, aiPlate[2][1], aiPlate[1][2]);
        XChgCoordinate(dreamcube, aiPlate[1][2], stCb);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private void XChgPlateCoordinate(ST_DreamCube dreamcube, E_PLATE ePlate, Boolean bClockWise)
    {
    	try {
        ST_Position stCb = null;
        int i = 0;

        switch (ePlate)
        {
            case X:
                if (bClockWise)
                {
                    i = dreamcube.Plate.B[2][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[2][1], dreamcube.Plate.D[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[0][1], dreamcube.Plate.F[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[0][1], dreamcube.Plate.U[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[0][1], stCb);

                    i = dreamcube.Plate.B[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][1], dreamcube.Plate.D[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][1], dreamcube.Plate.F[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][1], dreamcube.Plate.U[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][1], stCb);
                }
                else
                {
                    i = dreamcube.Plate.B[2][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[2][1], dreamcube.Plate.U[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[0][1], dreamcube.Plate.F[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[0][1], dreamcube.Plate.D[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[0][1], stCb);

                    i = dreamcube.Plate.B[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][1], dreamcube.Plate.U[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][1], dreamcube.Plate.F[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][1], dreamcube.Plate.D[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][1], stCb);
                }
                break;
            case Y:
                if (bClockWise)
                {
                    i = dreamcube.Plate.F[1][0];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][0], dreamcube.Plate.L[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][0], dreamcube.Plate.B[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][0], dreamcube.Plate.R[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][0], stCb);

                    i = dreamcube.Plate.F[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][1], dreamcube.Plate.L[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][1], dreamcube.Plate.B[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][1], dreamcube.Plate.R[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][1], stCb);
                }
                else
                {
                    i = dreamcube.Plate.F[1][0];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][0], dreamcube.Plate.R[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][0], dreamcube.Plate.B[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][0], dreamcube.Plate.L[1][0]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][0], stCb);

                    i = dreamcube.Plate.F[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.F[1][1], dreamcube.Plate.R[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][1], dreamcube.Plate.B[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.B[1][1], dreamcube.Plate.L[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][1], stCb);
                }
                break;
            case Z:
                if (bClockWise)
                {
                    i = dreamcube.Plate.U[1][0];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][0], dreamcube.Plate.R[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[0][1], dreamcube.Plate.D[1][2]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][2], dreamcube.Plate.L[2][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[2][1], stCb);

                    i = dreamcube.Plate.U[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][1], dreamcube.Plate.R[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][1], dreamcube.Plate.D[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][1], dreamcube.Plate.L[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][1], stCb);
                }
                else
                {
                    i = dreamcube.Plate.U[1][0];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][0], dreamcube.Plate.L[2][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[2][1], dreamcube.Plate.D[1][2]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][2], dreamcube.Plate.R[0][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[0][1], stCb);

                    i = dreamcube.Plate.U[1][1];
                    stCb = dreamcube.cube_status[i].Position.clone();
                    XChgCoordinate(dreamcube, dreamcube.Plate.U[1][1], dreamcube.Plate.L[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.L[1][1], dreamcube.Plate.D[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.D[1][1], dreamcube.Plate.R[1][1]);
                    XChgCoordinate(dreamcube, dreamcube.Plate.R[1][1], stCb);
                }
                break;
        }
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void XChgColor(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate, Boolean bClockWise)
    {
        if (bClockWise)
        {
            XChgColor_ClockWise(dreamcube, ePlate, aiPlate);
        }
        else
        {
            XChgColor_UnClockWise(dreamcube, ePlate, aiPlate);
        }
    }

    private void XChgColor_ClockWise(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate)
    {
    	try {
        int i = 0;
    	ST_Color stCC = null;

        switch (ePlate)
        {
            case UP:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case DOWN:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case FRONT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case BACK:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case LEFT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.U = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case RIGHT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.F;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.F;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.F;
                stCC = null;
                break;
            default:
                break;
        }
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private void XChgColor_UnClockWise(ST_DreamCube dreamcube, E_PLATE ePlate, int[][] aiPlate)
    {
    	try {
    	int i = 0;
        ST_Color stCC = null;

        switch (ePlate)
        {
            case UP:
            	i = aiPlate[0][0];
				stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.B;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.R;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.F;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.U = stCC.U;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.L;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case DOWN:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.B;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.D = stCC.D;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.L;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case FRONT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.U;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.R;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.D;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.F = stCC.F;
                dreamcube.cube_status[i].Color.D = stCC.L;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            case BACK:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = stCC.U;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.L;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = stCC.D;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.B = stCC.B;
                dreamcube.cube_status[i].Color.D = stCC.R;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
                break;
            case LEFT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.U = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.U;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.F;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.D;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.L = stCC.L;
                dreamcube.cube_status[i].Color.D = stCC.B;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                stCC = null;
                
                break;
            case RIGHT:
            	i = aiPlate[0][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[0][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.B;
                stCC = null;
                
            	i = aiPlate[2][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.B;
                stCC = null;
                
            	i = aiPlate[2][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                

                /*************************/

            	i = aiPlate[0][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = stCC.U;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][2];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = stCC.B;
                stCC = null;
                
            	i = aiPlate[2][1];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = stCC.D;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                
            	i = aiPlate[1][0];
            	stCC = dreamcube.cube_status[i].Color.clone();
                dreamcube.cube_status[i].Color.R = stCC.R;
                dreamcube.cube_status[i].Color.D = stCC.F;
                dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                stCC = null;
                break;
            default:
                break;
        }
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    private void XChgColor(ST_DreamCube dreamcube, E_PLATE ePlate, Boolean bClockWise)
    {
    	try {
        ST_Color stCC = null;
        int i = 0;
        switch(ePlate)
        {
            case X:
                if (bClockWise)
                {
                    i = GetBlockIndexFromCube(dreamcube, 1);
                	
					stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.B;
                    dreamcube.cube_status[i].Color.F = stCC.D;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 4);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.D;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 7);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.F;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.D;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 16);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.F;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 25);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.F;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.U;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 22);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.U;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 19);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.B;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.U;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 10);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.B;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                }
                else
                {
                    i = GetBlockIndexFromCube(dreamcube, 1);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.B;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.D;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 10);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.B;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 19);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.B;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.U;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 22);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.U;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 25);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.F;
                    dreamcube.cube_status[i].Color.F = stCC.U;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 16);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.F;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 7);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.F;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.D;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 4);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.D;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                }
                break;
            case Y:
                if (bClockWise)
                {
                    i = GetBlockIndexFromCube(dreamcube, 9);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.L;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.B;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 10);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.B;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 11);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.R;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.B;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 12);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.L;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 14);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.R;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 15);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.L;
                    dreamcube.cube_status[i].Color.L = stCC.F;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 16);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.F;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 17);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.R;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.F;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                }
                else
                {
                    i = GetBlockIndexFromCube(dreamcube, 9);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.L;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.B;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 10);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.B;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 11);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.R;
                    dreamcube.cube_status[i].Color.L = stCC.B;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 12);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.L;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 14);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.R;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 15);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = stCC.L;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.F;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 16);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.F;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 17);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = stCC.R;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.F;
                    stCC = null;
                }
                break;
            case Z:
                if (bClockWise)
                {
                    i = GetBlockIndexFromCube(dreamcube, 3);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.L;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.D;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 12);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.L;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 21);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.L;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.U;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 22);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.U;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 23);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.R;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.U;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 14);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.R;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 5);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.R;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.D;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 4);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.D;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                }
                else
                {
                    i = GetBlockIndexFromCube(dreamcube, 3);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.L;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.D;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 4);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.D;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 5);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.R;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = stCC.D;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 14);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.R;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 23);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = stCC.R;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.U;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 22);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.U;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 21);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.L;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = stCC.U;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                    
                    i = GetBlockIndexFromCube(dreamcube, 12);
                    stCC = dreamcube.cube_status[i].Color.clone();
                    dreamcube.cube_status[i].Color.U = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.D = stCC.L;
                    dreamcube.cube_status[i].Color.F = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.B = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.L = E_COLOR.NOCOLOR;
                    dreamcube.cube_status[i].Color.R = E_COLOR.NOCOLOR;
                    stCC = null;
                }
                break;
            default:
                break;
        }
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private void XChgOtherBlockIndex(ST_DreamCube dreamcube, E_PLATE cPlate)
    {
        switch (cPlate)
        {
            case UP:
                dreamcube.Plate.L[0][0] = dreamcube.Plate.B[0][2] = dreamcube.Plate.U[0][0];
                dreamcube.Plate.L[0][2] = dreamcube.Plate.F[0][0] = dreamcube.Plate.U[2][0];
                dreamcube.Plate.R[0][0] = dreamcube.Plate.F[0][2] = dreamcube.Plate.U[2][2];
                dreamcube.Plate.R[0][2] = dreamcube.Plate.B[0][0] = dreamcube.Plate.U[0][2];
                dreamcube.Plate.B[0][1] = dreamcube.Plate.U[0][1];
                dreamcube.Plate.L[0][1] = dreamcube.Plate.U[1][0];
                dreamcube.Plate.R[0][1] = dreamcube.Plate.U[1][2];
                dreamcube.Plate.F[0][1] = dreamcube.Plate.U[2][1];
                break;
            case DOWN:
                dreamcube.Plate.L[2][0] = dreamcube.Plate.B[2][2] = dreamcube.Plate.D[2][0];
                dreamcube.Plate.L[2][2] = dreamcube.Plate.F[2][0] = dreamcube.Plate.D[0][0];
                dreamcube.Plate.R[2][0] = dreamcube.Plate.F[2][2] = dreamcube.Plate.D[0][2];
                dreamcube.Plate.R[2][2] = dreamcube.Plate.B[2][0] = dreamcube.Plate.D[2][2];
                dreamcube.Plate.B[2][1] = dreamcube.Plate.D[2][1];
                dreamcube.Plate.L[2][1] = dreamcube.Plate.D[1][0];
                dreamcube.Plate.R[2][1] = dreamcube.Plate.D[1][2];
                dreamcube.Plate.F[2][1] = dreamcube.Plate.D[0][1];
                break;
            case FRONT:
                dreamcube.Plate.L[0][2] = dreamcube.Plate.U[2][0] = dreamcube.Plate.F[0][0];
                dreamcube.Plate.R[0][0] = dreamcube.Plate.U[2][2] = dreamcube.Plate.F[0][2];
                dreamcube.Plate.L[2][2] = dreamcube.Plate.D[0][0] = dreamcube.Plate.F[2][0];
                dreamcube.Plate.R[2][0] = dreamcube.Plate.D[0][2] = dreamcube.Plate.F[2][2];
                dreamcube.Plate.L[1][2] = dreamcube.Plate.F[1][0];
                dreamcube.Plate.R[1][0] = dreamcube.Plate.F[1][2];
                dreamcube.Plate.U[2][1] = dreamcube.Plate.F[0][1];
                dreamcube.Plate.D[0][1] = dreamcube.Plate.F[2][1];
                break;
            case BACK:
                dreamcube.Plate.L[0][0] = dreamcube.Plate.U[0][0] = dreamcube.Plate.B[0][2];
                dreamcube.Plate.R[0][2] = dreamcube.Plate.U[0][2] = dreamcube.Plate.B[0][0];
                dreamcube.Plate.D[2][0] = dreamcube.Plate.L[2][0] = dreamcube.Plate.B[2][2];
                dreamcube.Plate.D[2][2] = dreamcube.Plate.R[2][2] = dreamcube.Plate.B[2][0];
                dreamcube.Plate.U[0][1] = dreamcube.Plate.B[0][1];
                dreamcube.Plate.R[1][2] = dreamcube.Plate.B[1][0];
                dreamcube.Plate.L[1][0] = dreamcube.Plate.B[1][2];
                dreamcube.Plate.D[2][1] = dreamcube.Plate.B[2][1];
                break;
            case LEFT:
                dreamcube.Plate.B[0][2] = dreamcube.Plate.U[0][0] = dreamcube.Plate.L[0][0];
                dreamcube.Plate.F[0][0] = dreamcube.Plate.U[2][0] = dreamcube.Plate.L[0][2];
                dreamcube.Plate.B[2][2] = dreamcube.Plate.D[2][0] = dreamcube.Plate.L[2][0];
                dreamcube.Plate.F[2][0] = dreamcube.Plate.D[0][0] = dreamcube.Plate.L[2][2];
                dreamcube.Plate.U[1][0] = dreamcube.Plate.L[0][1];
                dreamcube.Plate.F[1][0] = dreamcube.Plate.L[1][2];
                dreamcube.Plate.B[1][2] = dreamcube.Plate.L[1][0];
                dreamcube.Plate.D[1][0] = dreamcube.Plate.L[2][1];
                break;
            case RIGHT:
                dreamcube.Plate.B[0][0] = dreamcube.Plate.U[0][2] = dreamcube.Plate.R[0][2];
                dreamcube.Plate.F[0][2] = dreamcube.Plate.U[2][2] = dreamcube.Plate.R[0][0];
                dreamcube.Plate.F[2][2] = dreamcube.Plate.D[0][2] = dreamcube.Plate.R[2][0];
                dreamcube.Plate.D[2][2] = dreamcube.Plate.B[2][0] = dreamcube.Plate.R[2][2];
                dreamcube.Plate.U[1][2] = dreamcube.Plate.R[0][1];
                dreamcube.Plate.F[1][2] = dreamcube.Plate.R[1][0];
                dreamcube.Plate.B[1][0] = dreamcube.Plate.R[1][2];
                dreamcube.Plate.D[1][2] = dreamcube.Plate.R[2][1];
                break;
            default:
                break;
        }
    }

    private void XChg(ST_DreamCube dreamcube, E_ROTATIONEVENT eRotation)
    {
        E_ROTATIONEVENT eRotationFirst = eRotation;
        E_ROTATIONEVENT eRotationSecond = E_ROTATIONEVENT.NULL;

        switch (eRotation)
        {
        	case DU:
                eRotationFirst = E_ROTATIONEVENT.U;
                eRotationSecond = E_ROTATIONEVENT.Y;
                break;        	
        	case Du:
                eRotationFirst = E_ROTATIONEVENT.u;
                eRotationSecond = E_ROTATIONEVENT.y;
                break;
        	case DD:
                eRotationFirst = E_ROTATIONEVENT.D;
                eRotationSecond = E_ROTATIONEVENT.y;
                break;
        	case Dd:
                eRotationFirst = E_ROTATIONEVENT.d;
                eRotationSecond = E_ROTATIONEVENT.Y;
                break;
        	case DF:
                eRotationFirst = E_ROTATIONEVENT.F;
                eRotationSecond = E_ROTATIONEVENT.Z;
                break;
        	case Df:
                eRotationFirst = E_ROTATIONEVENT.f;
                eRotationSecond = E_ROTATIONEVENT.z;
                break;
        	case DB:
                eRotationFirst = E_ROTATIONEVENT.B;
                eRotationSecond = E_ROTATIONEVENT.z;
                break;
        	case Db:
                eRotationFirst = E_ROTATIONEVENT.b;
                eRotationSecond = E_ROTATIONEVENT.Z;
                break;
        	case DL:
	            eRotationFirst = E_ROTATIONEVENT.L;
	            eRotationSecond = E_ROTATIONEVENT.x;
	            break;
        	case Dl:   
        		eRotationFirst = E_ROTATIONEVENT.l;
        		eRotationSecond = E_ROTATIONEVENT.X;
        		break;
        	case DR:
        		eRotationFirst = E_ROTATIONEVENT.R;
        		eRotationSecond = E_ROTATIONEVENT.X;
        		break;
        	case Dr:
        		eRotationFirst = E_ROTATIONEVENT.r;
        		eRotationSecond = E_ROTATIONEVENT.x;
        		break;
        }

        XChange(dreamcube, eRotationFirst);
        if (eRotationSecond != E_ROTATIONEVENT.NULL)
        {
            XChange(dreamcube, eRotationSecond);
        }

    }

    //这里不会存在双面操作，双面操作在调用此函数前会转换成两次单面操作
    private void XChange(ST_DreamCube dreamcube, E_ROTATIONEVENT eRotation)
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        int[][] aiPlate = null;
        Boolean bClockWise = true;

        switch (eRotation)
        { 
            case U:
                ePlate = E_PLATE.UP;
                aiPlate = dreamcube.Plate.U;
                bClockWise = true;
                break;
            case u:
                ePlate = E_PLATE.UP;
                aiPlate = dreamcube.Plate.U;
                bClockWise = false;
                break;
            case D:
                ePlate = E_PLATE.DOWN;
                aiPlate = dreamcube.Plate.D;
                bClockWise = true;
                break;
            case d:
                ePlate = E_PLATE.DOWN;
                aiPlate = dreamcube.Plate.D;
                bClockWise = false;
                break;
            case F:
                ePlate = E_PLATE.FRONT;
                aiPlate = dreamcube.Plate.F;
                bClockWise = true;
                break;
            case f:
                ePlate = E_PLATE.FRONT;
                aiPlate = dreamcube.Plate.F;
                bClockWise = false;
                break;
            case B:
                ePlate = E_PLATE.BACK;
                aiPlate = dreamcube.Plate.B;
                bClockWise = true;
                break;
            case b:
                ePlate = E_PLATE.BACK;
                aiPlate = dreamcube.Plate.B;
                bClockWise = false;
                break;
            case L:
                ePlate = E_PLATE.LEFT;
                aiPlate = dreamcube.Plate.L;
                bClockWise = true;
                break;
            case l:
                ePlate = E_PLATE.LEFT;
                aiPlate = dreamcube.Plate.L;
                bClockWise = false;
                break;
            case R:
                ePlate = E_PLATE.RIGHT;
                aiPlate = dreamcube.Plate.R;
                bClockWise = true;
                break;
            case r:
                ePlate = E_PLATE.RIGHT;
                aiPlate = dreamcube.Plate.R;
                bClockWise = false;
                break;
            case X:
                ePlate = E_PLATE.X;
                bClockWise = true;
                break;
            case x:
                ePlate = E_PLATE.X;
                bClockWise = false;
                break;
            case Y:
                ePlate = E_PLATE.Y;
                bClockWise = true;
                break;
            case y:
                ePlate = E_PLATE.Y;
                bClockWise = false;
                break;
            case Z:
                ePlate = E_PLATE.Z;
                bClockWise = true;
                break;
            case z:
                ePlate = E_PLATE.Z;
                bClockWise = false;
                break;
        }

        if (ePlate == E_PLATE.X || ePlate == E_PLATE.Y || ePlate == E_PLATE.Z)
        {
            XChgColor(dreamcube, ePlate, bClockWise);
            XChgPlateCoordinate(dreamcube, ePlate, bClockWise);
            XChgBlockIndex(dreamcube, ePlate, bClockWise);
        }
        else
        {
            XChgColor(dreamcube, ePlate, aiPlate, bClockWise);
            XChgPlateCoordinate(dreamcube, aiPlate, bClockWise);
            XChgBlockIndex(dreamcube, ePlate, aiPlate, bClockWise);
        }
    }

    //变换步骤状态数据，使得魔方在整体旋转时，仍能匹配上状态数据
    private void XChgStepStatusData(E_ROTATIONEVENT eRotation, Boolean bCheck)
    {
        if (bCheck)
        {
            return;
        }

        switch(eRotation)
        {
            case X:
                XChg_StepThreePlate(E_ROTATIONEVENT.R, E_ROTATIONEVENT.X, E_ROTATIONEVENT.l);
                break;
            case x:
                XChg_StepThreePlate(E_ROTATIONEVENT.r, E_ROTATIONEVENT.x, E_ROTATIONEVENT.L);
                break;
            case Y:
                XChg_StepThreePlate(E_ROTATIONEVENT.U, E_ROTATIONEVENT.Y, E_ROTATIONEVENT.d);
                break;
            case y:
                XChg_StepThreePlate(E_ROTATIONEVENT.u, E_ROTATIONEVENT.y, E_ROTATIONEVENT.D);
                break;
            case Z:
                XChg_StepThreePlate(E_ROTATIONEVENT.F, E_ROTATIONEVENT.Z, E_ROTATIONEVENT.b);
                break;
            case z:
                XChg_StepThreePlate(E_ROTATIONEVENT.f, E_ROTATIONEVENT.z, E_ROTATIONEVENT.B);
                break;
        }

    }

    //同时修改三个面
    private void XChg_StepThreePlate(E_ROTATIONEVENT eEvent1, E_ROTATIONEVENT eEvent2, E_ROTATIONEVENT eEvent3 )
    {
        XChg(DREAM_CUBE_STEP1, eEvent1);
        XChg(DREAM_CUBE_STEP1, eEvent2);
        XChg(DREAM_CUBE_STEP1, eEvent3);

        XChg(DREAM_CUBE_STEP2, eEvent1);
        XChg(DREAM_CUBE_STEP2, eEvent2);
        XChg(DREAM_CUBE_STEP2, eEvent3);

        XChg(DREAM_CUBE_STEP3, eEvent1);
        XChg(DREAM_CUBE_STEP3, eEvent2);
        XChg(DREAM_CUBE_STEP3, eEvent3);

        XChg(DREAM_CUBE_STEP4, eEvent1);
        XChg(DREAM_CUBE_STEP4, eEvent2);
        XChg(DREAM_CUBE_STEP4, eEvent3);

        XChg(DREAM_CUBE_STEP5, eEvent1);
        XChg(DREAM_CUBE_STEP5, eEvent2);
        XChg(DREAM_CUBE_STEP5, eEvent3);

        XChg(DREAM_CUBE_STEP6, eEvent1);
        XChg(DREAM_CUBE_STEP6, eEvent2);
        XChg(DREAM_CUBE_STEP6, eEvent3);

    }


    public CubeOperationChangingEventListener CubeOperationChangingFunc = null;
    public CubeOperationChangedEventListener CubeOperationChangedFunc = null; 
    public CubeOperationTipEventListener CubeOperationTipFunc = null;
    public CubeStatusChangedEventListener CubeStatusChangedFunc = null;
    public CubeCompledLevelEventListener CubeCompledLevelFunc = null;
    
    public CubeErrorEventListener CubeErrorFunc = null;
    public CubeMessageEventListener CubeMessageFunc = null;
    public CubeSoundEventListener CubeSoundFunc = null;

    //CubeOperation自定义事件处理
    public class CubeStatusEventArgs
    {
        public E_ROTATIONEVENT eRotate;
        public char cColorStep;
        public boolean bCapture;
    }

    
    @SuppressWarnings("serial")
	public class CubeOperationEvent extends EventObject 
    {
    	private CubeStatusEventArgs event = new CubeStatusEventArgs();

    	public CubeOperationEvent(Object source, CubeStatusEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(CubeStatusEventArgs e) 
    	{
    		this.event = e;
    	}

    	public CubeStatusEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface CubeOperationChangedEventListener extends EventListener {

    	public void event(CubeOperationEvent event);

	}
    
    public interface CubeOperationChangingEventListener extends EventListener {

    	public void event(CubeOperationEvent event);

	}

    public interface CubeOperationTipEventListener extends EventListener {

    	public void event(CubeOperationEvent event);

	}

    public interface CubeStatusChangedEventListener extends EventListener {

    	public void event(CubeOperationEvent event);

	}

    public interface CubeCompledLevelEventListener extends EventListener {

    	public void event(CubeCompledLevelEvent event);

	}

    public class CubeCompledLevelEventArgs
    {
        public E_COMPLED_STATUS eCompledLevel;
    }

    @SuppressWarnings("serial")
	public class CubeCompledLevelEvent extends EventObject 
    {
        private CubeCompledLevelEventArgs event = new CubeCompledLevelEventArgs();

    	public CubeCompledLevelEvent(Object source, CubeCompledLevelEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(CubeCompledLevelEventArgs e) 
    	{
    		this.event = e;
    	}

    	public CubeCompledLevelEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}
    
  
    
    //CubeError自定义事件处理
    public class CubeErrorEventArgs
    {
        public E_RET iErrorNo;
        public String szErrorInfo;
        public String szFunctionName;
    }
     
    @SuppressWarnings("serial")
	public class CubeErrorEvent extends EventObject 
    {
        private CubeErrorEventArgs event = new CubeErrorEventArgs();

    	public CubeErrorEvent(Object source, CubeErrorEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(CubeErrorEventArgs e) 
    	{
    		this.event = e;
    	}

    	public CubeErrorEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}

    public interface CubeErrorEventListener extends EventListener {

    	public void event(CubeErrorEvent event);

	}

    
   
    
    //CubeMessage自定义事件处理
    public class CubeMessageEventArgs
    {
        public String szInfo;
        public String szCaption;
        public E_Message_Type eMsgType;
    }

    //CubeSound自定义事件处理
    public class CubeSoundEventArgs
    {
        public E_SOUND eSound;
    }

    @SuppressWarnings("serial")
	public class CubeMessageEvent extends EventObject 
    {
        private CubeMessageEventArgs event = new CubeMessageEventArgs();

    	public CubeMessageEvent(Object source, CubeMessageEventArgs e) 
    	{
	    	super(source);
	    	this.event = e;
	    }

    	public void setEventState(CubeMessageEventArgs e) 
    	{
    		this.event = e;
    	}

    	public CubeMessageEventArgs getEventState() 
    	{
    		return this.event;
    	}

	}
    
    @SuppressWarnings("serial")
	public class CubeSoundEvent extends EventObject 
    {
        private E_SOUND eSound;

    	public CubeSoundEvent(Object source, E_SOUND eSound) 
    	{
	    	super(source);
	    	this.eSound = eSound;
	    }

    	public void setSound(E_SOUND eSound) 
    	{
    		this.eSound = eSound;
    	}

    	public E_SOUND getSound() 
    	{
    		return this.eSound;
    	}

	}


    public interface CubeMessageEventListener extends EventListener {

    	public void event(CubeMessageEvent event);

	}

    public interface CubeSoundEventListener extends EventListener {

    	public void event(CubeSoundEvent event);

	}



    

    protected void OnCubeOperationChanged(CubeStatusEventArgs e)
    {
        CubeOperationChangedEventListener listener = CubeOperationChangedFunc;
        if (listener != null)
        {
            CubeOperationEvent event = new CubeOperationEvent(this, e);
        	listener.event(event);
        }
    }

    protected void OnCubeOperationChanging(CubeStatusEventArgs e)
    {
        CubeOperationChangingEventListener listener = CubeOperationChangingFunc;
        if (listener != null)
        {
            CubeOperationEvent event = new CubeOperationEvent(this, e); 
        	listener.event(event);
        }
    }

    protected void OnCubeOperationTip(CubeStatusEventArgs e)
    {
        CubeOperationTipEventListener listener = CubeOperationTipFunc;
        if (listener != null)
        {
            CubeOperationEvent event = new CubeOperationEvent(this, e); 
        	listener.event(event);
        }
    }

    protected void OnCubeStatusChanged(CubeStatusEventArgs e)
    {
        CubeStatusChangedEventListener listener = CubeStatusChangedFunc;
        if (listener != null)
        {
            CubeOperationEvent event = new CubeOperationEvent(this, e); 
        	listener.event(event);
        }
    }

    protected void OnCubeCompledLevelChanged(CubeCompledLevelEventArgs e)
    {
        CubeCompledLevelEventListener listener = CubeCompledLevelFunc;
        if (listener != null)
        {
        	CubeCompledLevelEvent event = new CubeCompledLevelEvent(this, e); 
        	listener.event(event);
        }
    }

    


    protected void OnCubeError(CubeErrorEventArgs e)
    {
        CubeErrorEventListener listener = CubeErrorFunc;
        if (listener != null)
        {
            CubeErrorEvent event = new CubeErrorEvent(this, e); 
        	listener.event(event);
        }
    }

    protected void OnCubeMessage(CubeMessageEventArgs e)
    {
        CubeMessageEventListener listener = CubeMessageFunc;
        if (listener != null)
        {
            CubeMessageEvent event = new CubeMessageEvent(this, e); 
        	listener.event(event);
        }
    }

    public void OnCubeSound(E_SOUND eSound)
    {
        if (isAutoPlay)
        {
        	return;
        }
        CubeSoundEventListener listener = CubeSoundFunc;
        if (listener != null)
        {
            CubeSoundEvent event = new CubeSoundEvent(this, eSound); 
        	listener.event(event);
        }
    }


    private void RaiseCubeOperationChangedEvent()
    {
        if (!bEventEnable)
        {
            return;
        }
        CubeStatusEventArgs e = new CubeStatusEventArgs();
        //System.Threading.Thread.Sleep(iEventDelay);
        OnCubeOperationChanged(e);
    }

    private void RaiseCubeOperationChangingEvent(E_ROTATIONEVENT eRotate, Boolean bCapture)
    {
        if (!bEventEnable)
        {
            return;
        }
        CubeStatusEventArgs e = new CubeStatusEventArgs();
        e.eRotate = eRotate;
        e.bCapture = bCapture;
        OnCubeOperationChanging(e);
    }

    private void RaiseCubeOperationTipEvent(E_ROTATIONEVENT eRotate, char cColorStep)
    {
        if (!bEventEnable)
        {
            return;
        }
        CubeStatusEventArgs e = new CubeStatusEventArgs();
        e.eRotate = eRotate;
        e.cColorStep = cColorStep;
        OnCubeOperationTip(e);
    }

    protected void RaiseCubeStatusChangedEvent(E_COMPLED_STATUS eCompledLevel)
    {
        if (!bEventEnable)
        {
            return;
        }
        CubeCompledLevelEventArgs e = new CubeCompledLevelEventArgs();
        e.eCompledLevel = eCompledLevel;
        OnCubeCompledLevelChanged(e);
    }


    
    public void RaiseCubeErrorEvent(E_RET iErrorNo, String szErrorInfo, String szFunctionName)
    {
        CubeErrorEventArgs e = new CubeErrorEventArgs();
        e.iErrorNo = iErrorNo;
        e.szErrorInfo = szErrorInfo;
        e.szFunctionName = szFunctionName;
        OnCubeError(e);
    }

    public void RaiseCubeMessageEvent(String szInfo, String szCaption, E_Message_Type eMsgType)
    {
        CubeMessageEventArgs e = new CubeMessageEventArgs();
        e.szInfo = szInfo;
        e.szCaption = szCaption;
        e.eMsgType = eMsgType;
        OnCubeMessage(e);
    }


    public Boolean GetEventStatus()
    {
        return bEventEnable;
    }

    public void SetEventStatus(Boolean bValue)
    {
        bEventEnable = bValue;
    }
  
    //设置魔方的步骤计数条
    private void SetStepBar(int iTotalStep)
    {
    	 szStepBar = String.format("%03d", iTotalStep);
    }

    private void SetStepBar(int iCurrentStep, int iTotalStep)
    {
    	 szStepBar = String.format("%03d/%03d", iCurrentStep, iTotalStep);
    }
    
    public String GetStepBar()
    {
    	return szStepBar;
    }

    public void AutoStep()
    {}
  
    public void CheckCurrentStatus()
    {}
    
    public int GetMinLevel()
    {
    	return 0;
    }
    public int GetMaxLevel()
    {
    	return 0;
    }
    
    public void GetTrainningStep(int iSutdyLevel)
    {}
    
    public void SetTrainningLevel(int iStudyLevel)
    {}

    public void InitCubeRandom(int iStep)
    {}
    public void InitCubeByStep(String szStep)
    {
    }    
    public boolean IsFinish()
    {
    	return true;
    }
 }
