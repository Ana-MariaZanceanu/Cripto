package com.company;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Setup setup = new Setup();
        int ok = 1;
        int comm;
        long executionTimeBBS = 0, executionTimeJacobi = 0, executionTimeLFSR = 0;
        while(ok == 1){
            System.out.println("Menu:\n 1.Generator BBS\n 2.Generator Jacobi\n 3.Generator LFSR\n 4.Criptosistemul RCA\n 5.Calcul simbol Jacobi\n 6.Exit");
            comm = scanner.nextInt();
            switch (comm){
                case 1:
                    GeneratorBBS generatorBBS = new GeneratorBBS(16,setup);
                    long startBBS = System.nanoTime();
                    generatorBBS.algorithm();
                    long endBBS = System.nanoTime();
                    System.out.println(generatorBBS);
                    generatorBBS.test();
                    executionTimeBBS = endBBS - startBBS;
                    System.out.println("Timpul de executie: " + executionTimeBBS + " nanosec.");
                    break;
                case 2:
                    GeneratorJacobi generatorJacobi = new GeneratorJacobi(16,setup);
                    long startJacobi = System.nanoTime();
                    generatorJacobi.algorithm();
                    long endJacobi = System.nanoTime();
                    System.out.println(generatorJacobi);
                    generatorJacobi.test();
                    executionTimeJacobi = endJacobi - startJacobi;
                    System.out.println("Tmpul de executie: " + executionTimeJacobi + " nanosec.");
                    break;
                case 3:
                    if(executionTimeBBS == 0 && executionTimeJacobi == 0){
                        System.out.println("Calculati intai secventele cu generatorii BBS si Jacobi, pentru a se putea face testele de performanta!");
                        break;
                    }else{
                        int[] seed;
                        int t = 16;
                        int L = 40;
                        int m = 40;
                        int k1 = 21, k2 = 19, k3 = 2;
                        seed = new int[]{0,1,1,0,1,1,1,0,0,1,0,1,0,0,0,0,1,1,0,1,0,1,1,1,0,0,0,1,1,0,0,1,0,0,0,1,1,1,0,0};
                        GeneratorLFSR generatorLFSR = new GeneratorLFSR(t,L,seed);
                        generatorLFSR.setPolynom(m,k1,k2,k3);
                        System.out.println("Seed: ");
                        generatorLFSR.print(seed);
                        System.out.println("Incepem calculul registrilor:");
                        long startLFSR = System.nanoTime();
                        generatorLFSR.computeSequence();
                        long endLFSR = System.nanoTime();
                        System.out.println("Secventa alcatuita in " + t + " unitati de timp, plecand de la seed-ul dat, de " + L + " registri, avand polinomul primitiv x^" + m + "+x^" + k1 + "+x^" + k2 + "+x^" + k3 + " este:");
                        System.out.println(generatorLFSR.getSequence());
                        generatorLFSR.test();
                        executionTimeLFSR = endLFSR - startLFSR;
                        System.out.println("Timpul de executie: " + executionTimeLFSR + " nanosec.");
                        if(executionTimeLFSR < executionTimeBBS){
                            System.out.println("Timpul de executie pentru BBS este " + executionTimeBBS + " nanosec, deci este mai rapid decat generatorul BBS.");
                        }else{
                            System.out.println("Timpul de executie pentru BBS este " + executionTimeBBS + " nanosec, deci este mai incet decat generatorul BBS.");
                        }
                        if(executionTimeLFSR < executionTimeJacobi){
                            System.out.println("Timpul de executie pentru Jacobi este " + executionTimeJacobi + " nanosec, deci este mai rapid decat generatorul Jacobi.");
                        }else{
                            System.out.println("Timpul de executie pentru Jacobi este " + executionTimeJacobi + " nanosec, deci este mai incet decat generatorul Jacobi.");
                        }
                        break;
                    }
                case 4:
                    RCA rcaCrypto = new RCA();
                    int[] key;
                    key = new int[]{1,0,0,1,0,1,0,1,1,1,0,0,1,0,1,0};
                    rcaCrypto.setKey(key);
                    int[] message;
                    message = new int[]{0,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,0,1,0,0,0,1,1,1,0,1};
                    int[] keystream = rcaCrypto.getKeystream(message.length);
                    System.out.println("Mesajul este:");
                    rcaCrypto.print(message);
                    System.out.println("Keystream are lungimea mesajului, " + message.length + ", si este:");
                    rcaCrypto.print(keystream);
                    int[] criptotext = rcaCrypto.encryption(message,keystream);
                    System.out.println("Criptotextul este:");
                    rcaCrypto.print(criptotext);
                    int[] decripted = rcaCrypto.decryption(criptotext,keystream);
                    System.out.println("Mesajul decriptat este:");
                    rcaCrypto.print(decripted);
                    boolean response = rcaCrypto.correctDecryption(decripted,message);
                    if (response == true) {
                        System.out.println("Decriptare corecta!");
                    } else {
                        System.out.println("Decriptare incorecta!");
                    }
                    rcaCrypto.test(keystream);
                    break;
                case 5:
                    GeneratorJacobi jacobi = new GeneratorJacobi();
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Numarul 1:");
                    String numarul1 = scanner1.nextLine();
                    System.out.println("Numarul 2:");
                    String numarul2 = scanner1.nextLine();
                    System.out.println("Rezultatul este: " + jacobi.jacobiSymbol(new BigInteger(numarul1),new BigInteger(numarul2)));
                    break;
                case 6:
                    System.out.println("Multumesc!");
                    ok = 0;
                    break;
                default:
                    System.out.println("Puteti selecta 1, 2, 3, 4, 5 sau 6!");
                    break;
            }
        }
    }

}
