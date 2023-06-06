package IPVC.utils;

import IPVC.DAL.Entidade;

public class Session {
    private static Session instance;


    private Entidade currentEntitie;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Entidade getCurrentEntitie() {
        return currentEntitie;
    }

    public void setCurrentEntitie(Entidade currentEntitie) {
        this.currentEntitie = currentEntitie;
    }
}
