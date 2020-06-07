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
import com.clarc.main.ClarcSources;

@Controller
@RequestMapping("/adminSourcesDelete")
public class AdminSourcesDeleteController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcSources> getRecord(String id)
   {
      List<ClarcSources> r = jdbc.query("SELECT * FROM clarc_sources WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcSources>() {
		     @Override
		     public ClarcSources mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcSources(rs.getLong("id"),
                   rs.getString("sourceName"),
                   rs.getString("sourceUrl"),
                   rs.getInt("sourceType"),
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

	  List<ClarcSources> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminSources");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_sources WHERE id=?", recordId);
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

