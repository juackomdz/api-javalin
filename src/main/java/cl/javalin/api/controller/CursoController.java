package cl.javalin.api.controller;


import cl.javalin.api.model.CursoModel;
import cl.javalin.api.service.ServicioCurso;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class CursoController {

    private static final ServicioCurso servicio = new ServicioCurso();


    public static void guardar(Context ctx){
        CursoModel curso = ctx.bodyAsClass(CursoModel.class);
        servicio.guardar(curso);
        ctx.json(curso).status(HttpStatus.CREATED);
    };

    public static void listar(Context ctx){
        ctx.json(servicio.listar_cursos());
    };

    public static void buscar(Context ctx){
        Integer id = Integer.parseInt(ctx.pathParam("id"));
        CursoModel opt = servicio.buscar_curso(id);
        System.out.println("curso="+opt);
        if (opt!=null) {
            ctx.json(opt);
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Curso no encontrado");
        }
        
    };

    public static void eliminar(Context ctx){
        Integer id = Integer.parseInt(ctx.pathParam("id"));
        CursoModel opt = servicio.buscar_curso(id);
        if (opt!=null) {
            servicio.eliminar(id);
            ctx.result("eliminado con exito");
        } else {
            ctx.result("Curso no encontrado");
        }
        //System.out.println("retorno="+servicio.eliminar(id));
       
        //ctx.result("No se encontro curso");
        
    }

    public static void actualizar(Context ctx){
        Integer id = Integer.parseInt(ctx.pathParam("id"));
        CursoModel cursoUp = ctx.bodyAsClass(CursoModel.class);
        CursoModel opt = servicio.buscar_curso(id);
        if (opt!=null) {
            servicio.actualizar(id,cursoUp);
            ctx.json(cursoUp);
        } else {
            ctx.result("Error al actualizar");
        }
    }
}
