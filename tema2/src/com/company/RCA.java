package com.company;

public class RCA {
    private int[] key;
    public RCA(){}

    public void setKey(int[] key) {
        this.key = key;
    }

    public int[] getKey() {
        return key;
    }

    public int[] initState(int[] K){
        int[] S = new int[256];
        int[] st0 = new int[258];
        int i;
        int temp;
        int l = K.length;
        for(i = 0; i < 256; i++){
            S[i] = i;
        }
        int j = 0;
        for(i = 0; i < 256; i++){
            j = (j + S[i] + K[i % l]) % 256;
            temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
        i = 0; j = 0;
        System.out.println("i: " + i + ", j: " + j);
        st0[0] = i; st0[1] = j;//st contine i,j apoi permutarea din S
        for(int k = 2; k < 258; k++){
            st0[k] = S[k - 2];
        }
        return st0;
    }

    public int[] transition(int[] state){
        int keystreamByte;
        int[] s = copy(state,2);
        int[] st = new int[259];
        int i = (state[0] + 1) % 256;
        int j = (state[1] + s[i]) % 256;
        System.out.println("i: " + i + ", j: " + j);
        int temp;
        temp = s[i];
        s[i] = s[j];
        s[j] = temp;

        keystreamByte = s[(s[i] + s[j]) % 256] % 2;
        st[0] = keystreamByte; st[1] = i; st[2] = j;
        for(int k = 3; k < 259; k++){
            st[k] = s[k - 3];
        }
        System.out.println("Next state:");
        print(s);
        return st;
    }

    public void print(int[] vector){
        for(int i = 0; i < vector.length; i++){
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    public int[] copy(int[] source,int start){
        int[] dest = new int[source.length - start];
        int j = 0;
        for(int i = start; i < source.length; i++){
            dest[j] = source[i];
            j++;
        }
        return dest;
    }

    public int[] getKeystream(int n){
        int[] initialState = initState(key);
        int[] keystream = new int[n];
        System.out.println("Initial state:");
        print(copy(initialState,2));
        int[] state = transition(initialState);
        keystream[0] = state[0];
        int[] currentState = copy(state,1);
        for(int i = 1; i < n; i++){
            int[] nextState = transition(currentState);
            keystream[i] = nextState[0];
            currentState = copy(nextState,1);
        }
        return keystream;
    }

    public int[] encryption(int[] message,int[] keystream){
        int n = message.length;
        int[] ciphertext = new int[n];

        for(int i = 0; i < n; i++){
            ciphertext[i] = (message[i] + keystream[i]) % 2;
        }

        return ciphertext;
    }

    public int[] decryption(int[] ciphertext,int[] keystream){
        int n = ciphertext.length;
        int[] message = new int[n];

        for(int i = 0; i < n; i++){
            message[i] = (ciphertext[i] + keystream[i]) % 2;
        }

        return message;
    }

    public boolean correctDecryption(int[] decripted,int[] message){
        if(decripted.length != message.length)
            return false;
        for(int i = 0; i < decripted.length; i++)
        {
            if(decripted[i] != message[i])
                return false;
        }
        return true;
    }

    public void test(int[] keystream){
        int nr0 = 0, nr1 = 0;
        for(int i = 0; i < keystream.length; i++){
            if(keystream[i] == 0){
                nr0++;
            }else{
                nr1++;
            }
        }
        System.out.println("0 apare de " + nr0 + " ori deci cu o frecventa de " + (double)nr0 / (double)keystream.length + ", iar 1 apare de " + nr1 + " ori deci cu o frecventa de " + (double)nr1 / (double)keystream.length);
    }
}
