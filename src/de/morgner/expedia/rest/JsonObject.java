/*
 *  Copyright (C) 2012 Axel Morgner
 * 
 *  This file is part of structr <http://structr.org>.
 * 
 *  structr is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  structr is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with structr.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.morgner.expedia.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * An abstract base class for REST entities. This class encapsulates everything
 * neccesary to interact with a REST server in a single, convenient interface.
 * Derive your entities from this class, and you can easily create, load, update
 * and delete them on a REST server. 
 *
 * @author Christian Morgner
 */
public abstract class JsonObject {

	private static final Gson gson     = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();	
	private static final Logger logger = Logger.getLogger(JsonObject.class.getName());
	
	private static DefaultHttpClient client = null;
	
	/**
	 * Override this method to load additional resources after
	 * the entity has been created from the JSON source. You can
	 * for example use this method to load nested fields of an
	 * entity synchronously.
	 */
	public void onDbLoad() {
	}
	
	/**
	 * Creates this entity on the REST server. After successful creation, the ID
	 * of this entity will be set.
	 * 
	 * @throws Throwable 
	 */
	public static <R extends JsonObject, S extends JsonObject> R doPost(final Class<R> resultType, final Class<S> dataType, final S data, final String path) throws IOException, RestClientException, ExpediaException {
		return post(buildPath(path), data, dataType, resultType);
	}
	
	/**
	 * Stores the exposed attributes of this entity on the REST server.
	 * 
	 * @throws Throwable 
	 */
	public static <R extends JsonObject, S extends JsonObject> R doPut(final Class<R> resultType, final Class<S> dataType, final S data, final String path) throws IOException, RestClientException, ExpediaException {
		return put(buildPath(path), data, dataType, resultType);
	}
	
	/**
	 * Loads an entity from the given path. Use this method to load entities from
	 * arbitraty paths.
	 * 
	 * @param type the type of the entity to load
	 * @param path the path of the entity to load
	 * @return the entity from the REST server, or null if the entity was not found
	 * @throws Throwable 
	 */
	public static <T extends JsonObject> T doGet(final Class<T> type, final String path) throws IOException, RestClientException, ExpediaException {
		return load(type, buildPath(path));
	}

	/**
	 * Shuts down the the http client that is used for the database connection.
	 */
	public static void shutdownDatabaseConnection() {
		
		if(client != null) {
			client.getConnectionManager().shutdown();
			// client.close();
			client = null;
		}
	}
	
	/**
	 * @return the http client that is used to connect to the REST server.
	 */
	public static DefaultHttpClient getHttpClient() {
		
		if(client == null) {
			client = new DefaultHttpClient();
		}
		
		return client;
	}
	
	// ----- private static methods -----
	protected static <T extends JsonObject> T load(Class<T> type, String path) throws IOException, RestClientException, ExpediaException {
		
		logger.log(Level.INFO, "GET {0}", path);
		return execute(new HttpGet(path), type);
	}
	
	private static <S extends JsonObject, R extends JsonObject> R post(final String path, final S entity, final Class<S> type, final Class<R> resultType) throws IOException, RestClientException, ExpediaException {
		
		final HttpPost httpPost = new HttpPost(path);
		final StringBuilder buf = new StringBuilder();
		
		logger.log(Level.INFO, "POST {0}", path);

		// is there any data to post?
		if (type != null && entity != null) {
			
			gson.toJson(entity, type, buf);
			logger.log(Level.INFO, "JSON request:\n{0}", buf.toString());

			StringEntity body = new StringEntity(buf.toString(), "UTF-8");
			body.setContentType("application/json");
			httpPost.setEntity(body);
		}

		return execute(httpPost, resultType);
	}
	
	private static <S extends JsonObject, R extends JsonObject> R put(final String path, final S entity, final Class<S> type, final Class<R> resultType) throws IOException, RestClientException, ExpediaException {
		
		final HttpPut httpPut   = new HttpPut(path);
		final StringBuilder buf = new StringBuilder();
		
		logger.log(Level.INFO, "PUT {0}", path);
		
		// is there any data to post?
		if (type != null && entity != null) {
			
			gson.toJson(entity, type, buf);
			logger.log(Level.INFO, "JSON request:\n{0}", buf.toString());

			StringEntity body = new StringEntity(buf.toString(), "UTF-8");
			body.setContentType("application/json");
			httpPut.setEntity(body);
		}

		return execute(httpPut, resultType);
	}
	
	private static <T extends JsonObject> T execute(final HttpUriRequest request, final Class<T> type) throws IOException, RestClientException, ExpediaException {
			
		final DefaultHttpClient httpClient = getHttpClient();
		
		logger.log(Level.INFO, "request:\n{0}", request);
		
		final HttpResponse response = httpClient.execute(request);
		T result                    = null;

		logger.log(Level.INFO, "response:\n{0}", response);
		
		if (response.getStatusLine().getStatusCode() == 200) {

			// Patch JSON string: HotelSummary is not always an array
			final String json = StringUtils.replace(StringUtils.replace(consumeContent(response), "\"HotelSummary\":{", "\"HotelSummary\":[{"), "}}}}", "}]}}}");
			
			logger.log(Level.INFO, "JSON response:\n{0}", json);
			
			try {
				result = gson.fromJson(json, type);
				result.onDbLoad();
				
			} catch (JsonSyntaxException jsex) {

				jsex.printStackTrace();
				// log exception?
			}

		} else {
			
			throw new RestClientException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
		}

		return result;
	}

	private static String consumeContent(final HttpResponse response) throws IOException {
		
		final String content;
		
		try (InputStream is = response.getEntity().getContent()) {
			content = IOUtils.toString(is);
		}
		
		return content;
	}

	protected static String buildPath(final String url, final Object... params) {
		
		StringBuilder path = new StringBuilder(url);
		
		for(Object o : params) {
			
			if(o.getClass().isArray()) {
				Object[] array = (Object[])o;
				for(Object a : array) {
					path.append(a);
				}
				
			} else {
				path.append(o);
			}
		}
		
		return path.toString();
	}
}
