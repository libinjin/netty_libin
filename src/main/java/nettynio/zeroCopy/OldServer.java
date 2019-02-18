package nettynio.zeroCopy;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {
    public static void main(String[] args) throws IOException {
        DataInputStream dataInputStream =null;
        OutputStream outputStream = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            while(true){
                Socket socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                String fileName = "E:\\视频\\bb.mp4";
                outputStream = new FileOutputStream(fileName);

                byte[] data = new byte[4096];
                int len =-1;
                while ((len = dataInputStream.read(data))!=-1){
                    outputStream.write(data,0,len);
                   /* int readCount = dataInputStream.read(data,0,data.length);
                    if(-1 ==readCount){
                        break;
                    }*/
                }
                System.out.println("读取完毕");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            dataInputStream.close();
            outputStream.close();
        }
    }
}
