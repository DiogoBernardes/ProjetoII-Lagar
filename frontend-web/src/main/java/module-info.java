module Projeto.Lagar.frontend.web.main {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires Projeto.Lagar.database.main;
    requires java.validation;
    requires org.apache.tomcat.embed.core;

    opens IPVC to spring.core;
    opens IPVC.Controller to spring.beans;

    exports IPVC;
    exports IPVC.Controller;
    exports IPVC.models;


}