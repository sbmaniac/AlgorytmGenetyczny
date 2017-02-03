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
    private int maxIndyvidualFitnessValue;


    private Individual[] populationArray;
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
        maxIndyvidualFitnessValue = 0;

        populationArray = new Individual[population];

        for (int i = 0; i < populationArray.length; i++) {
            populationArray[i] = new Individual();
            populationArray[i].randGenes();
            int tempFitnessRate = evaluateFitnessRate(populationArray[i].getGeneDec());
            populationArray[i].setFitnessRate(tempFitnessRate);
            if (populationArray[i].getFitnessRate() > maxIndyvidualFitnessValue) {
                maxIndyvidualFitnessValue = populationArray[i].getFitnessRate();
            }
        }

        startIterations();


    }

    private void startIterations() {
        float[] rouletteArrayFitness = new float[populationArray.length];
        float[] rouletteArrayNewGenes = new float[populationArray.length];
        int fitnessRateSum = 0;
        float probabilitySum = 0;
        for (int i = 0; i < maxIterationNumber; i++) {


            fitnessRateSum = calculateFitnessSum();
            for (int j = 0; j < populationArray.length; j++) {
                rouletteArrayFitness[j] = probabilitySum + (populationArray[j].getFitnessRate() / fitnessRateSum);
            }

            for (int j = 0; j < populationArray.length; j++) {
                rouletteArrayNewGenes[j] = generator.nextFloat();
                for (int k = 0; k < populationArray.length; k++) {
                    if (k == 0) {
                        if (0 <= rouletteArrayNewGenes[j] && rouletteArrayNewGenes[j] <= rouletteArrayFitness[j]) {

                        }
                    }
                }
            }


        }


    }


    public int evaluateFitnessRate(int GeneDec) {
        int result;
        result = (a * GeneDec) ^ 3 + (b * GeneDec) ^ 2 + (c * GeneDec) + d;
        return result;
    }

    public void findMaxIndyvidual() {


    }

    public int calculateFitnessSum() {
        int fitnessRateSum = 0;
        for (int i = 0; i < populationArray.length; i++) {
            fitnessRateSum += populationArray[i].getFitnessRate();
        }
        return fitnessRateSum;
    }

}
