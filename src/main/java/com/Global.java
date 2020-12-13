package com;

import com.backEnd.Route;
import com.backEnd.Routes;
import com.frontEnd.Header;
import com.frontEnd.OnCloseRequest;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {
    public static final String extension = ".mydb";
    public final static Label pathCaption = new Label();
    public final static BorderPane tableWithPathCaptionBox = new BorderPane();
    public static String path = "";
    public static Routes mainList = new Routes();
    public static Stage primaryStage = new Stage();
    public static TableView<Route> mainTable = new TableView<>();
    public static boolean changed = false;
    public static OnCloseRequest onCloseRequest = new OnCloseRequest();

    public static void initialize(Node node) {
        mainList.getRoutes().addListener((ListChangeListener<Route>) c -> {
            changed = true;
        });
        primaryStage.setScene(new Scene(new BorderPane(node, new Header().getMainMenuBar(), null, null, null)));
    }

    public static void fromListToFile() {
        StringBuilder content = new StringBuilder();
        for (var el : mainList.getRoutes()) {
            content.append(String.format("id=%d;\n", el.getId()));
            content.append(String.format("name=%s;\n", el.getName()));
            content.append(String.format("type=%s;\n", el.getTypeOfTransport()));
            content.append(String.format("length=%d;\n", el.getLength()));
            content.append(String.format("stops=%d;\n\n", el.getCountOfStops()));
        }
        try (FileWriter writer = new FileWriter(path)) {
            writer.append(content);
            writer.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void fromFileToList() {
        String[] content = new String[0];
        try {
            content = Files
                    .readString(Paths.get(path))
                    .replaceAll("\n", "")
                    .replaceAll("\r", "")
                    .split(";");

        } catch (Exception e) {
            System.out.println("from file to list error: " + e.getMessage());
            e.printStackTrace();
        }

        mainList.clear();
        var j = 0;
        for (var i = 0; i < content.length / 5; i++) {
            Route route = new Route();
            route.setId(Long.parseLong(content[j].substring((content[j].indexOf("=")) + 1)));
            j++;
            route.setName(content[j].substring((content[j].indexOf("=") + 1)));
            j++;
            route.setTypeOfTransport(content[j].substring((content[j].indexOf("=") + 1)));
            j++;
            route.setLength(Integer.parseInt(content[j].substring((content[j].indexOf("=")) + 1)));
            j++;
            route.setCountOfStops(Integer.parseInt(content[j].substring((content[j].indexOf("=")) + 1)));
            j++;
            mainList.add(route);
        }


    }

    public static void newSource() {
        path = "";
        mainList.clear();
        ((Label) tableWithPathCaptionBox.getChildren().get(0)).setText("New database");
        changed = false;
    }

    public static void saveAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save database file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("My Database Files (*" + extension + ")", "*" + extension);
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(savedFileName());
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            path = file.getPath();
            try {
                fromListToFile();
                ((Label) tableWithPathCaptionBox.getChildren().get(0)).setText("File: " + path);
                changed = false;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void openAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open database file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("My Database Files (*" + extension + ")", "*" + extension);
        fileChooser.getExtensionFilters().addAll(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            newSource();
            path = file.getPath();
            fromFileToList();
            ((Label) tableWithPathCaptionBox.getChildren().get(0)).setText("File: " + path);
            changed = false;
        }

    }

    private static String savedFileName() {
        return String.format("database_%s", new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()));
    }

}
