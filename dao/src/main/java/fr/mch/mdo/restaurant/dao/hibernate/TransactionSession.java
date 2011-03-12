package fr.mch.mdo.restaurant.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionSession
{
	private Transaction transaction;
	private Session session;

	public TransactionSession() {
	}

	public TransactionSession(Transaction transaction, Session session) {
		this.transaction = transaction;
		this.session = session;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}
}
