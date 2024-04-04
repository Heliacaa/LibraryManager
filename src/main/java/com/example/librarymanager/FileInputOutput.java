package com.example.librarymanager;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileInputOutput{
    private String tName;

    private File file;
    //for 1 write for 2 read;
    private String mode;
    private ArrayList<Book> arrList;

    public FileInputOutput(String tName,File file,String mode,ArrayList<Book>arrList){
        this.tName=tName;
        this.file=file;
        this.mode=mode;
        this.arrList=arrList;
    }

    public void run(){
        try  {
            // ObjectMapper oluştur
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // JSON düzenler

            // JSON dosyasını oku, dosya yoksa yeni bir dosya oluştur
            ArrayNode books = objectMapper.createArrayNode();;

            // İlk kitabı ekler
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
                    }
                }
                books.add(book);
            }
            objectMapper.writeValue(file, books);
            System.out.println("Books are already exported.");;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
