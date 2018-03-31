package org.anirudh.redquark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.anirudh.redquark.dao.SolrDAO;
import org.anirudh.redquark.model.Item;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrJStart {

	private String solrURL = "http://localhost:8983/solr/quark";

	public static void main(String[] args) {

		new SolrJStart().execute();
	}

	public void execute() {

		System.out.println("Starting off " + this.getClass().toString());
		SolrDAO<Item> solrDAO = new SolrDAO<Item>(solrURL);

		addDocuments(solrDAO);
		readDocuments(solrDAO);

		addItems(solrDAO);
		readItems(solrDAO);

	}

	private void readItems(SolrDAO<Item> solrDAO) {

		QueryResponse response = solrDAO.readAll();

		List<Item> items = response.getBeans(Item.class);

		for (Item item : items) {

			System.out.println("Read item " + item.getId() + ", name = " + item.getName());
		}
	}

	private void addItems(SolrDAO<Item> solrDao) {

		Collection<Item> items = new ArrayList<Item>();

		items.add(new Item("1", "Item 1", 30.0F));
		items.add(new Item("2", "Item 2", 40.0F));
		items.add(new Item("3", "Item 3", 50.0F));
		items.add(new Item("4", "Item 4", 70.0F));

		solrDao.put(items);
	}

	private void readDocuments(SolrDAO<Item> solrDao) {

		SolrDocumentList docs = solrDao.readAllDocs();
		Iterator<SolrDocument> iterator = docs.iterator();

		int count = 4;

		while (iterator.hasNext() && count-- > 0) {

			SolrDocument resultDoc = iterator.next();

			String content = (String) resultDoc.getFieldValue("content");
			String id = (String) resultDoc.getFieldValue("id");

			System.out.println("Read " + resultDoc + " with id = " + id + " and content = " + content);
		}
	}

	private void addDocuments(SolrDAO<Item> solrDAO) {

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		for (int i = 0; i < 1000; i++) {
			docs.add(getRandomSolrDoc(i));
		}

		solrDAO.putDoc(docs);

	}

	private SolrInputDocument getRandomSolrDoc(int count) {

		SolrInputDocument doc = new SolrInputDocument();

		doc.addField("id", "id" + count);
		doc.addField("name", "doc" + count);
		doc.addField("price", count % 10);

		return doc;
	}
}
