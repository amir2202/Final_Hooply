/*
package com.hooply;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class ExternalDb {
    private Object lock1;
    static void insertRemoteUser(User user){
        final Object lock = new Object();

        final boolean[] found = {false};

        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    URL url = null;
                    try {
                        url = new URL("https://caracal.imada.sdu.dk/app2020/users");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");


                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    found[0] = true;
                    lock.notify();
                }
            }
        });
        thread.start();
        try {
            synchronized(lock) {
                while(found[0] == false) {
                    lock.wait();
                }
                // add remote db data to local db ???
                return;
            }
        } catch (InterruptedException e) {
            // maybe do smth for exception handling ? or just ignore lol
            return;
        }

    }
    }


}
*/