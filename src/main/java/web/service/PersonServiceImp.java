package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.PersonDAO;
import web.dao.RoleDAO;
import web.model.Person;
import web.model.Role;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("personServiceImp")
public class PersonServiceImp implements PersonService, UserDetailsService {

    @Autowired
    @Qualifier("personDao")
    PersonDAO personDAO;

    @Autowired
    @Qualifier("role")
    RoleDAO roleDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Person> index() {
        return personDAO.findAll();
    }
    @Transactional
    public Person show(Long  id) {
        Optional<Person> userFromDb = personDAO.findById(id);
        return userFromDb.orElse(new Person());
    }
    @Transactional
    public boolean save(Person person) {
        person.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        String pass = passwordEncoder.encode(person.getPassword());
        person.setPassword(pass);
        personDAO.save(person);
        return true;
    }
    @Transactional
    public void update(Long  id, Person updatedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
        String pass= passwordEncoder.encode(updatedPerson.getPassword());
        personToBeUpdated.setPassword(pass);
    }
    @Transactional
    public void delete(Long  id) {
        Optional<Person> employee = personDAO.findById(id);

        if(employee.isPresent()) {
            personDAO.deleteById(id);
        }
    }

    @Override
    public Person findByName(String username) {
        return personDAO.findByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDAO.findByName(username);

        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return person;
    }
}
