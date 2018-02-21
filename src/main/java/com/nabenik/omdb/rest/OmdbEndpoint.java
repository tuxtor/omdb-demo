package com.nabenik.omdb.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nabenik.omdb.controller.OmdbDao;
import com.nabenik.omdb.dto.OmdbDTO;

@RequestScoped
@Path("/omdb")
@Produces("application/json")
@Consumes("application/json")
public class OmdbEndpoint {
	
	@Inject
	OmdbDao omdbService;

	@GET
	@Path("/{id:[a-z]*[0-9][0-9]*}")
	public Response findById(@PathParam("id") final String imdbId) {
		OmdbDTO omdbdto = omdbService.getMovieInfo(imdbId);
		if (omdbdto == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(omdbdto).build(); 
	}

}