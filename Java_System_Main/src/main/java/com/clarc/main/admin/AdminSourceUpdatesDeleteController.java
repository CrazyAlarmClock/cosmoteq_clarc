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
import com.clarc.main.ClarcSourceUpdates;

@Controller
@RequestMapping("/adminSourceUpdatesDelete")
public class AdminSourceUpdatesDeleteController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcSourceUpdates> getRecord(String id)
   {
      List<ClarcSourceUpdates> r = jdbc.query("SELECT * FROM clarc_source_updates WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcSourceUpdates>() {
		     @Override
		     public ClarcSourceUpdates mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcSourceUpdates(rs.getLong("id"),
                   rs.getLong("sourceId"),
                   rs.getLong("updatedUserId"),
                   rs.getInt("status"),
                   rs.getString("updatedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   @PostMapping
   public ModelAndView showPage(@RequestParam("id") String recordId, Model model) {

	  List<ClarcSourceUpdates> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminSourceUpdates");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_source_updates WHERE id=?", recordId);
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

