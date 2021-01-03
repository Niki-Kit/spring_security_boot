package web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Person;

@Repository("personDao")
public interface PersonDAO extends JpaRepository<Person, Long> {
    Person findByName(String username);
}