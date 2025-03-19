package cl.javalin.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cl.javalin.api.model.UsuarioModel;

public class ServicioUsuarios {


    List<UsuarioModel> usuarios = new ArrayList<>();
    Integer lastId = 1;

    public List<UsuarioModel> listar_usuarios(){
        // Implementar la logica para obtener los usuarios

        //UsuarioModel usuarios = new UsuarioModel(1,"juan", "juan@juan.cl");

        return usuarios;
    }

    public UsuarioModel guardar_usuario(UsuarioModel user){
        // Implementar la logica para guardar un usuario
        UsuarioModel usuario = new UsuarioModel();
        usuario.setUsername(user.getUsername());
        usuario.setEmail(user.getEmail());
        usuario.setPassword(user.getPassword());
        usuario.setId(lastId++);

        usuarios.add(usuario);
        
        return usuario;
    }

    public Optional<UsuarioModel> buscar_usuario(Integer id){
        // Implementar la logica para buscar un usuario
        return usuarios.stream()
            .filter(user -> user.getId().equals(id)).findFirst();
    }

    public void borrar_usuario(Integer id){
        // Implementar la logica para borrar un usuario
        usuarios.removeIf(user -> user.getId().equals(id));
    }

    public boolean actualizar_usuario(Integer id, UsuarioModel usr){
        // Implementar la logica para actualizar un usuario
       /* 
        return usuarios.stream()
            .filter(user -> user.getId().equals(id)).findFirst().map(user -> {
                user.setUsername(usr.getUsername());
                user.setEmail(usr.getEmail());
                return true;
            }).orElse(false);
            */
            Optional<UsuarioModel> opt = usuarios.stream()
            .filter(user -> user.getId().equals(id)).findFirst();
            if(opt.isPresent()){
                UsuarioModel user = opt.get();
                user.setUsername(usr.getUsername());
                user.setEmail(usr.getEmail());
                return true;
                }
            return false;
    }


    public boolean login(UsuarioModel usuario){

        Optional<UsuarioModel> opt = usuarios.stream().filter(u -> 
            u.getUsername().equals(usuario.getUsername()) &&
            u.getPassword().equals(usuario.getPassword())
        ).findFirst();

        if (opt.isPresent()) {
            return true;
        }

        return false;
    }
}
