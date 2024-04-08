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

    public void setTitle(String title) {
        try {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title cannot be null.");
            }
            this.title = title;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        try {
            if (subtitle == null||subtitle.isBlank()) {
                throw new IllegalArgumentException("Subtitle information cannot be null.");
            }
            this.subtitle = subtitle;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        try {
            if (authors == null || authors.isBlank()) {
                throw new IllegalArgumentException("Authors information cannot be null. ");
            }

            // Virgül ile ayrılan yazarları parçala
            String[] authorArray = authors.split(",");

            // Yazarlar listesini oluştur
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

    public void setTranslators(String translators) {
        try {
            if (translators == null || translators.isBlank()) {
                throw new IllegalArgumentException("Translator information cannot be null.");
            }

            // Virgül ile ayrılan çevirmenleri parçala
            String[] translatorArray = translators.split(",");

            // Çevirmenler listesini oluştur
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

    public void setISBN(String ISBN) {
        try {
            if (ISBN == null || ISBN.isBlank()) {
                throw new IllegalArgumentException("ISBN information cannot be null.");
            }

            // ISBN numarasının uzunluğunu kontrol et
            if (ISBN.length() != 10 && ISBN.length() != 13) {
                throw new IllegalArgumentException("ISBN number should be 10 or 13 digits.");
            }

            // ISBN numarasının sadece rakam veya son karakterinde 'X' olup olmadığını kontrol et
            for (int i = 0; i < ISBN.length(); i++) {
                char c = ISBN.charAt(i);
                if (!Character.isDigit(c) && (i != ISBN.length() - 1 || (c != 'X' && c != 'x'))) {
                    throw new IllegalArgumentException("ISBN number must consist numbers only (The Last character can be 'X').");
                }
            }

            // Buraya kadar bir hata yoksa, ISBN numarasını atayabiliriz
            this.ISBN = ISBN;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        try {
            if (publisher == null || publisher.isBlank()) {
                throw new IllegalArgumentException("Publisher information cannot be null.");
            }
            // Buraya kadar bir hata yoksa, yayınevi bilgisini atayabiliriz
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

    public void setEdition(String edition) {
        try {
            if (edition == null||edition.isBlank()) {
                throw new IllegalArgumentException("Edition information cannot be null.");
            }

            // Edition değerinin sadece rakamlardan oluştuğunu kontrol et
            for (char c : edition.toCharArray()) {
                if (!Character.isDigit(c)) {
                    throw new IllegalArgumentException("Edition information must consist only numbers.");
                }
            }

            // Buraya kadar herhangi bir hata olmadığına göre, edition değerini atayabiliriz
            this.edition = edition;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        try {
            if (cover == null || cover.isBlank()) {
                throw new IllegalArgumentException("Cover information cannot be null.");
            }
            for (char c : cover.toCharArray()) {
                if (Character.isDigit(c)) {
                    throw new IllegalArgumentException("Cover information does not contain numbers.");
                }
            }
            this.cover = cover;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        try {
            if (language == null||language.isBlank()) {
                throw new IllegalArgumentException("Language information cannot be null.");
            }
            for (char c : language.toCharArray()) {
                if (Character.isDigit(c)) {
                    throw new IllegalArgumentException("Language information does not contain numbers.");
                }
            }
            this.language = language;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(String rating) throws NumberFormatException {
        try {
            double parsedRating = Double.parseDouble(rating);
            if (parsedRating < 0 || parsedRating > 10.0) {
                throw new IllegalArgumentException("Rating value should be between 0-10.");
            }
            this.rating=parsedRating;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Enter a double value for rating.");
        } catch (IllegalArgumentException e){
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
            } else {
                this.imgFilePath = imgFilePath;
            }
        } catch (Exception e) {
            // Hata durumunda gerekirse burada bir işlem yapabilirsiniz.
            e.printStackTrace();
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
