package ua.softserve.ita.controller.profile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.PersonService;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
@Api(value = "PersonControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get person by specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Person.class)})
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Person with id %d was not found?!", id)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update person")
    public Person update(@Valid @RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete person with specific id")
    public void deleteById(@PathVariable("id") Long id) {
        personService.deleteById(id);
    }

}
