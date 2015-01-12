package org.onyx.system.base.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseSystemTest {
    public static void main(String[] args) {
        final BaseClass base = new BaseClass();
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    base.begin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while(br.readLine()==null){
            }

            base.stop();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
