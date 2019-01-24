package service;

import common.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.net.InetSocketAddress;

public class BootStrap {
    public static void main(String[] args) {
        try {
            //启动ServerSocket
            //TNonblockingServerSocket tServer = new TNonblockingServerSocket(new InetSocketAddress(8899));
            //高可用一个Server,arg是用来构建好一系列的信息
            THsHaServer.Args arg = new THsHaServer.Args(new TNonblockingServerSocket(new InetSocketAddress(8899)));
            //创建一个处理器process
            PersonService.Processor<PersonServiceImp> processor = new PersonService.Processor<>(new PersonServiceImp());
            //设置一个工厂
            arg.protocolFactory(new TCompactProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));
            arg.workerThreads(5);

            TServer tserver = new THsHaServer(arg);
            System.out.println("启动服务端");
            //这是一个死循环，异步非阻塞，永远不会退出的
            tserver.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }
}
