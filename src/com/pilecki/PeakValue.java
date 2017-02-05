package com.pilecki;


import java.util.Random;

import static java.lang.Math.pow;

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
    private int maximumValue;
    private int maximumValueGlobal;
    private int maximumGene;
    private  int maximumGeneLocal;
    private Individual[] populationArray;
    private Individual[] tempPopulationArrayParents;
    private Individual[] tempPopulationArrayChildren;
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
        maxIndyvidualFitnessValue = Integer.MIN_VALUE;
        this.maximumValue = Integer.MIN_VALUE;
        maximumGene = 0;
        maximumGeneLocal = 0;

        populationArray = new Individual[population];
        tempPopulationArrayParents = new Individual[population];
        tempPopulationArrayChildren = new Individual[population];

        for(int i =0 ; i<populationArray.length;i++){
            tempPopulationArrayChildren[i]=new Individual();
        }


        for (int i = 0; i < populationArray.length; i++) {
            populationArray[i] = new Individual();
            populationArray[i].randGenes();
            int tempFitnessRate = evaluateFitnessRate(populationArray[i].getGeneDec());
            populationArray[i].setFitnessRate(tempFitnessRate);
            if (populationArray[i].getFitnessRate() > maxIndyvidualFitnessValue) {
                maxIndyvidualFitnessValue = populationArray[i].getFitnessRate();
                maximumValueGlobal = populationArray[i].getGeneDec();
                maximumGene = populationArray[i].getGeneDec();
            }
        }

        startIterations();
        maximumValueGlobal = evaluateFitnessRate(maximumGene);
        System.out.println("Najwieksza wartosc : "+maximumValueGlobal );
        System.out.println("Gen o najwiekszej wartosci:" + maximumGene);
        System.out.println("Liczba iteracji"+(totalIterationNumber-50));
    }

    private void startIterations() {
        float[] rouletteArrayFitness = new float[populationArray.length];
        float[] rouletteArrayNewGenes = new float[populationArray.length];
        int fitnessRateSum = 0;



        for (int i = 0; i < maxIterationNumber; i++) {
            float probabilitySum = 0;
            totalIterationNumber++;
            for(int J =0 ; J<populationArray.length;J++){
                tempPopulationArrayChildren[J]=new Individual();
            }

            fitnessRateSum = calculateFitnessSum();

            for (int j = 0; j < populationArray.length; j++) {
                if(j==0){
                    rouletteArrayFitness[j] = (float)populationArray[j].getFitnessRate()/fitnessRateSum;
                }else{
                    rouletteArrayFitness[j] = (float)populationArray[j].getFitnessRate()/fitnessRateSum + rouletteArrayFitness[j-1];
                }

            }

            for (int j = 0; j < populationArray.length; j++) {
                float tempFloat = generator.nextFloat();
                boolean isItSet = false;
                for (int k = 0; k < populationArray.length; k++) {
                    if (tempFloat <= rouletteArrayFitness[k]  && !isItSet) {
                        tempPopulationArrayParents[j] = new Individual();
                        tempPopulationArrayParents[j].setGeneDec(populationArray[k].getGeneDec());
                        isItSet = true;

                    }
                }
            }


            startCrossOver(tempPopulationArrayParents);
            startMutation();
            copyfromChildrenArrayToPopulationArray();

            maxIndyvidualFitnessValue=0;
            for(int j=0;j<populationArray.length;j++){

                int tempFitnessRate = evaluateFitnessRate(populationArray[j].getGeneDec());
                populationArray[j].setFitnessRate(tempFitnessRate);
                if (populationArray[j].getFitnessRate() > maxIndyvidualFitnessValue) {
                    maxIndyvidualFitnessValue = populationArray[j].getFitnessRate();

                    maximumGeneLocal = populationArray[j].getGeneDec();
                }
            }

            if(maximumValueGlobal<maximumValue){
                maximumValueGlobal = maximumValue;
                maximumGene = maximumGeneLocal;
            }

        }


    }

    private void copyfromChildrenArrayToPopulationArray() {
        for (int i = 0; i < populationArray.length; i++) {
            populationArray[i].setGeneBin(tempPopulationArrayChildren[i].getGeneBin());
            populationArray[i].setFitnessRate(0);
        }
    }

    private void startMutation() {
        String tempGeneBin;
        for (int i = 0; i < populationArray.length; i++) {
            if (randomMutationRate() <= mutationRate) {
                int locus = generator.nextInt(5);
                tempGeneBin = tempPopulationArrayChildren[i].getGeneBin();
                StringBuilder builder = new StringBuilder(tempGeneBin);
                if (builder.charAt(locus) == '0') {
                    builder.replace(locus, locus, "1");
                    tempGeneBin = builder.toString();
                    tempPopulationArrayChildren[i].setGeneBin(tempGeneBin);
                } else {
                    builder.replace(locus, locus, "0");
                    tempGeneBin =builder.toString();
                    tempPopulationArrayChildren[i].setGeneBin(tempGeneBin);
                }

            }
        }
    }

    private float randomMutationRate() {
        float tempFloat = generator.nextFloat();
        return tempFloat;
    }

    private void startCrossOver(Individual[] tempPopulationArrayParents) {
        Individual[] parentsArray = tempPopulationArrayParents;
        String parent1;
        String parent2;

        for (int i = 0; i < populationArray.length; i++) {

            if (i % 2 == 0) {
                if (randomCrossOverRate() <= crossoverRate) {
                    String substringParentAfterLocus1;
                    String substringParentAfterLocus2;
                    parent1 = parentsArray[i].getGeneBin();
                    parent2 = parentsArray[i + 1].getGeneBin();
                    int locus = generator.nextInt(4) + 1;

                    substringParentAfterLocus1 = parent1.substring(locus, parent1.length());
                    parent1 = parent1.substring(0, locus);
                    substringParentAfterLocus2 = parent2.substring(locus, parent2.length());
                    parent2 = parent2.substring(0, locus);

                    /*parent1.concat(substringParentAfterLocus2);
                    parent2.concat(substringParentAfterLocus1);*/

                    parent1 = parent1 + substringParentAfterLocus2;
                    parent2 = parent2 + substringParentAfterLocus1;


                    tempPopulationArrayChildren[i].setGeneBin(parent1);
                    tempPopulationArrayChildren[i + 1].setGeneBin(parent2);

                }else{
                    if(i<populationArray.length){
                        tempPopulationArrayChildren[i].setGeneBin(parentsArray[i].getGeneBin());
                        tempPopulationArrayChildren[i + 1].setGeneBin(parentsArray[i + 1].getGeneBin());
                    }
            }

            }


        }

    }

    private float randomCrossOverRate() {
        float tempFloat = generator.nextFloat();
        return tempFloat;

    }


    public int evaluateFitnessRate(int GeneDec) {
        int result;
        result = (int) ((int) (a*(pow(GeneDec,3.0)))+b*(pow(GeneDec,2.0)))+c*GeneDec+d;
        return result;
    }

    public int findMaxIndyvidual() {
        int tempMaxFitnessValue = 0;
        int tempMaxGeneValue;
        for (int i = 0; i < populationArray.length; i++) {
            if (tempMaxFitnessValue < populationArray[i].getFitnessRate()) {
                tempMaxFitnessValue = populationArray[i].getFitnessRate();
                tempMaxGeneValue = populationArray[i].getGeneDec();
            }
        }
        return tempMaxFitnessValue;
    }

    public int calculateFitnessSum() {
        int fitnessRateSum = 0;
        for (int i = 0; i < populationArray.length; i++) {
            fitnessRateSum += populationArray[i].getFitnessRate();
        }
        return fitnessRateSum;
    }

}
