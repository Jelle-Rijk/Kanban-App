package com.jellerijk.projects.learning.tools.kanban.utils;

import java.util.Collection;

public interface Publisher {
	public default void subscribe(Subscriber sub) {
		getSubscribers().add(sub);
	};

	/**
	 * Returns the Publisher's Subscribers.
	 * 
	 * @return
	 */
	public Collection<Subscriber> getSubscribers();

	/**
	 * Notifies all Subscribers with a message of type DEFAULT.
	 */
	public default void notifySubs() {
		notifySubs(PublishedMessageType.DEFAULT);
	}

	/**
	 * Notifies all Subscribers with a message.
	 * 
	 * @param messageType - Type of the message.
	 */
	public default void notifySubs(PublishedMessageType messageType) {
		getSubscribers().forEach(sub -> sub.update(messageType));
	};
}
