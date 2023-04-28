module Projeto.Lagar.frontend.desktop.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires Projeto.Lagar.database.main;

    opens IPVC.views to javafx.fxml;
    opens IPVC.Admin to javafx.fxml;
    exports IPVC;
}


