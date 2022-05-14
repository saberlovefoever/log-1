package org.whh.bz.demo;



public class PrintABCByOrder {
    static int mark = 1;

    public static void main(String[] args) {
        Object o = new Object();
        Thread a = new Thread(new A(o),"T_A");
        Thread b = new Thread(new B(o),"T_B");
        Thread c = new Thread(new C(o),"T_C");
        a.start();b.start();c.start();
    }
}

    class A implements Runnable {
        private Object o;
        public A(Object o){
            this.o = o;
        }
        @Override
        public void run() {
                for (int i = 1;i<3;++i){
                    synchronized (o){
                        try {
                            while (PrintABCByOrder.mark != 1) {
                                System.out.println("try Interrupted");
                                o.wait();
                                System.out.println("Interrupted");
                            }
                            System.out.println("A ");
                            PrintABCByOrder.mark = 2;
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                            System.out.println("catch Interrupted ");
                    }  finally {
                        o.notifyAll();
                            System.out.println("notify");
                    }
                }
            }

        }
        }

        class B implements Runnable {
            private Object o;
            public B(Object o){
                this.o = o;
            }
            @Override
            public void run() {
                    for (int i = 1;i<3;++i){
                        synchronized (o){
                        try {
                            while (PrintABCByOrder.mark != 2) {
                                o.wait();
                            }
                            System.out.println("B ");
                            PrintABCByOrder.mark = 3;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }  finally {
                            o.notifyAll();
                        }

                    }
                }

            }
        }

        class C implements Runnable {
            private Object o;
            public C(Object o){
                this.o = o;
            }
            @Override
            public void run() {
                    for (int i = 1; i < 3; ++i) {
                        synchronized (o) {
                            try {
                                while (PrintABCByOrder.mark != 3) {
                                    o.wait();
                                }
                                System.out.println("C ");
                                PrintABCByOrder.mark = 1;
                            } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            o.notifyAll();
                        }
                    }

                }
            }}


