package org.mypkg;

public class GameObjectFactory {
    public GameObject createGameObject(String number) {
        return new GameObject(number);
    }
}
