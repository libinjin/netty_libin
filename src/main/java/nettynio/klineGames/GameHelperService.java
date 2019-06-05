package nettynio.klineGames;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by leo on 2017/9/14.
 */
public class GameHelperService {



	public static void main(String[] args) {
/*
		byte[] bytes = GameHelperService.getStock("D:\\stocks\\11600004[20150106-20150616]");
		JSONObject json = new JSONObject();

		ByteBuffer bb = ByteBuffer.wrap(bytes);
		String code = String.valueOf(bb.getInt());
		json.put("code",code);
		byte[] temp = new byte[bb.getInt()];
		bb.get(temp);
		try{
			String name = new String(temp, "UTF-8");
			json.put("name",name);
			JSONArray array = new JSONArray();
			for (int i = 0; i < 109; i++) {
				int date = bb.getInt();
				long open = (long) (bb.getFloat() * 1000);
				long hight = (long) (bb.getFloat() * 1000);
				long low = (long) (bb.getFloat() * 1000);
				long close = (long) (bb.getFloat() * 1000);
				long money = (long) (bb.getDouble() * 1000);
				long amount = bb.getLong();
				JSONObject item = new JSONObject();
				item.put("date",date);
				item.put("open",open);
				item.put("hight",hight);
				item.put("low", low);
				item.put("close",close);
				item.put("money",money);
				item.put("amount",amount);
				array.add(item);

			}
			json.put("data",array);
		}catch (Exception e){
			e.printStackTrace();
		}

		System.out.println(json.toJSONString());*/


//		System.out.println(GameHelperService.getStock().length);
	}

	/**
	 * 获取股票数据
	 *
	 *
	 * @return
	 */
	public static byte[] getStock() {
		File dir = new File("");


		File[] files = dir.listFiles();

		int num = new Random().nextInt(files.length);

		File file = files[num];

		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream((int)file.length()+4);
			//TODO
			int stockCode = Integer.parseInt(file.getName().substring(0, 8));
			bos.write(int2Bytes(stockCode));
			byte[] b = new byte[1024];
			int len = -1;
			while((len = fis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			return bos.toByteArray();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * 获取股票数据
	 * @return
	 */
	public static byte[] getStock(String path) {

		File file = new File(path);

		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream((int)file.length()+4);
			//TODO
			int stockCode = Integer.parseInt(file.getName().substring(0, 8));
			bos.write(int2Bytes(stockCode));
			byte[] b = new byte[1024];
			int len = -1;
			while((len = fis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			return bos.toByteArray();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
//		byte[] result = null;
//		HashMap<String, String> hm = new HashMap<>();
//		hm.put("method", "getStock");
//		try {
//			result = (byte[]) JhssRPCClient.sendRPCRequest("stock", hm);
//		} catch (Exception e) {
//			logger.error("选取股票失败", Constants.PROJECT_NAME, "getStock", e);
//		}
//		return result;
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



}
