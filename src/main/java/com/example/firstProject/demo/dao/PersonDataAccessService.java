package com.example.firstProject.demo.dao;

import com.example.firstProject.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Person> personRowMapper(){
        return (resultSet , i)->{
            UUID personId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(personId,name);
        };
    }
    @Override
    public int insertPerson(UUID id, Person person) {
        final String query = "INSERT INTO person (id,name) VALUES (?,?)";
        return jdbcTemplate.update(query , id , person.getName());
    }

    @Override
    public List<Person> selectAllPerson() {
        final String query = "SELECT id, name FROM person;";
        return jdbcTemplate.query(query, personRowMapper());
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String query = "SELECT id, name FROM person WHERE id = ?;";
        Person person = jdbcTemplate.queryForObject(query ,personRowMapper(), id);
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String query = "DELETE FROM person WHERE id = ?;";
        return jdbcTemplate.update(query,id);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String query = "UPDATE person SET name=? WHERE id = ?";
        return jdbcTemplate.update(query,person.getName(),id);
    }
}
