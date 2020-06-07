package com.clarc.main.admin;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.clarc.main.ClarcUsers;

@Controller
@RequestMapping("/adminUsersEdit")
public class AdminUsersEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcUsers> getRecord(String id)
   {
      List<ClarcUsers> r = jdbc.query("SELECT * FROM clarc_users WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcUsers>() {
		     @Override
		     public ClarcUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcUsers(rs.getLong("id"),
                   rs.getString("userFirstName"),
                   rs.getString("userMiddleName"),
                   rs.getString("userLastName"),
                   rs.getString("userEmail"),
                   rs.getString("userLogin"),
                   rs.getString("userPassword"),
                   rs.getString("userPasswordMatch"),
                   rs.getLong("userCompanyId"),
                   rs.getString("userCompanyName"),
                   rs.getString("userCompanyPosition"),
                   rs.getString("userAddress"),
                   rs.getString("userPhone"),
                   rs.getString("userCity"),
                   rs.getString("userZip"),
                   rs.getString("userState"),
                   rs.getString("userCountry"),
                   rs.getString("userAboutMe"),
                   rs.getString("profilePhotoUrl"),
                   rs.getInt("userType"),
                   rs.getString("userAuthority"),
                   rs.getInt("userMembership"),
                   rs.getInt("userActivated"),
                   rs.getString("activationCode"),
                   rs.getString("lastLoginTime"),
                   rs.getString("prevLoginTime"),
                   rs.getString("fireBaseToken"),
                   rs.getInt("status"),
                   rs.getInt("agree"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcUsers> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcUsersRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcUsers", new ClarcUsers());

      return "admin/adminUsersEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcUsers") @Valid ClarcUsers clarcUsers, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminUsersEdit";
      }

      int n = jdbc.update("UPDATE clarc_users SET id=?,"
            + "userFirstName=?,"
            + "userMiddleName=?,"
            + "userLastName=?,"
            + "userEmail=?,"
            + "userLogin=?,"
            + "userPassword=?,"
            + "userPasswordMatch=?,"
            + "userCompanyId=?,"
            + "userCompanyName=?,"
            + "userCompanyPosition=?,"
            + "userAddress=?,"
            + "userPhone=?,"
            + "userCity=?,"
            + "userZip=?,"
            + "userState=?,"
            + "userCountry=?,"
            + "userAboutMe=?,"
            + "profilePhotoUrl=?,"
            + "userType=?,"
            + "userAuthority=?,"
            + "userMembership=?,"
            + "userActivated=?,"
            + "activationCode=?,"
            + "lastLoginTime=CURRENT_TIMESTAMP,"
            + "prevLoginTime=CURRENT_TIMESTAMP,"
            + "fireBaseToken=?,"
            + "status=?,"
            + "agree=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
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
            clarcUsers.getAddedTime(),
            clarcUsers.getId());

	  if(n > 0)
	  {
		 List<ClarcUsers> results = getRecord(String.valueOf(clarcUsers.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcUsersRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcUsers", new ClarcUsers());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminUsersEdit";
   }
}

