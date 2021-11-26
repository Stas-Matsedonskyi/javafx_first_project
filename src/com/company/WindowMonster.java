package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;

public class WindowMonster {
    public Dialog<Boolean> windowM = new Dialog<Boolean>();
    private Group GroupDialog= new Group();
    private Label LabelDialWindow;
    private GridPane PaneWindow;

    Button buttonO = new Button("OK");
    Button buttonC = new Button("Cancel");

    ToggleGroup buttonGroupR = new ToggleGroup();
    ToggleGroup buttonGroupD = new ToggleGroup();

    public WindowMonster() {
        Label label1 = new Label("Перший монстр");
        Label label2 = new Label("Другий монстр");

        LabelDialWindow = new Label("Оберіть двох монстрів:");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16);
        LabelDialWindow.setFont(font);

        PaneWindow = new GridPane();

        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));

        windowM.getDialogPane().setPrefSize(700, 600);
        windowM.getDialogPane().getChildren().add(PaneWindow);

        PaneWindow.setPadding(new Insets(25, 0, 0, 60));

        PaneWindow.add(label1, 0, 0);
        PaneWindow.add(label2, 3, 0);

        for (int i = 0; i < Main.creepsR.size(); i++) {
            PaneWindow.getRowConstraints().add(new RowConstraints(75));
            Label name = new Label(Main.creepsR.get(i).getName());
            RadioButton button = new RadioButton(Main.creepsR.get(i).getName());
            button.setToggleGroup(buttonGroupR);
            PaneWindow.add(button, 0, i + 1);
        }

        for (int i=0; i<Main.creepsD.size(); i++){
            PaneWindow.getRowConstraints().add(new RowConstraints(75));
            Label name = new Label(Main.creepsD.get(i).getName());
            RadioButton button = new RadioButton(Main.creepsD.get(i).getName());
            button.setToggleGroup(buttonGroupD);
            PaneWindow.add(button, 2, i+1);
        }

        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.add(buttonO, 0, Main.creepsR.size() + 1);
        PaneWindow.add(buttonC, 3, Main.creepsR.size() + 1);

        buttonO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RadioButton RadioSide = (RadioButton)buttonGroupR.getSelectedToggle();

                for (Creep creep : Main.creepsR){
                    if (RadioSide.getText().equals(creep.getName())){
                        try {
                            creep.shiftToMonster();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

                RadioButton RadioSide1 = (RadioButton)buttonGroupD.getSelectedToggle();

                for (Creep creep : Main.creepsD){
                    if (RadioSide1.getText().equals(creep.getName())){
                        try {
                            creep.shiftToMonster();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

                windowM.setResult(Boolean.TRUE);
            }

        });
    }

}
