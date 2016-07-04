package com.cipherprelude.templatevertxwithoutdb.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cipherprelude.templatevertxwithoutdb.restvos.Entity;
import com.cipherprelude.templatevertxwithoutdb.restvos.Response;
import com.cipherprelude.templatevertxwithoutdb.util.JsonUtil;

import io.vertx.ext.web.RoutingContext;

public class BackendService {

	private static final Logger LOG = LoggerFactory.getLogger(BackendService.class);
	
	private Map<String, Object> objMap = new HashMap<String, Object>();

	/**
	 * REST Endpoint: Delete Entity.
	 * 
	 * @param routingContext
	 */
	public void deleteEntity(final RoutingContext routingContext) {
		
		String entityId = null;
		try {
			entityId = URLDecoder.decode(routingContext.request().getParam("id"), "UTF-8");
		} catch (final UnsupportedEncodingException e1) {
			LOG.error("Exception while decoding entity id.", e1);
		}
		
		try {
			
			objMap.remove(entityId);
			
			
			LOG.info("Retrieved entity with entity id: {}.", entityId);
			
			Response r = new Response();
			r.setSuccess(true);
			routingContext.response().putHeader("content-type", "application/json")
			.putHeader("Access-Control-Allow-Origin", "*")
			.putHeader("Access-Control-Allow-Headers", "Content-Type")
			.putHeader("Access-Control-Allow-Headers", "Authorization")
			.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
			.end(JsonUtil.createJsonFromObject(r));
			
		
			
		} catch (final Exception e) {
			
			LOG.error("Error while deleting entity: {}.",
					entityId, e);
			
		}

	}



	/**
	 * REST Endpoint: Create Entity.
	 * 
	 * @param routingContext
	 */
	public void createEntity(final RoutingContext routingContext) {
		
		try {
			
			final String jsonStr = routingContext.getBodyAsString() ;
			
			Entity ent = JsonUtil.createObjectFromString(jsonStr, Entity.class);
			
			objMap.put(ent.getId(), ent);
			
			Response r = new Response();
			r.setSuccess(true);
			routingContext.response().putHeader("content-type", "application/json")
			.putHeader("Access-Control-Allow-Origin", "*")
			.putHeader("Access-Control-Allow-Headers", "Content-Type")
			.putHeader("Access-Control-Allow-Headers", "Authorization")
			.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
			.end(JsonUtil.createJsonFromObject(r));
			
		} catch (final Exception e) {
			LOG.error("Error while processing JSON Request for entity create", e);
		}

	}


	/**
	 * REST Endpoint: Edit Entity.
	 * 
	 * @param routingContext
	 */
	public void editEntity(final RoutingContext routingContext) {

		
		try {
			
			final String jsonStr = routingContext.getBodyAsString() ;
			
			Entity ent = JsonUtil.createObjectFromString(jsonStr, Entity.class);
			
			objMap.put(ent.getId(), ent);
			
			Response r = new Response();
			r.setSuccess(true);
			routingContext.response().putHeader("content-type", "application/json")
			.putHeader("Access-Control-Allow-Origin", "*")
			.putHeader("Access-Control-Allow-Headers", "Content-Type")
			.putHeader("Access-Control-Allow-Headers", "Authorization")
			.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
			.end(JsonUtil.createJsonFromObject(r));
			
		} catch (final Exception e) {
			LOG.error("Error while processing JSON Request for entity create", e);
		}
	}

	public void getEntity(final RoutingContext routingContext) {

		String entityId = null;
		try {
			entityId = URLDecoder.decode(routingContext.request().getParam("id"), "UTF-8");
		} catch (final UnsupportedEncodingException e1) {
			LOG.error("Exception while decoding entity id.", e1);
		}
		
		try {
			Entity ent = null ; 
			if (null != objMap.get(entityId))
				ent = (Entity) objMap.get(entityId);

			if (null != ent) {
				LOG.info("Retrieved entity with entity id: {}.", entityId);
				routingContext.response().putHeader("content-type", "application/json")
				.putHeader("Access-Control-Allow-Origin", "*")
				.putHeader("Access-Control-Allow-Headers", "Content-Type")
				.putHeader("Access-Control-Allow-Headers", "Authorization")
				.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
				.end(JsonUtil.createJsonFromObject(ent));
			} else {
				Response r = new Response();
				r.setSuccess(false);
				routingContext.response().putHeader("content-type", "application/json")
				.putHeader("Access-Control-Allow-Origin", "*")
				.putHeader("Access-Control-Allow-Headers", "Content-Type")
				.putHeader("Access-Control-Allow-Headers", "Authorization")
				.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
				.end(JsonUtil.createJsonFromObject(r));
			}
			
		
			
		} catch (final Exception e) {
			
			LOG.error("Error while generating response for entityId: {}.",
					entityId, e);
			
		}

	}

}
