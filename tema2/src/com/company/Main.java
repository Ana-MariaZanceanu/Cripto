package com.company;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        GeneratorUnu generatorUnu = new GeneratorUnu(16);
        generatorUnu.algorithm();
        System.out.println(generatorUnu);
        generatorUnu.test();

        GeneratorDoi generatorDoi = new GeneratorDoi(generatorUnu.getSetup());
        generatorDoi.algorithm();
        System.out.println(generatorDoi);
        generatorDoi.test();
    }
}
