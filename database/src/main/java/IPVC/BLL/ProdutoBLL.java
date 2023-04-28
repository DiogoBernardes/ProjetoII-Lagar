package IPVC.BLL;

import IPVC.DAL.Produto;
import IPVC.Database.Database;

import java.util.List;

public class ProdutoBLL {
    public static List<Produto> index() { return Database.query("produto.index").getResultList(); }

    public static Produto get(int id){ return Database.find(Produto.class, id); }

    public static void create(Produto entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Produto entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Produto entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("produto.count").getSingleResult()).intValue(); }
}

