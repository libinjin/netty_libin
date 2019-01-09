package nettynio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件的共享锁，又称为读锁
 * 排它锁，又称为写锁，写的时候上锁，读的时候可以同时读
 * 读写锁技能提高效率，又能互斥。读中没有写，写中没有读，写中也没有写。
 */
public class NioSharedLock {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/file/aa.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.lock(3,6,true);
        System.out.println("valid:"+fileLock.isValid());
        System.out.println("lock type:"+fileLock.isShared());
        fileLock.release();
    }
}
