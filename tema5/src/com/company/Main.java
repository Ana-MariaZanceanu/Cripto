package com.company;

public class Main {

    public static void main(String[] args) {
        //String[] H = {"67452301", "EFCDAB89", "98BADCFE", "10325476", "C3D2E1F0"};
        String message1 = "abc";
        SHA1 sha1 = new SHA1(message1);
        String s1 = sha1.processing();
        System.out.println(s1);

        String message2 = "abd";
        SHA1 sha2 = new SHA1(message2);
        String s2 = sha2.processing();
        System.out.println(s2);

        String message3 = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
        SHA1 sha3 = new SHA1(message3);
        System.out.println(sha3.processing());


        String sha1Resume = sha1.resume(s1);
        System.out.println("Resume for x = 'abc': " + sha1Resume);

        String sha2Resume = sha2.resume(s2);
        System.out.println("Resume for x'= 'abd': " + sha2Resume);

        System.out.println("Hamming distance between x and x': " + sha1.distHamming(sha1Resume,sha2Resume));

        BirthdayAttack birthdayAttack = new BirthdayAttack();
        birthdayAttack.attack();
    }
}
