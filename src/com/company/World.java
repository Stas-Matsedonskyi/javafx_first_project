package com.company;

import java.util.*;

public class World {
    private Deque<Barrack> macro = new LinkedList();
    private Map<String, Creep> units = new HashMap<>();

    public void addUnit(String name, Creep creep) {
        units.put(name, creep);
    }

    public void addMacro(Barrack barrack) {
        macro.add(barrack);
    }

    public void displayUnit() {
        units.forEach((a, b) -> System.out.println("Ім'я: " + a + "   " + "Характеристики: " + b));
    }

    public Creep search(String name) {
        return units.get(name);
    }

    public void displayMacro() {
        for (Barrack b : macro) {
            System.out.println(b);
        }
    }

    public int countU(String s) {
        int c = 0;
        Iterator<Map.Entry<String, Creep>> iterator = units.entrySet().iterator();
        for (int i = 0; i < units.size(); ++i) {
            if (iterator.hasNext()) {
                Map.Entry<String, Creep> entry = iterator.next();
                if (entry.getValue().getTeam().equals(s)) {
                    c++;
                }
            }
        }
        return c;
    }

    public int countA(String s) {
        int c = 0;
        Iterator<Map.Entry<String, Creep>> iterator = units.entrySet().iterator();
        for (int i = 0; i < units.size(); ++i) {
            if (iterator.hasNext()) {
                Map.Entry<String, Creep> entry = iterator.next();
                if (entry.getValue().getState().equals(s)) {
                    c++;
                }
            }
        }
        return c;
    }

    public void cleanD(String s) {
        units.values().removeIf(value -> value.getState().equals(s));
    }

}

