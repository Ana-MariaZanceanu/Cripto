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
     * Incepem verificarile pt cel mai mic divizor prim posibil, 2. Cat timp nr in cauza este divizibil cu 2 acesta va fi impartit la 2 si va creste exponentul corespunzator lui. Dupa aceea, se va adauga in map perechea corespunzatoare <divizor,exponent>. Se continua cu acelasi proces si pentru urmatorii divizori primi posibili, pana in momentul in care divizor*divizor>=nr de descompus.
     * @param number - numarul care se va descompune in factori primi
     * @return un map  ce va contine decompunerea in factori primi a nr dat ca parametru
     */
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
//        BigInteger halfNumber = number.divide(BigInteger.valueOf(2));
//        while(divisor.compareTo(halfNumber) < 0 || divisor.multiply(divisor).compareTo(number) == 0)
//        {
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

    /**
     * Daca a este compus, il descompunem apoi calculam fiecare simbol jacobi (p/n) unde p este un factor prim al lui a; rezultatul va fi ridicat la exponentul factorului prim in a, apoi in result se multiplica totul.
     * Altfel:
     * daca n=1, rezultatul este 1
     * daca a=1, rezultatul este 1
     * daca a=0 sau n=0, rezultatul este 0
     * daca a=-1:
     *          daca n mod 4 = 1 atunci rezultatul este 1
     *          daca n mod 4 = 3 atunci rezultatul este -1
     * daca a=2:
     *          daca n mod 8 = +/- 1 (de fapt 1 sau 7-acesta fiind corespondentul lui -1 mod 8) atunci rezultatul este 1
     *          daca n mod 8 = +/- 3 (de fapt 3 sau 5-acesta fiind corespondentul lui -3 mod 8) atunci rezultatul este -1
     * daca a si n sunt impare:
     *          daca n mod 4 = 1 sau a mod 4 = 1 atunci rezulatatul este (n/a)
     *          daca n mod 4 = 3 si a mod 4 = 3 atunci rezultatul este -(a/n)
     * @param a primul nr din perechea pt care se calculeaza simbolul jacobi
     * @param n al doilea nr din perechea pt care se calculeaza simbolul jacobi
     * @return rezultatul calculului (a/n)
     */
    public BigInteger jacobiSymbol(BigInteger a,BigInteger n)
    {
        BigInteger result = new BigInteger("1");
        Map<BigInteger,Integer> factorizationForA = new HashMap<>();
        if(!(a.isProbablePrime(1)))
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

    /**
     * Acesta este algoritmul prin care se gaseste secventa binara corespunzatoare.
     * In descompunerea in factori primi putem pune din prima nr p si q intrucat n=p*q si p si q sunt prime.
     * a va fi la fiecare iteratie seed + i, iar daca a si n nu sunt coprime, putem spune ca rezultatul pentru iteratia in cauza este 0 (conform algoritmului de calcul al simbolului jacobi).
     * Daca n este compus, il descompunem in factori primi si calculam fiecare simbol jacobi (a/p) unde p este factor prim al lui n; rezultatul va fi ridicat la exponentul factorului prim in n, apoi in result se multiplica totul.
     * Altfel, daca n este prim, se calculeaza simbolul jacobi (a/n).
     * Rezultatul din urma fiecarei iteratii se adauga la secventa. (daca rezultatul este -1, vom pune 0, pentru a avea sens testul de comparatie intre nr de 1 si nr de 0)
     * @return secventa binara compusa
     */
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
                if(!(setup.getN().isProbablePrime(1)))//
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
            if(result.compareTo(BigInteger.ONE.negate()) == 0)//secventa alcatuita trebuie sa fie binara...-1 va fi 0
            {
                result = BigInteger.ZERO;
            }
            stringBuilder.append(result.intValue());
        }
        sequence = new String(stringBuilder);
        return sequence;
    }

    public BigInteger jacobiSymbolResult(BigInteger a,BigInteger n)
    {
        BigInteger result = new BigInteger("1");
        Map<BigInteger,Integer> factorizationForA = new HashMap<>();
        if(!(a.isProbablePrime(1)))
        {
            factorizationForA = simplePrimeFactorization(a);
            System.out.println("Descompunerea in factori primi pentru " + a + ":");
            System.out.println(factorizationForA);
            Iterator it = factorizationForA.entrySet().iterator();
            Map.Entry pair;
            while(it.hasNext())
            {
                pair = (Map.Entry) it.next();
                result = result.multiply(jacobiSymbolResult((BigInteger)pair.getKey(),n).pow((int)pair.getValue()));
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

    public Map<BigInteger,Integer> simplePrimeFactorization(BigInteger number)
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
        BigInteger halfNumber = number.divide(BigInteger.valueOf(2));
        while(divisor.compareTo(halfNumber) < 0 || divisor.multiply(divisor).compareTo(number) == 0)
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

    public BigInteger getJacobiSymbolResult(BigInteger m,BigInteger n){
        BigInteger result = new BigInteger("1");
        BigInteger gcd;
        gcd = m.gcd(n);
        Map<BigInteger,Integer> factorizationForN = new HashMap<>();
        if(gcd.compareTo(BigInteger.ONE) != 0)//nu sunt coprime
        {
            System.out.println("nu sunt coprime, gcd: " + gcd);
            result = BigInteger.ZERO;
        }
        else
        {
            if(n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){
                System.out.println("Al doilea numar trebuie sa fie impar!");
                result = null;
            }else{
                if(!(n.isProbablePrime(1)))
                {
                    factorizationForN = simplePrimeFactorization(n);
                    System.out.println("Descompunerea in factori primi pentru " + n + ":");
                    System.out.println(factorizationForN);
                    Iterator it = factorizationForN.entrySet().iterator();
                    Map.Entry pair;
                    while(it.hasNext())
                    {
                        pair = (Map.Entry) it.next();
                        result = result.multiply(jacobiSymbolResult(m,(BigInteger) pair.getKey()));
                    }
                }
                else
                    result = jacobiSymbolResult(m,n);
            }
        }
        return result;
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
