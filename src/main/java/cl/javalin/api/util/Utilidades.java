package cl.javalin.api.util;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.HttpStatus;

public class Utilidades {

    public Object generateResponse(String mensaje, HttpStatus status){

        Map<String, Object> map = new HashMap<>();
        try {
            
            map.put("status", status);
            map.put("mensaje", mensaje);
        } catch (Exception e) {
            
            map.clear();
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            map.put("mensaje", e.getMessage());
        }

        return map;
        
    }
}
