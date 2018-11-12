package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import configs.Database;
import models.VWUserStateSystem;

public class UserHandler{
  public static Route userSystemValidate = (Request request, Response response) -> {
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
}