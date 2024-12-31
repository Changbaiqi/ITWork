package com.cbq.it.core;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CNNTest {

    @Test
    public void input() {
    }

    @Test
    public void formWard() {
    }

    @Test
    public void printInput() {
        CNN cnn = new CNN(
                new ArrayList<Integer>(Arrays.asList(3)),
                new ArrayList<Integer>(Arrays.asList(1)),
                new ArrayList<Integer>(Arrays.asList(2)),
                new ArrayList<double[][]>(Arrays.<double[][]>asList(
                        new double[][]{
                                {1, 1, 1},
                                {1, 2, 1},
                                {2, 3, 2}
                        }
                )),
                new double[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 0, 0},
                        {0, 1, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0, 0},
                        {0, 1, 1, 1, 1, 0}
                });
        cnn.formWard();
        cnn.printInput();
    }
}