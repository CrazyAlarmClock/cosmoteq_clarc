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
import com.clarc.main.ClarcAdmins;

@Controller
@RequestMapping("/adminAdminsDelete")
public class AdminAdminsDeleteController {

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
   @PostMapping
   public ModelAndView showPage(@RequestParam("id") String recordId, Model model) {

	  List<ClarcAdmins> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminAdmins");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_admins WHERE id=?", recordId);
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

