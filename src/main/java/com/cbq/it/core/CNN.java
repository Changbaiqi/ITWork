package com.cbq.it.core;

import java.util.ArrayList;

public class CNN {
    private ArrayList<Integer> kernelsSize = new ArrayList<>(); //对应每层卷积核的长度
    private ArrayList<Integer> kernelsStepSize = new ArrayList<>(); //对应卷积核卷积步长
    private ArrayList<Integer> poolsSize = new ArrayList<>(); //对应池化层长度
    private ArrayList<double[][]> kernels= new ArrayList<>(); //卷积核
    private double  input[][];
    private ArrayList<double[][]> outs = new ArrayList<>(); //结果

    public CNN(ArrayList<Integer> kernelsSize, ArrayList<Integer> kernelsStepSize, ArrayList<Integer> poolsSize, ArrayList<double[][]> kernels, double[][] input) {
        this.kernelsSize = kernelsSize;
        this.kernelsStepSize = kernelsStepSize;
        this.poolsSize = poolsSize;
        this.kernels = kernels;
        this.input = input;

        for(int i =0;i< kernelsSize.size();++i){
            int outW = ((input[0].length-kernelsSize.get(i))/kernelsStepSize.get(i))+1;
            int outH = ((input.length-kernelsSize.get(i))/kernelsStepSize.get(i))+1;
            outs.add(new double[outH][outW]);
        }
    }

    /**
     * 向前传播
     */
    public void formWard(){

        //直接进行卷积
        for(int i = 0;i<outs.size();++i){ //输出层数
            for(int y= 0;y< outs.get(i).length;++y){  //对应层数卷积输出的y遍历
                for(int x = 0; x<outs.get(i)[y].length;++x){ //对应层数卷积输出的x遍历
                    int Xmin = x,Xmax = x+kernelsSize.get(i);
                    int Ymin = y,Ymax = y+kernelsSize.get(i);
                    double res=0;
                    for(int inputY=Ymin ; inputY<Ymax;++inputY){
                        for(int inputX = Xmin; inputX<Xmax;++inputX){
                            res+=(input[inputY][inputX]*kernels.get(i)[inputY-y][inputX-x]);
                        }
                    }
                    outs.get(i)[y][x] = res;
                }
            }
        }

        //非线性操作层,这里采用的是REUL函数
        for(int i = 0;i< outs.size();++i){
            for(int y=0;y < outs.get(i).length;++y){
                for (int x = 0; x < outs.get(i)[y].length; x++) {
                    outs.get(i)[y][x] = Math.max(0.1 * outs.get(i)[y][x],outs.get(i)[y][x]);
                }
            }
        }

        //池化层
        for(int i = 0;i<poolsSize.size();++i){
            double[][] out = outs.get(i);
            boolean isCanPool=out.length%poolsSize.get(i) ==0? true: false;
            if(!isCanPool) {
                try {
                    throw new Exception("池化失败");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            double[][] res  = new double[out.length/poolsSize.get(i)][out[0].length/poolsSize.get(i)];

            for(int y = 0;y < res.length;++y){
                for(int x = 0; x< res[y].length;++x){
                    int Xmin = x*poolsSize.get(i),Xmax = (x*poolsSize.get(i))+poolsSize.get(i);
                    int Ymin = y*poolsSize.get(i),Ymax = (y*poolsSize.get(i))+poolsSize.get(i);
                    res[y][x] = maxPool(out,Xmin,Xmax,Ymin,Ymax);
                }
            }
            outs.set(i,res);
        }

    }

    /**
     * 最大特征值池化
     * @return
     */
    double maxPool(double out[][],int Xmin,int Xmax,int Ymin,int Ymax){
        double max = Double.MIN_VALUE;
        for(int y = Ymin;y<Ymax;++y){
            for (int x = Xmin; x < Xmax; x++) {
                max = max<out[y][x]? out[y][x]: max;
            }
        }
        return max;
    }


    public ArrayList<double[][]> getOuts() {
        return outs;
    }

    /**
     * 打印所有层数的卷积结果
     */
    public void printInput(){
        for(int i=0;i< outs.size();++i){
            for(int y = 0;y<outs.get(i).length;++y){
                for(int x = 0; x< outs.get(i)[y].length;++x){
                    System.out.print(outs.get(i)[y][x]+" ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println();
        }
    }

}
