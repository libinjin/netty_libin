package nettynio.quoteStragy;

import nettynio.klineGames.GameHelperService;
import nettynio.util.ByteTools;
import nettynio.util.YouguuHttpsClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static final String FROM_PATH = "";

    //写入的文件夹
    public static final String INTO_PATH = "D:\\stocks";

    /**
     * 传入两个路径
     *
     */
    public static List<KlineFile> getPath(String filePath){

        //股票1：code  2:股票名称 UTF-8 格式 3：date 4:open  5:hight  6: low  7：close 8：money 9：amount

        //获取shdata下的所有股票
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        long startTime  = 20180101L;

        long endTime = Long.parseLong(sdf.format(new Date()));

        List<File> filelist = new ArrayList<>();

        List<File> fileList = FileUtil.getFileList(filePath,filelist);

        List<KlineFile> klineFileList = new ArrayList<>();

        int i =0;

        if(fileList !=null && fileList.size()>0){

            for (File file : fileList) {

                String fileName = file.getName();

                String code = fileName.split("\\.")[0];

                int index = filePath.indexOf("shdata");
                String stData;
                int marketID;
                if(index != -1){
                    marketID = 1;
                    stData = "shdata";
                }else{
                    marketID = 2;
                    stData = "szdata";
                }
                String name = HisKlineReader.convertTo8(code, marketID);//1: 上海 2:深圳
                if(name.length()!=8){
                    continue;
                }
                /**
                 * 得到每个文件的所有数据
                 */
                List<CommonKlinePoint> klist = HisKlineReader.getKLineByTime(file, startTime, endTime);

                //取出109个满足条件的点
                List<Profit> profitList = getProfitList(klist);


             /*   for (Profit profit :profitList) {
                    System.out.println(" 股票下标："+profit.getIndex()+"  收益：,"+profit.getSyl());
                }*/

                klineFileList = getKlineFileList(klineFileList, profitList, klist, name, stData);

                System.out.println("i："+i++);
            }
        }
        return klineFileList;
    }

    public static List<KlineFile> getKlineFileList(List<KlineFile> klineFileList, List<Profit> profitList, List<CommonKlinePoint> klist, String name, String stData){


        int size = profitList.size();
        if(size >= 109){
            Profit fprofit = profitList.get(0);

            Profit lprofit =  profitList.get(size-1);

            int first = fprofit.getIndex();

            int last = lprofit.getIndex()+1;

            double fprice = fprofit.getColsePrice();

            double lprice = lprofit.getColsePrice();

            int scope = (int)((lprice-fprice)/fprice * 100);

            List<CommonKlinePoint> newKline = klist.subList(first, last);

            int nameInt = Integer.parseInt(name);

            String codeName = getCodeName(nameInt);

            if("".equals(codeName)){
                return klineFileList;
            }

            long startDate = newKline.get(0).getDate();

            long endDate = newKline.get(newKline.size()-1).getDate();

            //System.out.println("获取股票中文名称："+codeName);

            String scop = transFile(scope);

            String fileName = name+"["+startDate+"-"+endDate+"]";

            String absolutFile = INTO_PATH+"\\"+stData+"\\"+scop+"\\"+name+"["+startDate+"-"+endDate+"]";

            List<String> fileList = new ArrayList<>();

            fileList.add(stData);

            fileList.add(scop);

            KlineFile klineFile = new KlineFile(newKline, fileName, fileList, absolutFile, nameInt, codeName);

            klineFileList.add(klineFile);
        }
        return klineFileList;
    }

    public static List<Profit> getProfitList(List<CommonKlinePoint> klist){

        List<Profit> profitList = new ArrayList<>();

        if(klist !=null && klist.size()>0){

            boolean flag = true;
            for (int i = 1; i < klist.size(); i++) {
                double first = klist.get(i-1).getCur();

                double second = klist.get(i).getCur();

                double syl = (second-first)/first * 100;

                if(-11<syl && syl<11){
                    Profit profit = new Profit(i, syl, second);
                    profitList.add(profit);
                }else{
                    flag = false;
                }
                if(!flag){//不要了
                    profitList.clear();
                    flag = true;
                }
                if(profitList.size()>=109){//取得109个点
                    break;
                }
            }
        }
        return profitList;
    }


    public static String getCodeName(int nameInt){

        String url = "http://quote.youguu.com/quote/stocklist2/board/stock/curpricewithtop5?code="+nameInt+"&toString=1";
        String codeResult = YouguuHttpsClient.getClient().doGet(url);

        String regEx="[`~!@#$%^&*()+=|{}:;\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？, a-zA-Z0-9 ]";
        Pattern c = Pattern.compile(regEx);
        Matcher mc=c.matcher(codeResult);
        String result =  mc.replaceAll("").trim();
        String[] str = result.split("-");

        String codeName = "";
        if(str.length==2){
            codeName = str[1];
        }
        return codeName;
    }

    public static String transFile(int scope){

        if(0>scope && scope>=-10){
            scope = -10;
        }else if(-10>scope && scope>=-20){
            scope = -20;
        }else if(-20>scope && scope>=-30){
            scope = -30;
        }else if(-30>scope && scope>=-40){
            scope = -40;
        }else if(-40>scope && scope>=-50){
            scope = -50;
        }else if(-50>scope && scope>=-60){
            scope = -60;
        }else if(-60>scope && scope>=-70){
            scope = -70;
        }else if(-70>scope && scope>=-80){
            scope = -80;
        }else if(-80>scope && scope>=-90){
            scope = -90;
        }else if(-90>scope && scope>=-100){
            scope = -100;
        }

        else if(0<scope && scope<=10){
            scope = 10;
        }else if(10<scope && scope<=20){
            scope = 20;
        }else if(20<scope && scope<=30){
            scope = 30;
        }else if(30<scope && scope<=40){
            scope = 40;
        }else if(40<scope && scope<=50){
            scope = 50;
        }else if(50<scope && scope<=60){
            scope = 60;
        }  else if(60<scope && scope<=70){
            scope = 70;
        }else if(70<scope && scope<=80){
            scope = 80;
        }else if(80<scope && scope<=90){
            scope = 90;
        }else if(90<scope && scope<=100){
            scope = 100;
        }else{
            scope = 999;
        }

        return String.valueOf(scope);
    }

    public static List<File> getFileList(String path,  List<File> filelist) {

        File dir = new File(path);

        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组

        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                String fileName = files[i].getName();

                if (files[i].isDirectory()) { // 判断是文件还是文件夹

                    getFileList(files[i].getAbsolutePath(), filelist); // 获取文件绝对路径
                } else if (fileName.toLowerCase().endsWith("data")) { // 判断文件名是否以.data结尾

                    String strFileName = files[i].getAbsolutePath();
                    //System.out.println("strFileName:"+strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }
        }

        return filelist;
    }

    public static void writeInTo(List<KlineFile> fileList){

        for (KlineFile klineFile : fileList) {

            String fileName = klineFile.getFileName();

            System.out.println("生成的文件名称："+fileName);

            List<String> files = klineFile.getFile();

            String codeName = klineFile.getName();

            List<CommonKlinePoint> klist = klineFile.getKlist();

            putInToPath(fileName, files, codeName, klist);
        }
    }

    public static void putInToPath(String fileName, List<String> directories, String codeName, List<CommonKlinePoint> klist){

        FileOutputStream fos = null;
        try {
            String rootName = new File(INTO_PATH).getAbsolutePath();

            File file = createFileWithMultilevelDirectory(directories,fileName,rootName);

            fos = new FileOutputStream(file);

            byte[] bytes = codeName.getBytes("utf-8");
            fos.write(GameHelperService.int2Bytes(bytes.length));
            fos.write(bytes);

            for (int i = 0; i < klist.size(); i++) {

                CommonKlinePoint point = klist.get(i);

                long date = point.getDate();
                int trdate= new Long(date).intValue();
                float open = (float) point.getOpen();//开盘价
                float high = (float)point.getHigh();//最高价
                float low = (float)point.getLow();//最低价
                float cur = (float)point.getCur();//当前价格
                double money = point.getSum();//成交额 money
                long amount = point.getVolume();//成交量
                //System.out.println("date:"+date+"open:"+open+"high:"+high+"low:"+low+"cur:"+cur+"money:"+money+"amount:"+amount);
                fos.write(ByteTools.int2Bytes(trdate));
                fos.write(ByteTools.float2Bytes(open));
                fos.write(ByteTools.float2Bytes(high));
                fos.write(ByteTools.float2Bytes(low));
                fos.write(ByteTools.float2Bytes(cur));
                fos.write(ByteTools.double2Bytes(money));
                fos.write(ByteTools.long2Bytes(amount));

            /*  ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.putInt(trdate);
                buffer.putFloat(open);
                buffer.putFloat(high);
                buffer.putFloat(low);
                buffer.putFloat(cur);
                buffer.putDouble(money);
                buffer.putLong(amount);
                buffer.flip();
                fileChannel.write(buffer);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {


        /*List<KlineFile> first = FileUtil.getPath("C:\\Users\\jhss_hd\\Desktop\\data\\shdata");
        writeInTo(first);*/



        long start = System.currentTimeMillis();
        List<KlineFile> shFile = FileUtil.getPath("E:\\quotedata\\datas\\shdata");

        writeInTo(shFile);
        long end = System.currentTimeMillis();


        System.out.println(start-end+"ms");
/*        List<KlineFile> szFile = FileUtil.getPath("E:\\quotedata\\datas\\szdata");

        writeInTo(szFile);*/


/*       ByteBuffer bb = null;
       int length = 50;
        bb = ByteBuffer.allocate(length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        String name = "金太阳";
        bb.putInt(name.getBytes("utf-8").length);
        bb.put(name.getBytes("utf-8"));


        bb.flip();
        byte[] temp = new byte[bb.getInt()];
        bb.get(temp);
        String sname = new String(temp, "utf-8");//金太阳

        System.out.println(sname);*/



    }


    //创建多级目录
    public static File createMultilevelDirectory(List<String> directories,String rootPath) {
        if (directories.size() == 0) {
            return null;
        }
        File root = new File(rootPath);
        for (int i = 0; i < directories.size(); i++) {
            File directory = new File(root, directories.get(i));
            directory.mkdir();
            root = directory;
        }
        return root;
    }

    public static File createFileWithMultilevelDirectory(List<String> directories,String fileName,String rootName) throws IOException {
//调用上面的创建多级目录的方法
        File filePath =  createMultilevelDirectory(directories,rootName);
        File file = new File(filePath,fileName);
        file.createNewFile();
        return  file;
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
