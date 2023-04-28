package IPVC.BLL;

import IPVC.DAL.ProdutoMP;
import IPVC.Database.Database;

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
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("produtoMP.count").getSingleResult()).intValue(); }
}
