package IPVC.BLL;

import IPVC.DAL.Fatura;
import IPVC.Database.Database;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class FaturaBLL {

        public static List<Fatura> index() { return Database.query("fatura.index").getResultList(); }

        public static Fatura get(int id){ return Database.find(Fatura.class, id); }

        public static void create(Fatura entity){
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        }
        public static void update(Fatura entity){
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        }

        public static void remove(int id){
            Fatura entity = get(id);
            entity.setDeleted_on(Timestamp.from(Instant.now()));

            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        }

        public static int count() { return ((Long) Database.query("fatura.count").getSingleResult()).intValue(); }
}
