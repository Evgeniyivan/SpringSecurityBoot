package users.controller;

import javax.validation.Valid;

import users.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import users.model.User;
import users.service.UserService;

@Controller
public class LoginController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public LoginController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping("/user")
	public String getCurrentUserInfo(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "show";
	}

	@GetMapping("/admin/users")
	public String showAllUsers(Model model) {
		model.addAttribute("usersList", userService.getAllUsers());
		return "users";
	}

	@GetMapping("/admin/users/{id}")
	public String showUserById(@PathVariable("id") Long id, Model model) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "show";
	}

	@GetMapping("/admin/users/new")
	public String newUser(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("allRoles", roleService.getAllRoles());
		return "new";
	}

	@PostMapping("/admin/users")
	public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
							 @RequestParam(value = "index", required = false) Long[] index) {
		if (bindingResult.hasErrors()) {
			return "new";
		}
		if (index != null) {
			for (Long id : index) {
				user.addRole(roleService.findById(id));
			}
		} else {
			user.addRole(roleService.findById(2L));
		}
		userService.save(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}/edit")
	public String editUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allRoles", roleService.getAllRoles());
		model.addAttribute("user", userService.getById(id));
		return "edit";
	}

	@PatchMapping("/admin/users/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
							 @RequestParam(value = "index", required = false) Long[] index) {
		if (bindingResult.hasErrors()) {
			return "edit";
		}

		if (index != null) {
			for (Long id : index) {
				user.addRole(roleService.findById(id));
			}
		} else {
			user.addRole(roleService.findById(2L));
		}
		userService.update(user);
		return "redirect:/admin/users";
	}

	@DeleteMapping("/admin/users/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
		return "redirect:/admin/users";
	}
	

}
