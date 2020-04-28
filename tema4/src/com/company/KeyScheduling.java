package com.company;

public class KeyScheduling {
    private int[] K;
    private int[][] PC1;
    private int[] LSIterations;
    private int[][] subkeys = new int[16][48];
    private int[][] PC2;
    public KeyScheduling(){}
    public KeyScheduling(int[] K,int[][] PC1,int[] LSIterations,int[][] PC2){
        this.K = K;
        this.PC1 = PC1;
        this.LSIterations = LSIterations;
        this.PC2 = PC2;
    }

    public int[] getK() {
        return K;
    }

    public void setK(int[] K) {
        this.K = K;
    }

    public int[][] getPC1() {
        return PC1;
    }

    public void setPC1(int[][] PC1) {
        this.PC1 = PC1;
    }

    public int[] getLSIterations() {
        return LSIterations;
    }

    public void setLSIterations(int[] LSIterations) {
        this.LSIterations = LSIterations;
    }

    public int[][] getPC2() {
        return PC2;
    }

    public void setPC2(int[][] PC2) {
        this.PC2 = PC2;
    }

    public int[][] getSubkeys() {
        return subkeys;
    }

    public void print(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public int[] permutedKey(int nrBits,int[][] pc1, int[] key){
        int[] finalKey = new int[nrBits];
        int i = 0;
        for(int r = 0; r < pc1.length; r++){
            for(int c = 0; c < pc1[0].length; c++){
                finalKey[i] = key[pc1[r][c] - 1];
                i++;
            }
        }
        return finalKey;
    }
    public int[] divideArray(int[] array,int start,int end){
        int[] divided = new int[28];
        int j = 0;
        for(int i = start; i < end; i++){
            divided[j] = array[i];
            j++;
        }
        return divided;
    }
    public int[] shifts(int[] array,int nrShifts){
        for(int i = 0; i < nrShifts; i++){
            array = shiftIteration(array);
        }
        return array;
    }
    public int[] shiftIteration(int[] array){
        int[] shifted = new int[array.length];
        int j = 0;
        for(int i = 1; i < array.length; i++){
            shifted[j] = array[i];
            j++;
        }
        shifted[j] = array[0];
        return shifted;
    }
    public int[] concatenateArrays(int[] array1,int[] array2){
        int[] concatenated = new int[array1.length + array2.length];
        int j = 0;
        for(int i = 0; i < array1.length; i++){
            concatenated[j] = array1[i];
            j++;
        }
        for(int m = 0; m < array2.length; m++){
            concatenated[j] = array2[m];
            j++;
        }
        return concatenated;
    }
    public void setSubkeys(){
        int[] keyPerm = permutedKey(56,this.PC1,this.K);
        int[] C0 = divideArray(keyPerm,0,28);
        int[] D0 = divideArray(keyPerm,28,56);
        //K1,...,K16 inainte de PC2
        int[][] Kn = new int[16][56];
        int j = 0;
        for(int i = 0; i < 16; i++){
            int[] C1 = shifts(C0,this.LSIterations[i]);
            int[] D1 = shifts(D0,this.LSIterations[i]);
            Kn[j] = concatenateArrays(C1,D1);
            j++;
            C0 = C1;
            D0 = D1;
        }
        //setam K1,...,K16 dupa PC2
        for(int r = 0; r < this.subkeys.length; r++){
            this.subkeys[r] = permutedKey(48,this.PC2,Kn[r]);
        }
    }
}
