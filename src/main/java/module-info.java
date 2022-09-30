module com.example.hundirlaflotaf {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.hundirlaflotaf to javafx.fxml;
    exports com.example.hundirlaflotaf;
}