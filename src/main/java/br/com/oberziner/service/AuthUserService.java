package br.com.oberziner.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.oberziner.dao.AuthUserDAO;
import br.com.oberziner.entity.AuthUser;

@Path("user")
public class AuthUserService {

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void putUser(AuthUser user) {
		AuthUserDAO.save(user);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthUser getUserInformation(@PathParam("id") Integer id) {
		return AuthUserDAO.getUser(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AuthUser> getAllUsers() {
		return AuthUserDAO.getAllUsers();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(AuthUser user) {
		AuthUserDAO.update(user);
	}

	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") Integer id) {
		AuthUserDAO.remove(AuthUserDAO.getUser(id));
	}
}
