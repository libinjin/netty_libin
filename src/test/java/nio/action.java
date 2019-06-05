package nio;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.junit.Test;

public class action {

    /**
     * 测试核数
     */
    @Test
    public void testDefalut(){
       int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println("DEFAULT_EVENT_LOOP_THREADS:"+DEFAULT_EVENT_LOOP_THREADS);
    }
}
