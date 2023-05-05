package IPVC.BLL;

import IPVC.DAL.Login;
import IPVC.Database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class LoginBLL {


        public static List<Login> index() { return Database.query("login.index").getResultList(); }

        public static Login get(int id){ return Database.find(Login.class, id); }

        public static void create(Login entity){
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        }
        public static void update(Login entity){
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        }

        public static void remove(int id){
            Login entity = get(id);
            entity.setDeleted_on(Timestamp.from(Instant.now()));
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        }

        public static int count() { return ((Long) Database.query("login.count").getSingleResult()).intValue(); }



}
