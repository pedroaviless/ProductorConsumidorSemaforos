public class Consumidor extends Thread {

    private Buffer buffer;
    private int id;

    public Consumidor(int id, Buffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int dato = buffer.recoger();

                if (dato == -1) {
                    System.out.println(
                            Colores.ROJO + "[CONSUMIDOR " + id + "] Recibe señal de fin. Termina." + Colores.RESET
                    );
                    break;
                }

                System.out.println(
                        Colores.MAGENTA + "[CONSUMIDOR " + id + "] Consume → " +
                                Colores.VERDE + dato + Colores.RESET
                );

                Thread.sleep(300);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
