package IPVC.BLL;

import IPVC.DAL.ProdutoEMB;
import IPVC.DAL.ProdutoMP;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ProdutoEMBBLL {
    public static List<ProdutoEMB> index() { return Database.query("produtoEMB.index").getResultList(); }

    public static ProdutoEMB get(int id){ return Database.find(ProdutoEMB.class, id); }

    public static void create(ProdutoEMB entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(ProdutoEMB entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        ProdutoEMB entity = get(id);
        entity.setDeleted_on(Timestamp.from(Instant.now()));
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("produtoEMB.count").getSingleResult()).intValue(); }

    public static void removeByEmbalamento(int idEmbalamento) {
        List<ProdutoEMB> produtoEMBS = Database.query("produtoEMB.findByEmbalamento").setParameter("idEmbalamento", idEmbalamento).getResultList();
        Database.beginTransaction();
        for (ProdutoEMB prodEMB : produtoEMBS) {
            Database.delete(prodEMB);
        }
        Database.commitTransaction();
    }
}
