package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import configs.Database;
import models.Correlation;

public class CorrelationHandler{
  public static Route generate = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      db.open();
      Correlation n = new Correlation();
      n.saveIt();
      rpta = n.get("id") + "";
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
}
