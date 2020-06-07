package com.clarc.main.admin;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.clarc.main.ClarcAdmins;

@Controller
@RequestMapping("/adminAdminsAdd")
public class AdminAdminsAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcAdmins", new ClarcAdmins());

      return "admin/adminAdminsAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcAdmins") @Valid ClarcAdmins clarcAdmins, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminAdminsAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_admins (id,"
            + "adminType,"
            + "adminName,"
            + "adminEmail,"
            + "adminAccessAreas,"
            + "adminLogin,"
            + "adminPassword,"
            + "adminAuthority,"
            + "adminStatus,"
            + "lastLoginTime,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP,"
            + "CURRENT_TIMESTAMP)",
	  		clarcAdmins.getId(),
	  		clarcAdmins.getAdminType(),
	  		clarcAdmins.getAdminName(),
	  		clarcAdmins.getAdminEmail(),
	  		clarcAdmins.getAdminAccessAreas(),
	  		clarcAdmins.getAdminLogin(),
	  		clarcAdmins.getAdminPassword(),
	  		clarcAdmins.getAdminAuthority(),
	  		clarcAdmins.getAdminStatus(),
	  		clarcAdmins.getLastLoginTime(),
	  		clarcAdmins.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminAdminsAdd";
   }
}

