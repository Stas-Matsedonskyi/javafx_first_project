package com.company;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;


public class Window {
    public Dialog<Boolean> dialog = new Dialog<Boolean>();
    private Group GroupDialog= new Group();
    private Label LabelDialWindow;
    private GridPane PaneWindow;

    Label name, health, damage, speed, team, level;
    TextField TextFieldName, TextFieldHealth,TextFieldSpeed, TextFieldDamage;
    Button button = new Button("OK");
    RadioButton buttonRadiant = new RadioButton("Radiant");
    RadioButton buttonDire = new RadioButton("Dire");
    ToggleGroup buttonGroup = new ToggleGroup();
    ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("First level", "Second level", "Third level"));

    public Window() {

        buttonDire.setToggleGroup(buttonGroup);
        buttonRadiant.setToggleGroup(buttonGroup);

        name = new Label("name");
        health = new Label("health");
        damage = new Label("damage");
        speed = new Label("speed");
        team = new Label("team");
        level = new Label("level");

        TextFieldName = new TextField();
        TextFieldHealth = new TextField();
        TextFieldDamage = new TextField();
        TextFieldSpeed = new TextField();

        GroupDialog = new Group();
        PaneWindow = new GridPane();

        LabelDialWindow = new Label("New creep:");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16);
        LabelDialWindow.setFont(font);
        TextField textField = new TextField();
        textField.maxWidth(150.0);

        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));
        PaneWindow.getRowConstraints().add(new RowConstraints(75));

        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));
        PaneWindow.getColumnConstraints().add(new ColumnConstraints(120));

        dialog.getDialogPane().setPrefSize(700, 600);
        dialog.getDialogPane().getChildren().add(PaneWindow);

        PaneWindow.setPadding(new Insets(25, 0, 0, 60));

        PaneWindow.add(name, 0, 0);
        PaneWindow.add(TextFieldName, 1, 0, 3, 1);

        PaneWindow.add(health, 0, 1);
        PaneWindow.add(TextFieldHealth, 1, 1, 3, 1);

        PaneWindow.add(damage, 0, 2);
        PaneWindow.add(TextFieldDamage, 1, 2, 3, 1);

        PaneWindow.add(speed, 0, 3);
        PaneWindow.add(TextFieldSpeed, 1, 3, 3, 1);

        PaneWindow.add(level, 0, 4);
        PaneWindow.add(choiceBox, 1, 4, 3, 1);

        PaneWindow.add(team, 0, 5);
        PaneWindow.add(buttonRadiant, 1, 5);
        PaneWindow.add(buttonDire, 2, 5);

        PaneWindow.add(button, 2, 6);


        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                RadioButton RadioSide = (RadioButton)buttonGroup.getSelectedToggle();
                int lvl = choiceBox.getSelectionModel().getSelectedIndex();

                Creep creep = null;
                try {
                    if(RadioSide.getText().equals("Radiant")){
                        if(lvl == 0){
                            creep = new Creep(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Radiant");
                        }else if(lvl == 1){
                            creep = new Melee(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Radiant", 4);
                        }else if(lvl == 2){
                            creep = new Ranged(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Radiant", 2, 200);
                        }
                        Main.creepsR.add(creep);
                    }else if(RadioSide.getText().equals("Dire")){
                        if(lvl == 0){
                            creep = new Creep(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Dire");
                        }else if(lvl == 1){
                            creep = new Melee(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Dire", 4);
                        }else if(lvl == 2){
                            creep = new Ranged(Integer.parseInt(TextFieldHealth.getText()), Double.parseDouble(TextFieldSpeed.getText()),
                                    TextFieldName.getText(), Integer.parseInt(TextFieldDamage.getText()), "Dire", 2, 200);
                        }
                        Main.creepsD.add(creep);
                    }
                    Main.map.addUnit(creep);

                    Main.group.getChildren().add(creep.creeps);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.setResult(Boolean.TRUE);
            }

        });

    }



    public Scene returnDialogScene(){
        Scene scene = new Scene(GroupDialog, 1280, 720, Color.LIGHTGREY);
        return scene;
    }

}
