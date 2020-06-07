package com.clarc.main.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.clarc.main.ClarcInfopageAccessTotals;

@Controller
@RequestMapping("/adminInfopageAccessTotals")
public class AdminInfopageAccessTotalsController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      String strOutput = "";

      List<ClarcInfopageAccessTotals> results = jdbc.query("SELECT * FROM clarc_infopage_access_totals ORDER BY id DESC",
         new RowMapper<ClarcInfopageAccessTotals>() {
            @Override
            public ClarcInfopageAccessTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
               return new ClarcInfopageAccessTotals(rs.getLong("id"),
                  rs.getLong("pageId"),
                  rs.getInt("pageViews"),
                  rs.getString("accessDate"));
            }
         }
      );
      for(ClarcInfopageAccessTotals r : results) {
         strOutput += "<tr>";
		 strOutput += "<th scope='row'>"+r.getId()+"</th>";
		 strOutput += "<td>"+r.getPageId()+"</td>";
		 strOutput += "<td>"+r.getPageViews()+"</td>";
		 strOutput += "<td>"+r.getAccessDate()+"</td>";
         strOutput += "<td style='text-align:right;'><nobr><a href='#' onClick='showDetails("+r.getId()+");return false;' role='button' class='btn btn-primary btn-sm'><i class='fas fa-eye'></i></a><a href='/adminInfopageaccesstotalsEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminInfopageaccesstotalsDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td>";
         strOutput += "</tr>";
      }

      if(strOutput.length() == 0) {

         strOutput += "<tr>";
         strOutput += "<td colspan='5' style='text-align:center;'><b>Записи не найдены.</b></td>";
         strOutput += "</tr>";
      }

      model.addAttribute("output", strOutput);

      return "admin/adminInfopageAccessTotals";
   }
}

