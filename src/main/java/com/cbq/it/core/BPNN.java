package com.cbq.it.core;

import com.cbq.it.utils.FileUtils;
import com.cbq.it.utils.NerveUtils;
import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class BPNN {
    private int inputSize;
    private int outSize;
    private double studyRate;
    private int hiddens[];
    private ArrayList<double[][]> layers;  //层值
    private ArrayList<double[][]> diffV;//差值
    private ArrayList<double[][]> weights;//权值

    public BPNN(int inputSize, int outSize,double studyRate, int hiddens[]) {
        this.inputSize = inputSize;
        this.outSize = outSize;
        this.studyRate = studyRate;
        this.layers = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.diffV = new ArrayList<>();
        this.hiddens = hiddens;
        layers.add(new double[1][inputSize]);  //输入层添加
        diffV.add(new double[1][inputSize]); //差值
        for (int i = 0; i < hiddens.length; ++i) {
            layers.add(new double[1][hiddens[i]]);
            diffV.add(new double[1][hiddens[i]]);
        } //隐含层添加
        layers.add(new double[1][outSize]); //输出层添加
        diffV.add(new double[1][outSize]);  //差值

        for (int i = 1; i < layers.size(); ++i) {
            weights.add(new double[layers.get(i - 1)[0].length][layers.get(i)[0].length]);
        } //权值层添加

    }


    /**
     * 随机权值
     */
    public void randomWeights() {
        for (int i = 0; i < weights.size(); i++) {
            for (int y = 0; y < weights.get(i).length; y++) {
                for (int x = 0; x < weights.get(i)[y].length; x++) {
                    weights.get(i)[y][x] = (Math.random() * 2) - 1;
                }
            }
        }
    }

    /**
     * 输入
     *
     * @param input 输入的值
     */
    public void input(double input[][]) {
        double[][] inputLayer = layers.get(0);//输入层
        for (int y = 0; y < inputLayer[0].length; y++) {
            layers.get(0)[0][y]=inputLayer[0][y] = input[0][y];
        } //输入
    }

    /**
     * 获取输出层数据
     * @return
     */
    public double[][] getOut(){
        return layers.get(layers.size()-1);
    }

    /**
     * 向前传播
     */
    public void forward() {
        //向前传播
        for (int i = 0; i < weights.size(); i++) {
            layers.set(i + 1, NerveUtils.matrixMulti(layers.get(i), weights.get(i)));
            //利用激活函数
            for (double[] weight : layers.get(i + 1)) {
                for (int x = 0; x < weight.length; x++) {
                    weight[x]=NerveUtils.sigmoid(weight[x]);
                }
            }
        }
    }

    /**
     * 向后传播
     */
    public void back(double out[][]) {
        double[][] outLayer = layers.get(layers.size() - 1);
        double[][] outDiff = diffV.get(diffV.size() - 1);
        //输入层差值
        for (int i = 0; i < outDiff[0].length; i++) {
            outDiff[0][i] = out[0][i] - outLayer[0][i];
            //原值
//            double res = (-Math.log((1.0/outLayer)))
//            (-Math.log((1.0/outDiff[0][i])-1))-
        }
        //差值传递
        for (int i = diffV.size() - 2; i >= 0; i--) {
            diffV.set(i, NerveUtils.matrixMulti(diffV.get(i + 1), NerveUtils.matrixTrans(weights.get(i))));
        }
        //权值修正
        for (int i = 0; i < weights.size(); i++) {
            double[][] weight = weights.get(i);
            for (int y = 0; y < weight.length; y++) {
                for (int x = 0; x < weight[y].length; x++) {
                    double[][] Llayer = layers.get(i);
                    double[][] Rlayer = layers.get(i + 1);
                    double[][] Rdiff = diffV.get(i+1);
                    weight[y][x] -= -Rdiff[0][x]*Rlayer[0][x]*(1-Rlayer[0][x])*Llayer[0][y]*studyRate;
                }
            }
        }
    }

    public void printLayers(){
        for (int i = 0; i < layers.size(); i++) {
            double layer[][] = layers.get(i);
            System.out.print("layer" + (i + 1) + ">>>");
            for (int y = 0; y < layer.length; y++) {
                for (int x = 0; x < layer[y].length; x++) {
                    System.out.print(layer[y][x] + " ");
                }
            }
            System.out.println();
        }
    }
    /**
     * 输出神经网络，方便查看值
     */
    public void printNerve() {
        printLayers();
        for (int i = 0; i < weights.size(); i++) {
            double weight[][] = weights.get(i);
            System.out.print("weights"+(i+1)+">>>");
            for (int y = 0; y < weight.length; y++) {
                for (int x = 0; x < weight[y].length; x++) {
                    System.out.print(weight[y][x] + " ");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < diffV.size(); i++) {
            double diff[][] = diffV.get(i);
            System.out.print("diffV"+(i+1)+">>>");
            for (int y = 0; y < diff.length; y++) {
                for (int x = 0; x < diff[y].length; x++) {
                    System.out.print(diff[y][x] + " ");
                }
            }
            System.out.println();
        }
    }


    /**
     * 用于保存模型
     * @param fileName
     */
    public void save(String fileName){
        ObjectMapper objectMapper = new ObjectMapper();
        BPNNConfig config = new BPNNConfig(inputSize,outSize,studyRate,hiddens,weights);

        try {
            //权值信息
            String weightsJson = objectMapper.writeValueAsString(config);
            FileUtils.saveFile(weightsJson.getBytes(),fileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<double[][]> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<double[][]> weights) {
        this.weights = weights;
    }

    /**
     * 用于读取模型然后初始化构建
     * @param fileName
     * @return
     */
    public static BPNN build(String fileName){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = FileUtils.readTxt(new File("cache/" + fileName));
            BPNNConfig bpnnConfig= objectMapper.readValue(s,BPNNConfig.class);

            BPNN bpnn = new BPNN(bpnnConfig.getInputSize(), bpnnConfig.getOutSize(), bpnnConfig.getStudyRate(), bpnnConfig.getHiddens());
            bpnn.setWeights(bpnnConfig.getWeights());
            return bpnn;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
