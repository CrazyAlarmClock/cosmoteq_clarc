package com.clarc.main;

public class ClarcInfopageAccess {

   private long id;
   private long userId;
   private long pageId;
   private int status;
   private String addedTime;

    public ClarcInfopageAccess() {

      this.id = 0;
      this.userId = 0;
      this.pageId = 0;
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcInfopageAccess(long id, long userId, long pageId, int status, String addedTime) {

      this.id = id;
      this.userId = userId;
      this.pageId = pageId;
      this.status = status;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public long getUserId() {

      return userId;
   }

   public void setUserId(long userId) {

      this.userId = userId;
   }

   public long getPageId() {

      return pageId;
   }

   public void setPageId(long pageId) {

      this.pageId = pageId;
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

      return String.format("ClarcInfopageAccess[id='%d', userId='%d', pageId='%d', status='%d', addedTime='%s']",
         id, userId, pageId, status, addedTime);
   }
}

