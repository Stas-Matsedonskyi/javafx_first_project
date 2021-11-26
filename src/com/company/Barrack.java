package com.company;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class Barrack {
    private Queue<Creep> batchR = new LinkedList<>();
    private Queue<Creep> batchD = new LinkedList<>();

    private String name;
    protected Group GroupBarrack;
    protected GridPane WrapperBarrack = new GridPane();
    protected Label LabelBarrack = new Label();
    protected Image ImageBarrack;
    protected ImageView ImageViewBarrack;

    public void add(Creep creep, String m) {
        if (m.equals("Radiant")){
            batchR.add(creep);
        }

        else if (m.equals("Dire")){
            batchD.add(creep);
        }
    }

    public Creep getAndRemoveR(){
        return batchR.remove();
    }

    public Creep getAndRemoveD(){
        return batchD.remove();
    }

    public int sizeR(){
        return batchR.size();
    }

    public int sizeD(){
        return batchD.size();
    }

    public void resetBar(){
        for (Creep creep : batchR){
            creep.setActBar(false);
        }

        for (Creep creep1 : batchD){
            creep1.setActBar(false);
        }
    }

    public boolean checkEmptyR(){
        return !batchR.isEmpty();
    }

    public boolean checkEmptyD(){
        return !batchD.isEmpty();
    }

    public String getName(){
        return name;
    }

    public Barrack(String name) throws FileNotFoundException {
        this.name = name;
        double x, y;
        if(name.equals("Radiant")){
            x = 370;
            y = 2400;
            this.WrapperBarrack.getRowConstraints().add(new RowConstraints(100));
            this.WrapperBarrack.getRowConstraints().add(new RowConstraints(500));
            this.WrapperBarrack.getColumnConstraints().add(new ColumnConstraints(500));

            LabelBarrack.setTextFill(Color.BLACK);
            LabelBarrack.setFont(new Font(35));
            LabelBarrack.setText("Radiant Barrack");
            LabelBarrack.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            ImageBarrack = new Image(new FileInputStream("src/sample/radiant_barrack.png"));


        }else{
            x = 3000;
            y = 250;
            this.WrapperBarrack.getRowConstraints().add(new RowConstraints(150));
            this.WrapperBarrack.getRowConstraints().add(new RowConstraints(500));
            this.WrapperBarrack.getColumnConstraints().add(new ColumnConstraints(500));

            LabelBarrack.setTextFill(Color.BLACK);
            LabelBarrack.setFont(new Font(35));
            LabelBarrack.setText("Dire Barrack");
            LabelBarrack.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
            ImageBarrack = new Image(new FileInputStream("src/sample/dire_barrack.png"));

        }
        ImageViewBarrack = new ImageView(ImageBarrack);
        WrapperBarrack.setTranslateX(x);
        WrapperBarrack.setTranslateY(y);
        ImageViewBarrack.setPreserveRatio(true);
        ImageViewBarrack.setFitWidth(600);
        ImageViewBarrack.setFitHeight(600);
        WrapperBarrack.setHalignment(LabelBarrack, HPos.CENTER);
        WrapperBarrack.setValignment(LabelBarrack, VPos.CENTER);
        WrapperBarrack.setHalignment(ImageViewBarrack, HPos.CENTER);
        WrapperBarrack.setValignment(ImageViewBarrack, VPos.CENTER);
//        WrapperBarrack.setGridLinesVisible(true);
        WrapperBarrack.add(LabelBarrack, 0, 0);
        WrapperBarrack.add(ImageViewBarrack, 0, 1);

        LabelBarrack.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        LabelBarrack.setTextFill(Color.BLACK);

        this.GroupBarrack = new Group(WrapperBarrack);
    }


}
