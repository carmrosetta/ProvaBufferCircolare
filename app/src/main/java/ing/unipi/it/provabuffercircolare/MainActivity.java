package ing.unipi.it.provabuffercircolare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BufferCircolare bufferCircolare = new BufferCircolare(100000, 4);
        Consumatore c1 = new Consumatore(0, bufferCircolare);
        Consumatore c2 = new Consumatore(1, bufferCircolare);
        Consumatore c3 = new Consumatore(2, bufferCircolare);
        Consumatore c4 = new Consumatore(3, bufferCircolare);
        Produttore p = new Produttore(bufferCircolare);
        p.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();
    }




}
