package cl.javalin.api;

import cl.javalin.api.controller.CursoController;
import cl.javalin.api.controller.UsuarioController;


import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;


public class App {
    public static void main(String[] args) {
        Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                // Rutas de la API
                path("/usuarios", () -> {
                    get(UsuarioController::listar);
                    post(UsuarioController::guardar);
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
                path("/testheader", () -> {
                    get("/", ctx -> {
                        String header = ctx.header("header");
                        if (header.equals("a")) {
                            ctx.result("header 1");
                        } else {
                            ctx.result("header 2");
                        }
                    });
                });
            });
        }).start(7007);

    }
}
