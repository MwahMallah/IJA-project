package org.vut.ija_project.Common;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
