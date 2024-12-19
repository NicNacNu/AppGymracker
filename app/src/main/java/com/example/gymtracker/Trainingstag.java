package com.example.gymtracker;

public class Trainingstag {

    // Attribute der Klasse
    private int trainingsTagNummer;
    private String trainingstagName;
    private String wochentagName;

    // Konstruktor
    public Trainingstag(int trainingsTagNummer, String trainingstagName, String wochentagName) {
        this.trainingsTagNummer = trainingsTagNummer;
        this.trainingstagName = trainingstagName;
        this.wochentagName = wochentagName;
    }

    // Getter und Setter
    public int getTrainingsTagNummer() {
        return trainingsTagNummer;
    }

    public void setTrainingsTagNummer(int trainingsTagNummer) {
        this.trainingsTagNummer = trainingsTagNummer;
    }

    public String getTrainingstagName() {
        return trainingstagName;
    }

    public void setTrainingstagName(String trainingstagName) {
        this.trainingstagName = trainingstagName;
    }

    public String getWochentagName() {
        return wochentagName;
    }

    public void setWochentagName(String wochentagName) {
        this.wochentagName = wochentagName;
    }

    // Überschreibe die toString-Methode für eine lesbare Darstellung
    @Override
    public String toString() {
        return "Trainingstag{" +
                "trainingsTagNummer=" + trainingsTagNummer +
                ", trainingstagName='" + trainingstagName + '\'' +
                ", wochentagName='" + wochentagName + '\'' +
                '}';
    }
}

