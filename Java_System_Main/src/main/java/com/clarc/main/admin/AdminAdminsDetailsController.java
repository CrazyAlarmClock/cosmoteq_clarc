package com.clarc.main.admin;

import org.springframework.http.MediaType;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONObject;
import com.clarc.main.ClarcAdmins;

@Controller
@RequestMapping(value = "/adminAdminsDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminAdminsDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcAdmins> results = jdbc.query("SELECT * FROM clarc_admins WHERE id=?",
         new Object[] { recordId },
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
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>Тип:</td><td>"+r.getAdminType()+"</td></tr>";
         strOutput += "<tr><td>Имя:</td><td>"+r.getAdminName()+"</td></tr>";
         strOutput += "<tr><td>E-mail:</td><td>"+r.getAdminEmail()+"</td></tr>";
         strOutput += "<tr><td>Доступ:</td><td>"+r.getAdminAccessAreas()+"</td></tr>";
         strOutput += "<tr><td>Логин:</td><td>"+r.getAdminLogin()+"</td></tr>";
         strOutput += "<tr><td>Пароль:</td><td>"+r.getAdminPassword()+"</td></tr>";
         strOutput += "<tr><td>Роль авторизации:</td><td>"+r.getAdminAuthority()+"</td></tr>";
         strOutput += "<tr><td>Статус:</td><td>"+r.getAdminStatus()+"</td></tr>";
         strOutput += "<tr><td>Последний вход:</td><td>"+r.getLastLoginTime()+"</td></tr>";
         strOutput += "<tr><td>Дата создания:</td><td>"+r.getAddedTime()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminAdminsEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminAdminsDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

