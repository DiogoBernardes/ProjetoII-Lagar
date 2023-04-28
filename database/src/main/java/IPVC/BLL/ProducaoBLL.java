package IPVC.BLL;

import IPVC.DAL.Producao;
import IPVC.Database.Database;

import java.util.List;

public class ProducaoBLL {
    public static List<Producao> index() { return Database.query("producao.index").getResultList(); }

    public static Producao get(int id){ return Database.find(Producao.class, id); }

    public static void create(Producao entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Producao entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Producao entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("producao.count").getSingleResult()).intValue(); }
}
