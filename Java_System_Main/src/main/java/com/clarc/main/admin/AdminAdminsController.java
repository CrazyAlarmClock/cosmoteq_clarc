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
import com.clarc.main.ClarcAdmins;

@Controller
@RequestMapping("/adminAdmins")
public class AdminAdminsController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      String strOutput = "";

      List<ClarcAdmins> results = jdbc.query("SELECT * FROM clarc_admins ORDER BY id DESC",
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
      for(ClarcAdmins r : results) {
         strOutput += "<tr>";
		 strOutput += "<th scope='row'>"+r.getId()+"</th>";
		 strOutput += "<td>"+r.getAdminType()+"</td>";
		 strOutput += "<td>"+r.getAdminName()+"</td>";
		 strOutput += "<td>"+r.getAdminEmail()+"</td>";
		 strOutput += "<td>"+r.getAdminAccessAreas()+"</td>";
		 strOutput += "<td>"+r.getAdminLogin()+"</td>";
		 strOutput += "<td>"+r.getAdminStatus()+"</td>";
		 strOutput += "<td>"+r.getAddedTime()+"</td>";
         strOutput += "<td style='text-align:right;'><nobr><a href='#' onClick='showDetails("+r.getId()+");return false;' role='button' class='btn btn-primary btn-sm'><i class='fas fa-eye'></i></a><a href='/adminAdminsEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminAdminsDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td>";
         strOutput += "</tr>";
      }

      if(strOutput.length() == 0) {

         strOutput += "<tr>";
         strOutput += "<td colspan='9' style='text-align:center;'><b>Записи не найдены.</b></td>";
         strOutput += "</tr>";
      }

      model.addAttribute("output", strOutput);

      return "admin/adminAdmins";
   }
}

