package com.joviroa.url_shortener.util;

public class Base62 {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String fromBase10(long n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(ALPHABET.charAt((int) (n % 62)));
            n /= 62;
        }
        return sb.reverse().toString();
    }

    public static long toBase10(String str) {
        long res = 0;
        for (char c : str.toCharArray()) {
            res = res * 62 + ALPHABET.indexOf(c);
        }
        return res;
    }
}
