package com.clarc.main;

public class ClarcAdmins {

   private long id;
   private int adminType;
   private String adminName;
   private String adminEmail;
   private String adminAccessAreas;
   private String adminLogin;
   private String adminPassword;
   private String adminAuthority;
   private int adminStatus;
   private String lastLoginTime;
   private String addedTime;

    public ClarcAdmins() {

      this.id = 0;
      this.adminType = 0;
      this.adminName = "";
      this.adminEmail = "";
      this.adminAccessAreas = "";
      this.adminLogin = "";
      this.adminPassword = "";
      this.adminAuthority = "";
      this.adminStatus = 0;
      this.lastLoginTime = "";
      this.addedTime = "";
   }

    public ClarcAdmins(long id, int adminType, String adminName, String adminEmail, String adminAccessAreas, String adminLogin, String adminPassword, String adminAuthority, int adminStatus, String lastLoginTime, String addedTime) {

      this.id = id;
      this.adminType = adminType;
      this.adminName = adminName;
      this.adminEmail = adminEmail;
      this.adminAccessAreas = adminAccessAreas;
      this.adminLogin = adminLogin;
      this.adminPassword = adminPassword;
      this.adminAuthority = adminAuthority;
      this.adminStatus = adminStatus;
      this.lastLoginTime = lastLoginTime;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public int getAdminType() {

      return adminType;
   }

   public void setAdminType(int adminType) {

      this.adminType = adminType;
   }

   public String getAdminName() {

      return adminName;
   }

   public void setAdminName(String adminName) {

      this.adminName = adminName;
   }

   public String getAdminEmail() {

      return adminEmail;
   }

   public void setAdminEmail(String adminEmail) {

      this.adminEmail = adminEmail;
   }

   public String getAdminAccessAreas() {

      return adminAccessAreas;
   }

   public void setAdminAccessAreas(String adminAccessAreas) {

      this.adminAccessAreas = adminAccessAreas;
   }

   public String getAdminLogin() {

      return adminLogin;
   }

   public void setAdminLogin(String adminLogin) {

      this.adminLogin = adminLogin;
   }

   public String getAdminPassword() {

      return adminPassword;
   }

   public void setAdminPassword(String adminPassword) {

      this.adminPassword = adminPassword;
   }

   public String getAdminAuthority() {

      return adminAuthority;
   }

   public void setAdminAuthority(String adminAuthority) {

      this.adminAuthority = adminAuthority;
   }

   public int getAdminStatus() {

      return adminStatus;
   }

   public void setAdminStatus(int adminStatus) {

      this.adminStatus = adminStatus;
   }

   public String getLastLoginTime() {

      return lastLoginTime;
   }

   public void setLastLoginTime(String lastLoginTime) {

      this.lastLoginTime = lastLoginTime;
   }

   public String getAddedTime() {

      return addedTime;
   }

   public void setAddedTime(String addedTime) {

      this.addedTime = addedTime;
   }

   public String toString() {

      return String.format("ClarcAdmins[id='%d', adminType='%d', adminName='%s', adminEmail='%s', adminAccessAreas='%s', adminLogin='%s', adminPassword='%s', adminAuthority='%s', adminStatus='%d', lastLoginTime='%s', addedTime='%s']",
         id, adminType, adminName, adminEmail, adminAccessAreas, adminLogin, adminPassword, adminAuthority, adminStatus, lastLoginTime, addedTime);
   }
}

