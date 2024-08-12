package CajeroBase;

/**
 *
 * @author Leon
 */


import java.util.List;

public class Retirar implements Runnable {

    private List<String> dinero;
    private double montoRetiro; // Cambiado a double para que sea consistente con el saldo

    public Retirar(List<String> dinero, double montoRetiro) {
        this.dinero = dinero;
        this.montoRetiro = montoRetiro;
    }

    @Override
    public void run() {
        synchronized (dinero) {
            double saldoActual = SuperClaseSaldo.getSaldo(); // Obtener el saldo de manera segura

            // Verificar si hay suficiente saldo
            if (saldoActual < montoRetiro) {
                System.out.println("Saldo insuficiente para retirar " + montoRetiro);
                return; // Salir si no hay suficiente saldo
            }

            // Actualizar el saldo
            SuperClaseSaldo.actualizarSaldo(-montoRetiro); // Pasar el monto como negativo para el retiro
            dinero.notify(); // Notificar otros hilos si es necesario
            System.out.println("Se ha retirado " + montoRetiro);
        }
    }
}
