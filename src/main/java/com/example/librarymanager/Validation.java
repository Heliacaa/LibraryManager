package com.example.librarymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public void checkTags(String tags){
        String input = "kelime1,kelime2,kelime3"; // Virgülle ayrılmış kelimeler

        try {
            // Regular expression oluşturalım
            Pattern pattern = Pattern.compile("\\b(\\w+)(,|$)"); // Virgülle ayrılmış kelime grupları

            // Matcher nesnesi oluşturalım
            Matcher matcher = pattern.matcher(input);

            // Her bir eşleşmeyi kontrol edelim
            while (matcher.find()) {
                String word = matcher.group(1); // Kelimeyi al
                System.out.println("Kelime: " + word);

                // İstenilen kontrolü buraya ekleyebilirsiniz
                // Örneğin, her kelimenin boş olmamasını kontrol edebilirsiniz
                if (word.trim().isEmpty()) {
                    throw new InputMismatchException("Boş bir kelime girişi yapılamaz.");
                }
            }

            // Tüm kontrolleri geçtiyse, işleme devam edebiliriz
            System.out.println("Giriş geçerli: " + input);
        } catch (InputMismatchException e) {
            throw e;
        }
    }
}
