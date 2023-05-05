package IPVC.BLL;

import IPVC.DAL.TipoEntidade;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TipoEntidadeBLL {
    public static List<TipoEntidade> index() { return Database.query("tipoEntidade.index").getResultList(); }

    public static TipoEntidade get(int id){ return Database.find(TipoEntidade.class, id); }

    public static void create(TipoEntidade entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(TipoEntidade entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        TipoEntidade entity = get(id);
        entity.setDeleted_on(Timestamp.from(Instant.now()));
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("tipoEntidade.count").getSingleResult()).intValue(); }
}
