package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MITM {
    private int[] plaintext1;
    private int[] ciphertext1;
    private int[] plaintext2;
    private int[] ciphertext2;
    private int[][] generatedKeys = new int[256][64];

    public MITM(int[] plaintext1,int[] ciphertext1,int[] plaintext2,int[] ciphertext2){
        this.plaintext1 = plaintext1;
        this.ciphertext1 = ciphertext1;
        this.plaintext2 = plaintext2;
        this.ciphertext2 = ciphertext2;
    }

    public int[][] getGeneratedKeys() {
        return generatedKeys;
    }

    public int[] stringToIntArray(String raw){
        int[] num = new int[raw.length()];

        for (int i = 0; i < raw.length(); i++){
            num[i] = raw.charAt(i) - '0';
        }

        return num;
    }

    public int[] addingZeros(int[] array,int length){
        int[] result = new int[length];
        int nrZeros = length - array.length;
        int i;
        for(i = 0; i < nrZeros; i++){
            result[i] = 0;
        }
        for(int j = 0; j < array.length; j++){
            result[i] = array[j];
            i++;
        }
        return result;
    }

    public void print(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void generateKeys(){
        for(long i = 0; i < 256; i++){
            BigInteger bigInteger = (BigInteger.valueOf(i)).pow(8);
            String s = bigInteger.toString(2);
            int[] bits = stringToIntArray(s);
            generatedKeys[(int)i] = addingZeros(bits,64);
        }
//        for(int j = 0; j < 256; j++)
//            print(generatedKeys[j]);
    }

    public int[] addingNumberToArray(int[] array,int number){
        int[] newArray = new int[array.length + 1];
        int i;
        for(i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        newArray[i] = number;
        return newArray;
    }

    public int[] removeLastNumberFromArray(int[] array){
        int[] newArray = new int[array.length - 1];
        for(int i = 0; i < array.length - 1; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }

    public boolean equalsArrays(int[] array1,int[] array2){
        for(int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i]){
                return false;
            }
        }
        return true;
    }

    public List<Integer> candidateKeys(int[][] encrypted,int[][] decrypted){
        List<Integer> pairResult = new ArrayList<>();
        for(int j = 0; j < 256; j++){
            for(int i = 0; i < 256; i++){
                if(equalsArrays(removeLastNumberFromArray(encrypted[i]),removeLastNumberFromArray(decrypted[j]))){
                    pairResult.add(new Integer(encrypted[i][64]));
                    pairResult.add(new Integer(decrypted[j][64]));
                }
            }
        }
        return pairResult;
    }

    public List<Integer> firstPairKeys(){
        DES des = new DES();
        int[][] encryptedWithKeyInd = new int[256][65];
        for(int i = 0; i < 256; i++){
            des.setPlaintext(plaintext1);
            des.setKey(generatedKeys[i]);
            encryptedWithKeyInd[i] = addingNumberToArray(des.encrypt(),i);
            //print(encryptedWithKeyInd[i]);
        }

        int[][] decryptedWithKeyInd = new int[256][65];
        for(int i = 0; i < 256; i++){
            des.setCiphertext(ciphertext1);
            des.setKey(generatedKeys[i]);
            decryptedWithKeyInd[i] = addingNumberToArray(des.decrypt(),i);
            //print(decryptedWithKeyInd[i]);
        }

        //System.out.println(candidateKeys(encryptedWithKeyInd,decryptedWithKeyInd));
        return candidateKeys(encryptedWithKeyInd,decryptedWithKeyInd);
    }

    public List<Integer> secondPairKeys(List<Integer> candidateKeysFromFirstPair){
        DES des = new DES();
        List<Integer> candidateKeysFromSecondPair = new ArrayList<>();

        int[] set = new int[candidateKeysFromFirstPair.size()];
        int j = 0;
        for(Integer i : candidateKeysFromFirstPair){
            set[j] = i.intValue();
            j++;
        }

        for(int i = 0; i < set.length; i+= 2){
            des.setPlaintext(plaintext2);
            des.setKey(generatedKeys[set[i]]);
            int[] encryptedText = des.encrypt();
            des.setCiphertext(ciphertext2);
            des.setKey(generatedKeys[set[i + 1]]);
            int[] decryptedText = des.decrypt();
            if(equalsArrays(encryptedText,decryptedText)){
                candidateKeysFromSecondPair.add(new Integer(set[i]));
                candidateKeysFromSecondPair.add(new Integer(set[i + 1]));
            }
        }

        return candidateKeysFromSecondPair;
    }

    public int[] findKeysFromHistory(){
        this.generateKeys();
        List<Integer> secondPairKeys = this.secondPairKeys(this.firstPairKeys());
        int[] keysSet = new int[secondPairKeys.size()];

        int j = 0;
        for(Integer i : secondPairKeys){
            keysSet[j] = i.intValue();
            j++;
        }

        return keysSet;
    }
}
