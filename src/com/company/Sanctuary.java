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
import java.util.LinkedList;

public class Sanctuary {
    private LinkedList<Melee> sanc = new LinkedList<>();



    protected Group groupSanc;
    protected GridPane wrapperSanc = new GridPane();
    protected Image imageSanc;
    protected ImageView imageViewSanc;

    public Sanctuary() throws FileNotFoundException {

        double x, y;
        x=1000;
        y=1100;

        this.wrapperSanc.getColumnConstraints().add(new ColumnConstraints(600));
        this.wrapperSanc.getRowConstraints().add(new RowConstraints(200));

        imageSanc = new Image(new FileInputStream("src/sample/sanctuary.png"));
        imageViewSanc = new ImageView(imageSanc);
        imageViewSanc.setPreserveRatio(true);
        imageViewSanc.setFitWidth(500);
        imageViewSanc.setFitHeight(300);

        wrapperSanc.setTranslateX(x);
        wrapperSanc.setTranslateY(y);
        wrapperSanc.setHalignment(imageViewSanc, HPos.CENTER);
        wrapperSanc.setValignment(imageViewSanc, VPos.CENTER);
        wrapperSanc.add(imageViewSanc, 0, 0);

        this.groupSanc = new Group(wrapperSanc);
    }

}
