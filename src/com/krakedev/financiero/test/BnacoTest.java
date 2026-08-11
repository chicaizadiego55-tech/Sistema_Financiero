package com.krakedev.financiero.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BnacoTest {

    @Test
    public void testCrearCuentaCodigosConsecutivos() {
        Banco banco = new Banco();
        Cliente c1 = new Cliente("1712345678", "Ana", "Torres");
        Cliente c2 = new Cliente("1787654321", "Carlos", "Mendoza");

        Cuenta cuenta1 = banco.crearCuenta(c1);
        Cuenta cuenta2 = banco.crearCuenta(c2);

        assertEquals("1000", cuenta1.getId());
        assertEquals("1001", cuenta2.getId());
        assertEquals("A", cuenta1.getTipo());
        assertEquals("Ana", cuenta1.getPropietario().getNombre());
    }

    @Test
    public void testDepositarValidaciones() {
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");

        assertTrue(banco.depositar(100.0, cuenta));
        assertEquals(100.0, cuenta.getSaldoActual());

        assertFalse(banco.depositar(0.0, cuenta));
        assertFalse(banco.depositar(-50.0, cuenta));
        assertEquals(100.0, cuenta.getSaldoActual());
    }

    @Test
    public void testRetirarValidaciones() {
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");
        banco.depositar(200.0, cuenta);

        assertTrue(banco.retirar(50.0, cuenta));
        assertEquals(150.0, cuenta.getSaldoActual());

        // Intento de retirar más del saldo disponible
        assertFalse(banco.retirar(300.0, cuenta));
        // Intento de retirar monto negativo o cero
        assertFalse(banco.retirar(-10.0, cuenta));
        assertEquals(150.0, cuenta.getSaldoActual());
    }

    @Test
    public void testTransferirExitosoYFallido() {
        Banco banco = new Banco();
        Cuenta origen = new Cuenta("1000");
        Cuenta destino = new Cuenta("1001");

        banco.depositar(500.0, origen);

        // Transferencia válida
        assertTrue(banco.transferir(origen, destino, 200.0));
        assertEquals(300.0, origen.getSaldoActual());
        assertEquals(200.0, destino.getSaldoActual());

        // Transferencia con saldo insuficiente
        assertFalse(banco.transferir(origen, destino, 400.0));
        assertEquals(300.0, origen.getSaldoActual());
        assertEquals(200.0, destino.getSaldoActual());
    }
}