package nettynio.quoteStragy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Profit {

    private int index;
    private double syl;

    private double colsePrice;//收盘价


    public Profit(int index, double syl, double colsePrice) {
        this.index = index;
        this.syl = syl;
        this.colsePrice = colsePrice;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getSyl() {
        return syl;
    }

    public void setSyl(double syl) {
        this.syl = syl;
    }

    public double getColsePrice() {
        return colsePrice;
    }

    public void setColsePrice(double colsePrice) {
        this.colsePrice = colsePrice;
    }

    public static void main(String[] args) throws IOException {
        ByteBuffer bb = null;
        int length = 10;
        bb = ByteBuffer.allocate(length);
     /*   bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);
        bb.putInt(4);*/
        writeString("水电费", 6, bb);
        bb.putInt(6);
        System.out.println(new String(bb.array(),"GBK"));
    }

    public static void writeString(String s, int length, ByteBuffer bb)
            throws IOException {
        int i = 0;
        int strPos = 0;
        byte[] buf = null;
        try {
            buf = s.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            throw new IOException("不能将字符串转换为(" + "GBK" + ")编码格式！");
        }
        int strLen = buf.length;
        while (i < length) {
            byte ch = (strPos < strLen ? buf[strPos++] : 0);
            bb.put(ch);
            i++;
        }
    }
}
