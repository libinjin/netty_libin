package nettynio.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


/**
 *
 * 实现功能描述:<br>
 * 实现把String、int、float、double转换为Byte[]的功能。<br>
 * 实现把Byte[]转换为String、double。<br>
 *
 * @author mchen
 *
 * @Phone 010-59282977-826
 *
 * @date 2007-06-28
 *
 */
public class ByteTools {
    /**
     *
     * 方法描述:将String转换为Byte[]
     *
     * @param str
     * @return byte[]
     */
    public static byte[] string2Bytes(String str) {


        return str.getBytes();

/*		int len = str.length();
		byte[] ret = new byte[len << 1];
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			ret[(i << 1) + 1] = (byte) ((ch >> 8) & 0xFF);
			ret[i << 1] = (byte) (ch & 0xFF);
		}
		return ret;
*/
    }

    /**
     *
     * 方法描述:将String转换为Byte[] 第一位标示长度
     *
     * @param str
     * @return byte[]
     */
    public static byte[] string2Byteslen(String str) {



        byte[] bb = str.getBytes();
        byte[] result=new byte[bb.length+1];
        result[0]=(byte)bb.length;
        System.arraycopy(bb, 0, result, 1, bb.length);
        return result;
    }

    /**
     *
     * 方法描述:将int转换为Byte[]
     *
     * @param value
     * @return byte[]
     */
    public static byte[] int2Bytes(int value) {

        byte[] bRet = new byte[4];
        bRet[0] = (byte) ((value >> 24) & 0x000000FF);
        bRet[1] = (byte) ((value >> 16) & 0x000000FF);
        bRet[2] = (byte) ((value >> 8) & 0x000000FF);
        bRet[3] = (byte) (value & 0x000000FF);
        return bRet;

    }


    /**
     *
     * 方法描述:将float转换为Byte[]
     *
     * @param value
     * @return byte[]
     */
    public static byte[] float2Bytes(float value) {

        return int2Bytes(Float.floatToIntBits(value));

    }

    /**
     *
     * 方法描述:将double转换为Byte[]
     *
     * @param value
     * @return byte[]
     */
    public static byte[] double2Bytes(double value) {

        byte[] bRet = new byte[8];
        long nvalue = Double.doubleToLongBits(value);
        bRet[0] = (byte) ((nvalue >> 56) & 0x000000FF);
        bRet[1] = (byte) ((nvalue >> 48) & 0x000000FF);
        bRet[2] = (byte) ((nvalue >> 40) & 0x000000FF);
        bRet[3] = (byte) ((nvalue >> 32) & 0x000000FF);
        bRet[4] = (byte) ((nvalue >> 24) & 0x000000FF);
        bRet[5] = (byte) ((nvalue >> 16) & 0x000000FF);
        bRet[6] = (byte) ((nvalue >> 8) & 0x000000FF);
        bRet[7] = (byte) (nvalue & 0x000000FF);
        return bRet;

    }

    /**
     *
     * 方法描述:将Byte[]转换为String
     *
     * @param b
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String byteToString(byte[] b){
        return new String(b);

    }
    public static String byteToString2(byte[] b){
        ByteBuffer buf=ByteBuffer.wrap(b);
        return buf.asCharBuffer().toString();
    }

    /**
     *
     * 方法描述:将Byte[]转换为String
     *
     * @param b
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String byteToString(byte[] b,String charset){

        return new String(b,Charset.forName(charset));

    }

    /**
     *
     * 方法描述:将Byte[]转换为Double
     *
     * @param b
     * @return
     */
    public static double byteToDouble(byte[] b) {

        long l = (((long) b[0] << 56) + ((long) (b[1] & 255) << 48)
                + ((long) (b[2] & 255) << 40) + ((long) (b[3] & 255) << 32)
                + ((long) (b[4] & 255) << 24) + ((b[5] & 255) << 16)
                + ((b[6] & 255) << 8) + ((b[7] & 255) << 0));

        return Double.longBitsToDouble(l);

    }


    /**
     * 方法描述:将Byte[]转换为int
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }

    /**
     *
     * 方法描述:将Byte[]转换为Float.
     *
     * @param b
     * @return
     */
    public static float byte2Float(byte[] b) {

        return Float.intBitsToFloat(bytesToInt(b));

    }



    /**
     * 方法描述:将long转换为Byte[].
     * @param value
     * @return
     */
    public static byte[] long2Bytes(long value) {

        byte[] bRet = new byte[8];

        bRet[0] = (byte) (value >> 56);
        bRet[1] = (byte) (value >> 48);
        bRet[2] = (byte) (value >> 40);
        bRet[3] = (byte) (value >> 32);
        bRet[4] = (byte) (value >> 24);
        bRet[5] = (byte) (value >> 16);
        bRet[6] = (byte) (value >> 8);
        bRet[7] = (byte) (value >> 0);

        return bRet;

    }


    /**
     * 方法描述:将Byte[]转换为long.
     * @param bytes
     * @return
     */
    public static long bytes2Long(byte[] bytes) {

        return ((((long) bytes[0] & 0xff) << 56)
                | (((long) bytes[1] & 0xff) << 48)
                | (((long) bytes[2] & 0xff) << 40)
                | (((long) bytes[3] & 0xff) << 32)
                | (((long) bytes[4] & 0xff) << 24)
                | (((long) bytes[5] & 0xff) << 16)
                | (((long) bytes[6] & 0xff) << 8)
                | (((long) bytes[7] & 0xff) << 0));

    }
/*    *//**
     * 读取定长的string
     * @param is
     * @param len
     * @return
     *//*
    public static String readString(InputStream is,int len){
        String result = null;
        byte[] temp = new byte[len];

        try {
            int step=0;
            while(step<len && step !=-1){
                step +=  is.read(temp, step, len-step);

            }

            if(step!=-1){
                result = new String(temp,Youguu.DEFAULT_CHARSET);
            }


        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }*/

    /**
     * 从流中读取double
     * @param is
     * @return
     */
    public static double readDouble(InputStream is){
        double result = 0;
        try{
            int len = 8;
            byte[] temp = new byte[len];
            int step=0;
            while(step<len && step !=-1){
                step +=  is.read(temp, step, len-step);
            }

            result = byteToDouble(temp);
        }catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从流中读取float
     * @param is
     * @return
     */
    public static float readFloat(InputStream is){
        float result = 0;
        try{
            int len = 4;
            byte[] temp = new byte[len];
            int step=0;
            while(step<len && step !=-1){
                step +=  is.read(temp, step, len-step);
            }
            result = byte2Float(temp);
        }catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从流中读取long
     * @param is
     * @return
     */
    public static long readLong(InputStream is){
        long result = 0;
        try{
            int len = 8;
            byte[] temp = new byte[len];
            int step=0;
            while(step<len && step !=-1){
                step +=  is.read(temp, step, len-step);
            }
            result = bytes2Long(temp);
        }catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从流中读取int
     * @param is
     * @return
     */
    public static int readInt(InputStream is){
        int result = 0;
        try{
            int len = 4;
            byte[] temp = new byte[len];
            int step=0;
            while(step<len && step !=-1){
                step +=  is.read(temp, step, len-step);
            }
            result = bytesToInt(temp);
        }catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println( string2Bytes( "000001" ).length );


    }

}
