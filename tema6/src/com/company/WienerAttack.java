package com.company;

import java.math.BigInteger;
import java.util.Random;

public class WienerAttack {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e = BigInteger.ONE;
    private BigInteger d;
    private BigInteger l;
    private BigInteger phi;
    private final Random r = new Random();
    private final int length = 512;
    private final int lengthForE = 1024;
    public WienerAttack(){}
    public void generate(){
        this.setQ();
        this.setP();
        this.setN();
        this.setPhi();
        this.setE();
        this.setD();
    }
    public void setQ(){
        this.q = BigInteger.probablePrime(this.length,r);
    }

    public BigInteger getQ() {
        return this.q;
    }

    public void setP(){
        this.p = BigInteger.probablePrime(this.length,r);
        while(!(this.p.compareTo(this.q) > 0 && this.p.compareTo(this.q.multiply(BigInteger.valueOf(2))) < 0)){
            this.p = BigInteger.probablePrime(this.length,r);
        }
    }

    public BigInteger getP() {
        return this.p;
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

    public void setE(){
        while(!(this.e.compareTo(BigInteger.ONE) > 0 && this.e.compareTo(this.phi) < 0 && this.e.gcd(this.phi).compareTo(BigInteger.ONE) == 0)){
            this.e = new BigInteger(this.lengthForE,r);
        }
    }

    public BigInteger getE() {
        return this.e;
    }

    public int criterion(BigInteger l, BigInteger d){
        if(l.compareTo(BigInteger.ZERO) != 0 && d.compareTo(BigInteger.ZERO) != 0){
            BigInteger s = this.n;
            BigInteger p = this.n.subtract(this.e.multiply(d).subtract(BigInteger.ONE).divide(l)).add(BigInteger.ONE);
            BigInteger b = s.multiply(s);
            BigInteger ac = p.multiply(BigInteger.valueOf(4));
            BigInteger delta = b.subtract(ac);
            BigInteger deltaSqrt = delta.sqrt();
            if(delta.compareTo(BigInteger.ZERO) >= 0 && deltaSqrt.compareTo(BigInteger.ZERO) >= 0 && deltaSqrt.multiply(deltaSqrt).compareTo(delta) == 0){
                System.out.println("aici1");
                System.out.println(delta);
                System.out.println(deltaSqrt);
                return 1;
            }
        }
        return 0;
    }

    public BigInteger findD(){
        BigInteger l = BigInteger.ONE;
        BigInteger D = BigInteger.ONE;
        BigInteger quotient1 = this.e.divide(this.n);
        BigInteger remainder1 = this.e.mod(this.n);
        BigInteger alpha1 = quotient1;
        BigInteger beta1 = BigInteger.ONE;
        l = alpha1;
        D = beta1;
        if(criterion(l,D) == 1){
            return D;
        }
        BigInteger quotient2 = this.n.divide(remainder1);
        BigInteger remainder2 = this.n.mod(remainder1);
        BigInteger alpha2 = quotient1.multiply(quotient2).add(BigInteger.ONE);
        BigInteger beta2 = quotient2;
        l = alpha2;
        D = beta2;
        if(criterion(l,D) == 1){
            return D;
        }
        BigInteger quotient = remainder1.divide(remainder2);
        BigInteger remainder = remainder1.mod(remainder2);
        BigInteger alpha = quotient.multiply(alpha2).add(alpha1);
        BigInteger beta = quotient.multiply(beta2).add(beta1);
        l = alpha;
        D = beta;
        if(criterion(l,D) == 1){
            return D;
        }
        do{
            remainder1 = remainder2;
            remainder2 = remainder;
            alpha1 = alpha2;
            alpha2 = alpha;
            beta1 = beta2;
            beta2 = beta;

            quotient = remainder1.divide(remainder2);
            remainder = remainder1.mod(remainder2);
            alpha = quotient.multiply(alpha2).add(alpha1);
            beta = quotient.multiply(beta2).add(beta1);
        }while(criterion(l,D) == 1);
        return D;
    }

    public void setD(){
        this.d = findD();
    }

    public BigInteger getD() {
        return this.d;
    }

}
