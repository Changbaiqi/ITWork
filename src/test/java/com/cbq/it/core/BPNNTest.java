package com.cbq.it.core;


import com.cbq.it.utils.ImageUtils;
import com.cbq.it.utils.NerveUtils;
import org.icepear.echarts.Line;
import org.icepear.echarts.charts.line.LineAreaStyle;
import org.icepear.echarts.charts.line.LineSeries;
import org.icepear.echarts.components.coord.cartesian.CategoryAxis;
import org.icepear.echarts.render.Engine;
import org.junit.jupiter.api.Test;

class BPNNTest {

    @Test
    public void imageUtilsTest(){
        double[][] doubles = ImageUtils.readImageMatrixBinary("E:\\IntelliJ_WorkSpace\\ITWork\\src\\main\\resources\\0-1-test.png");
        NerveUtils.printMatrix(doubles);

        OldCNN cnn = new OldCNN(2,2,2, new double[][]{
                {1, 1, -1},
                {-1, 0, 1},
                {1, -1, 0}},doubles);
        cnn.convolution();
        cnn.pool();

        OldCNN cnn1 = new OldCNN(2,2,2, new double[][]{
                {0, 1, 0},
                {0, 1, -1},
                {1, 1, 0}},doubles);
        cnn1.convolution();
        cnn1.pool();

        OldCNN cnn2 = new OldCNN(2,2,2, new double[][]{
                {1, -1, 1},
                {0, 0, -1},
                {0, 1, 1}},doubles);
        cnn2.convolution();
        cnn2.pool();

        System.out.println();
//        NerveUtils.printMatrix(cnn.getConv());
        NerveUtils.printMatrix(cnn.getPool());
        System.out.println();
        NerveUtils.printMatrix(cnn1.getPool());
        System.out.println();
        NerveUtils.printMatrix(cnn2.getPool());
    }
    @Test
    public void ccc() {
        BPNN bpnn = new BPNN(32, 2, 0.05, new int[]{16,16});
        //随机权值
        bpnn.randomWeights();
        //训练
        for (int i = 44; i <= 1000; ++i) {
            System.out.println("当前数字："+i);
            //神经网络输入
            bpnn.input(NerveUtils.intTurnBit(i,32));
            //神经网络向前传播
            bpnn.forward();
            //神经网络向后传播，奇偶
            bpnn.back(new double[][]{{i%2!=0?0.99:0.01, i%2==0?0.99:0.01}});
            //神经网络输出
//            bpnn.printNerve();
//            System.out.println();
        }
        System.out.println("当前数字："+4320);
        //神经网络输入
        bpnn.input(NerveUtils.intTurnBit(4320,32));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printNerve();


        System.out.println("当前数字："+141321);
        //神经网络输入
        bpnn.input(NerveUtils.intTurnBit(141321,32));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printNerve();

        System.out.println("当前数字："+4320);
        //神经网络输入
        bpnn.input(NerveUtils.intTurnBit(4320,32));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printNerve();


        System.out.println("当前数字："+13);
        //神经网络输入
        bpnn.input(NerveUtils.intTurnBit(13,32));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printNerve();
    }


    @Test
    public void imageTest(){
//        double[][] doubles = ImageUtils.readImageBinary("E:\\Image训练集\\测试\\img-0\\4101.jpg");
//        System.out.println(doubles[0].length);
        BPNN bpnn = NerveUtils.nerveImage(
                new String[]{
                        "E:\\Image训练集\\测试\\img-0",
                        "E:\\Image训练集\\测试\\img-1",
                        "E:\\Image训练集\\测试\\img-2",
                        "E:\\Image训练集\\测试\\img-3",
                        "E:\\Image训练集\\测试\\img-4",
                        "E:\\Image训练集\\测试\\img-5",
                        "E:\\Image训练集\\测试\\img-6",
                        "E:\\Image训练集\\测试\\img-7",
                        "E:\\Image训练集\\测试\\img-8",
                        "E:\\Image训练集\\测试\\img-9"});
        //神经网络输入
        bpnn.input(ImageUtils.readImageBinaryResource("0-1-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();


        //神经网络输入
        bpnn.input(ImageUtils.readImageBinaryResource("9-4-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();


        //神经网络输入
        bpnn.input(ImageUtils.readImageBinaryResource("9-5-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
        System.out.println();


        //神经网络输入
        bpnn.input(ImageUtils.readImageBinaryResource("0-3-test.png"));
        //神经网络向前传播
        bpnn.forward();
        bpnn.printLayers();
    }

    @Test
    public void chartsTest(){
        Line line = new Line()
                .addXAxis(new CategoryAxis()
                        .setData(new Integer[]{1,2,3,4,5,6,7,8,9})
                        .setBoundaryGap(false))
                .addYAxis()
                .addSeries(new LineSeries()
                        .setData(new Number[]{100,300,600,700})
                        .setAreaStyle(new LineAreaStyle()));

        Engine engine = new Engine();
        engine.render("index.html",line);
    }
}