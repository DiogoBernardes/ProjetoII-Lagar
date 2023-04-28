package IPVC.BLL;

import IPVC.DAL.CodigoPostal;
import IPVC.Database.Database;

import java.util.List;

public class CodigoPostalBLL {

    public static List<CodigoPostal> index() { return Database.query("cp.index").getResultList(); }

    public static CodigoPostal get(int id){ return Database.find(CodigoPostal.class, id); }

    public static void create(CodigoPostal entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(CodigoPostal entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        CodigoPostal entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("cp.count").getSingleResult()).intValue(); }

}
