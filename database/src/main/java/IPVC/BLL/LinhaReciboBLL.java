package IPVC.BLL;

import IPVC.DAL.LinhaRecibo;
import IPVC.Database.Database;

import java.util.List;

public class LinhaReciboBLL {
    public static List<LinhaRecibo> index() { return Database.query("linhaRecibo.index").getResultList(); }

    public static LinhaRecibo get(int id){ return Database.find(LinhaRecibo.class, id); }

    public static void create(LinhaRecibo entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(LinhaRecibo entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        LinhaRecibo entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("linhaRecibo.count").getSingleResult()).intValue(); }
}