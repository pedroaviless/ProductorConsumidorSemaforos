🎯 Productor – Consumidor con Semáforos (Java)
🧵 Programación Concurrente – Ejercicio Completo y Mejorado

Este proyecto implementa el clásico problema Productor–Consumidor usando solo semáforos, sin synchronized, sin wait() ni notify().
Incluye además varios consumidores, colores en consola y una finalización perfectamente coordinada.

🌐 1. Descripción General

El sistema consta de:

🛠️ Un Productor → genera números aleatorios.

📥 Un Buffer de un hueco → sincronizado con semáforos.

👷 Varios Consumidores → procesan los datos generados.

🏁 Cierre seguro mediante mensajes especiales (-1).

Todo el flujo está controlado mediante:

Semaphore puedeEscribir = new Semaphore(1);
Semaphore puedeLeer = new Semaphore(0);

🧩 2. Arquitectura del Sistema
            ┌──────────────────────┐
            │      Productor       │
            └──────────┬───────────┘
                       │ poner()
                       ▼
              ┌───────────────────┐
              │      Buffer       │
              │   [1 hueco]       │
              └───────┬──────────┘
          recoger()   │
   ┌────────────┬─────┴──────────────┬──────────────┐
   ▼            ▼                     ▼              ▼
Consumidor 1  Consumidor 2     ...   Consumidor N
   │            │                     │              │
   └────────────┴──────────┬─────────┴──────────────┘
                            │
                       Señal de fin (-1)


✔ Solo un hueco → no hace falta mutex
✔ Sin condiciones de carrera
✔ Sin hilos bloqueados al finalizar

🔐 3. Sincronización (solo semáforos)
Semáforo	Inicial	¿Quién lo usa?	Función
puedeEscribir	1	Productor	Permite escribir cuando el hueco está libre
puedeLeer	0	Consumidores	Permite leer cuando existe un dato
🔄 Flujo del Productor
Esperar turno → escribir dato → habilitar lectura

puedeEscribir.acquire();
this.dato = valor;
puedeLeer.release();

🔄 Flujo del Consumidor
Esperar dato → leer → permitir nueva escritura

puedeLeer.acquire();
int valor = this.dato;
puedeEscribir.release();

🏁 4. Finalización del Sistema

Cuando el Productor termina:

➡️ Envía tantos -1 como consumidores existan.

Cada consumidor:

✔ Detecta -1
✔ Muestra mensaje de cierre
✔ Termina su hilo correctamente

🧪 5. Ejecución típica
[PRODUCTOR] Inicia producción...
[PRODUCTOR] Generado → 7
[CONSUMIDOR 1] Consume → 7
[PRODUCTOR] Generado → 12
[CONSUMIDOR 2] Consume → 12
[PRODUCTOR] Señal de fin enviada para consumidor 1
[PRODUCTOR] Señal de fin enviada para consumidor 2
[CONSUMIDOR 1] Recibe señal de fin. Termina.
[CONSUMIDOR 2] Recibe señal de fin. Termina.

=== TODOS LOS HILOS HAN FINALIZADO CORRECTAMENTE ===

📁 6. Archivos del Proyecto
Archivo	Descripción
Buffer.java	Buffer sincronizado con semáforos
Productor.java	Genera números y señales de fin
Consumidor.java	Consume datos y detecta fin
Main.java	Lanza hilos y coordina ejecución
Colores.java	Sistema de colores ANSI
🎨 7. Código estructurado (vista rápida)
🟦 Buffer
public class Buffer {
    private int dato;

    private Semaphore puedeEscribir = new Semaphore(1);
    private Semaphore puedeLeer = new Semaphore(0);

    public void poner(int valor) throws InterruptedException {
        puedeEscribir.acquire();
        this.dato = valor;
        puedeLeer.release();
    }

    public int recoger() throws InterruptedException {
        puedeLeer.acquire();
        int valor = dato;
        puedeEscribir.release();
        return valor;
    }
}

🟩 Productor
for (int i = 0; i < 10; i++) {
    int num = random.nextInt(20) + 1;
    buffer.poner(num);
}

// Señal de fin para cada consumidor
for (int i = 0; i < numConsumidores; i++) {
    buffer.poner(-1);
}

🟪 Consumidor
while (true) {
    int dato = buffer.recoger();
    if (dato == -1) break;
    System.out.println("Consume → " + dato);
}

🟥 Main
Buffer buffer = new Buffer();
Productor productor = new Productor(buffer, NUM_CONSUMIDORES);
Consumidor c1 = new Consumidor(1, buffer);
Consumidor c2 = new Consumidor(2, buffer);
