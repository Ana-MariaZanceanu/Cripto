package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Metode instanta = new Metode();
        Scanner in = new Scanner(System.in);

        String plainText = new String();
        System.out.println("Introduceti textul de criptat: ");
        plainText = in.nextLine();
//        plainText = "RSA is a public-key encryption algorithm and the standard for encrypting data sent over the internet. It also happens to be one of the methods used in our PGP and GPG programs. Unlike Triple DES, RSA is considered an asymmetric algorithm due to its use of a pair of keys. You’ve got your public key, which is what we use to encrypt our message, and a private key to decrypt it. The result of RSA encryption is a huge batch of mumbo jumbo that takes attackers quite a bit of time and processing power to break.";

        System.out.println("Introduceti cheia: ");
        String cheieIn = new String();
        cheieIn = in.nextLine();
        cheieIn = instanta.filtrarePlaintext(cheieIn);
        char[] key = cheieIn.toCharArray();

        System.out.println("Textul original este:" + "\n" + plainText);



        String filtredText = new String(instanta.filtrarePlaintext(plainText));
        System.out.println("Textul filtrat este:" + "\n" + filtredText);


//        //char[] key = {'a','b','a','b','b'};
//        char[] key = {'b','a','c','b','d','z','a','a','d','c'};
//        //char[] key = {'b','a','c'};
        int[] keyNr = new int[key.length];
        int i = 0;
        for(char c : key)
        {
            keyNr[i] = c - 'a';
            i++;
        }
        System.out.println("Cheia este:");
        for (char c : key)
            System.out.print(c + " ");
        System.out.println();
        for(i = 0; i < keyNr.length; i++)
            System.out.print(keyNr[i] + " ");
        System.out.println();
        String cryptotext = new String(instanta.criptarePlaintext(filtredText,keyNr));
        System.out.println("Criptotextul este:");
        System.out.println(cryptotext);
        System.out.println("Textul decriptat este:");
        String decryption = new String(instanta.decriptareCriptotext(cryptotext,keyNr));
        System.out.println(decryption);
        boolean verifDecryption = instanta.verificare(filtredText,decryption);
        if(verifDecryption)
            System.out.println("Decriptare corecta!");
        else
            System.out.println("Decriptare gresita!");


        int l = 1;
        int lungimeCheie = 1;
        double minDevStd = 0.5;
        double devStdFinal;
        int ok = 0;
        while(ok == 0)
        {
            devStdFinal = instanta.indexCoincidentaAbatereStd(cryptotext,l);
            if(devStdFinal < minDevStd)
            {
                minDevStd = devStdFinal;
                lungimeCheie = l;

            }
            if(l - lungimeCheie > 2 * lungimeCheie)
                ok = 1;
            l++;
        }
        //System.out.println("Abaterea standard: " + minDevStd);
        System.out.println("Lungimea cheii este: " + lungimeCheie);
        int lk = 0;
        int ki;
        char[] findKey = new char[lungimeCheie];
        while(lk < lungimeCheie)
        {
            ki = instanta.identificareCheie(cryptotext,lungimeCheie,lk);
            findKey[lk] = (char) (ki + 97);
            //System.out.print(ki + " ");
            lk++;
        }
        System.out.println("Cheia va fi:");
        for(char c : findKey)
            System.out.print(c + " ");
    }
}
