module Projeto.Lagar.database.main {
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;
    opens IPVC.DAL to org.hibernate.orm.core;
    exports IPVC.BLL;
    exports IPVC.DAL;
}