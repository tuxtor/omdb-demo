package com.nabenik.omdb.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabenik.omdb.dto.OmdbDTO;

/**
 * Session Bean implementation class OmdbDao
 */
@Named
public class OmdbDao {
	
	@Inject
	@ConfigProperty(name = "omdbremote.url")
	String baseOmdbUrl;

	/**
	 * Sync petition to omdb
	 * @param imdbId
	 * @return OmdbDTO representation
	 */
	public OmdbDTO getMovieInfo(String imdbId) throws Exception {
		Client client = ClientBuilder.newClient();
		
		System.out.println("Request url " + baseOmdbUrl.concat("&i=").concat(imdbId));
		
		String details =  client.target(baseOmdbUrl.concat("&i=").concat(imdbId))
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		//Wrong way of marshalling 
		ObjectMapper mapper = new ObjectMapper();
		OmdbDTO omdbDTO = new OmdbDTO();
		omdbDTO = mapper.readValue(details, OmdbDTO.class);
		return omdbDTO;
		
	}

}
