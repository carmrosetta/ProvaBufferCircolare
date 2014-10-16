package ing.unipi.it.provabuffercircolare;

import android.util.Log;

import java.util.concurrent.locks.Condition;

/**
 * Created by carmen on 16/10/14.
 */
public class Consumatore extends Thread {

    int indice;
    BufferCircolare buffer;

    public Consumatore(int i, BufferCircolare buf) {
        this.indice = i;
        this.buffer = buf;
    }

    @Override
    public void run() {
        int dato;
        super.run();
        while (true) {

            try {
                dato = buffer.preleva(indice);
                Log.e("Consumatore " + indice, "Prelevato dato " + dato);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
