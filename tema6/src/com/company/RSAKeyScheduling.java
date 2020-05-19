package com.company;

import java.math.BigInteger;
import java.util.Random;

public class RSAKeyScheduling {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e = BigInteger.ONE;
    private BigInteger d;
    private final int length1 = 512;
    private int lengthForE;
    private final Random r = new Random();

    public RSAKeyScheduling(){}

    public void generate(){
        this.setP();
        this.setQ();
        this.setN();
        this.setPhi();
        this.setE();
        this.setD();
    }

    public void setP(){
        this.p = BigInteger.probablePrime(this.length1,this.r);
    }

    public BigInteger getP() {
        return this.p;
    }

    public void setQ(){
        this.q = BigInteger.probablePrime(this.length1,this.r);
    }

    public BigInteger getQ() {
        return this.q;
    }

    public void setN(){
        this.n = this.p.multiply(this.q);
    }

    public BigInteger getN() {
        return this.n;
    }

    public void setPhi(){
        this.phi = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
    }

    public BigInteger getPhi() {
        return this.phi;
    }

    public int getLengthForE() {
        return this.lengthForE;
    }

    public void setLengthForE(int lengthForE) {
        this.lengthForE = lengthForE;
    }

    public void setE(){
        while(!(this.e.compareTo(BigInteger.ONE) > 0 && this.e.compareTo(this.phi) < 0 && this.e.gcd(this.phi).compareTo(BigInteger.ONE) == 0)){
            this.e = new BigInteger(this.lengthForE,r);
        }
    }

    public BigInteger getE() {
        return this.e;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public void setPhi(BigInteger phi) {
        this.phi = phi;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger inverseModular(BigInteger a, BigInteger n){
        BigInteger t = BigInteger.ZERO;
        BigInteger r = n;
        BigInteger newT = BigInteger.ONE;
        BigInteger newR = a;
        BigInteger quotient;
        BigInteger auxT, auxR;

        while(newR.compareTo(BigInteger.ZERO) != 0){
            quotient = r.divide(newR);
            auxT = newT;
            newT = t.subtract(quotient.multiply(auxT));
            t = auxT;
            auxR = newR;
            newR = r.subtract(quotient.multiply(auxR));
            r = auxR;
        }

        if(r.compareTo(BigInteger.ONE) > 0){
            System.out.println(a + " is not invertible");
            return BigInteger.ONE.negate();
        }
        if(t.compareTo(BigInteger.ZERO) < 0){
            t = t.add(n);
        }
        return t;
    }

    public void setD(){
        //d este inversul modular al lui e mod phi
        //d * e congr. 1 mod phi
        //e si phi coprime
        this.d = inverseModular(this.e,this.phi);
    }

    public BigInteger getD() {
        return this.d;
    }
}
