package com.company;

import java.math.BigInteger;

public class GeneratorBBS {
    private Setup setup;
    private String sequence;
    private int l;

    public GeneratorBBS(){}

    public GeneratorBBS(int l, Setup setup){
        this.setup = setup;
        this.l = l;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public Setup getSetup() {
        return setup;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    /**
     * Acesta reprezinta algoritmul de generare a unei secvente de l biti. Folosind un setup prestabilit (p si q 2 nr mari, prime, p si q congruente cu 3 mod 4, n nr mare, n = p * q, iar seed reprezinta timpul sistemului in milisecunde), compunem o secventa de l biti.
     * @return secventa alcatuita
     */
    public String algorithm()
    {
        StringBuilder stringBuilder = new StringBuilder();
        BigInteger s = new BigInteger(Long.toString(setup.getSeed()));
        BigInteger x1, x2;
        BigInteger doi = new BigInteger("2");
        x1 = s.multiply(s).mod(setup.getN());
        for(int i = 0; i < this.l; i++)
        {
            x2 = x1.multiply(x1).mod(setup.getN());
            stringBuilder.append(x2.mod(doi));
            x1 = x2;
        }
        sequence = new String(stringBuilder);
        return sequence;
    }

    public void test()
    {
        char[] chars = sequence.toCharArray();
        int nr0 = 0;
        int nr1 = 0;
        for(char c : chars)
        {
            if(c == '0')
                nr0++;
            else
                nr1++;
        }
        if(Math.abs(nr0 - nr1) <=3)
            System.out.println("Test trecut!");
        else
            System.out.println("Test picat!");
    }

    @Override
    public String toString() {
        return "GeneratorBBS{" +
                "setup=" + setup + "\n" +
                "sequence=" + sequence +
                '}';
    }
}
