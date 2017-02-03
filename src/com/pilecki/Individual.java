package com.pilecki;

import java.util.Random;

/**
 * Created by sbmaniac on 03.02.2017.
 */
public class Individual {

    private int geneDec;
    private String geneBin;
    private int fitnessRate;


    public int getGeneDec() {
        return geneDec;
    }

    public void setGeneDec(int geneDec) {
        this.geneDec = geneDec;
        setGeneBinAfterGenDecModif(geneDec);
    }

    private void setGeneBinAfterGenDecModif(int geneDec) {
        geneBin = convertFromDecToBin(geneDec);

    }

    public String getGeneBin() {
        return geneBin;
    }

    public void setGeneBin(String geneBin) {
        this.geneBin = geneBin;
        setGeneDecAfterGenBinModif(geneBin);
    }

    private void setGeneDecAfterGenBinModif(String geneBin) {
        geneDec = convertFromBinToDec(geneBin);
    }

    public int getFitnessRate() {
        return fitnessRate;
    }

    public void setFitnessRate(int fitnessRate) {
        this.fitnessRate = fitnessRate;
    }

    public void randGenes() {
        Random generator = new Random();
        setGeneDec(generator.nextInt(32));
    }



    public String convertFromDecToBin(int value) {
        String resultWithFiveBits = "";
        String convertedInteger = Integer.toBinaryString(value);
        if (convertedInteger.length() < 5) {
            for (int i = 0; i < (5 - convertedInteger.length()); i++) {
                resultWithFiveBits = resultWithFiveBits + "0";
            }
            return resultWithFiveBits.concat(convertedInteger);
        } else {
            return convertedInteger;
        }


    }

    public int convertFromBinToDec(String value) {
        int x = Integer.parseInt(value, 2);
        return x;
    }


}
