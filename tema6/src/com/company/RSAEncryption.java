package com.company;

import java.math.BigInteger;

public class RSAEncryption {
    private BigInteger plaintext;
    private BigInteger ciphertext;
    private BigInteger e;
    private BigInteger n;
    public RSAEncryption(){
    }
    public RSAEncryption(BigInteger plaintext,BigInteger e,BigInteger n){
        this.plaintext = plaintext;
        this.e = e;
        this.n = n;
    }

    public BigInteger getPlaintext() {
        return this.plaintext;
    }

    public void setPlaintext(BigInteger plaintext) {
        this.plaintext = plaintext;
    }

    public BigInteger getE() {
        return this.e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getN() {
        return this.n;
    }

    public void setN(BigInteger n) {
        this.n = n;
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

    public void setCiphertext(){
        System.out.println("aici");
        BigInteger expResult = exp_by_squaring_iterative(this.plaintext,this.e);
        this.ciphertext = expResult.mod(this.n);
    }

    public BigInteger getCiphertext() {
        return this.ciphertext;
    }
}
