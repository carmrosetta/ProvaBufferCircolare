package ing.unipi.it.provabuffercircolare;

import android.util.Log;

/**
 * Created by carmen on 16/10/14.
 */
public class Produttore extends Thread {
    BufferCircolare buffer;
    int i = 1;

    public Produttore(BufferCircolare buf) {
        this.buffer = buf;
    }

    @Override
    public void run() {

        super.run();
        while (i<1000000000) {

            try {
                buffer.inserisciDato(i);
                Log.e("Produttore", "Inserito " + i);
                //Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;

        }
    }
}
