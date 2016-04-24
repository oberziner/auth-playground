package br.com.oberziner.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthRequestFilter implements ContainerRequestFilter {

	@Inject
	Authenticator authenticator;

	private final static Logger log = Logger.getLogger(AuthRequestFilter.class
			.getName());

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {

		String path = requestCtx.getUriInfo().getPath();
		log.info("Filtering request path: " + path);

		// IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for
		// this case before validating the headers (CORS stuff)
		if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
			requestCtx.abortWith(Response.status(Response.Status.OK).build());

			return;
		}

		// For any other methods besides login and user-add, the authToken must be verified
		if (!(path.startsWith("/auth/login") || 
			 ((path.equals("/users")) && requestCtx.getMethod().equals("POST")))) {
			String authToken = requestCtx
					.getHeaderString(AuthHTTPHeaderNames.AUTH_TOKEN);

			// if it isn't valid, just kick them out.
			if (!authenticator.isAuthTokenValid(authToken)) {
				requestCtx.abortWith(Response.status(
						Response.Status.UNAUTHORIZED).build());
			}
		}
	}
}