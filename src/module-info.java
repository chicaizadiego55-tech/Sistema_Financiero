module Financiero {
    
    requires org.junit.jupiter.api;

    exports com.krakedev.financiero.entidades;
    exports com.krakedev.financiero.servicios;
    
    opens com.krakedev.financiero.test to org.junit.platform.commons;
}