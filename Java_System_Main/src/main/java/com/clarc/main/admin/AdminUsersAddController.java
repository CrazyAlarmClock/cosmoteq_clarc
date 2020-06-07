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
import com.clarc.main.ClarcUsers;

@Controller
@RequestMapping("/adminUsersAdd")
public class AdminUsersAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcUsers", new ClarcUsers());

      return "admin/adminUsersAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcUsers") @Valid ClarcUsers clarcUsers, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminUsersAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_users (id,"
            + "userFirstName,"
            + "userMiddleName,"
            + "userLastName,"
            + "userEmail,"
            + "userLogin,"
            + "userPassword,"
            + "userPasswordMatch,"
            + "userCompanyId,"
            + "userCompanyName,"
            + "userCompanyPosition,"
            + "userAddress,"
            + "userPhone,"
            + "userCity,"
            + "userZip,"
            + "userState,"
            + "userCountry,"
            + "userAboutMe,"
            + "profilePhotoUrl,"
            + "userType,"
            + "userAuthority,"
            + "userMembership,"
            + "userActivated,"
            + "activationCode,"
            + "lastLoginTime,"
            + "prevLoginTime,"
            + "fireBaseToken,"
            + "status,"
            + "agree,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP,"
            + "CURRENT_TIMESTAMP,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcUsers.getId(),
	  		clarcUsers.getUserFirstName(),
	  		clarcUsers.getUserMiddleName(),
	  		clarcUsers.getUserLastName(),
	  		clarcUsers.getUserEmail(),
	  		clarcUsers.getUserLogin(),
	  		clarcUsers.getUserPassword(),
	  		clarcUsers.getUserPasswordMatch(),
	  		clarcUsers.getUserCompanyId(),
	  		clarcUsers.getUserCompanyName(),
	  		clarcUsers.getUserCompanyPosition(),
	  		clarcUsers.getUserAddress(),
	  		clarcUsers.getUserPhone(),
	  		clarcUsers.getUserCity(),
	  		clarcUsers.getUserZip(),
	  		clarcUsers.getUserState(),
	  		clarcUsers.getUserCountry(),
	  		clarcUsers.getUserAboutMe(),
	  		clarcUsers.getProfilePhotoUrl(),
	  		clarcUsers.getUserType(),
	  		clarcUsers.getUserAuthority(),
	  		clarcUsers.getUserMembership(),
	  		clarcUsers.getUserActivated(),
	  		clarcUsers.getActivationCode(),
	  		clarcUsers.getLastLoginTime(),
	  		clarcUsers.getPrevLoginTime(),
	  		clarcUsers.getFireBaseToken(),
	  		clarcUsers.getStatus(),
	  		clarcUsers.getAgree(),
	  		clarcUsers.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminUsersAdd";
   }
}

