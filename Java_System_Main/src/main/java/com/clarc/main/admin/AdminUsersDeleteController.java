package com.clarc.main.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import com.clarc.main.ClarcUsers;

@Controller
@RequestMapping("/adminUsersDelete")
public class AdminUsersDeleteController {

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
   @PostMapping
   public ModelAndView showPage(@RequestParam("id") String recordId, Model model) {

	  List<ClarcUsers> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminUsers");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_users WHERE id=?", recordId);
         if(n > 0)
         {
        	 mv.addObject("operationSuccess", "Операция произведена успешно!");
         }
         else
         {
            mv.addObject("operationError", "Ошибка удаления записи.");
         }
      }
      else
      {
    	  mv.addObject("operationError", "Запись не найдена.");
      }

      return mv;
   }
}

