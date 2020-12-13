package com.frontEnd;

import com.Global;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalWindow {

    private final Stage STAGE = new Stage();

    public ModalWindow() {
        Scene scene;
        BorderPane mainWorkSpace;
        Button closeBtn;

        Effect blend = new Blend();
        Effect blur = new GaussianBlur();

        closeBtn = new Button("Cancel");
        closeBtn.setOnAction(event -> {
            Global.primaryStage.getScene().getRoot().setEffect(blend);
            close();
        });

        double x = Global.primaryStage.getX() + 7;
        double y = Global.primaryStage.getY() + 30;
        double width = Global.primaryStage.getWidth() - 15;
        double height = Global.primaryStage.getHeight() - 37;

        mainWorkSpace = new BorderPane();
        mainWorkSpace.setBottom(closeBtn);
        mainWorkSpace.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) closeBtn.getOnAction().handle(new ActionEvent());
        });
        mainWorkSpace.setPadding(new Insets(10));
        BorderPane.setAlignment(closeBtn, Pos.CENTER);

        scene = new Scene(mainWorkSpace);
        scene.setRoot(mainWorkSpace);
        scene.setUserAgentStylesheet("/com/style.css");

        this.STAGE.setScene(scene);
        this.STAGE.initModality(Modality.WINDOW_MODAL);
        this.STAGE.getIcons().addAll(Global.primaryStage.getIcons());
        this.STAGE.initOwner(Global.primaryStage);
        this.STAGE.setWidth(Global.primaryStage.getWidth());
        this.STAGE.setHeight(Global.primaryStage.getHeight());
        this.STAGE.setResizable(false);
        this.STAGE.setOpacity(0.7);
        this.STAGE.setWidth(width);
        this.STAGE.setHeight(height);
        this.STAGE.setX(x);
        this.STAGE.setY(y);
        this.STAGE.initModality(Modality.APPLICATION_MODAL);
        this.STAGE.initStyle(StageStyle.UNDECORATED);

        Global.primaryStage.getScene().getRoot().setEffect(blur);
    }

    public void setMainWorkSpace(Node node) {
        ((BorderPane) this.STAGE.getScene().getRoot()).setCenter(node);
    }

    public void close() {
        this.STAGE.close();
    }

    public void show() {
        this.STAGE.show();
    }

}
