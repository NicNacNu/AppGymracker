package com.example.gymtracker;

import java.time.LocalDate;
import java.util.Date;

public class TrainingsDaten {

    // Attribute der Klasse
    private int uebungsNummer;
    private int trainingsTagNummer;
    private int arbeitsSatzAnfangsGewicht;
    private int arbeitsSatzAnfangsWiederholung;
    private int arbeitsSatzMaxGewicht;
    private int arbeitsSatzMaxWiederholung;
    private Date trainingsDatum;

    // Konstruktor
    public TrainingsDaten(int uebungsNummer, int trainingsTagNummer, int arbeitsSatzAnfangsGewicht,
                          int arbeitsSatzAnfangsWiederholung, int arbeitsSatzMaxGewicht,
                          int arbeitsSatzMaxWiederholung, Date trainingsDatum) {
        this.uebungsNummer = uebungsNummer;
        this.trainingsTagNummer = trainingsTagNummer;
        this.arbeitsSatzAnfangsGewicht = arbeitsSatzAnfangsGewicht;
        this.arbeitsSatzAnfangsWiederholung = arbeitsSatzAnfangsWiederholung;
        this.arbeitsSatzMaxGewicht = arbeitsSatzMaxGewicht;
        this.arbeitsSatzMaxWiederholung = arbeitsSatzMaxWiederholung;
        this.trainingsDatum = trainingsDatum;
    }

    // Getter und Setter
    public int getUebungsNummer() {
        return uebungsNummer;
    }

    public void setUebungsNummer(int uebungsNummer) {
        this.uebungsNummer = uebungsNummer;
    }

    public int getTrainingsTagNummer() {
        return trainingsTagNummer;
    }

    public void setTrainingsTagNummer(int trainingsTagNummer) {
        this.trainingsTagNummer = trainingsTagNummer;
    }

    public int getArbeitsSatzAnfangsGewicht() {
        return arbeitsSatzAnfangsGewicht;
    }

    public void setArbeitsSatzAnfangsGewicht(int arbeitsSatzAnfangsGewicht) {
        this.arbeitsSatzAnfangsGewicht = arbeitsSatzAnfangsGewicht;
    }

    public int getArbeitsSatzAnfangsWiederholung() {
        return arbeitsSatzAnfangsWiederholung;
    }

    public void setArbeitsSatzAnfangsWiederholung(int arbeitsSatzAnfangsWiederholung) {
        this.arbeitsSatzAnfangsWiederholung = arbeitsSatzAnfangsWiederholung;
    }

    public int getArbeitsSatzMaxGewicht() {
        return arbeitsSatzMaxGewicht;
    }

    public void setArbeitsSatzMaxGewicht(int arbeitsSatzMaxGewicht) {
        this.arbeitsSatzMaxGewicht = arbeitsSatzMaxGewicht;
    }

    public int getArbeitsSatzMaxWiederholung() {
        return arbeitsSatzMaxWiederholung;
    }

    public void setArbeitsSatzMaxWiederholung(int arbeitsSatzMaxWiederholung) {
        this.arbeitsSatzMaxWiederholung = arbeitsSatzMaxWiederholung;
    }

    public Date getTrainingsDatum() {
        return trainingsDatum;
    }

    public void setTrainingsDatum(Date trainingsDatum) {
        this.trainingsDatum = trainingsDatum;
    }

    // Überschreibe die toString-Methode für eine lesbare Darstellung
    @Override
    public String toString() {
        return "TrainingsDaten{" +
                "uebungsNummer=" + uebungsNummer +
                ", trainingsTagNummer=" + trainingsTagNummer +
                ", arbeitsSatzAnfangsGewicht=" + arbeitsSatzAnfangsGewicht +
                ", arbeitsSatzAnfangsWiederholung=" + arbeitsSatzAnfangsWiederholung +
                ", arbeitsSatzMaxGewicht=" + arbeitsSatzMaxGewicht +
                ", arbeitsSatzMaxWiederholung=" + arbeitsSatzMaxWiederholung +
                ", trainingsDatum=" + trainingsDatum +
                '}';
    }
}
