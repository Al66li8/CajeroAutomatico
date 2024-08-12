package CajeroBase;
import java.util.List;

public class Deposito implements Runnable {

    private List<String> dinero;
    private double montoDeposito; // Cambiado a double para que sea consistente con el saldo

    public Deposito(List<String> dinero, double montoDeposito) {
        this.dinero = dinero;
        this.montoDeposito = montoDeposito;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // Esperar 2 segundos entre depósitos

                synchronized (dinero) {
                    SuperClaseSaldo.actualizarSaldo(montoDeposito); // Actualizar saldo de manera segura
                    dinero.notify(); // Notificar otros hilos si es necesario
                    System.out.println("Se ha depositado " + montoDeposito);
                }
            } catch (InterruptedException e) {
                System.out.println("Error en el hilo de depósito: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                break; // Salir del bucle si el hilo está interrumpido
            }
        }
    }
}
