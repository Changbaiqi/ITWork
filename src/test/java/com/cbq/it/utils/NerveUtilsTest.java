package com.cbq.it.utils;

import com.cbq.it.core.BPNN;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

class NerveUtilsTest {

    @Test
    void matrixTrans() {
        double matrix[][] = new double[][]{
                {1,1,0},
                {1,0,2},
                {4,0,3}};
        NerveUtils.printMatrix(NerveUtils.matrixTrans(matrix));
    }

    @Test
    void matrixAdd() {

    }

    @Test
    void matrixMulti() {
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


    @Test
    void intTurnBit() {
        double[][] doubles = NerveUtils.intTurnBit(12, 32);
        System.out.println(Arrays.deepToString(doubles));
    }

    @Test
    void nerveCNNImage() {
//        double[][] doubles = turnMatrix("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\IT-0-1.png");
//        NerveUtils.printMatrix(doubles);
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
        bpnn.save("weights.txt");

        //神经网络输入

        //图片二值化

        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\0-2-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();

        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\1-2-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();

        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\1-4-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();

        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\0-3-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();
    }

    @Test
    void inputCNNImageTest(){
        double[][] testRes = NerveUtils.turnMatrixPlus(new File("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\0-2-test.png").getAbsolutePath());
        BPNN bpnn = new BPNN(testRes[0].length, 10, 0.005, new int[]{128, 128});
        bpnn.readFileWeight("weights.txt");

        bpnn.input(NerveUtils.turnMatrixPlus("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\5-2-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();

    }

}