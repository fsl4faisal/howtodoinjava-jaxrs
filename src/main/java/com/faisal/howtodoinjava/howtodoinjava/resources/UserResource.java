package com.faisal.howtodoinjava.howtodoinjava.resources;

import com.faisal.howtodoinjava.howtodoinjava.model.User;
import io.micrometer.common.util.StringUtils;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/*
TODO learn about the annotations here
 XmlAccessorType
 XmlRootElement

 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "users")
@Path("/users")
public class UserResource {
    public static Map<Integer, User> DB = new HashMap<>();

    public static final int ID1 = 1;

    public static final int ID2 = 2;

    static {
        DB.put(ID1, new User(ID1, "/user-management/1", "Md", "Faisal"));
        DB.put(ID2, new User(ID2, "/user-management/1", "Md", "Faisal"));
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Users getAllUsers() {
        return new Users(new ArrayList<>(DB.values()));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createUser(User user) throws URISyntaxException {
        if (StringUtils.isEmpty(user.getFirstName()) || StringUtils.isEmpty(user.getLastName())) {
            return Response
                    .status(400)
                    .entity("Please provide all mandatory inputs")
                    .build();
        }
        user.setId(DB.values().size() + 1);
        user.setUri("/user-management/" + user.getId());
        DB.put(user.getId(), user);
        return Response
                .status(201)
                .contentLocation(new URI(user.getUri()))
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        return DB.entrySet().stream()
                .findAny()
                .map(entry -> {
                    try {
                        return Response.status(200)
                                .entity(entry.getValue())
                                .contentLocation(new URI("/user-management" + id))
                                .build();
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(Response.status(404).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User updatedUser) {
        /*
        TODO I don't think this is better than just var user=DB.get(id)
        because DB.get(id) is constant time and here we are iterating the
        entrySet through the stream
         */
        return DB.entrySet().stream()
                .findAny().map(entry -> {
                    var user = entry.getValue();
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    DB.put(user.getId(), user);
                    return Response
                            .status(200)
                            .entity(user)
                            .build();
                })
                .orElse(Response
                        .status(404)
                        .build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        var user = DB.get(id);
        int status;
        if (user != null) {
            DB.remove(id);
            status = 200;
        } else {
            status = 404;
        }
        return Response.status(status).build();
    }
}

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "users")
record Users(@XmlElement(name = "users") List<User> users) {
}
