package org.mypkg;

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(boolean correctAnswer);
}
