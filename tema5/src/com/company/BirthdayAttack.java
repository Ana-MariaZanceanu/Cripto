package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BirthdayAttack {
    private List<String> listResumes = new ArrayList<>();
    private List<String> listMessages = new ArrayList<>();
    private List<String> listHashedMessages = new ArrayList<>();
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

    public boolean existInList(String resume){
        for(String s : listResumes){
            if(s.compareTo(resume) == 0){
                int i = listResumes.indexOf(s);
                System.out.println("Message 1: " + listMessages.get(i));
                System.out.println("Hashed message 1: " + listHashedMessages.get(i));
                System.out.println("Resume 1: " + s);
                return true;
            }
        }
        return false;
    }

    public void attack(){
        SHA1 sha1 = new SHA1();
        int i = 0;
        while(i < 77000){
            String randomMessage = generateRandomString(10);
            sha1.setMessage(randomMessage);
            String hashedMessage = sha1.processing();
            String resume = sha1.resume(hashedMessage);
            if(existInList(resume)){
                System.out.println("Message 2: " + randomMessage);
                System.out.println("Hashed message 2: " + hashedMessage);
                System.out.println("Resume 2: " + resume);
                break;
            }else{
                listResumes.add(resume);
                listMessages.add(randomMessage);
                listHashedMessages.add(hashedMessage);
            }
            i++;
        }
    }
}
