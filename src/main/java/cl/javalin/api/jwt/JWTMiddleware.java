package cl.javalin.api.jwt;

import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public class JWTMiddleware implements Handler{

    @Override
    public void handle(Context ctx) throws Exception{

        JWTUtil jwt = new JWTUtil();

        String auth = ctx.header("Authorization");

        System.out.println("auth= "+auth);
        if(auth == null || !auth.startsWith("Bearer ")){
            System.out.println("entra al if");
            ctx.res().sendError(400);

            System.out.println("resp= "+Map.of("error","Token invlido"));
            return;
        }
        


        if (auth != null) {
            String token = auth.replace("Bearer ", "");
            System.out.println("token= "+token);

            try {
                Claims claims = jwt.parseClaims(token);
                ctx.attribute("user",claims.get("username"));
    
    
            } catch (JwtException e) {
                ctx.status(403).result(e.getMessage());
            }
        }
        
    }
}
