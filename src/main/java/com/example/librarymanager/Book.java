package com.example.librarymanager;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book {
    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private ArrayList<String> translators;
    private String ISBN;
    private String publisher;
    private String date;
    private String edition;
    private String cover;
    private String language;
    private double rating;
    private ArrayList<String> tags;
    private String imgFilePath;
    public static final String[] specs = {"title","subtitle","authors","translators","ISBN","publisher","date","edition","cover","language","rating","tags","imgFilePath"};
    //private Validation validator = new Validation();

    public Book() {

    }

    public Book(String title, String subtitle, ArrayList<String> authors, ArrayList<String> translators, String ISBN, String publisher, String date, String edition, String cover, String language, double rating, ArrayList<String> tags,String imgFilePath) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.translators = translators;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.date = date;
        this.edition = edition;
        this.cover = cover;
        this.language = language;
        this.rating = rating;
        this.tags = tags;
        setImgFilePath(imgFilePath);
    }
    public Book(String title, String subtitle, String authors, String translators, String ISBN, String publisher, String date, String edition, String cover, String language, String rating, String tags,String imgFilePath) {
        setTitle(title);
        setSubtitle(subtitle);
        setAuthors(authors);
        setTranslators(translators);
        setISBN(ISBN);
        setPublisher(publisher);
        setDate(date);
        setEdition(edition);
        setCover(cover);
        setLanguage(language);
        setRating(rating);
        setTags(tags);
        setImgFilePath(imgFilePath);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException{
        try {
            Validation.checkTitle(title);
            this.title = title;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) throws IllegalArgumentException {
        try {
            Validation.checkSubtitle(subtitle);
            this.subtitle = subtitle;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) throws IllegalArgumentException,InputMismatchException{
        try {
            Validation.checkAuthors(authors);
            // Split authors separated by commas
            String[] authorArray = authors.split(",");

            // Create authors list
            this.authors = new ArrayList<>(Arrays.asList(authorArray));
        } catch (IllegalArgumentException e) {
            throw e;
        }
        try{
            String[] authorArray = authors.split(",");
            this.authors = new ArrayList<>(Arrays.asList(authorArray));
        }catch(Exception e){
            throw new InputMismatchException("Authors should be written separated by commas.");
        }
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) throws IllegalArgumentException,InputMismatchException {
        try {
            Validation.checkTranslators(translators);

            // Split translators separated by commas
            String[] translatorArray = translators.split(",");

            // Create the translators list
            this.translators = new ArrayList<>(Arrays.asList(translatorArray));
        } catch (IllegalArgumentException e) {
            throw e;
        }
        try{
            String[] translatorArray = translators.split(",");
            this.translators = new ArrayList<>(Arrays.asList(translatorArray));
        }catch(Exception e){
            throw new InputMismatchException("Translator information should be written separated by commas.");
        }
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) throws IllegalArgumentException{
        try {
            Validation.checkISBN(ISBN);
            this.ISBN = ISBN;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) throws IllegalArgumentException{
        try {
            Validation.checkPublisher(publisher);
            this.publisher = publisher;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) throws IllegalArgumentException{
        try {
            Validation.checkEdition(edition);
            this.edition = edition;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) throws IllegalArgumentException{
        try {
            Validation.checkCover(cover);
            this.cover = cover;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) throws IllegalArgumentException{
        try {
            Validation.checkLanguage(language);
            this.language = language;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(String rating) throws IllegalArgumentException {
        try {
            Validation.checkRating(rating);
            double parsedRating = Double.parseDouble(rating);
            this.rating=parsedRating;
        }catch (IllegalArgumentException e){
            throw e;
        }
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(String tags) throws InputMismatchException{
        if (tags == null || tags.isBlank()) {
            throw new InputMismatchException("Tags information cannot be null. ");
        }
        try{
            String[] tagArray = tags.split(",");
            this.tags = new ArrayList<>(Arrays.asList(tagArray));
        }catch(Exception e){
            throw new InputMismatchException("Tags should be separated by commas.");
        }
    }
    public void setTags(ArrayList tags){
        if (tags == null || tags.isEmpty()){
            throw new InputMismatchException("Tags cannot be null.");
        }
        this.tags=tags;
    }
    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        try {
            if (imgFilePath == null) {
                this.imgFilePath = "noPic.jpg";
            }else if(imgFilePath.equals("noPic.jpg")) {
                this.imgFilePath = "noPic.jpg";
            }
            else {
                try {
                    // Create the URI of the file
                    URI dosyaURI = URI.create(imgFilePath);

                    //Create path using URI
                    Path dosyaYoluPath = Paths.get(dosyaURI);

                    // Check if the file exists
                    if (Files.exists(dosyaYoluPath)) {
                        this.imgFilePath = imgFilePath;
                    } else {
                        this.imgFilePath = "noPic.jpg";
                    }
                } catch (Exception e) {;
                    //Set a default path
                    this.imgFilePath = "noPic.jpg";
                }
            }
        } catch (Exception e) {
            // In case of error, you can take action here if necessary.
            this.imgFilePath = "noPic.jpg";
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", authors=" + String.join(",",tags) +
                ", translators=" + String.join(",",tags) +
                ", ISBN='" + ISBN + '\'' +
                ", publisher='" + publisher + '\'' +
                ", date='" + date + '\'' +
                ", edition='" + edition + '\'' +
                ", cover='" + cover + '\'' +
                ", language='" + language + '\'' +
                ", rating=" + rating +
                ", tags=" + String.join(",",tags) +
                '}';
    }
}
