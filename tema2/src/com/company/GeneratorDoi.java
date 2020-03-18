package com.company;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.math.*;

public class GeneratorDoi {
    private Setup setup;
    private int l;
    private String sequence;

    /**
     * Constructor pentru cel de-al doilea generator.
     * @param setup - folosim un setup prestabilit (il vom utiliza pe cel pe care il utilizam si la primul generator)
     */
    public GeneratorDoi(Setup setup){
        l = 16;
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

    public Map<BigInteger,Integer> primeFactorization(BigInteger number)
    {
        Map<BigInteger,Integer> factorization = new HashMap<>();
        BigInteger divisor = BigInteger.valueOf(2);
        int exponent = 0;
        int ok = 0;
        BigInteger copy = number;
        while(copy.mod(divisor).equals(BigInteger.ZERO))
        {
            ok = 1;
            copy = copy.divide(divisor);
            exponent++;
        }
        if(ok == 1)
        {
            factorization.put(divisor,new Integer(exponent));
            ok = 0;
            exponent = 0;
        }
        divisor = BigInteger.valueOf(3);
        while(divisor.multiply(divisor).compareTo(number) < 0 || divisor.multiply(divisor).compareTo(number) == 0)
        {
            if(divisor.isProbablePrime(1))
            {
                while(copy.mod(divisor).equals(BigInteger.ZERO))
                {
                    ok = 1;
                    copy = copy.divide(divisor);
                    exponent++;
                }
                if(ok == 1)
                {
                    factorization.put(divisor,new Integer(exponent));
                    ok = 0;
                    exponent = 0;
                }
            }
            divisor = divisor.add(BigInteger.valueOf(2));
        }
        return factorization;
    }

    public BigInteger jacobiSymbol(BigInteger a,BigInteger n)
    {
        BigInteger result = new BigInteger("1");
        Map<BigInteger,Integer> factorizationForA = new HashMap<>();
        if(!(a.isProbablePrime(1)))///daca a este compus, il descompunem apoi calculam fiecare simbol jacobi (p/n) unde p este un factor prim al lui a; rezultatul va fi ridicat la exponentul factorului prim in a, apoi in result se multiplica totul
        {
            factorizationForA = primeFactorization(a);
            Iterator it = factorizationForA.entrySet().iterator();
            Map.Entry pair;
            while(it.hasNext())
            {
                pair = (Map.Entry) it.next();
                result = result.multiply(jacobiSymbol((BigInteger)pair.getKey(),n).pow((int)pair.getValue()));
            }
        }
        else {
            if (n.equals(BigInteger.ONE)) {
                result = BigInteger.ONE;
                return result;
            }
            else {
                if (a.equals(BigInteger.ONE)) {
                    result = BigInteger.ONE;
                    return result;
                }
                else {
                    if (a.equals(BigInteger.ZERO) || n.equals(BigInteger.ZERO)) {
                        result = BigInteger.ZERO;
                        return result;
                    }
                    else {
                        if (a.equals(BigInteger.valueOf(1).negate()))
                        {
                            if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.ONE))
                            {
                                result = BigInteger.ONE;
                                return result;
                            }
                            else {
                                if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))
                                {
                                    result = BigInteger.valueOf(1).negate();
                                    return result;
                                }
                            }
                        }
                        else {
                            if (a.equals(BigInteger.valueOf(2))) {
                                if (n.mod(BigInteger.valueOf(8)).equals(BigInteger.ONE) || n.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(7))) {
                                    result = BigInteger.ONE;
                                    return result;
                                } else {
                                    if (n.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || n.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))) {
                                        result = BigInteger.valueOf(1).negate();
                                        return result;
                                    }
                                }
                            }
                            else
                            {
                                if (a.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE) && n.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE))
                                {
                                    if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.ONE) || a.mod(BigInteger.valueOf(4)).equals(BigInteger.ONE)) {
                                        return result;
                                    } else {
                                        if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                                            result = result.negate();
                                            return result;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public String algorithm()
    {
        StringBuilder stringBuilder = new StringBuilder();
        long s = setup.getSeed();
        BigInteger a;
        Map<BigInteger,Integer> factorizationForN = new HashMap<>();
        factorizationForN.put(setup.getP(),1);
        factorizationForN.put(setup.getQ(),1);
        BigInteger result = new BigInteger("1");
        BigInteger gcd;
        for(int i = 1; i <= this.l; i++)
        {
            a = BigInteger.valueOf(s + i);
            gcd = a.gcd(setup.getN());
            if(gcd.compareTo(BigInteger.ONE) != 0)//nu sunt coprime
            {
                System.out.println("nu sunt coprime, gcd: " + gcd);
                result = BigInteger.ZERO;
            }
            else
            {
                if(!(setup.getN().isProbablePrime(1)))//daca n este compus, il descompunem in factori primi si calculam fiecare simbol jacobi (a/p) unde p este factor prim al lui n; rezultatul va fi ridicat la exponentul factorului prim in n, apoi in result se multiplica totul
                {
                    Iterator it = factorizationForN.entrySet().iterator();
                    Map.Entry pair;
                    while(it.hasNext())
                    {
                        pair = (Map.Entry) it.next();
                        result = result.multiply(jacobiSymbol(a,(BigInteger) pair.getKey()));
                    }
                }
                else
                    result = jacobiSymbol(a,setup.getN());
            }
            if(result.compareTo(BigInteger.ONE.negate()) == 0)//secventa alcatuita trebuie sa fie binara...deci -1 va deveni 1 (-1 mod 2)
            {
                result = BigInteger.ONE;
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
        return "GeneratorDoi{" +
                "setup=" + setup + "\n" +
                "sequence='" + sequence + '\'' +
                '}';
    }
}
