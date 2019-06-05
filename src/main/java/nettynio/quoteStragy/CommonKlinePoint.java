package nettynio.quoteStragy;


/**
 * 采取硬盘存储和内存存储相结合的方法
 *
 * @author hucy Create on 2008-1-4 11:02:35
 */
public class CommonKlinePoint implements Cloneable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * k线点时间日期(格式： 如：日、周、月、季、年YYYYMMDD ； 分钟k线 YYYYMMDDHHMM)
	 */
	private long date;
	/**
	 * 开盘价
	 */
	private double open;
	/**
	 * 最高价
	 */
	private double high;
	/**
	 * 最低价
	 */
	private double low;
	/**
	 * 当前价格
	 */
	private double cur;
	/**
	 * 成交额
	 */
	private double sum;

	/**
	 * 成交量
	 */
	private long volume;


	/**
	 * 换手率
	 */
	private float swap;

	private long volume5PointAvg;

	private double price5PointAvg;

	private long volume10PointAvg;

	private double price10PointAvg;

	private double price20PointAvg;

	private double price60PointAvg;

	private double price120PointAvg;

	private double price240PointAvg;

	/**
	 * 仅供内部计算使用，存储标准周期时间， 年类型: yyyy 季:yyyySe 月:yyyyMM 周:yyyyww 日:yyyyMMdd 分钟k线：yyyyMMddHHmm 周期编码
	 */
	private long keyDate;

	/**
	 * 结算价
	 */
	private double settlePrice;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getCur() {
		return cur;
	}

	public void setCur(double cur) {
		this.cur = cur;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getKeyDate() {
		return keyDate;
	}

	public void setKeyDate(long keyDate) {
		this.keyDate = keyDate;
	}

	public float getSwap() {
		return swap;
	}

	public void setSwap(float swap) {
		this.swap = swap;
	}

	public long getVolume5PointAvg() {
		return volume5PointAvg;
	}

	public void setVolume5PointAvg(long volume5PointAvg) {
		this.volume5PointAvg = volume5PointAvg;
	}

	public double getPrice5PointAvg() {
		return price5PointAvg;
	}

	public void setPrice5PointAvg(double price5PointAvg) {
		this.price5PointAvg = price5PointAvg;
	}

	public long getVolume10PointAvg() {
		return volume10PointAvg;
	}

	public void setVolume10PointAvg(long volume10PointAvg) {
		this.volume10PointAvg = volume10PointAvg;
	}

	public double getPrice10PointAvg() {
		return price10PointAvg;
	}

	public void setPrice10PointAvg(double price10PointAvg) {
		this.price10PointAvg = price10PointAvg;
	}

	public double getPrice20PointAvg() {
		return price20PointAvg;
	}

	public void setPrice20PointAvg(double price20PointAvg) {
		this.price20PointAvg = price20PointAvg;
	}

	public double getPrice60PointAvg() {
		return price60PointAvg;
	}

	public void setPrice60PointAvg(double price60PointAvg) {
		this.price60PointAvg = price60PointAvg;
	}

	public double getPrice120PointAvg() {
		return price120PointAvg;
	}

	public void setPrice120PointAvg(double price120PointAvg) {
		this.price120PointAvg = price120PointAvg;
	}

	public double getPrice240PointAvg() {
		return price240PointAvg;
	}

	public void setPrice240PointAvg(double price240PointAvg) {
		this.price240PointAvg = price240PointAvg;
	}

	public double getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(double settlePrice) {
		this.settlePrice = settlePrice;
	}

	public CommonKlinePoint() {
	}

	public CommonKlinePoint(long date, double open, double high, double low, double cur, double sum, long volume) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.cur = cur;
		this.sum = sum;
		this.volume = volume;
	}

	@Override
	public String toString() {
		StringBuffer strbuf = new StringBuffer(this.getClass().getSimpleName() + "==");
		strbuf.append("date:" + date);
		strbuf.append(",open:" + open);
		strbuf.append(",high:" + high);
		strbuf.append(",low:" + low);
		strbuf.append(",cur:" + cur);
		strbuf.append(",sum:" + sum);
		strbuf.append(",volume:" + volume);
		return strbuf.toString();
	}

}
