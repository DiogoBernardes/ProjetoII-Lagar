package IPVC.BLL;

import IPVC.DAL.Produto;
import IPVC.DAL.TipoUtilizador;
import IPVC.DAL.Utilizador;
import IPVC.Database.Database;

import java.util.List;

public class TipoUtilizadorBLL {
    public static List<TipoUtilizador> index() { return Database.query("tipoUtilizador.index").getResultList(); }
    public static TipoUtilizador getEmployeeByRole(String Cargo) { List<TipoUtilizador> tipoUtilizadors = Database.query("tipoUtilizador.getEmployeeByRole").setParameter("Cargo", Cargo).getResultList();
        return tipoUtilizadors.isEmpty() ? null : tipoUtilizadors.get(0);
    }

    public static TipoUtilizador get(int id){ return Database.find(TipoUtilizador.class, id); }

    public static void create(TipoUtilizador entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(TipoUtilizador entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        TipoUtilizador entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("tipoUtilizador.count").getSingleResult()).intValue(); }
}
