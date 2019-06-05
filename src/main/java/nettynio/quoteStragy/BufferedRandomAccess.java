package nettynio.quoteStragy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 对随即读取文件的功能添加 缓存功能，并继承ByteArrayInputStream
 * 缓存按块进行缓存
 * @author hucy 2008-4-3 上午10:24:08
 *
 */
public class BufferedRandomAccess extends ByteArrayInputStream  {
	//缓存块大小
	private  int blockSize;
	//一次缓存的块个数
	private  int cacheNum;
	//随即读取文件
	private RandomAccessFile raf;
	//文件长度
	private long fileLen;
	//文件读取时 缓存的方向，如果向后缓存重当前位置开始读取，如果向前缓存为 指定位置-blockSize 开始读取
	private int direction;
	
	private long startRafPos;
	private long endRafPos;
	
	public static final int  CACHE_BEFORE =1;
	public static final int CACHE_AFTER =2;
	
	public BufferedRandomAccess(RandomAccessFile raf , int blockSize ,
                                int cacheNum , int direction) throws IOException {
		super( new byte[blockSize *cacheNum] );
		//初始化时没有数据
		super.pos =0;
		super.count =0;
		super.mark=0;
		
		this.raf = raf;
		this.blockSize = blockSize;
		this.cacheNum = cacheNum;
		this.direction = direction;
		
		this.startRafPos =0;
		this.endRafPos =0;
		this.fileLen = raf.length();
	}

	/**
	 * 调整缓存位置
	 * @param seekpos
	 * @throws IOException 
	 */
	public void seek(long seekpos) throws IllegalArgumentException{
		try{
			if( startRafPos ==0 &&  endRafPos==0) {
				fillBuf(seekpos);
			}else if(seekpos >= startRafPos && seekpos < endRafPos ){
				super.pos = (int)(seekpos - startRafPos);
			}else if( seekpos < startRafPos ||  seekpos >= endRafPos ){
				fillBuf(seekpos);
			}
		}catch(IOException ex){
			throw new IllegalArgumentException(ex);
		}
	}
	
	/**
	 * 把文件内容读取到缓存中
	 * @param seekpos
	 * @return
	 * @throws IOException
	 */
    private void fillBuf(long seekpos) throws IOException{
		//重新填充缓存,重置所有参数
		if(direction == CACHE_BEFORE ){
			this.endRafPos = seekpos + blockSize > fileLen ? fileLen : seekpos + blockSize;
			this.startRafPos = this.endRafPos - cacheNum *blockSize >0 ? this.endRafPos - cacheNum *blockSize :0;
			raf.seek( startRafPos );
			super.count = raf.read( super.buf);
			super.pos = (int)(seekpos - this.startRafPos);
		}else if(direction == CACHE_AFTER ){
			this.startRafPos =  seekpos > fileLen ? fileLen  : seekpos ;
			this.endRafPos = this.startRafPos + cacheNum *blockSize  > fileLen ? fileLen :  this.startRafPos + cacheNum *blockSize;
			
			raf.seek( startRafPos );
			super.count = raf.read( super.buf);
			super.pos =(int)(seekpos - this.startRafPos);			
		}
    }	
	
    /**
     * 返回RandomAccessFile剩余长度
     * @return
     */
    public int available() {
    	return (int)(this.fileLen - this.startRafPos - super.pos);
    }
    
    /**
     * 返回RandomAccessFile长度
     * @return
     */
    public long length(){
    	return this.fileLen;
    }

    /**
     * 返回当前读取点在RandomAccessFile的位置
     * @return
     */
    public long getFilePointer() {
    	return this.startRafPos + super.pos;
    }
    
    /**
     * 从RandomAccessFile 读取len个字节到字节数据b
     */
    public int read(byte b[], int off, int len) {
    	if (b == null) {
    	    throw new NullPointerException();
    	} else if ((off < 0) || (off > b.length) || (len < 0) ||
    		   ((off + len) > b.length) || ((off + len) < 0)) {
    	    throw new IndexOutOfBoundsException();
    	}
    	
    	int hasReadLen = 0 ;

    	while( this.startRafPos + super.pos <  this.fileLen  && hasReadLen < len ){
    		//本次要读取的内容
    		int remainlen = len - hasReadLen;
    		if( super.pos >= super.count){
    			seek(this.startRafPos + super.pos);
    		}
        	int onelen = pos + remainlen > count ? count - pos : remainlen;
    		
    		//System.out.println("count:"+count+" startpos: " +this.startRafPos+ "  endpos"+ this.endRafPos+ " buf.length: " +buf.length +"  pos:"+pos +" off:"+ off +"  onelen:" + onelen +" len:"+len);
    		System.arraycopy(buf, pos, b, off, onelen);
    		//重新填充
    		hasReadLen +=onelen;
        	pos += onelen;
        	off += onelen;
    	}
    	return hasReadLen == 0? -1: hasReadLen;
    }
    
    /**
     * 读取一个字节
     */
    public int read(){
    	if(pos >= count){
    		//seek一下
    		seek(this.startRafPos + super.pos);
    	}
    	return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }
    

	
	/**
	 * 跳过n个字节
	 */
    public long skip(long n) {
    	if ( super.pos + n +this.startRafPos > fileLen) {
    	    n = fileLen - (  super.pos + this.startRafPos );
    	}
    	if (n < 0) {
    	    return 0;
    	}
    	this.seek(this.startRafPos + n+ super.pos);
    	return n;
    }
    
    /**
     * 是否支持设置标记
     */
    public boolean markSupported() {
    	return true;
    }    
    
    public void mark(int readAheadLimit) {
    	mark = pos;
    }    
    
    public void reset() {
    	this.seek(mark);
    }    
    
    public void close() throws IOException {
    }
	
}
