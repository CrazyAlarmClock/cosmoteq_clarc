package com.clarc.main;

public class ClarcInfopages {

   private long id;
   private String pageName;
   private long pageCategory;
   private String pageDescription;
   private long pageAccessRights;
   private int status;
   private String addedTime;

    public ClarcInfopages() {

      this.id = 0;
      this.pageName = "";
      this.pageCategory = 0;
      this.pageDescription = "";
      this.pageAccessRights = 0;
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcInfopages(long id, String pageName, long pageCategory, String pageDescription, long pageAccessRights, int status, String addedTime) {

      this.id = id;
      this.pageName = pageName;
      this.pageCategory = pageCategory;
      this.pageDescription = pageDescription;
      this.pageAccessRights = pageAccessRights;
      this.status = status;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public String getPageName() {

      return pageName;
   }

   public void setPageName(String pageName) {

      this.pageName = pageName;
   }

   public long getPageCategory() {

      return pageCategory;
   }

   public void setPageCategory(long pageCategory) {

      this.pageCategory = pageCategory;
   }

   public String getPageDescription() {

      return pageDescription;
   }

   public void setPageDescription(String pageDescription) {

      this.pageDescription = pageDescription;
   }

   public long getPageAccessRights() {

      return pageAccessRights;
   }

   public void setPageAccessRights(long pageAccessRights) {

      this.pageAccessRights = pageAccessRights;
   }

   public int getStatus() {

      return status;
   }

   public void setStatus(int status) {

      this.status = status;
   }

   public String getAddedTime() {

      return addedTime;
   }

   public void setAddedTime(String addedTime) {

      this.addedTime = addedTime;
   }

   public String toString() {

      return String.format("ClarcInfopages[id='%d', pageName='%s', pageCategory='%d', pageDescription='%s', pageAccessRights='%d', status='%d', addedTime='%s']",
         id, pageName, pageCategory, pageDescription, pageAccessRights, status, addedTime);
   }
}

