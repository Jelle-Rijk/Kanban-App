package com.jellerijk.projects.learning.tools.kanban.utils;

public interface Subscriber {
	/**
	 * Called by a Publisher whenever an update of type DEFAULT is needed.
	 */
	public default void update() {
		update(PublishedMessageType.DEFAULT);
	}

	/**
	 * Called by a Publisher whenever an update is needed.
	 * 
	 * @param messageType - Type of update requested by the Publisher.
	 */
	public void update(PublishedMessageType messageType);

}
