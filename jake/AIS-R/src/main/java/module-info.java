module com.ais.recruit.aisr {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
                            
    opens com.ais.recruit.aisr to javafx.fxml;
    exports com.ais.recruit.aisr;
    exports com.ais.recruit.aisr.controller;
    opens com.ais.recruit.aisr.controller;
}