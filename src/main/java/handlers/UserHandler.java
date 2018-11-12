package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import configs.Database;
import models.User;
import models.UserKey;
import models.UserState;
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
      if (s == null){
        rpta = "repeated";
      }else{
        //No es repetido -> OK
				//2. Crear usuario
        User n = new User();
        n.set("user", user);
        n.set("pass", pass);
        n.set("email", email);
        n.set("user_state_id", 1);
        n.saveIt();
        userId = (int) n.get("id"); 
        //3. Crear asociación usuario sistema
        UserState n2 = new UserState();
        n2.set("user_id", userId);
        n2.set("system_id", systemId);
        n2.saveIt();
        //4. Crear key de activación y asociar
        UserKey n3 = new UserKey();
        n3.set("user_id", userId);
        n3.set("activation", org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(40));
        n3.saveIt();
      }
    }catch (Exception e) {
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
      User s = User.findFirst("user = ?", user);
      if (s == null){
        rpta = "0";
      }else{
        rpta = s.get("id") + "";
      }
    }catch (Exception e) {
      String[] error = {"An error occurred while getting the user", e.toString()};
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
}