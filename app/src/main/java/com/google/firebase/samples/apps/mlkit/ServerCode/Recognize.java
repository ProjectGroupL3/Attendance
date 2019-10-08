package com.google.firebase.samples.apps.mlkit.ServerCode;

import java.net.*;
import java.io.*;
public class Recognize extends Thread{
    Socket s;
    InputStreamReader isr;
    BufferedReader br;
    String message;
    PrintWriter pw;
    Recognize(Socket s){
        this.s=s;
    }
    public void run(){
        System.out.println("Accepted");
        String id=null;
        pw=null;
        try {
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            pw=new PrintWriter(s.getOutputStream());
            byte[] buffer = (byte[]) ois.readObject();
            String imageName="test"+Thread.currentThread().getId()+".PNG";
            // System.out.println(imageName);
            FileOutputStream fos=new FileOutputStream(imageName);
            fos.write(buffer);
            PythonFile pf=new PythonFile(imageName);
            id=pf.exec();

            if(id!=null && id!="unknown"){
                System.out.println("Image recognized as "+id);
            }else{
                System.out.println("F");
                id="unknown";
            }
            pw.write(id);
            //delete image
            // File file=new File(imageName);
            // if(file.delete()){
            // 	System.out.println("Deleted "+imageName);
            // }

        }catch (Exception e){
            System.out.println(e);
            // pw=new PrintWriter(s.getOutputStream());
            pw.write("unknown");
        }
        pw.flush();
        pw.close();



    }
}