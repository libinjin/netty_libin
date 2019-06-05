package nettynio.klineGames;

public class Data {

    private int date;
    private long open;
    private long hight;
    private long low;
    private long close;
    private long money;
    private long amount;

    public Data(int date, long open, long hight, long low, long close, long money, long amount) {
        this.date = date;
        this.open = open;
        this.hight = hight;
        this.low = low;
        this.close = close;
        this.money = money;
        this.amount = amount;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getHight() {
        return hight;
    }

    public void setHight(long hight) {
        this.hight = hight;
    }

    public long getLow() {
        return low;
    }

    public void setLow(long low) {
        this.low = low;
    }

    public long getClose() {
        return close;
    }

    public void setClose(long close) {
        this.close = close;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Data{" +
                "date=" + date +
                ", open=" + open +
                ", hight=" + hight +
                ", low=" + low +
                ", close=" + close +
                ", money=" + money +
                ", amount=" + amount +
                '}';
    }

    public static void main(String[] args) {
        String path = "E:\\quotedata\\datas\\szdata";
        System.out.println(path.substring(path.lastIndexOf("\\")+1));
    }
}
