package IPVC.BLL;

import IPVC.DAL.Entidade;
import IPVC.DAL.Utilizador;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class EntidadeBLL {

    public static List<Entidade> index() { return Database.query("entidade.index").getResultList(); }
    public static List<Entidade> getEntities(int TipoEntidade) { return Database.query("entidade.getClients").setParameter("idTipoEntidade", TipoEntidade)
            .getResultList();}
    public static Entidade getNIF(int NIF) { List<Entidade> entidades = Database.query("entidade.getNIF").setParameter("NIF", NIF).getResultList();
        return entidades.isEmpty() ? null : entidades.get(0);
    }

    public static Entidade getDataLogin(String username, String password) { return (Entidade) Database.query("entidade.getDataLogin").
            setParameter("username", username)
            .setParameter("pass",password)
            .getResultStream().findFirst().orElse(null); }

    public static Entidade getEmail(String Email) {List<Entidade> entidades = Database.query("entidade.getEmail").setParameter("Email", Email).getResultList();
        return entidades.isEmpty() ? null : entidades.get(0);
    }
    public static Entidade getUsername(String Username) {List<Entidade> entidades = Database.query("entidade.getUsername").setParameter("Username", Username).getResultList();
        return entidades.isEmpty() ? null : entidades.get(0);
    }
    public static Entidade getTelemovel(int Telemovel) {List<Entidade> entidades = Database.query("entidade.getTelemovel").setParameter("Telemovel", Telemovel).getResultList();
        return entidades.isEmpty() ? null : entidades.get(0);
    }
    public static Entidade getEntityByName(String Nome) { List<Entidade> entidades = Database.query("entidade.getEntityByName").setParameter("Nome", Nome).getResultList();
        return entidades.isEmpty() ? null : entidades.get(0);
    }
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
        entity.setDeleted_on(Timestamp.from(Instant.now()));

        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() { return ((Long) Database.query("entidade.count").getSingleResult()).intValue(); }

    public static boolean checkNIF(String nif){
        Entidade entidade = getNIF(Integer.parseInt(nif));
        return entidade == null && nif.length() == 9;
    }

    public static boolean checkEmail(String email){
        Entidade entidade = getEmail(email);
        return entidade == null;
    }

    public static boolean checkUsername(String username){
        Entidade entidade = getUsername(username);
        return entidade == null;
    }

    public static boolean checkTelemovel(String telemovel){
        Entidade entidade = getTelemovel(Integer.parseInt(telemovel));
        return entidade == null && telemovel.length() == 9;
    }

    public static boolean checkLogin(String username, String password){
        Entidade entidade = getDataLogin(username, password);
        return entidade != null;
    }

}
