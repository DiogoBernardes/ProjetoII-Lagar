package IPVC.views;

import IPVC.DAL.Utilizador;

public class Session {
    private static Session instance;
    private Utilizador currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Utilizador getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilizador currentUser) {
        this.currentUser = currentUser;
    }
}
