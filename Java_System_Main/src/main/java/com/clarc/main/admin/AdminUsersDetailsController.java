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
import com.clarc.main.ClarcUsers;

@Controller
@RequestMapping(value = "/adminUsersDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUsersDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcUsers> results = jdbc.query("SELECT * FROM clarc_users WHERE id=?",
         new Object[] { recordId },
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
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>Имя:</td><td>"+r.getUserFirstName()+"</td></tr>";
         strOutput += "<tr><td>Фамилия:</td><td>"+r.getUserMiddleName()+"</td></tr>";
         strOutput += "<tr><td>Отчество:</td><td>"+r.getUserLastName()+"</td></tr>";
         strOutput += "<tr><td>Email:</td><td>"+r.getUserEmail()+"</td></tr>";
         strOutput += "<tr><td>Логин:</td><td>"+r.getUserLogin()+"</td></tr>";
         strOutput += "<tr><td>Пароль:</td><td>"+r.getUserPassword()+"</td></tr>";
         strOutput += "<tr><td>Пароль повторно:</td><td>"+r.getUserPasswordMatch()+"</td></tr>";
         strOutput += "<tr><td>ID компании:</td><td>"+r.getUserCompanyId()+"</td></tr>";
         strOutput += "<tr><td>Компания:</td><td>"+r.getUserCompanyName()+"</td></tr>";
         strOutput += "<tr><td>Должность:</td><td>"+r.getUserCompanyPosition()+"</td></tr>";
         strOutput += "<tr><td>Адрес:</td><td>"+r.getUserAddress()+"</td></tr>";
         strOutput += "<tr><td>Телефон:</td><td>"+r.getUserPhone()+"</td></tr>";
         strOutput += "<tr><td>Город:</td><td>"+r.getUserCity()+"</td></tr>";
         strOutput += "<tr><td>Индекс:</td><td>"+r.getUserZip()+"</td></tr>";
         strOutput += "<tr><td>Регион:</td><td>"+r.getUserState()+"</td></tr>";
         strOutput += "<tr><td>Страна:</td><td>"+r.getUserCountry()+"</td></tr>";
         strOutput += "<tr><td>Обо мне:</td><td>"+r.getUserAboutMe()+"</td></tr>";
         strOutput += "<tr><td>Фото:</td><td>"+r.getProfilePhotoUrl()+"</td></tr>";
         strOutput += "<tr><td>Тип пользователя:</td><td>"+r.getUserType()+"</td></tr>";
         strOutput += "<tr><td>Авторизация:</td><td>"+r.getUserAuthority()+"</td></tr>";
         strOutput += "<tr><td>Тарифный план:</td><td>"+r.getUserMembership()+"</td></tr>";
         strOutput += "<tr><td>Пользователь активирован:</td><td>"+r.getUserActivated()+"</td></tr>";
         strOutput += "<tr><td>Код активации:</td><td>"+r.getActivationCode()+"</td></tr>";
         strOutput += "<tr><td>Последний логин:</td><td>"+r.getLastLoginTime()+"</td></tr>";
         strOutput += "<tr><td>Предпоследний логин:</td><td>"+r.getPrevLoginTime()+"</td></tr>";
         strOutput += "<tr><td>FireBase токен:</td><td>"+r.getFireBaseToken()+"</td></tr>";
         strOutput += "<tr><td>Статус:</td><td>"+r.getStatus()+"</td></tr>";
         strOutput += "<tr><td>Согласие с условиями:</td><td>"+r.getAgree()+"</td></tr>";
         strOutput += "<tr><td>Дата создания:</td><td>"+r.getAddedTime()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminUsersEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminUsersDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

