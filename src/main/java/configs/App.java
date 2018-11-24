package configs;

import static spark.Spark.exception;
import static spark.Spark.staticFiles;
import static spark.Spark.port;
import static spark.Spark.options;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;
import configs.FilterHandler;
import handlers.UserHandler;
import handlers.UserStateHandler;
import handlers.KeyHandler;
import handlers.CorrelationHandler;

public class App {
  public static void main(String args[]){
    exception(Exception.class, (e, req, res) -> e.printStackTrace());
		staticFiles.location("/public");
		staticFiles.header("Access-Control-Allow-Origin", "*");
		staticFiles.header("Access-Control-Request-Method",  "*");
		staticFiles.header("Access-Control-Allow-Headers",  "*");
		//staticFiles.expireTime(600);
		//puerto
		port(4100);
		//CORS
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			return "OK";
		});
		//filters
		before("*", FilterHandler.setHeaders);
		before("*", FilterHandler.ambinteLogs);
		//ruta de test/conexion
		get("/test/conexion", (request, response) -> {
			return "Conxión OK";
		});
		//ruta de servicios REST
		//-user_state
		get("/user_state/list", UserStateHandler.list);
		get("/user_state/get/:id", UserStateHandler.get);
		//-user
		post("/user/create", UserHandler.create);
		get("/user/get/:id", UserHandler.get);
		get("/user/get_by_name_system_id", UserHandler.getByUserNameAndSystemId);
		get("/user/get_id_by_user", UserHandler.getIdByUser);
		post("/user/update_state", UserHandler.updateState);
		post("/user/update_pass", UserHandler.updatePass);
    post("/user/update_email", UserHandler.updateEmail);
		post("/user/delete/:id", UserHandler.delete);
		post("/user/system/validate", UserHandler.userPassSystemValidate);
		post("/user/update", UserHandler.update);
		//-key
		post("/key/activation/validate", KeyHandler.activationKeyValidate);
		post("/key/reset/validate", KeyHandler.resetKeyValidate);
		post("/key/reset_by_email", KeyHandler.resetByEmail);
		post("/key/activation/update_by_user_id", KeyHandler.activationUpdateByUserId);
		post("/key/reset/update_by_user_id", KeyHandler.resetUpdateByUserId);
    //-correlation
    post("/correlation/generate", CorrelationHandler.generate);
  }
}
