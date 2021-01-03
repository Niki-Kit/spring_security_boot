package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Person;
import web.service.PersonService;

@Controller
@RequestMapping("/admin")
public class PeopleController {

    @Autowired
    @Qualifier("personServiceImp")
    private PersonService personService;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.index());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person", personService.show(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personService.save(person);
        return "adminPage";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long  id) {
        model.addAttribute("person", personService.show(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") Long id) {
        personService.update(id, person);
        return "adminPage";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long  id) {
        personService.delete(id);
        return "adminPage";
    }

}