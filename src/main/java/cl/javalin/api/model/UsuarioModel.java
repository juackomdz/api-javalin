package cl.javalin.api.model;

public class UsuarioModel {

    private Integer id;
    private String username;
    private String email;
    
    public UsuarioModel() {
    }

    public UsuarioModel(Integer id, String username, String email) {
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}
