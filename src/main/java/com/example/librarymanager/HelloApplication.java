package com.example.librarymanager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {
    private TableView<Book> tableView = new TableView<>();
    private static Library lib;
    private FileInputOutput fileInputOutput;
    private TextField textFieldSearch = new TextField();
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();


    @Override
    public void start(Stage stage) throws IOException {
        VBox mainlayout = new VBox();
        HBox firstRow = new HBox(8);

        Label labelSearch = new Label("Search :");
        Label labelSortBy = new Label("Sort by : ");


        choiceBox.getItems().addAll(
                "title",
                "subtitle",
                "authors",
                "translators",
                "ISBN",
                "publisher",
                "date",
                "edition",
                "cover",
                "language",
                "rating",
                "tags"
        );
        choiceBox.setValue("title");

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
        buttonSearch.setOnAction(e->search());
        Button buttonAdd = new Button("Add");

        MenuItem mItemNew = new MenuItem("New");
        MenuItem mItemImport = new MenuItem("Import");
        MenuItem mItemExport = new MenuItem("Export");
        mItemExport.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                fileInputOutput = new FileInputOutput("fThread",selectedFile,"w", lib.getBookList());
                System.out.println("Selected file: " + filePath);
                fileInputOutput.run();
            } else {
                System.out.println("File selection canceled.");
            }
        });
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
        VBox.setVgrow(secondContainer,Priority.ALWAYS);
        VBox.setMargin(firstRow,new Insets(8));
        buttonAdd.setOnAction(e->openAddBookWindow());

        Scene scene = new Scene(mainlayout, 1200, 500);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    private void openAddBookWindow() {
        // Create a new stage for the add book window
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Add Book");
        addBookStage.initModality(Modality.APPLICATION_MODAL);

        // Create layout for add book window
       /* VBox addBookLayout = new VBox();
        addBookLayout.setAlignment(Pos.CENTER);
        addBookLayout.setSpacing(10);
        addBookLayout.setPadding(new Insets(20));*/

        GridPane addBookLayout = new GridPane();
        addBookLayout.setAlignment(Pos.CENTER);
        addBookLayout.setHgap(10);
        addBookLayout.setVgap(10);
        addBookLayout.setPadding(new Insets(20));

        // Create text fields for book information
        TextField titleField = new TextField();
        TextField subtitleField = new TextField();
        TextField authorField = new TextField();
        TextField translatorField = new TextField();
        TextField ISBNField = new TextField();
        TextField publisherField = new TextField();
        TextField dateField = new TextField();
        TextField editionField = new TextField();
        TextField coverField = new TextField();
        TextField languageField = new TextField();
        TextField ratingField = new TextField();
        TextField tagsField = new TextField();


        // Create labels for text fields
        Label titleLabel = new Label("Title:");
        Label subtitleLabel = new Label("Subtitle:");
        Label authorlabel = new Label("Author:");
        Label translatorlabel = new Label("Translator:");
        Label ISBNLabel = new Label("ISBN:");
        Label publisherLabel = new Label("Publisher:");
        Label dateLabel = new Label("Date:");
        Label editionLabel = new Label("Edition:");
        Label coverLabel = new Label("Cover:");
        Label languageLabel = new Label("Language:");
        Label ratingLabel = new Label("Rating:");
        Label tagsLabel = new Label("Tags:");


        addBookLayout.add(titleLabel, 0, 0);
        addBookLayout.add(titleField, 1, 0);
        addBookLayout.add(subtitleLabel, 2, 0);
        addBookLayout.add(subtitleField, 3, 0);
        addBookLayout.add(authorlabel, 0, 1);
        addBookLayout.add(authorField, 1, 1);
        addBookLayout.add(translatorlabel, 2, 1);
        addBookLayout.add(translatorField, 3, 1);
        addBookLayout.add(ISBNLabel, 0, 2);
        addBookLayout.add(ISBNField, 1, 2);
        addBookLayout.add(publisherLabel, 2, 2);
        addBookLayout.add(publisherField, 3, 2);
        addBookLayout.add(dateLabel, 0, 3);
        addBookLayout.add(dateField, 1, 3);
        addBookLayout.add(editionLabel, 2, 3);
        addBookLayout.add(editionField, 3, 3);
        addBookLayout.add(coverLabel, 0, 4);
        addBookLayout.add(coverField, 1, 4);
        addBookLayout.add(languageLabel, 2, 4);
        addBookLayout.add(languageField, 3, 4);
        addBookLayout.add(ratingLabel, 0, 5);
        addBookLayout.add(ratingField, 1, 5);
        addBookLayout.add(tagsLabel, 2, 5);
        addBookLayout.add(tagsField, 3, 5);



        //"title","subtitle","authors","translators","ISBN","publisher","date","edition","cover","language","rating","tags"

        // Add components to layout
        //addBookLayout.getChildren().addAll(titleLabel, titleField, subtitleLabel, subtitleField, authorlabel, authorField, translatorlabel, translatorField, ISBNLabel, ISBNField, publisherLabel, publisherField,
                //dateLabel, dateField, editionLabel, editionField, coverLabel, coverField, languageLabel, languageField, ratingLabel, ratingField, tagsLabel, tagsField);

        // Create button for adding book
        Button addBookButton = new Button("Add");
        addBookButton.setOnAction(e -> {
            // Retrieve book information from text fields and add to library
            String title = titleField.getText();
            String subtitle = subtitleField.getText();
            String author = authorField.getText();
            String ISBN = ISBNField.getText();
            String publisher = publisherField.getText();
            String date = dateField.getText();
            String edition = editionField.getText();
            String cover = coverField.getText();
            String language = languageField.getText();
            String rating = ratingField.getText();
            Book curr = new Book(title, subtitle, new ArrayList<>(), new ArrayList<>(), ISBN, publisher, date, edition, cover, language, 0.0, new ArrayList<>());
            // Create Book object and add to library
            // For demonstration, let's assume lib is accessible here
            //lib.getBookList().add(new Book(title, subtitle, null, null, "", "", "", "", "", "", 0.0, null));
            lib.getBookList().add(curr);
            tableView.getItems().clear();
            for(Book i : lib.getBookList()){
                tableView.getItems().add(i);
            }
            // Close the add book window
            addBookStage.close();
        });

        // Add button to layout
        //addBookLayout.getChildren().add(addBookButton);
        addBookLayout.add(addBookButton, 1, 6, 3, 1);
        GridPane.setHalignment(addBookButton, HPos.CENTER);

        // Set scene for add book window
        Scene addBookScene = new Scene(addBookLayout, 600, 300);
        addBookStage.setScene(addBookScene);
        // Show add book window
        addBookStage.show();
    }
    public void search(){
        if(textFieldSearch.getText().isBlank()){
            return;
        }
        else{
            String txtInfoVal = textFieldSearch.getText();
            tableView.getItems().clear();
            switch(choiceBox.getValue()){
                case "title":
                    for(Book i : lib.getBookList()){
                        if(i.getTitle().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "subtitle":
                    for(Book i : lib.getBookList()){
                        if(i.getSubtitle().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "authors":
                    for(Book i : lib.getBookList()){
                        for(String curr : i.getAuthors()){
                            if(curr.equalsIgnoreCase(txtInfoVal)){
                                tableView.getItems().add(i);
                            }
                        }
                    }
                    break;
                case "translators":
                    for(Book i : lib.getBookList()){
                        for(String curr : i.getTranslators()){
                            if(curr.equalsIgnoreCase(txtInfoVal)){
                                tableView.getItems().add(i);
                            }
                        }
                    }

                    break;
                case "ISBN":
                    for(Book i : lib.getBookList()){
                        if(i.getISBN().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }

                    break;
                case "publisher":
                    for(Book i : lib.getBookList()){
                        if(i.getPublisher().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }

                    break;
                case "date":
                    for(Book i : lib.getBookList()){
                        if(i.getDate().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }

                    break;
                case "edition":
                    for(Book i : lib.getBookList()){
                        if(i.getEdition().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "cover":
                    for(Book i : lib.getBookList()){
                        if(i.getCover().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "language":
                    for(Book i : lib.getBookList()){
                        if(i.getLanguage().equalsIgnoreCase(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "rating":
                    for(Book i : lib.getBookList()){
                        if(i.getRating()==Double.parseDouble(txtInfoVal)){
                            tableView.getItems().add(i);
                        }
                    }
                    break;
                case "tags":
                    for(Book i : lib.getBookList()){
                        for(String curr : i.getTags()){
                            if(curr.equalsIgnoreCase(txtInfoVal)){
                                tableView.getItems().add(i);
                            }
                        }
                    }
                    break;
            }
        }

    }
    public static void main(String[] args) {
        launch();
    }
}