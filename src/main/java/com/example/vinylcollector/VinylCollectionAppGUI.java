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
import java.util.Set;

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
    private String searchFilter = "";
    private String genreFilter = "Alle";



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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/vinylcollector/appicon_vinyl-disc.png")));

        tableView = createTableView();
        albumCover = createAlbumCover();
        titleTextField = new TextField("Title");
        artistTextField = new TextField("Künstler");
        yearTextField = new TextField("Erscheinungsjahr");
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
        grid.requestFocus();
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

        TableColumn<Vinyl, String> titleCol = new TableColumn<>("Titel");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Vinyl, String> artistCol = new TableColumn<>("Künstler");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Vinyl, Number> yearCol = new TableColumn<>("Erscheinungsjahr");
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

        titleTextField = new TextField();
        titleTextField.setPromptText("Titel");
        titleTextField.getStyleClass().add("neumorphic-field");
        titleTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        titleTextField.setEditable(false);
        grid.add(titleTextField, 0, 0, 2, 1);

        artistTextField = new TextField();
        artistTextField.setPromptText("Künstler");
        artistTextField.getStyleClass().add("neumorphic-field");
        artistTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        artistTextField.setEditable(false);
        grid.add(artistTextField, 0, 1, 2, 1);

        yearTextField = new TextField();
        yearTextField.setPromptText("Erscheinungsjahr");
        yearTextField.getStyleClass().add("neumorphic-field");
        yearTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        yearTextField.setEditable(false);
        grid.add(yearTextField, 0, 2, 2, 1);

        genreTextField = new TextField();
        genreTextField.setPromptText("Genre");
        genreTextField.getStyleClass().add("neumorphic-field");
        genreTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        genreTextField.setEditable(false);
        grid.add(genreTextField, 2, 0, 2, 1);

        spotifyLinkTextField = new TextField();
        spotifyLinkTextField.setPromptText("Spotify Link");
        spotifyLinkTextField.getStyleClass().add("neumorphic-field");
        spotifyLinkTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        spotifyLinkTextField.setEditable(false);
        grid.add(spotifyLinkTextField, 2, 1, 2, 1);

        //Button addCoverButton = new Button("Add Album Cover");
        //addCoverButton.getStyleClass().add("neumorphic-button");
        //grid.add(addCoverButton, 0, 0);
        //grid.add(albumCover, 0, 2);

        //Hinzufügen
        Button addToCollectionButton = new Button("Zur Sammlung hinzufügen");
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

        //Suchleiste
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Suche");
        searchTextField.getStyleClass().add("neumorphic-field");
        grid.add(searchTextField, 0, 6, 2, 1);
        searchTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter = newValue;
            filterTable();
        });

        //Dropdown Menü
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getStyleClass().add("neumorphic-field");
        genreComboBox.setPromptText("Filtern nach Genre");
        grid.add(genreComboBox, 2, 6);

        // Listener für das Dropdown-Menü
        genreComboBox.setOnAction(event -> {
            genreFilter = genreComboBox.getValue();
            filterTable();
        });


        // Fülle das Dropdown-Menü mit den Genres aus der Datenbank
        fillGenreComboBoxFromDatabase(genreComboBox);

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
        Stage addVinylStage = new Stage();
        addVinylStage.setTitle("Vinyl zur Sammlung hinzufügen");
        addVinylStage.initModality(Modality.APPLICATION_MODAL);
        addVinylStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/vinylcollector/appicon_vinyl-disc.png")));

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

        TextField newTitleTextField = new TextField();
        newTitleTextField.setPromptText("Titel");
        newTitleTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        newTitleTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newTitleTextField, 0, 0, 2, 1);
        newTitleTextField.setFocusTraversable(false);

        TextField newArtistTextField = new TextField();
        newArtistTextField.setPromptText("Künstler");
        newArtistTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        newArtistTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newArtistTextField, 0, 1, 2, 1);
        newArtistTextField.setFocusTraversable(false);

        TextField newYearTextField = new TextField();
        newYearTextField.setPromptText("Erscheinungsjahr");
        newYearTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        newYearTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newYearTextField, 0, 2, 2, 1);
        newYearTextField.setFocusTraversable(false);

        TextField newGenreTextField = new TextField();
        newGenreTextField.setPromptText("Genre");
        newGenreTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        newGenreTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newGenreTextField, 2, 0, 2, 1);
        newGenreTextField.setFocusTraversable(false);

        TextField newSpotifyLinkTextField = new TextField();
        newSpotifyLinkTextField.setPromptText("Spotify Link");
        newSpotifyLinkTextField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: black;");
        newSpotifyLinkTextField.getStyleClass().add("neumorphic-field");
        addVinylGrid.add(newSpotifyLinkTextField, 2, 1, 2, 1);
        newSpotifyLinkTextField.setFocusTraversable(false);

        Button cancelButton = new Button("          Abbrechen          ");
        cancelButton.getStyleClass().add("neumorphic-button");
        addVinylGrid.add(cancelButton, 0, 4, 2, 1);
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        cancelButton.setStyle("-fx-background-color: #FFC0CB;");

        Button addButton = new Button("             Hinzufügen             ");
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
        addVinylStage.requestFocus();
    }



    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erfolg");
        alert.setHeaderText(null);
        alert.setContentText("Vinyl erfolgreich in die Datenbank eingetragen!");

        alert.showAndWait();
    }

    private void handleEditButton() {
        Vinyl selectedVinyl = tableView.getSelectionModel().getSelectedItem();

        if (selectedVinyl != null) {
            Stage editVinylStage = new Stage();
            editVinylStage.setTitle("Vinyl bearbeiten");
            editVinylStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/vinylcollector/appicon_vinyl-disc.png")));
            editVinylStage.initModality(Modality.APPLICATION_MODAL);

            GridPane editVinylGrid = new GridPane();
            editVinylGrid.setAlignment(Pos.CENTER);
            editVinylGrid.setHgap(10);
            editVinylGrid.setVgap(15);
            editVinylGrid.setPadding(new Insets(25, 25, 25, 25));
            editVinylGrid.setBackground(new Background(new BackgroundFill(Color.web("#e0e5ec"), CornerRadii.EMPTY, Insets.EMPTY)));

            int numColumnsEdit = 4;
            int numRowsEdit = 6;

            for (int col = 0; col < numColumnsEdit; col++) {
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(100.0 / numColumnsEdit);
                editVinylGrid.getColumnConstraints().add(column);
            }

            for (int row = 0; row < numRowsEdit; row++) {
                RowConstraints rowConstraints = new RowConstraints();
                editVinylGrid.getRowConstraints().add(rowConstraints);
            }

            RowConstraints tableRowConstraints = new RowConstraints();
            tableRowConstraints.setVgrow(Priority.ALWAYS);
            editVinylGrid.getRowConstraints().set(4, tableRowConstraints);

            TextField editTitleTextField = new TextField(selectedVinyl.getTitle());
            editTitleTextField.getStyleClass().add("neumorphic-field");
            editVinylGrid.add(editTitleTextField, 0, 0, 2, 1);
            editTitleTextField.setFocusTraversable(false);

            TextField editArtistTextField = new TextField(selectedVinyl.getArtist());
            editArtistTextField.getStyleClass().add("neumorphic-field");
            editVinylGrid.add(editArtistTextField, 0, 1, 2, 1);
            editArtistTextField.setFocusTraversable(false);

            TextField editYearTextField = new TextField(selectedVinyl.getYear());
            editYearTextField.getStyleClass().add("neumorphic-field");
            editVinylGrid.add(editYearTextField, 0, 2, 2, 1);
            editYearTextField.setFocusTraversable(false);

            TextField editGenreTextField = new TextField(selectedVinyl.getGenre());
            editGenreTextField.getStyleClass().add("neumorphic-field");
            editVinylGrid.add(editGenreTextField, 2, 0, 2, 1);
            editGenreTextField.setFocusTraversable(false);

            TextField editSpotifyLinkTextField = new TextField(selectedVinyl.getSpotifyLink());
            editSpotifyLinkTextField.getStyleClass().add("neumorphic-field");
            editVinylGrid.add(editSpotifyLinkTextField, 2, 1, 2, 1);
            editSpotifyLinkTextField.setFocusTraversable(false);

            Button cancelEditButton = new Button("          Abbrechen          ");
            cancelEditButton.getStyleClass().add("neumorphic-button");
            editVinylGrid.add(cancelEditButton, 0, 4, 2, 1);
            GridPane.setHalignment(cancelEditButton, HPos.CENTER);
            cancelEditButton.setStyle("-fx-background-color: #FFC0CB;");
            cancelEditButton.setOnAction(event -> editVinylStage.close());

            Button saveEditButton = new Button("          Speichern          ");
            saveEditButton.getStyleClass().add("neumorphic-button");
            editVinylGrid.add(saveEditButton, 2, 4, 2, 1);
            GridPane.setHalignment(saveEditButton, HPos.CENTER);
            saveEditButton.setStyle("-fx-background-color: #90EE90;");
            saveEditButton.setOnAction(event -> {
                String editedTitle = editTitleTextField.getText();
                String editedArtist = editArtistTextField.getText();
                String editedYear = editYearTextField.getText();
                String editedGenre = editGenreTextField.getText();
                String editedSpotifyLink = editSpotifyLinkTextField.getText();
                String placeholder = "";

                String[] editedValues = {editedTitle, editedArtist, editedYear, editedGenre, editedSpotifyLink, placeholder};
                VinylDataExchange.writeIntoDataBase(editedValues, selectedVinyl.getID());


                String[][] data = VinylDataExchange.getFullDataTable();
                vinyls.clear();
                fillVinylList(data);

                editVinylStage.close();
            });

            // Setze die Szene und zeige das Edit-Fenster an
            Scene editVinylScene = new Scene(editVinylGrid, 400, 300);
            editVinylScene.getStylesheets().add("file:" + Objects.requireNonNull(getClass().getResource("/com/example/vinylcollector/neumorphic.css")).getPath());
            editVinylStage.setScene(editVinylScene);
            editVinylStage.showAndWait();
            editVinylStage.requestFocus();
        }
    }

    private void handleDeleteButton() {
        // Implementiere die Logik für das Löschen hier
        Vinyl selectedVinyl = tableView.getSelectionModel().getSelectedItem();
        if (selectedVinyl != null) {
            // Frage den Benutzer nach Bestätigung, bevor du das Vinyl löschst
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

            confirmation.setTitle("Löschen");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Möchten Sie dieses Vinyl wirklich löschen?");

            Stage deleteVinyl = (Stage) confirmation.getDialogPane().getScene().getWindow();
            deleteVinyl.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/vinylcollector/appicon_vinyl-disc.png")));

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
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                tableView.getSelectionModel().clearSelection();
                clearTextFields();
                enableEditAndDeleteButtons(false);
            }
        });

        grid.setOnMouseClicked(event -> {
            tableView.getSelectionModel().clearSelection();
            clearTextFields();
            enableEditAndDeleteButtons(false);
        });
    }

    private void clearTextFields() {
        titleTextField.clear();
        artistTextField.clear();
        yearTextField.clear();
        genreTextField.clear();
        spotifyLinkTextField.clear();
        albumCover.setImage(null);
    }

    private void enableEditAndDeleteButtons(boolean enable) {
        editButton.setDisable(!enable);
        deleteButton.setDisable(!enable);
    }

    private void filterTable() {
        ObservableList<Vinyl> filteredList = FXCollections.observableArrayList();

        for (Vinyl vinyl : vinyls) {
            boolean matchesSearchTerm = vinyl.getTitle().toLowerCase().contains(searchFilter.toLowerCase()) ||
                    vinyl.getArtist().toLowerCase().contains(searchFilter.toLowerCase()) ||
                    vinyl.getYear().toLowerCase().contains(searchFilter.toLowerCase()) ||
                    vinyl.getGenre().toLowerCase().contains(searchFilter.toLowerCase()) ||
                    vinyl.getSpotifyLink().toLowerCase().contains(searchFilter.toLowerCase());

            boolean matchesGenre = "Alle".equalsIgnoreCase(genreFilter) || genreFilter.equalsIgnoreCase(vinyl.getGenre());

            if (matchesSearchTerm && matchesGenre) {
                filteredList.add(vinyl);
            }
        }
        tableView.setItems(filteredList);
    }

    private void fillGenreComboBoxFromDatabase(ComboBox<String> genreComboBox) {
        Set<String> uniqueGenres = VinylDataExchange.getGenresFromDatabase();

        // Füge "Alle" als erste Option hinzu
        ObservableList<String> genreList = FXCollections.observableArrayList("Alle");
        genreList.addAll(uniqueGenres);

        genreComboBox.getItems().clear();
        genreComboBox.getItems().addAll(genreList);
    }


}