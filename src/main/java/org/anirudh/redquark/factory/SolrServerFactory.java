package org.anirudh.redquark.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrServerFactory {

	/**
	 * Singleton instance throughout the application
	 */
	private static SolrServerFactory instance = new SolrServerFactory();

	/**
	 * Map to map Solr Client with the URL
	 */
	private Map<String, SolrClient> urlToServer = new ConcurrentHashMap<>();

	private SolrServerFactory() {

	}

	public static SolrServerFactory getInstance() {

		return instance;
	}

	public SolrClient createClient(String solrURL) {

		if (urlToServer.containsKey(solrURL)) {
			return urlToServer.get(solrURL);
		}

		SolrClient client = new HttpSolrClient.Builder(solrURL).build();
		urlToServer.put(solrURL, client);

		return client;
	}
}
