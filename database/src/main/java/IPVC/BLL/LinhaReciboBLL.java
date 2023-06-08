package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.DAL.LinhaFatura;
import IPVC.DAL.LinhaRecibo;
import IPVC.DAL.Recibo;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class LinhaReciboBLL {
    public static List<LinhaRecibo> index() { return Database.query("linhaRecibo.index").getResultList(); }

    public static LinhaRecibo get(int id){ return Database.find(LinhaRecibo.class, id); }

    public static List<LinhaRecibo> getProductsByRecibo(Recibo recibo) { return Database.query("linhaRecibo.findProductsByRecibo").setParameter("recibo", recibo).getResultList();
    }
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
        entity.setDeleted_on(Timestamp.from(Instant.now()));
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("linhaRecibo.count").getSingleResult()).intValue(); }

    public static void removeByRecibo(int idRecibo) {
        List<LinhaRecibo> linhasRecibo = Database.query("linhaRecibo.findByRecibo").setParameter("idRecibo", idRecibo).getResultList();
        Database.beginTransaction();
        for (LinhaRecibo lr : linhasRecibo) {
            lr.setDeleted_on(Timestamp.from(Instant.now()));
            Database.update(lr);
        }
        Database.commitTransaction();
    }


}