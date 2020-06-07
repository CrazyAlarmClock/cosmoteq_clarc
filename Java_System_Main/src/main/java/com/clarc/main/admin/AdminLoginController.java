package com.clarc.main.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mainadmin")
public class AdminLoginController {

   @GetMapping
   public String showLoginPage(Model model) {
	   
      return "admin/adminLogin";
   }
}
