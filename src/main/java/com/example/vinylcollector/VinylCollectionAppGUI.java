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

    private ObservableList<VinylFX> vinyls = FXCollections.observableArrayList();
    private TableView<VinylFX> tableView;
    private ImageView albumCover;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vinyl Collection App");

        tableView = createTableView();
        albumCover = createAlbumCover();

        GridPane grid = createGrid();
        createForm(grid);

        Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add("file:" + getClass().getResource("/com/example/vinylcollector/neumorphic.css").getPath());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TableView<VinylFX> createTableView() {
        TableView<VinylFX> tableView = new TableView<>();
        TableColumn<VinylFX, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<VinylFX, String> artistCol = new TableColumn<>("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<VinylFX, Number> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        tableView.getColumns().addAll(titleCol, artistCol, yearCol);
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
        Label titleLabel = new Label("Title:");
        TextField titleTextField = new TextField();
        titleTextField.getStyleClass().add("neumorphic-field");
        grid.add(titleLabel, 0, 0);
        grid.add(titleTextField, 1, 0);

        Label artistLabel = new Label("Artist:");
        TextField artistTextField = new TextField();
        artistTextField.getStyleClass().add("neumorphic-field");
        grid.add(artistLabel, 0, 1);
        grid.add(artistTextField, 1, 1);

        Label yearLabel = new Label("Year:");
        TextField yearTextField = new TextField();
        yearTextField.getStyleClass().add("neumorphic-field");
        grid.add(yearLabel, 0, 2);
        grid.add(yearTextField, 1, 2);

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
            int year = Integer.parseInt(yearTextField.getText());

            VinylFX vinyl = new VinylFX(title, artist, year);
            vinyl.setAlbumCover(albumCover.getImage()); // Set the album cover for the vinyl
            vinyls.add(vinyl);

            titleTextField.clear();
            artistTextField.clear();
            yearTextField.clear();
            albumCover.setImage(null); // Clear the album cover after adding the vinyl
        });
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
