package CajeroBase;

/**
 *
 * @author Leon
 */

public class ConsultarSaldo implements Runnable {

    @Override
    public void run() {
        synchronized (SuperClaseSaldo.class) {
            // Obtener el saldo de manera segura
            double saldo = SuperClaseSaldo.getSaldo();
            System.out.println("El saldo actual es: " + saldo);
        }
    }
}
