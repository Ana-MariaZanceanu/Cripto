package com.company;
import java.lang.Math;
public class Metode {
    public double[] procente = new double[]{0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};

    public String filtrarePlaintext(String plaintext)
    {
        char[] chars = plaintext.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : chars)
        {
            if(c >= 'a' && c <= 'z')
                stringBuilder.append(c);
            if(c >= 'A' && c <= 'Z')
                stringBuilder.append((char)(c + 'a' - 'A'));
        }
        String filtred = new String(stringBuilder);
        return filtred;
    }
    public String criptarePlaintext(String plaintext, int[] cheia)
    {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = plaintext.toCharArray();
        int i = 0;
        for(char c : chars)
        {
            if(i == cheia.length)
                i=0;
            stringBuilder.append((char)(97 + (c + cheia[i] - 'a') % 26));
            i++;
        }
        String textEncrypted = new String(stringBuilder);
        return textEncrypted;
    }
    public int deShift(char c,int k)
    {
        if(c - k < 97)
            return c - k +26;
        return c - k;
    }
    public String decriptareCriptotext(String criptotext, int[] cheia)
    {
        StringBuilder stringBuilder = new StringBuilder();
        char chars[] = criptotext.toCharArray();
        int i = 0;
        for(char c : chars)
        {
            if(i == cheia.length)
                i=0;
            stringBuilder.append((char)(deShift(c,cheia[i])));
            i++;
        }
        String textDecrypted = new String(stringBuilder);
        return textDecrypted;
    }
    public boolean verificare(String str1, String str2)
    {
        return str1.equals(str2);
    }
    public int[] frecventaLitere(String text)
    {
        char[] chars = text.toCharArray();
        int[] f = new int[26];
        for(char c : chars)
        {
            f[c - 'a']++;
        }
        return f;
    }
    public void afisareFrecventa(String text)
    {
        int frecventa[] = frecventaLitere(text);
        for(int i = 0; i < frecventa.length; i++)
            System.out.println((char)(i + 97) + " " + frecventa[i]);
    }
    public double indexCoincidenta(int[] frecventa,int lungime)
    {
        double index = 0.0;
        double arg1;
        double arg2;

        for(int i = 0; i < 26; i++)
        {
            arg1 = (double)frecventa[i] / (double)lungime;
            // System.out.println("arg1 la iteratia " + i + " " + arg1);
            arg2 = ((double)frecventa[i] - 1) / ((double)lungime - 1);
            // System.out.println("arg2 la iteratia " + i + " " + arg2);
            index = index + arg1 * arg2;
            // System.out.println("index la iteratia " + i + " " + index);
        }
        //System.out.println(index);
        return index;
    }
    public String substringFromText(String text,int indexStart,int index)
    {
        char[] chars = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = indexStart; i < chars.length; i = i + index)
        {
            stringBuilder.append(chars[i]);
        }
        String substr = new String(stringBuilder);
        return substr;
    }
    public double indexCoincidentaAbatereStd(String criptotext,int m)
    {
        double index;
        int pozitie = 0;
        String subStr = new String();
        //double suma = 0.0;
        double[] valori = new double[m];
        int i = 0;
        while (pozitie < m) {
            subStr = substringFromText(criptotext, pozitie, m);
            index = indexCoincidenta(frecventaLitere(subStr), subStr.length());
            //System.out.println("index " + index);
            //suma += index;
            valori[i] = index;
            i++;
            pozitie++;
        }
        double devStd = 0.0;
        for (i = 0; i < valori.length; i++) {
            devStd += (valori[i] - 0.065) * (valori[i] - 0.065);
        }
        devStd = devStd / (valori.length - 1);
        devStd = Math.sqrt(devStd);
        //System.out.println("abatere " + devStd);
        return devStd;
    }
    public double indexMutualCoincidenta(int[] frecventa,int lungime)
    {
        double index = 0.0;
        double arg;
        for(int i = 0; i < 26; i++)
        {
            arg = procente[i] * (double) frecventa[i] / (double)lungime;
            index = index +  arg;
        }
        return index;
    }
    public String deshiftString(String text,int ds)
    {
        char[] chars = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char c : chars)
        {
            stringBuilder.append((char)deShift(c,ds));
        }
        String result = new String(stringBuilder);
        return result;
    }
    public int identificareCheie(String text,int lungCheie,int ind)
    {
        double index = 0.0;
        int l = 0;
        double abatereStd = 0.0;
        double minAbatereStd = 0.5;
        String subStr = new String();
        String subStrDeshift = new String();
        subStr = substringFromText(text,ind,lungCheie);
        int ok = 0;
        int k = 0;
        while(ok == 0)
        {
            subStrDeshift = deshiftString(subStr,l);
            index = indexMutualCoincidenta(frecventaLitere(subStrDeshift),subStrDeshift.length());
            abatereStd = (index - 0.065) * (index - 0.065);
            abatereStd = Math.sqrt(abatereStd);
            if(abatereStd < minAbatereStd)
            {
                minAbatereStd = abatereStd;
                k = l;
            }
            if(l == 26)
                ok = 1;
            l++;
        }
        return k;
    }
}

