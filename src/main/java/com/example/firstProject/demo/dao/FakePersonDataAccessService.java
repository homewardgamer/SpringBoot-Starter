package com.example.firstProject.demo.dao;

import com.example.firstProject.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> DB= new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id , person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPerson() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();

    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> toDelete = selectPersonById(id);
        if(toDelete.isEmpty())
            return 0;
        DB.remove(toDelete.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return selectPersonById(id)
                .map(person1 -> {
                    int index = DB.indexOf(person1);
                    if(index >=0){
                        DB.set(index , new Person(id , person.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }

}
