package com.example.librarymanager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileInputOutput{
    private String tName;

    private File file;
    //for 1 write for 2 read;
    private String mode;
    private ArrayList<Book> arrList;

    public File getFile() {
        return file;
    }

    public FileInputOutput(String tName, File file, String mode, ArrayList<Book>arrList){
        this.tName=tName;
        this.file=file;
        this.mode=mode;
        this.arrList=arrList;
    }

    public void run()throws IOException{
        try  {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // JSON düzenler


            ArrayNode books = objectMapper.createArrayNode();


            for(Book currBook : arrList){
                ObjectNode book = objectMapper.createObjectNode();
                for(String a : Book.specs) {
                    switch(a){
                        case "title":
                            book.put(a,currBook.getTitle().trim());
                            break;
                        case "subtitle":
                            book.put(a,currBook.getSubtitle().trim());
                            break;
                        case "authors":
                            ArrayNode arrNode = objectMapper.createArrayNode();
                            for(String curr : currBook.getAuthors()){
                                arrNode.add(curr.trim());
                            }
                            book.put(a,arrNode);
                            break;
                        case "translators":
                            ArrayNode arrNodet = objectMapper.createArrayNode();
                            for(String curr : currBook.getTranslators()){
                                arrNodet.add(curr.trim());
                            }
                            book.put(a,arrNodet);
                            break;
                        case "ISBN":
                            book.put(a,currBook.getISBN().trim());
                            break;
                        case "publisher":
                            book.put(a,currBook.getPublisher().trim());
                            break;
                        case "date":
                            book.put(a,currBook.getDate().trim());
                            break;
                        case "edition":
                            book.put(a,currBook.getEdition().trim());
                            break;
                        case "cover":
                            book.put(a,currBook.getCover().trim());
                            break;
                        case "language":
                            book.put(a,currBook.getLanguage().trim());
                            break;
                        case "rating":
                            book.put(a,currBook.getRating());
                            break;
                        case "tags":
                            ArrayNode arrNodeTags = objectMapper.createArrayNode();
                            for(String curr : currBook.getTags()){
                                arrNodeTags.add(curr.trim());
                            }
                            book.put(a,arrNodeTags);
                            break;
                        case "imgFilePath":
                            book.put(a,currBook.getImgFilePath().trim());
                            break;
                    }
                }
                books.add(book);
            }
            objectMapper.writeValue(file, books);
            System.out.println("Books are already exported.");
        } catch (IOException e) {
            throw new IOException("An error occurred while saving the file");
        }
    }
    public void autoPull() throws IOException{
        try {
            // read JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("books.json");
            JsonNode rootNode = objectMapper.readTree(file);

            //get ArrayNode
            ArrayNode booksArrayNode = (ArrayNode) rootNode;

            // Process each element in the ArrayNode
            for (JsonNode bookNode : booksArrayNode) {
                // Process each book
                processBookNode(bookNode,arrList);
            }
        } catch (Exception e) {
            throw new IOException("An error occurred while reading the file.The file is not there or unreadable.");
        }
    }
    private static void processBookNode(JsonNode bookNode,ArrayList<Book> arrList) {
        String title;
        try{
            title = bookNode.get("title").asText().trim();
            Validation.checkTitle(title);
        }catch(IllegalArgumentException | NullPointerException e){
            title="NoTitle";
        }

        String subtitle;
        try {
            subtitle = bookNode.get("subtitle").asText().trim();
            Validation.checkSubtitle(subtitle);
        } catch (IllegalArgumentException | NullPointerException e) {
            subtitle = "NoSubtitle";
        }

        // Get the authors arraylist
        ArrayList<String> authors=new ArrayList<>();
        try{
            JsonNode authorsNode = bookNode.get("authors");
            if (authorsNode.isArray()) {
                for (JsonNode author : authorsNode) {
                    authors.add(author.asText().trim());
                }
            }
        }catch (IllegalArgumentException | NullPointerException e) {
            authors = new ArrayList<>();
        }


        // Get the translators arrayList
        ArrayList<String> translators = new ArrayList<>();
        try{
            JsonNode translatorsNode = bookNode.get("translators");
            if (translatorsNode.isArray()) {
                for (JsonNode translator : translatorsNode) {
                    translators.add(translator.asText().trim());
                }
            }
        }catch (IllegalArgumentException | NullPointerException e) {
            translators = new ArrayList<>();
        }


        // ISBN
        String ISBN = "1234567890";
        try {
            String tempISBN = bookNode.get("ISBN").asText().trim();
            if (!tempISBN.isEmpty()) {
                Validation.checkISBN(tempISBN);
                ISBN = tempISBN;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the ISBN value is invalid or null, assign "NoISBN"
        }

// Publisher
        String publisher = "NoPublisher";
        try {
            String tempPublisher = bookNode.get("publisher").asText().trim();
            if (!tempPublisher.isEmpty()) {
                Validation.checkPublisher(tempPublisher);
                publisher = tempPublisher;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the publisher value is invalid or null, assign "NoPublisher"
        }

// Date
        String date = "01/01/2000";
        try {
            String tempDate = bookNode.get("date").asText().trim();
            if (!tempDate.isEmpty()) {
                Validation.checkDate(tempDate);
                date = tempDate;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the date value is invalid or null, assign "NoDate"
        }

// Edition
        String edition = "1";
        try {
            String tempEdition = bookNode.get("edition").asText().trim();
            if (!tempEdition.isEmpty()) {
                Validation.checkEdition(tempEdition);
                edition = tempEdition;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the edition value is invalid or null, assign "NoEdition"
        }

// Cover
        String cover = "NoCover";
        try {
            String tempCover = bookNode.get("cover").asText().trim();
            if (!tempCover.isEmpty()) {
                Validation.checkCover(tempCover);
                cover = tempCover;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the cover value is invalid or null, assign "NoCover"
        }

// Language
        String language = "NoLanguage";
        try {
            String tempLanguage = bookNode.get("language").asText().trim();
            if (!tempLanguage.isEmpty()) {
                Validation.checkLanguage(tempLanguage);
                language = tempLanguage;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the language value is invalid or null, assign "NoLanguage"
        }

// Rating
        double rating = 0.0;
        try {
            rating = bookNode.get("rating").asDouble();
            Validation.checkRating(String.valueOf(rating));
        } catch (IllegalArgumentException | NullPointerException e) {
            // If the rating value is invalid or null, assign 0.0
        }

        // Get the tags arrayList
        ArrayList<String> tags = new ArrayList<>();
        try{
            JsonNode tagsNode = bookNode.get("tags");
            if (tagsNode.isArray()) {
                for (JsonNode tag : tagsNode) {
                    if(!HelloApplication.getTagsList().contains(tag.asText())){
                        HelloApplication.getTagsList().add(tag.asText());
                    }
                    if(HelloApplication.getTagsList().contains(tag.asText())){
                        tags.add(tag.asText().trim());
                    }
                }
            }
        }catch (IllegalArgumentException | NullPointerException e) {
            tags=new ArrayList<>();
        }
        String imgFilePath = bookNode.get("imgFilePath").asText().trim();

        // Create book object.
        Book curr = new Book(title, subtitle, authors, translators, ISBN, publisher, date, edition, cover, language, rating, tags, imgFilePath);
        arrList.add(curr);
    }
    public void importBooks(){
        try{
            try {
                // read JSON file
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(file);

                //get ArrayNode
                ArrayNode booksArrayNode = (ArrayNode) rootNode;

                // Process each element in the ArrayNode
                arrList.clear();
                for (JsonNode bookNode : booksArrayNode) {
                    // Process each book
                    processBookNode(bookNode,arrList);
                }
            } catch (Exception e) {
                throw new IOException("An error occurred while reading the file.The file is not there or unreadable.");
            }
        }catch(IOException e){
        }
    }
}
