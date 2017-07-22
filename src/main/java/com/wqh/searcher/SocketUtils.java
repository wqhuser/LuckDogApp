package com.wqh.searcher;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class SocketUtils {  
    public static void close(Socket s){  
        try {  
            s.shutdownInput();  
            s.shutdownOutput();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
  
  
    public static byte[] readBytes(DataInputStream in,int length) throws IOException {
//    	BufferedInputStream bis = new BufferedInputStream(in);
        int r=0;  
        byte[] data=new byte[length];  
        int test =0;
        test = in.read(data,r,length-r);  
        while(r<length && test != -1){
            System.out.println("test:" + test);
            r += test;
            System.out.println("len: " + r);
            test = in.read(data,r,length-r);  
        }  
        data = Arrays.copyOf(data, r);
        return data;  
    }  
  
    public static void writeBytes(DataOutputStream out,byte[] bytes,int length) throws IOException{  
        out.writeInt(length);  
        out.write(bytes,0,length);  
        out.flush();  
    }  
}
