package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.UserState;

public class UserStateHandler{
  public static Route list = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<UserState> rptaList = UserState.findAll();
      for (UserState el : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", el.get("id"));
        obj.put("name", el.get("name"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"It was not possible to list the user states.", e.toString()};
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
      int id   = Integer.parseInt(request.params(":id"));
      db.open();
      UserState s = UserState.findFirst("id = ?", id);
      rpta = s.getString("name");
    }catch (Exception e) {
      String[] error = {"It was not possible to get the user state.", e.toString()};
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