package com.clarc.main;

public class ClarcInfopageCategories {

   private long id;
   private String pageCategoryName;
   private int status;
   private String addedTime;

    public ClarcInfopageCategories() {

      this.id = 0;
      this.pageCategoryName = "";
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcInfopageCategories(long id, String pageCategoryName, int status, String addedTime) {

      this.id = id;
      this.pageCategoryName = pageCategoryName;
      this.status = status;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public String getPageCategoryName() {

      return pageCategoryName;
   }

   public void setPageCategoryName(String pageCategoryName) {

      this.pageCategoryName = pageCategoryName;
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

      return String.format("ClarcInfopageCategories[id='%d', pageCategoryName='%s', status='%d', addedTime='%s']",
         id, pageCategoryName, status, addedTime);
   }
}

