/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author tomaszkoltun
 */
@Provider
public class LogingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        System.out.println("Request Filter:");
        System.out.println("Headers: "+crc.getHeaders());
//        String s = crc.getHeaderString("authorization");
//        System.out.println(s);
        final String authorization = crc.getHeaderString("authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            System.out.println("credentials: "+values[0]+" "+values[1]);
            if(!values[0].equals("admin") || !values[1].equals("admin")){
                String msg = "bad login / pw";
                crc.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build());
            }
            
        }
    }

    @Override
    public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) throws IOException {
        System.out.println("Response Filter:");
        System.out.println("Headers: "+crc.getHeaders());
        
    }
    
}
