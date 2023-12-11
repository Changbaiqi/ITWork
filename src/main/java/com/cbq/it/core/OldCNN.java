package com.cbq.it.core;

public class OldCNN {
    private int stepSize;//步长
    private double kernel[][]; //卷积核
    private double input[][]; //输入
    private double conv[][];//卷积结果
    private int poolSize; //池化层大小
    private int poolStepSize; // 池化层步长
    private double pool[][]; //池化结果

    /**
     *
     * @param stepSize 卷积步长
     * @param poolSize 池化层大小
     * @param poolStepSize 池化层步长
     * @param kernel 卷积核
     * @param input 输入
     */
    public OldCNN(int stepSize, int poolSize, int poolStepSize, double[][] kernel, double[][] input) {
        this.stepSize = stepSize;
        this.kernel = kernel;
        this.input = input;
        this.poolSize = poolSize;
        this.poolStepSize = poolStepSize;
    }

    /**
     * 卷积
     * @return
     */
    public void convolution(){
        int Klength = kernel.length;
        int OutLen = (input.length-Klength)/stepSize+1;
        double out[][] = new double[OutLen][OutLen];
        for(int y =Klength-1,yy=0;y<input.length;y+=stepSize,++yy){
            for(int x=Klength-1,xx=0;x<input.length;x+=stepSize,++xx){
                int minX = x-(Klength-1);
                int maxX = x;
                int minY= y-(Klength-1);
                int maxY = y;
                out[yy][xx]=solve(input,kernel,minX,maxX,minY,maxY);
            }
        }
        this.conv = out;
    }

    /**
     * 池化
     */
    public void pool(){
        int OutLen = (conv.length-poolSize)/poolStepSize+1;
        double out[][] = new double[OutLen][OutLen];
        for(int y =poolSize-1,yy=0;y<conv.length;y+=poolStepSize,++yy){
            for(int x=poolSize-1,xx=0;x<conv.length;x+=poolStepSize,++xx){
                int minX = x-(poolSize-1);
                int maxX = x;
                int minY= y-(poolSize-1);
                int maxY = y;
                out[yy][xx]=avePolo(conv,minX,maxX,minY,maxY);
            }
        }
        this.pool= out;
    }


    private double solve(double input[][], double Kernel[][], int minX, int maxX, int minY, int maxY){
        double sum =0;
        for(int y = minY,yy=0;y<=maxY;++y,++yy){
            for(int x=minX,xx=0;x<=maxX;++x,++xx){
                sum+=input[y][x]*Kernel[yy][xx];
            }
        }
        return sum;
    }

    private double avePolo(double input[][],int minX,int maxX,int minY,int maxY){
        double sum =0;
        int count=0;
        for(int y = minY,yy=0;y<=maxY;++y,++yy){
            for(int x=minX,xx=0;x<=maxX;++x,++xx){
                sum+=input[y][x];
                ++count;
            }
        }
        return (double) (sum/count);
    }


    public int getStepSize() {
        return stepSize;
    }

    public double[][] getKernel() {
        return kernel;
    }

    public double[][] getInput() {
        return input;
    }

    public double[][] getConv() {
        return conv;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getPoolStepSize() {
        return poolStepSize;
    }

    public double[][] getPool() {
        return pool;
    }

}
