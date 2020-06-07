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
import com.clarc.main.ClarcUsers;

@Controller
@RequestMapping("/adminUsers")
public class AdminUsersController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      String strOutput = "";

      List<ClarcUsers> results = jdbc.query("SELECT * FROM clarc_users ORDER BY id DESC",
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
      for(ClarcUsers r : results) {
         strOutput += "<tr>";
		 strOutput += "<th scope='row'>"+r.getId()+"</th>";
		 strOutput += "<td>"+r.getUserEmail()+"</td>";
		 strOutput += "<td>"+r.getUserLogin()+"</td>";
		 strOutput += "<td>"+r.getUserPhone()+"</td>";
		 strOutput += "<td>"+r.getStatus()+"</td>";
		 strOutput += "<td>"+r.getAddedTime()+"</td>";
         strOutput += "<td style='text-align:right;'><nobr><a href='#' onClick='showDetails("+r.getId()+");return false;' role='button' class='btn btn-primary btn-sm'><i class='fas fa-eye'></i></a><a href='/adminUsersEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminUsersDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td>";
         strOutput += "</tr>";
      }

      if(strOutput.length() == 0) {

         strOutput += "<tr>";
         strOutput += "<td colspan='7' style='text-align:center;'><b>Записи не найдены.</b></td>";
         strOutput += "</tr>";
      }

      model.addAttribute("output", strOutput);

      return "admin/adminUsers";
   }
}

