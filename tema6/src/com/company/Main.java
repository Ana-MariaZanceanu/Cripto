package com.company;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        BigInteger plaintext = new BigInteger("89");
        System.out.println("Plaintext is:");
        System.out.println(plaintext);

        RSAKeyScheduling keyScheduling = new RSAKeyScheduling();
        keyScheduling.setLengthForE(1024);
        keyScheduling.generate();

        RSAEncryption encryption = new RSAEncryption(plaintext,keyScheduling.getE(),keyScheduling.getN());
        encryption.setCiphertext();
        BigInteger ciphertext = encryption.getCiphertext();
        System.out.println("1.Ciphertext is:");
        System.out.println(ciphertext);


        RSADecryption decryption = new RSADecryption(ciphertext,keyScheduling.getD(),keyScheduling.getN(),keyScheduling.getP(),keyScheduling.getQ());
        long startTime = System.nanoTime();
        decryption.setPlaintext();
        long endTime = System.nanoTime();
        BigInteger decrypted = decryption.getPlaintext();
        System.out.println("2.Decrypted message is:");
        System.out.println(decrypted);
        if(plaintext.compareTo(decrypted) == 0){
            System.out.println("Correct decryption!");
        }else{
            System.out.println("Incorrect decryption!");
        }
        long decryptionTime = endTime - startTime;
        System.out.println("Decryption was done in " + decryptionTime + " nanos.");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("DECRYPTION WITH CHINESE REMAINDER THEOREM:");
        System.out.println();
        RSAKeyScheduling secondKeyScheduling = new RSAKeyScheduling();
        secondKeyScheduling.setLengthForE(32);
        secondKeyScheduling.generate();

        RSAEncryption secondEncryption = new RSAEncryption(plaintext,secondKeyScheduling.getE(),secondKeyScheduling.getN());
        secondEncryption.setCiphertext();
        BigInteger secondCiphertext = secondEncryption.getCiphertext();
        System.out.println("2.Ciphertext:");
        System.out.println(secondCiphertext);


        RSADecryption secondDecryption = new RSADecryption(secondCiphertext,secondKeyScheduling.getD(),secondKeyScheduling.getN(),secondKeyScheduling.getP(),secondKeyScheduling.getQ());
        startTime = System.nanoTime();
        secondDecryption.setPlaintextWithCRT();
        endTime = System.nanoTime();
        BigInteger secondDecrypted = secondDecryption.getPlaintext();
        System.out.println("2.Decrypted message is:");
        System.out.println(secondDecrypted);
        if(plaintext.compareTo(secondDecrypted) == 0){
            System.out.println("Correct decryption!");
        }else{
            System.out.println("Incorrect decryption!");
        }
        long decryptionTimeCRT = endTime - startTime;
        System.out.println("Decryption was done in " + decryptionTimeCRT + " nanos.");
        if(decryptionTimeCRT < decryptionTime){
            System.out.println("Decryption with CRT is faster than simple decryption.");
        }

        WienerAttack wienerAttack = new WienerAttack();
        wienerAttack.generate();
        System.out.println(wienerAttack.getP());
        System.out.println(wienerAttack.getQ());
        System.out.println(wienerAttack.getN());
        System.out.println(wienerAttack.getE());
        System.out.println(wienerAttack.getD());
        System.out.println(wienerAttack.getPhi());

        RSAEncryption thirdEncryption = new RSAEncryption(plaintext,wienerAttack.getE(),wienerAttack.getN());
        thirdEncryption.setCiphertext();
        BigInteger thirdCiphertext = thirdEncryption.getCiphertext();
        System.out.println("2.Ciphertext:");
        System.out.println(thirdCiphertext);

        RSADecryption thirdDecryption = new RSADecryption(thirdCiphertext,wienerAttack.getD(),wienerAttack.getN(),wienerAttack.getP(),wienerAttack.getQ());
        thirdDecryption.setPlaintext();
        BigInteger thirdDecrypted = thirdDecryption.getPlaintext();
        System.out.println("2.Decrypted message is:");
        System.out.println(thirdDecrypted);
        if(plaintext.compareTo(thirdDecrypted) == 0){
            System.out.println("Correct decryption!");
        }else{
            System.out.println("Incorrect decryption!");
        }
    }
}