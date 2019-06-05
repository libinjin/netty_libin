package nettynio.klineGames;

import java.nio.ByteBuffer;

/**
 * Created by leo on 2017/9/14.
 */
public class GameData {


	private String code;//股票代码
	private String name;//股票名称
	private String startDate;//K线起始时间(不算前60根，前60根算60日均线用)
	private String endDate;//K线结束时间
	private double rangeRate;//区间涨幅(endDate收盘价-startDate开盘价/startDate开盘价)
	private static final String ENCODE = "UTF-8";//字符集
	private static int init_kline_count = 109;//初始化K线根数
	private static int start_step = 78;
	private static int total_round = 60;//总回合数
	private static int round_kline_count = 1;//单回合K线根数

	/**
	 * 行情数据初始化
	 *
	 * @param hqData
	 * @return
	 */
	public String init(byte[] hqData) {

		ByteBuffer bb = ByteBuffer.wrap(hqData);

		code = String.valueOf(bb.getInt());
		int len = bb.getInt();
		byte[] temp = new byte[len];

		bb.get(temp);

		int step = 1;//计数变量，计算日期区间使用

		try {

			name = new String(temp, ENCODE);//金太阳
			/**
			 * 初始化K线数据
			 */
			for (int i = 0; i < init_kline_count; i++) {

			/*	private final Map<String,Object> values; //只有 开高低收为 key的 value都为null
				private final Map<String,DataField> cols;//open  - [name=,index=,length=,caption=...]
				private final String[] names;    开高低收                 */
				int date = bb.getInt();
				long open = (long) (bb.getFloat() * 1000);//小数点向前移动三位数
				long hight = (long) (bb.getFloat() * 1000);
				long low = (long) (bb.getFloat() * 1000);
				long close = (long) (bb.getFloat() * 1000);
				long money = (long) (bb.getDouble() * 1000);
				long amount = bb.getLong();

				Data data = new Data(date,open,hight,low,close,money,amount);

				System.out.println(data);

				step++;
			}



		} catch (Exception e) {

			e.printStackTrace();
		}

		return "";
	}

	public static void main(String[] args) {

		System.out.println("获取数据：");

		//byte[] bytes = GameHelperService.getStock("D:\\stocks\\21300606[20170411-20170913]");
		byte[] bytes = GameHelperService.getStock("D:\\stocks\\shdata\\-10\\10000010[20180103-20180615]");

		GameData gameData = new GameData();
		gameData.init(bytes);

	}

}
