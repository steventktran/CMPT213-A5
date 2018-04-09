package ca.cmpt213.as5.model;

public interface Observable {
    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    void notifyAddObservers(Offering newOffering,Component newComponent);
}
