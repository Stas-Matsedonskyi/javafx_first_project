package com.company;

import com.company.Creep;
import com.company.Main;
import com.company.Melee;
import com.company.Ranged;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KeyPressHandler implements EventHandler<KeyEvent>{

    @Override
    public void handle(KeyEvent event) {
        for(int i = 0; i < Main.creepsR.size(); i++){
            if(Main.creepsR.size() > 0){
                if(Main.creepsR.get(i).getActive()){
                    if (event.getCode().equals(KeyCode.W) && !event.isShiftDown()){
                        Main.creepsR.get(i).move(0,-5);
                    }

                    if (event.getCode().equals(KeyCode.D)){
                        Main.creepsR.get(i).move(5,0);
                    }

                    if (event.getCode().equals(KeyCode.S)){
                        Main.creepsR.get(i).move(0,5);
                    }

                    if (event.getCode().equals(KeyCode.A)){
                        Main.creepsR.get(i).move(-5,0);
                    }
                }
            }
        }

        for(int i = 0; i < Main.creepsD.size(); i++){
            if(Main.creepsD.size() > 0){
                if(Main.creepsD.get(i).getActive()){
                    if (event.getCode().equals(KeyCode.W)){
                        Main.creepsD.get(i).move(0,-5);
                    }

                    if (event.getCode().equals(KeyCode.D)){
                        Main.creepsD.get(i).move(5,0);
                    }

                    if (event.getCode().equals(KeyCode.S)){
                        Main.creepsD.get(i).move(0,5);
                    }

                    if (event.getCode().equals(KeyCode.A)){
                        Main.creepsD.get(i).move(-5,0);
                    }
                }
            }
        }

        if (event.getCode().equals(KeyCode.INSERT)){
            Window w = new Window();
            w.dialog.show();
        }

        if (event.getCode().equals(KeyCode.DELETE)){
            ArrayList<Creep> creepsToDel = new ArrayList<Creep>();
            for(Creep creep : Main.creepsR){
                if(creep.getActive()){
                    creepsToDel.add(creep);
                    Main.StatusBarInfo(creep, false);
                    Main.map.deleteUnit(creep);
                }
            }

            for(Creep creep : Main.creepsD){
                if(creep.getActive()){
                    creepsToDel.add(creep);
                    Main.StatusBarInfo(creep, false);
                }
            }

            for(Creep creep : creepsToDel){
                Main.group.getChildren().remove(creep.creeps);
                if(creep.getTeam().equals("Radiant")){
                    Main.creepsR.remove(creep);
                }else{
                    Main.creepsD.remove(creep);
                }
            }
        }

        if (event.getCode().equals(KeyCode.ESCAPE)){
            for(Creep creep : Main.creepsR){
                if(creep.getActive()){
                    creep.resetActive();
                    creep.creepWrapper.setStyle(" ");
                    Main.StatusBarInfo(creep, false);
                }
            }

            for(Creep creep : Main.creepsD){
                if(creep.getActive()){
                    creep.resetActive();
                    creep.creepWrapper.setStyle(" ");
                    Main.StatusBarInfo(creep, false);
                }
            }
        }

        if(event.getCode().equals(KeyCode.B)){

            for (Creep creep : Main.creepsR) {
                creep.setActBar(true);
                creep.setXDest(470);
                creep.setYDest(2300);
                creep.setSpeed(50);
            }


            for (Creep creep : Main.creepsD){
                creep.setActBar(true);
                creep.setXDest(2800);
                creep.setYDest(150);
                creep.setSpeed(50);
            }

        }

        if (event.getCode().equals(KeyCode.N)){
            Creep tmp = null;
            Main.barracks[0].resetBar();

            System.out.println(Main.barracks[0].sizeR());

            while(Main.barracks[0].checkEmptyR()) {
                for (int i = 0; i <= Main.barracks[0].sizeR(); i++) {
                    tmp = Main.barracks[0].getAndRemoveR();
                    Main.creepsR.add(tmp);
                    System.out.println(tmp);
                }
            }

            for (Creep creep1 : Main.creepsR){
                Main.group.getChildren().add(creep1.creeps);
                creep1.setSpeed(creep1.getSpeedInitial());
            }

            Main.barracks[1].resetBar();

            while(Main.barracks[1].checkEmptyD()) {
                for (int i = 0; i <= Main.barracks[1].sizeD(); i++) {
                    Main.creepsD.add(Main.barracks[1].getAndRemoveD());
                }
            }

            for (Creep creep2 : Main.creepsD){
                Main.group.getChildren().add(creep2.creeps);
                creep2.setSpeed(creep2.getSpeedInitial());
            }

        }

        if (event.getCode().equals(KeyCode.R)){
            WindowMonster wm = new WindowMonster();
            wm.windowM.show();
        }

    }
}