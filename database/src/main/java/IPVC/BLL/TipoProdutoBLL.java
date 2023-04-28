package IPVC.BLL;

import IPVC.DAL.TipoProduto;
import IPVC.Database.Database;

import java.util.List;

public class TipoProdutoBLL {
    public static List<TipoProduto> index() { return Database.query("tipoProduto.index").getResultList(); }

    public static TipoProduto get(int id){ return Database.find(TipoProduto.class, id); }

    public static void create(TipoProduto entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(TipoProduto entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        TipoProduto entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("tipoProduto.count").getSingleResult()).intValue(); }
}