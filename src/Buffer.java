import java.util.concurrent.Semaphore;

public class Buffer {
    private int dato;
    private boolean disponible = false;

    private Semaphore puedeEscribir = new Semaphore(1);
    private Semaphore puedeLeer = new Semaphore(0);

    public void poner(int valor) throws InterruptedException {
        puedeEscribir.acquire();

        this.dato = valor;
        this.disponible = true;

        puedeLeer.release();
    }

    public int recoger() throws InterruptedException {
        puedeLeer.acquire();

        int valorLeido = this.dato;
        this.disponible = false;

        puedeEscribir.release();
        return valorLeido;
    }
}
