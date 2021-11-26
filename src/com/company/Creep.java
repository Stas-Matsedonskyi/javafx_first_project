package com.company;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Delayed;

//оголошення класу мікрообєкту
public class Creep implements Comparable<Creep>, Cloneable{
    private int health;
    private double speed;
    private String name;
    private int damage;
    private String state;
    private String team;
    private boolean active;
    private boolean actBar = false;
    private int maxHealth;
    private double speedInitial;
    private int id;
    private static int ID=0;

    protected GridPane creepWrapper = new GridPane();

    private double cordsX;
    private double cordsY;
    private double destX;
    private double destY;

    public static double RadiantX = 370;
    public static double RadiantY = 2000;
    public static double DireX = 2800;
    public static double DireY = 150;

    protected Image icreep;
    protected ImageView creepView;
    protected Label ctext;

    protected Group creeps;

    //перший конструктор
    public Creep(int health, double speed, String name, int damage, String team)
            throws FileNotFoundException {
        this.health = health;
        this.speed = speed;
        this.name = name;
        this.damage = damage;
        this.state = "alive";
        this.team = team;
        this.destX = (double)Main.rnd.nextInt(3700);
        this.destY = (double)Main.rnd.nextInt(3000);

        this.id = Creep.ID;
        Creep.ID++;

        this.maxHealth = this.health;
        this.speedInitial = this.speed;

        if (team.equals("Radiant")){

            this.cordsX = Creep.RadiantX;
            this.cordsY = Creep.RadiantY;

            icreep = new Image(new FileInputStream("src/sample/creep_r.png"));
            this.creepView = new ImageView(icreep);

            this.creepWrapper.setTranslateX(this.cordsX);
            this.creepWrapper.setTranslateY(this.cordsY);

            Creep.RadiantX = 370;
            Creep.RadiantY = 2000;
        }
        if (team.equals("Dire")){

            this.cordsX = Creep.DireX;
            this.cordsY = Creep.DireY;

            icreep = new Image(new FileInputStream("src/sample/creep_d.png"));
            this.creepView = new ImageView(icreep);

            this.creepWrapper.setTranslateX(this.cordsX);
            this.creepWrapper.setTranslateY(this.cordsY);

            Creep.DireX = 2800;
            Creep.RadiantY = 150;
        }

        ctext = new Label(name +" " + health);
        ctext.setFont(new Font(20));
        ctext.setTextFill(Color.WHITE);

        creepView = new ImageView(icreep);

        creepView.setFitWidth(150);
        creepView.setFitHeight(355);
        creepView.setPreserveRatio(true);

        this.creepWrapper.setOnMouseClicked((event) -> {
            this.resetActive();
            if(this.getActive()) {
                String styleWrapper = "-fx-border-color: yellow;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-style: solid;";
                this.creepWrapper.setStyle(styleWrapper);
                Main.StatusBarInfo(this, true);
            }else {
                this.creepWrapper.setStyle(" ");
                Main.StatusBarInfo(this, false);
            }
        });

        creepWrapper.setHalignment(this.creepView, HPos.RIGHT);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);
        creepWrapper.setHalignment(this.creepView, HPos.CENTER);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);

        creepWrapper.add(this.creepView, 0, 0);
        creepWrapper.add(this.ctext, 0, 1);

        this.creeps = new Group( this.creepWrapper );
    }

    //сеттери і геттери
    public int getHealth(){ return health; }
    public void setHealth(int health) {
        this.health = health;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public String getTeam() {return team;}
    public void setTeam(String team) { this.team = team;}
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCordsX(double x){ this.cordsX = x;}
    public double getCordsX(){ return cordsX;}
    public void setCordsY(double y){ this.cordsY = y;}
    public double getCordsY(){ return cordsY;}
    public void resetActive(){this.active = !active;}
    public boolean getActive() { return active;}
    public int getId(){ return id;}
    public double getXDest(){
        return this.destX;
    }
    public void setXDest(double newX){
        this.destX = newX;
    }
    public double getYDest(){
        return this.destY;
    }
    public void setYDest(double newY){
        this.destY = newY;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }
    public boolean isActBar() {
        return actBar;
    }
    public void setActBar(boolean actBar) {
        this.actBar = actBar;
    }
    public double getSpeedInitial() {
        return speedInitial;
    }
    public void setSpeedInitial(double speedInitial) {
        this.speedInitial = speedInitial;
    }

    //введення параметрів через консоль
    public static Creep askCreepParameters() throws FileNotFoundException {
        System.out.println("Введіть параметри для нового об'єкту ");
        System.out.print("HP: ");
        int health = Main.in.nextInt();
        System.out.print("Move speed: ");
        double speed = Main.in.nextDouble();
        System.out.print("Name: ");
        String name = Main.in.next();
        System.out.print("Damage: ");
        int damage = Main.in.nextInt();
        String team = "Neutral";
        return new Creep(health, speed, name, damage, team);
    }

    //делегування першого конструктора
    public Creep() throws FileNotFoundException {
        this(400, 6, "catapult", 75, "Radiant");
    }

//    public void setTeam(){
//        System.out.print("Team: ");
//        String s = Main.in.next();
//        if(s.equals("r") || s.equals("R")){
//            team = "RADIANT";
//        }
//        else if(s.equals("d") || s.equals("D")){
//            team = "DIRE";
//        }
//        else{
//            team = "Neutral";
//        }
//    }

    public void specialForDireClone() throws FileNotFoundException {

        creepWrapper.getChildren().remove(this.creepView);

        this.cordsX = Creep.DireX;
        this.cordsY = Creep.DireY;
        icreep = new Image(new FileInputStream("src/sample/creep_d.png"));
        this.creepView = new ImageView(icreep);
        this.creepWrapper.setTranslateX(this.cordsX);
        this.creepWrapper.setTranslateY(this.cordsY);

        ctext.setText(name +" " + health);
        ctext.setFont(new Font(20));
        ctext.setTextFill(Color.WHITE);
        creepView.setFitWidth(150);
        creepView.setFitHeight(355);
        creepView.setPreserveRatio(true);

        creepWrapper.setHalignment(this.creepView, HPos.RIGHT);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);
        creepWrapper.setHalignment(this.creepView, HPos.CENTER);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);

        creepWrapper.add(this.creepView, 0, 0);
    }

    public void move(double x, double y){

        if(!((this.getCordsX() + this.getSpeed() * x) <= -10) && !((this.getCordsX() + this.getSpeed() * x) > 3800)){
            this.setCordsX(this.getCordsX() + this.getSpeed() * x);
        }

        if(!((this.getCordsY() + this.getSpeed() * y) <= 0) && !((this.getCordsY() + this.getSpeed() * y) > 3300)){
            this.setCordsY(this.getCordsY() + this.getSpeed() * y);
        }

        this.creepWrapper.setTranslateX(this.cordsX);
        this.creepWrapper.setTranslateY(this.cordsY);
    }

    public  boolean checkLife(){
        return health > 0;
    }

    public void move(){

        if (this.active){
            return;
        }

        if(Math.round(this.getCordsX()) == Math.round(this.destX)){

            this.destX = (double)Main.rnd.nextInt(3800);

        }else if(Math.round(this.getCordsY()) == Math.round(this.destY)){

            this.destY = (double)Main.rnd.nextInt(3300);

        }else{
            double xDiff = this.destX - this.getCordsX();
            double yDiff = this.destY - this.getCordsY();

            //handling x difference
            if(xDiff < 0){

                if(Math.abs(xDiff) <= this.getSpeed()){
                    this.setCordsX(this.getCordsX() - (Math.abs(xDiff)/10));
                }else{
                    this.setCordsX(this.getCordsX() - (this.getSpeed()/10));
                }

            }else if(xDiff > 0){

                if(xDiff <= this.getSpeed()){
                    this.setCordsX(this.getCordsX() + (xDiff/10));
                }else{
                    this.setCordsX(this.getCordsX() + (this.getSpeed()/10));
                }

            }

            //handling y difference
            if(yDiff < 0){

                if(Math.abs(yDiff) <= this.getSpeed()){
                    this.setCordsY(this.getCordsY() - (Math.abs(yDiff)/10));
                }else{
                    this.setCordsY(this.getCordsY() - (this.getSpeed()/10));
                }

            }else if(yDiff > 0){

                if(yDiff <= this.getSpeed()){
                    this.setCordsY(this.getCordsY() + (yDiff/10));
                }else{
                    this.setCordsY(this.getCordsY() + (this.getSpeed()/10));
                }

            }
        }

        if(!this.getActive()){
            this.creepWrapper.setTranslateX(this.cordsX);
            this.creepWrapper.setTranslateY(this.cordsY);
        }
    }

    public void fight(Creep creep){
            if (creep.checkLife()) {

                if (creep instanceof Melee){

                    if (((Melee) creep).getArmour() != 0  && (this.getDamage() > 100)){
                        ((Melee) creep).set_armour(((Melee) creep).getArmour() - 1);
                        ((Melee) creep).setArmour(((Melee) creep).getArmour());
                    }

                    else if (((Melee) creep).getArmour() == 0){
                        creep.setHealth(creep.getHealth() - this.getDamage());
                    }

                }

                else {
                    creep.setHealth(creep.getHealth() - this.getDamage());
                }
            }

            if (creep.getHealth() <= 0) {
                creep.setHealth(0);
            }
    }

    public void shiftToMonster() throws FileNotFoundException {
        creepWrapper.getChildren().remove(this.creepView);

        this.setTeam("Monster");

        icreep = new Image(new FileInputStream("src/sample/monster.png"));
        this.creepView = new ImageView(icreep);

        this.creepWrapper.setTranslateX(this.getCordsX());
        this.creepWrapper.setTranslateY(this.getCordsY());

        this.creepView.setFitWidth(150);
        this.creepView.setFitHeight(355);
        this.creepView.setPreserveRatio(true);

        creepWrapper.setHalignment(this.creepView, HPos.RIGHT);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);
        creepWrapper.setHalignment(this.creepView, HPos.CENTER);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);

        creepWrapper.add(this.creepView, 0, 0);


    }

    public double speedComp(Creep anotherCreep){
        double comp_sp = anotherCreep.getSpeed();
        return (this.speed - comp_sp);
    }

    @Override
    public String toString() {
        return "Creep{" +
                "health= " + health +
                ", speed= " + speed +
                ", name= " + name +
                ", damage= " + damage +
                ", state= " + state +
                ", team= " + team + " }" +"\n";
    }

    @Override
    public int compareTo(Creep anotherCreep){
        int comp_hp = anotherCreep.getHealth();
        return (this.health - comp_hp);
    }

    public static class dmgComparator implements Comparator<Creep>{

        @Override
        public int compare(Creep c1, Creep c2){
            int d1=c1.getDamage();
            int d2=c2.getDamage();
            return (d1 - d2);
        }
    }

//    public Creep makeACopy() throws FileNotFoundException {
//        Creep tmp = new Creep();
//
//        tmp.health = health;
//        tmp.speed = speed;
//        tmp.name = name;
//        tmp.damage = damage;
//        tmp.state = state;
//        tmp.team = team;
//
//        return tmp;
//    }

    @Override
    public Creep clone(){
        Creep tmp = null;
        try {
            tmp = new Creep();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert tmp != null;
        tmp.health = health;
        tmp.speed = speed;
        tmp.name = name;
        tmp.damage = damage;
        tmp.state = state;
        tmp.team = team;
        if (team.equals("Dire")){
            try {
                tmp.specialForDireClone();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        return tmp;
    }

}