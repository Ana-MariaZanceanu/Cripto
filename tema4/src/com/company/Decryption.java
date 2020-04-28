package com.company;

public class Decryption {
    private int[] message;
    private int[][] subkeys;
    private int[][] IP;
    private int[][] invIP;
    private int[][] E;
    private int[][] S1;
    private int[][] S2;
    private int[][] S3;
    private int[][] S4;
    private int[][] S5;
    private int[][] S6;
    private int[][] S7;
    private int[][] S8;
    private int[][] P;
    public Decryption(){}
    public Decryption(int[] message,int[][] subkeys,int[][] IP,int[][] invIP,int[][] E,int[][] S1,int[][] S2,int[][] S3,int[][] S4,int[][] S5,int[][] S6,int[][] S7,int[][] S8,int[][] P){
        this.message = message;
        this.subkeys = subkeys;
        this.IP = IP;
        this.invIP = invIP;
        this.E = E;
        this.S1 = S1;
        this.S2 = S2;
        this.S3 = S3;
        this.S4 = S4;
        this.S5 = S5;
        this.S6 = S6;
        this.S7 = S7;
        this.S8 = S8;
        this.P = P;
    }

    public int[] getMessage() {
        return message;
    }

    public void setMessage(int[] message) {
        this.message = message;
    }

    public int[][] getSubkeys() {
        return subkeys;
    }

    public void setSubkeys(int[][] subkeys) {
        this.subkeys = subkeys;
    }

    public int[][] getIP() {
        return IP;
    }

    public void setIP(int[][] IP) {
        this.IP = IP;
    }

    public int[][] getInvIP() {
        return invIP;
    }

    public void setInvIP(int[][] invIP) {
        this.invIP = invIP;
    }

    public int[][] getE() {
        return E;
    }

    public void setE(int[][] e) {
        E = e;
    }

    public int[][] getS1() {
        return S1;
    }

    public void setS1(int[][] s1) {
        S1 = s1;
    }

    public int[][] getS2() {
        return S2;
    }

    public void setS2(int[][] s2) {
        S2 = s2;
    }

    public int[][] getS3() {
        return S3;
    }

    public void setS3(int[][] s3) {
        S3 = s3;
    }

    public int[][] getS4() {
        return S4;
    }

    public void setS4(int[][] s4) {
        S4 = s4;
    }

    public int[][] getS5() {
        return S5;
    }

    public void setS5(int[][] s5) {
        S5 = s5;
    }

    public int[][] getS6() {
        return S6;
    }

    public void setS6(int[][] s6) {
        S6 = s6;
    }

    public int[][] getS7() {
        return S7;
    }

    public void setS7(int[][] s7) {
        S7 = s7;
    }

    public int[][] getS8() {
        return S8;
    }

    public void setS8(int[][] s8) {
        S8 = s8;
    }

    public int[][] getP() {
        return P;
    }

    public void setP(int[][] p) {
        this.P = p;
    }

    public void print(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    public int[] permute(int nrBits,int[][] IP, int[] message){
        int[] permuted = new int[nrBits];
        int i = 0;
        for(int r = 0; r < IP.length; r++){
            for(int c = 0; c < IP[0].length; c++){
                permuted[i] = message[IP[r][c] - 1];
                i++;
            }
        }
        return permuted;
    }
    public int[] divideArray(int[] array,int start,int end){
        int[] divided = new int[32];
        int j = 0;
        for(int i = start; i < end; i++){
            divided[j] = array[i];
            j++;
        }
        return divided;
    }
    public int[] xor(int[] array1,int[] array2){
        int[] result = new int[array1.length];
        for(int i = 0; i < array1.length; i++){
            result[i] = (array1[i] + array2[i]) % 2;
        }
        return result;
    }
    public int binarToDec(int[] binar){
        int nr = 0;
        int power = 0;
        for(int i = binar.length - 1; i >= 0; i--){
            nr = nr + (int)Math.pow(2,power) * binar[i];
            power++;
        }
        return nr;
    }
    public int[] decToBinar(int nr){
        int[] result = new int[4];
        int i = 0;
        while(nr != 0){
            result[i] = nr % 2;
            nr = nr / 2;
            i++;
        }
        while(i != 4){
            result[i] = 0;
            i++;
        }
        int[] finalResult = new int[4];
        int k = 3;
        for(int j = 0; j < 4; j++){
            finalResult[j] = result[k];
            k--;
        }
        return finalResult;
    }
    public int[] sbox(int[][] S,int[] B){
        int[] output;
        int[] forI = new int[2];
        int[] forJ = new int[4];
        forI[0] = B[0]; forI[1] = B[5];
        forJ[0] = B[1]; forJ[1] = B[2]; forJ[2] = B[3]; forJ[3] = B[4];
        int i = binarToDec(forI);
        int j = binarToDec(forJ);
        int nrFromS = S[i][j];
        output = decToBinar(nrFromS);
        return output;
    }
    public int[] f(int[] Rn,int[] Kn){
        int[] RnExp = permute(48,this.E,Rn);
        int[] result = xor(RnExp,Kn);
        int j = 0;
        int[] resultF = new int[32];
        int m = 0;
        for(int i = 0; i < 8; i++){
            int[] B = divideArray(result,j,j+6);
            j += 6;
            int[] resultSbox = new int[4];
            switch (i){
                case 0:
                    resultSbox = sbox(S1,B);
                    break;
                case 1:
                    resultSbox = sbox(S2,B);
                    break;
                case 2:
                    resultSbox = sbox(S3,B);
                    break;
                case 3:
                    resultSbox = sbox(S4,B);
                    break;
                case 4:
                    resultSbox = sbox(S5,B);
                    break;
                case 5:
                    resultSbox = sbox(S6,B);
                    break;
                case 6:
                    resultSbox = sbox(S7,B);
                    break;
                case 7:
                    resultSbox = sbox(S8,B);
                    break;
            }
            for(int l = 0; l < 4; l++){
                resultF[m] = resultSbox[l];
                m++;
            }
        }
        int[] finalResultF = permute(32,this.P,resultF);
        return finalResultF;
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
    public int[] decrypt(){
        int[] permuteMessage = permute(64,this.IP,this.message);
        // print(permuteMessage);
        int[] L0 = divideArray(permuteMessage,0,32);
        int[] R0 = divideArray(permuteMessage,32,64);
        for(int i = 15; i >=0; i--){
            int[] L1 = R0;
            int[] R1 = xor(L0,f(R0,this.subkeys[i]));
//            print(L1);
//            print(R1);
            L0 = L1;
            R0 = R1;
        }
//        System.out.println("L16:");
//        print(L0);
//        System.out.println("R16:");
//        print(R0);
        int[] Rev = concatenateArrays(R0,L0);
        int[] decrypted = permute(64,invIP,Rev);
        return decrypted;
    }
}
