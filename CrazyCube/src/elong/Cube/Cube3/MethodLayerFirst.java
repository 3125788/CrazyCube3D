package elong.Cube.Cube3;


public class MethodLayerFirst extends CrazyCube{
	

    int S1_COMPARE_BLOCK_NUM = 9;
    int[] S1_CompareList;       //比较方块列表
    int S2_COMPARE_BLOCK_NUM = 13;
    int[] S2_CompareList;       //比较方块列表
    int S3_COMPARE_BLOCK_NUM = 17;
    int[] S3_CompareList;       //比较方块列表
    int S4_COMPARE_BLOCK_NUM = 21;
    int[] S4_CompareList;       //比较方块列表
    int S5_COMPARE_BLOCK_NUM = 25;
    int[] S5_CompareList;       //比较方块列表
    int S6_COMPARE_BLOCK_NUM = 25;
    int[] S6_CompareList;       //比较方块列表
    int S7_COMPARE_BLOCK_NUM = 25;
    int[] S7_CompareList;       //比较方块列表


    public MethodLayerFirst()
    {
        YDegree(270.0f);
        InitCompareList();
        InitCubeStepStatus();
        InitCubeStartStatus();
    }


    private void InitCompareList()
    {
        //设置合理的比较顺序，可以减少比较次数，以下是优化后的比较顺序
        S1_CompareList = new int[] { 1, 3, 4, 5, 7, 10, 12, 14, 16 };    //比较方块列表,S1_COMPARE_BLOCK_NUM
        S2_CompareList = new int[] { 10, 12, 14, 16, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S2_COMPARE_BLOCK_NUM
        S3_CompareList = new int[] { 9, 10, 11, 12, 14, 15, 16, 17, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S3_COMPARE_BLOCK_NUM
        S4_CompareList = new int[] { 19, 21, 23, 25, 9, 10, 11, 12, 14, 15, 16, 17, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S4_COMPARE_BLOCK_NUM
        S5_CompareList = new int[] { 18, 19, 20, 21, 23, 24, 25, 26, 9, 10, 11, 12, 14, 15, 16, 17, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S5_COMPARE_BLOCK_NUM
        S6_CompareList = new int[] { 19, 21, 23, 25, 18, 20, 24, 26, 9, 10, 11, 12, 14, 15, 16, 17, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S6_COMPARE_BLOCK_NUM
        S7_CompareList = new int[] { 18, 19, 20, 21, 23, 24, 25, 26, 9, 10, 11, 12, 14, 15, 16, 17, 0, 1, 2, 3, 4, 5, 6, 7, 8 };    //比较方块列表,S7_COMPARE_BLOCK_NUM
    }


    private void InitCubeStepStatus()
    {
        InitCoordinate(DREAM_CUBE_STEP1);
        InitCoordinate(DREAM_CUBE_STEP2);
        InitCoordinate(DREAM_CUBE_STEP3);
        InitCoordinate(DREAM_CUBE_STEP4);
        InitCoordinate(DREAM_CUBE_STEP5);
        InitCoordinate(DREAM_CUBE_STEP6);
        InitCoordinate(DREAM_CUBE_STEP7);

        InitStep1Color();
        InitStep2Color();
        InitStep3Color();
        InitStep4Color();
        InitStep5Color();
        InitStep6Color();
        InitStep7Color();

        InitPlate(DREAM_CUBE_STEP1);
        InitPlate(DREAM_CUBE_STEP2);
        InitPlate(DREAM_CUBE_STEP3);
        InitPlate(DREAM_CUBE_STEP4);
        InitPlate(DREAM_CUBE_STEP5);
        InitPlate(DREAM_CUBE_STEP6);
        InitPlate(DREAM_CUBE_STEP7);
    }

    private void InitStep1Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP1, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP1, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP1, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP1, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP1, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
    }

    private void InitStep2Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP2, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP2, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP2, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP2, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
    }

    private void InitStep3Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP3, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP3, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP3, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP3, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
    }

    private void InitStep4Color()
    {
        short i = 0;
        for (i = 0; i <= 26; i++)
        {
            SetColor(DREAM_CUBE_STEP4, i, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        }
        SetColor(DREAM_CUBE_STEP4, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 13, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP4, 18, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 19, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 20, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 21, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 22, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 23, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 24, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 25, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP4, 26, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
    }

    private void InitStep5Color()
    {
        SetColor(DREAM_CUBE_STEP5, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 13, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP5, 18, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 19, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 20, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 21, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 22, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 23, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 24, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 25, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP5, 26, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
    }

    private void InitStep6Color()
    {
        SetColor(DREAM_CUBE_STEP6, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 13, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 18, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 19, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 20, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP6, 21, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 22, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 23, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 24, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 25, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP6, 26, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
    }


    private void InitStep7Color()
    {
        SetColor(DREAM_CUBE_STEP7, 0, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 1, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 2, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 3, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 4, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 5, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 6, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 7, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 8, E_COLOR.NOCOLOR, E_COLOR.RED, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 9, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 10, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 11, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 12, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 13, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 14, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 15, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 16, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 17, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 18, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 19, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 20, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.GREEN, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 21, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 22, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 23, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
        SetColor(DREAM_CUBE_STEP7, 24, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.WHITE, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 25, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR);
        SetColor(DREAM_CUBE_STEP7, 26, E_COLOR.ORANGE, E_COLOR.NOCOLOR, E_COLOR.BLUE, E_COLOR.NOCOLOR, E_COLOR.NOCOLOR, E_COLOR.YELLOW);
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
        Boolean bOldEventStatus = GetEventStatus();

        SetEventStatus(false);

        InitCubeRandom(50);

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
            case 5:
                S1_RunInto();
                S2_RunInto();
                S3_RunInto();
                S4_RunInto();
                break;
            case 6:
                S1_RunInto();
                S2_RunInto();
                S3_RunInto();
                S4_RunInto();
                S5_RunInto();
                break;
            case 7:
                S1_RunInto();
                S2_RunInto();
                S3_RunInto();
                S4_RunInto();
                S5_RunInto();
                S6_RunInto();
                break;
        }
        SetEventStatus(bOldEventStatus);
        isAutoPlay = false;
        CheckCurrentStatus();
    }

    public void GetTrainningStep(int iSutdyLevel)
    {
        Boolean bOldEventStatus = GetEventStatus();            //保存原来的事件处理状态
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
            case 5:
                S5_RunInto();
                break;
            case 6:
                S6_RunInto();
                break;
            case 7:
                S7_RunInto();
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
        Boolean bDone = false;
        if (iStudyLevel >= E_STUDY_STATUS.STUDY_STEP1.ordinal() && iStudyLevel <= E_STUDY_STATUS.STUDY_STEP7.ordinal())
        {
            stTrainStatus.iStudyLevel = iStudyLevel;
            stTrainStatus.iStatus = iStudyLevel;
            while (!bDone)     //这个作用是防止训练级别不正确的情况，因为状态有可能处于更高级别
            {
                TrainningInit(iStudyLevel);
                bDone = true;
                for (int i = (int)(iStudyLevel - 1); i < 7; i++)
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
        if (S7_IsMatch())
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

        Boolean bOldEventStatus = GetEventStatus();           //保存原来的事件处理状态
        SetEventStatus(false);                             //设置成不使用事件

        SetGameMode(E_GAMEMODE.INIT_MODE);       //设置成游戏模式
        isAutoPlay = true;                             //设置AUTO标志

        CloneCube();

        ClearStep();                                   //清除步骤
        S1_RunInto();                                   //自动操作
        S2_RunInto();
        S3_RunInto();
        S4_RunInto();
        S5_RunInto();
        S6_RunInto();
        S7_RunInto();

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
        E_COMPLED_STATUS eCompledLevel = E_COMPLED_STATUS.COMPLED_NONE;

        if (isAutoPlay)        //自动运行时不上报事件
        {
            return;
        }

		TurnIntoStandarCube(DREAM_CUBE_CHECKSTATUS, false, true);

        if (S7_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS7;
        }
        else if (S6_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS6;
        }
        else if (S5_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS5;
        }
        else if (S4_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS4;
        }
        else if (S3_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS3;
        }
        else if (S2_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS2;
        }
        else if (S1_IsMatch())
        {
            eCompledLevel = E_COMPLED_STATUS.COMPLED_STATUS1;
        }

        SetAutoPlayStatus(eCompledLevel);

        if (eCompledLevel != E_COMPLED_STATUS.COMPLED_NONE)
        {
            RaiseCubeStatusChangedEvent(eCompledLevel);

        }

    }




    /*FBLR四个面按逆时针方向，取当前颜色的下一面*/
    private E_RET GetNextSidePlateUnClockwise(E_COLOR eColor, E_PLATE[] ePlate)
    {
        if (eColor != E_COLOR.BLUE && eColor != E_COLOR.GREEN && eColor != E_COLOR.WHITE && eColor != E_COLOR.YELLOW)
        {
            return E_RET.CUBE_ERR_COLOR;
        }
        switch (eColor)
        {
            case BLUE:
                ePlate[0] = E_PLATE.RIGHT;
                break;
            case YELLOW:
                ePlate[0] = E_PLATE.BACK;
                break;
            case GREEN:
                ePlate[0] = E_PLATE.LEFT;
                break;
            case WHITE:
                ePlate[0] = E_PLATE.FRONT;
                break;
            default:
                return E_RET.CUBE_ERR_COLOR;
        }
        return E_RET.CUBE_OK;
    }


    /*检查是否与第一步的状态匹配*/
    private Boolean IsMatchStep1Status()
    {
        return S1_IsMatch();
    }

    /*检查是否与第2步的状态匹配*/
    private Boolean IsMatchStep2Status()
    {
        return S2_IsMatch();
    }

    /*检查是否与第3步的状态匹配*/
    private Boolean IsMatchStep3Status()
    {
        return S3_IsMatch();
    }

    /*检查是否与第4步的状态匹配*/
    private Boolean IsMatchStep4Status()
    {
        return S4_IsMatch();
    }

    /*检查是否与第5步的状态匹配*/
    private Boolean IsMatchStep5Status()
    {
        return S5_IsMatch();
    }

    /*检查是否与第6步的状态匹配*/
    private Boolean IsMatchStep6Status()
    {
        return S6_IsMatch();
    }

    /*检查是否与第7步的状态匹配*/
    private Boolean IsMatchStep7Status()
    {
        return S7_IsMatch();
    }




    /*检查是否与第一步的状态匹配*/
    private Boolean S1_IsMatch()
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

    /*判断指定的方块是否在规定位置，只对方块1/3/5/7进行判断*/
    private Boolean S1_IsBlockInPosition(int iBlock)
    {
        int iBlock2 = 0;

        if (!IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.DOWN))    //如果蓝色不在底面，则肯定位置不对
        {
            return false;
        }
        switch (iBlock)
        {
            case 1:
                iBlock2 = 10;
                break;
            case 3:
                iBlock2 = 12;
                break;
            case 5:
                iBlock2 = 14;
                break;
            case 7:
                iBlock2 = 16;
                break;
            default:
                return false;
        }
        if (IsBlockInSpecificPosition(iBlock, iBlock) && IsBlockInSpecificPosition(iBlock, iBlock2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*是否在中间状态，如果是需要进行一步转换，然后才能到*/
    private Boolean S1_IsMiddleStatus(int iBlock)
    {
        if (iBlock != 1 && iBlock != 3 && iBlock != 5 && iBlock != 7)
        {
            return false;    //参数错误当成非中间状态处理
        }

        /*在顶面或底面肯定不是中间状态*/
        if (IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.UP) || IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.DOWN))
        {
            return false;
        }

        if ((IsBlockInSpecificPosition(iBlock, 9))
            || (IsBlockInSpecificPosition(iBlock, 11))
            || (IsBlockInSpecificPosition(iBlock, 15))
            || (IsBlockInSpecificPosition(iBlock, 17))
            )
        {   /*只要在9、11、15、17四个位置之一的都不是中间状态，与朝向无关*/
            return false;
        }
        return true;        //其余均为中间状态
    }


    /*非红色出现在哪个面上*/
    private E_RET S1_GetPlateIsnotRed(int iBlock, E_PLATE[] ePlate)
    {   /*注意：操作的方块只会有两种颜色，因为是棱块*/
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.UP;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.DOWN;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR_PLATE;     //代表出错
        }
        return E_RET.CUBE_OK;
    }

    /*红色棱块邻居颜色*/
    private E_COLOR S1_GetNeighbourColor(int iBlock)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.D;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
        }
        else
        {
            return E_COLOR.NOCOLOR;     //代表出错
        }
    }


    /*是否与朋友在一起*/
    private Boolean S1_IsStayWithFriend(int iBlock)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_PLATE ePlate2 = E_PLATE.UP;
        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_RET eRet = E_RET.CUBE_ERR;
        eRet = S1_GetPlateIsnotRed(iBlock, ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return false;
        }
        eColor = S1_GetNeighbourColor(iBlock);
        if (eColor == E_COLOR.NOCOLOR)
        {
            return false;
        }

        ePlate2 = GetColorPlatebyStandarColor(eColor);
        if (ePlate2 == E_PLATE.NOPLATE)
        {
            return false;
        }
        if (ePlate[0] == ePlate2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*退出中间状态*/
    private E_RET S1_OP_ExitMiddleStatus(int iBlock)
    {
        E_PLATE ePlate = E_PLATE.UP;
        E_RET eRet = E_RET.CUBE_ERR;
        ePlate = GetPlateWithColor(iBlock, E_COLOR.RED);
        if (ePlate == E_PLATE.NOPLATE)
        {
            System.out.println("Red not in valid 6 plate");
            return eRet;
        }

        switch (ePlate)         /*这里可以进行算法优化*/
        {
            case FRONT:
                RotationByPhysicalFormula("F");
                break;
            case BACK:
                RotationByPhysicalFormula("B");
                break;
            case LEFT:
                RotationByPhysicalFormula("L");
                break;
            case RIGHT:
                RotationByPhysicalFormula("R");
                break;
            default:
                System.out.println("Happen the wrong plate in S1_OP_ExitMiddleStatus()");
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }

    /*转到顶面*/
    private E_RET S1_OP_IntoUpPlate(int iBlock)
    {
        int iCount = 0;
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;
        eRet = S1_GetPlateIsnotRed(iBlock, ePlate);

        if (eRet != E_RET.CUBE_OK)
        {
            System.out.println("Color block not in valid 6 plate in S1_OP_IntoUpPlate()");
            return eRet;
        }

        do
        {
            switch (ePlate[0])
            {
                case FRONT:
                    RotationByPhysicalFormula("F");
                    break;
                case BACK:
                    RotationByPhysicalFormula("B");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("L");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("R");
                    break;
                default:
                    System.out.println("Happen wrong plate in S1_OP_IntoUpPlate()");
                    return E_RET.CUBE_ERR_PLATE;
            }
            iCount++;
            if (iCount > 4)
            {
                System.out.println("Can not turn into up plate in S1_OP_IntoUpPlate()");
                return E_RET.CUBE_ERR_OVERAROUND;
            }
        } while (!IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.UP));   //只要没有转到顶面，就一直转
        S1_OP_FindFriendOnUpPlate(iBlock);

        /*反过来转会iCount次*/
        for (int i = 0; i < iCount; i++)
        {
            switch (ePlate[0])
            {
                case FRONT:
                    RotationByPhysicalFormula("f");
                    break;
                case BACK:
                    RotationByPhysicalFormula("b");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("l");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("r");
                    break;
                default:
                    return E_RET.CUBE_ERR_PLATE;
            }
        }
        return E_RET.CUBE_OK;
    }

    /*转到底面*/
    private void S1_OP_IntoDownPlate(int iBlock)
    {
        int iCount = 0;
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;
        eRet = S1_GetPlateIsnotRed(iBlock, ePlate);

        if (eRet != E_RET.CUBE_OK)
        {
            System.out.println("Color block not in valid 6 plate");
            return;
        }

        do
        {
            switch (ePlate[0])
            {
                case FRONT:
                    RotationByPhysicalFormula("F");
                    break;
                case BACK:
                    RotationByPhysicalFormula("B");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("L");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("R");
                    break;
                default:
                    System.out.println("Happen wrong plate in S1_OP_IntoDownPlate()");
                    return;
            }
            iCount++;
            if (iCount > 4)
            {
                System.out.println("Get wrong plate in S1_OP_IntoDownPlate()");
                return;
            }
        } while (!IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.DOWN));   //只要没有转到底面，就一直转

    }

    private void S1_OP_FindFriendOnUpPlate(int iBlock)
    {
        int iCount = 0;
        while (!S1_IsStayWithFriend(iBlock))
        {
            //Rotation(E_ROTATIONEVENT.U, ref MAGIC_CUBE_CACULATE);
            RotationByPhysicalFormula("U");
            iCount++;
            if (iCount > 4)
            {
                System.out.println("Can find friend in S1_OP_FindFriendOnUpPlate()");
                return;
            }
        }
    }

    private void S1_RunInto()
    {
        int iCount = 0;
        int iBlock = 0xff;
        while (!S1_IsMatch())
        {
            for (int i = 0; i < 4; i++)
            {
                switch (i)
                {
                    case 0:
                        iBlock = 1;
                        break;
                    case 1:
                        iBlock = 3;
                        break;
                    case 2:
                        iBlock = 5;
                        break;
                    case 3:
                        iBlock = 7;
                        break;
                }

                if (!S1_IsBlockInPosition(iBlock))    //是否在指定位置
                {
                    if (S1_IsMiddleStatus(iBlock))    //是否在中间状态
                    {
                        S1_OP_ExitMiddleStatus(iBlock);
                    }
                    if (S1_IsStayWithFriend(iBlock))
                    {
                        S1_OP_IntoDownPlate(iBlock);    //直接转到底面结束
                    }
                    else
                    {
                        S1_OP_IntoUpPlate(iBlock);      //否则，先转到顶面，转换后转回底面
                        S1_OP_IntoDownPlate(iBlock);
                    }
                }
            }
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error");
                return;
            }
        }
    }

    /*检查是否与第2步的状态匹配*/
    private Boolean S2_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S2_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S2_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                return false;
            }
        }

        return true;

    }


    /*红色方块是否在y=1的ROW上，且不在顶面*/
    private Boolean S2_BlockInY1Row(int iBlock)
    {
        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.y == 1) && !IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.UP))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*红色方块是否在y=1的ROW上，且在顶面*/
    private Boolean S2_BlockInY1RowAndUpPlate(int iBlock)
    {
        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.y == 1) && IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.UP))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*红色方块是否在y=-1的ROW上，且不在底面*/
    private Boolean S2_BlockInY_1Row(int iBlock)
    {
        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.y == -1) && !IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.DOWN))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*红色方块是否在y=-1的ROW上，且在底面*/
    private Boolean S2_BlockInY_1RowAndDownPlate(int iBlock)
    {
        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.y == -1) && IsXColorInYPlate(iBlock, E_COLOR.RED, E_PLATE.DOWN))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*取Y1row上的邻居颜色*/
    private E_COLOR S2_GetNeighbourColorInY1Row(int iBlock)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
        }
        else
        {
            return E_COLOR.NOCOLOR;     //代表出错
        }
    }

    /*非红色出现在哪个面上*/
    private E_PLATE S2_GetPlateIsnotRed(int iBlock)
    {   /*注意：操作不考虑顶面及底面的颜色*/
        E_PLATE ePlate = E_PLATE.NOPLATE;
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            ePlate = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            ePlate = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            ePlate = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.RED && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            ePlate = E_PLATE.RIGHT;
        }
        else
        {
            ePlate = E_PLATE.NOPLATE;
        }
        return ePlate;
    }

    /*根据坐标位置取Y-1ROW，且在底面的操作面*/
    private E_RET S2_GetOpPlate4Y_1RowAndDownPlate(int iBlock, E_PLATE[] ePlate)
    {
        if (!S2_BlockInY_1RowAndDownPlate(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y3;     //代表错误
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[0].Position))
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[2].Position))
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[6].Position))
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.Equals(DREAM_CUBE_INITSTATUS.cube_status[8].Position))
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;

    }

    /*根据坐标位置取Y1ROW，且在顶面的操作面*/
    private E_RET S2_GetOpPlate4Y1RowAndUpPlate(int iBlock, E_PLATE[] ePlate)
    {
        if (!S2_BlockInY1RowAndUpPlate(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y1;
        }
        if (IsBlockInSpecificPosition(iBlock, 18))
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        else if (IsBlockInSpecificPosition(iBlock, 20))
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (IsBlockInSpecificPosition(iBlock, 24))
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (IsBlockInSpecificPosition(iBlock, 26))
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;

    }


    /*是否与朋友在一起*/
    private Boolean S2_IsStayWithFriend(int iBlock)
    {
        E_PLATE ePlate = E_PLATE.UP;
        E_PLATE ePlate2 = E_PLATE.UP;
        E_COLOR eColor = E_COLOR.NOCOLOR;
        ePlate = S2_GetPlateIsnotRed(iBlock);
        if (ePlate == E_PLATE.NOPLATE)
        {
            return false;
        }
        eColor = S2_GetNeighbourColorInY1Row(iBlock);
        if (eColor == E_COLOR.NOCOLOR)
        {
            return false;
        }

        ePlate2 = GetColorPlatebyStandarColor(eColor);
        if (ePlate2 == E_PLATE.NOPLATE)
        {
            return false;
        }
        if (ePlate == ePlate2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /*蓝色方块在Y1ROW上的方向，顺时针true或逆时针false*/
    private E_RET S2_GetDirectionInY1Row(int iBlock, Boolean[] bDirection)
    {
        if (!S2_BlockInY1Row(iBlock))
        {
            return E_RET.CUBE_ERR;
        }

        if (((IsBlockInSpecificPosition(iBlock, 18))
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.RED))
            || ((IsBlockInSpecificPosition(iBlock, 20))
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.RED))
            || ((IsBlockInSpecificPosition(iBlock, 24))
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.RED))
            || ((IsBlockInSpecificPosition(iBlock, 26))
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.RED))
            )
        {
            bDirection[0] = true;      //顺时针
        }
        else
        {
            bDirection[0] = false;     //逆时针
        }
        return E_RET.CUBE_OK;
    }

    /*蓝色方块在Y-1ROW上的方向，顺时针true或逆时针false*/
    private E_RET S2_GetDirectionInY_1Row(int iBlock, Boolean[] bDirection)
    {
        if (!S2_BlockInY_1Row(iBlock))
        {
            return E_RET.CUBE_ERR;
        }

        if ((IsBlockInSpecificPosition(iBlock, 0)
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.RED))
            || (IsBlockInSpecificPosition(iBlock, 2)
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.RED))
            || (IsBlockInSpecificPosition(iBlock, 6)
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.RED))
            || (IsBlockInSpecificPosition(iBlock, 8)
                && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.RED)))
        {
            bDirection[0] = true;      //顺时针
        }
        else
        {
            bDirection[0] = false;     //逆时针
        }
        return E_RET.CUBE_OK;
    }

    /*在Y1ROW上找朋友*/
    private E_RET S2_OP_FindFriendInY1Row(int iBlock)
    {
        Boolean[] bDirection = new Boolean[]{true};
        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_PLATE ePlate = E_PLATE.UP;
        E_RET eRet = E_RET.CUBE_ERR;

        eColor = S2_GetNeighbourColorInY1Row(iBlock);
        if (eColor == E_COLOR.NOCOLOR)
        {
            return E_RET.CUBE_ERR_NOCOLOR;
        }

        eRet = S2_GetDirectionInY1Row(iBlock, bDirection);
        if (eRet != E_RET.CUBE_OK)
        {
            System.out.println("Get direction error in S2_OP_FindFriendInY1Row()");
            return eRet;
        }

        while (!S2_IsStayWithFriend(iBlock))
        {
            if (bDirection[0])
            {
                RotationByPhysicalFormula("U");
            }
            else
            {
                RotationByPhysicalFormula("u");
            }
        }


        ePlate = GetColorPlatebyStandarColor(eColor);
        if (ePlate == E_PLATE.NOPLATE)
        {
            return eRet;
        }
        /*继续转90°*/
        if (bDirection[0])
        {
            RotationByPhysicalFormula("U");
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("F");
                    break;
                case BACK:
                    RotationByPhysicalFormula("B");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("L");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("R");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_FindFriendInY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }
        }
        else
        {
            RotationByPhysicalFormula("u");
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("f");
                    break;
                case BACK:
                    RotationByPhysicalFormula("b");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("l");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("r");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_FindFriendInY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }

        }

        /*反转90°*/
        if (bDirection[0])
        {
            RotationByPhysicalFormula("u");
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("f");
                    break;
                case BACK:
                    RotationByPhysicalFormula("b");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("l");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("r");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_FindFriendInY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }
        }
        else
        {
            RotationByPhysicalFormula("U");
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("F");
                    break;
                case BACK:
                    RotationByPhysicalFormula("B");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("L");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("R");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_FindFriendInY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }
        }
        return E_RET.CUBE_OK;
    }

    private E_RET S2_OP_TurnIntoY1Row(int iBlock)
    {
        Boolean[] bDirection = new Boolean[]{true};
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;

        if (S2_BlockInY_1Row(iBlock))
        {
            ePlate[0] = GetPlateWithColor(iBlock, E_COLOR.RED);
            if (ePlate[0] == E_PLATE.NOPLATE)
            {
                System.out.println("Get error plate in S2_OP_TurnIntoY1Row()");
                return eRet;
            }

            eRet = S2_GetDirectionInY_1Row(iBlock, bDirection);
            if (eRet != E_RET.CUBE_OK)
            {
                return eRet;
            }

            switch (ePlate[0])
            {
                case FRONT:
                    if (bDirection[0])
                    {
                        RotationByPhysicalFormula("FUf");
                    }
                    else
                    {
                        RotationByPhysicalFormula("fuF");
                    }
                    break;
                case BACK:
                    if (bDirection[0])
                    {
                        RotationByPhysicalFormula("BUb");
                    }
                    else
                    {
                        RotationByPhysicalFormula("buB");
                    }
                    break;
                case LEFT:
                    if (bDirection[0])
                    {
                        RotationByPhysicalFormula("LUl");
                    }
                    else
                    {
                        RotationByPhysicalFormula("luL");
                    }
                    break;
                case RIGHT:
                    if (bDirection[0])
                    {
                        RotationByPhysicalFormula("RUr");
                    }
                    else
                    {
                        RotationByPhysicalFormula("ruR");
                    }
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_TurnIntoY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }
        }
        else if (S2_BlockInY_1RowAndDownPlate(iBlock))
        {
            eRet = S2_GetOpPlate4Y_1RowAndDownPlate(iBlock, ePlate);

            if (eRet != E_RET.CUBE_OK)
            {
                System.out.println("Get error plate in S2_OP_TurnIntoY1Row()");
                return eRet;
            }
            switch (ePlate[0])
            {
                case FRONT:
                    RotationByPhysicalFormula("FUf");
                    break;
                case BACK:
                    RotationByPhysicalFormula("BUb");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("LUl");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("RUr");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_TurnIntoY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }

        }
        else if (S2_BlockInY1RowAndUpPlate(iBlock))
        {
            eRet = S2_GetOpPlate4Y1RowAndUpPlate(iBlock, ePlate);    //任取一面
            if (eRet != E_RET.CUBE_OK)
            {
                System.out.println("Get error plate in S2_OP_TurnIntoY1Row()");
                return eRet;
            }
            switch (ePlate[0])
            {
                case FRONT:
                    RotationByPhysicalFormula("Fuf");
                    break;
                case BACK:
                    RotationByPhysicalFormula("Bub");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("Lul");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("Rur");
                    break;
                default:
                    System.out.println("Happen wrong plate in S2_OP_TurnIntoY1Row()");
                    return E_RET.CUBE_ERR_PLATE;
            }
        }

        if (!S2_BlockInY1Row(iBlock))
        {
            System.out.println("Not in correct position in S2_OP_TurnIntoY1Row()");
            return E_RET.CUBE_ERR;
        }
        return E_RET.CUBE_OK;

    }

    private void S2_RunInto()
    {
        int iCount = 0;
        int iBlock = 0xff;
        if (!IsMatchStep1Status())
        {
            return;
        }

        while (!IsMatchStep2Status())
        {
            for (int i = 0; i < 4; i++)
            {
                switch (i)
                {
                    case 0:
                        iBlock = 0;
                        break;
                    case 1:
                        iBlock = 2;
                        break;
                    case 2:
                        iBlock = 6;
                        break;
                    case 3:
                        iBlock = 8;
                        break;
                }

                if (!IsBlockInCorrectPostion(iBlock))
                {
                    if (S2_BlockInY1Row(iBlock))
                    {
                        S2_OP_FindFriendInY1Row(iBlock);
                    }
                    else
                    {
                        S2_OP_TurnIntoY1Row(iBlock);
                        S2_OP_FindFriendInY1Row(iBlock);
                    }
                }
            }
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S2_RunInto()");
                return;
            }
        }

    }


    /*检查是否与第3步的状态匹配*/
    private Boolean S3_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S3_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S3_CompareList[i];
            if (IsBlockNotInCorrectPostion(iBlock))
            {
                return false;
            }
        }

        return true;

    }


    /*指定方块是否在Y1ROW上*/
    private Boolean S3_BlockInY1Row(int iBlock)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.y == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*取得Y1ROW上的那一面的颜色*/
    private E_COLOR S3_GetNeighbourColorInY1Row(int iBlock)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
        }
        else
        {
            return E_COLOR.NOCOLOR;     //代表出错
        }
    }

    /*取得Y1ROW上的那一面*/
    private E_RET S3_GetNeighbourPlateInY1Row(int iBlock, E_PLATE[] ePlate)
    {
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }

    /*取得Y1ROW上且在顶面的那一面的颜色*/
    private E_COLOR S3_GetUpPlateColorInY1Row(int iBlock)
    {
        return DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U;
    }


    /*是否与朋友在一起*/
    private Boolean S3_IsStayWithFriend(int iBlock)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_PLATE ePlate2 = E_PLATE.UP;
        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_RET eRet = E_RET.CUBE_ERR;

        eColor = S3_GetNeighbourColorInY1Row(iBlock);
        if (eColor == E_COLOR.NOCOLOR)
        {
            return false;
        }

        eRet = S3_GetNeighbourPlateInY1Row(iBlock, ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return false;
        }

        ePlate2 = GetColorPlatebyStandarColor(eColor);
        if (ePlate2 == E_PLATE.NOPLATE)
        {
            return false;
        }
        if (ePlate[0] == ePlate2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /*在Y1ROW上找朋友*/
    private E_RET S3_OP_FindFriendInY1Row(int iBlock)
    {
        if (!S3_BlockInY1Row(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y1;
        }

        while (!S3_IsStayWithFriend(iBlock))
        {
            //Rotation(E_ROTATIONEVENT.U, ref MAGIC_CUBE_CACULATE);
            RotationByPhysicalFormula("U");
        }
        return E_RET.CUBE_OK;
    }

    /*取Y2层的两个操作面*/
    private E_RET S3_GetBothOpPlateInY2Row(int iBlock, E_PLATE[] ePlate2)
    {
        if (S3_BlockInY1Row(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y2;
        }

        if (IsBlockInSpecificPosition(iBlock, 9))
        {
            ePlate2[0] = E_PLATE.BACK;
            ePlate2[1] = E_PLATE.LEFT;
        }
        else if (IsBlockInSpecificPosition(iBlock, 11))
        {
        	ePlate2[0] = E_PLATE.RIGHT;
        	ePlate2[1] = E_PLATE.BACK;
        }
        else if (IsBlockInSpecificPosition(iBlock, 15))
        {
        	ePlate2[0] = E_PLATE.LEFT;
        	ePlate2[1] = E_PLATE.FRONT;
        }
        else if (IsBlockInSpecificPosition(iBlock, 17))
        {
        	ePlate2[0] = E_PLATE.FRONT;
        	ePlate2[1] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR;
        }

        if (ePlate2[0] == ePlate2[1])
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        else
        {
            return E_RET.CUBE_OK;
        }
    }

    /*取Y1层的两个操作面及操作方向*/
    private E_RET S3_GetBothOpPlateAndDirectionInY1Row(int iBlock, E_PLATE[] ePlate2, Boolean[] bDirection)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_PLATE[] eFirstPlate = new E_PLATE[]{E_PLATE.UP};
        E_PLATE[] eSecondPlate = new E_PLATE[]{E_PLATE.UP};
        E_COLOR eColor = E_COLOR.NOCOLOR;
        E_RET eRet = E_RET.CUBE_ERR;

        if (!S3_BlockInY1Row(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y1;
        }

        eRet = S3_GetNeighbourPlateInY1Row(iBlock, eSecondPlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }

        eColor = S3_GetUpPlateColorInY1Row(iBlock);
        if (eColor == E_COLOR.NOCOLOR)
        {
            return E_RET.CUBE_ERR_NOCOLOR;
        }

        eFirstPlate[0] = GetColorPlatebyStandarColor(eColor);
        if (eFirstPlate[0] == E_PLATE.NOPLATE)
        {
            return eRet;
        }

        eRet = GetNextSidePlateUnClockwise(eColor, ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }

        if (ePlate[0] == eSecondPlate[0])
        {
            bDirection[0] = false;
        }
        else
        {
            bDirection[0] = true;
        }
        ePlate2[0] = eFirstPlate[0];
        ePlate2[1] = eSecondPlate[0];

        return E_RET.CUBE_OK;
    }


    /*将不再Y1ROW的方块转到Y1ROW*/
    private E_RET S3_OP_TurnIntoY1Row(int iBlock)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP, E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;

        if (S3_BlockInY1Row(iBlock))
        {
            return E_RET.CUBE_ERR_NOT_IN_Y2;
        }
        eRet = S3_GetBothOpPlateInY2Row(iBlock, ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }

        switch (ePlate[0])
        {
            case FRONT:
                RotationByPhysicalFormula("fUFU");
                break;
            case BACK:
                RotationByPhysicalFormula("bUBU");
                break;
            case LEFT:
                RotationByPhysicalFormula("lULU");
                break;
            case RIGHT:
                RotationByPhysicalFormula("rURU");
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }

        switch (ePlate[1])
        {
            case FRONT:
                RotationByPhysicalFormula("Fuf");
                break;
            case BACK:
                RotationByPhysicalFormula("Bub");
                break;
            case LEFT:
                RotationByPhysicalFormula("Lul");
                break;
            case RIGHT:
                RotationByPhysicalFormula("Rur");
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }

        return E_RET.CUBE_OK;
    }

    private E_RET S3_OP_TurnIntoPostion(int iBlock)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP, E_PLATE.UP};

        Boolean[] bDirection = new Boolean[]{true};

        E_RET eRet = E_RET.CUBE_ERR;

        eRet = S3_GetBothOpPlateAndDirectionInY1Row(iBlock, ePlate, bDirection);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }

        switch (ePlate[0])
        {
            case FRONT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("UFuf");
                }
                else
                {
                    RotationByPhysicalFormula("ufUF");
                }
                break;
            case BACK:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("UBub");
                }
                else
                {
                    RotationByPhysicalFormula("ubUB");
                }
                break;
            case LEFT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("ULul");
                }
                else
                {
                    RotationByPhysicalFormula("ulUL");
                }
                break;
            case RIGHT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("URur");
                }
                else
                {
                    RotationByPhysicalFormula("urUR");
                }
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }

        switch (ePlate[1])
        {
            case FRONT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("ufUF");
                }
                else
                {
                    RotationByPhysicalFormula("UFuf");
                }
                break;
            case BACK:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("ubUB");
                }
                else
                {
                    RotationByPhysicalFormula("UBub");
                }
                break;
            case LEFT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("ulUL");
                }
                else
                {
                    RotationByPhysicalFormula("ULul");
                }
                break;
            case RIGHT:
                if (bDirection[0])
                {
                    RotationByPhysicalFormula("urUR");
                }
                else
                {
                    RotationByPhysicalFormula("URur");
                }
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }


    private void S3_RunInto()
    {
        int iCount = 0;
        int iBlock = 0xff;
        E_RET eRet = E_RET.CUBE_ERR;

        if (!IsMatchStep1Status())
        {
            return;
        }

        if (!IsMatchStep2Status())
        {
            return;
        }

        while (!IsMatchStep3Status())
        {
            for (int i = 0; i < 4; i++)
            {
                switch (i)
                {
                    case 0:
                        iBlock = 9;
                        break;
                    case 1:
                        iBlock = 11;
                        break;
                    case 2:
                        iBlock = 15;
                        break;
                    case 3:
                        iBlock = 17;
                        break;
                }

                if (!IsBlockInCorrectPostion(iBlock))
                {
                    if (S3_BlockInY1Row(iBlock))
                    {
                        S3_OP_FindFriendInY1Row(iBlock);
                    }
                    else
                    {
                        S3_OP_TurnIntoY1Row(iBlock);
                        S3_OP_FindFriendInY1Row(iBlock);
                    }
                    eRet = S3_OP_TurnIntoPostion(iBlock);
                    if (eRet != E_RET.CUBE_OK)
                    {
                        System.out.println("Get error in S3_RunInto()");
                    }
                }
            }
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S3_RunInto()");
                return;
            }
        }

    }


    /*检查是否与第4步的状态匹配*/
    private Boolean S4_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S4_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S4_CompareList[i];

            if (i < 4)
            {
                if (!IsXColorInYPlate(iBlock, E_COLOR.ORANGE, E_PLATE.UP))    //如果不在顶面，状态也是错误的
                {
                    return false;
                }

            }
            else
            {
                if (IsBlockNotInCorrectPostion(iBlock))
                {
                    return false;
                }
            }
        }
        return true;

    }


    /*指定坐标的方块是否为橙色*/
    private Boolean S4_SpecificBlockInXZIsOrange(int iX, int iZ)
    {
        int iBlock = 19;
        for (int i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 19;
                    break;
                case 1:
                    iBlock = 21;
                    break;
                case 2:
                    iBlock = 23;
                    break;
                case 3:
                    iBlock = 25;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX
                && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ
                && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
            {
                return true;
            }
        }
        return false;
    }

    /*上面的颜色是否为橙色*/
    private Boolean S4_BlockUpColorIsOrange(int iBlock)
    {
        if (iBlock != 19 && iBlock != 21 && iBlock != 23 && iBlock != 25)
        {
            return false;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*十字状态*/
    private Boolean S4_UpPlateStatusIsCross()
    {
        if (S4_SpecificBlockInXZIsOrange(-1, 0) && S4_SpecificBlockInXZIsOrange(0, -1) && S4_SpecificBlockInXZIsOrange(1, 0) && S4_SpecificBlockInXZIsOrange(0, 1))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*一字状态*/
    private Boolean S4_UpPlateStatusIsOne()
    {
        if ((S4_SpecificBlockInXZIsOrange(0, -1) && S4_SpecificBlockInXZIsOrange(0, 1) && (!S4_SpecificBlockInXZIsOrange(-1, 0)) && (!S4_SpecificBlockInXZIsOrange(1, 0)))
            || (S4_SpecificBlockInXZIsOrange(-1, 0) && S4_SpecificBlockInXZIsOrange(1, 0) && (!S4_SpecificBlockInXZIsOrange(0, -1)) && (!S4_SpecificBlockInXZIsOrange(0, 1))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*角状态*/
    private Boolean S4_UpPlateStatusIsCorner()
    {
        if (S4_UpPlateStatusIsCross())
        {
            return false;
        }
        if (S4_UpPlateStatusIsOne())
        {
            return false;
        }
        if (S4_GetNum4UpPlateIsOrange() >= 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*点状态*/
    private Boolean S4_UpPlateStatusIsPoint()
    {
        if (S4_GetNum4UpPlateIsOrange() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*统计19、21、23、25四个方块在顶面为橙色的个数*/
    private int S4_GetNum4UpPlateIsOrange()
    {
        int iBlock = 19;
        int i;
        int iCount = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 19;
                    break;
                case 1:
                    iBlock = 21;
                    break;
                case 2:
                    iBlock = 23;
                    break;
                case 3:
                    iBlock = 25;
                    break;
            }
            if (S4_BlockUpColorIsOrange(iBlock))
            {
                iCount++;
            }
        }
        return iCount;
    }


    private E_RET S4_GetUpPlateStatus(int[] iStatus)
    {
        if (S4_UpPlateStatusIsCross())
        {
            iStatus[0] = 0;
        }
        else if (S4_UpPlateStatusIsCorner())
        {
            iStatus[0] = 1;
        }
        else if (S4_UpPlateStatusIsOne())
        {
            iStatus[0] = 2;
        }
        else if (S4_UpPlateStatusIsPoint())
        {
            iStatus[0] = 3;
        }
        else
        {
            return E_RET.CUBE_ERR_UNHANDLE;
        }
        return E_RET.CUBE_OK;
    }

    private void S4_SelectOpPlate(int iStatus, E_PLATE[] ePlate2)
    {
        if (0 == iStatus)
        {
            return;
        }

        switch (iStatus)
        {
            case 1:
                if (S4_SpecificBlockInXZIsOrange(-1, 0) && S4_SpecificBlockInXZIsOrange(0, -1))
                {
                    ePlate2[0] = E_PLATE.RIGHT;
                    ePlate2[1] = E_PLATE.FRONT;
                }
                else if (S4_SpecificBlockInXZIsOrange(1, 0) && S4_SpecificBlockInXZIsOrange(0, -1))
                {
                    ePlate2[0] = E_PLATE.FRONT;
                    ePlate2[1] = E_PLATE.LEFT;
                }
                else if (S4_SpecificBlockInXZIsOrange(1, 0) && S4_SpecificBlockInXZIsOrange(0, 1))
                {
                    ePlate2[0] = E_PLATE.LEFT;
                    ePlate2[1] = E_PLATE.BACK;
                }
                else if (S4_SpecificBlockInXZIsOrange(-1, 0) && S4_SpecificBlockInXZIsOrange(0, 1))
                {
                    ePlate2[0] = E_PLATE.BACK;
                    ePlate2[1] = E_PLATE.RIGHT;
                }
                break;
            case 2:
                if (S4_SpecificBlockInXZIsOrange(-1, 0) && S4_SpecificBlockInXZIsOrange(1, 0))
                {
                    ePlate2[0] = E_PLATE.FRONT;
                    ePlate2[1] = E_PLATE.LEFT;
                }
                else if (S4_SpecificBlockInXZIsOrange(0, -1) && S4_SpecificBlockInXZIsOrange(0, 1))
                {
                    ePlate2[0] = E_PLATE.RIGHT;
                    ePlate2[1] = E_PLATE.FRONT;
                }
                break;
            case 3:
                ePlate2[0] = E_PLATE.RIGHT;
                ePlate2[1] = E_PLATE.FRONT;
                break;
            default:
                return;
        }
    }

    private E_RET S4_OP_TurnIntoPosition(E_PLATE[] ePlate2)
    {
        if (ePlate2[0] != E_PLATE.FRONT && ePlate2[0] != E_PLATE.BACK && ePlate2[0] != E_PLATE.LEFT && ePlate2[0] != E_PLATE.RIGHT)
        {
            return E_RET.CUBE_ERR_PLATE;
        }

        if (ePlate2[1] != E_PLATE.FRONT && ePlate2[1] != E_PLATE.BACK && ePlate2[1] != E_PLATE.LEFT && ePlate2[1] != E_PLATE.RIGHT)
        {
            return E_RET.CUBE_ERR_PLATE;
        }

        switch (ePlate2[0])
        {
            case FRONT:
                RotationByPhysicalFormula("f");
                break;
            case BACK:
                RotationByPhysicalFormula("b");
                break;
            case LEFT:
                RotationByPhysicalFormula("l");
                break;
            case RIGHT:
                RotationByPhysicalFormula("r");
                break;
        }

        RotationByPhysicalFormula("u");

        switch (ePlate2[1])
        {
            case FRONT:
                RotationByPhysicalFormula("f");
                break;
            case BACK:
                RotationByPhysicalFormula("b");
                break;
            case LEFT:
                RotationByPhysicalFormula("l");
                break;
            case RIGHT:
                RotationByPhysicalFormula("r");
                break;
        }

        RotationByPhysicalFormula("U");

        switch (ePlate2[1])
        {
            case FRONT:
                RotationByPhysicalFormula("F");
                break;
            case BACK:
                RotationByPhysicalFormula("B");
                break;
            case LEFT:
                RotationByPhysicalFormula("L");
                break;
            case RIGHT:
                RotationByPhysicalFormula("R");
                break;
        }

        switch (ePlate2[0])
        {
            case FRONT:
                RotationByPhysicalFormula("F");
                break;
            case BACK:
                RotationByPhysicalFormula("B");
                break;
            case LEFT:
                RotationByPhysicalFormula("L");
                break;
            case RIGHT:
                RotationByPhysicalFormula("R");
                break;
        }

        return E_RET.CUBE_OK;
    }

    private void S4_RunInto()
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP, E_PLATE.UP};
        int[] iStatus = new int[]{0};
        int iCount = 0;
        E_RET eRet = E_RET.CUBE_ERR;

        if (!IsMatchStep1Status())
        {
            return;
        }

        if (!IsMatchStep2Status())
        {
            return;
        }

        if (!IsMatchStep3Status())
        {
            return;
        }

        while (!IsMatchStep4Status())
        {
            eRet = S4_GetUpPlateStatus(iStatus);
            if (eRet != E_RET.CUBE_OK)
            {
                System.out.println("Unhandle error in S4_RunInto()");
            }

            if (iStatus[0] != 0)
            {
                S4_SelectOpPlate(iStatus[0], ePlate);
                S4_OP_TurnIntoPosition(ePlate);
            }
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S4_RunInto()");
                return;
            }

        }

    }


    public class S5_Position
    {
        public int key;
        public E_PLATE plate;
    }

    /*检查是否与第5步的状态匹配*/
    private Boolean S5_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S5_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S5_CompareList[i];

            if (i < 8)
            {
                if (!IsXColorInYPlate(iBlock, E_COLOR.ORANGE, E_PLATE.UP))    //如果不在顶面，状态也是错误的
                {
                    return false;
                }

            }
            else
            {
                if (IsBlockNotInCorrectPostion(iBlock))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /*指定方块上面的颜色是否为橙色*/
    protected Boolean S5_BlockUpColorIsOrange(int iBlock)
    {
        if (iBlock < 18 || iBlock > 26)
        {
            return false;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.U == E_COLOR.ORANGE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /*取顶面不是橙色的方块个数*/
    private int S5_GetNum4UpPlateIsnotOrange()
    {
        int iCount = 0;

        for (int i = 18; i <= 26; i++)
        {
            if (!S5_BlockUpColorIsOrange(i))
            {
                iCount++;
            }
        }
        return iCount;
    }

    private E_RET S5_GetOrangePlateY1Corner(int iBlock, E_PLATE[] ePlate)
    {
        if (iBlock != 18 && iBlock != 20 && iBlock != 24 && iBlock != 26)
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.ORANGE)
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.ORANGE)
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.ORANGE)
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.ORANGE)
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else
        {
            return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }



    private E_RET S5_GetOperatePlateFor2Corner(E_PLATE[] ePlate)
    {
        int i = 0;
        int iBlock = 18;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 20;
                    break;
                case 2:
                    iBlock = 24;
                    break;
                case 3:
                    iBlock = 26;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == -1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == -1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.RIGHT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == 1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == -1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.FRONT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == 1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == 1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.LEFT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == -1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == 1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.BACK;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
        }
        return E_RET.CUBE_ERR_PLATE;
    }

    private E_RET S5_GetOperatePlateFor3Corner(E_PLATE[] ePlate)
    {
        S5_Position[] stPlate = new S5_Position[4];
        E_PLATE eLeftPlate = E_PLATE.UP;
        E_PLATE[] ePlate1 = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;
        int i = 0;
        int iBlock = 18;

        stPlate[0] = new S5_Position();
        stPlate[1] = new S5_Position();
        stPlate[2] = new S5_Position();
        stPlate[3] = new S5_Position();
        stPlate[0].plate = E_PLATE.FRONT;
        stPlate[1].plate = E_PLATE.RIGHT;
        stPlate[2].plate = E_PLATE.BACK;
        stPlate[3].plate = E_PLATE.LEFT;
        stPlate[0].key = 0;
        stPlate[1].key = 0;
        stPlate[2].key = 0;
        stPlate[3].key = 0;


        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 20;
                    break;
                case 2:
                    iBlock = 24;
                    break;
                case 3:
                    iBlock = 26;
                    break;
            }

            eRet = S5_GetOrangePlateY1Corner(iBlock, ePlate1);
            if (eRet != E_RET.CUBE_OK)
            {
                continue;
            }
            if (ePlate1[0] == E_PLATE.FRONT)
            {
                stPlate[0].key = 1;
            }
            else if (ePlate1[0] == E_PLATE.RIGHT)
            {
                stPlate[1].key = 1;
            }
            else if (ePlate1[0] == E_PLATE.BACK)
            {
                stPlate[2].key = 1;
            }
            else if (ePlate1[0] == E_PLATE.LEFT)
            {
                stPlate[3].key = 1;
            }
        }


        for (i = 0; i < 4; i++)
        {
            if (stPlate[i].key == 0)
            {
                eLeftPlate = stPlate[i].plate;
                break;
            }
        }

        if (eLeftPlate == E_PLATE.FRONT)
        {
            ePlate[0] = E_PLATE.BACK;
        }
        else if (eLeftPlate == E_PLATE.BACK)
        {
            ePlate[0] = E_PLATE.FRONT;
        }
        else if (eLeftPlate == E_PLATE.LEFT)
        {
            ePlate[0] = E_PLATE.RIGHT;
        }
        else if (eLeftPlate == E_PLATE.RIGHT)
        {
            ePlate[0] = E_PLATE.LEFT;
        }
        return E_RET.CUBE_OK;
    }


    private E_RET S5_GetOperatePlateFor4Corner(E_PLATE[] ePlate)
    {
        int i = 0;
        int iBlock = 18;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 20;
                    break;
                case 2:
                    iBlock = 24;
                    break;
                case 3:
                    iBlock = 26;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == -1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == -1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.RIGHT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == 1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == -1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.FRONT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == 1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == 1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.LEFT;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
            else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == -1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == 1)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.ORANGE)
                {
                    ePlate[0] = E_PLATE.BACK;
                    return E_RET.CUBE_OK;
                }
                else
                {
                }
            }
        }
        return E_RET.CUBE_ERR_PLATE;
    }


    private E_RET S5_GetDirectionForXZ(int iX, int iZ, Boolean[] bDirection)
    {
        int i = 0;
        int iBlock = 18;

        if (!(-1 == iX && -1 == iZ) && !(1 == iX && -1 == iZ) && !(1 == iX && 1 == iZ) && !(-1 == iX && 1 == iZ))
        {
            return E_RET.CUBE_ERR_OPERATEBLOCK;
        }

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 20;
                    break;
                case 2:
                    iBlock = 24;
                    break;
                case 3:
                    iBlock = 26;
                    break;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ)
            {
                if (-1 == iX && -1 == iZ)
                {
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.ORANGE)
                    {
                        bDirection[0] = true;
                        return E_RET.CUBE_OK;
                    }
                    else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.ORANGE)
                    {
                        bDirection[0] = false;
                        return E_RET.CUBE_OK;
                    }
                    else
                    {
                    }
                }
                else if (1 == iX && -1 == iZ)
                {
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B == E_COLOR.ORANGE)
                    {
                        bDirection[0] = true;
                        return E_RET.CUBE_OK;
                    }
                    else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.ORANGE)
                    {
                        bDirection[0] = false;
                        return E_RET.CUBE_OK;
                    }
                    else
                    {
                    }
                }
                else if (1 == iX && 1 == iZ)
                {
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R == E_COLOR.ORANGE)
                    {
                        bDirection[0] = true;
                        return E_RET.CUBE_OK;
                    }
                    else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.ORANGE)
                    {
                        bDirection[0] = false;
                        return E_RET.CUBE_OK;
                    }
                    else
                    {
                    }
                }
                else if (-1 == iX && 1 == iZ)
                {
                    if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F == E_COLOR.ORANGE)
                    {
                        bDirection[0] = true;
                        return E_RET.CUBE_OK;
                    }
                    else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L == E_COLOR.ORANGE)
                    {
                        bDirection[0] = false;
                        return E_RET.CUBE_OK;
                    }
                    else
                    {
                    }
                }

            }
        }
        return E_RET.CUBE_ERR_DIRECTION;

    }


    private E_RET S5_GetDirectionFor3Corner(Boolean[] bDirection)
    {
        int i = 0;
        int iX = 0;
        int iZ = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iX = -1;
                    iZ = -1;
                    break;
                case 1:
                    iX = 1;
                    iZ = -1;
                    break;
                case 2:
                    iX = 1;
                    iZ = 1;
                    break;
                case 3:
                    iX = -1;
                    iZ = 1;
                    break;
            }
            if (E_RET.CUBE_OK == S5_GetDirectionForXZ(iX, iZ, bDirection))
            {
                return E_RET.CUBE_OK;
            }

        }
        return E_RET.CUBE_ERR_DIRECTION;
    }

    private E_RET S5_GetOperatePlateAndDirction(E_PLATE[] ePlate, Boolean[] bDirection)
    {
        E_RET iRet = E_RET.CUBE_ERR;

        int iNum = 0;

        iNum = S5_GetNum4UpPlateIsnotOrange();

        switch (iNum)
        {
            case 3:
                iRet = S5_GetOperatePlateFor3Corner(ePlate);
                if (iRet != E_RET.CUBE_OK)
                {
                    return iRet;
                }
                iRet = S5_GetDirectionFor3Corner(bDirection);
                if (iRet != E_RET.CUBE_OK)
                {
                    return iRet;
                }
                break;
            case 2:
                iRet = S5_GetOperatePlateFor2Corner(ePlate);
                if (iRet != E_RET.CUBE_OK)
                {
                    return iRet;
                }
                bDirection[0] = true;
                break;
            case 4:
                iRet = S5_GetOperatePlateFor4Corner(ePlate);
                if (iRet != E_RET.CUBE_OK)
                {
                    return iRet;
                }
                bDirection[0] = true;
                break;
            default:
                return E_RET.CUBE_ERR;
        }
        return E_RET.CUBE_OK;
    }


    private void S5_OP_TurnIntoPosition(Boolean bDirction, E_PLATE ePlate)
    {
        if (bDirction)
        {
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("fuFufuuF");
                    break;
                case BACK:
                    RotationByPhysicalFormula("buBubuuB");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("luLuluuL");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("ruRuruuR");
                    break;
                default:
                    System.out.println("Happen wrong plate in S5_OP_TurnIntoPosition()");
                    return;
            }
        }
        else
        {
            switch (ePlate)
            {
                case FRONT:
                    RotationByPhysicalFormula("FUfUFUUf");
                    break;
                case BACK:
                    RotationByPhysicalFormula("BUbUBUUb");
                    break;
                case LEFT:
                    RotationByPhysicalFormula("LUlULUUl");
                    break;
                case RIGHT:
                    RotationByPhysicalFormula("RUrURUUr");
                    break;
                default:
                	System.out.println("Happen wrong plate in S5_OP_TurnIntoPosition()");
                    return;
            }

        }
    }

    private void S5_RunInto()
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        Boolean[] bDirection = new Boolean[]{true};
        E_RET eRet = E_RET.CUBE_ERR;
        int iCount = 0;

        if (!IsMatchStep1Status())
        {
            return;
        }

        if (!IsMatchStep2Status())
        {
            return;
        }

        if (!IsMatchStep3Status())
        {
            return;
        }

        if (!IsMatchStep4Status())
        {
            return;
        }

        while (!IsMatchStep5Status())
        {
            eRet = S5_GetOperatePlateAndDirction(ePlate, bDirection);
            if (eRet != E_RET.CUBE_OK)
            {
                System.out.println("Unhandle error in S5_RunInto()");
            }

            S5_OP_TurnIntoPosition(bDirection[0], ePlate[0]);
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S5_RunInto()");
                return;
            }

        }

    }



    /*检查是否与第6步的状态匹配*/
    private Boolean S6_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S6_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S6_CompareList[i];

            if (i < 4)
            {
                if (!IsXColorInYPlate(iBlock, E_COLOR.ORANGE, E_PLATE.UP))    //如果不在顶面，状态也是错误的
                {
                    return false;
                }

            }
            else
            {
                if (IsBlockNotInCorrectPostion(iBlock))
                {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean S6_HaveSameColor(int iX1, int iZ1, int iX2, int iZ2)
    {
        int i = 0;
        int iBlock = 18;
        int iBlock1 = 0;
        int iBlock2 = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 20;
                    break;
                case 2:
                    iBlock = 24;
                    break;
                case 3:
                    iBlock = 26;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ1)
            {
                iBlock1 = iBlock;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX2 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ2)
            {
                iBlock2 = iBlock;
            }
        }

        if (((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.L))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.R)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private E_RET S6_GetPlateWithSameColor(E_PLATE[] ePlate)
    {
        int i = 0;
        int iX1 = 0;
        int iX2 = 0;
        int iZ1 = 0;
        int iZ2 = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iX1 = -1;
                    iX2 = 1;
                    iZ1 = -1;
                    iZ2 = -1;
                    if (S6_HaveSameColor(iX1, iZ1, iX2, iZ2))
                    {
                        ePlate[0] = E_PLATE.BACK;
                        return E_RET.CUBE_OK;
                    }
                    break;
                case 1:
                    iX1 = 1;
                    iX2 = 1;
                    iZ1 = -1;
                    iZ2 = 1;
                    if (S6_HaveSameColor(iX1, iZ1, iX2, iZ2))
                    {
                        ePlate[0] = E_PLATE.RIGHT;
                        return E_RET.CUBE_OK;
                    }
                    break;
                case 2:
                    iX1 = 1;
                    iX2 = -1;
                    iZ1 = 1;
                    iZ2 = 1;
                    if (S6_HaveSameColor(iX1, iZ1, iX2, iZ2))
                    {
                        ePlate[0] = E_PLATE.FRONT;
                        return E_RET.CUBE_OK;
                    }
                    break;
                case 3:
                    iX1 = -1;
                    iX2 = -1;
                    iZ1 = 1;
                    iZ2 = -1;
                    if (S6_HaveSameColor(iX1, iZ1, iX2, iZ2))
                    {
                        ePlate[0] = E_PLATE.LEFT;
                        return E_RET.CUBE_OK;
                    }
                    break;
                default:
                    return E_RET.CUBE_ERR;
            }

        }
        return E_RET.CUBE_ERR;
    }

    private E_RET S6_GetOperatePlate(E_PLATE[] ePlate)
    {
        if (E_RET.CUBE_OK != S6_GetPlateWithSameColor(ePlate))
        {
            ePlate[0] = E_PLATE.BACK;
        }
        return E_RET.CUBE_OK;
    }


    private void S6_OP_TurnIntoPosition(E_PLATE ePlate)
    {
        switch (ePlate)
        {
            case FRONT:
                RotationByPhysicalFormula("RbRFFrBRFFRR");
                break;
            case BACK:
                RotationByPhysicalFormula("LfLBBlFLBBLL");
                break;
            case LEFT:
                RotationByPhysicalFormula("FrFLLfRFLLFF");
                break;
            case RIGHT:
                RotationByPhysicalFormula("BlBRRbLBRRBB");
                break;
            default:
                return;
        }

    }

    private void S6_OP_TurnUpPlate()
    {
        int iCount = 0;
        while (!IsMatchStep6Status())
        {
            RotationByPhysicalFormula("U");
            iCount++;
            if (iCount >= 4)
            {
                return;
            }
        }
    }

    private void S6_RunInto()
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;
        int iCount = 0;

        if (!IsMatchStep1Status())
        {
            return;
        }

        if (!IsMatchStep2Status())
        {
            return;
        }

        if (!IsMatchStep3Status())
        {
            return;
        }

        if (!IsMatchStep4Status())
        {
            return;
        }

        if (!IsMatchStep5Status())
        {
            return;
        }

        if (IsMatchStep7Status())
        {
            return;
        }
        while (!IsMatchStep6Status())
        {
            eRet = S6_GetOperatePlate(ePlate);
            if (eRet != E_RET.CUBE_OK)
            {
                System.out.println("Get error in S6_RunInto()");
            }

            S6_OP_TurnIntoPosition(ePlate[0]);

            S6_OP_TurnUpPlate();    //有可能需要旋转一下顶面位置才正确

            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S6_RunInto()");
                return;
            }

        }

    }



    /*检查是否与第7步的状态匹配*/
    private Boolean S7_IsMatch()
    {
        int i = 0;
        int iBlock = 0;

        for (i = 0; i < S7_COMPARE_BLOCK_NUM; i++)
        {
            iBlock = S7_CompareList[i];

            if (IsBlockNotInCorrectPostion(iBlock))
            {
                return false;
            }

        }
        return true;
    }
    
    
    /*指定Y1ROW是否同色*/
    private Boolean S7_IsSameColor(int iX1, int iZ1, int iX2, int iZ2, int iX3, int iZ3)
    {
        int i = 0;
        int iBlock = 18;
        int iBlock1 = 0;
        int iBlock2 = 0;
        int iBlock3 = 0;

        for (i = 0; i < 8; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 19;
                    break;
                case 2:
                    iBlock = 20;
                    break;
                case 3:
                    iBlock = 21;
                    break;
                case 4:
                    iBlock = 23;
                    break;
                case 5:
                    iBlock = 24;
                    break;
                case 6:
                    iBlock = 25;
                    break;
                case 7:
                    iBlock = 26;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ1)
            {
                iBlock1 = iBlock;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX2 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ2)
            {
                iBlock2 = iBlock;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX3 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ3)
            {
                iBlock3 = iBlock;
            }
        }

        if (((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.F))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.B))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.L) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.L))
            || ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.R) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.R)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    private Boolean S7_HaveSameColor()
    {
        int i = 0;
        int iX1 = 0;
        int iX2 = 0;
        int iX3 = 0;
        int iZ1 = 0;
        int iZ2 = 0;
        int iZ3 = 0;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iX1 = -1;
                    iX2 = 1;
                    iX3 = 0;
                    iZ1 = -1;
                    iZ2 = -1;
                    iZ3 = -1;
                    break;
                case 1:
                    iX1 = 1;
                    iX2 = 1;
                    iX3 = 1;
                    iZ1 = -1;
                    iZ2 = 1;
                    iZ3 = 0;
                    break;
                case 2:
                    iX1 = 1;
                    iX2 = -1;
                    iX3 = 0;
                    iZ1 = 1;
                    iZ2 = 1;
                    iZ3 = 1;
                    break;
                case 3:
                    iX1 = -1;
                    iX2 = -1;
                    iX3 = -1;
                    iZ1 = 1;
                    iZ2 = -1;
                    iZ3 = 0;
                    break;
                default:
                    return false;
            }
            if (S7_IsSameColor(iX1, iZ1, iX2, iZ2, iX3, iZ3))
            {
                return true;
            }

        }
        return false;
    }



    private E_RET S7_GetColorWithSameColor(int iX1, int iZ1, int iX2, int iZ2, int iX3, int iZ3, E_COLOR[] eColor)
    {
        int i = 0;
        int iBlock = 18;
        int iBlock1 = 0;
        int iBlock2 = 0;
        int iBlock3 = 0;

        for (i = 0; i < 8; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 18;
                    break;
                case 1:
                    iBlock = 19;
                    break;
                case 2:
                    iBlock = 20;
                    break;
                case 3:
                    iBlock = 21;
                    break;
                case 4:
                    iBlock = 23;
                    break;
                case 5:
                    iBlock = 24;
                    break;
                case 6:
                    iBlock = 25;
                    break;
                case 7:
                    iBlock = 26;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX1 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ1)
            {
                iBlock1 = iBlock;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX2 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ2)
            {
                iBlock2 = iBlock;
            }
            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX3 && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ3)
            {
                iBlock3 = iBlock;
            }
        }

        if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.F) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.F))
        {
            eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.F;
        }
        else if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.B) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.B))
        {
            eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.B;
        }
        else if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.L) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.L))
        {
            eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.L;
        }
        else if ((DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R != E_COLOR.NOCOLOR) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock2].Color.R) && (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R == DREAM_CUBE_CHECKSTATUS.cube_status[iBlock3].Color.R))
        {
            eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock1].Color.R;
        }
        else
        {
            return E_RET.CUBE_ERR_COLOR;
        }
        return E_RET.CUBE_OK;
    }


    private E_RET S7_GetPlateWithSameColor(E_PLATE[] ePlate)
    {
        int i = 0;
        int iX1 = 0;
        int iX2 = 0;
        int iX3 = 0;
        int iZ1 = 0;
        int iZ2 = 0;
        int iZ3 = 0;
        E_COLOR[] eColor = new E_COLOR[]{E_COLOR.NOCOLOR};
        E_RET eRet = E_RET.CUBE_ERR;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iX1 = -1;
                    iX2 = 1;
                    iX3 = 0;
                    iZ1 = -1;
                    iZ2 = -1;
                    iZ3 = -1;
                    break;
                case 1:
                    iX1 = 1;
                    iX2 = 1;
                    iX3 = 1;
                    iZ1 = -1;
                    iZ2 = 1;
                    iZ3 = 0;
                    break;
                case 2:
                    iX1 = 1;
                    iX2 = -1;
                    iX3 = 0;
                    iZ1 = 1;
                    iZ2 = 1;
                    iZ3 = 1;
                    break;
                case 3:
                    iX1 = -1;
                    iX2 = -1;
                    iX3 = -1;
                    iZ1 = 1;
                    iZ2 = -1;
                    iZ3 = 0;
                    break;
                default:
                    return E_RET.CUBE_ERR_PLATE;
            }
            eRet = S7_GetColorWithSameColor(iX1, iZ1, iX2, iZ2, iX3, iZ3, eColor);
            if (eRet == E_RET.CUBE_OK)
            {
                ePlate[0] = GetColorPlatebyStandarColor(eColor[0]);
                if (ePlate[0] == E_PLATE.NOPLATE)
                {
                    return eRet;
                }
                else
                {
                    return E_RET.CUBE_OK;
                }
            }
        }
        return E_RET.CUBE_ERR;
    }


    private Boolean S7_IsStayWithFriend()
    {
        int i = 0;
        int iX1 = 0;
        int iX2 = 0;
        int iX3 = 0;
        int iZ1 = 0;
        int iZ2 = 0;
        int iZ3 = 0;
        E_COLOR[] eColor = new E_COLOR[]{E_COLOR.NOCOLOR};
        E_PLATE ePlate1 = E_PLATE.UP;
        E_PLATE ePlate2 = E_PLATE.UP;
        E_RET eRet = E_RET.CUBE_ERR;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iX1 = -1;
                    iX2 = 1;
                    iX3 = 0;
                    iZ1 = -1;
                    iZ2 = -1;
                    iZ3 = -1;
                    ePlate1 = E_PLATE.BACK;
                    break;
                case 1:
                    iX1 = 1;
                    iX2 = 1;
                    iX3 = 1;
                    iZ1 = -1;
                    iZ2 = 1;
                    iZ3 = 0;
                    ePlate1 = E_PLATE.RIGHT;
                    break;
                case 2:
                    iX1 = 1;
                    iX2 = -1;
                    iX3 = 0;
                    iZ1 = 1;
                    iZ2 = 1;
                    iZ3 = 1;
                    ePlate1 = E_PLATE.FRONT;
                    break;
                case 3:
                    iX1 = -1;
                    iX2 = -1;
                    iX3 = -1;
                    iZ1 = 1;
                    iZ2 = -1;
                    iZ3 = 0;
                    ePlate1 = E_PLATE.LEFT;
                    break;
                default:
                    return false;
            }
            eRet = S7_GetColorWithSameColor(iX1, iZ1, iX2, iZ2, iX3, iZ3, eColor);
            if (eRet == E_RET.CUBE_OK)
            {
                ePlate2 = GetColorPlatebyStandarColor(eColor[0]);
                if (ePlate2 == E_PLATE.NOPLATE)
                {
                    return false;
                }
                else
                {
                    if (ePlate1 == ePlate2)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private E_RET S7_OP_FindFriend()
    {
        int iCount = 0;
        while (!S7_IsStayWithFriend())
        {
            RotationByPhysicalFormula("U");
            iCount++;
            if (iCount >= 4)
            {
                return E_RET.CUBE_ERR_OVERAROUND;
            }
        }
        return E_RET.CUBE_OK;
    }

    private E_RET S7_GetOperatePlate(Boolean bDirection, E_PLATE[] ePlate2)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_RET eRet = E_RET.CUBE_ERR;

        eRet = S7_GetPlateWithSameColor(ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }
        switch (ePlate[0])
        {
            case FRONT:
                if (bDirection)
                {
                    ePlate2[0] = E_PLATE.LEFT;
                }
                else
                {
                    ePlate2[0] = E_PLATE.RIGHT;
                }
                ePlate2[1] = E_PLATE.BACK;
                break;
            case BACK:
                if (bDirection)
                {
                    ePlate2[0] = E_PLATE.RIGHT;
                }
                else
                {
                    ePlate2[0] = E_PLATE.LEFT;
                }
                ePlate2[1] = E_PLATE.FRONT;
                break;
            case LEFT:
                if (bDirection)
                {
                    ePlate2[0] = E_PLATE.BACK;
                }
                else
                {
                    ePlate2[0] = E_PLATE.FRONT;
                }
                ePlate2[1] = E_PLATE.RIGHT;
                break;
            case RIGHT:
                if (bDirection)
                {
                    ePlate2[0] = E_PLATE.FRONT;
                }
                else
                {
                    ePlate2[0] = E_PLATE.BACK;
                } ePlate2[1] = E_PLATE.LEFT;
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }

    private E_RET S7_GetColorInXZ(int iX, int iZ, E_COLOR[] eColor)
    {
        int i = 0;
        int iBlock = 19;

        for (i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:
                    iBlock = 19;
                    break;
                case 1:
                    iBlock = 21;
                    break;
                case 2:
                    iBlock = 23;
                    break;
                case 3:
                    iBlock = 25;
                    break;
            }

            if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.x == iX && DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Position.z == iZ)
            {
                if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F != E_COLOR.NOCOLOR)
                {
                    eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.F;
                    return E_RET.CUBE_OK;
                }
                else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B != E_COLOR.NOCOLOR)
                {
                    eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.B;
                    return E_RET.CUBE_OK;
                }
                else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L != E_COLOR.NOCOLOR)
                {
                    eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.L;
                    return E_RET.CUBE_OK;
                }
                else if (DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R != E_COLOR.NOCOLOR)
                {
                    eColor[0] = DREAM_CUBE_CHECKSTATUS.cube_status[iBlock].Color.R;
                    return E_RET.CUBE_OK;
                }
            }
        }
        return E_RET.CUBE_ERR_NOCOLOR;
    }

    private E_RET S7_GetOperateDirection(Boolean[] bDirection)
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP};
        E_COLOR[] eColor = new E_COLOR[]{E_COLOR.NOCOLOR};
        int iX = 0;
        int iZ = 0;
        E_RET eRet = E_RET.CUBE_ERR;

        eRet = S7_GetPlateWithSameColor(ePlate);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }
        switch (ePlate[0])
        {
            case FRONT:
                ePlate[0] = E_PLATE.BACK;
                iX = 0;
                iZ = -1;
                break;
            case BACK:
                ePlate[0] = E_PLATE.FRONT;
                iX = 0;
                iZ = 1;
                break;
            case LEFT:
                ePlate[0] = E_PLATE.RIGHT;
                iX = 1;
                iZ = 0;
                break;
            case RIGHT:
                ePlate[0] = E_PLATE.LEFT;
                iX = -1;
                iZ = 0;
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }

        eRet = S7_GetColorInXZ(iX, iZ, eColor);
        if (eRet != E_RET.CUBE_OK)
        {
            return E_RET.CUBE_ERR;
        }
        switch (ePlate[0])
        {
            case FRONT:
                if (eColor[0] == E_COLOR.WHITE)
                {
                    bDirection[0] = true;
                }
                else if (eColor[0] == E_COLOR.YELLOW)
                {
                    bDirection[0] = false;
                }
                break;
            case BACK:
                if (eColor[0] == E_COLOR.YELLOW)
                {
                    bDirection[0] = true;
                }
                else if (eColor[0] == E_COLOR.WHITE)
                {
                    bDirection[0] = false;
                }
                break;
            case LEFT:
                if (eColor[0] == E_COLOR.GREEN)
                {
                    bDirection[0] = true;
                }
                else if (eColor[0] == E_COLOR.BLUE)
                {
                    bDirection[0] = false;
                }
                break;
            case RIGHT:
                if (eColor[0] == E_COLOR.BLUE)
                {
                    bDirection[0] = true;
                }
                else if (eColor[0] == E_COLOR.GREEN)
                {
                    bDirection[0] = false;
                }
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }


    private E_RET S7_GetOperatePlateAndDirection(E_PLATE[] ePlate2, Boolean[] bDirection)
    {
        E_RET eRet = E_RET.CUBE_ERR;

        eRet = S7_GetOperateDirection(bDirection);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }
        eRet = S7_GetOperatePlate(bDirection[0], ePlate2);
        if (eRet != E_RET.CUBE_OK)
        {
            return eRet;
        }

        return E_RET.CUBE_OK;
    }

    private E_RET S7_OP_TurnIntoPositionMethod1(E_PLATE ePlate)
    {
        switch (ePlate)
        {
            case FRONT:
                RotationByPhysicalFormula("fuFufuuF");
                break;
            case BACK:
                RotationByPhysicalFormula("buBubuuB");
                break;
            case LEFT:
                RotationByPhysicalFormula("luLuluuL");
                break;
            case RIGHT:
                RotationByPhysicalFormula("ruRuruuR");
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }

    private E_RET S7_OP_TurnIntoPositionMethod2(E_PLATE ePlate)
    {
        switch (ePlate)
        {
            case FRONT:
                RotationByPhysicalFormula("FUfUFUUf");
                break;
            case BACK:
                RotationByPhysicalFormula("BUbUBUUb");
                break;
            case LEFT:
                RotationByPhysicalFormula("LUlULUUl");
                break;
            case RIGHT:
                RotationByPhysicalFormula("RUrURUUr");
                break;
            default:
                return E_RET.CUBE_ERR_PLATE;
        }
        return E_RET.CUBE_OK;
    }

    private void S7_OP_TurnIntoPosition(Boolean bDirection, E_PLATE eFirstPlate, E_PLATE eSecondPlate)
    {
        if (bDirection)     //顶面为顺时针，先用算法2在用算法1
        {
            S7_OP_TurnIntoPositionMethod2(eFirstPlate);
            S7_OP_TurnIntoPositionMethod1(eSecondPlate);
        }
        else
        {
            S7_OP_TurnIntoPositionMethod1(eFirstPlate);
            S7_OP_TurnIntoPositionMethod2(eSecondPlate);
        }
    }


    private void S7_RunInto()
    {
        E_PLATE[] ePlate = new E_PLATE[]{E_PLATE.UP, E_PLATE.UP};
        Boolean[] bDirection = new Boolean[]{true};
        int iCount = 0;
        E_RET eRet = E_RET.CUBE_ERR;

        if (!IsMatchStep1Status())
        {
            return;
        }

        if (!IsMatchStep2Status())
        {
            return;
        }

        if (!IsMatchStep3Status())
        {
            return;
        }

        if (!IsMatchStep4Status())
        {
            return;
        }

        if (!IsMatchStep5Status())
        {
            return;
        }

        if (!IsMatchStep6Status())
        {
            return;
        }

        while (!IsMatchStep7Status())
        {
            if (S7_HaveSameColor())
            {
                eRet = S7_OP_FindFriend();
                if (eRet != E_RET.CUBE_OK)
                {
                    return;
                }

                eRet = S7_GetOperatePlateAndDirection(ePlate, bDirection);
                if (eRet != E_RET.CUBE_OK)
                {
                    return;
                }
            }
            else
            {
            	ePlate[0] = E_PLATE.RIGHT;
            	ePlate[1] = E_PLATE.BACK;
                bDirection[0] = false;
            }
            S7_OP_TurnIntoPosition(bDirection[0], ePlate[0], ePlate[1]);
            iCount++;
            if (iCount >= 10)
            {
                System.out.println("Over 10 times, there must be some error in S7_RunInto()");
                return;
            }
        }

    }

    public int GetMinLevel()
    {
    	return E_STUDY_STATUS.STUDY_STEP1.ordinal();
    }
    public int GetMaxLevel()
    {
    	return E_STUDY_STATUS.STUDY_STEP7.ordinal();
    }



}
