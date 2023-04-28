package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.Database.Database;

import java.util.List;

public class EntidadeBLL {

    public static List<Entidade> index() { return Database.query("entidade.index").getResultList(); }

    public static List<Entidade> getClients(int TipoEntidade) { return Database.query("entidade.getClients").setParameter("idTipoEntidade", TipoEntidade)
            .getResultList();}

    public static Entidade get(int id){ return Database.find(Entidade.class, id); }

    public static void create(Entidade entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Entidade entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Entidade entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("entidade.count").getSingleResult()).intValue(); }

    public static boolean checkClient(int idTipoEntidade){
        Entidade entidade = index().stream().filter(e -> e.getTipoEntidade().getId_TipoEntidade() == idTipoEntidade).findFirst().orElse(null);
        return entidade != null;
    }
}
