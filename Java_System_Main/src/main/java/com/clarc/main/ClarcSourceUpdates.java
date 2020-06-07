package com.clarc.main;

public class ClarcSourceUpdates {

   private long id;
   private long sourceId;
   private long updatedUserId;
   private int status;
   private String updatedTime;

    public ClarcSourceUpdates() {

      this.id = 0;
      this.sourceId = 0;
      this.updatedUserId = 0;
      this.status = 0;
      this.updatedTime = "";
   }

    public ClarcSourceUpdates(long id, long sourceId, long updatedUserId, int status, String updatedTime) {

      this.id = id;
      this.sourceId = sourceId;
      this.updatedUserId = updatedUserId;
      this.status = status;
      this.updatedTime = updatedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public long getSourceId() {

      return sourceId;
   }

   public void setSourceId(long sourceId) {

      this.sourceId = sourceId;
   }

   public long getUpdatedUserId() {

      return updatedUserId;
   }

   public void setUpdatedUserId(long updatedUserId) {

      this.updatedUserId = updatedUserId;
   }

   public int getStatus() {

      return status;
   }

   public void setStatus(int status) {

      this.status = status;
   }

   public String getUpdatedTime() {

      return updatedTime;
   }

   public void setUpdatedTime(String updatedTime) {

      this.updatedTime = updatedTime;
   }

   public String toString() {

      return String.format("ClarcSourceUpdates[id='%d', sourceId='%d', updatedUserId='%d', status='%d', updatedTime='%s']",
         id, sourceId, updatedUserId, status, updatedTime);
   }
}

