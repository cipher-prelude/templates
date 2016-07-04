package com.cipherprelude.templatevertxwithoutdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cipherprelude.templatevertxwithoutdb.service.BackendService;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestServiceController extends AbstractVerticle {

	private RestServiceController that = this;
	private static final Logger LOG = LoggerFactory.getLogger(RestServiceController.class);

	public BackendService backendSvc;

	@Override
	public void start() {
		LOG.debug("REST Controller Event Loop Thread: " + Thread.currentThread());
		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());
		router.options("/*").handler(that::optionsCall);

		backendSvc = new BackendService();
		
	
		router.get("/rest/get").handler(this.backendSvc::getEntity);
		router.post("/rest/create").handler(this.backendSvc::createEntity);
		router.put("/rest/edit").handler(this.backendSvc::editEntity);
		router.delete("/rest/delete").handler(this.backendSvc::deleteEntity);

		
		vertx.createHttpServer().requestHandler(router::accept).listen(9090);
	}

	/**
	 * Handler to set allowed headers.
	 * 
	 * @param routingContext
	 */
	private void optionsCall(RoutingContext routingContext) {

		try {
			routingContext.response().putHeader("Access-Control-Allow-Origin", "*")
					.putHeader("Access-Control-Allow-Methods", "GET, POST, PUT , OPTIONS")
					.putHeader("Access-Control-Allow-Headers", "Content-Type")
					.putHeader("Access-Control-Allow-Headers", "Authorization")
					.putHeader("Access-Control-Max-Age", "86400").end();
		} catch (final Exception e) {
			LOG.error("Exception while setting roting context option calls.", e);
			e.printStackTrace();
		}

	}

	public void setBackendSvc(final BackendService backendSvc) {
		this.backendSvc = backendSvc;
	}
	
	


}
