package org.anirudh.redquark.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.anirudh.redquark.factory.SolrServerFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrDAO<T> {

	private HttpSolrClient solrClient = null;

	public SolrDAO(String solrURL) {

		solrClient = (HttpSolrClient) SolrServerFactory.getInstance().createClient(solrURL);

	}

	public void put(T dao) {

		put(createSingleTonSet(dao));

	}

	public void put(Collection<T> dao) {

		try {
			UpdateResponse updateResponse = solrClient.addBeans(dao);
			System.out.println("Added document to Solr. Time taken = " + updateResponse.getElapsedTime());
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public void putDoc(SolrInputDocument doc) {

		putDoc(createSingleTonSet(doc));
	}

	public void putDoc(Collection<SolrInputDocument> docs) {

		try {
			long startTime = System.currentTimeMillis();
			UpdateRequest request = new UpdateRequest();
			request.setAction(AbstractUpdateRequest.ACTION.COMMIT, false, false);
			request.add(docs);
			UpdateResponse response = request.process(solrClient);
			System.out.print("Added documents to solr. Time taken = " + response.getElapsedTime());
			long endTime = System.currentTimeMillis();
			System.out.println(" , time-taken=" + ((double) (endTime - startTime)) / 1000.00 + " seconds");
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public QueryResponse readAll() {

		SolrQuery query = new SolrQuery();

		query.setQuery("*:*");

		QueryResponse queryResponse = null;

		try {
			queryResponse = solrClient.query(query);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}

		return queryResponse;
	}

	public SolrDocumentList readAllDocs() {

		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery("*:*");

		QueryResponse queryResponse = null;

		try {
			queryResponse = solrClient.query(solrQuery);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}

		SolrDocumentList docs = queryResponse.getResults();

		return docs;
	}

	private <U> Collection<U> createSingleTonSet(U dao) {

		if (dao == null) {
			return Collections.emptySet();
		}

		return Collections.singleton(dao);
	}
}
