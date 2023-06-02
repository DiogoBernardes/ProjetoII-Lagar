module Projeto.Lagar.frontend.web.main {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires Projeto.Lagar.database.main;

    opens IPVC to spring.core;
    opens IPVC.Controller to spring.beans;

    exports IPVC;
    exports IPVC.Controller;


}