package com.example.librarymanager;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> bookList = new ArrayList<>();
    public ArrayList<Book> getBookList() {
        return bookList;
    }
    public Library(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public Library() {
    }

    @Override
    public String toString() {
        return "Library{" +
                "bookList=" + bookList +
                '}';
    }
}

