package com.example.gymtracker;


public class uebung {

    // Attribute der Klasse
    private int uebungsNummer;
    private String uebungsName;
    private int anzahlSaetze;
    private int prGewicht;
    private int prWiederholungen;

    // Konstruktor
    public uebung(int uebungsNummer, String uebungsName, int anzahlSaetze, int prGewicht, int prWiederholungen) {
        this.uebungsNummer = uebungsNummer;
        this.uebungsName = uebungsName;
        this.anzahlSaetze = anzahlSaetze;
        this.prGewicht = prGewicht;
        this.prWiederholungen = prWiederholungen;
    }

    // Getter und Setter
    public int getUebungsNummer() {
        return uebungsNummer;
    }

    public String getUebungsName() {
        return uebungsName;
    }

    public void setUebungsName(String uebungsName) {
        this.uebungsName = uebungsName;
    }

    public int getAnzahlSaetze() {
        return anzahlSaetze;
    }

    public void setAnzahlSaetze(int anzahlSaetze) {
        this.anzahlSaetze = anzahlSaetze;
    }

    public int getPrGewicht() {
        return prGewicht;
    }

    public void setPrGewicht(int prGewicht) {
        this.prGewicht = prGewicht;
    }

    public int getPrWiederholungen() {
        return prWiederholungen;
    }

    public void setPrWiederholungen(int prWiederholungen) {
        this.prWiederholungen = prWiederholungen;
    }

    // Überschreibe die toString-Methode für eine lesbare Darstellung
    @Override
    public String toString() {
        return "Uebung{" +
                "uebungsNummer=" + uebungsNummer +
                ", uebungsName='" + uebungsName + '\'' +
                ", anzahlSaetze=" + anzahlSaetze +
                ", prGewicht=" + prGewicht +
                ", prWiederholungen=" + prWiederholungen +
                '}';
    }
}
