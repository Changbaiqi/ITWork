package com.cbq.it.utils;

import com.cbq.it.core.BPNN;
import com.cbq.it.core.CNN;
import org.icepear.echarts.Line;
import org.icepear.echarts.charts.line.LineAreaStyle;
import org.icepear.echarts.charts.line.LineSeries;
import org.icepear.echarts.components.coord.cartesian.CategoryAxis;
import org.icepear.echarts.render.Engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NerveUtils {
    static double[][] kernel1 = {
        { 1, 1,-1},
        {-1, 0, 1},
        {-1,-1, 0}
    };

    static double[][] kernel2 = {
            {-1, 0,-1},
            { 0, 0,-1},
            { 1,-1, 0}
    };
    static double[][] kernel3 = {
            { 0, 1, 0},
            {-1, 0, 1},
            { 0,-1,-1}
    };
    static double[][] kernel4 = {
            { 0, 0, 0},
            { 1, 1, 1},
            { 0, 0, 0}
    };


    /**
     * 矩阵转置
     *
     * @param matrix 需要转置的矩阵
     * @return
     */
    public static double[][] matrixTrans(double matrix[][]) {
        double matrixRes[][] = new double[matrix[0].length][matrix.length];
        for (int y = 0; y < matrix.length; ++y) {
            for (int x = 0; x < matrix[y].length; ++x) {
                matrixRes[x][y] = matrix[y][x];
            }
        }
        return matrixRes;
    }

    /**
     * 矩阵相加
     *
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static double[][] matrixAdd(double matrix1[][], double matrix2[][]) {
        double matrixRes[][] = new double[matrix1.length][matrix1[0].length];
        return matrixRes;
    }

    /**
     * 矩阵相乘
     *
     * @param matrix1 矩阵1
     * @param matrix2 矩阵2
     * @return 返回相乘矩阵
     */
    public static double[][] matrixMulti(double matrix1[][], double matrix2[][]) {
        int matrixW = matrix2[0].length; //相乘矩阵的宽
        int matrixH = matrix1.length; //相乘矩阵的高
        double matrixRes[][] = new double[matrixH][matrixW];

        for (int y = 0; y < matrixH; y++) {
            for (int x = 0; x < matrixW; x++) {
                //矩阵相乘相加
                double sum = 0;
                for (int m1x = 0; m1x < matrix1[0].length; m1x++) {
                    sum += (matrix1[y][m1x] * matrix2[m1x][x]);
                }
                matrixRes[y][x] = sum;
            }
        }
        return matrixRes;
    }

    /**
     * 输出矩阵
     *
     * @param matrix 需要输出的矩阵
     */
    public static void printMatrix(double matrix[][]) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                System.out.print(matrix[y][x] + " ");
            }
            System.out.println();
        }
    }

    /**
     * sigmoid激活函数
     *
     * @param v
     * @return
     */
    public static double sigmoid(double v) {
        return 1.0 / (1 + Math.exp(-v));
    }

    /**
     * sigmoid求导
     *
     * @param v
     * @return
     */
    public static double sigmoidD(double v) {
        return v - (1 - v);
    }

    /**
     * 整形转二进制
     *
     * @param num
     * @return
     */
    public static double[][] intTurnBit(int num, int bit) {
        char arr[] = Integer.toBinaryString(num).toCharArray();
        double res[][] = new double[1][bit];
        int index = 0;
        while (index < bit - arr.length) {
            res[0][index] = 0;
            ++index;
        }
        while (index < bit) {
            res[0][index] = arr[index - (bit - arr.length)] == '0' ? 0 : 1;
            ++index;
        }
        return res;
    }

    public static BPNN nerveImage(String filePaths[]) {
        double[][] testRes = ImageUtils.readImageBinary(new File(filePaths[0]).listFiles()[0].getAbsolutePath());
        BPNN bpnn = new BPNN(testRes[0].length, filePaths.length, 0.005, new int[]{64, 64}); //构建神经网络
        bpnn.randomWeights(); //随机权值


        List<List<String>> list = new ArrayList<>();
        int minColl = Integer.MAX_VALUE;
        for (int i = 0; i < filePaths.length; ++i) {
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < filePaths.length; ++i) {
            File file = new File(filePaths[i]);
            File[] files = file.listFiles();
            for (File file1 : files) {
                list.get(i).add(file1.getAbsolutePath());
            }
            minColl = minColl > list.get(i).size() ? list.get(i).size() : minColl;
        }

        int count = 0, yes = 0, no = 0;
        ArrayList<Integer> countArr = new ArrayList<>();
        ArrayList<Double> countScore = new ArrayList<>();
        ArrayList<Double> diffScore = new ArrayList<>();


        for (int i = 0; i < minColl; ++i) {
            System.out.println("第" + (i + 1) + "轮训练");
            for (int x = 0; x < list.size(); ++x) {
                String s = list.get(x).get(i);


                //神经网络输入
                bpnn.input(ImageUtils.readImageBinary(s));
                //神经网络向前传播
                bpnn.forward();

                double[][] outLayers = bpnn.getOut();


                ++count;
                double answerScore = -1;
                double answerIndex = -1;
                for (int y = 0; y < outLayers[0].length; ++y) {
                    if (answerScore <= outLayers[0][y]) {
                        answerScore = outLayers[0][y];
                        answerIndex = y;
                    }
                }
                if (answerIndex == x) ++yes;
                else ++no;
                countArr.add(count);
                countScore.add((double) yes / count);
                //均方误差
                double powSum = 0;
                for (int y = 0; y < outLayers[0].length; ++y) {
                    if (y == x) {
                        powSum += Math.pow(outLayers[0][y] - 0.99, 2);
                    } else {
                        powSum += Math.pow(outLayers[0][y] - 0.01, 2);
                    }
                }
                diffScore.add(powSum / outLayers[0].length);


                //神经网络向后传播
                double[][] answer = new double[1][filePaths.length];
                for (int y = 0; y < answer[0].length; ++y) {
                    if (y == x) answer[0][y] = 0.99;
                    else answer[0][y] = 0.01;
                }
                bpnn.back(answer);


            }
            Line line = new Line()
                    .addXAxis(new CategoryAxis()
                            .setData(countArr.toArray())
                            .setBoundaryGap(false))
                    .addYAxis()
                    .addSeries(new LineSeries()
                            .setData(countScore.toArray())
                            .setAreaStyle(new LineAreaStyle()))
                .addSeries(new LineSeries()
                        .setData(diffScore.toArray())
                        .setAreaStyle(new LineAreaStyle()));

            Engine engine = new Engine();
            engine.render("indexFormAverage.html", line);
        }


        return bpnn;
    }

    public static BPNN nerveCNNImage(String filePaths[]) {
        double[][] testRes = turnMatrixPlus(new File(filePaths[0]).listFiles()[0].getAbsolutePath());
        BPNN bpnn = new BPNN(testRes[0].length, filePaths.length, 0.005, new int[]{120, 120});
        bpnn.randomWeights();


        List<List<String>> list = new ArrayList<>();
        int minColl = Integer.MAX_VALUE;
        for (int i = 0; i < filePaths.length; ++i) {
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < filePaths.length; ++i) {
            File file = new File(filePaths[i]);
            File[] files = file.listFiles();
            for (File file1 : files) {
                list.get(i).add(file1.getAbsolutePath());
            }
            minColl = minColl > list.get(i).size() ? list.get(i).size() : minColl;
        }

        int count = 0, yes = 0, no = 0;
        ArrayList<Integer> countArr = new ArrayList<>();
        ArrayList<Double> countScore = new ArrayList<>();
        for (int i = 0; i < minColl; ++i) {
            System.out.println("第" + (i + 1) + "轮训练");
            for (int x = 0; x < list.size(); ++x) {
                String s = list.get(x).get(i);
                //图片二值化
//                double[][] imageMatrix = ImageUtils.readImageMatrixBinary(s);

                //卷积-----------------------------------------
                double[][] doubles = turnMatrixPlus(s);
                //神经网络输入
                bpnn.input(doubles);
                //神经网络向前传播
                bpnn.forward();


                double[][] outLayers = bpnn.getOut();


                ++count;
                double answerScore = -1;
                double answerIndex = -1;
                for (int y = 0; y < outLayers[0].length; ++y) {
                    if (answerScore <= outLayers[0][y]) {
                        answerScore = outLayers[0][y];
                        answerIndex = y;
                    }
                }
                if (answerIndex == x) ++yes;
                else ++no;
                countArr.add(count);
                countScore.add((double) yes / count);


                //神经网络向后传播
                double[][] answer = new double[1][filePaths.length];
                for (int y = 0; y < answer[0].length; ++y) {
                    if (y == x) answer[0][y] = 0.99;
                    else answer[0][y] = 0.01;
                }
                bpnn.back(answer);
            }
            Line line = new Line()
                    .addXAxis(new CategoryAxis()
                            .setData(countArr.toArray())
                            .setBoundaryGap(false))
                    .addYAxis()
                    .addSeries(new LineSeries()
                            .setData(countScore.toArray())
                            .setAreaStyle(new LineAreaStyle()));

            Engine engine = new Engine();
            engine.render("indexFormAverage.html", line);
        }
        System.out.println("训练完成，正确率为："+countScore.get(countScore.size()-1));



        return bpnn;
    }


    public static double[][] turnMatrixPlus(String path) {
        double[][] imageMatrix = ImageUtils.readImageMatrixBinary(path);
        CNN cnn = new CNN(
                new ArrayList<Integer>(Arrays.asList(3,3,3,3)),//对应每个卷积核的大小
                new ArrayList<Integer>(Arrays.asList(1,1,1,1)),//对应每个卷积核的步长
                new ArrayList<Integer>(Arrays.asList(2,2,2,2)),//对应池化层的大小
                new ArrayList<double[][]>(Arrays.<double[][]>asList(
                        kernel1, //卷积核1
                        kernel2, //卷积核2
                        kernel3, //卷积核3
                        kernel4 //卷积核4
                )),
                imageMatrix //输入值
        );
        cnn.formWard(); //向前传播
        ArrayList<double[][]> outs = cnn.getOuts(); //获取输出
        //输出数据一维化
        double[][] res= new double[1][outs.size()*outs.get(0).length*outs.get(0).length];
        int ans = 0;
        for(int i=0;i<outs.size();++i){
            for (int yy = 0; yy < outs.get(i).length; yy++) {
                for (int xx = 0; xx < outs.get(i)[yy].length; xx++) {
                    res[0][ans++] = outs.get(i)[yy][xx];
                }
            }
        }
        return res;
    }

}
