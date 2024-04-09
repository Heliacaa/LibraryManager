package com.example.librarymanager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
                            book.put(a,currBook.getTitle());
                            break;
                        case "subtitle":
                            book.put(a,currBook.getSubtitle());
                            break;
                        case "authors":
                            ArrayNode arrNode = objectMapper.createArrayNode();
                            for(String curr : currBook.getAuthors()){
                                arrNode.add(curr);
                            }
                            book.put(a,arrNode);
                            break;
                        case "translators":
                            ArrayNode arrNodet = objectMapper.createArrayNode();
                            for(String curr : currBook.getAuthors()){
                                arrNodet.add(curr);
                            }
                            book.put(a,arrNodet);
                            break;
                        case "ISBN":
                            book.put(a,currBook.getISBN());
                            break;
                        case "publisher":
                            book.put(a,currBook.getPublisher());
                            break;
                        case "date":
                            book.put(a,currBook.getDate());
                            break;
                        case "edition":
                            book.put(a,currBook.getEdition());
                            break;
                        case "cover":
                            book.put(a,currBook.getCover());
                            break;
                        case "language":
                            book.put(a,currBook.getLanguage());
                            break;
                        case "rating":
                            book.put(a,currBook.getRating());
                            break;
                        case "tags":
                            ArrayNode arrNodeTags = objectMapper.createArrayNode();
                            for(String curr : currBook.getAuthors()){
                                arrNodeTags.add(curr);
                            }
                            book.put(a,arrNodeTags);
                            break;
                        case "imgFilePath":
                            book.put(a,currBook.getImgFilePath());
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
        String title = bookNode.get("title").asText();
        String subtitle = bookNode.get("subtitle").asText();

        // Get the authors arraylist
        JsonNode authorsNode = bookNode.get("authors");
        ArrayList<String> authors = new ArrayList<>();
        if (authorsNode.isArray()) {
            for (JsonNode author : authorsNode) {
                authors.add(author.asText());
            }
        }

        // Get the translators arrayList
        JsonNode translatorsNode = bookNode.get("translators");
        ArrayList<String> translators = new ArrayList<>();
        if (translatorsNode.isArray()) {
            for (JsonNode translator : translatorsNode) {
                translators.add(translator.asText());
            }
        }

        String ISBN = bookNode.get("ISBN").asText();
        String publisher = bookNode.get("publisher").asText();
        String date = bookNode.get("date").asText();
        String edition = bookNode.get("edition").asText();
        String cover = bookNode.get("cover").asText();
        String language = bookNode.get("language").asText();
        double rating = bookNode.get("rating").asDouble();

        // Get the tags arrayList
        JsonNode tagsNode = bookNode.get("tags");
        ArrayList<String> tags = new ArrayList<>();
        if (tagsNode.isArray()) {
            for (JsonNode tag : tagsNode) {
                tags.add(tag.asText());
            }
        }

        String imgFilePath = bookNode.get("imgFilePath").asText();

        // Create book object.
        Book curr = new Book(title, subtitle, authors, translators, ISBN, publisher, date, edition, cover, language, rating, tags, imgFilePath);
        arrList.add(curr);
    }
}
