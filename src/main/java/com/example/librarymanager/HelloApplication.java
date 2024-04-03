package com.example.librarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {
    private TableView<Book> tableView = new TableView<>();
    private static Library lib;
    @Override
    public void start(Stage stage) throws IOException {
        VBox mainlayout = new VBox();
        HBox firstRow = new HBox(8);

        Label labelSearch = new Label("Search :");
        Label labelSortBy = new Label("Sort by : ");

        TextField textFieldSearch = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Book b1 = new Book("Harry Potter and the Philosopher's Stone", "Subtitle 1", new ArrayList<>(List.of("J.K. Rowling")), new ArrayList<>(List.of("Translator 1")), "9781408855652", "Bloomsbury Publishing", "26 June 1997", "First Edition", "Hardcover", "English", 4.5, new ArrayList<>(List.of("Fantasy", "Magic")));
        Book b2 = new Book("The Hobbit", "Subtitle 2", new ArrayList<>(List.of("J.R.R. Tolkien")), new ArrayList<>(List.of("Translator 2")), "9780547928227", "Houghton Mifflin Harcourt", "21 September 1937", "First Edition", "Paperback", "English", 4.8, new ArrayList<>(List.of("Fantasy")));
        Book b3 = new Book("Pride and Prejudice", "Subtitle 3", new ArrayList<>(List.of("Jane Austen")), new ArrayList<>(), "9780141199078", "Penguin Books", "28 January 1813", "First Edition", "Paperback", "English", 4.2, new ArrayList<>(List.of("Romance")));
        ArrayList<Book> arrList = new ArrayList<>();

        arrList.add(b1);
        arrList.add(b2);
        arrList.add(b3);

        lib = new Library(arrList);

        HBox secondContainer = new HBox();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> subTitleColumn = new TableColumn<>("Subtitle");
        subTitleColumn.setCellValueFactory(new PropertyValueFactory<>("subtitle"));

        TableColumn<Book, String[]> authorsColumn = new TableColumn<>("Authors");
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));

        TableColumn<Book, String[]> translatorsColumn = new TableColumn<>("Translators");
        translatorsColumn.setCellValueFactory(new PropertyValueFactory<>("translators"));

        TableColumn<Book, String> ISBNColumn = new TableColumn<>("ISBN");
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Book, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Book, String> editionColumn = new TableColumn<>("Edition");
        editionColumn.setCellValueFactory(new PropertyValueFactory<>("edition"));

        TableColumn<Book, String> coverColumn = new TableColumn<>("Cover");
        coverColumn.setCellValueFactory(new PropertyValueFactory<>("cover"));

        TableColumn<Book, String> languageColumn = new TableColumn<>("Language");
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn<Book, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<Book, String[]> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));

        tableView.getColumns().addAll(titleColumn,subTitleColumn,authorsColumn,translatorsColumn,ISBNColumn,publisherColumn,dateColumn,editionColumn,coverColumn,languageColumn,ratingColumn,tagsColumn);
        for(Book i : lib.getBookList()){
            tableView.getItems().add(i);
        }
        HBox.setHgrow(tableView,Priority.ALWAYS);

        Button buttonSearch = new Button("Search");
        Button buttonAdd = new Button("Add");

        MenuItem mItemNew = new MenuItem("New");
        MenuItem mItemImport = new MenuItem("Import");
        MenuItem mItemExport = new MenuItem("Export");
        MenuItem mItemAbout = new MenuItem("About");

        Menu mFile = new Menu("File");
        Menu mAbout = new Menu("Help");

        MenuBar mBar = new MenuBar();

        mAbout.getItems().addAll(mItemAbout);
        mFile.getItems().addAll(mItemNew,mItemImport,mItemExport);
        mBar.getMenus().addAll(mFile,mAbout);

        firstRow.getChildren().addAll(labelSearch,textFieldSearch,labelSortBy,choiceBox,buttonSearch,buttonAdd);

        HBox.setHgrow(textFieldSearch, Priority.ALWAYS);
        VBox popupContentVBox = new VBox();
        popupContentVBox.setPrefWidth(200);
        popupContentVBox.setPrefHeight(400);
        Image image = new Image("img1.jpg");
        popupContentVBox.setAlignment(Pos.TOP_CENTER);
        popupContentVBox.setPadding(new Insets(10));
        popupContentVBox.setMinWidth(200);
        VBox popupContentInformationVBox = new VBox();
        popupContentInformationVBox.setAlignment(Pos.CENTER_LEFT);

        HBox popupContentButtonHBox = new HBox();
        Button button1 = new Button("Button1");
        Button button2 = new Button("Button2");
        Button button3 = new Button("Button3");

        popupContentButtonHBox.getChildren().addAll(button1,button2,button3);
        popupContentButtonHBox.setAlignment(Pos.BOTTOM_CENTER);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            popupContentVBox.getChildren().clear();
            popupContentInformationVBox.getChildren().clear();
            // Önceki verileri temizle
            if (newValue != null) {
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
                Label label = new Label("\nTitle: " + newValue.getTitle() + "\nSubititle: " + newValue.getSubtitle()+"\nAuthors: " + newValue.getAuthors().toString() + "\nTranslators: " + newValue.getTranslators().toString()+"\nISBN: " + newValue.getISBN() + "\nPublisher: " + newValue.getPublisher()+ "\nDate: " + newValue.getDate()+ "\nEdition: " + newValue.getEdition()+ "\nCover: " + newValue.getCover()+ "\nLanguage: " + newValue.getLanguage()+ "\nRating: " + newValue.getRating()+ "\nTags: " + newValue.getTags()+"\n\n");
                popupContentInformationVBox.getChildren().addAll(label,popupContentButtonHBox);
                popupContentVBox.getChildren().addAll(imageView,popupContentInformationVBox);
            }
        });
        secondContainer.getChildren().addAll(tableView,popupContentVBox);
        mainlayout.getChildren().addAll(mBar,firstRow,secondContainer);

        VBox.setMargin(firstRow,new Insets(8));

        Scene scene = new Scene(mainlayout, 1200, 500);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}