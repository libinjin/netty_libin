package service;

import common.DataException;
import common.Person;
import common.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

public class PersonServiceImp implements PersonService.Iface {
  private Log log = LogFactory.getLog(PersonServiceImp.class);
    @Override
    public Person getPersonByName(String username) throws DataException, TException {
        System.out.println("name:"+username);
        Person person = new Person();
        log.info("+++++++++++++++++++++++++===");
        person.setUsername("asfsdfsfd");
        person.setAge(26);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePersion(Person person) throws DataException, TException {
        System.out.println("============================");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
