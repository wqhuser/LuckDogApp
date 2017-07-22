package com.wqh.searcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

public class HttpsMockServer extends HttpsMockBase {  
    static DataInputStream in;  
    static DataOutputStream out;  
    static String hash;  
    static Key key;  
    static ExecutorService executorService= Executors.newFixedThreadPool(20);  
    public static void main(String args[]) throws Exception{  
        int port=80;  
        ServerSocket ss= ServerSocketFactory.getDefault().createServerSocket(port);  
        ss.setReceiveBufferSize(102400);  
        ss.setReuseAddress(false);  
        while(true){  
            try {  
                final Socket s = ss.accept();  
                doHttpsShakeHands(s);  
                executorService.execute(new Runnable() {  
                    public void run() {  
                        doSocketTransport(s);  
                    }  
                });  
  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private static void doSocketTransport(Socket s){  
        try{  
            System.out.println("--------------------------------------------------------");  
            int length=in.readInt();  
            byte[] clientMsg=readBytes(length);  
            System.out.println("�ͻ���ָ������Ϊ:" + byte2hex(clientMsg));  
  
            writeBytes("�������Ѿ���������".getBytes());  
        }catch (Exception ex){  
            ex.printStackTrace();  
        }  
    }  
  
    public static byte[] readBytes(int length) throws  Exception{  
        byte[] undecrpty=SocketUtils.readBytes(in,length);  
        System.out.println("��ȡδ������Ϣ:"+byte2hex(undecrpty));  
        return DesCoder.decrypt(undecrpty,key);  
    }  
  
    public static void writeBytes(byte[] data) throws  Exception{  
        byte[] encrpted=DesCoder.encrypt(data,key);  
        System.out.println("д����ܺ���Ϣ:"+byte2hex(encrpted));  
        SocketUtils.writeBytes(out,encrpted,encrpted.length);  
    }  
  
    private static void doHttpsShakeHands(Socket s) throws Exception {  
         in=new DataInputStream(s.getInputStream());  
         out=new DataOutputStream(s.getOutputStream());  
  
        //��һ�� ��ȡ�ͻ��˷��͵�֧�ֵ���֤���򣬰���hash�㷨������ѡ��SHA1��Ϊhash  
        int length=in.readInt();  
        in.skipBytes(4);  
        byte[] clientSupportHash=SocketUtils.readBytes(in,length);  
        String clientHash=new String(clientSupportHash);  
        hash=clientHash;  
        System.out.println("�ͻ��˷�����hash�㷨Ϊ:"+clientHash);  
  
        //�ڶ��������ͷ�����֤�鵽�ͻ���  
        byte[] certificateBytes=CertificateUtils.readCertificates();  
        privateKey=CertificateUtils.readPrivateKeys();  
        System.out.println("����֤���ͻ���,�ֽڳ���Ϊ:"+certificateBytes.length);  
        System.out.println("֤������Ϊ:" + byte2hex(certificateBytes));  
        SocketUtils.writeBytes(out, certificateBytes, certificateBytes.length);  
  
        System.out.println("��ȡ�ͻ���ͨ��Կ���ܺ�������");  
        int secureByteLength=in.readInt();  
        byte[] secureBytes=SocketUtils.readBytes(in, secureByteLength);  
  
        System.out.println("��ȡ���Ŀͻ��˵������Ϊ:"+byte2hex(secureBytes));  
        byte secureSeed[]=decrypt(secureBytes);  
        System.out.println("���ܺ�����������Ϊ:" +byte2hex(secureSeed));  
  
        //���� ��ȡ�ͻ��˼����ַ�  
        int skip=in.readInt();  
        System.out.println("���� ��ȡ�ͻ��˼�����Ϣ,��Ϣ����Ϊ ��" +skip);  
        byte[] data=SocketUtils.readBytes(in,skip);  
  
        System.out.println("�ͻ��˷��͵ļ�����ϢΪ : " +byte2hex(data));  
        System.out.println("��˽Կ����Ϣ���ܣ�������SHA1��hashֵ");  
        byte message[] =decrypt(data,new SecureRandom(secureBytes));  
        byte serverHash[]=cactHash(message);  
  
  
        System.out.println("��ȡ�ͻ��˼����SHA1ժҪ");  
        int hashSkip=in.readInt();  
        byte[] clientHashBytes=SocketUtils.readBytes(in,hashSkip);  
        System.out.println("�ͻ���SHA1ժҪΪ : " + byte2hex(clientHashBytes));  
  
        System.out.println("��ʼ�ȽϿͻ���hash�ͷ������˴���Ϣ�м����hashֵ�Ƿ�һ��");  
        boolean isHashEquals=byteEquals(serverHash,clientHashBytes);  
        System.out.println("�Ƿ�һ�½��Ϊ �� " + isHashEquals);  
  
  
  
        System.out.println("��һ��У��ͻ��˷��͹�������Ϣ��ժ��һ�£���������ʼ��ͻ��˷�����Ϣ��ժҪ");  
        System.out.println("����������ڼ��ܷ���������Ϣ,secureRandom : "+byte2hex(secureSeed));  
        SecureRandom secureRandom=new SecureRandom(secureSeed);  
  
        String randomMessage=random();  
        System.out.println("����������ɵ������ϢΪ : "+randomMessage);  
  
        System.out.println("��DES�㷨��ʹ�ÿͻ�����ɵ�����������Ϣ����");  
        byte[] desKey=DesCoder.initSecretKey(secureRandom);  
        key=DesCoder.toKey(desKey);  
  
        byte serverMessage[]=DesCoder.encrypt(randomMessage.getBytes(), key);  
        SocketUtils.writeBytes(out,serverMessage,serverMessage.length);  
        System.out.println("�������˷��͵Ļ��ܺ����ϢΪ:"+byte2hex(serverMessage)+",��������Ϊ:"+byte2hex(secureSeed));  
  
        System.out.println("�������˿�ʼ����hashժҪֵ");  
        byte serverMessageHash[]=cactHash(randomMessage.getBytes());  
        System.out.println("�������˼����hashժҪֵΪ :" +byte2hex(serverMessageHash));  
        SocketUtils.writeBytes(out,serverMessageHash,serverMessageHash.length);  
  
        System.out.println("���ֳɹ���֮������ͨ�Ŷ���ʹ��DES�����㷨���м���");  
    }  
  
}
