package com.example.librarymanager;
import java.io.File;
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
    private Validation validator = new Validation();

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        try{
            String[] authorArray = authors.split(",");
            this.authors = new ArrayList<>(Arrays.asList(authorArray));
        }catch(Exception e){
            throw new InputMismatchException("Authorlar virgül ile ayrılarak yazılmalı.");
        }
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        try{
            String[] translatorArray = translators.split(",");
            this.translators = new ArrayList<>(Arrays.asList(translatorArray));
        }catch(Exception e){
            throw new InputMismatchException("Translatorlar virgül ile ayrılarak yazılmalı.");
        }
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(String rating) throws NumberFormatException {
        try {
            double parsedRating = Double.parseDouble(rating);
            this.rating=parsedRating;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Rating için double bir değer giriniz.");
        }
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(String tags) throws InputMismatchException{
//        if (tags == null || tags.isBlank()) {
//            throw new InputMismatchException("Etiketler boş olamaz.");
//        }
        try{
            String[] tagArray = tags.split(",");
            this.tags = new ArrayList<>(Arrays.asList(tagArray));
        }catch(Exception e){
            throw new InputMismatchException("Tagler virgül ile ayrılarak yazılmalı.");
        }
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        if(imgFilePath.equals(null)){
            this.imgFilePath="noPic.jpg";
        }
        this.imgFilePath = imgFilePath;
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
