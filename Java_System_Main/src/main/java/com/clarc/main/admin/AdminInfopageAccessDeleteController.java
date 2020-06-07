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
import com.clarc.main.ClarcInfopageAccess;

@Controller
@RequestMapping("/adminInfopageAccessDelete")
public class AdminInfopageAccessDeleteController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopageAccess> getRecord(String id)
   {
      List<ClarcInfopageAccess> r = jdbc.query("SELECT * FROM clarc_infopage_access WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopageAccess>() {
		     @Override
		     public ClarcInfopageAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopageAccess(rs.getLong("id"),
                   rs.getLong("userId"),
                   rs.getLong("pageId"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   @PostMapping
   public ModelAndView showPage(@RequestParam("id") String recordId, Model model) {

	  List<ClarcInfopageAccess> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminInfopageAccess");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_infopage_access WHERE id=?", recordId);
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

