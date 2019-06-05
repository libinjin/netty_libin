package nettynio.quoteStragy;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HisKlineReader {


	public static final String FIRST_TYPE_INDEX = "1"; // 指数

	/**
	 * 上证一级分类对应的代码规则
	 */
	public static HashMap<String, String> LEVELFIRSTMAP_SH = new HashMap<String, String>();

	/**
	 * 上证二级分类对应的代码规则
	 */
	public static HashMap<String, String> LEVELSECONDMAP_SH = new HashMap<String, String>();

	/**
	 * 深证一级分类对应的代码规则
	 */
	public static HashMap<String, String> LEVELFIRSTMAP_SZ = new HashMap<String, String>();

	/**
	 * 深证二级分类对应的代码规则
	 */
	public static HashMap<String, String> LEVELSECONDMAP_SZ = new HashMap<String, String>();

	static {
		// 上证
		LEVELFIRSTMAP_SH.put("1", "000"); // 指数
		LEVELFIRSTMAP_SH.put("2", "600,601,603,732,730,780");// A股
		LEVELFIRSTMAP_SH.put("3", "900");// B股
		LEVELFIRSTMAP_SH.put("4", "50,51,52");// 基金
		LEVELFIRSTMAP_SH.put("5", "009,010,019,020,100,104,105,106,107,113,120,121,122,126,129,13");// 国债
		LEVELFIRSTMAP_SH.put("6", "580,582");// 权证

		// 深圳
		LEVELFIRSTMAP_SZ.put("1", "39"); // 指数
		LEVELFIRSTMAP_SZ.put("2", "00,30");// A股
		LEVELFIRSTMAP_SZ.put("3", "20");// B股
		LEVELFIRSTMAP_SZ.put("4", "15,16,18");// 基金
		LEVELFIRSTMAP_SZ.put("5", "10,11,12");// 国债
		LEVELFIRSTMAP_SZ.put("6", "08,28,38");// 权证

		LEVELSECONDMAP_SZ.put("21", "30");// 创业板
		LEVELSECONDMAP_SZ.put("22", "002");// 中小板
		LEVELSECONDMAP_SZ.put("41", "15");// ETF
		LEVELSECONDMAP_SZ.put("42", "16");// LOF
	}


	/**
	 * 转换为8位证券代码, 支持指数, A股, 基金
	 *
	 * @param code
	 *            6位证券代码
	 * @param marketID
	 *            市场ID
	 * @return String
	 */
	public static String convertTo8(String code, int marketID) {
		if(marketID == 3 || marketID == 4 || marketID == 5){
			return marketID+"1"+code;
		}
		if (code.length() != 6) {
			return code;
		} else {
			StringBuilder sb = new StringBuilder();
			if (marketID == 1) {
				// 上证
				for (Map.Entry<String, String> entry : LEVELFIRSTMAP_SH.entrySet()) {
					boolean isExponent = false;
					if (FIRST_TYPE_INDEX.equals(entry.getKey())) {
						isExponent = true;
					}
					String[] codeStart = entry.getValue().split(",");
					for (String cs : codeStart) {
						if (code.indexOf(cs) == 0 && isExponent) {
							return sb.append("10").append(code).toString();
						} else if (code.indexOf(cs) == 0 && !isExponent) {
							return sb.append("11").append(code).toString();
						}
					}
				}
			} else if (marketID == 2) {
				// 深证
				for (Map.Entry<String, String> entry : LEVELFIRSTMAP_SZ.entrySet()) {
					boolean isExponent = false;
					if (FIRST_TYPE_INDEX.equals(entry.getKey())) {
						isExponent = true;
					}
					String[] codeStart = entry.getValue().split(",");
					for (String cs : codeStart) {
						if (code.indexOf(cs) == 0 && isExponent) {
							return sb.append("20").append(code).toString();
						} else if (code.indexOf(cs) == 0 && !isExponent) {
							return sb.append("21").append(code).toString();
						}
					}
				}
			}
			return code;
		}
	}




	/**
	 * 每个klinepoint的大小m
	 */
	private static final int blockSize = 132+8;

	private final static int BufCount = 250;


	public static List<CommonKlinePoint> getKLineByTime(File file, final long startTime, final long endTime){
		// 先对参数进行必要的合法性判断(正负)
	/*	if ((startTime < 0) || (endTime < 0)) {
			//logger.error("please assign the time correctly!");
			return new ArrayList<CommonKlinePoint>();
		} else if (startTime > endTime) { // 两时间参数相等或是start>endTime的特殊情况
			//logger.error("please assign the startTime and endTime correctly!");
			return new ArrayList<CommonKlinePoint>();
		}
		// 读取历史文件名称
		// 得到文件路径  （E:\quotedata\datas\shdata\kline\day\0\000010.data）
		final String historyFileName = getKlinePath(stockCode, marketid, klinetype);*/

		//final String historyFileName = filePath;

		// 用endTime和文件末每个klinePoint的时间循环比较
		long location = -1;
		long fileLength = 0;

		final LinkedList<CommonKlinePoint> k_points = new LinkedList<CommonKlinePoint>();
		if (!file.exists()) {
			return new ArrayList<CommonKlinePoint>();
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");

			fileLength = raf.length();

			location = fileLength - fileLength % blockSize;

			final BufferedRandomAccess bufRa = new BufferedRandomAccess(raf, blockSize, BufCount, BufferedRandomAccess.CACHE_BEFORE);

			final DataInputStream dis = new DataInputStream(bufRa);

			long diff = -1;
			//先根据终止时间从文件中查找
			do {
				location -= blockSize;
				if (location < 0) {
					break;
				}
				bufRa.seek(location + 124);
				final long tmpTime = dis.readLong();
				diff = endTime - tmpTime;
			} while (diff < 0);//要找的时间小于当前点时间继续

			//没有找到endTime之前的点就返回空List
			if ( diff < 0) {
				return new ArrayList<CommonKlinePoint>();
			}

			//把小于等于结束时间的当前点加入列表
			if (diff >= 0) {
				location += blockSize;
			}

			// 找到以后就从后向前与起始时间相比较
			while (location > 0) {
				location -= blockSize;
				bufRa.seek(location);
				final CommonKlinePoint k_point = readKlinePoint(dis);
				if (startTime - k_point.getKeyDate() <= 0) {
					k_points.addFirst(k_point);
				} else {
					break;
				}
			}

			return k_points;
		} catch (final FileNotFoundException e) {
			return null;
		} catch (final IOException e) {
			return null;
		} finally {
			close(raf);
		}
	}





	private static String getKlinePath(String stockCode, int marketid, String klinetype) {

		StringBuffer filepath = new StringBuffer();
		filepath.append("E:\\quotedata\\datas");
		filepath.append(File.separator);
		if(marketid == 1){
			filepath.append("shdata");
		}else if(marketid == 2){
			filepath.append("szdata");
		}
		filepath.append(File.separator);
		filepath.append("kline");
		filepath.append(File.separator);
		filepath.append(klinetype);
		filepath.append(File.separator);
		filepath.append(stockCode.substring(stockCode.length() - 1, stockCode.length()));
		filepath.append(File.separator);
		filepath.append(stockCode + ".data");

		return filepath.toString();
	}



	/**
	 * 从文件中读取一个KLinePoint点并返回 Method for readKlinePoint.
	 *
	 * @param dis
	 * @return KlinePoint
	 * @throws IOException
	 */
	private static CommonKlinePoint readKlinePoint(final DataInputStream dis) throws IOException {
		final CommonKlinePoint k_point = new CommonKlinePoint();
		k_point.setDate(dis.readLong());
		k_point.setOpen(dis.readDouble());
		k_point.setHigh(dis.readDouble());
		k_point.setLow(dis.readDouble());
		k_point.setCur(dis.readDouble());
		k_point.setSum(dis.readDouble());
		k_point.setVolume5PointAvg(dis.readLong());
		k_point.setPrice5PointAvg(dis.readDouble());
		k_point.setVolume10PointAvg(dis.readLong());
		k_point.setPrice10PointAvg(dis.readDouble());
		k_point.setPrice20PointAvg(dis.readDouble());
		k_point.setPrice60PointAvg(dis.readDouble());
		k_point.setPrice120PointAvg(dis.readDouble());
		k_point.setPrice240PointAvg(dis.readDouble());
		k_point.setVolume(dis.readLong());
		k_point.setSwap(dis.readFloat());
		k_point.setKeyDate(dis.readLong());
		k_point.setSettlePrice(dis.readDouble());
		return k_point;
	}


	/**
	 * 关闭文件 Method for close.
	 *
	 * @param raf
	 */
	private static void close(final RandomAccessFile raf) {
		if (raf != null) {
			try {
				raf.close();
			} catch (final IOException e) {
			}
		}
	}


	public static void main(String args[]){

		//取出D:/s

	/*	for (String codeStr : stockList) {

		}*/
		//取出每个股票的代码

		String stockCode = "600000";
		int marketId = 1;
		/*long startTime =201007280000l;
		long endTime = 201607290000l;*/

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long startTime = getStartTime(3);
		long endTime = Long.parseLong(sdf.format(new Date()));
		List<CommonKlinePoint> klist = HisKlineReader.getKLineByTime(new File(""), startTime, endTime);


		//取出109个满足条件的点


		//List<CommonKlinePoint> klist =  HisKlineReader.getKLineByTime(stockCode, marketid, "1minute", startTime, endTime);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("src/main/resources/file/21300606[20170411-20170913]");
			FileChannel fileChannel = fos.getChannel();
			long startDate = 0;
			long endDate = 0;
			System.out.println("klist.size():"+klist.size());
			for (int i = 0; i < klist.size(); i++) {

				CommonKlinePoint point = klist.get(i);

				if(i==0){
					startDate = point.getDate();
				}
				if(i==klist.size()-1){
					endDate =  point.getDate();
				}
		/*			long open = (long) (bb.getFloat() * 1000);//小数点向前移动三位数
					long hight = (long) (bb.getFloat() * 1000);
					long low = (long) (bb.getFloat() * 1000);
					long close = (long) (bb.getFloat() * 1000);
					long money = (long) (bb.getDouble() * 1000);
					long amount = bb.getLong();*/
				long date = point.getDate();
				int trdate= new Long(date).intValue();
				float open = (float) point.getOpen();//开盘价
				float high = (float)point.getHigh();//最高价
				float low = (float)point.getLow();//最低价
				float cur = (float)point.getCur();//当前价格
				double money = point.getSum();//成交额 money
				long amount = point.getVolume();//成交量
				System.out.println("date:"+date+"open:"+open+"high:"+high+"low:"+low+"cur:"+cur+"money:"+money+"amount:"+amount);
				/*System.out.println(point.toString());*/
				//这里分配好的capacity的值时永远不会变的
				ByteBuffer buffer = ByteBuffer.allocate(512);
				buffer.putInt(trdate);
				buffer.putFloat(open);
				buffer.putFloat(high);
				buffer.putFloat(low);
				buffer.putFloat(cur);
				buffer.putDouble(money);
				buffer.putLong(amount);
				buffer.flip();
				fileChannel.write(buffer);
			}
			String fileName = "["+startDate+"-"+endDate+"]";
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

	//获取开始时间
	private static long getStartTime(int year){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Long startTime =0L;
		if(startTime == null || startTime == 0){
			calendar.add(Calendar.YEAR, -year);
			startTime = Long.parseLong(sdf.format(calendar.getTime()));
		}
		//System.out.println("startTime:"+startTime);
		return startTime;
	}


}
