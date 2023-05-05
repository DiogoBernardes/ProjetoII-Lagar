package IPVC.BLL;

import IPVC.DAL.LinhaFatura;
import IPVC.DAL.ProdutoMP;
import IPVC.Database.Database;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ProdutoMPBLL {

    public static List<ProdutoMP> index() { return Database.query("produtoMP.index").getResultList(); }

    public static ProdutoMP get(int id){ return Database.find(ProdutoMP.class, id); }

    public static void create(ProdutoMP entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(ProdutoMP entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        ProdutoMP entity = get(id);
        entity.setDeleted_on(Timestamp.from(Instant.now()));
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("produtoMP.count").getSingleResult()).intValue(); }

    public static void removeByProducao(int idProducao) {
        List<ProdutoMP> produtoMPS = Database.query("produtoMP.findByProducao").setParameter("idProducao", idProducao).getResultList();
        Database.beginTransaction();
        for (ProdutoMP prodMP : produtoMPS) {
            Database.delete(prodMP);
        }
        Database.commitTransaction();
    }
}
