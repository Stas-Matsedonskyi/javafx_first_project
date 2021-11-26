package com.company;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tower {

    private String side;
    double x, y;
    protected Group groupTower;
    protected GridPane wrapperTower = new GridPane();
    protected Image imageTower;
    protected ImageView imageViewTower;

    public void setSide(String s){
        this.side=side;
    }
    public String getSide(){
        return side;
    }

    public Tower(double x, double y) throws FileNotFoundException {
        side = "Neutral";
        this.x = x;
        this.y = y;

        this.wrapperTower.getColumnConstraints().add(new ColumnConstraints(600));
        this.wrapperTower.getRowConstraints().add(new RowConstraints(200));

        imageTower = new Image(new FileInputStream("src/sample/tower.png"));
        imageViewTower = new ImageView(imageTower);
        imageViewTower.setPreserveRatio(true);
        imageViewTower.setFitWidth(500);
        imageViewTower.setFitHeight(300);

        wrapperTower.setTranslateX(x);
        wrapperTower.setTranslateY(y);
        wrapperTower.setHalignment(imageViewTower, HPos.CENTER);
        wrapperTower.setValignment(imageViewTower, VPos.CENTER);
        wrapperTower.add(imageViewTower, 0, 0);

        this.groupTower = new Group(wrapperTower);
    }
}
