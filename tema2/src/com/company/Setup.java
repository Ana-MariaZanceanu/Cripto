package com.company;

import java.math.BigInteger;
import java.util.Random;

public class Setup {
    private int length = 512;
    private BigInteger p = new BigInteger("0");
    private BigInteger q = new BigInteger("0");
    private BigInteger n = new BigInteger("1");
    private long seed;

    /**
     * p si q 2 nr mari, prime, generate random; p si q sunt congruente cu 3 mod 4; n nr mare, n = p * q, iar seed reprezinta timpul sistemului in milisecunde
     */
    public Setup()
    {
        Random r = new Random();
        BigInteger trei = new BigInteger("3");
        BigInteger patru = new BigInteger("4");
        while(p.equals(q) || (!p.mod(patru).equals(trei) && !q.mod(patru).equals(trei)))
        {
            p = BigInteger.probablePrime(length,r);
            q = BigInteger.probablePrime(length,r);
        }
        n = n.multiply(p).multiply(q);
        seed = System.currentTimeMillis();
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public BigInteger getN() {
        return this.n;
    }

    public long getSeed() { return this.seed;}

    @Override
    public String toString() {
        return "Setup{" +
                "length=" + length + "\n" +
                "p=" + p + "(" + p.bitLength() + ")" + "\n" +
                "q=" + q + "(" + q.bitLength() + ")" + "\n" +
                "n=" + n + "(" + n.bitLength() + ")" + "\n" +
                "seed=" + seed +
                '}';
    }
}
