package cl.javalin.api;

import cl.javalin.api.controller.CursoController;
import cl.javalin.api.controller.UsuarioController;
import cl.javalin.api.jwt.JWTMiddleware;
import cl.javalin.api.service.ServicioExt;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;


public class App {
    public static void main(String[] args) {
        
        /** 
        Javalin app = Javalin.create().start(7007);
        JWTMiddleware jwt = new JWTMiddleware();
        
        app.before(ctx -> {
            jwt.handle(ctx);
        });

        */
        //app.get("/usuarios", UsuarioController::listar);
        //app.post("/usuarios", UsuarioController::guardar);
        
        Javalin.create(config -> {
            
            config.router.apiBuilder(() -> {
                // Rutas de la API
                path("/usuarios", () -> {
                    before(new JWTMiddleware());

                    get(UsuarioController::listar);
                    
                    path("/{id}", () -> {
                        get(UsuarioController::buscar);
                        put(UsuarioController::actualizar);
                        delete(UsuarioController::eliminar);
                    });
                });
                path("/curso", () -> {
                    get(CursoController::listar);
                    post(CursoController::guardar);
                    path("/{id}", () -> {
                        get(CursoController::buscar);
                        delete(CursoController::eliminar);
                        put(CursoController::actualizar);
                    });
                });
                path("/test", () -> {
                    get("/header", ctx -> {
                        String header = ctx.header("header");
                        if (header.equals("a")) {
                            ctx.result("header 1");
                        } else {
                            ctx.result("header 2");
                        }
                    });
                    get("/external/{id}", ctx -> {
                        ServicioExt ext = new ServicioExt();
                        Integer id = Integer.parseInt(ctx.pathParam("id"));
                        String res = ext.getData("/todos/"+id);
                        ctx.json(res);
                    });
                });
                post("/login", UsuarioController::login);
                post("/registrar",UsuarioController::guardar);
            });
        }).start(7007);
    }
}
