package com.google.firebase.samples.apps.mlkit.ServerCode;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sid on 24/9/19.
 */
public class Server {
    static Socket s;
    static ServerSocket ss;
    public  static void main(String args[]){
        System.out.println("Server Running...");
        try{
            ss=new ServerSocket(7800);
            while(true){
                s=ss.accept();
                Recognize recog=new Recognize(s);
                recog.start();
            }
        }catch (Exception e){

        }
    }
}
