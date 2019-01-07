package service;

import common.Person;
import common.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransportException;

public class BootStrap {
    public static void main(String[] args) {
        try {

            //启动ServerSocket
            TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
           //高可用一个Server,arg是用来构建好一系列的信息
            THsHaServer.Args arg = new THsHaServer.Args(socket).workerThreads(4);
           //创建一个处理器process
            PersonService.Processor<PersonServiceImp> processor = new PersonService.Processor<>(new PersonServiceImp());
            //设置一个工厂
            arg.protocolFactory(new TCompactProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));

            TServer server = new THsHaServer(arg);
            System.out.println("启动服务端");
            //这是一个死循环，异步非阻塞，永远不会退出的
            server.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }
}
