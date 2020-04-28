package com.company;

public class Main {

    public static void main(String[] args) {
        //DES - encryption and decryption
//        int[] key = {0,0,0,1,0,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,0,1};
//        int[] plaintext = {0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,1,0,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,0,1,1,1,1};
//        DES des = new DES(plaintext,key);
//        int[] encrypted = des.encrypt();
//        System.out.println("Plaintext:");
//        des.print(plaintext);
//        System.out.println("Encrypted text:");
//        des.print(encrypted);
//        int[] decrypted = des.decrypt();
//        System.out.println("Decrypted text:");
//        des.print(decrypted);
//        des.correctDecryption(decrypted,plaintext);


//        int[] key1 = {0,0,0,1,0,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,0,1};
//        int[] key2 = {1,0,0,0,1,1,1,1,1,0,0,0,0,1,0,1,0,0,0,0,0,1,1,0,1,1,0,1,1,0,0,0,1,0,0,1,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,1,0,1,0,1,0,1,0,0,1,0,1};

        int[] key1 = {1,0,1,1,0,1,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,1,0,0,1,0,1,1,0,0,1,1,1,0,1,1,1,1,1,0,0,1,1,1,0,1,0,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1};
        int[] key2 = {0,0,0,1,1,1,1,1,0,1,1,1,1,0,1,1,0,0,1,0,1,0,1,0,0,0,0,1,1,0,0,0,1,1,1,1,0,0,1,0,1,0,0,1,1,0,1,0,1,1,0,0,0,0,1,1,1,1,1,0,0,0,0,1};

        int[] plaintext1 = {0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,1,0,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,0,1,1,1,1};
        int[] plaintext2 = {1,0,0,0,1,1,0,0,0,1,1,1,1,0,0,0,1,1,1,0,1,0,0,0,0,0,1,1,0,1,1,0,1,0,1,0,1,0,1,1,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,0,1,1,0,1,0,0,0};
        DES des11 = new DES(plaintext1,key1);
        DES des12 = new DES(des11.encrypt(),key2);
        int[] ciphertext1 = des12.encrypt();
        //des12.print(ciphertext1);

        DES des21 = new DES(plaintext2,key1);
        DES des22 = new DES(des21.encrypt(),key2);
        int[] ciphertext2 = des22.encrypt();
        //des22.print(ciphertext2);

        MITM mitm = new MITM(plaintext1,ciphertext1,plaintext2,ciphertext2);
        int[][] keysGenerated = mitm.getGeneratedKeys();
        int[] keysFound = mitm.findKeysFromHistory();
        int ok = 0;
        for(int i = 0; i < keysFound.length; i += 2){
            if(mitm.equalsArrays(keysGenerated[keysFound[i]],key1) && mitm.equalsArrays(keysGenerated[keysFound[i + 1]],key2)){
                System.out.println("First key was found!");
                mitm.print(keysGenerated[keysFound[i]]);
                System.out.println("Second key was found!");
                mitm.print(keysGenerated[keysFound[i + 1]]);
                ok = 1;
            }
        }
        if(ok == 0){
            System.out.println("No key was found!");
        }
    }
}
