package br.com.oberziner.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
import br.com.oberziner.security.Encrypter;

@Path("users")
@Stateless
public class AuthUserService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void putUser(AuthUser user) throws NoSuchAlgorithmException {
		user.setPassword(Encrypter.encryptMD5(user.getPassword()));
		dao.save(user);
	}

	@Inject
	AuthUserDAO dao;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthUser getUserInformation(@PathParam("id") Integer id) {
		return dao.getUser(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AuthUser> getAllUsers() {
		return dao.getAllUsers();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(AuthUser user) throws NoSuchAlgorithmException {
		user.setPassword(Encrypter.encryptMD5(user.getPassword()));
		dao.update(user);
	}

	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") Integer id) {
		dao.removeWithId(id);
	}
}
