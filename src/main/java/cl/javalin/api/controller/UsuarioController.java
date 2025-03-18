package cl.javalin.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import cl.javalin.api.jwt.JWTMiddleware;
import cl.javalin.api.jwt.JWTUtil;
import cl.javalin.api.model.UsuarioModel;
import cl.javalin.api.service.ServicioUsuarios;
import cl.javalin.api.util.Utilidades;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class UsuarioController {

    private static final ServicioUsuarios usuarioService = new ServicioUsuarios();
    private static final Utilidades util = new Utilidades();
    private static JWTUtil jwt = new JWTUtil();


    public static void guardar(Context ctx){
        UsuarioModel user = ctx.bodyAsClass(UsuarioModel.class);

        usuarioService.guardar_usuario(user);
        ctx.json(util.generateResponse("Creado con exito", HttpStatus.CREATED)).status(201);
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
            ctx.json(util.generateResponse("Usuario no encontrado", HttpStatus.NOT_FOUND)).status(404);
        }
    }

    public static void actualizar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        UsuarioModel user = ctx.bodyAsClass(UsuarioModel.class);
        if (usuarioService.actualizar_usuario(id, user)) {
            ctx.json(util.generateResponse("Usuario actualizado con exito", HttpStatus.ACCEPTED)).status(HttpStatus.ACCEPTED);
        } else {
            ctx.json(util.generateResponse("Error al actualizar", HttpStatus.BAD_GATEWAY)).status(400);
        }
    }

    public static void eliminar(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<UsuarioModel> opt = usuarioService.buscar_usuario(id);
        if (opt.isPresent()) {
            usuarioService.borrar_usuario(id);
            //ctx.result("Usuario eliminado").status(200);
            ctx.json(util.generateResponse("Eliminado con exito", HttpStatus.OK)).status(200);
        } else {
            ctx.json(util.generateResponse("Usuario no encontrado", HttpStatus.NOT_FOUND)).status(404);
        }
    }




    ///--------------------LOGIN------------------------------------
    public static void login(Context ctx){
        UsuarioModel usuario = ctx.bodyAsClass(UsuarioModel.class);

        System.out.println("user= "+usuarioService.login(usuario));
        if (usuarioService.login(usuario)) {
            String token = jwt.generateToken(usuario);
            Map<String, String> res = new HashMap<>();
            res.put("usuario", usuario.getUsername());
            res.put("token", token);

            ctx.json(res);
        } else {
            ctx.result("Credenciales incorrectas");
        }
    }
}
