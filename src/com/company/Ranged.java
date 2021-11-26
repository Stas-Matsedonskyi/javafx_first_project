package com.company;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Ranged extends Melee implements Cloneable{
    private String[] Armour;
    private int mana;

    public Ranged(int health, double speed, String name, int damage, String team, int armour, int mana)
            throws FileNotFoundException {
        super(health, speed, name, damage, team, armour);
        this.mana = mana;
        setArmour();

        if (team.equals("Radiant")){
            icreep = new Image(new FileInputStream("src/sample/ranged_r.png"));
            this.creepView = new ImageView(icreep);

            this.creepWrapper.setTranslateX(this.getCordsX());
            this.creepWrapper.setTranslateY(this.getCordsY());
        }
        if (team.equals("Dire")){
            icreep = new Image(new FileInputStream("src/sample/ranged_d.png"));
            this.creepView = new ImageView(icreep);

            this.creepWrapper.setTranslateX(this.getCordsX());
            this.creepWrapper.setTranslateY(this.getCordsY());
        }

        this.ctext = new Label(name +" " + health + " " + Arrays.toString(Armour));
        this.ctext.setFont(new Font(20));
        this.ctext.setTextFill(Color.WHITE);

        this.creepView = new ImageView(this.icreep);

        this.creepView.setFitWidth(150);
        this.creepView.setFitHeight(355);
        this.creepView.setPreserveRatio(true);

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

        this.creepWrapper.getChildren().clear();
        creepWrapper.setHalignment(this.creepView, HPos.RIGHT);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);
        creepWrapper.setHalignment(this.ctext, HPos.CENTER);
        creepWrapper.setValignment(this.ctext, VPos.CENTER);

        creepWrapper.add(this.creepView, 0, 0);
        creepWrapper.add(this.ctext, 0, 1);

        this.creeps = new Group( this.creepWrapper );
    }

    public Ranged() throws FileNotFoundException {
        this(100, 8, "ranged", 75, "Radiant", 1, 100);
        setArmour();
    }

    public void grantHP(Creep unit){

        if (unit.getHealth() == unit.getMaxHealth()){
            unit.setMaxHealth(unit.getHealth()+mana);
        }
        unit.setHealth(unit.getHealth()+mana);
    }

    public void setArmour() {
        int k = super.getArmour();
        Armour = new String[k];
        for (int i = 0; i < k; ++i) {
            Armour[i] = "⛨";
        }
    }

    private void setMana(int m){
        this.mana = m;
    }
    private int getMana(){
        return mana;
    }

    public void upgradeToMega() throws FileNotFoundException {
        setHealth(getHealth() + 700);
        setSpeed(getSpeedInitial() + 8);
        setSpeedInitial(getSpeed());
        setName("MEGA_" + getName());
        setDamage(getDamage() + 50);
        set_armour(getArmour()+1);
        setArmour(getArmour());
        setMana(getMana()+100);

        this.creepWrapper.getChildren().remove(creepView);

        if (getTeam().equals("Radiant")){
            icreep = new Image(new FileInputStream("src/sample/mega_ranged_r.png"));
        }
        else{
            icreep = new Image(new FileInputStream("src/sample/mega_ranged_d.png"));
        }

        this.creepView = new ImageView(this.icreep);
        this.creepView.setFitWidth(150);
        this.creepView.setFitHeight(355);
        this.creepView.setPreserveRatio(true);
        this.creepWrapper.add(creepView, 0, 0);
    }

    public static Ranged askCreepParameters() throws FileNotFoundException {
        System.out.println("Введіть параметри для нового об'єкту ");
        System.out.print("HP: ");
        int health = Main.in.nextInt();
        System.out.print("Move speed: ");
        double speed = Main.in.nextDouble();
        System.out.print("Name: ");
        String name = Main.in.next();
        System.out.print("Damage: ");
        int damage = Main.in.nextInt();
        String state = "alive";
        String team = "Neutral";
        System.out.print("Armour: ");
        int armour = Main.in.nextInt();
        System.out.print("Mana: ");
        int mana = Main.in.nextInt();
        return new Ranged(health, speed, name, damage, team, armour, mana);
    }

    public Ranged makeACopy() throws FileNotFoundException {
        Ranged tmp = new Ranged();

        tmp.setHealth(super.getHealth());
        tmp.setSpeed(super.getSpeed());
        tmp.setName(super.getName());
        tmp.setDamage(super.getDamage());
        tmp.setState(super.getState());
        tmp.setTeam(super.getTeam());
        tmp.setArmour();

        if( this.Armour==null )tmp.Armour=new String[0];
        else tmp.Armour = Arrays.copyOf
                (this.Armour,
                        this.Armour.length);

        return tmp;
    }

    public void specialForDireClone() throws FileNotFoundException {
        creepWrapper.getChildren().remove(this.creepView);

        icreep = new Image(new FileInputStream("src/sample/ranged_d.png"));
        this.creepView = new ImageView(icreep);

        this.setCordsX(Creep.DireX);
        this.setCordsY(Creep.DireY);

        this.creepWrapper.setTranslateX(this.getCordsX());
        this.creepWrapper.setTranslateY(this.getCordsY());

        this.ctext.setText(getName() +" " + getHealth() + " " + Arrays.toString(Armour));

        this.creepView.setFitWidth(150);
        this.creepView.setFitHeight(355);
        this.creepView.setPreserveRatio(true);

        creepWrapper.setHalignment(this.creepView, HPos.RIGHT);
        creepWrapper.setValignment(this.creepView, VPos.CENTER);
        creepWrapper.setHalignment(this.ctext, HPos.CENTER);
        creepWrapper.setValignment(this.ctext, VPos.CENTER);

        creepWrapper.add(this.creepView, 0, 0);
    }

    public void specialForMega() throws FileNotFoundException {

        creepWrapper.getChildren().remove(this.creepView);

        if (this.getTeam().equals("Radiant")){
            icreep = new Image(new FileInputStream("src/sample/mega_ranged_r.png"));
            this.setCordsX(Creep.RadiantX);
            this.setCordsY(Creep.RadiantY);
        }
        else {
            icreep = new Image(new FileInputStream("src/sample/mega_ranged_d.png"));
            this.setCordsX(Creep.DireX);
            this.setCordsY(Creep.DireY);
        }
        this.creepView = new ImageView(icreep);

        this.creepWrapper.setTranslateX(this.getCordsX());
        this.creepWrapper.setTranslateY(this.getCordsY());

        ctext.setText(getName() +" " + getHealth() + " " + Arrays.toString(Armour));
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

    @Override
    public String toString() {
        return "Creep{" +
                "health= " + super.getHealth() +
                ", speed= " + super.getSpeed() +
                ", name= " + super.getName() +
                ", damage= " + super.getDamage() +
                ", state= " + super.getState() +
                ", team= " + super.getTeam() +
                ", armour= " + Arrays.toString(Armour) +
                ", mana= " + mana + "}" + "\n";
    }

    @Override
    public Ranged clone(){
        Ranged tmp = null;
        try {
            tmp = new Ranged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert tmp != null;
        tmp.setHealth(super.getHealth());
        tmp.setSpeed(super.getSpeed());
        tmp.setName(super.getName());
        tmp.setDamage(super.getDamage());
        tmp.setState(super.getState());
        tmp.setTeam(super.getTeam());
        tmp.setArmour();
        tmp.mana = mana;

        if( this.Armour==null )tmp.Armour=new String[0];
        else tmp.Armour = Arrays.copyOf
                (this.Armour,
                        this.Armour.length);

        if (super.getTeam().equals("Dire")){
            try {
                tmp.specialForDireClone();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (tmp.getName().contains("MEGA")){
            try {
                tmp.specialForMega();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return tmp;
    }


}
