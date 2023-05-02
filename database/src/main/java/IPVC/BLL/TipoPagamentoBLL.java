package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.DAL.TipoPagamento;
import IPVC.Database.Database;

import java.util.List;

public class TipoPagamentoBLL {
    public static List<TipoPagamento> index() { return Database.query("tipoPagamento.index").getResultList(); }
    public static TipoPagamento getPaymentByDescription(String descricao) { List<TipoPagamento> tipoPagamentos = Database.query("tipoPagamento.getPaymentByDescription").setParameter("descricao", descricao).getResultList();
        return tipoPagamentos.isEmpty() ? null : tipoPagamentos.get(0);
    }
    public static TipoPagamento get(int id){ return Database.find(TipoPagamento.class, id); }

    public static void create(TipoPagamento entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(TipoPagamento entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        TipoPagamento entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("tipoPagamento.count").getSingleResult()).intValue(); }
}
