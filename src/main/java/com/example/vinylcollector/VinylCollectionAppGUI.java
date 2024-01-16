package com.example.vinylcollector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class VinylCollectionAppGUI extends Application {

    private final ObservableList<Vinyl> vinyls = FXCollections.observableArrayList();
    private TableView<Vinyl> tableView;
    private ImageView albumCover;
    private TextField titleTextField;
    private TextField artistTextField;
    private TextField yearTextField;
    private TextField genreTextField;
    private TextField spotifyLinkTextField;
    private Button editButton;
    private Button deleteButton;

    /**
     * starts start() function
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vinyl Collection App");

        tableView = createTableView();
        albumCover = createAlbumCover();
        titleTextField = new TextField("Title");
        artistTextField = new TextField("Artist");
        yearTextField = new TextField("Year");
        genreTextField = new TextField("Genre");
        spotifyLinkTextField = new TextField("Spotify Link");

        GridPane grid = createGrid();
        MenuBar menuBar = createMenuBar();
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(menuBar);
        borderPane.setCenter(grid);
        createForm(grid);

        setupTableSelectionListener();
        setupTableDeselectionListener(grid);

        // Lade Daten aus der Datenbank
        String[][] data = VinylDataExchange.getFullDataTable();
        fillVinylList(data);

        Scene scene = new Scene(borderPane, 1200, 1000);
        scene.getStylesheets().add("file:" + Objects.requireNonNull(getClass().getResource("/com/example/vinylcollector/neumorphic.css")).getPath());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Methode zum Befüllen der vinyls-Liste
    private void fillVinylList(String[][] data) {
        if (data != null) {
            for (String[] row : data) {
                String id = row[0];
                String title = row[1];
                String artist = row[2];
                String year = row[3];
                String genre = row[4];
                String spotifyLink = row[5];
                String imagePath = row[6];

                Vinyl vinyl = new Vinyl(id, title, artist, year, genre, spotifyLink, imagePath);
                vinyls.add(vinyl);
            }
        }
    }

    // Tabelle
    private TableView<Vinyl> createTableView() {
        TableView<Vinyl> tableView = new TableView<>();

        TableColumn<Vinyl, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Vinyl, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Vinyl, String> artistCol = new TableColumn<>("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Vinyl, Number> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Vinyl, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Vinyl, String> spotifyLinkCol = new TableColumn<>("Spotify Link");
        spotifyLinkCol.setCellValueFactory(new PropertyValueFactory<>("spotifyLink"));

        tableView.getColumns().addAll(idCol, titleCol, artistCol, yearCol, genreCol, spotifyLinkCol);
        tableView.setItems(vinyls);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    private ImageView createAlbumCover() {
        ImageView albumCover = new ImageView();
        albumCover.setFitWidth(200);
        albumCover.setFitHeight(200);
        albumCover.getStyleClass().add("neumorphic-image-view");
        return albumCover;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.web("#e0e5ec"), CornerRadii.EMPTY, Insets.EMPTY)));
        grid.getStylesheets().add("file:" + Objects.requireNonNull(getClass().getResource("/com/example/vinylcollector/neumorphic.css")).getPath());
        int numColumns = 4;
        int numRows = 10;

        for (int col = 0; col < numColumns; col++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / numColumns);
            grid.getColumnConstraints().add(column);
        }

        for (int row = 0; row < numRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            grid.getRowConstraints().add(rowConstraints);
        }

        RowConstraints tableRowConstraints = new RowConstraints();
        tableRowConstraints.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().set(7, tableRowConstraints);

        return grid;
    }

    private void createForm(GridPane grid) {

        titleTextField = new TextField("Title");
        titleTextField.getStyleClass().add("neumorphic-field");
        titleTextField.setEditable(false);
        grid.add(titleTextField, 0, 0, 2, 1);

        artistTextField = new TextField("Artist");
        artistTextField.getStyleClass().add("neumorphic-field");
        artistTextField.setEditable(false);
        grid.add(artistTextField, 0, 1, 2, 1);

        yearTextField = new TextField("Year");
        yearTextField.getStyleClass().add("neumorphic-field");
        yearTextField.setEditable(false);
        grid.add(yearTextField, 0, 2, 2, 1);

        genreTextField = new TextField("Genre");
        genreTextField.getStyleClass().add("neumorphic-field");
        genreTextField.setEditable(false);
        grid.add(genreTextField, 2, 0, 2, 1);

        spotifyLinkTextField = new TextField("Spotify Link");
        spotifyLinkTextField.getStyleClass().add("neumorphic-field");
        spotifyLinkTextField.setEditable(false);
        grid.add(spotifyLinkTextField, 2, 1, 2, 1);

        //Button addCoverButton = new Button("Add Album Cover");
        //addCoverButton.getStyleClass().add("neumorphic-button");
        //grid.add(addCoverButton, 0, 0);
        //grid.add(albumCover, 0, 2);

        Button addToCollectionButton = new Button("Add to Collection");
        addToCollectionButton.getStyleClass().add("neumorphic-button");
        grid.add(addToCollectionButton, 0, 5);
        GridPane.setHalignment(addToCollectionButton, HPos.CENTER);
        addToCollectionButton.setOnAction(event -> openAddVinylWindow());

        // Neue Knöpfe für Bearbeiten und Löschen
        editButton = new Button("Bearbeiten");
        editButton.getStyleClass().add("neumorphic-button");
        grid.add(editButton, 1, 5);
        GridPane.setHalignment(editButton, HPos.CENTER);
        editButton.setOnAction(event -> handleEditButton());
        editButton.setDisable(true);

        deleteButton = new Button("Vinyl Löschen");
        deleteButton.getStyleClass().add("neumorphic-button");
        grid.add(deleteButton, 2, 5);
        GridPane.setHalignment(deleteButton, HPos.CENTER);
        deleteButton.setOnAction(event -> handleDeleteButton());
        deleteButton.setDisable(true);

        //addCoverButton.setOnAction(event -> addAlbumCover());

        grid.add(tableView, 0, 7, 4, 2);

    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu optionsMenu = new Menu("Optionen");
        Menu myListMenu = new Menu("Meine Liste");
        Menu statsMenu = new Menu("Statistiken");

        myListMenu.setOnAction(event -> {
            System.out.println("My List selected");
        });

        statsMenu.setOnAction(event -> {
            System.out.println("Stats selected");
        });

        // Add "File" menu to the menu bar
        menuBar.getMenus().add(optionsMenu);
        menuBar.getMenus().add(myListMenu);
        menuBar.getMenus().add(statsMenu);

        return menuBar;
    }

    /**
     * Not implemented
     */
    private void addAlbumCover() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            albumCover.setImage(image);
        }
    }

    private void openAddVinylWindow() {
        // Neues Fenster erstellen
        Stage addVinylStage = new Stage();
        addVinylStage.setTitle("Add Vinyl to Collection");
        addVinylStage.initModality(Modality.APPLICATION_MODAL);

        // Neues GridPane für das Formular im neuen Fenster
        GridPane addVinylGrid = new GridPane();
        addVinylGrid.setAlignment(Pos.CENTER);
        addVinylGrid.setHgap(10);
        addVinylGrid.setVgap(15);
        addVinylGrid.setPadding(new Insets(25, 25, 25, 25));
        addVinylGrid.setBackground(new Background(new BackgroundFill(Color.web("#e0e5ec"), CornerRadii.EMPTY, Insets.EMPTY)));
        int numColumnsAdd = 4;
        int numRowsAdd = 6;

        for (int col = 0; col < numColumnsAdd; col++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / numColumnsAdd);
            addVinylGrid.getColumnConstraints().add(column);
        }

        for (int row = 0; row < numRowsAdd; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            addVinylGrid.getRowConstraints().add(rowConstraints);
        }

        RowConstraints tableRowConstraints = new RowConstraints();
        tableRowConstraints.setVgrow(Priority.ALWAYS);
        addVinylGrid.getRowConstraints().set(4, tableRowConstraints);

        TextField newTitleTextField = new TextField("Title");
        newTitleTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newTitleTextField, 0, 0, 2, 1);

        TextField newArtistTextField = new TextField("Artist");
        newArtistTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newArtistTextField, 0, 1, 2, 1);

        TextField newYearTextField = new TextField("Year");
        newYearTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newYearTextField, 0, 2, 2, 1);

        TextField newGenreTextField = new TextField("Genre");
        newGenreTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newGenreTextField, 2, 0, 2, 1);

        TextField newSpotifyLinkTextField = new TextField("Spotify Link");
        newSpotifyLinkTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newSpotifyLinkTextField, 2, 1, 2, 1);

        Button cancelButton = new Button("          Cancel          ");
        cancelButton.getStyleClass().add("neumorphic-button");
        addVinylGrid.add(cancelButton, 0, 4, 2, 1);
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        cancelButton.setStyle("-fx-background-color: #FFC0CB;");

        Button addButton = new Button("             Add             ");
        addButton.getStyleClass().add("neumorphic-button");
        addVinylGrid.add(addButton, 2, 4, 2, 1);
        GridPane.setHalignment(addButton, HPos.CENTER);
        addButton.setStyle("-fx-background-color: #90EE90;");

        Scene addVinylScene = new Scene(addVinylGrid, 400, 300);
        addVinylScene.getStylesheets().add("file:" + Objects.requireNonNull(getClass().getResource("/com/example/vinylcollector/neumorphic.css")).getPath());

        cancelButton.setOnAction(event -> addVinylStage.close());
        addButton.setOnAction(event -> {
            String newTitle = newTitleTextField.getText();
            String newArtist = newArtistTextField.getText();
            String newYear = newYearTextField.getText();
            String newGenre = newGenreTextField.getText();
            String newSpotifyLink = newSpotifyLinkTextField.getText();
            String imagePath = "";

            String[] values = {newTitle, newArtist, newYear, newGenre, newSpotifyLink, imagePath};
            VinylDataExchange.writeIntoDataBase(values);

            String[][] data = VinylDataExchange.getFullDataTable();
            vinyls.clear();
            fillVinylList(data);

            showSuccessDialog();

            addVinylStage.close();
        });

        addVinylStage.setScene(addVinylScene);
        addVinylStage.showAndWait();
    }


    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erfolg");
        alert.setHeaderText(null);
        alert.setContentText("Vinyl erfolgreich in die Datenbank eingetragen!");

        alert.showAndWait();
    }

    private void handleEditButton() {
        // Implementiere die Logik für das Bearbeiten hier
        Vinyl selectedVinyl = tableView.getSelectionModel().getSelectedItem();
        if (selectedVinyl != null) {
            // Öffne ein Bearbeitungsfenster oder fülle das Formular mit den ausgewählten Werten
            // Je nach Anforderungen deiner Anwendung
        }
    }

    private void handleDeleteButton() {
        // Implementiere die Logik für das Löschen hier
        Vinyl selectedVinyl = tableView.getSelectionModel().getSelectedItem();
        if (selectedVinyl != null) {
            // Frage den Benutzer nach Bestätigung, bevor du das Vinyl löschst
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Bestätigung");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Möchten Sie dieses Vinyl wirklich löschen?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Führe die Löschaktion durch
                VinylDataExchange.deleteFromDatabase(selectedVinyl.getID());

                // Aktualisiere die Datenbankanzeige
                String[][] data = VinylDataExchange.getFullDataTable();
                vinyls.clear(); // Lösche die vorhandenen Vinyls
                fillVinylList(data); // Fülle die Liste mit aktualisierten Daten
            }
        }
    }
    private void setupTableSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Setze die Werte der ausgewählten Vinyl in die Textfelder
                titleTextField.setText(newValue.getTitle());
                artistTextField.setText(newValue.getArtist());
                yearTextField.setText(newValue.getYear());
                genreTextField.setText(newValue.getGenre());
                spotifyLinkTextField.setText(newValue.getSpotifyLink());

                // Zeige das Albumcover an
                if (newValue.getAlbumCover() != null) {
                    albumCover.setImage(newValue.getAlbumCover());
                } else {
                    albumCover.setImage(null);
                }

                enableEditAndDeleteButtons(true);
            } else {
                enableEditAndDeleteButtons(false);
            }
        });
    }
    private void setupTableDeselectionListener(GridPane grid) {
        grid.setOnMouseClicked(event -> {
            // Deselect the table row when clicking outside the table
            tableView.getSelectionModel().clearSelection();

            // Disable edit and delete buttons when there is no selection
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        });
    }

    private void enableEditAndDeleteButtons(boolean enable) {
        editButton.setDisable(!enable);
        deleteButton.setDisable(!enable);
    }

}