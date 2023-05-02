package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.DAL.TipoProduto;
import IPVC.DAL.TipoUtilizador;
import IPVC.DAL.Utilizador;
import IPVC.Database.Database;

import java.util.List;

public class UtilizadorBLL {
    public static List<Utilizador> index() { return Database.query("utilizador.index").getResultList(); }

    public static Utilizador getDataLogin(String username, String password) { return (Utilizador) Database.query("utilizador.getDataLogin").
            setParameter("username", username)
            .setParameter("pass",password)
            .getResultStream().findFirst().orElse(null); }

    public static Utilizador getTelemovel(int Telemovel) {List<Utilizador> utilizadores = Database.query("utilizador.getTelemovel").setParameter("Telemovel", Telemovel).getResultList();
        return utilizadores.isEmpty() ? null : utilizadores.get(0);
    }
    public static Utilizador getUserByName(String username) { return (Utilizador) Database.query("utilizador.getUsername").setParameter("username", username).getResultStream().findFirst().orElse(null);
    }
    public static Utilizador getByRole(String cargo) {  List<Utilizador> resultList = Database.query("utilizador.getByRole")
            .setParameter("cargo", cargo)
            .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);}

    public static Utilizador get(int id){ return Database.find(Utilizador.class, id); }

    public static void create(Utilizador entity){
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }
    public static void update(Utilizador entity){
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(int id){
        Utilizador entity = get(id);
        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("utilizador.count").getSingleResult()).intValue(); }

    public static boolean checkLogin(String username, String password){
        Utilizador utilizador = getDataLogin(username, password);
        return utilizador != null;
    }

    public static boolean checkUsername(String username){
        Utilizador utilizador = getUserByName(username);
        System.out.println(utilizador);
        return utilizador == null;
    }
    public static boolean checkTelemovel(String telemovel){
        Utilizador utilizador = getTelemovel(Integer.parseInt(telemovel));
        return utilizador == null && telemovel.length() == 9;
    }
}
