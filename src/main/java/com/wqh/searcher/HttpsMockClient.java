package com.wqh.searcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.SecureRandom;

public class HttpsMockClient extends  HttpsMockBase {  
    static DataInputStream in;  
    static DataOutputStream out;  
    static Key key;  
    public static void main(String args[]) throws  Exception{  
        int port=80;  
//        Socket s=new Socket("183.56.147.1",port);  
        Socket s=new Socket("localhost",port);  
        s.setReceiveBufferSize(102400);  
        s.setKeepAlive(true);  
        in=new DataInputStream(s.getInputStream());  
        out=new DataOutputStream(s.getOutputStream());  
        shakeHands();  
  
        System.out.println("------------------------------------------------------------------");  
        String name="duck";  
        writeBytes(name.getBytes());  
  
        int len=in.readInt();  
        byte[] msg=readBytes(len);  
        System.out.println("������������Ϣ:"+byte2hex(msg));  
        Thread.sleep(1000*100);  
  
  
    }  
  
    private static void shakeHands() throws Exception {  
        //��һ�� �ͻ��˷����Լ�֧�ֵ�hash�㷨  
        String supportHash="SHA1";  
        int length=supportHash.getBytes().length;  
        out.writeInt(length);  
        SocketUtils.writeBytes(out, supportHash.getBytes(), length);  
  
        //�ڶ��� �ͻ�����֤��������֤���Ƿ�Ϸ�  
        int skip=in.readInt();  
        byte[] certificate=SocketUtils.readBytes(in,skip);  
        java.security.cert.Certificate cc= CertificateUtils.createCertificate(certificate);  
  
        publicKey=cc.getPublicKey();  
        cc.verify(publicKey);  
        System.out.println("�ͻ���У���������֤���Ƿ�Ϸ���" +true);  
  
        //����  �ͻ���У��������˷��͹�����֤��ɹ�,���������ù�Կ����  
        System.out.println("�ͻ���У��������˷��͹�����֤��ɹ�,���������ù�Կ����");  
        SecureRandom seed=new SecureRandom();  
        int seedLength=2;  
        byte seedBytes[]=seed.generateSeed(seedLength);  
        System.out.println("��ɵ������Ϊ : " + byte2hex(seedBytes));  
        System.out.println("��������ù�Կ���ܺ��͵�������");  
        byte[] encrptedSeed=encryptByPublicKey(seedBytes, null);  
        SocketUtils.writeBytes(out,encrptedSeed,encrptedSeed.length);  
  
        System.out.println("���ܺ��seedֵΪ :" + byte2hex(encrptedSeed));  
  
        String message=random();  
        System.out.println("�ͻ��������ϢΪ:"+message);  
  
        System.out.println("ʹ��������ù�Կ����Ϣ����");  
        byte[] encrpt=encryptByPublicKey(message.getBytes(),seed);  
        System.out.println("���ܺ���Ϣλ��Ϊ : " +encrpt.length);  
        SocketUtils.writeBytes(out,encrpt,encrpt.length);  
  
        System.out.println("�ͻ���ʹ��SHA1������ϢժҪ");  
        byte hash[]=cactHash(message.getBytes());  
        System.out.println("ժҪ��ϢΪ:"+byte2hex(hash));  
  
        System.out.println("��Ϣ������ɣ�ժҪ������ɣ����ͷ�����");  
        SocketUtils.writeBytes(out,hash,hash.length);  
  
  
        System.out.println("�ͻ����������������Ϣ��ɣ���ʼ���ܷ������˷��ͻ�������Ϣ��ժҪ");  
        System.out.println("���ܷ������˷��͵���Ϣ");  
        int serverMessageLength=in.readInt();  
        byte[] serverMessage=SocketUtils.readBytes(in,serverMessageLength);  
        System.out.println("�������˵���Ϣ����Ϊ ��" + byte2hex(serverMessage));  
  
        System.out.println("��ʼ��֮ǰ��ɵ���������DES�㷨������Ϣ,����Ϊ:"+byte2hex(seedBytes));  
        byte[] desKey= DesCoder.initSecretKey(new SecureRandom(seedBytes));  
        key=DesCoder.toKey(desKey);  
  
        byte[] decrpytedServerMsg=DesCoder.decrypt(serverMessage, key);  
        System.out.println("���ܺ����ϢΪ:"+byte2hex(decrpytedServerMsg));  
  
        int serverHashLength=in.readInt();  
        byte[] serverHash=SocketUtils.readBytes(in,serverHashLength);  
        System.out.println("��ʼ���ܷ������˵�ժҪ��Ϣ:"+byte2hex(serverHash));  
  
        byte[] serverHashValues=cactHash(decrpytedServerMsg);  
        System.out.println("����������˷��͹�������Ϣ��ժҪ : " +byte2hex(serverHashValues));  
  
        System.out.println("�жϷ������˷��͹�����hashժҪ�Ƿ�ͼ������ժҪһ��");  
        boolean isHashEquals=byteEquals(serverHashValues,serverHash);  
  
        if(isHashEquals){  
            System.out.println("��֤��ɣ����ֳɹ�");  
        }else{  
            System.out.println("��֤ʧ�ܣ�����ʧ��");  
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
}  
