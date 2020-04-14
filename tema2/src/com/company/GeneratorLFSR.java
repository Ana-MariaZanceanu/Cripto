package com.company;

public class GeneratorLFSR {
    private int L;
    private int[] seed;
    private int t;
    private String sequence;
    private int m,k1,k2,k3;

    public GeneratorLFSR(){}

    public GeneratorLFSR(int t,int L,int[] seed){
        this.t = t;
        this.L = L;
        this.seed = seed;
    }

    public String getSequence() {
        return sequence;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int[] getSeed() {
        return seed;
    }

    public void setSeed(int[] seed) {
        this.seed = seed;
    }

    public void setPolynom(int m, int k1,int k2,int k3){
        this.m = m;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
    }

    public int primitivePolynom(int[] register){
        return (register[L - m] + register[L - k1] + register[L - k2] + register[L - k3]) % 2;
    }

    public void print(int[] vector){
        for(int i = 0; i < vector.length; i++){
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    public void computeSequence(){
        StringBuilder stringBuilder = new StringBuilder();
        int[] before = new int[L];
        before = seed;
        stringBuilder.append(before[L - 1]);
        for(int i = 1; i < t; i++){
            int[] after = new int[L];
            for(int j = 1; j < L; j++){
                after[j] = before[j - 1];
            }
            after[0] = primitivePolynom(before);
            stringBuilder.append(after[L - 1]);
            print(after);
            before = after;
        }
        sequence = new String(stringBuilder);
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
}
