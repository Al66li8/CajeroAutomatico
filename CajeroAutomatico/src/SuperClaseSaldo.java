package CajeroBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SuperClaseSaldo {
    private static final double LIMITE_SALDO = 500000.00; // Límite máximo de saldo
    private static double saldo = 300000.00; // Saldo inicial
    private static final Lock saldoLock = new ReentrantLock();
    private static final String SALDO_FILE = "saldo.txt";

    static {
        cargarSaldo(); // Cargar saldo desde el archivo al iniciar la aplicación
    }

    // Obtener el saldo de manera segura
    public static double getSaldo() {
        saldoLock.lock();
        try {
            return saldo;
        } finally {
            saldoLock.unlock();
        }
    }

    // Actualizar el saldo de manera segura y persistir en el archivo
    public static void actualizarSaldo(double monto) {
        saldoLock.lock();
        try {
            double nuevoSaldo = saldo + monto;
            if (nuevoSaldo > LIMITE_SALDO) {
                System.err.println("Error: El saldo no puede superar el límite de " + LIMITE_SALDO);
                return;
            }
            saldo = nuevoSaldo;
            guardarSaldo(); // Persistir el saldo actualizado
        } finally {
            saldoLock.unlock();
        }
    }

    // Cargar saldo desde el archivo
    private static void cargarSaldo() {
        File file = new File(SALDO_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                saldo = Double.parseDouble(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                // Manejar errores, iniciar saldo en 0 si el archivo está corrupto
                saldo = 0;
                System.err.println("Error cargando el saldo: " + e.getMessage());
            }
        }
    }

    // Guardar saldo en el archivo
    private static void guardarSaldo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALDO_FILE))) {
            writer.write(Double.toString(saldo));
        } catch (IOException e) {
            // Manejar errores de escritura
            System.err.println("Error guardando el saldo: " + e.getMessage());
        }
    }
}
