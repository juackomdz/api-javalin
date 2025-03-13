package cl.javalin.api.controller;

import java.util.Optional;

import cl.javalin.api.model.UsuarioModel;
import cl.javalin.api.service.ServicioUsuarios;
import io.javalin.http.Context;

public class UsuarioController {

    private static final ServicioUsuarios usuarioService = new ServicioUsuarios();


    public static void guardar(Context ctx){
        UsuarioModel user = ctx.bodyAsClass(UsuarioModel.class);

        usuarioService.guardar_usuario(user);
        ctx.json(user).status(201);
    }

    public static void listar(Context ctx){
        ctx.json(usuarioService.listar_usuarios());
    }

    public static void buscar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<UsuarioModel> opt = usuarioService.buscar_usuario(id);
        if (opt.isPresent()) {
            ctx.json(opt.get());
        } else {
            ctx.result("Usuario no encontrado").status(404);
        }
    }

    public static void actualizar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        UsuarioModel user = ctx.bodyAsClass(UsuarioModel.class);
        if (usuarioService.actualizar_usuario(id, user)) {
            ctx.json(user).status(201);
        } else {
            ctx.result("Error al actualizar Usuario").status(400);
        }
    }

    public static void eliminar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<UsuarioModel> opt = usuarioService.buscar_usuario(id);
        if (opt.isPresent()) {
            usuarioService.borrar_usuario(id);
            ctx.result("Usuario eliminado").status(200);
        } else {
            ctx.result("Usuario no encontrado");
        }
    }
}
