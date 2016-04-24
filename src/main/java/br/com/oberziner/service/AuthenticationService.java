package br.com.oberziner.service;

import java.security.GeneralSecurityException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.oberziner.security.Authenticator;
import br.com.oberziner.security.AuthHTTPHeaderNames;

@Stateless
@Path("auth")
public class AuthenticationService {

	@Inject
	Authenticator demoAuthenticator;

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpHeaders httpHeaders,
			@FormParam("username") String username,
			@FormParam("password") String password) {

		try {
			String authToken = demoAuthenticator.login(username, password);

			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add("auth_token", authToken);
			JsonObject jsonObj = jsonObjBuilder.build();

			return getNoCacheResponseBuilder(Response.Status.OK).entity(
					jsonObj.toString()).build();

		} catch (final LoginException ex) {
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add("message",
					"Problem matching service key, username and password");
			JsonObject jsonObj = jsonObjBuilder.build();

			return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED)
					.entity(jsonObj.toString()).build();
		}
	}

	@POST
	@Path("logout")
	public Response logout(@Context HttpHeaders httpHeaders) {
		try {
			String authToken = httpHeaders
					.getHeaderString(AuthHTTPHeaderNames.AUTH_TOKEN);

			demoAuthenticator.logout(authToken);

			return getNoCacheResponseBuilder(Response.Status.NO_CONTENT)
					.build();
		} catch (final GeneralSecurityException ex) {
			return getNoCacheResponseBuilder(
					Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private Response.ResponseBuilder getNoCacheResponseBuilder(
			Response.Status status) {
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		cc.setMaxAge(-1);
		cc.setMustRevalidate(true);

		return Response.status(status).cacheControl(cc);
	}
}