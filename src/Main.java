public class Main {
    public static void main(String[] args) {

        final int NUM_CONSUMIDORES = 2;

        System.out.println(Colores.AZUL + "=== INICIO DEL PROGRAMA ===" + Colores.RESET);

        Buffer buffer = new Buffer();
        Productor productor = new Productor(buffer, NUM_CONSUMIDORES);
        Consumidor[] consumidores = new Consumidor[NUM_CONSUMIDORES];

        // Crear consumidores
        for (int i = 0; i < NUM_CONSUMIDORES; i++) {
            consumidores[i] = new Consumidor(i + 1, buffer);
        }

        productor.start();
        for (Consumidor c : consumidores) {
            c.start();
        }

        try {
            productor.join();
            for (Consumidor c : consumidores) {
                c.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                Colores.AZUL +
                        "\n=== TODOS LOS HILOS HAN FINALIZADO CORRECTAMENTE ===" +
                        Colores.RESET
        );
    }
}
