package co.edu.icesi.ci.thymeval.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.controller.interfaces.UserController;
import co.edu.icesi.ci.thymeval.model.AdvancedInfo;
import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.model.BasicInfo;
import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.editInfo;
import co.edu.icesi.ci.thymeval.service.UserServiceImpl;

@Controller
public class UserControllerImpl implements UserController {

	UserServiceImpl userService;

	@Autowired
	public UserControllerImpl(UserServiceImpl userService) {
		this.userService = userService;
		;
	}

	@GetMapping("/users/add")
	public String addUser(Model model) {
		model.addAttribute("user", new UserApp());
		return "users/add-user";
	}

	@GetMapping("/users/del/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		UserApp user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}

	@GetMapping("/users/")
	public String indexUser(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}

	@PostMapping("/users/add/")
	public String saveUser(@Validated(BasicInfo.class) UserApp user, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		UserApp usermod = new UserApp();
		if(bindingResult.hasErrors()) {
			//model.addAttribute("users", userService.findAll());
			return "users/add-user";
		}
		if (!action.equals("Cancel"))
			usermod.setId(userService.save(user).getId());
			model.addAttribute("user", usermod);
			model.addAttribute("genders", userService.getGenders());
			model.addAttribute("types", userService.getTypes());
		return "users/add-user1";
	}
	
	
	@PostMapping("/users/add1")
	public String saveUser1(@Validated(AdvancedInfo.class) UserApp user, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("genders", userService.getGenders());
			model.addAttribute("types", userService.getTypes());
			return "users/add-user1";
		}
		if (!action.equals("Cancel")) {
			//model.addAttribute("user",userService.findById(user.getId())); 
			userService.updateUserForSave(user);
			model.addAttribute("users", userService.findAll());
		}
			
		return "redirect:/users/";
	}


	@GetMapping("/users/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<UserApp> user = userService.findById(id);
		if (user.isEmpty())
			throw new IllegalArgumentException("Invalid user Id:" + id);
		user.get().setPassword("");
		model.addAttribute("user", user.get());
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());
		return "users/update-user";
	}

	@PostMapping("/users/edit/{id}")
	public String updateUser(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action, @Validated(editInfo.class) UserApp user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("genders", userService.getGenders());
			model.addAttribute("types", userService.getTypes());
			return "users/update-user";
		}
		if (action != null && !action.equals("Cancel")) {
			userService.update(user);
			model.addAttribute("users", userService.findAll());
		}
		return "redirect:/users/";
	}
}
