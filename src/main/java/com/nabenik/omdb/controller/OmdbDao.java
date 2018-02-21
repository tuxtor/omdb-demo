package com.nabenik.omdb.controller;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabenik.omdb.dto.OmdbDTO;

/**
 * Session Bean implementation class OmdbDao
 */
@Stateless
public class OmdbDao {
	
	
	final String BASE_OMDB_URL = "http://www.omdbapi.com/?apikey=a3804773&i=";

	/**
	 * Sync petition to omdb
	 * @param imdbId
	 * @return OmdbDTO representation
	 */
	public OmdbDTO getMovieInfo(String imdbId) {
		Client client = ClientBuilder.newClient();
		
		
		String details =  client.target(BASE_OMDB_URL.concat(imdbId))
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
