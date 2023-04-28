package IPVC.BLL;

import IPVC.DAL.LinhaFatura;
import IPVC.Database.Database;

import java.util.List;

public class LinhaFaturaBLL {
    public static List<LinhaFatura> index() { return Database.query("linhaFatura.index").getResultList(); }

    public static LinhaFatura get(int id){ return Database.find(LinhaFatura.class, id); }

    public static void create(LinhaFatura entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(LinhaFatura entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        LinhaFatura entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("linhaFatura.count").getSingleResult()).intValue(); }
}
