package org.whh.bz.demo;


public class SellT{
    volatile static int ticket = 10;
    public static void main(String[] args) {
        Object o = new Object();
        Sell t_a = new Sell(o);
        Sell t_b = new Sell(o);
        Sell t_c = new Sell(o);
        Thread a = new Thread(t_a,"sell T A");
        Thread b = new Thread(t_b,"sell T B");
        Thread c = new Thread(t_c,"sell T C");
        a.start();b.start();c.start();
    }
}
class Sell implements Runnable{

     Object o;
    public Sell(Object o) {
        this.o = o;
    }
    @Override
    public void run() {
        while (true){
            synchronized (o){
                if (SellT.ticket==0) {
                    System.exit(0);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    SellT.ticket--;
                    System.out.println(Thread.currentThread().getName() + "" + SellT.ticket + "ticket");
                }
            }
        }
}