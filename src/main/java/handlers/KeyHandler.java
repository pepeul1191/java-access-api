package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import configs.Database;
import models.UserKey;
import models.User;

public class KeyHandler{
  public static Route activationKeyValidate = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      String activationKey = request.queryParams("activation_key");
      db.open();
      UserKey s = UserKey.findFirst("user_id = ? AND activation = ?", userId, activationKey);
      if (s == null){
        rpta = "0";
      }else{
        rpta = "1";
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to validate the activation key.", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route resetKeyValidate = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      String resetKey = request.queryParams("reset_key");
      db.open();
      UserKey s = UserKey.findFirst("user_id = ? AND reset = ?", userId, resetKey);
      if (s == null){
        rpta = "0";
      }else{
        rpta = "1";
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to validate the reset key.", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route resetByEmail = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String email = request.queryParams("email");
      db.open();
      User s1 = User.findFirst("email = ?", email);
      if (s1 == null){
        rpta = "user_not_found";
        response.status(404);
      }else{
        UserKey s2 = UserKey.findFirst("user_id = ?", s1.get("id"));
        if(s2 == null){
          rpta = "user_key_not_found";
          response.status(404);
        }else{
          String key = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(40);
          s2.set("reset", key);
          s2.saveIt();
          JSONObject temp = new JSONObject();
          temp.put("user_id", s1.get("id"));
          temp.put("reset_key", key);
          rpta = temp.toString();
        }
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to reset update the reset user key", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route activationUpdateByUserId = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      db.open();
      UserKey s = UserKey.findFirst("user_id = ?", userId);
      if (s == null){
        response.status(404);
        rpta = "not_found";
      }else{
        String key = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(40);
        s.set("activation", key);
        s.saveIt();
        rpta = key;
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to update the activation user key", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route resetUpdateByUserId = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      db.open();
      UserKey s = UserKey.findFirst("user_id = ?", userId);
      if (s == null){
        response.status(404);
        rpta = "not_found";
      }else{
        String key = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(40);
        s.set("reset", key);
        s.saveIt();
        rpta = key;
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to update the reset user key", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route activationKeyGet = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String userId = request.params("user_id");
      db.open();
      UserKey s = UserKey.findFirst("user_id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        rpta = (String) s.get("activation");
      }
    }catch (Exception e) {
      String[] error = {"It was not possible to get the activation user key", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };
}
