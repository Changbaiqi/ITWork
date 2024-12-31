package com.cbq.it.core;

import java.util.ArrayList;

public class BPNNConfig {
    private int inputSize;
    private int outSize;
    private double studyRate;
    private int hiddens[];
    private ArrayList<double[][]> weights;//权值

    public BPNNConfig() {
    }

    public BPNNConfig(int inputSize, int outSize, double studyRate, int[] hiddens, ArrayList<double[][]> weights) {
        this.inputSize = inputSize;
        this.outSize = outSize;
        this.studyRate = studyRate;
        this.hiddens = hiddens;
        this.weights = weights;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getOutSize() {
        return outSize;
    }

    public void setOutSize(int outSize) {
        this.outSize = outSize;
    }

    public ArrayList<double[][]> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<double[][]> weights) {
        this.weights = weights;
    }

    public double getStudyRate() {
        return studyRate;
    }

    public void setStudyRate(double studyRate) {
        this.studyRate = studyRate;
    }

    public int[] getHiddens() {
        return hiddens;
    }

    public void setHiddens(int[] hiddens) {
        this.hiddens = hiddens;
    }
}
