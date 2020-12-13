package com.frontEnd;

import com.backEnd.Route;
import com.backEnd.Routes;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static com.Global.*;
import static java.lang.Double.MAX_VALUE;

public class MainWindow {
    private final StackPane tableRoot = new StackPane();
    private final BorderPane mainBoxOfElements = new BorderPane();
    private final TableColumn<Route, Long> idColumn = new TableColumn<>("ID");
    private final TableColumn<Route, String> nameColumn = new TableColumn<>("Name");
    private final TableColumn<Route, String> typeColumn = new TableColumn<>("Type of transport");
    private final TableColumn<Route, Integer> lengthColumn = new TableColumn<>("Length (km)");
    private final TableColumn<Route, Integer> stopsColumn = new TableColumn<>("Count of stops");

    public MainWindow() {

        VBox leftBoxOfElements = new VBox();
        VBox rightBoxOfElements = new VBox();

        // Page Name Label ++
        {
            pathCaption.setText("New database");
            pathCaption.setAlignment(Pos.CENTER_LEFT);
            pathCaption.setMaxSize(MAX_VALUE, MAX_VALUE);
            pathCaption.setPadding(new Insets(0, 0, 10, 0));
        }

        // Run Table ++
        {
            var width = 182;
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            idColumn.setPrefWidth(42);
            idColumn.setStyle("-fx-text-alignment: center");

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setPrefWidth(width);

            typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTransport"));
            typeColumn.setPrefWidth(width);

            lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
            lengthColumn.setPrefWidth(width);

            stopsColumn.setCellValueFactory(new PropertyValueFactory<>("countOfStops"));
            stopsColumn.setPrefWidth(width);

            mainTable.setOnMouseClicked(event -> {
                editLine(event);
                remove(event);
            });
            mainTable.setPrefHeight(250);
            mainTable.setEditable(true);
            mainTable.getColumns().add(idColumn);
            mainTable.getColumns().add(nameColumn);
            mainTable.getColumns().add(typeColumn);
            mainTable.getColumns().add(lengthColumn);
            mainTable.getColumns().add(stopsColumn);
            mainTable.setItems(mainList.getRoutes());
            tableRoot.getChildren().add(mainTable);
        }

        // Table With Caption Box ++
        {
            tableWithPathCaptionBox.setTop(pathCaption);
            tableWithPathCaptionBox.setCenter(tableRoot);
            mainBoxOfElements.setTop(tableWithPathCaptionBox);
        }

        // Left Box Of Elements ++
        {
            TextField inputFilter = new TextField();
            TextField inputRemoveCondition = new TextField();
            Button btnFilter = new Button();
            Button btnRemove = new Button();
            Button btnResult1 = new Button();
            Button btnResult2 = new Button();
            HBox filterBox = new HBox();
            HBox removeBox = new HBox();

            //Button Remove ++
            {
                btnRemove.setText("Remove by condition");
                btnRemove.setMaxSize(MAX_VALUE, MAX_VALUE);
                btnRemove.setOnAction(event -> {
                    if (!inputRemoveCondition.getText().equals("") && mainList != null && !mainList.getRoutes().isEmpty()) {
                        mainList.removeDownThen(Integer.parseInt(inputRemoveCondition.getText()));
                        inputRemoveCondition.clear();
                    }
                });
            }

            //Button Filter ++
            {
                btnFilter.setText("Apply filter");
                btnFilter.setPrefWidth(129);
                btnFilter.setMaxSize(MAX_VALUE, MAX_VALUE);
                btnFilter.setOnAction(event -> {
                    if (mainList != null && !mainList.getRoutes().isEmpty()) {
                        addToTable(mainList.filter(inputFilter.getText()));
                    }
                });

            }

            //Input Filter ++
            {
                inputFilter.setPromptText("Enter filter");
                inputFilter.setPrefWidth(250);
                inputFilter.setMaxSize(MAX_VALUE, MAX_VALUE);
                inputFilter.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        btnFilter.getOnAction().handle(new ActionEvent());
                    }
                });
            }

            //Remove Condition ++
            {
                inputRemoveCondition.setPromptText("Enter remove condition");
                inputRemoveCondition.setPrefWidth(250);
                inputRemoveCondition.setMaxSize(MAX_VALUE, MAX_VALUE);
                inputRemoveCondition.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) btnRemove.getOnAction().handle(new ActionEvent());
                });
                inputRemoveCondition.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        inputRemoveCondition.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }

            //Button Result1 ++
            {

                btnResult1.setText("Result 1");
                btnResult1.setMaxSize(MAX_VALUE, MAX_VALUE);
                btnResult1.setOnAction(event -> resultOne());
            }

            //Button Result2 ++
            {
                btnResult2.setText("Result2");
                btnResult2.setMaxSize(MAX_VALUE, MAX_VALUE);
                btnResult2.setOnAction(event -> {
                    if (mainList != null && !mainList.getRoutes().isEmpty()) {
                        String result = String.format("Quantity of routes: %d", mainList.resultTwo());
                        Label outputText = new Label(result);
//                        outputText.setStyle("-fx-font-size: 14");
                        outputText.setPadding(new Insets(10));
                        ModalWindow modalWindow = new ModalWindow();
                        modalWindow.setMainWorkSpace(outputText);
                        modalWindow.show();
                    }
                });
            }

            // Filter Box ++
            {
                filterBox.setSpacing(10);
                filterBox.setAlignment(Pos.CENTER);
                filterBox.getChildren().setAll(inputFilter, btnFilter);
                filterBox.setPadding(new Insets(10, 0, 0, 0));
            }

            // Remove Box ++
            {
                removeBox.setSpacing(10);
                removeBox.setAlignment(Pos.CENTER);
                removeBox.getChildren().setAll(inputRemoveCondition, btnRemove);
            }

            leftBoxOfElements.setMaxSize(MAX_VALUE, MAX_VALUE);
            leftBoxOfElements.setSpacing(10);
            leftBoxOfElements.setPrefWidth(390);
            leftBoxOfElements.setPadding(new Insets(0, 5, 0, 0));
            leftBoxOfElements.getChildren().setAll(filterBox, removeBox, btnResult1, btnResult2);
        }

        // Right Box Of Elements ++
        {
            Button sortingBtn = new Button("Sorting");
            BorderPane selectionBox = new BorderPane();
            Button applyBtn = new Button("Apply pop property");
            TextField getMinField = new TextField();
            TextField getMaxField = new TextField();
            Label captionOfBox = new Label("Enter interval count of stops");

            // Sorting Button ++
            {
                sortingBtn.setMaxSize(MAX_VALUE, MAX_VALUE);
                sortingBtn.setOnAction(event -> {
                    if (mainList != null && !mainList.getRoutes().isEmpty()) {
                        mainList.sortByType();
                    }
                });
            }

            // Caption of box ++
            {
                captionOfBox.setStyle("-fx-font-size: 14");
                captionOfBox.setTextAlignment(TextAlignment.CENTER);
                captionOfBox.setAlignment(Pos.CENTER);
                captionOfBox.setPadding(new Insets(10, 0, 10, 0));
                captionOfBox.setMaxSize(MAX_VALUE, MAX_VALUE);
            }

            // Min ++
            {
                getMinField.setPromptText("Min");
                getMinField.setMaxSize(MAX_VALUE, MAX_VALUE);
                getMinField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        getMinField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }

            // Max ++
            {
                getMaxField.setPromptText("Max");
                getMaxField.setMaxSize(MAX_VALUE, MAX_VALUE);
                getMaxField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        getMaxField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                getMaxField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) applyBtn.getOnAction().handle(new ActionEvent());
                });
            }

            // Apply Button ++
            {
                applyBtn.setMaxSize(MAX_VALUE, MAX_VALUE);
                applyBtn.setOnAction(event -> {
                    if (mainList != null && !mainList.getRoutes().isEmpty() && !getMaxField.getText().equals("") && !getMinField.getText().equals("")) {
                        addToTable(mainList.betweenStops(Integer.parseInt(getMinField.getText()), Integer.parseInt(getMaxField.getText())));
                        getMinField.clear();
                        getMaxField.clear();
                    }
                });

            }

            // Min Max Fields Box ++
            {
                HBox minMaxFieldsBox = new HBox(10);
                {
                    minMaxFieldsBox.getChildren().setAll(getMinField, getMaxField);
                    minMaxFieldsBox.setAlignment(Pos.CENTER);
                    minMaxFieldsBox.setPadding(new Insets(0, 0, 10, 0));
                }

                selectionBox.setTop(captionOfBox);
                selectionBox.setCenter(minMaxFieldsBox);
                selectionBox.setBottom(applyBtn);
            }

            rightBoxOfElements.getChildren().addAll(sortingBtn, selectionBox);
            rightBoxOfElements.setSpacing(6);
            rightBoxOfElements.setMaxSize(MAX_VALUE, MAX_VALUE);
            rightBoxOfElements.setPrefWidth(390);
            rightBoxOfElements.setPadding(new Insets(10, 0, 10, 5));

        }

        // Form of adding element and show all button ++
        {
            var width = 153;

            HBox hBox = new HBox();
            TextField nameField = new TextField();
            TextField typeField = new TextField();
            TextField lengthField = new TextField();
            TextField stopsField = new TextField();
            Button showAll = new Button("Show All");
            Button commit = new Button("Add element to table");
            VBox vBox = new VBox();

            nameField.setPromptText("Enter name");
            nameField.setMaxSize(MAX_VALUE, MAX_VALUE);
            nameField.setPrefWidth(width);

            typeField.setPromptText("Enter type of transport");
            typeField.setMaxSize(MAX_VALUE, MAX_VALUE);
            typeField.setPrefWidth(width);

            lengthField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    lengthField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            lengthField.setPromptText("Enter length");
            lengthField.setMaxSize(MAX_VALUE, MAX_VALUE);
            lengthField.setPrefWidth(width);

            stopsField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    stopsField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            stopsField.setPromptText("Enter stops");
            stopsField.setMaxSize(MAX_VALUE, MAX_VALUE);
            stopsField.setPrefWidth(width);
            stopsField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) commit.getOnAction().handle(new ActionEvent());
            });

            // Show all button
            {
                showAll.setOnAction(event -> {
                    if (mainList != null) {
                        addToTable(mainList);
                    }
                });
                showAll.setTooltip(new Tooltip("Click to show all\nelements from database"));
                showAll.setMaxSize(MAX_VALUE, MAX_VALUE);
            }

            commit.setOnAction(event -> {
                if (mainList != null && !nameField.getText().equals("") && !typeField.getText().equals("") && !lengthField.getText().equals("") && !stopsField.getText().equals("")) {
                    mainList.add(new Route(nameField.getText(), typeField.getText(), Integer.parseInt(lengthField.getText()), Integer.parseInt(stopsField.getText())));
                    nameField.clear();
                    typeField.clear();
                    lengthField.clear();
                    stopsField.clear();
                }
            });
            commit.setMaxSize(MAX_VALUE, MAX_VALUE);

            hBox.setSpacing(10);
            hBox.setMaxSize(MAX_VALUE, MAX_VALUE);
            hBox.getChildren().addAll(nameField, typeField, lengthField, stopsField, commit);

            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10, 0, 0, 0));
            vBox.getChildren().setAll(showAll, hBox);

            mainBoxOfElements.setCenter(vBox);
        }

        mainBoxOfElements.setBottom(new HBox(10, leftBoxOfElements, rightBoxOfElements));
        mainBoxOfElements.setPadding(new Insets(10));
    }

    public BorderPane getMainBoxOfElements() {
        return mainBoxOfElements;
    }

    private void addToTable(Routes routes) {
        mainTable.setItems(routes.getRoutes());
    }

    public void editLine(MouseEvent mouseEvent) {
        Route currentRoute = mainTable.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY && currentRoute != null) {
            HBox inputFieldsBox = new HBox();
            VBox updateBox = new VBox();
            TextField editId = new TextField();
            TextField editName = new TextField();
            TextField editType = new TextField();
            TextField editLength = new TextField();
            TextField editStops = new TextField();
            Button commit = new Button();

            updateBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    tableRoot.getChildren().clear();
                    tableRoot.getChildren().add(mainTable);
                    addToTable(mainList);
                }
            });

            editId.setPrefWidth(idColumn.getWidth() + 1);
            editId.setDisable(true);
            editId.setStyle("-fx-opacity: 1; -fx-text-fill: #bbbbbb");
            editId.setText(String.valueOf(currentRoute.getId()));

            editName.setPrefWidth(nameColumn.getWidth());
            editName.setText(currentRoute.getName());

            editType.setPrefWidth(typeColumn.getWidth());
            editType.setText(currentRoute.getTypeOfTransport());

            editLength.setPrefWidth(lengthColumn.getWidth());
            editLength.setText(String.valueOf(currentRoute.getLength()));
            editLength.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    editLength.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            editStops.setPrefWidth(stopsColumn.getWidth());
            editStops.setText(String.valueOf(currentRoute.getCountOfStops()));
            editStops.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    editStops.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            commit.setTextAlignment(TextAlignment.CENTER);
            commit.setText("Commit");
            commit.setOnAction(event -> {
                if (!editName.getText().equals("") && !editType.getText().equals("") && !editLength.getText().equals("") && !editStops.getText().equals("")) {
                    var id = Long.parseLong(editId.getText());
                    mainList.getById(id).setName(editName.getText());
                    mainList.getById(id).setTypeOfTransport(editType.getText());
                    mainList.getById(id).setLength(Integer.parseInt(editLength.getText()));
                    mainList.getById(id).setCountOfStops(Integer.parseInt(editStops.getText()));
                    tableRoot.getChildren().clear();
                    tableRoot.getChildren().add(mainTable);
                }
            });
            commit.setMaxWidth(MAX_VALUE);

            inputFieldsBox.getChildren().addAll(editId, editName, editType, editLength, editStops);
            inputFieldsBox.isFocused();
            updateBox.getChildren().addAll(inputFieldsBox, commit);
            tableRoot.getChildren().clear();
            tableRoot.getChildren().addAll(updateBox, mainTable);

            StackPane.setMargin(updateBox, new Insets(mouseEvent.getY(), mainBoxOfElements.getPadding().getRight(), 0, 0));
            updateBox.toFront();
        }
    }

    public void remove(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            Route currentRoute = mainTable.getSelectionModel().getSelectedItem();
            if (currentRoute != null) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem(String.format("Remove %s's element", currentRoute.getId()));
                removeItem.setOnAction(event1 -> mainList.remove(currentRoute.getId()));
                contextMenu.getItems().add(removeItem);
                mainTable.setContextMenu(contextMenu);
            }
        }

    }

    public void resultOne() {

        VBox typeScrollPane = new VBox(new Label("Type of transport"));
        VBox minScrollPane = new VBox(new Label("Min count of stops"));
        VBox maxScrollPane = new VBox(new Label("Max count of stops"));
        ScrollPane fullScrollPane = new ScrollPane(new HBox(10));

        for (var el : mainList.resultOne()) {
            typeScrollPane.getChildren().add(new Label(el.getType()));
            minScrollPane.getChildren().add(new Label(el.getMin() + " km"));
            maxScrollPane.getChildren().add(new Label(el.getMax() + " km"));
        }
        ((HBox) fullScrollPane.getContent()).getChildren().addAll(typeScrollPane, minScrollPane, maxScrollPane);

        ModalWindow window = new ModalWindow();
        window.setMainWorkSpace(fullScrollPane);
        window.show();
    }
}
