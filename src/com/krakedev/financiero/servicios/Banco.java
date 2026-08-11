package com.krakedev.financiero.servicios;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class Banco {
    private int ultimoCodigo = 1000;

    public Banco() {
    }

    public int getUltimoCodigo() {
        return ultimoCodigo;
    }

    public void setUltimoCodigo(int ultimoCodigo) {
        this.ultimoCodigo = ultimoCodigo;
    }

    public Cuenta crearCuenta(Cliente cliente) {
        String codigoStr = ultimoCodigo + "";
        ultimoCodigo++;
        Cuenta nuevaCuenta = new Cuenta(codigoStr);
        nuevaCuenta.setPropietario(cliente);
        return nuevaCuenta;
    }

    public boolean depositar(double monto, Cuenta cuenta) {
        if (monto > 0 && cuenta != null) {
            cuenta.setSaldoActual(cuenta.getSaldoActual() + monto);
            return true;
        }
        return false;
    }

    public boolean retirar(double monto, Cuenta cuenta) {
        if (monto > 0 && cuenta != null && monto <= cuenta.getSaldoActual()) {
            cuenta.setSaldoActual(cuenta.getSaldoActual() - monto);
            return true;
        }
        return false;
    }

    public boolean transferir(Cuenta origen, Cuenta destino, double monto) {
        if (origen != null && destino != null && retirar(monto, origen)) {
            if (depositar(monto, destino)) {
                return true;
            } else {
                // Si falla el depósito, se restituye el dinero a la cuenta de origen
                depositar(monto, origen);
            }
        }
        return false;
    }
}
