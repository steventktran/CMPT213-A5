package ca.cmpt213.as5.model;

/**
 * Required to create the interface for our
 * watcher which is a observer
 */

public interface Observer {
    void addUpdate(Offering newOffer, Component component);
}
