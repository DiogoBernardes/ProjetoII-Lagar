package IPVC.BLL;

import IPVC.DAL.Embalamento;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class EmbalamentoBLL {
    public static List<Embalamento> index() { return Database.query("embalamento.index").getResultList(); }

    public static Embalamento get(int id){ return Database.find(Embalamento.class, id); }

    public static void create(Embalamento entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Embalamento entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Embalamento entity = get(id);
        entity.setDeleted_on(Timestamp.from(Instant.now()));

        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("embalamento.count").getSingleResult()).intValue(); }

}
