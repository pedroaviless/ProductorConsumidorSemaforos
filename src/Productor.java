import java.util.Random;

public class Productor extends Thread {
    private Buffer buffer;
    private int numConsumidores;

    public Productor(Buffer buffer, int numConsumidores) {
        this.buffer = buffer;
        this.numConsumidores = numConsumidores;
    }

    @Override
    public void run() {
        Random random = new Random();
        System.out.println(Colores.AZUL + "[PRODUCTOR] Inicia producción..." + Colores.RESET);

        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(20) + 1;

            try {
                System.out.println(
                        Colores.CIAN + "[PRODUCTOR] Generado → " +
                                Colores.VERDE + num + Colores.RESET
                );

                buffer.poner(num);
                Thread.sleep(700);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Señales de fin para consumidores
        for (int i = 1; i <= numConsumidores; i++) {
            try {
                System.out.println(
                        Colores.AMARILLO +
                                "[PRODUCTOR] Señal de fin enviada para consumidor " + i +
                                Colores.RESET
                );
                buffer.poner(-1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(
                Colores.AZUL + "[PRODUCTOR] Finaliza producción." + Colores.RESET
        );
    }
}
