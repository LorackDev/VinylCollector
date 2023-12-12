package com.example.vinylcollector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class VinylCollectionAppGUI extends Application {

    private ObservableList<Vinyl> vinyls = FXCollections.observableArrayList();
    private TableView<Vinyl> tableView;
    private ImageView albumCover;

    /**
     * starts start() function
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vinyl Collection App");

        tableView = createTableView();
        albumCover = createAlbumCover();

        GridPane grid = createGrid();
        MenuBar menuBar = createMenuBar();
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(menuBar);
        borderPane.setCenter(grid);
        createForm(grid);

        Scene scene = new Scene(borderPane, 800, 600);
        //Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add("file:" + getClass().getResource("/com/example/vinylcollector/neumorphic.css").getPath());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Tabelle
    private TableView<Vinyl> createTableView() {
        TableView<Vinyl> tableView = new TableView<>();
        TableColumn<Vinyl, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Vinyl, String> artistCol = new TableColumn<>("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Vinyl, Number> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Vinyl, Number> genreCol = new TableColumn<>("Genre");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn<Vinyl, Number> spotifyLinkCol = new TableColumn<>("Spotify Link");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("spotifyLink"));

        tableView.getColumns().addAll(titleCol, artistCol, yearCol, genreCol, spotifyLinkCol);
        tableView.setItems(vinyls);

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
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.web("#e0e5ec"), CornerRadii.EMPTY, Insets.EMPTY)));
        return grid;
    }

    private void createForm(GridPane grid) {

        TextField titleTextField = new TextField("Title");
        titleTextField.getStyleClass().add("neumorphic-field");
        grid.add(titleTextField, 1, 0);

        TextField artistTextField = new TextField("Artist");
        artistTextField.getStyleClass().add("neumorphic-field");
        grid.add(artistTextField, 1, 1);

        TextField yearTextField = new TextField("Year");
        yearTextField.getStyleClass().add("neumorphic-field");
        grid.add(yearTextField, 1, 2);

        TextField genreTextField = new TextField("Genre");
        genreTextField.getStyleClass().add("neumorphic-field");
        grid.add(genreTextField, 2, 0);

        TextField spotifyLinkTextField = new TextField("Spotify Link");
        spotifyLinkTextField.getStyleClass().add("neumorphic-field");
        grid.add(spotifyLinkTextField, 2, 1);

        Button addCoverButton = new Button("Add Album Cover");
        addCoverButton.getStyleClass().add("neumorphic-button");
        grid.add(addCoverButton, 1, 3);
        grid.add(albumCover, 1, 4);

        Button addButton = new Button("Add to Collection");
        addButton.getStyleClass().add("neumorphic-button");
        grid.add(addButton, 1, 5);

        grid.add(tableView, 0, 6, 2, 1);

        addCoverButton.setOnAction(event -> addAlbumCover());
        addButton.setOnAction(event -> {
            String title = titleTextField.getText();
            String artist = artistTextField.getText();
            String genre = artistTextField.getText();
            String spotifyLink = artistTextField.getText();
            String imagePath = artistTextField.getText();
            int year = Integer.parseInt(yearTextField.getText());

            Vinyl vinyl = new Vinyl(title, artist, year, genre, spotifyLink, imagePath);
            vinyl.setAlbumCover(albumCover.getImage()); // Set the album cover for the vinyl
            vinyls.add(vinyl);

            titleTextField.clear();
            artistTextField.clear();
            yearTextField.clear();
            albumCover.setImage(null); // Clear the album cover after adding the vinyl
        });
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu myListMenu = new Menu("Meine Liste");
        Menu statsMenu = new Menu("Statistiken");

        myListMenu.setOnAction(event -> {
            System.out.println("My List selected");
        });

        statsMenu.setOnAction(event -> {
            System.out.println("Stats selected");
        });

        // Add "File" menu to the menu bar
        menuBar.getMenus().add(myListMenu);
        menuBar.getMenus().add(statsMenu);

        return menuBar;
    }


    private void addAlbumCover() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            albumCover.setImage(image);
        }
    }
}
