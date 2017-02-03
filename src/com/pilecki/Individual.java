package com.pilecki;

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
        setGeneAfterGenDecModif(geneDec);
    }

    private void setGeneAfterGenDecModif(int geneDec) {
        geneBin = convertFromDecToBin(geneDec);

    }

    public int getFitnessRate() {
        return fitnessRate;
    }

    public void setFitnessRate(int fitnessRate) {
        this.fitnessRate = fitnessRate;
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
