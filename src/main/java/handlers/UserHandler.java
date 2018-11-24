package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import configs.Database;
import models.User;
import models.UserKey;
import models.UserSystem;
import models.VWUserStateSystem;

public class UserHandler{
  public static Route userPassSystemValidate = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String user = request.queryParams("user");
      String pass = request.queryParams("pass");
      String systemId = request.queryParams("system_id");
      db.open();
      VWUserStateSystem s = VWUserStateSystem.findFirst("user = ? AND pass = ? AND system_id = ?", user, pass, systemId);
      if (s == null){
        rpta = "inexistant";
      }else{
        rpta = s.getString("state");
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while validating the user", e.toString()};
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

  public static Route create = (Request request, Response response) -> {
    String rpta = "";
    int userId = 0;
    Database db = new Database();
    try {
      String user = request.queryParams("user");
      String pass = request.queryParams("pass");
      String systemId = request.queryParams("system_id");
      String email = request.queryParams("email");
      db.open();
      //1. Es repetido -> ERROR
      User s = User.findFirst("user = ? OR email = ?", user, email);
      if (s != null){
        response.status(501);
        rpta = "repeated";
      }else{
        //No es repetido -> OK
				//2. Crear usuario
        User n = new User();
        n.set("user", user);
        n.set("pass", pass);
        n.set("email", email);
        n.set("user_state_id", 6); // 6 = email_pending
        n.saveIt();
        userId = ((java.math.BigInteger)n.get("id")).intValue();
        //3. Crear asociación usuario sistema
        UserSystem n2 = new UserSystem();
        n2.set("user_id", userId);
        n2.set("system_id", systemId);
        n2.saveIt();
        //4. Crear key de activación y asociar
        String activationKey = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(40);
        UserKey n3 = new UserKey();
        n3.set("user_id", userId);
        n3.set("activation", activationKey);
        n3.saveIt();
        JSONObject rptaTemp = new JSONObject();
        rptaTemp.put("user_id", userId);
        rptaTemp.put("activation_key", activationKey);
        rpta = rptaTemp.toString();
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] error = {"An error occurred while creating the user", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        //TODO eliminar usuario_sistema y key en caso de error
  	    db.close();
  	  }
    }
    return rpta;
  };

  public static Route getIdByUser = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String user = request.queryParams("user");
      db.open();
      User s = User.findFirst("user = ?", user);
      if (s == null){
        rpta = "0";
      }else{
        rpta = s.get("id") + "";
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] error = {"An error occurred while getting the user", e.toString()};
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

  public static Route updateState = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      int userStateId = Integer.parseInt(request.queryParams("user_state_id"));
      System.out.println("1 +++++++++++++++++++++++++++++++++++++++++++++++++");
      System.out.println(userId);
      System.out.println(userStateId);
      System.out.println("2 +++++++++++++++++++++++++++++++++++++++++++++++++");
      db.open();
      User s = User.findFirst("id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        s.set("user_state_id", userStateId);
        s.saveIt();
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while updating the user state", e.toString()};
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

  public static Route updatePass = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      String pass = request.queryParams("pass");
      db.open();
      User s = User.findFirst("id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        s.set("pass", pass);
        s.saveIt();
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while updating the user password", e.toString()};
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

  public static Route updateEmail = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      String email = request.queryParams("email");
      db.open();
      //validate if other user have the new email
      User e = User.findFirst("id != ? AND email = ?", userId, email);
      if(e == null){ // if no other user have the email => update
        User s = User.findFirst("id = ?", userId);
        if (s == null){
          rpta = "not_found";
        }else{
          s.set("email", email);
          s.saveIt();
        }
      }else{
        String[] error = {"Email all ready used", e.toString()};
        JSONObject rptaTry = new JSONObject();
        rptaTry.put("tipo_mensaje", "error");
        rptaTry.put("mensaje", error);
        rpta = rptaTry.toString();
        response.status(501);
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while updating the user email", e.toString()};
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

  public static Route delete = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.params("id"));
      db.open();
      User s = User.findFirst("id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        s.delete();
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while deleting the user", e.toString()};
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

  public static Route get = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.params("id"));
      db.open();
      User s = User.findFirst("id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        JSONObject temp = new JSONObject();
        temp.put("user", s.get("user"));
        temp.put("email", s.get("email"));
        temp.put("state_id", s.get("user_state_id"));
        rpta = temp.toString();
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] error = {"An error occurred while getting the user", e.toString()};
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

  public static Route getByUserNameAndSystemId = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String userName = request.queryParams("user");
      int systemId = Integer.parseInt(request.queryParams("system_id"));
      db.open();
      VWUserStateSystem s = VWUserStateSystem.findFirst("user = ? AND system_id = ?", userName, systemId);
      if (s == null){
        rpta = "not_found";
      }else{
        JSONObject temp = new JSONObject();
        temp.put("id", s.get("user_id"));
        temp.put("user", s.get("user"));
        temp.put("email", s.get("email"));
        temp.put("state_id", s.get("user_state_id"));
        rpta = temp.toString();
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] error = {"An error occurred while getting the user", e.toString()};
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

  public static Route update = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      int userId = Integer.parseInt(request.queryParams("user_id"));
      int userStateId = Integer.parseInt(request.queryParams("user_state_id"));
      String email = request.queryParams("email");
      db.open();
      User s = User.findFirst("id = ?", userId);
      if (s == null){
        rpta = "not_found";
      }else{
        s.set("email", email);
        s.set("user_state_id", userStateId);
        s.saveIt();
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] error = {"An error occurred while updating the user", e.toString()};
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
