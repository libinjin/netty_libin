package client;

import common.Person;
import common.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) {
        //服务端用的TFramedTransport，这里也用TFramedTransport
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899));
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);
        try {
            //相当于打开客户端
            transport.open();
            Person person = client.getPersonByName("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());
            System.out.println("------------------------");
            Person person1 = new Person();
            person1.setUsername("jiwna");
            person1.setAge(26);
            person1.setMarried(false);
            client.savePersion(person1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }


    }
}
