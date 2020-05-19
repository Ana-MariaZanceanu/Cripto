package com.company;

import java.math.BigInteger;

public class RSADecryption {
    private BigInteger ciphertext;
    private BigInteger plaintext;
    private BigInteger d;
    private BigInteger n;
    private BigInteger p,q;

    public RSADecryption(){
    }

    public RSADecryption(BigInteger ciphertext,BigInteger d,BigInteger n,BigInteger p,BigInteger q){
        this.ciphertext = ciphertext;
        this.d = d;
        this.n = n;
        this.p = p;
        this.q = q;
    }

    public BigInteger getCiphertext() {
        return this.ciphertext;
    }

    public void setCiphertext(BigInteger ciphertext) {
        this.ciphertext = ciphertext;
    }

    public BigInteger getD() {
        return this.d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getN() {
        return this.n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getP() {
        return this.p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger exp_by_squaring(BigInteger x,BigInteger n){
        System.out.println("x " + x);
        System.out.println("n " + n);
        if(n.compareTo(BigInteger.ZERO) < 0){
            return exp_by_squaring(BigInteger.ONE.divide(x),n.negate());
        }else{
            if(n.compareTo(BigInteger.ZERO) == 0){
                return BigInteger.ONE;
            }else{
                if(n.compareTo(BigInteger.ONE) == 0){
                    return x;
                }else{
                    if(n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){
                        return exp_by_squaring(x.multiply(x),n.divide(BigInteger.valueOf(2)));
                    }else{
                        return x.multiply(exp_by_squaring(x.multiply(x),n.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2))));
                    }
                }
            }
        }
    }

    public BigInteger exp_by_squaring_iterative(BigInteger x, BigInteger n){
        if(n.compareTo(BigInteger.ZERO) < 0){
            x = BigInteger.ONE.divide(x);
            n = n.negate();
        }
        if(n.compareTo(BigInteger.ZERO) == 0){
            return BigInteger.ONE;
        }
        BigInteger y = BigInteger.ONE;
        while(n.compareTo(BigInteger.ONE) > 0){
            if(n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){
                x = x.multiply(x);
                n = n.divide(BigInteger.valueOf(2));
            }else{
                y = x.multiply(y);
                x = x.multiply(x);
                n = n.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
            }
        }
        return x.multiply(y);
    }

    public void setPlaintext(){
        this.plaintext = this.ciphertext.modPow(this.d,this.n);
    }

    public BigInteger getPlaintext() {
        return this.plaintext;
    }

    public void setPlaintextWithCRT(){
        BigInteger dP = this.d.mod(this.p.subtract(BigInteger.ONE));
        BigInteger dQ = this.d.mod(this.q.subtract(BigInteger.ONE));
        BigInteger qInv = new RSAKeyScheduling().inverseModular(this.q,this.p);
        BigInteger m1 = this.ciphertext.modPow(dP,this.p);
        BigInteger m2 = this.ciphertext.modPow(dQ,this.q);
        BigInteger h = qInv.multiply(m1.subtract(m2)).mod(this.p);
        this.plaintext = m2.add(h.multiply(this.q));
    }
}
