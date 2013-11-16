package elong.Cube.Cube3;

public class MethodBridge extends CrazyCube{

	
    int S1_COMPARE_BLOCK_NUM = 6;
    int[] S1_CompareList;       //比较方块列表
    int S2_COMPARE_BLOCK_NUM = 12;
    int[] S2_CompareList;       //比较方块列表
    int S3_COMPARE_BLOCK_NUM = 20;
    int[] S3_CompareList;       //比较方块列表
    int S4_COMPARE_BLOCK_NUM = 26;
    int[] S4_CompareList;       //比较方块列表


    public MethodBridge()
    {
        YDegree(270.0f);
        InitCompareList();
        InitCubeStepStatus();
        InitCubeStartStatus();
    }


    private void InitCompareList()
    {
        S1_CompareList = new int[] { 0, 3, 6, 9, 12, 15};    //比较方块列表
        S2_CompareList = new int[] { 0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17 };    //比较方块列表
        S3_CompareList = new int[] { 0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17, 18, 20, 24, 26, 22, 4, 10, 16 };    //比较方块列表
        S4_CompareList = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };    //比较方块列表

    }


    private void InitCubeStepStatus()
    {
        InitCoordinate(DREAM_CUBE_STEP1);
        InitCoordinate(DREAM_CUBE_STEP2);
        InitCoordinate(DREAM_CUBE_STEP3);
        InitCoordinate(DREAM_CUBE_STEP4);
        InitCoordinate(DREAM_CUBE_STEP5);
        InitCoordinate(DREAM_CUBE_STEP6);

        InitStep1Color();
        InitStep2Color();
        InitStep3Color();
        InitStep4Color();
        InitStep5Color();
        InitStep6Color();

        InitPlate(DREAM_CUBE_STEP1);
        InitPlate(DREAM_CUBE_STEP2);
        InitPlate(DREAM_CUBE_STEP3);
        InitPlate(DREAM_CUBE_STEP4);
        InitPlate(DREAM_CUBE_STEP5);
        InitPlate(DREAM_CUBE_STEP6);
    }

    private void InitStep1Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP1, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP1, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
    }

    private void InitStep2Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP2, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP2, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
    }

    private void InitStep3Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP3, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP3, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 18, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 20, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 24, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 26, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 4, E_COLOR.NOCOLOR, E_COLOR.UNDESIGNATE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.UNDESIGNATE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.UNDESIGNATE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 22, E_COLOR.UNDESIGNATE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
    }

    private void InitStep4Color()
    {
        InitColor(DREAM_CUBE_STEP4);
    }

    private void InitStep5Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP5, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
    }

    private void InitStep6Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP6, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
    }
    
    /*初始化魔方为随机状态*/
    public void InitCubeRandom(int iStep)
    {
    	iCurrentStepIndex = 0;
    	iCurrentIndex = 0;
        InitDreamCubeRandom(iStep);
        InitCubeStepStatus();
        InitCubeStartStatus();
        ClearStep();
    }
    
    public void InitCubeByStep(String szStep)
    {
    	iCurrentStepIndex = 0;
    	iCurrentIndex = 0;
        InitDreamCubeByStep(szStep);
        InitCubeStepStatus();
        InitCubeStartStatus();
        ClearStep();
    }    

    /*初始化训练状态*/
    private void TrainningInit(int iStudyLevel)
    {
        boolean bOldEventStatus = GetEventStatus();

        SetEventStatus(false);

        InitCubeRandom(30);

        isAutoPlay = true;                  //这个操作也是自动操作

        switch (iStudyLevel)
        {
            case 1:
                break;
            case 2:
                S1_RunInto();
                break;
            case 3:
                S1_RunInto();
                S2_RunInto();
                break;
            case 4:
                S1_RunInto();
                S2_RunInto();
                S3_RunInto();
                break;
        }
        SetEventStatus(bOldEventStatus);
        isAutoPlay = false;
        CheckCurrentStatus();
    }

    public void GetTrainningStep(int iSutdyLevel)
    {
        boolean bOldEventStatus = GetEventStatus();            //保存原来的事件处理状态
        SetEventStatus(false);                              //设置成不使用事件

        isAutoPlay = true;                              //设置AUTO标志
        ClearStep();
        CloneCube();                                   //先将魔方复制

        manualPlayIndex = 0;
        XChgDreamCube4Check();              //这句一定要，否则状态可能不正确

        switch (iSutdyLevel)                            //解到下一完成状态的魔方
        {
            case 1:
                S1_RunInto();
                break;
            case 2:
                S2_RunInto();
                break;
            case 3:
                S3_RunInto();
                break;
            case 4:
                S4_RunInto();
                break;
        }
        RestoreCube();                                         //恢复魔方状态到解魔方前

        TrainPlayStep(trainPlayStep);  

        isAutoPlay = false;                                     //设置AUTO标志

        SetEventStatus(bOldEventStatus);
    }

    /*设置训练级别并启动训练过程*/
    public void SetTrainningLevel(int iStudyLevel)
    {
        boolean bDone = false;
        if (iStudyLevel >= E_STUDY_STATUS.STUDY_STEP1.ordinal() && iStudyLevel <= E_STUDY_STATUS.STUDY_STEP4.ordinal())
        {
            stTrainStatus.iStudyLevel = iStudyLevel;
            stTrainStatus.iStatus = iStudyLevel;
            while (!bDone)     //这个作用是防止训练级别不正确的情况，因为状态有可能处于更高级别
            {
                TrainningInit(iStudyLevel);
                bDone = true;
                for (int i = (int)(iStudyLevel - 1); i < 4; i++)
                {
                    if (abAutoPlayStatus[i])     //如果发现更高级别状态不正确，则再循环一次
                    {
                        bDone = false;
                        break;
                    }
                }
            }
        }
    }


    /*是否已经完成*/
    public boolean IsFinish()
    {
        if (S4_IsMatch())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void AutoStep()
    {
       	iCurrentStepIndex = 0;
    	iCurrentIndex = 0;
        TurnIntoStandarCube(DREAM_CUBE_CACULATE, true, false);

        boolean bOldEventStatus = GetEventStatus();           //保存原来的事件处理状态
        SetEventStatus(false);                             //设置成不使用事件

        SetGameMode(E_GAMEMODE.INIT_MODE);       //设置成游戏模式
        isAutoPlay = true;                             //设置AUTO标志

        CloneCube();

        ClearStep();                                   //清除步骤
        S1_RunInto();                                   //自动操作
        S2_RunInto();
        S3_RunInto();
        S4_RunInto();

        isAutoPlay = false;                            //清除自动标志
        SetEventStatus(bOldEventStatus);                   //恢复事件状态

        ManualPlayStep(autoPlayStep);                 //读取自动完成的步骤

        RestoreCube();                                //将魔方状态恢复至运算前的状态

        InitCubeStepStatus();                           //初始化步骤状态
        InitCubeStartStatus();                          //初始化开始状态

        ClearAutoPlayStatus();                             //相应清除自动状态然后重新检查当前状态
        CheckCurrentStatus();                              //检查魔方当前状态
        SetGameMode(E_GAMEMODE.AUTO_MODE);       //设置成自动解魔方模式        
    }

    /*检查当前魔方的完成状态*/
    public void CheckCurrentStatus()
    {
        int i = 0;
        boolean bMatch = false;
        E_COMPLED_STATUS eLevel = GetHighestStatus();

        if (eLevel.ordinal() < E_COMPLED_STATUS.COMPLED_STATUS2.ordinal())      //二级以上需要全匹配
        {
            for (i = 0; i < 4; i++)
            {
                RotationNoEvent(E_ROTATIONEVENT.x, DREAM_CUBE_CACULATE);

                if (!bMatch)            //只有未匹配的才需要检查，可以避免重复上报
                {
                    bMatch = CheckCubeStatus();
                }
            }

        }
        else
        {
            bMatch = CheckCubeStatus();
        }
    }


    private boolean CheckCubeStatus()
    {
        E_COMPLED_STATUS eCompledLevel = E_COMPLED_STATUS.COMPLED_NONE;

        if (isAutoPlay)        //自动运行时不上报事件
        {
            return true;
        }

        //XChgMagicCube4Check();
        TurnIntoStandarCube(DREAM_CUBE_CHECKSTATUS, false, true);

        if (S4_IsMatch())
        {
        	if(eGameMode == E_GAMEMODE.STUDY_MODE)
        	{
            	if(iCurrentStepIndex == trainPlayStep.length() - 1)
            	{
            		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS4;
            	}
        	}
        	else
        	{
        		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS4;
        	}
        }
        else if (S3_IsMatch())
        {
        	if(eGameMode == E_GAMEMODE.STUDY_MODE)        //这样可以防止为了检测状态旋转X轴时产生误检的情况，学习模式时，只要步骤还未完成，都不算完成
        	{
            	if(iCurrentStepIndex == trainPlayStep.length() - 1)
            	{
            		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS3;
            	}
        	}
        	else
        	{
        		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS3;
        	}
        }
        else if (S2_IsMatch())
        {
        	if(eGameMode == E_GAMEMODE.STUDY_MODE)
        	{
            	if(iCurrentStepIndex == trainPlayStep.length() - 1)
            	{
            		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS2;
            	}
        	}
        	else
        	{
        		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS2;
        	}
        }
        else if (S1_IsMatch())
        {
        	if(eGameMode == E_GAMEMODE.STUDY_MODE)
        	{
            	if(iCurrentStepIndex == trainPlayStep.length() - 1)
            	{
            		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS1;
            	}
        	}
        	else
        	{
        		eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS1;
        	}
        }

        SetAutoPlayStatus(eCompledLevel);

        if (eCompledLevel != E_COMPLED_STATUS.COMPLED_NONE)
        {
        	RaiseCubeStatusChangedEvent(eCompledLevel);

            return true;
        }
        return false;
    }


    /*指定的棱块、棱角块是否在规定位置*/
    private boolean IsBlockPairInPosition(int iFirstBlock, int iSecondBlock)
    {
        if (IsBlockInCorrectPostion(iFirstBlock) && IsBlockInCorrectPostion(iSecondBlock))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private boolean S1_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S1_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S1_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                /*方块不在指定位置，直接返回*/
                return false;
            }
        }

        return true;

    }


    /*将红白棱块归位*/
    private void S1_TurnBlock3IntoPosition()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 3;
        ePlate = GetPlateWithColor(iBlock, E_COLOR.WHITE);
        switch (ePlate)
        { 
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("Bl");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("ufL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("UfL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("fL");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("bl");
                }
                else if (IsBlockInSpecificPosition(iBlock, 3))
                {
                    RotationByPhysicalFormula("DFL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("dFL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("FL");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("d");
                }
                else if (IsBlockInSpecificPosition(iBlock, 15))
                {
                    RotationByPhysicalFormula("fd");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("Fd");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("FFd");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("D");
                }
                else if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("BD");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("bD");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("BBD");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 3))
                {
                    //do nothing
                }
                else if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("l");
                }
                else if (IsBlockInSpecificPosition(iBlock, 15))
                {
                    RotationByPhysicalFormula("L");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("LL");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("dd");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("Rdd");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("rdd");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("RRdd");
                }
                break;
        }
    }

    /*将白蓝棱块（15）转到指定位置（7）*/
    private void S1_TurnBlock15Into7()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 15;

        if (IsBlockPairInPosition(6, 15))
        {
            return;    //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.WHITE);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("UUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("Ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("x");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("X");
                }
                else if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("RF");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("FFx");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 7))
                {
                }
                else if (IsBlockInSpecificPosition(iBlock, 15))
                {
                    RotationByPhysicalFormula("f");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("F");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("FF");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("BBxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("bxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("Bxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("xx");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 3))
                {
                }
                else if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("bUUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 15))
                {
                    RotationByPhysicalFormula("Fx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("Uxx");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("ldL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("rUFF");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("RUFF");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("UFF");
                }
                break;
        }
    }

    private void S1_TurnBlock6Into24()
    { 
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 6;

        if (IsBlockPairInPosition(6, 15))
        {
            return;   //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.RED);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("bUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("rU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("UUrU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("urU");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("bbrU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("rrurU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 6))
                {
                    RotationByPhysicalFormula("XXFxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("RU");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 6))
                {
                    RotationByPhysicalFormula("xFFUX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("xfUX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("xFUX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("xUX");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("bu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("bbu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("u");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("Bu");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("bbUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 6))
                {
                    //这里可以先归位。因为两个方块已经对在一起，不需要在单独将6转到24的位置。
                    //下一步操作前会先判断S1_IsFirstBlockPairInPosition，保证不重复计算
                    RotationByPhysicalFormula("fRUf");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("brU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    //do nothing
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("rUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("rrUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("UU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("RUU");
                }
                break;
        }
    }

    private void S1_TurnBlock9Into7()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 9;

        if (IsBlockPairInPosition(0, 9))
        {
            return; //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.WHITE);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("xx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("Uxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("uxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("uuxx");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("bbxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("ldL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    //do nothing
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("LFFlx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("Lflx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("x");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("X");
                }
                else if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("BX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("bX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("bbX");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 9))
                {
                    RotationByPhysicalFormula("bxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("ux");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("rrUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("rUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("RUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("Ux");
                }
                break;
        }
    }

    private void S1_TurnBlock0Into18()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 0;

        if (IsBlockPairInPosition(0, 9))
        {
            return;   //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.RED);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("UB");          
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("B");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("uuB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("uB");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("b");
                }
                else if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("ru");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("RRB");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("RuB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("U");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("RB");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("BBu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("Bu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("bu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("u");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 0))
                {
                    RotationByPhysicalFormula("bUB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    //do nothing
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("Ubu");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("BB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("rBB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("RBB");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("RRBB");
                }
                break;
        }
    }


    private void S1_Step1()
    {
        if (S1_IsMatch())
        {
            return;
        }
        S1_TurnBlock3IntoPosition();
    }

    private void S1_Step2()
    {
        if (S1_IsMatch())
        {
            return;
        }
        S1_TurnBlock15Into7();
        S1_TurnBlock6Into24();
        if (!IsBlockPairInPosition(6, 15))
        { 
            //now block 15 in position 7, block 6 in position 24
            RotationByPhysicalFormula("Xf");    //first block pair in position
        }

    }

    private void S1_Step3()
    {
        if (S1_IsMatch())
        {
            return;
        }
        S1_TurnBlock9Into7();
        S1_TurnBlock0Into18();
        if (!IsBlockPairInPosition(0, 9))
        {
            //now block 9 in position 7, block 0 in position 18
            RotationByPhysicalFormula("XXB");    //second block pair in position            
        }
    }

    private void S1_RunInto()
    {
        if (S1_IsMatch())
        {
            return;
        }
        S1_Step1();
        S1_Step2();
        S1_Step3();
    }
    
    private boolean S2_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S2_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S2_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                /*方块不在指定位置，直接返回*/
                return false;
            }
        }

        return true;
    }

    /*将红黄棱块归位*/
    private void S2_TurnBlock5IntoPosition()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 5;
        ePlate = GetPlateWithColor(iBlock, E_COLOR.YELLOW);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("xuRR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("UxuRR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("UXURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("XURR");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("xURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 5))
                {
                    RotationByPhysicalFormula("RRUXURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("XuRR");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("XXURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("RUXURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("uRR");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("xxuRR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("rUXURR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("URR");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("UURR");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("R");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("r");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("RR");
                }
                break;
        }
    }

    /*将黄蓝棱块（17）转到指定位置（7）*/
    private void S2_TurnBlock17Into7()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 17;

        if (IsBlockPairInPosition(8, 17))
        {
            return;    //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.YELLOW);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("UUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("Ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("x");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("X");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("XUUxx");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("PUp");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("UUxx");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("xUUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("pUxR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("xx");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("Uxx");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("ruRxx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 17))
                {
                    RotationByPhysicalFormula("Rupx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("uxx");
                }
                break;
        }
    }

    private void S2_TurnBlock8Into20()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 8;

        if (IsBlockPairInPosition(8, 17))
        {
            return;   //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.RED);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("UlBLUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("lBLUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("UUlBLUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("ulBLUU");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("rUR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("RUrUU");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("RulBLrUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("lbL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("u");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("lBLUUlbL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("U");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("UUlbL");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("ulbL");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("UU");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("rUURUU");
                }
                else if (IsBlockInSpecificPosition(iBlock, 8))
                {
                    RotationByPhysicalFormula("PUpuLFl");   //块对直接归位
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("UlbL");
                }
                break;
        }
    }

    private void S2_TurnBlock11Into7()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 11;

        if (IsBlockPairInPosition(2, 11))
        {
            return; //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.YELLOW);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("UUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("Ux");
                }
                else if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("x");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("X");
                }
                else if (IsBlockInSpecificPosition(iBlock, 7))
                {
                    RotationByPhysicalFormula("XXUUx");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 25))
                {
                    RotationByPhysicalFormula("UUxx");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 1))
                {
                    RotationByPhysicalFormula("xUUx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("lBOx");
                }
                else if (IsBlockInSpecificPosition(iBlock, 19))
                {
                    RotationByPhysicalFormula("xx");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 21))
                {
                    RotationByPhysicalFormula("Uxx");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 11))
                {
                    RotationByPhysicalFormula("ruPX");
                }
                else if (IsBlockInSpecificPosition(iBlock, 23))
                {
                    RotationByPhysicalFormula("uxx");
                }
                break;
        }
    }

    private void S2_TurnBlock2Into18()
    {
        E_PLATE ePlate = E_PLATE.NOPLATE;
        short iBlock = 2;

        if (IsBlockPairInPosition(2, 11))
        {
            return;   //已经在正确位置
        }

        ePlate = GetPlateWithColor(iBlock, E_COLOR.RED);
        switch (ePlate)
        {
            case UP:
                if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("lBBLu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("ulBBLu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("UlBBLu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("UUlBBLu");
                }
                break;
            case DOWN:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("ruR");
                }
                break;
            case FRONT:
                if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("U");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("urUUR");
                }
                break;
            case BACK:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("rURu");
                }
                else if (IsBlockInSpecificPosition(iBlock, 18))
                {
                    RotationByPhysicalFormula("UrUUR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("u");
                }
                break;
            case LEFT:
                if (IsBlockInSpecificPosition(iBlock, 24))
                {
                    RotationByPhysicalFormula("UUrUUR");
                }
                break;
            case RIGHT:
                if (IsBlockInSpecificPosition(iBlock, 2))
                {
                    RotationByPhysicalFormula("ruRUrUUR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 20))
                {
                    RotationByPhysicalFormula("rUUR");
                }
                else if (IsBlockInSpecificPosition(iBlock, 26))
                {
                    RotationByPhysicalFormula("UU");
                }
                break;
        }
    }


    private void S2_Step1()
    {
        if (S2_IsMatch())
        {
            return;
        }
        S2_TurnBlock5IntoPosition();
    }

    private void S2_Step2()
    {
        if (S2_IsMatch())
        {
            return;
        }
        S2_TurnBlock17Into7();
        S2_TurnBlock8Into20();
        if (!IsBlockPairInPosition(8, 17))
        {
            //now block 17 in position 7, block 8 in position 20
            RotationByPhysicalFormula("XXuRUUr");    //first block pair in position
        }

    }

    private void S2_Step3()
    {
        if (S2_IsMatch())
        {
            return;
        }
        S2_TurnBlock11Into7();
        S2_TurnBlock2Into18();
        if (!IsBlockPairInPosition(2, 11))
        {
            //now block 11 in position 7, block 2 in position 18
            RotationByPhysicalFormula("XXrUR");    //first block pair in position
        }
        
    }


    private void S2_RunInto()
    {
        if (S2_IsMatch())
        {
            return;
        }
        S2_Step1();
        S2_Step2();
        S2_Step3();
    }

    private boolean S3_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S3_COMPARE_BLOCK_NUM - 3; i++)     //后面三个方块不需要比较
        {
            iBlock = S3_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                /*方块不在指定位置，直接返回*/
                return false;
            }
        }

        return true;
    }

    //判断顶层的角块的顶面颜色都是同样的颜色
    private boolean IsUpPlateCornerWithSameColor(E_COLOR eColor)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[18].Color.U == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[20].Color.U == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[24].Color.U == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[26].Color.U == eColor)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //判断顶层角块侧面的颜色是否相同，位置也要正确
    private boolean IsUpRowCornerSidePlateWithSameColor()
    {
        int iBlock1 = 0;
        int iBlock2 = 0;

        
        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B != DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B)
        {
            return false;
        }
        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][2]; 
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R != DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.R)
        {
            return false;
        }
        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][2];
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F != DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F)
        {
            return false;
        }
        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L != DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.L)
        {
            return false;
        }

        return true;

    }

    //获取顶面角块颜色为指定颜色的数量
    private int S3_GetNumOfUpPlateCornerWithColor(E_COLOR eColor)
    {
        int iNum = 0;

        if (DREAM_CUBE_CHECKSTATUS.cube_status[18].Color.U == eColor)
        {
            iNum++;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[20].Color.U == eColor)
        {
            iNum++;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[24].Color.U == eColor)
        {
            iNum++;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[26].Color.U == eColor)
        {
            iNum++;
        }
        return iNum;
    }


    //获取某个顺(逆)时针方向的角块（指ORANGE顺(逆)时针转动能够到达顶面）
    private int S3_GetBlockByClockWise(boolean bClockWise)
    {
        E_COLOR eColor = E_COLOR.ORANGE;
        E_COLOR eClockWise = E_COLOR.NOCOLOR;
        E_COLOR eUnClockWise = E_COLOR.NOCOLOR;
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    //iBlock = 18;
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];            
                    eClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
                    eUnClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
                    break;
                case 1:
                    //iBlock = 20;
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];  
                    eClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
                    eUnClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
                    break;
                case 2:
                    //iBlock = 24;
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];  
                    eClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
                    eUnClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
                    break;
                case 3:
                    //iBlock = 26;
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[2][2];  
                    eClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
                    eUnClockWise = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
                    break;
            }
            if (eClockWise == eColor)
            {
                if (bClockWise)
                {
                    return iBlock;
                }
            }
            else if (eUnClockWise == eColor)
            {
                if (!bClockWise)
                {
                    return iBlock;
                }
            }
        }
        return 0xff;
    }

    

    private int S3_GetBlockIndex()
    {
        int iNum = S3_GetNumOfUpPlateCornerWithColor(E_COLOR.ORANGE);

        switch (iNum)
        { 
            case 0:
                return S3_GetBlockByClockWise(false);     //取逆时针的某个方块
            case 1:
                if (DREAM_CUBE_CACULATE.cube_status[18].Color.U == E_COLOR.ORANGE)
                {
                    return 18;
                }
                else if (DREAM_CUBE_CACULATE.cube_status[20].Color.U == E_COLOR.ORANGE)
                {
                    return 20;
                }
                else if (DREAM_CUBE_CACULATE.cube_status[24].Color.U == E_COLOR.ORANGE)
                {
                    return 24;
                }
                else if (DREAM_CUBE_CACULATE.cube_status[26].Color.U == E_COLOR.ORANGE)
                {
                    return 26;
                }
                break;
            case 2:
                return S3_GetBlockByClockWise(true);       //取顺时针的某个方块
            case 3:
                System.out.println("S3_GetNumOfUpPlateCornerWithColor = 3");
                break;
            case 4:
                break;
        }
        return 0xff;
    }


    //将顶面转动到可以完成公式的位置
    private void S3_TurnUpPlateIntoReday1()
    {
        int iBlock = 0;
        if (IsUpPlateCornerWithSameColor(E_COLOR.ORANGE))
        {
            return;         //已经在正确的位置
        }

        iBlock = S3_GetBlockIndex();
        if (iBlock == 0xff)
        {
            System.out.println("S3_GetBlockIndex = 0xff");
            return;
        }

        if (iBlock != 18 && iBlock != 20 && iBlock != 24 && iBlock != 26)
        {
            return;
        }

        while(!IsBlockInSpecificPosition(iBlock, 18))   //将方块转动到位置18
        {
            RotationByPhysicalFormula("U");
        }
    }

    //将顶面转动到可以完成公式的位置
    private void S3_TurnUpPlateIntoReday2()
    {
        int i = 0;
        int iBlock1 = 0;
        int iBlock2 = 0;
        if (IsUpRowCornerSidePlateWithSameColor())
        {
            S3_TurnUpPlateIntoPosition();
            return;         //已经在正确的位置
        }

        for (i = 0; i < 4; i++)
        {
            iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
            iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B)
            {
                break;
            }
            RotationByPhysicalFormula("U");
        }
    }

    
    //将顶面转到完成位置，这一步保证顶面颜色是正确的
    private void S3_Step1()
    {
        int iCount = 0;
        if (S3_IsMatch())
        {
            return;
        }
        if (IsUpPlateCornerWithSameColor(E_COLOR.ORANGE))
        {
            return;         //已经在正确的位置
        }

        while (!IsUpPlateCornerWithSameColor(E_COLOR.ORANGE))
        {
            S3_TurnUpPlateIntoReday1();

            RotationByPhysicalFormula("rULuRUlu");
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S3_Step1()");
                return;
            }
        }
    }


    //这一步保证位置是正确的
    private void S3_Step2()
    {
        int iCount = 0;
        if (S3_IsMatch())
        {
            return;
        }
        if (IsBlockInCorrectPostion(18)
            && IsBlockInCorrectPostion(20)
            && IsBlockInCorrectPostion(24)
            && IsBlockInCorrectPostion(26))
        {
            return;
        }

        if (IsUpPlateCornerWithSameColor(E_COLOR.ORANGE) && IsUpRowCornerSidePlateWithSameColor())
        {
            S3_TurnUpPlateIntoPosition();
            return;
        }

        if (IsUpPlateCornerWithSameColor(E_COLOR.ORANGE))
        {
            while (!IsUpRowCornerSidePlateWithSameColor())
            {
                S3_TurnUpPlateIntoReday2();
                RotationByPhysicalFormula("LFrULuRUlufl");
                S3_TurnUpPlateIntoPosition();
                iCount++;
                if (iCount >= 10)
                {
                    System.out.println("Over 10 times, there must be some error in S3_Step2()");
                    return;
                }
            }
        }
        else
        {
            System.out.println("Status not right in S3_Step2()");
        }
    }

    private void S3_TurnUpPlateIntoPosition()
    {
        int i = 0;
        int iBlock = 0;
        for (i = 0; i < 4; i++)
        {
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.GREEN)
            {
                break;
            }
            RotationByPhysicalFormula("U");
        }
    }

    private void S3_Step3()
    {
        int i = 0;
        int iBlock = 0;
        if (S3_IsMatch())
        {
            return;
        }
        for (i = 0; i < 4; i++)
        {
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
            {
                break;
            }
            RotationByPhysicalFormula("X");
        }
    }

    private void S3_RunInto()
    {
        S3_Step1();
        S3_Step2();
        S3_Step3();
    }
    
    private boolean S4_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S4_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S4_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                /*方块不在指定位置，直接返回*/
                return false;
            }
        }

        return true;
    }

    //获取顶面颜色不正确的个数
    private int S4_GetNumOfColorNotRightOnUpPlate()
    {
        int i = 0;
        int iCount = 0;
        int iBlock = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            { 
                case 0:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];
                    break;
                case 1:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];
                    break;
                case 2:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];
                    break;
                case 3:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];
                    break;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.RED)
            {
                iCount++;
            }            
        }
        return iCount;
    }


    //获取底面颜色不正确的个数
    private int S4_GetNumOfColorNotRightOnDownPlate()
    {
        int i = 0;
        int iCount = 0;
        int iBlock = 0;

        for (i = 0; i < 2; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.D[0][1];
                    break;
                case 1:
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.D[2][1];
                    break;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.RED)
            {
                iCount++;
            }
        }
        return iCount;
    }


    private void S4_Step1()
    {
        if (S4_IsMatch())
        {
            return;
        }

        int i = 0;
        int iUpCount = S4_GetNumOfColorNotRightOnUpPlate();
        int iDownCount = S4_GetNumOfColorNotRightOnDownPlate();
        int iCount = iUpCount + iDownCount;
        int iBlock1 = 0;
        int iBlock2 = 0;

        switch (iCount)
        {
            case 0:
                //do nothing
                break;
            case 2:
                switch (iDownCount)
                { 
                    case 0:
                        //两个不正确面均在顶面
                        //do nothing
                        break;
                    case 1:
                        //顶面及底面各有一个不正确的面
                        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];
                        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];
                        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.ORANGE
                                    && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.RED)
                            || (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.ORANGE
                                    && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.RED))
                        {
                            //不正确的面在M上
                            RotationByPhysicalFormula("U");
                        }
                        RotationByPhysicalFormula("XX");
                        break;
                    case 2:
                        //两个不正确的面均在底面
                        RotationByPhysicalFormula("XX");
                        break;
                }

                //两个不正确面在顶面
                for (i = 0; i < 4; i++)
                {
                    iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];     //UF
                    iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];     //UR
                    if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.RED))
                    {
                        //UF UR颜色不正确
                        S4_Situation_UFUR();
                        break;
                    }
                    iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];     //UB
                    iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];     //UF
                    if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.RED))
                    {
                        //UF UB颜色不正确
                        S4_Situation_UFUB();
                        break;
                    }
                    RotationByPhysicalFormula("U");
                }

                break;
            case 4:
                switch (iDownCount)
                { 
                    case 0:
                        //U面4个棱块颜色不正确
                        S4_Situation_U4();
                        break;
                    case 1:
                        //顶面有三个错误，底面1个错误
                        S4_Situation_U3D1();
                        break;
                    case 2:
                        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];     //UB
                        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];     //UF
                        //这里看的是正确的
                        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE || DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.RED)
                            && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE || DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.RED))
                        {
                            RotationByPhysicalFormula("XX");

                            //现在是U4状态
                            //U面4个棱块颜色不正确
                            S4_Situation_U4();
                            break;
                        }
                        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];     //UL
                        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];     //UR
                        //这里看的是正确的
                        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE || DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.RED)
                            && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE || DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.RED))
                        {
                            RotationByPhysicalFormula("uXX");

                            //现在是U4状态
                            //U面4个棱块颜色不正确
                            S4_Situation_U4();
                            break;
                        }

                        //能够执行到这里，说明是：顶面两块不关于中心块对称，则M2后接一个U（或U'）化为两种基本情况之一
                        RotationByPhysicalFormula("XXU");
                        //顶面有三个错误，底面1个错误
                        S4_Situation_U3D1();

                        break;

                }
                break;
            //6个棱块颜色均不正确    
            case 6:
                S4_Situation_U6();
                break;
            default:
                System.out.println(String.format("U:%d D:%d", iUpCount, iDownCount));
                break;
        }
   
    }

    //UF UR颜色不正确
    private void S4_Situation_UFUR()
    {
        RotationByPhysicalFormula("xUxUUxUx");
    }

    //UF UB颜色不正确
    private void S4_Situation_UFUB()
    {
        RotationByPhysicalFormula("xUXuxUx");
    }

    private void S4_Situation_ULUFURDF()
    {
        RotationByPhysicalFormula("XUX");
    }

    private void S4_Situation_ULUBURDB()
    {
        RotationByPhysicalFormula("xUx");
    }

    //顶面有三个错误，底面1个错误
    private void S4_Situation_U3D1()
    {
        int iDownBlock = 0;

        int iBlock1 = 0;
        int iBlock2 = 0;
        int iBlock3 = 0;

        iDownBlock = DREAM_CUBE_CHECKSTATUS.Plate.D[2][1];  //DB
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iDownBlock].Color.D != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iDownBlock].Color.D != E_COLOR.RED)
        {
            for (int i = 0; i < 4; i++)
            {
                iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];     //UL
                iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];     //UB
                iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];     //UR
                if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U != E_COLOR.RED))
                {
                    S4_Situation_ULUBURDB();
                    return;
                }
                RotationByPhysicalFormula("U");
            }
            return;
        }

        iDownBlock = DREAM_CUBE_CHECKSTATUS.Plate.D[0][1];      //DF
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iDownBlock].Color.D != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iDownBlock].Color.D != E_COLOR.RED)
        {
            for (int i = 0; i < 4; i++)
            {
                iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];     //UL
                iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];     //UF
                iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];     //UR
                if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U != E_COLOR.RED)
                        && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U != E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U != E_COLOR.RED))
                {
                    S4_Situation_ULUFURDF();
                    return;
                }
                RotationByPhysicalFormula("U");
            }
            return;
        }

        return;
    }


    //U面4个棱块颜色不正确
    private void S4_Situation_U4()
    { 
        RotationByPhysicalFormula("xUUxUUxUx");
    }

    //6个棱块颜色均不正确
    private void S4_Situation_U6()
    { 
        RotationByPhysicalFormula("xUxUUxUxuxUxUxUx");
    }


    //橙黄23/橙白21色块是否与相邻两个角块在一起
    private boolean S4_IsBlockStayWithFriend(int iBlockIndex)
    {
        int iBlock1 = 0;
        int iBlock2 = 0;
        int iBlock3 = 0;

        E_COLOR eColor = E_COLOR.NOCOLOR;

        if (iBlockIndex == 21)  //橙白
        {
            eColor = E_COLOR.WHITE;
        }
        else if (iBlockIndex == 23) //橙黄
        {
            eColor = E_COLOR.YELLOW;
        }
        else
        {
            return false;
        }
        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];
        iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];

        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.B == eColor)
        {
            return true;
        }

        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][2];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][2];
        iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][2];

        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.R == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.R == eColor)
        {
            return true;
        }

        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];
        iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][2];

        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.F == eColor)
        {
            return true;
        }

        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];
        iBlock3 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];

        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.U == E_COLOR.ORANGE
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.L == eColor
            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.L == eColor)
        {
            return true;
        }

        return false;
    }

    //橙黄23/橙白21色块是否在底面
    private boolean S4_IsBlockInDownPlate(int iBlockIndex)
    {
        E_COLOR eColor = E_COLOR.NOCOLOR;

        if (iBlockIndex == 21)  //橙白
        {
            eColor = E_COLOR.WHITE;
        }
        else if (iBlockIndex == 23) //橙黄
        {
            eColor = E_COLOR.YELLOW;
        }
        else
        {
            return false;
        }

        int iBlock1 = 0;
        int iBlock2 = 0;

        iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.D[0][1]; //DF
        iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.D[2][1]; //DB

        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == eColor)
            || (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B == eColor))
        {
            return true;
        }

        return false;
    }


    private void S4_TurnBlockIntoDownPlate(int iBlockIndex)
    {
        int i = 0;
        if (S4_IsBlockInDownPlate(iBlockIndex))
        {
            return;     //已经在底面
        }

        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_COLOR eAnotherBlockColor = E_COLOR.NOCOLOR;
        int iAnotherBlock = 0;

        if (iBlockIndex == 21)
        {
            eColor = E_COLOR.WHITE;
            eAnotherBlockColor = E_COLOR.YELLOW;
            iAnotherBlock = 23;
        }
        else if (iBlockIndex == 23)
        {
            eColor = E_COLOR.YELLOW;
            eAnotherBlockColor = E_COLOR.WHITE;
            iAnotherBlock = 21;
        }
        else
        {
            System.out.println(String.format("Parameter error, BlockIndex:%d", iBlockIndex));
            return;
        }

        int iBlock1 = 0;
        int iBlock2 = 0;
        if (S4_IsBlockInDownPlate(iAnotherBlock))
        { 
            //另一块在底面
            iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.D[0][1];   //DF

            for (i = 0; i < 4; i++)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == eAnotherBlockColor)
                {
                    //DF
                    iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1]; //UB
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
                                && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B == eColor)
                    {
                        //UB
                        RotationByPhysicalFormula("xUUX");
                        break;
                    }
                }
                else
                {
                    //DB
                    iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1]; //UF
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
                                && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F == eColor)
                    {
                        //UF
                        RotationByPhysicalFormula("XUUx");
                        break;
                    }
                }
                RotationByPhysicalFormula("u");
            }
        }
        else
        {
            //两块都在顶面
            for (i = 0; i < 4; i++)
            {
                iBlock1 = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1]; //UB
                iBlock2 = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1]; //UF
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.U == E_COLOR.ORANGE
                            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == eColor)
                {
                    //UB
                    RotationByPhysicalFormula("xUUX");
                    break;
                }
                else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.U == E_COLOR.ORANGE
                            && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F == eColor)
                {
                    //UF
                    RotationByPhysicalFormula("XUUx");
                    break;
                }
                RotationByPhysicalFormula("u");
            }
        }
    }
    
    private void S4_Step2()
    {
        if (S4_IsMatch())
        {
            return;
        }

        int i = 0;
        int iBlockWhite = 0;
        int iBlockYellow = 0;
        int iBlock = 0;

        S4_TurnBlockIntoDownPlate(21);
        S4_TurnBlockIntoDownPlate(23);
        
        if (!S4_IsBlockInDownPlate(21) || !S4_IsBlockInDownPlate(23))
        {
            System.out.println("block 21/23 not in down plate in S4_Step2()");
        }

        iBlockWhite = DREAM_CUBE_CHECKSTATUS.Plate.D[2][1];   //DB
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockWhite].Color.B == E_COLOR.WHITE)
        {
            //DB
            for (i = 0; i < 4; i++)
            {
                iBlockYellow = DREAM_CUBE_CHECKSTATUS.Plate.U[0][0];
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockYellow].Color.B == E_COLOR.YELLOW)
                {
                    break;
                }
                RotationByPhysicalFormula("u");
            }
        }
        else
        { 
            //DF
            for (i = 0; i < 4; i++)
            {
                iBlockYellow = DREAM_CUBE_CHECKSTATUS.Plate.U[2][0];
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockYellow].Color.F == E_COLOR.YELLOW)
                {
                    break;
                }
                RotationByPhysicalFormula("u");
            }
        }

        RotationByPhysicalFormula("xx");
        if (S4_IsBlockStayWithFriend(21) && S4_IsBlockStayWithFriend(23))
        {
            for (i = 0; i < 4; i++)
            {
                iBlockWhite = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockWhite].Color.L == E_COLOR.WHITE)
                {
                    break;
                }
                RotationByPhysicalFormula("u");     //将顶面转到正确位置
            }
        }

        //把中心块转回本位
        for (i = 0; i < 4; i++)
        {
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
            {
                break;
            }
            RotationByPhysicalFormula("x");
        }

    }

    //取得位置不对的方块数
    private int S4_GetNumOfPositonNotRight()
    {
        int iCount = 0;
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            { 
                case 0:
                    iBlock = 1;
                    break;
                case 1:
                    iBlock = 7;
                    break;
                case 2:
                    iBlock = 19;
                    break;
                case 3:
                    iBlock = 25;
                    break;
            }
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                iCount++;
            }
        }

        return iCount;        
    }

    //两个棱块位置不正确
    private void S4_Situation_E2()
    {
    	System.out.println("Unhandle situation in S4_Situation_E2()");
    }

    //三个棱块位置不正确
    private void S4_Situation_E3()
    {
        int iBlock = 0;
        int i = 0;

        iBlock = DREAM_CUBE_CHECKSTATUS.Plate.F[2][1];     //FD
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D == E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.BLUE)
        { 
            //正确方块在FD
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];  //UF
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
            {
                RotationByPhysicalFormula("xUUX");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][0];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.WHITE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("u");
                }
            }
            else
            {
                RotationByPhysicalFormula("UUxUU");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("x");
                }
            }

            return;
        }

        iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[2][1];     //UF
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.BLUE)
        {
            //正确方块在UF
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];  //UB
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.GREEN)
            {
                RotationByPhysicalFormula("xBBX");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.B[1][2];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.WHITE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("B");
                }
            }
            else
            {
                RotationByPhysicalFormula("BBxBB");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("x");
                }
            }

            return;
        }

        iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[0][1];     //UB
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.GREEN)
        {
            //正确方块在UB
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.B[2][1];  //BD
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D == E_COLOR.RED)
            {
                RotationByPhysicalFormula("xDDX");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.D[1][0];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.WHITE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("D");
                }
            }
            else
            {
                RotationByPhysicalFormula("DDxDD");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("x");
                }
            }
            return;
        }

        iBlock = DREAM_CUBE_CHECKSTATUS.Plate.B[2][1];     //BD
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D == E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.GREEN)
        {
            //正确方块在BD
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.F[2][1];  //FD
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.BLUE)
            {
                RotationByPhysicalFormula("xFFX");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.F[1][0];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.WHITE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("F");
                }
            }
            else
            {
                RotationByPhysicalFormula("FFxFF");
                for (i = 0; i < 4; i++)
                {
                    iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
                    {
                        break;
                    }
                    RotationByPhysicalFormula("x");
                }
            }
            return;
        }

    }

    //四个棱块位置不正确
    private void S4_Situation_E4()
    {
        int iBlockUF = GetBlockIndexByCoordinate(0, 1, 1, DREAM_CUBE_CHECKSTATUS);
        int iBlock = 0;
        int i = 0;


        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.U == E_COLOR.RED)
            && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.F == E_COLOR.GREEN))
        {
            //四个都不正确，交叉换棱（关于魔方中心对称的两组棱分别对换）E2ME2.
            RotationByPhysicalFormula("yyXyy");

        }
        else if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.U == E_COLOR.ORANGE)
            && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.F == E_COLOR.GREEN))
        {
            //四个都不正确，邻棱需要对换（即同一面上两棱对换）。U2M2U2
            RotationByPhysicalFormula("uuXXuu");
        }
        else if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.F == E_COLOR.BLUE)
            && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlockUF].Color.U == E_COLOR.RED))
        {
            //四个都不正确，邻棱需要对换（即同一面上两棱对换）。U2M2U2
            RotationByPhysicalFormula("ffXXff");
        }
        else
        {
            System.out.println("Unhandle situation in S4_Situation_E4()");
        }

        for (i = 0; i < 4; i++)
        {
            iBlock = DREAM_CUBE_CHECKSTATUS.Plate.U[1][1];
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
            {
                break;
            }
            RotationByPhysicalFormula("x");
        }
    }

    private void S4_Step3()
    {
        if (S4_IsMatch())
        {
            return;
        }

        int iCount = S4_GetNumOfPositonNotRight();

        switch (iCount)
        { 
            case 0:
                return;     //已经完成
            case 2:
            	S4_Situation_E2();
            	break;
            case 3:
                S4_Situation_E3();
                break;
            case 4:
                S4_Situation_E4();
                break;
        }

    }



    private void S4_RunInto()
    {
        S4_Step1();
        S4_Step2();
        S4_Step3();
    }

    public int GetMinLevel()
    {
    	return E_STUDY_STATUS.STUDY_STEP1.ordinal();
    }
    public int GetMaxLevel()
    {
    	return E_STUDY_STATUS.STUDY_STEP4.ordinal();
    }


}
