package com.company;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    public static HashMap<Creep, ImageView> creepImageViewHashMap;
    public static HashMap<Barrack, Group> barrackGroupHashMap;
    public static HashMap<Tower, Group> towerGroupHashMap;
    public static HashMap<Sanctuary, Group> sanctuaryGroupHashMap;

    public Group getPane() {
        return Main.miniMapGroup;
    }

    public MiniMap() throws FileNotFoundException {

        Main.miniMapView = new ImageView(new Image(new FileInputStream("src/sample/world.png")));
        Main.miniMapBoxView.setX(0);
        Main.miniMapBoxView.setY(0);
        Main.miniMapBoxView.setHeight(5);
        Main.miniMapBoxView.setWidth(5);
        Main.miniMapBoxView.setFill(Color.TRANSPARENT);
        Main.miniMapBoxView.setStroke(Color.YELLOW);
        Main.miniMapView.setFitWidth(400);
        Main.miniMapView.setFitHeight(350);

        Main.miniMapGroup.getChildren().add(Main.miniMapView);
        Main.miniMapGroup.getChildren().add(Main.miniMapBoxView);
        Main.miniMapBoxView.toFront();

        creepImageViewHashMap = new HashMap<>();
        barrackGroupHashMap = new HashMap<>();
        towerGroupHashMap = new HashMap<>();
        sanctuaryGroupHashMap = new HashMap<>();

        for (Creep creep : Main.creepsR) {
            addUnit(creep);
        }

        for (Creep creep : Main.creepsD) {
            addUnit(creep);
        }

        for (Barrack barrack : Main.barracks) {
            addBarrack(barrack);

        }

        addTower(Main.towers[0], 140, 1200);
        addTower(Main.towers[1], 130, 1700);
        addTower(Main.towers[2], 1620, 280);
        addTower(Main.towers[3], 760, 320);
        addTower(Main.towers[4], 880, 1750);
        addTower(Main.towers[5], 1280, 1450);
        addTower(Main.towers[6], 1630, 1240);
        addTower(Main.towers[7], 3000, 1100);
        addTower(Main.towers[8], 3000, 1800);
        addTower(Main.towers[9], 2220, 840);
        addTower(Main.towers[10], 1700, 2450);
        addTower(Main.towers[11], 2840, 2360);

        addSanctuary(Main.sanctuary);

        Main.miniMapGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                Main.miniMapBoxView.setX(x);
                Main.miniMapBoxView.setY(y);

                double posX = ( (x) / (4000*0.1) * 4000);
                double posY = ( (y) / (3500*0.1) * 3500);
                Main.scrollPane.setVvalue(posY / 3500);
                Main.scrollPane.setHvalue(posX / 4000);
            }
        });
    }

    public void addUnit(Creep creep) throws FileNotFoundException {
        ImageView imageView = null;

        if (creep instanceof Melee && !(creep instanceof Ranged)){
            if (creep.getTeam().equals("Radiant")){
                imageView = new ImageView(new Image(new FileInputStream("src/sample/melee_r.png")));
            }else
                imageView = new ImageView(new Image(new FileInputStream("src/sample/melee_d.png")));
        }

        else if(creep instanceof Ranged){
            if (creep.getTeam().equals("Radiant")){
                imageView = new ImageView(new Image(new FileInputStream("src/sample/ranged_r.png")));
            }else
                imageView = new ImageView(new Image(new FileInputStream("src/sample/ranged_d.png")));
        }

        else{
            if (creep.getTeam().equals("Radiant")){
                imageView = new ImageView(new Image(new FileInputStream("src/sample/creep_r.png")));
            }else
                imageView = new ImageView(new Image(new FileInputStream("src/sample/creep_d.png")));
        }

        imageView.setLayoutX(creep.getCordsX() * SCALE);
        imageView.setLayoutY(creep.getCordsY() * SCALE);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(190 * SCALE);
        creepImageViewHashMap.put(creep, imageView);
        Main.miniMapGroup.getChildren().add(imageView);
    }

    public void deleteUnit(Creep creep){
        Main.miniMapGroup.getChildren().remove(creepImageViewHashMap.get(creep));
    }

    public void addBarrack(Barrack barrack) throws FileNotFoundException {
        ImageView imageView;
        Group group;
        switch (barrack.getName()) {
            case "Radiant" -> {
                imageView = new ImageView(new Image(new FileInputStream("src/sample/radiant_barrack.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(700 * MiniMap.SCALE);
                imageView.setX(370 * SCALE);
                imageView.setY(2400 * SCALE);
            }
            case "Dire" -> {
                imageView = new ImageView(new Image(new FileInputStream("src/sample/dire_barrack.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(700 * MiniMap.SCALE);
                imageView.setX(3000 * SCALE);
                imageView.setY(250 * SCALE);
            }
            default -> throw new IllegalStateException("Unexpected value: " + barrack.getName());
        }

        group = new Group(imageView);
        group.setStyle("-fx-border-color: white");

        barrackGroupHashMap.put(barrack, group);
        Main.miniMapGroup.getChildren().addAll(group);
    }

    public void addTower(Tower tower, double x, double y) throws FileNotFoundException {
        ImageView imageView;
        Group group;

        imageView = new ImageView(new Image(new FileInputStream("src/sample/circle.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100 * MiniMap.SCALE);
        imageView.setX(x * SCALE);
        imageView.setY(y * SCALE);

        group = new Group(imageView);
        group.setStyle("-fx-border-color: white");

        towerGroupHashMap.put(tower, group);
        Main.miniMapGroup.getChildren().addAll(group);
    }

    public void addSanctuary(Sanctuary sanctuary) throws FileNotFoundException {
        ImageView imageView;
        Group group;

        imageView = new ImageView(new Image(new FileInputStream("src/sample/sanctuary.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200 * MiniMap.SCALE);
        imageView.setX(1000 * SCALE);
        imageView.setY(1100 * SCALE);

        group = new Group(imageView);
        group.setStyle("-fx-border-color: white");

        sanctuaryGroupHashMap.put(sanctuary, group);
        Main.miniMapGroup.getChildren().addAll(group);
    }

    public void updateMap() {
        for (Creep creep : Main.creepsR) {
            ImageView imageView = creepImageViewHashMap.get(creep);
            imageView.setLayoutX(creep.getCordsX() * MiniMap.SCALE);
            imageView.setLayoutY(creep.getCordsY() * MiniMap.SCALE);
        }

        for (Creep creep : Main.creepsD) {
            ImageView imageView = creepImageViewHashMap.get(creep);
            imageView.setLayoutX(creep.getCordsX() * MiniMap.SCALE);
            imageView.setLayoutY(creep.getCordsY() * MiniMap.SCALE);
        }
    }


}
