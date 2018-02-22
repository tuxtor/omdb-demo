package com.nabenik.omdb.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabenik.omdb.dto.OmdbDTO;

/**
 * Session Bean implementation class OmdbDao
 */
@RequestScoped
public class OmdbDao {
	
	@Inject
	@ConfigProperty(name = "omdbremote.url")
	String baseOmdbUrl;

	/**
	 * Sync petition to omdb
	 * @param imdbId
	 * @return OmdbDTO representation
	 */
	public OmdbDTO getMovieInfo(String imdbId) {
		Client client = ClientBuilder.newClient();
		
		System.out.println(baseOmdbUrl);
		
		String details =  client.target(baseOmdbUrl.concat("&i=").concat(imdbId))
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		//Wrong way of marshalling 
		ObjectMapper mapper = new ObjectMapper();
		OmdbDTO omdbDTO = new OmdbDTO();
		try {
			omdbDTO = mapper.readValue(details, OmdbDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return omdbDTO;
		
	}

}
