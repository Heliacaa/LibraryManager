package com.example.librarymanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
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
        Label labelSortBy = new Label("Search by : ");


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

        //Book b1 = new Book("Harry Potter and the Philosopher's Stone", "Subtitle 1", new ArrayList<>(List.of("J.K. Rowling")), new ArrayList<>(List.of("Translator 1")), "9781408855652", "Bloomsbury Publishing", "26 June 1997", "First Edition", "Hardcover", "English", 4.5, new ArrayList<>(List.of("Fantasy", "Magic")),"img1.jpg");
        //Book b2 = new Book("The Hobbit", "Subtitle 2", new ArrayList<>(List.of("J.R.R. Tolkien")), new ArrayList<>(List.of("Translator 2")), "9780547928227", "Houghton Mifflin Harcourt", "21 September 1937", "First Edition", "Paperback", "English", 4.8, new ArrayList<>(List.of("Fantasy")),"img1.jpg");
        //Book b3 = new Book("Pride and Prejudice", "Subtitle 3", new ArrayList<>(List.of("Jane Austen")), new ArrayList<>(), "9780141199078", "Penguin Books", "28 January 1813", "First Edition", "Paperback", "English", 4.2, new ArrayList<>(List.of("Romance")),"img1.jpg");
        ArrayList<Book> arrList = new ArrayList<>();

        //arrList.add(b1);
        //arrList.add(b2);
        //arrList.add(b3);

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

        fileInputOutput = new FileInputOutput("rThread",new File("books.json"),"r",lib.getBookList());
        try{
            fileInputOutput.autoPull();
        }catch (IOException ioEx){
            showAlert("Warning","Warning", ioEx.getMessage());
        }


        tableView.getColumns().addAll(titleColumn,subTitleColumn,authorsColumn,translatorsColumn,ISBNColumn,publisherColumn,dateColumn,editionColumn,coverColumn,languageColumn,ratingColumn,tagsColumn);
        for(Book i : lib.getBookList()){
            tableView.getItems().add(i);
        }
        HBox.setHgrow(tableView,Priority.ALWAYS);

        Button buttonSearch = new Button("Search");
        try{
            buttonSearch.setOnAction(e->search());
        }catch(NumberFormatException e){

        }
        Button buttonResetSearch = new Button("Reset Search");
        buttonResetSearch.setOnAction(e->resetSearch());
        Button buttonAdd = new Button("Add");

        MenuItem mItemNew = new MenuItem("New");
        mItemNew.setOnAction(e->newLib(stage));
        MenuItem mItemImport = new MenuItem("Import");
        mItemImport.setOnAction(e->importLib(stage));
        MenuItem mItemExport = new MenuItem("Export");
        mItemExport.setOnAction(e ->exportLib(stage));
        MenuItem mItemAbout = new MenuItem("About");

        Menu mFile = new Menu("File");
        Menu mAbout = new Menu("Help");
        mItemAbout.setOnAction(event -> showHelpWindow());

        MenuBar mBar = new MenuBar();

        mAbout.getItems().addAll(mItemAbout);
        mFile.getItems().addAll(mItemNew,mItemImport,mItemExport);
        mBar.getMenus().addAll(mFile,mAbout);

        firstRow.getChildren().addAll(labelSearch,textFieldSearch,labelSortBy,choiceBox,buttonSearch,buttonResetSearch,buttonAdd);

        HBox.setHgrow(textFieldSearch, Priority.ALWAYS);
        VBox popupContentVBox = new VBox();
        popupContentVBox.setPrefWidth(200);
        popupContentVBox.setPrefHeight(400);

        popupContentVBox.setAlignment(Pos.TOP_CENTER);
        popupContentVBox.setPadding(new Insets(10));
        popupContentVBox.setMinWidth(200);
        VBox popupContentInformationVBox = new VBox();
        popupContentInformationVBox.setAlignment(Pos.CENTER_LEFT);

        HBox popupContentButtonHBox = new HBox(8);
        Button buttonEdit = new Button("Edit");
        Button buttonDelete = new Button("Delete");
        //Button button3 = new Button("Button3");

        popupContentButtonHBox.getChildren().addAll(buttonEdit,buttonDelete);
        popupContentButtonHBox.setAlignment(Pos.BOTTOM_CENTER);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            popupContentVBox.getChildren().clear();
            popupContentInformationVBox.getChildren().clear();
            // Clear previous data.
            if (newValue != null) {
                Image image = new Image(newValue.getImgFilePath());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
                Label label = new Label("\nTitle: " + newValue.getTitle() + "\nSubititle: " + newValue.getSubtitle()+"\nAuthors: " + newValue.getAuthors().toString() + "\nTranslators: " + newValue.getTranslators().toString()+"\nISBN: " + newValue.getISBN() + "\nPublisher: " + newValue.getPublisher()+ "\nDate: " + newValue.getDate()+ "\nEdition: " + newValue.getEdition()+ "\nCover: " + newValue.getCover()+ "\nLanguage: " + newValue.getLanguage()+ "\nRating: " + newValue.getRating()+ "\nTags: " + newValue.getTags()+"\n\n");
                popupContentInformationVBox.getChildren().addAll(label,popupContentButtonHBox);
                popupContentVBox.getChildren().addAll(imageView,popupContentInformationVBox);
            }
            buttonDelete.setOnAction(e->{
                lib.getBookList().remove(newValue);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(newValue.getTitle()+" "+" was successfully deleted.");
                alert.initOwner(stage);
                alert.showAndWait();
                tableView.getItems().clear();
                tableView.getItems().addAll(lib.getBookList());
            });
        });
        secondContainer.getChildren().addAll(tableView,popupContentVBox);
        mainlayout.getChildren().addAll(mBar,firstRow,secondContainer);
        VBox.setVgrow(secondContainer,Priority.ALWAYS);
        VBox.setMargin(firstRow,new Insets(8));
        buttonAdd.setOnAction(e->openAddBookWindow());
        buttonEdit.setOnAction(e -> editBook(stage));

        stage.setOnCloseRequest(e->{
            fileInputOutput= new FileInputOutput("autoSave",new File("books.json"),"w",lib.getBookList());
            try {
                fileInputOutput.run();
                showAlert("Information","Information","The file was automatically saved to: "+fileInputOutput.getFile().getAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(mainlayout, 1200, 500);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }
    public void editBook(Stage stage){
        {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                openEditBookWindow(selectedBook);
            } else {
                // Handle case where no book is selected.
            }
        }
    }
    public void resetSearch(){
        tableView.getItems().clear();
        tableView.getItems().addAll(lib.getBookList());
    }
    public void exportLib(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        try{
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                fileInputOutput = new FileInputOutput("fThread",selectedFile,"w", lib.getBookList());
                System.out.println("Selected file: " + filePath);
                fileInputOutput.run();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("File was successfully created on ."+filePath.toString());
                alert.initOwner(stage);
                alert.showAndWait();
            } else {
                selectedFile = new File("books.json");
                String filePath = selectedFile.getAbsolutePath();
                fileInputOutput = new FileInputOutput("fThread",selectedFile,"w", lib.getBookList());
                System.out.println("Selected file: " + filePath);
                fileInputOutput.run();
                throw new NoSuchFileException("File selection is cancelled.\nFile was automatically saved on :\n"+selectedFile.getAbsolutePath());
            }
        }catch(Exception exception){
            showAlert("Info","Info",exception.getMessage());
        }
    }
    public void importLib(Stage stage){
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Source File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(stage);
            try{
                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    fileInputOutput = new FileInputOutput("fThread",selectedFile,"r", lib.getBookList());
                    System.out.println("Selected file: " + filePath);
                    fileInputOutput.importBooks();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("File was successfully imported from "+filePath.toString());
                    tableView.getItems().clear();
                    for(Book i : lib.getBookList()){
                        tableView.getItems().add(i);
                    }
                    alert.initOwner(stage);
                    alert.showAndWait();
                } else {
                    throw new NoSuchFileException("File selection is cancelled.");
                }
            }catch(Exception exception){
                showAlert("Info","Info",exception.getMessage());
            }
        }
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
        TextField authorField = new TextField("J.R.R. Tolkien,H.G. Wells");
        authorField.setOnMouseClicked(e -> {
            authorField.clear();
            //Remove click event tracker
            authorField.setOnMouseClicked(null);
        });
        TextField translatorField = new TextField("Constance,Gregory Rabassa");
        translatorField.setOnMouseClicked(e -> {
            translatorField.clear();
            //Remove click event tracker
            translatorField.setOnMouseClicked(null);
        });

        TextField ISBNField = new TextField();
        TextField publisherField = new TextField();
        //TextField dateField = new TextField();
        TextField editionField = new TextField();
        TextField coverField = new TextField();
        TextField languageField = new TextField();
        TextField ratingField = new TextField();
        //TextField tagsField = new TextField();


        // Create labels for text fields
        Label titleLabel = new Label("Title:");
        Label subtitleLabel = new Label("Subtitle:");
        Label authorlabel = new Label("Authors:");
        Label translatorlabel = new Label("Translators:");
        Label ISBNLabel = new Label("ISBN:");
        Label publisherLabel = new Label("Publisher:");
        Label dateLabel = new Label("Date:");
        Label editionLabel = new Label("Edition:");
        Label coverLabel = new Label("Cover:");
        Label languageLabel = new Label("Language:");
        Label ratingLabel = new Label("Rating:");
        Label tagsLabel = new Label("Tags:");
        Label filePath = new Label("Image :");

        Button selectTagsButton = new Button("Select Tags");
        ArrayList<String> tags = new ArrayList<>();
        selectTagsButton.setOnAction(e -> showTagsSelectionWindow(addBookStage,tags));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

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
        addBookLayout.add(datePicker, 1, 3);
        addBookLayout.add(editionLabel, 2, 3);
        addBookLayout.add(editionField, 3, 3);
        addBookLayout.add(coverLabel, 0, 4);
        addBookLayout.add(coverField, 1, 4);
        addBookLayout.add(languageLabel, 2, 4);
        addBookLayout.add(languageField, 3, 4);
        addBookLayout.add(ratingLabel, 0, 5);
        addBookLayout.add(ratingField, 1, 5);
        addBookLayout.add(tagsLabel, 2, 5);
        addBookLayout.add(selectTagsButton, 3, 5);
        addBookLayout.add(filePath,0,6);

        Button addImgButton = new Button("Add Image");

        FileChooser fileChooser = new FileChooser();

        // Sadece PNG ve JPEG uzantılı dosyaları filtreleme
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        final File[] selectedFile = new File[1];
        // Butona tıklandığında dosya seçiciyi açma
        addImgButton.setOnAction(e -> {
            try{
                selectedFile[0] = fileChooser.showOpenDialog(addBookStage);
                if (selectedFile[0] != null) {

                } else {
                    throw new NoSuchFileException("File selection is cancelled.If you do not select a file, your book image will be entered by default.");
                }
            }catch(NoSuchFileException exception){
                showAlert("Info","Info",exception.getMessage());
            }
        });

        // Create button for adding book
        Button addBookButton = new Button("Add");
        addBookButton.setOnAction(e -> {
            // Retrieve book information from text fields and add to library
            String title = titleField.getText();
            String subtitle = subtitleField.getText();
            String author = authorField.getText();
            String translator = translatorField.getText();
            String ISBN = ISBNField.getText();
            String publisher = publisherField.getText();
            // Seçilen tarihi al
            LocalDate selectedDate = datePicker.getValue();

            // Tarihi stringe dönüştür
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = selectedDate.format(formatter);
            String edition = editionField.getText();
            String cover = coverField.getText();
            String language = languageField.getText();
            String rating = ratingField.getText();
            String imgFilePath = "noPic.jpg";

            try {
                if(selectedFile[0]!=null){
                    imgFilePath=selectedFile[0].toURI().toURL().toString();
                }
            } catch (MalformedURLException ex) {
                showAlert("Warning","Warning",ex.getMessage());
            }

            try{
               Book curr = new Book(title, subtitle,author,translator, ISBN, publisher, date, edition, cover, language, rating,"tags",imgFilePath);
               curr.setTags(tags);
               lib.getBookList().add(curr);
            }catch (InputMismatchException inputMismatchException){
                showAlert("Warning","Warning",inputMismatchException.getMessage());
                return;
            }catch(NumberFormatException numberFormatException){
                showAlert("Warning","Warning",numberFormatException.getMessage());
                return;
            }catch(IllegalArgumentException illegalArgumentException){
                showAlert("Warning","Warning",illegalArgumentException.getMessage());
                return;
            }

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
        addBookLayout.add(addImgButton,1,6);
        GridPane.setHalignment(addBookButton, HPos.CENTER);

        // Set scene for add book window
        Scene addBookScene = new Scene(addBookLayout, 600, 300);
        addBookStage.setScene(addBookScene);
        // Show add book window
        addBookStage.show();
    }
    private void showTagsSelectionWindow(Stage primaryStage,ArrayList<String> tags) {
        // Tags selection window
        Stage tagsStage = new Stage();
        tagsStage.setTitle("Select Tags");

// Layout for tags selection window
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

// Mock tags list
        List<String> tagsList = new ArrayList<>(Arrays.asList(
                "Action and Adventure",
                "Anthology",
                "Art",
                "Autobiographies",
                "Biographies",
                "Children's",
                "Comics",
                "Cookbooks",
                "Diaries",
                "Dictionaries",
                "Drama",
                "Encyclopedias",
                "Fantasy",
                "Guide",
                "Health",
                "History",
                "Horror",
                "Journals",
                "Math",
                "Mystery",
                "Poetry",
                "Prayer books",
                "Religion, Spirituality & New Age",
                "Romance",
                "Satire",
                "Science",
                "Science Fiction",
                "Self-help",
                "Series",
                "Travel",
                "Trilogy"
        ));

// Create a flow pane to hold the checkboxes
        FlowPane checkBoxPane = new FlowPane();
        checkBoxPane.setHgap(10);
        checkBoxPane.setVgap(10);
        checkBoxPane.setPadding(new Insets(10));

// Create and add checkboxes for each tag
        for (String tag : tagsList) {
            CheckBox checkBox = new CheckBox(tag);
            if (tags.contains(tag)) {
                checkBox.setSelected(true);
            }
            checkBoxPane.getChildren().add(checkBox);
        }

// ScrollPane to make the tag list scrollable
        ScrollPane scrollPane = new ScrollPane(checkBoxPane);
        scrollPane.setFitToWidth(true);

// Add scroll pane to the center of the border pane
        root.setCenter(scrollPane);

// Add button to confirm selection
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            tags.clear();
            for (Node node : checkBoxPane.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        tags.add(checkBox.getText());
                    }
                }
            }
            tagsStage.close();
        });

// Add confirm button to the bottom of the border pane
        root.setBottom(confirmButton);
        BorderPane.setAlignment(confirmButton, Pos.CENTER);

        tagsStage.setScene(new Scene(root, 400, 400));
        tagsStage.initOwner(primaryStage);
        tagsStage.show();

    }
    private void showTagsSearchWindow() {
        // Tags selection window
        Stage tagsStage = new Stage();
        tagsStage.setTitle("Select Tags to Search");
        ArrayList<String> tags = new ArrayList<>();

// Layout for tags selection window
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

// Mock tags list
        List<String> tagsList = new ArrayList<>(Arrays.asList(
                "Action and Adventure",
                "Anthology",
                "Art",
                "Autobiographies",
                "Biographies",
                "Children's",
                "Comics",
                "Cookbooks",
                "Diaries",
                "Dictionaries",
                "Drama",
                "Encyclopedias",
                "Fantasy",
                "Guide",
                "Health",
                "History",
                "Horror",
                "Journals",
                "Math",
                "Mystery",
                "Poetry",
                "Prayer books",
                "Religion, Spirituality & New Age",
                "Romance",
                "Satire",
                "Science",
                "Science Fiction",
                "Self-help",
                "Series",
                "Travel",
                "Trilogy"
        ));

// Add checkboxes for each tag to the grid pane
        int col = 0;
        int row = 0;
        for (String tag : tagsList) {
            CheckBox checkBox = new CheckBox(tag);
            checkBox.setMaxWidth(Double.MAX_VALUE);
            checkBox.setOnAction(e -> {
                if (checkBox.isSelected()) {
                    tags.add(tag);
                } else {
                    tags.remove(tag);
                }
            });
            root.add(checkBox, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

// Add a ScrollPane to make the list of checkboxes scrollable
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

// Add a button to trigger the search action
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            tableView.getItems().clear();
            for (Book book : lib.getBookList()) {
                if (book.getTags().containsAll(tags)) {
                    tableView.getItems().add(book);
                }
            }
            tagsStage.close();
        });

// Place the search button at the bottom of the window
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(scrollPane, searchButton);
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        tagsStage.setScene(new Scene(vbox, 400, 400));
        tagsStage.show();
    }
    public void search(){
        if(choiceBox.getValue().equals("tags")){
            showTagsSearchWindow();
        }else if(textFieldSearch.getText().isBlank()){
            return;
        }
        else{
            String txtInfoVal = textFieldSearch.getText();
            tableView.getItems().clear();
            switch(choiceBox.getValue()){
                case "title":
                    try{
                        Validation.checkTitle(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getTitle().equalsIgnoreCase(txtInfoVal)||i.getTitle().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "subtitle":
                    try{
                        Validation.checkSubtitle(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getSubtitle().equalsIgnoreCase(txtInfoVal)||i.getSubtitle().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "authors":
                    try {
                        // Check the input for authors
                        Validation.checkAuthors(txtInfoVal);

                        // Split the author input by commas
                        String[] authorArray = txtInfoVal.split(",");

                        // Iterate over all books
                        for (Book book : lib.getBookList()) {
                            // Iterate over each author
                            for (String author : authorArray) {
                                // Check if the author is in the book's authors list
                                for (String curr : book.getAuthors()) {
                                    // If the book contains the author, add it to the table view
                                    if (curr.trim().equalsIgnoreCase(author.trim())) {
                                        tableView.getItems().add(book);
                                        // Break out of the loop since we've found a match
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        // Show an alert for invalid input
                        showAlert("Alert", "Alert", e.getMessage());
                    }
                    break;
                case "translators":
                    try {
                        // Check the input for translators
                        Validation.checkTranslators(txtInfoVal);

                        // Split the translator input by commas
                        String[] translatorArray = txtInfoVal.split(",");

                        // Iterate over all books
                        for (Book book : lib.getBookList()) {
                            // Iterate over each translator
                            for (String translator : translatorArray) {
                                // Check if the translator is in the book's translators list
                                for (String curr : book.getTranslators()) {
                                    // If the book contains the translator, add it to the table view
                                    if (curr.trim().equalsIgnoreCase(translator.trim())) {
                                        tableView.getItems().add(book);
                                        // Break out of the loop since we've found a match
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        // Show an alert for invalid input
                        showAlert("Alert", "Alert", e.getMessage());
                    }
                    break;
                case "ISBN":
                    try{
                        Validation.checkISBN(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getISBN().equalsIgnoreCase(txtInfoVal)||i.getISBN().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "publisher":
                    try{
                        Validation.checkPublisher(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getPublisher().equalsIgnoreCase(txtInfoVal)||i.getPublisher().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "date":
                    try {
                        Validation.checkDate(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getDate().equalsIgnoreCase(txtInfoVal)||i.getDate().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "edition":
                    try{
                        Validation.checkEdition(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getEdition().equalsIgnoreCase(txtInfoVal)||i.getEdition().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "cover":
                    try{
                        Validation.checkCover(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getCover().equalsIgnoreCase(txtInfoVal)||i.getCover().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "language":
                    try{
                        Validation.checkLanguage(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            if(i.getLanguage().equalsIgnoreCase(txtInfoVal)||i.getLanguage().toLowerCase().contains(txtInfoVal.toLowerCase())){
                                tableView.getItems().add(i);
                            }
                        }
                    }catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;
                case "rating":
                    try {
                        Validation.checkRating(txtInfoVal);
                        for(Book i : lib.getBookList()){
                            double parsedDouble = Double.parseDouble(txtInfoVal);
                            if(i.getRating()==parsedDouble){
                                tableView.getItems().add(i);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        showAlert("Alert","Alert",e.getMessage());
                    }
                    break;

//                case "tags":
//                    for(Book i : lib.getBookList()){
//                        for(String curr : i.getTags()){
//                            if(curr.equalsIgnoreCase(txtInfoVal)||curr.toLowerCase().contains(txtInfoVal)){
//                                tableView.getItems().add(i);
//                            }
//                        }
//                    }
//                    return;
                    //break;
            }
        }
    }
    public void newLib(Stage stage){
        lib = new Library();
        tableView.getItems().clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("New library is created successfully.");
        alert.initOwner(stage);
        alert.showAndWait();
        return;
    }
    private void openEditBookWindow(Book bookToEdit) {
        // Create a new stage for the edit book window
        Stage editBookStage = new Stage();
        editBookStage.setTitle("Edit Book");
        editBookStage.initModality(Modality.APPLICATION_MODAL);

        // Create layout for edit book window
        GridPane editBookLayout = new GridPane();
        editBookLayout.setAlignment(Pos.CENTER);
        editBookLayout.setHgap(10);
        editBookLayout.setVgap(10);
        editBookLayout.setPadding(new Insets(20));

        // Create text fields for book information
        TextField titleField = new TextField(bookToEdit.getTitle());
        TextField subtitleField = new TextField(bookToEdit.getSubtitle());
        TextField authorField = new TextField(String.join(", ", bookToEdit.getAuthors()));
        TextField translatorField = new TextField(String.join(", ", bookToEdit.getTranslators())); // Assuming translator is not editable in this scenario
        TextField ISBNField = new TextField(bookToEdit.getISBN());
        TextField publisherField = new TextField(bookToEdit.getPublisher());
        //TextField dateField = new TextField(bookToEdit.getDate());
        TextField editionField = new TextField(bookToEdit.getEdition());
        TextField coverField = new TextField(bookToEdit.getCover());
        TextField languageField = new TextField(bookToEdit.getLanguage());
        TextField ratingField = new TextField(String.valueOf(bookToEdit.getRating()));
        //TextField tagsField = new TextField(String.join(", ", bookToEdit.getTags())); // Assuming tags are separated by comma

        // Create labels for text fields
        Label titleLabel = new Label("Title:");
        Label subtitleLabel = new Label("Subtitle:");
        Label authorLabel = new Label("Authors:");
        Label translatorLabel = new Label("Translators:");
        Label ISBNLabel = new Label("ISBN:");
        Label publisherLabel = new Label("Publisher:");
        Label dateLabel = new Label("Date:");
        Label editionLabel = new Label("Edition:");
        Label coverLabel = new Label("Cover:");
        Label languageLabel = new Label("Language:");
        Label ratingLabel = new Label("Rating:");
        Label tagsLabel = new Label("Tags:");
        Label filePath = new Label("Image :");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

        Button selectTagsButton = new Button("Select Tags");
        ArrayList<String> tags = new ArrayList<>();
        selectTagsButton.setOnAction(e -> showTagsSelectionWindow(editBookStage,bookToEdit.getTags()));
        // Add labels and text fields to the grid pane
        editBookLayout.add(titleLabel, 0, 0);
        editBookLayout.add(titleField, 1, 0);
        editBookLayout.add(subtitleLabel, 2, 0);
        editBookLayout.add(subtitleField, 3, 0);
        editBookLayout.add(authorLabel, 0, 1);
        editBookLayout.add(authorField, 1, 1);
        editBookLayout.add(translatorLabel, 2, 1);
        editBookLayout.add(translatorField, 3, 1);
        editBookLayout.add(ISBNLabel, 0, 2);
        editBookLayout.add(ISBNField, 1, 2);
        editBookLayout.add(publisherLabel, 2, 2);
        editBookLayout.add(publisherField, 3, 2);
        editBookLayout.add(dateLabel, 0, 3);
        editBookLayout.add(datePicker, 1, 3);
        editBookLayout.add(editionLabel, 2, 3);
        editBookLayout.add(editionField, 3, 3);
        editBookLayout.add(coverLabel, 0, 4);
        editBookLayout.add(coverField, 1, 4);
        editBookLayout.add(languageLabel, 2, 4);
        editBookLayout.add(languageField, 3, 4);
        editBookLayout.add(ratingLabel, 0, 5);
        editBookLayout.add(ratingField, 1, 5);
        editBookLayout.add(tagsLabel, 2, 5);
        editBookLayout.add(selectTagsButton, 3, 5);
        editBookLayout.add(filePath,0,6);

        Button addImgButton = new Button("Add Image");

        FileChooser fileChooser = new FileChooser();

        // Sadece PNG ve JPEG uzantılı dosyaları filtreleme
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        final File[] selectedFile = {new File(bookToEdit.getImgFilePath())};
        // Butona tıklandığında dosya seçiciyi açma
        addImgButton.setOnAction(e -> {
            selectedFile[0] = fileChooser.showOpenDialog(editBookStage);
            if (selectedFile[0] == null) {
                return;
            } else {
                try {
                    bookToEdit.setImgFilePath(selectedFile[0].toURI().toURL().toString());

                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Create button for saving edited book
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try{
                bookToEdit.setTitle(titleField.getText());
                bookToEdit.setSubtitle(subtitleField.getText());
                bookToEdit.setAuthors(authorField.getText());
                bookToEdit.setTranslators(translatorField.getText());
                bookToEdit.setISBN(ISBNField.getText());
                bookToEdit.setPublisher(publisherField.getText());
                // Seçilen tarihi al
                LocalDate selectedDate = datePicker.getValue();

                // Tarihi stringe dönüştür
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = selectedDate.format(formatter);
                bookToEdit.setDate(date);
                bookToEdit.setEdition(editionField.getText());
                bookToEdit.setCover(coverField.getText());
                bookToEdit.setLanguage(languageField.getText());
                bookToEdit.setRating(ratingField.getText());
                //bookToEdit.setTags(tagsField.getText());
                bookToEdit.setTags(bookToEdit.getTags());
            }catch (InputMismatchException inputMismatchException){
                showAlert("Warning","Warning",inputMismatchException.getMessage());
                return;
            }catch(NumberFormatException numberFormatException){
                showAlert("Warning","Warning",numberFormatException.getMessage());
                return;
            }catch(IllegalArgumentException illegalArgumentException){
                showAlert("Warning","Warning",illegalArgumentException.getMessage());
                return;
            }
            // Update book information with edited values

            tableView.getItems().clear();
            for(Book i : lib.getBookList()){
                tableView.getItems().add(i);
            }
            // Close the edit book window
            editBookStage.close();
        });

        // Add button to the grid pane
        editBookLayout.add(saveButton, 1, 6, 3, 1);
        editBookLayout.add(addImgButton,1,6);
        GridPane.setHalignment(saveButton, HPos.CENTER); // Center the button horizontally

        // Set scene for edit book window
        Scene editBookScene = new Scene(editBookLayout, 600, 300);
        editBookStage.setScene(editBookScene);

        // Show edit book window
        editBookStage.show();
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        // Create a custom dialog pane with a larger size
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setPrefWidth(400); // Set preferred width
        dialogPane.setPrefHeight(200); // Set preferred height

        alert.showAndWait();
    }
    public void showHelpWindow() {
        // Create a new stage for the help window
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.initModality(Modality.APPLICATION_MODAL);

        //make the help window buttons 2 per line like addbooklayout

        // Create layout for help window
        VBox helpLayout = new VBox();
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setSpacing(10);
        helpLayout.setPadding(new Insets(20));
        Label label = new Label("You can access the menu by clicking the 'File' or 'Help' buttons at the top of the screen. The 'File' menu allows you to create a new library, import and export books, and exit the application. The 'Help' menu allows you to access information about the application.");
        Label addbook = new Label("You can add a book to the library by clicking the 'Add' button on the main screen. You will be prompted to enter information about the book, such as title, author, ISBN, etc. Click the 'Add' button on the add book window to add the book to the library.");
        Label editbook = new Label("You can edit a book in the library by selecting the book in the table and clicking the 'Edit' button on the main screen. You will be prompted to edit the information about the book. Click the 'Save' button on the edit book window to save the changes.");
        Label deletebook = new Label("You can delete a book from the library by selecting the book in the table and clicking the 'Delete' button on the main screen. The book will be permanently removed from the library.");
        Label searchbook = new Label("You can search for a book in the library by entering a search term in the search box on the main screen and selecting a search criteria from the dropdown menu. Click the 'Search' button to display the search results in the table.");
        Label resetsearch = new Label("You can reset the search results by clicking the 'Reset Search' button on the main screen. This will display all the books in the library in the table.");

        label.setWrapText(true);
        addbook.setWrapText(true);
        editbook.setWrapText(true);
        deletebook.setWrapText(true);
        searchbook.setWrapText(true);
        resetsearch.setWrapText(true);
        helpLayout.getChildren().addAll(label,addbook,editbook,deletebook,searchbook,resetsearch);

        // Set scene for help window
        Scene helpScene = new Scene(helpLayout, 600, 400);
        helpStage.setScene(helpScene);

        // Show help window
        helpStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}