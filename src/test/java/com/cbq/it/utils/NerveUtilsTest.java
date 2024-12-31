package com.cbq.it.utils;

import com.cbq.it.core.BPNN;
//import org.bytedeco.libfreenect._freenect_context;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

public class NerveUtilsTest {


    /**
     * 用于测试矩阵转置
     */
    @Test
    public void matrixTrans() {
        double matrix[][] = new double[][]{
                {1,1,0},
                {1,0,2},
                {4,0,3}};
        NerveUtils.printMatrix(NerveUtils.matrixTrans(matrix));
    }

    /**
     * 用于测试矩阵相加
     */
    @Test
    public void matrixAdd() {

        Double arr[] = {1.123,23.25,68.136,0.231,664.12};
        Arrays.sort(arr,(a,b)-> Double.compare(b,a));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 用于测试矩阵相乘
     */
    @Test
    public void matrixMulti() {
        double matrix1[][]={
                {1,1,0},
                {1,0,2},
                {4,0,3}
        };
        double matrix2[][]={
                {1,0,1},
                {3,2,1},
                {2,0,1}
        };

        double[][] doubles = NerveUtils.matrixMulti(matrix1, matrix2);
        NerveUtils.printMatrix(doubles);
    }

    /**
     * 用于测试数字转二进制数组
     */
    @Test
    public void intTurnBit() {
        double[][] doubles = NerveUtils.intTurnBit(12, 32);
        System.out.println(Arrays.deepToString(doubles));
    }

    /**
     * 用于测试CNN图片训练
     */
    @Test
    public void nerveCNNImage() {
        BPNN bpnn = NerveUtils.nerveCNNImage(
                new String[]{
                        "E:\\Python_Object\\bpneural-network\\img-0",
                        "E:\\Python_Object\\bpneural-network\\img-1",
                        "E:\\Python_Object\\bpneural-network\\img-2",
                        "E:\\Python_Object\\bpneural-network\\img-3",
                        "E:\\Python_Object\\bpneural-network\\img-4",
                        "E:\\Python_Object\\bpneural-network\\img-5",
                        "E:\\Python_Object\\bpneural-network\\img-6",
                        "E:\\Python_Object\\bpneural-network\\img-7",
                        "E:\\Python_Object\\bpneural-network\\img-8",
                        "E:\\Python_Object\\bpneural-network\\img-9",
                });
        //保存模型
        bpnn.save("weightsRes.txt");
    }
    @Test
    public void ccccc(){
        System.out.println();
    }

    /**
     * 直接使用训练好的模型
     */
    @Test
    public void inputCNNImageTest(){
        BPNN bpnn = BPNN.build("weightsForAverage.txt");
        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\5-10-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();
        double[][] out = bpnn.getOut();
        int maxIndex = 0;
        double maxScore = 0;
        for(int i=0;i< out[0].length;++i){
            if(out[0][i]>maxScore){
                maxIndex=i;
                maxScore = out[0][i];
            }
        }
        System.out.println("预测结果为："+maxIndex);
    }

}