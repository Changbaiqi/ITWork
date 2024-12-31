package com.cbq.it.core;


import com.cbq.it.utils.ImageUtils;
import com.cbq.it.utils.NerveUtils;
import org.icepear.echarts.Line;
import org.icepear.echarts.charts.line.LineAreaStyle;
import org.icepear.echarts.charts.line.LineSeries;
import org.icepear.echarts.components.coord.cartesian.CategoryAxis;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

public class BPNNTest {

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
//        BPNN bpnn = BPNN.build("BPNNweights.txt");

        BPNN bpnn = NerveUtils.nerveImage(
                new String[]{
                        "E:\\Image训练集\\mnist\\0",
                        "E:\\Image训练集\\mnist\\1",
                        "E:\\Image训练集\\mnist\\2",
                        "E:\\Image训练集\\mnist\\3",
                        "E:\\Image训练集\\mnist\\4",
                        "E:\\Image训练集\\mnist\\5",
                        "E:\\Image训练集\\mnist\\6",
                        "E:\\Image训练集\\mnist\\7",
                        "E:\\Image训练集\\mnist\\8",
                        "E:\\Image训练集\\mnist\\9"});

        bpnn.save("BPNNweights.txt");

//        //神经网络输
//        bpnn.input(ImageUtils.readImageBinary("E:\\Image训练集\\6-0.png"));
//        //神经网络向前传播
//        bpnn.forward();
//        bpnn.printLayers();
//        double[][] out = bpnn.getOut();
//        double max =0,index=-1;
//        for (int i = 0; i < out[0].length; i++) {
//            if(max<out[0][i]){
//                max = out[0][i];
//                index = i;
//            }
//        }
//        System.out.println(index);
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
        engine.render("indexFormAverage.html",line);
    }
}