package ing.unipi.it.provabuffercircolare;

import java.io.BufferedReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by carmen on 16/10/14.
 */
public class BufferCircolare {

    private int buffer[];
    private int dimBuffer;
    private int numConsumatori;
    private int primoElemento[];//indice di ciascun consumatore
    private int ultimoElemento;
    private int elementiInseriti[];//elementi letti da ciascun consumatore
    int cont = 0;

    private Lock lock = new ReentrantLock();
    private Condition non_pieno = lock.newCondition();
    private Condition[] non_vuoto;

    public BufferCircolare(int numElementi, int numConsumatori) {

        this.dimBuffer = numElementi;
        this.buffer = new int[numElementi];
        this.numConsumatori = numConsumatori;
        this.primoElemento = new int[numConsumatori];
        this.elementiInseriti = new int[numConsumatori];
        this.non_vuoto = new Condition[numConsumatori];


        this.ultimoElemento = 0;


        for (int j = 0; j < numConsumatori; j++) {

            non_vuoto[j] = lock.newCondition();

        }


    }


    public void inserisciDato (int dato) throws InterruptedException {
        lock.lock();
        try {
           while (cont == dimBuffer)
           non_pieno.await();//il produttore aspetta ci sia almeno un posto in cui inserire
            buffer[ultimoElemento] = dato;
            cont ++;
            ultimoElemento = (ultimoElemento+1)%dimBuffer;
            for(int k = 0; k < numConsumatori; k++) {
                elementiInseriti[k]++;
                non_vuoto[k].signal();
            }
        } finally {
            lock.unlock();
        }

    }


    public int preleva (int indiceConsumatore) throws InterruptedException {
        int dato ;
        lock.lock();
        try {
            while(cont == 0)
            non_vuoto[indiceConsumatore].await();
            dato = buffer[primoElemento[indiceConsumatore]];
            primoElemento[indiceConsumatore] = (primoElemento[indiceConsumatore]+1)%dimBuffer;
            elementiInseriti[indiceConsumatore]--;
            for(int i = 0; i < numConsumatori && i!= indiceConsumatore; i++) {
                if(elementiInseriti[indiceConsumatore] >= elementiInseriti[i]) {
                    cont --;
                    non_pieno.signal();
                }
            }


        }finally {
            lock.unlock();
        }

        return dato;
    }

}
