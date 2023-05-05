package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.DAL.Produto;
import IPVC.DAL.TipoProduto;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TipoProdutoBLL {
    public static List<TipoProduto> index() { return Database.query("tipoProduto.index").getResultList(); }
    public static TipoProduto getByDescription(String descricao) {  List<TipoProduto> resultList = Database.query("tipoProduto.getByDescription")
            .setParameter("descricao", descricao)
            .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);}

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
        entity.setDeleted_on(Timestamp.from(Instant.now()));
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("tipoProduto.count").getSingleResult()).intValue(); }
}