package ie.markoconnor.controller;

import ie.markoconnor.service.TodoService;
import ie.markoconnor.web.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@SessionAttributes("name")
public class TodoController {

    @Autowired
    TodoService service;

    @RequestMapping(value="/list-todos", method = RequestMethod.GET)
    public String showTodos(ModelMap model){
        String name = (String) model.get("name");
        model.put("todos", service.retrieveTodos(name));
        return "list-todos";
    }

    @RequestMapping(value="/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model){
        model.addAttribute("todo", new Todo(0, (String) model.get("name"),
                "Default Description", new Date(), false ));
        return "todo";
    }

    @RequestMapping(value="/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam int id){
        service.deleteTodo(id);
        return "redirect:/list-todos";
    }
    @RequestMapping(value="/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
        Todo todo = service.retrieveTodos(id);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value="/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model,@Valid Todo todo, BindingResult result){

        if (result.hasErrors()){
            return "todo";
        }
        todo.setUser((String) model.get("name"));
        todo.setTargetDate((Date) model.get("targetDate"));

        service.updateTodo(todo);

        return "redirect:/list-todos";
    }

    @RequestMapping(value="/add-todo", method = RequestMethod.POST)
    public String addTodos(ModelMap model, @Valid Todo todo, BindingResult result){
        if (result.hasErrors()){
            return "todo";
        }
        service.addTodo((String) model.get("name"), todo.getDesc(), new Date(), true);
        return "redirect:/list-todos";

    }
}

