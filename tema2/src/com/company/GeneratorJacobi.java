package com.company;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.math.*;

public class GeneratorJacobi {
    private Setup setup;
    private int l;
    private String sequence;

    public GeneratorJacobi(){}

    /**
     * Constructor pentru cel de-al doilea generator.
     * @param setup - folosim un setup prestabilit (il vom utiliza pe cel pe care il utilizam si la primul generator)
     */
    public GeneratorJacobi(int l, Setup setup){
        this.l = l;
        this.setup = setup;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    /**
     * Calculam simbolul jacobi pt n si k
     * @param n - primul nr
     * @param k - al doilea nr
     * @return simbolul jacobi pt n si k
     */
    public BigInteger jacobiSymbol(BigInteger n,BigInteger k)
    {
        BigInteger t = new BigInteger("1");
        BigInteger r;
        n = n.mod(k);
        while(n.compareTo(BigInteger.ZERO) != 0){
            while(n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){
                n = n.divide(BigInteger.valueOf(2));
                r = k.mod(BigInteger.valueOf(8));
                if(r.compareTo(BigInteger.valueOf(3)) == 0 || r.compareTo(BigInteger.valueOf(5)) == 0){
                    t = t.negate();
                }
            }
            BigInteger aux;
            aux = n;
            n = k;
            k = aux;
            if(n.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0 && k.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0){
                t = t.negate();
            }
            n = n.mod(k);
        }
        if(k.compareTo(BigInteger.ONE) == 0){
            return t;
        }else{
            return BigInteger.ZERO;
        }
    }

    /**
     * Pentru 1<=i<=16, a va fi seed+i si se calculeaza simbolul jacobi pentru a si n. Rezultatul din urma simbolului jacobi se va adauga in secventa- daca rezultatul este -1 vom adauga 0.
     * @return secventa compusa de biti
     */
    public String algorithm()
    {
        StringBuilder stringBuilder = new StringBuilder();
        long s = setup.getSeed();
        BigInteger a;
        BigInteger result;
        BigInteger gcd;
        for(int i = 1; i <= this.l; i++)
        {
            a = BigInteger.valueOf(s + i);
            System.out.println("a = " + a);
            gcd = a.gcd(setup.getN());
            if(gcd.compareTo(BigInteger.ONE) != 0)//nu sunt coprime
            {
                System.out.println("nu sunt coprime, gcd: " + gcd);
                result = BigInteger.ZERO;
            }
            else {
                if (setup.getN().mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0) {
                    System.out.println("Al doilea numar trebuie sa fie impar!");
                    return null;
                } else {
                    result = jacobiSymbol(a,setup.getN());
                    System.out.println("Result: " + result);
                    if (result.compareTo(BigInteger.ONE.negate()) == 0)//secventa alcatuita trebuie sa fie binara...-1 va fi 0
                    {
                        result = BigInteger.ZERO;
                    }
                }
            }
            stringBuilder.append(result.intValue());
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
        return "GeneratorJacobi{" +
                "setup=" + setup + "\n" +
                "sequence='" + sequence + '\'' +
                '}';
    }
}
