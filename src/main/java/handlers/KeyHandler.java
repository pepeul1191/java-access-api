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

public class KeyHandler{
  public static Route activationKeyValidate = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {

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

    }catch (Exception e) {
      String[] error = {"It was not possible to update the activation key by user_id", e.toString()};
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

    }catch (Exception e) {
      String[] error = {"It was not possible to update the reset key by user_id.", e.toString()};
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