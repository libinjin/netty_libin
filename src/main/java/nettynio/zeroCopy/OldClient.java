package nettynio.zeroCopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 读取文件发送给服务端
 * 零拷贝都是对于文件来说的
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            Socket socket = new Socket("localhost",8899);
            String fileName = "E:\\视频\\aa.mp4";
            inputStream = new FileInputStream(fileName);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            /**
             * 把文件读取到后放入到socket中
             */
            long start = System.currentTimeMillis();
            byte[] data = new byte[4096];
            int len =-1;
            int total = 0;
            while((len=inputStream.read(data))!=-1){
                total += len;
                dataOutputStream.write(data,0,len);
            }
            long end = System.currentTimeMillis();
            System.out.println("发送总字节数："+total+"耗时："+(end-start)+"ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            dataOutputStream.close();
        }
    }
}
