package com.company;

import java.math.BigInteger;

public class SHA1 {

    private final String[] H = {"67452301", "EFCDAB89", "98BADCFE", "10325476", "C3D2E1F0"};
    private String message;
    private int ml;
    private int[][] hBits = new int[5][32];
    private int[] messageBits;
    private int[] mlBits;
    private int[][] W = new int[80][32];
    private int[][] ABCDE = new int[5][32];

    public SHA1(){}

    public int[] charToIntArray(String raw){
        int[] num = new int[raw.length()];

        for (int i = 0; i < raw.length(); i++){
            num[i] = raw.charAt(i) - '0';
        }

        return num;
    }

    public String intArrayToString(int[] array){
        String s = "";
        for(int i = 0; i < array.length; i++){
            s = s + (char)(array[i] + '0');
        }
        return s;
    }

    public String concatWithZeros(int length,String raw){
        String s = "";
        int nrZeros = length - raw.length();
        int i = 0;
        while(i < nrZeros){
            s = s + "0";
            i++;
        }
        s = s + raw;
        return s;
    }

    public void print(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
        }
        System.out.println();
    }

    /**
     * Resursa: https://www.geeksforgeeks.org/convert-string-binary-sequence/
     * @param s sirul de convertit
     * @return sirul in binar
     */
    public String strToBinary(String s)
    {
        int n = s.length();
        String str = "";

        for (int i = 0; i < n; i++)
        {
            int val = Integer.valueOf(s.charAt(i));

            String bin = "";
            while (val > 0)
            {
                if (val % 2 == 1)
                {
                    bin += '1';
                }
                else
                    bin += '0';
                val /= 2;
            }
            bin = concatWithZeros(8,reverse(bin));

            str = str + bin;
        }
        return str;
    }

    public String reverse(String input)
    {
        char[] a = input.toCharArray();
        int l, r = 0;
        r = a.length - 1;

        for (l = 0; l < r; l++, r--)
        {
            char temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }
        return String.valueOf(a);
    }

    public SHA1(String message){
        this.message = message;
        this.ml = message.length();
        for(int i = 0; i < H.length; i++){
            BigInteger bi = new BigInteger(H[i],16);
            hBits[i] = charToIntArray(concatWithZeros(32,bi.toString(2)));
        }
        this.messageBits = charToIntArray(strToBinary(this.message));
        this.mlBits = charToIntArray(concatWithZeros(64,Integer.toBinaryString(this.messageBits.length)));
    }

    public int[] concatArrays(int[] array1,int[] array2){
        int[] result = new int[array1.length + array2.length];
        int ind = 0;
        for(int i = 0; i < array1.length; i++){
            result[ind] = array1[i];
            ind++;
        }
        for(int j = 0; j < array2.length; j++){
            result[ind] = array2[j];
            ind++;
        }
        return result;
    }

    public int[] divideArray(int[] array,int start,int end){
        int[] divided = new int[end - start];
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

    public int[] leftShift(int[] array){
        int[] shifted = new int[array.length];
        int j = 0;
        for(int i = 1; i < array.length; i++){
            shifted[j] = array[i];
            j++;
        }
        shifted[j] = array[0];
        return shifted;
    }

    public int[] leftShifts(int[] array,int nrShifts){
        for(int i = 0; i < nrShifts; i++){
            array = leftShift(array);
        }
        return array;
    }

    public int[] preProcessing(){
        int nr = this.messageBits.length + 1;
        String paddingStr = "1";
        while(nr % 512 != 448){
            paddingStr = paddingStr + "0";
            nr++;
        }
        int[] padding = charToIntArray(paddingStr);
        int[] content = concatArrays(this.messageBits,padding);
        int[] allContentBits = concatArrays(content,this.mlBits);
        return allContentBits;
    }

    public void extendW(){
        for(int i = 16; i < 80; i++){
            W[i] = leftShifts(xor(W[i - 3],xor(W[i - 8],xor(W[i - 14],W[i - 16]))),1);
        }
    }

    public int[] and(int[] array1,int[] array2){
        int[] result = new int[array1.length];
        for(int i = 0; i < array1.length; i++){
            if(array1[i] == 1 && array2[i] == 1){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }

    public int[] or(int[] array1,int[] array2){
        int[] result = new int[array1.length];
        for(int i = 0; i < array1.length; i++){
            if(array1[i] == 1 || array2[i] == 1){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }

    public int[] not(int[] array1){
        int[] result = new int[array1.length];
        for(int i = 0; i < array1.length; i++){
            if(array1[i] == 1){
                result[i] = 0;
            }else{
                result[i] = 1;
            }
        }
        return result;
    }

    public BigInteger[] mainLoop(BigInteger[] initialize){
        int[] A = charToIntArray(concatWithZeros(32,initialize[0].toString(2)));
        int[] B = charToIntArray(concatWithZeros(32,initialize[1].toString(2)));
        int[] C = charToIntArray(concatWithZeros(32,initialize[2].toString(2)));
        int[] D = charToIntArray(concatWithZeros(32,initialize[3].toString(2)));
        int[] E = charToIntArray(concatWithZeros(32,initialize[4].toString(2)));
        int[] F = new int[32];
        int[] K = new int[32];
        BigInteger mod32 = new BigInteger("4294967296");
        for(int j = 0; j < 80; j++){
            if(j >= 0 && j <= 19){
                F = or(and(B,C),and(not(B),D));
                BigInteger bi = new BigInteger("5A827999",16);
                K = charToIntArray(concatWithZeros(32,bi.toString(2)));
            }else{
                if(j >= 20 && j <= 39){
                    F = xor(B,xor(C,D));
                    BigInteger bi = new BigInteger("6ED9EBA1",16);
                    K = charToIntArray(concatWithZeros(32,bi.toString(2)));
                }else{
                    if(j >= 40 && j <= 59){
                        F = or(and(B,C),or(and(B,D),and(C,D)));
                        BigInteger bi = new BigInteger("8F1BBCDC",16);
                        K = charToIntArray(concatWithZeros(32,bi.toString(2)));
                    }else{
                        if(j >= 60 && j <= 79){
                            F = xor(B,xor(C,D));
                            BigInteger bi = new BigInteger("CA62C1D6",16);
                            K = charToIntArray(concatWithZeros(32,bi.toString(2)));
                        }
                    }
                }
            }

            BigInteger aShiftDec = new BigInteger(intArrayToString(leftShifts(A,5)),2);
            BigInteger fDec = new BigInteger(intArrayToString(F),2);
            BigInteger eDec = new BigInteger(intArrayToString(E),2);
            BigInteger kDec = new BigInteger(intArrayToString(K),2);
            BigInteger wjDec = new BigInteger(intArrayToString(W[j]),2);

            BigInteger sum = aShiftDec.add(fDec).add(eDec).add(kDec).add(wjDec);
            sum = sum.mod(mod32);

            int[] temp = charToIntArray(concatWithZeros(32,sum.toString(2)));
            E = D;
            D = C;
            C = leftShifts(B,30);
            B = A;
            A = temp;
        }

        BigInteger aDec = new BigInteger(intArrayToString(A),2);
        BigInteger bDec = new BigInteger(intArrayToString(B),2);
        BigInteger cDec = new BigInteger(intArrayToString(C),2);
        BigInteger dDec = new BigInteger(intArrayToString(D),2);
        BigInteger eDec = new BigInteger(intArrayToString(E),2);

        BigInteger[] hh = new BigInteger[5];
        hh[0] = aDec;
        hh[1] = bDec;
        hh[2] = cDec;
        hh[3] = dDec;
        hh[4] = eDec;
        return hh;
    }

    public String processing(){
        int[] messageContent = this.preProcessing();
        int i = 0;
        BigInteger mod32 = new BigInteger("4294967296");
        BigInteger[] mainLoopResult = new BigInteger[5];
        for(int k = 0; k < 5; k++){
            mainLoopResult[k] = new BigInteger(intArrayToString(this.hBits[k]),2);
        }
        while(i != messageContent.length){
            int[] blockContent = divideArray(messageContent,i,i + 512);
            int div = 0;
            for(int j = 0; j < 16; j++){
                W[j] = divideArray(blockContent,div,div + 32);
                div += 32;
            }
            extendW();
            BigInteger[] result = mainLoop(mainLoopResult);
            for(int k = 0; k < 5; k++){
                mainLoopResult[k] = mainLoopResult[k].add(result[k]).mod(mod32);
            }
            i += 512;
        }
        String messageDigest = "";
        for(int k = 0; k < 5; k++){
            messageDigest = messageDigest + concatWithZeros(8,mainLoopResult[k].toString(16));
        }
        return messageDigest;
    }

    public String resume(String messageDigest){
        String s = messageDigest.substring(0,8);
        BigInteger bigInteger = new BigInteger(s,16);
        return concatWithZeros(32,bigInteger.toString(2));
    }

    public int distHamming(String resume1,String resume2){
        char[] charsResume1 = resume1.toCharArray();
        char[] charsResume2 = resume2.toCharArray();
        int distance = 0;
        for(int i = 0; i < charsResume1.length; i++){
            if(charsResume1[i] != charsResume2[i]){
                distance++;
            }
        }
        return distance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.ml = message.length();
        for(int i = 0; i < H.length; i++){
            BigInteger bi = new BigInteger(H[i],16);
            hBits[i] = charToIntArray(concatWithZeros(32,bi.toString(2)));
        }
        this.messageBits = charToIntArray(strToBinary(this.message));
        this.mlBits = charToIntArray(concatWithZeros(64,Integer.toBinaryString(this.messageBits.length)));
    }
}