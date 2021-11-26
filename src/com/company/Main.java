package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.shape.Rectangle;
import org.w3c.dom.html.HTMLLabelElement;


public class Main extends Application{

    public static Scanner in = new Scanner(System.in);
    public static Random rnd = new Random();
    public static ArrayList<Creep> creepsR = new ArrayList<Creep>();
    public static ArrayList<Creep> creepsD = new ArrayList<Creep>();
    public static Barrack[] barracks = new Barrack[2];
    public static Tower[] towers = new Tower[12];
    public static Sanctuary sanctuary;
    public static MiniMap map;
    public static int counterRT=0;
    public static int counterDT=0;

    static {
        System.out.println("Static block is used!");
        try {
            sanctuary = new Sanctuary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        System.out.println("Non-static block is used!");
    }

    Button button1 = new Button("Clone");
    Button button2 = new Button("Create");
    Button button3 = new Button("About");

    public static Window window;
    public static Stage primaryStage;
    static AnimationTimer timer;
    public static Group group = new Group();
    static Scene scene;
    static VBox StatusBarWrapper = new VBox();
    static Group StatusGroup = new Group();
    static StackPane layout;
    public static Wallpaper wallpaper;
    public static ScrollPane scrollPane;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    static ImageView miniMapView;
    static Group miniMapGroup = new Group();
    static Scale miniMapScale = new Scale();
    static Rectangle miniMapBoxView = new Rectangle();

    public static Label label = new Label();
    public static Label label1 = new Label();
    public static Label label2 = new Label();
    public static Label label3 = new Label();
    public static int buttonCounter = 0;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;

        this.setWallpaper();
        createMacro();
        Group root = new Group(group);
        scrollPane = new ScrollPane(root);

        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
        scrollPane.setMaxHeight(Wallpaper.border.getHeight());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        layout = new StackPane();
        layout.getChildren().add(scrollPane);
        StatusGroup.getChildren().add(StatusBarWrapper);
        layout.getChildren().add(StatusGroup);
        layout.setAlignment(StatusGroup, Pos.BOTTOM_CENTER);


        scene = new Scene(layout, this.width,this.height);

        Creep creep = new Creep();

        Melee melee = new Melee();

        Ranged ranged = new Ranged();

        creepsR.add(creep);
        creepsR.add(melee);
        creepsR.add(ranged);

        for (Creep creep1 : Main.creepsR){
            group.getChildren().add(creep1.creeps);
        }

        map = new MiniMap();

        miniMapGroup.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                miniMapBoxView.setX(x);
                miniMapBoxView.setY(y);

                double posX = ( (x) / (4000*0.1) * 4000);
                double posY = ( (y) / (3500*0.1) * 3500);
                scrollPane.setVvalue(posY / 3500);
                scrollPane.setHvalue(posX / 4000);
            }
        });


        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setY(scrollPane.getVvalue() * 350 - 5);
            }
        });

        scrollPane.hvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setX(scrollPane.getHvalue() * 400 - 5);
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                for (Creep creep1 : creepsR){
                    creep1.move();
                }

                for (Creep creep2 : creepsD){
                    creep2.move();
                    }

                creepFight();
                creepHeal();
                creepInBarrack();
                try {
                    creepInSanctuary();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    creepCaptureTower();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                intersectsMonster();

                map.updateMap();

            }
        };

        scene.setOnKeyPressed(new KeyPressHandler());

        HBox topMenu = new HBox();
        topMenu.setMaxHeight(30);
        topMenu.setMaxWidth(250);
        topMenu.getChildren().addAll(button1, button2, button3);

        GridPane gridPane = new GridPane();

        gridPane.setMaxHeight(205);
        gridPane.setMaxWidth(450);

        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));

        label.setFont(new Font(20));
        label.setTextFill(Color.TRANSPARENT);
        label1.setFont(new Font(20));
        label1.setTextFill(Color.TRANSPARENT);
        label2.setFont(new Font(20));
        label2.setTextFill(Color.TRANSPARENT);
        label3.setFont(new Font(20));
        label3.setTextFill(Color.TRANSPARENT);

        gridPane.add(label, 0, 0);
        gridPane.add(label1, 0, 1);
        gridPane.add(label2, 0, 2);
        gridPane.add(label3, 0, 3);

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Window w = new Window();
                w.dialog.show();
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonCounter++;
                competition();

                if (!(buttonCounter%2 == 0)){
                    label.setTextFill(Color.WHITE);
                    label1.setTextFill(Color.WHITE);
                    label2.setTextFill(Color.WHITE);
                    label3.setTextFill(Color.WHITE);
                }

                else {
                    label.setTextFill(Color.TRANSPARENT);
                    label1.setTextFill(Color.TRANSPARENT);
                    label2.setTextFill(Color.TRANSPARENT);
                    label3.setTextFill(Color.TRANSPARENT);
                }
            }
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ArrayList<Creep> clones = new ArrayList<>();
//                Creep tmp;
                for(Creep creep : creepsR) {
                    if (creep.getActive()) {
                        Creep tmp  = creep.clone();
                        clones.add(tmp);
                    }
                }

                for(Creep creep1 : creepsD) {
                    if (creep1.getActive()) {
                        Creep tmp = creep1.clone();
                        clones.add(tmp);
                    }
                }

                if (!clones.isEmpty()){
                    for (Creep creep1 : clones) {
                        if (creep1.getTeam().equals("Radiant"))
                            creepsR.add(creep1);
                        if (creep1.getTeam().equals("Dire"))
                            creepsD.add(creep1);
                        try {
                            map.addUnit(creep1);
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        group.getChildren().add(creep1.creeps);
                        creep1.move();
                    }
                }


            }
        });

        layout.getChildren().add(gridPane);
        layout.setAlignment(gridPane, Pos.TOP_RIGHT);

        layout.getChildren().add(topMenu);
        layout.setAlignment(topMenu, Pos.TOP_LEFT);

        layout.getChildren().add(map.getPane());
        layout.setAlignment(map.getPane(), Pos.BOTTOM_RIGHT);

        primaryStage.setTitle("DOTA 2");
        primaryStage.setScene(scene);
        timer.start();
        primaryStage.show();
    }

    public static void StatusBarInfo(Creep creep, boolean active){
        if (active){
            Label info = new Label(creep.toString());
            info.setFont(new Font(20));
            info.setId(String.valueOf(creep.getId()));
            StatusBarWrapper.getChildren().add(info);
            StatusBarWrapper.setPrefWidth(1200);
            StatusBarWrapper.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            StatusBarWrapper.setAlignment(Pos.CENTER);
        }
        else{
            for(Node label : StatusBarWrapper.getChildren()){
                if(label.getId().equals(String.valueOf(creep.getId()))){
                    StatusBarWrapper.getChildren().remove(label);
                    break;
                }
            }
        }
    }

    public void setWallpaper() throws FileNotFoundException {
        wallpaper = new Wallpaper();
        group.getChildren().add(Wallpaper.getWallGroup());
    }

    public static void setupStage() throws FileNotFoundException {
        window = new Window();
        Main.primaryStage.setTitle("New creep creation");
        Main.primaryStage.setScene(window.returnDialogScene());
    }

    public static void createMacro() throws FileNotFoundException {
        //Barracks
        Main.barracks[0] = new Barrack("Radiant");
        Main.barracks[1] = new Barrack("Dire");

        for (Barrack barrack : barracks){
            group.getChildren().add(barrack.GroupBarrack);
        }

        //Towers
        Main.towers[0] = new Tower(140, 1200);
        Main.towers[1] = new Tower(130, 1700);
        Main.towers[2] = new Tower(1620, 280);
        Main.towers[3] = new Tower(760, 320);
        Main.towers[4] = new Tower(880, 1750);
        Main.towers[5] = new Tower(1280, 1450);
        Main.towers[6] = new Tower(1630, 1240);
        Main.towers[7] = new Tower(3000, 1100);
        Main.towers[8] = new Tower(3000, 1800);
        Main.towers[9] = new Tower(2220, 840);
        Main.towers[10] = new Tower(1700, 2450);
        Main.towers[11] = new Tower(2840, 2360);

        for (Tower tower : towers){
            group.getChildren().add(tower.groupTower);
        }

        //Sanctuary
        group.getChildren().add(sanctuary.groupSanc);
    }

    static long lastInvocationTime = new Date().getTime();
    static long interval = 250;

    public static void creepFight(){

        ArrayList<Creep> creepsToDel = new ArrayList<Creep>();
        long current = new Date().getTime();

        for (Creep creep1 : creepsR){
            for (Creep creep2 : creepsD) {
                if (creep1.creeps.intersects(creep2.creeps.getLayoutBounds())) {
                    if (((current - lastInvocationTime) > interval)) {
                        creep1.fight(creep2);
                        creep2.fight(creep1);

                        if (creep1 instanceof Melee) {
                            creep1.ctext.setText(creep1.getName() + " " + creep1.getHealth() + " " + ((Melee) creep1).ArmourSymb());
                        } else {
                            creep1.ctext.setText(creep1.getName() + " " + creep1.getHealth());
                        }

                        if (creep2 instanceof Melee) {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth() + " " + ((Melee) creep2).ArmourSymb());
                        } else {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth());
                        }
                    }
                    lastInvocationTime = current;
                }
                if(!creep1.checkLife()){
                    Main.group.getChildren().remove(creep1.creeps);
                    creepsToDel.add(creep1);
                }else if(!creep2.checkLife()){
                    Main.group.getChildren().remove(creep2.creeps);
                    creepsToDel.add(creep2);
                }
            }
        }

        for(Creep creep : creepsToDel){
            if(creep.getTeam().equals("Radiant")){
                Main.creepsR.remove(creep);
                Main.map.deleteUnit(creep);
            }else{
                Main.creepsD.remove(creep);
                Main.map.deleteUnit(creep);
            }
        }
    }

    public static void creepHeal(){
        long current = new Date().getTime();

        for (Creep creep1 : creepsR){
            for (Creep creep2 : creepsR) {
                if (creep1.creeps.intersects(creep2.creeps.getLayoutBounds()) && !(creep1==creep2) && (creep1 instanceof Ranged)) {
                    if (((current - lastInvocationTime) > interval)) {
                        ((Ranged) creep1).grantHP(creep2);
                        if (creep2 instanceof Melee) {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth() + " " + ((Melee) creep2).ArmourSymb());
                        } else {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth());
                        }
                    }
                    lastInvocationTime = current;
                }
            }
        }

        for (Creep creep1 : creepsD){
            for (Creep creep2 : creepsD) {
                if (creep1.creeps.intersects(creep2.creeps.getLayoutBounds()) && !(creep1==creep2) && (creep1 instanceof Ranged)) {
                    if (((current - lastInvocationTime) > interval)) {
                        ((Ranged) creep1).grantHP(creep2);

                        if (creep2 instanceof Melee) {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth() + " " + ((Melee) creep2).ArmourSymb());
                        } else {
                            creep2.ctext.setText(creep2.getName() + " " + creep2.getHealth());
                        }
                    }
                    lastInvocationTime = current;
                }
            }
        }
    }

    public static void creepInBarrack(){
        ArrayList<Creep> creepsToDel = new ArrayList<>();
        for (Creep creep : creepsR){
            if (creep.isActBar()){
                if (creep.creeps.intersects(barracks[0].GroupBarrack.getLayoutBounds())){
                    Main.barracks[0].add(creep, "Radiant");
                    creepsToDel.add(creep);
                }
            }

            if (creep.creeps.intersects(barracks[0].GroupBarrack.getLayoutBounds()) && creep.getHealth() < creep.getMaxHealth()){
                creep.setHealth(creep.getMaxHealth());

                if (creep instanceof Melee) {
                    creep.ctext.setText(creep.getName() + " " + creep.getHealth() + " " + ((Melee) creep).ArmourSymb());
                } else {
                    creep.ctext.setText(creep.getName() + " " + creep.getHealth());
                }
            }
        }

        for (Creep creep : creepsD){
            if (creep.isActBar()){
                if (creep.creeps.intersects(barracks[1].GroupBarrack.getLayoutBounds())){
                    Main.barracks[1].add(creep, "Dire");
                    creepsToDel.add(creep);
                }
            }

            if (creep.creeps.intersects(barracks[1].GroupBarrack.getLayoutBounds()) && creep.getHealth() < creep.getMaxHealth()){
                creep.setHealth(creep.getMaxHealth());

                if (creep instanceof Melee) {
                    creep.ctext.setText(creep.getName() + " " + creep.getHealth() + " " + ((Melee) creep).ArmourSymb());
                } else {
                    creep.ctext.setText(creep.getName() + " " + creep.getHealth());
                }
            }
        }

        for(Creep creep : creepsToDel){
            if(creep.getTeam().equals("Radiant")){
                Main.group.getChildren().remove(creep.creeps);
                Main.creepsR.remove(creep);
            }else{
                Main.group.getChildren().remove(creep.creeps);
                Main.creepsD.remove(creep);
            }
        }
    }

    public static void creepInSanctuary() throws FileNotFoundException {
        for (Creep creep : creepsR){
            if (creep.creeps.intersects(sanctuary.groupSanc.getLayoutBounds()) && creep instanceof Melee){

                if (creep.getName().contains("MEGA")){
                    break;
                }
                else { ((Melee) creep).upgradeToMega();}

                creep.ctext.setText(creep.getName() + " " + creep.getHealth() + " " + ((Melee) creep).ArmourSymb());
            }
        }

        for (Creep creep : creepsD){
            if (creep.creeps.intersects(sanctuary.groupSanc.getLayoutBounds()) && creep instanceof Melee){

                if (creep.getName().contains("MEGA")){
                    break;
                }
                else { ((Melee) creep).upgradeToMega();}

                creep.ctext.setText(creep.getName() + " " + creep.getHealth() + " " + ((Melee) creep).ArmourSymb());
            }
        }

    }

    public static void creepCaptureTower() throws FileNotFoundException {
        for (Creep creep : creepsR){
            for (int i=0; i<12; i++){
                if (creep.creeps.intersects(towers[i].groupTower.getLayoutBounds())){
                    towers[i].setSide("Radiant");
                    towers[i].wrapperTower.getChildren().remove(towers[i].imageViewTower);
                    towers[i].imageTower = new Image(new FileInputStream("src/sample/radiant_tower.png"));
                    towers[i].imageViewTower = new ImageView(towers[i].imageTower);
                    towers[i].imageViewTower.setPreserveRatio(true);
                    towers[i].imageViewTower.setFitWidth(500);
                    towers[i].imageViewTower.setFitHeight(300);
                    towers[i].wrapperTower.getChildren().add(towers[i].imageViewTower);
                }
            }
        }

        for (Creep creep : creepsD){
            for (int i=0; i<12; i++){
                if (creep.creeps.intersects(towers[i].groupTower.getLayoutBounds())){
                    towers[i].setSide("Dire");
                    towers[i].wrapperTower.getChildren().remove(towers[i].imageViewTower);
                    towers[i].imageTower = new Image(new FileInputStream("src/sample/dire_tower.png"));
                    towers[i].imageViewTower = new ImageView(towers[i].imageTower);
                    towers[i].imageViewTower.setPreserveRatio(true);
                    towers[i].imageViewTower.setFitWidth(500);
                    towers[i].imageViewTower.setFitHeight(300);
                    towers[i].wrapperTower.getChildren().add(towers[i].imageViewTower);
                }
            }
        }

        for (int i=0; i<12; ++i){
            if (towers[i].getSide().equals("Radiant")){
                counterRT++;
            }
            else if (towers[i].getSide().equals("Dire")){
                counterDT++;
            }
        }
    }

    public void competition() {

        ArrayList<Creep> tempRhp = new ArrayList<>(creepsR);
        ArrayList<Creep> tempRdmg = new ArrayList<>(creepsR);
        ArrayList<Creep> tempDhp = new ArrayList<>(creepsD);
        ArrayList<Creep> tempDdmg = new ArrayList<>(creepsD);

        int a;

        for (int i=0; i<tempRhp.size(); i++){
            for (int j=0; j<tempRhp.size(); j++){
                if (tempRhp.get(i).compareTo(tempRhp.get(j)) > 0){
                    tempRhp.remove(j);
                }
            }
        }

        Main.label.setText(tempRhp.get(0).getName() + " найздоровіший з Radiant: " + tempRhp.get(0).getHealth());

        tempRhp.clear();

        for (int i=0; i<tempRdmg.size(); i++){
            for (int j=0; j<tempRdmg.size(); j++){
                a=new Creep.dmgComparator().compare(tempRdmg.get(i), tempRdmg.get(j));
                if (a > 0){
                    tempRdmg.remove(j);
                }
            }
        }
        Main.label1.setText(tempRdmg.get(0).getName() + " найсильніший з Radiant: " + tempRdmg.get(0).getDamage());

        tempRdmg.clear();

        for (int i=0; i<tempDhp.size(); i++){
            for (int j=0; j<tempDhp.size(); j++){
                if (tempDhp.get(i).compareTo(tempDhp.get(j)) > 0){
                    tempDhp.remove(j);
                }
            }
        }
        Main.label2.setText(tempDhp.get(0).getName() + " найздоровіший з Dire: " + tempDhp.get(0).getHealth());
        tempDhp.clear();

        int b;
        for (int i=0; i<tempDdmg.size(); i++){
            for (int j=0; j<tempDdmg.size(); j++){
                b=new Creep.dmgComparator().compare(tempDdmg.get(i), tempDdmg.get(j));
                if (b > 0){
                    tempDdmg.remove(j);
                }
            }
        }
        Main.label3.setText(tempDdmg.get(0).getName() + " найсильніший з Dire: " + tempDdmg.get(0).getDamage());
        tempDdmg.clear();

    }

    public void intersectsMonster(){

        ArrayList<Creep> tempRsp = new ArrayList<>(creepsR);

        for (Creep creep1 : creepsR){
            for (Creep creep2 : creepsR){
                if (creep1.speedComp(creep2) > 0 && !(creep1.getTeam().equals("Monster"))){
                    tempRsp.remove(creep2);
                }
            }
        }

        for (Creep creep1 : creepsD){
            for (Creep creep2 : creepsD){
                if (creep1.speedComp(creep2) > 0 && !(creep1.getTeam().equals("Monster"))){
                    tempRsp.remove(creep2);
                }
            }
        }
        int a=0, b=0;

        for (Creep creep : creepsR){
            for (Creep victim : creepsR){
                if (creep.getTeam().equals("Monster")){
                    creep.setSpeed(tempRsp.get(0).getSpeed()*1.5);

                    creep.setXDest(victim.getCordsX());
                    creep.setYDest(victim.getCordsY());

                    if (creep.creeps.intersects(victim.creeps.getLayoutBounds()) && !(victim.getTeam().equals("Monster"))){
                        creep.creepView.setFitHeight(creep.creepView.getFitHeight()+10);
                        creep.creepView.setFitWidth(creep.creepView.getFitWidth()+10);
                        victim.setSpeed(0);
                        victim.setCordsX(a+350);
                        victim.setCordsY(50);
                    }

                    a=+350;
                }
            }
        }

        for (Creep creep : creepsD){
            for (Creep victim : creepsD){
                if (creep.getTeam().equals("Monster")){
                    creep.setSpeed(tempRsp.get(0).getSpeed()*1.5);

                    creep.setXDest(victim.getCordsX());
                    creep.setYDest(victim.getCordsY());

                    if (creep.creeps.intersects(victim.creeps.getLayoutBounds()) && !(victim.getTeam().equals("Monster"))){
                        creep.creepView.setFitHeight(creep.creepView.getFitHeight()+10);
                        creep.creepView.setFitWidth(creep.creepView.getFitWidth()+10);
                        victim.setSpeed(0);
                        victim.setCordsX(50);
                        victim.setCordsY(b+350);
                    }

                    b+=350;
                }
            }
        }




    }

}