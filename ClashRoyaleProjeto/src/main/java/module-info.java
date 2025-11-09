module br.edu.ifsp.clashroyaleprojeto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens br.edu.ifsp.clashroyaleprojeto to javafx.fxml;
    exports br.edu.ifsp.clashroyaleprojeto;
}