package ca.cmpt213.as5.model;

/**
 * Required to create the interface for our
 * observable Course Object
 */


public interface Observable {
    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    void notifyAddObservers(Offering newOffering,Component newComponent);
}
