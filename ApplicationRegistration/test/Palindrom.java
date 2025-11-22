package com.demo.test;

public class Palindrom {

    public boolean isPalindrom(String s) {
        if (s == null) return false;

        String reverse = "";
        int length = s.length();

        for (int i = length - 1; i >= 0; i--) {
            reverse = reverse + s.charAt(i);
        }

        return s.equalsIgnoreCase(reverse);
    }
}
