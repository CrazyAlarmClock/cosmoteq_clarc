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
import com.clarc.main.ClarcAdmins;

@Controller
@RequestMapping("/adminAdminsEdit")
public class AdminAdminsEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcAdmins> getRecord(String id)
   {
      List<ClarcAdmins> r = jdbc.query("SELECT * FROM clarc_admins WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcAdmins>() {
		     @Override
		     public ClarcAdmins mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcAdmins(rs.getLong("id"),
                   rs.getInt("adminType"),
                   rs.getString("adminName"),
                   rs.getString("adminEmail"),
                   rs.getString("adminAccessAreas"),
                   rs.getString("adminLogin"),
                   rs.getString("adminPassword"),
                   rs.getString("adminAuthority"),
                   rs.getInt("adminStatus"),
                   rs.getString("lastLoginTime"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcAdmins> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcAdminsRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcAdmins", new ClarcAdmins());

      return "admin/adminAdminsEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcAdmins") @Valid ClarcAdmins clarcAdmins, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminAdminsEdit";
      }

      int n = jdbc.update("UPDATE clarc_admins SET id=?,"
            + "adminType=?,"
            + "adminName=?,"
            + "adminEmail=?,"
            + "adminAccessAreas=?,"
            + "adminLogin=?,"
            + "adminPassword=?,"
            + "adminAuthority=?,"
            + "adminStatus=?,"
            + "lastLoginTime=CURRENT_TIMESTAMP,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
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
            clarcAdmins.getAddedTime(),
            clarcAdmins.getId());

	  if(n > 0)
	  {
		 List<ClarcAdmins> results = getRecord(String.valueOf(clarcAdmins.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcAdminsRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcAdmins", new ClarcAdmins());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminAdminsEdit";
   }
}

