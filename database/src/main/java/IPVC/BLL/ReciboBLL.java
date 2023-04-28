package IPVC.BLL;

import IPVC.DAL.Recibo;
import IPVC.Database.Database;

import java.util.List;

public class ReciboBLL {
    public static List<Recibo> index() { return Database.query("recibo.index").getResultList(); }

    public static Recibo get(int id){ return Database.find(Recibo.class, id); }

    public static void create(Recibo entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Recibo entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Recibo entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("recibo.count").getSingleResult()).intValue(); }
}
