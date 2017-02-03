package com.pilecki;


import java.util.Random;

/**
 * Created by sbmaniac on 03.02.2017.
 */
public class PeakValue {
    private int a, b, c, d;
    private int population;
    private float crossoverRate;
    private float mutationRate;
    private int maxIterationNumber;
    private int totalIterationNumber;
    private int[] parentsArray = new int[population];
    Random generator = new Random();

    public PeakValue(int a, int b, int c, int d, float crossoverRate, float mutationRate, int population, int maxIterationNumber) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.population = population;
        this.maxIterationNumber = maxIterationNumber;
        this.totalIterationNumber = 0;
        initialFillOfParentsArray();
    }


    private void initialFillOfParentsArray() {
        for (int i = 0; i < parentsArray.length; i++) {
            parentsArray[i] = generator.nextInt(32);
        }
    }
}
