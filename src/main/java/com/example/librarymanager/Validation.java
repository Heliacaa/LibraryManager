package com.example.librarymanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Validation {

    public static void checkTitle(String title) throws IllegalArgumentException {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
    }

    public static void checkSubtitle(String subtitle) throws IllegalArgumentException {
        if (subtitle == null || subtitle.isBlank()) {
            throw new IllegalArgumentException("Subtitle information cannot be null or empty.");
        }
    }
    public static void checkCover(String cover) throws IllegalArgumentException {
        if (cover == null || cover.isBlank()) {
            throw new IllegalArgumentException("Cover value cannot be null or blank.");
        }
        // Kapak bilgisinde sayısal karakterler olup olmadığını kontrol et
        if (cover.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Cover value cannot contain numbers.");
        }
    }
    public static void checkLanguage(String language) throws IllegalArgumentException {
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Language value cannot be null or blank.");
        }
        // Dil bilgisinin sadece harflerden oluştuğunu kontrol et
        if (!language.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Language value must consist of letters only.");
        }
    }
    public static void checkAuthors(String authors) throws IllegalArgumentException, InputMismatchException {
        if (authors == null || authors.isBlank()) {
            throw new IllegalArgumentException("Authors information cannot be null or empty.");
        }

        // Split authors separated by commas
        String[] authorArray = authors.split(",");

        // Check if the array is empty after split
        if (authorArray.length == 0) {
            throw new InputMismatchException("Authors should be written separated by commas.");
        }
    }

    public static void checkTranslators(String translators) throws IllegalArgumentException, InputMismatchException {
        if (translators == null || translators.isBlank()) {
            throw new IllegalArgumentException("Translator information cannot be null or empty.");
        }

        // Split translators separated by commas
        String[] translatorArray = translators.split(",");

        // Check if the array is empty after split
        if (translatorArray.length == 0) {
            throw new InputMismatchException("Translator information should be written separated by commas.");
        }
    }

    public static void checkISBN(String isbn) throws IllegalArgumentException {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN information cannot be null or empty.");
        }

        // Check the length of the ISBN number
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new IllegalArgumentException("ISBN number should be 10 or 13 digits.");
        }

        // Check if the ISBN number has only digits or 'X' in the last character
        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c) && (i != isbn.length() - 1 || (c != 'X' && c != 'x'))) {
                throw new IllegalArgumentException("ISBN number must consist of numbers only (The last character can be 'X').");
            }
        }
    }
    public static void checkPublisher(String publisher) throws IllegalArgumentException {
        if (publisher == null || publisher.isBlank()) {
            throw new IllegalArgumentException("Publisher information cannot be null or empty.");
        }
    }

    public static void checkDate(String date) throws IllegalArgumentException {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date value cannot be null or blank.");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            // Tarih formatını doğru bir şekilde parse edebiliyor mu kontrol et
            LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date value must be in the format dd/MM/yyyy.");
        }
    }

    public static void checkEdition(String edition) throws IllegalArgumentException {
        if (edition == null || edition.isBlank()) {
            throw new IllegalArgumentException("Edition information cannot be null or empty.");
        }

        // Edition bilgisinin sadece sayılardan oluştuğunu kontrol et
        try {
            Integer.parseInt(edition);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Edition information must consist of numbers only.");
        }
    }
    public static void checkRating(String rating) throws IllegalArgumentException {
        if (rating == null || rating.toString().isBlank()) {
            throw new IllegalArgumentException("Rating value cannot be null or blank.");
        }
        double parsedRating;
        try {
            parsedRating = Double.parseDouble(rating.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rating value must be a valid double number.");
        }
        if (parsedRating < 0 || parsedRating > 10) {
            throw new IllegalArgumentException("Rating value should be between 0 and 10.");
        }
    }
}

