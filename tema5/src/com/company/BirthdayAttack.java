package com.company;

import java.util.Random;

public class BirthdayAttack {
    private String[] randomMessages = new String[80];
    private String[] resumesRandomMessages = new String[80];
    public BirthdayAttack(){}

    public int generateRandomLength(){
        Random random = new Random();
        return random.nextInt(100);
    }

    public String generateRandomString(int length){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            int index = random.nextInt(alphabet.length());
            stringBuilder.append(alphabet.charAt(index));
        }
        return stringBuilder.toString();
    }

    public int attack(){
        SHA1 sha1 = new SHA1();
        while(true){
            for(int i = 0; i < 80; i++){
                randomMessages[i] = generateRandomString(generateRandomLength());
                sha1.setMessage(randomMessages[i]);
                resumesRandomMessages[i] = sha1.resume(sha1.processing());
                System.out.println(resumesRandomMessages[i]);
            }
            for(int i = 0; i < 79; i++){
                for(int j = i + 1; j < 80; j++){
                    if(resumesRandomMessages[i] == resumesRandomMessages[j]){
                        System.out.println("Messages are:");
                        System.out.println(randomMessages[i]);
                        System.out.println(randomMessages[j]);
                        System.out.println("Resumes:");
                        System.out.println(resumesRandomMessages[i]);
                        System.out.println(resumesRandomMessages[j]);
                        return 1;
                    }
                }
            }
        }
    }
}
