package com.example.firstProject.demo.api;

import com.example.firstProject.demo.model.Person;
import com.example.firstProject.demo.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/v1/person")
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public String addPerson(@Valid @NonNull @RequestBody Person person){
        if(personService.addPerson(person) == 1){
            return "Success adding person";
        }
        return "Error adding person";

    }
    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPerson();
    }

    @GetMapping(path = "{id}")
    public Optional<Person> getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id);
    }
    @DeleteMapping(path = "{id}")
    public int deletePersonById(@PathVariable("id") UUID id){
        return personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public int updatePersonById(@PathVariable("id") UUID id ,@Valid @NonNull @RequestBody Person person){
        return personService.updatePerson(id,person);
    }

}
