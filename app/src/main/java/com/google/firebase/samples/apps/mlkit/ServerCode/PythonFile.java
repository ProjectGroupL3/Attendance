package com.google.firebase.samples.apps.mlkit.ServerCode;

import java.io.*;

public class PythonFile{
    private String imageName;
    public PythonFile(String imageName){
        this.imageName=imageName;
    }
    public String exec(){
        try {
            System.out.println("Image id "+imageName);
            String command="python3 face_recognize.py "+imageName;
            Process p=Runtime.getRuntime().exec(command);
            BufferedReader stdInput=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s=null;
            while((s=stdInput.readLine()) != null){
                return s;
            }
            return s;
        } catch (IOException e) {
            System.out.println(e);
        }
        return "unknown";
    }

}