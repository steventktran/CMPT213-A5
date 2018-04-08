package ca.cmpt213.as5.model;

public interface Observable {
    void add(Observer observer);
    void delete(Observer observer);
    void notifyAddObservers(Offering newOffering,Component newComponent);
}
