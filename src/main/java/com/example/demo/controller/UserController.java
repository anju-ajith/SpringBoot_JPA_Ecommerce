package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/registerUser")
	public String saveCustomer(@ModelAttribute Users u) {

		userService.saveUser(u);
		return "index";
	}

	/*
	 * @PostMapping("/userLogin") public String userLogin(@ModelAttribute Users u,
	 * HttpSession session, RedirectAttributes ra) { List<Users> user =
	 * userService.loginUser(u.getEmail(), u.getPassword());
	 * System.out.println(user); if (user != null && !user.isEmpty()) {
	 * session.setAttribute("user", user.get(0)); return "redirect:/"; }
	 * ra.addFlashAttribute("error", "Your Email-Id/password is incorrect"); return
	 * "redirect:/userLoginPage"; }
	 */

	@GetMapping("/userProfile")
	public String getUserProfilePage(Authentication authentication, Model m) {

		String email = authentication.getName();
		Users u = userService.getUserByEmail(email);
		Users user = userService.getUserDetailsById(u.getUser_id());
		m.addAttribute("userProfile", user);
		return "userProfile";

	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute Users u, RedirectAttributes ra) {
		userService.editUserProfileById(u);
		ra.addFlashAttribute("success", "Profile updated successfully!");
		return "redirect:/userProfile";
	}

	/*
	 * @GetMapping("/logout") public String userLogout(HttpSession session) {
	 * session.invalidate(); return "redirect:/"; }
	 */

	@PostMapping("/resetUserPassword")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, Model m,
			RedirectAttributes redirectAttributes) {

		Users user = userService.getUserByEmail(email);
		if (user == null) {
			redirectAttributes.addFlashAttribute("error", "Email not found");

			return "redirect:/userLoginPage";
		}
		 user.setPassword(password);
		userService.updateUserPassword(user);
		redirectAttributes.addFlashAttribute("message", "Password reset successful!");
		return "redirect:/userLoginPage";
	}

	@GetMapping("/getCustomerDetails")
	public String getCustomerDetails(Model m) {
		List<Users> userList = userService.getUserDetails();
		m.addAttribute("users", userList);
		return "admin/customerList";

	}

	@GetMapping("/userOtpLogin")
	public String getOtpLoginPage() {
		return "userLoginPageOtp";
	}

}
