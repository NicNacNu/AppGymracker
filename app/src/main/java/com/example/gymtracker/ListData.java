package com.example.gymtracker;

public class ListData {

    TrainingsDaten trainingsDaten;
    Trainingstag trainingstag;
    uebung Uebung;

    public ListData(TrainingsDaten trainingsDaten, Trainingstag trainingstag, uebung uebung) {
        this.trainingsDaten = trainingsDaten;
        this.trainingstag = trainingstag;
        Uebung = uebung;
    }

    // Getter Methoden
    public TrainingsDaten getTrainingsDaten() {
        return trainingsDaten;
    }

    public Trainingstag getTrainingstag() {
        return trainingstag;
    }

    public uebung getUebung() {
        return Uebung;
    }
}
