/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.jame.service.network;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public abstract class ServiceMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MESSAGE_TYPE_EVENT = 10;
	public static final int MESSAGE_TYPE_REQUEST = 20;
	public static final int MESSAGE_TYPE_RESPONSE = 30;
	private final String messageId;
	private final int messageType;

	/**
	 * @param messageId
	 * @param messageType
	 */
	protected ServiceMessage(final String messageId, final int messageType) {
		this.messageId = messageId;
		this.messageType = messageType;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageTpe
	 * @return
	 */
	public static final String decodeMessageType(final int messageType) {
		switch (messageType) {
			case MESSAGE_TYPE_EVENT:
				return "EVENT";
			case MESSAGE_TYPE_REQUEST:
				return "REQUEST";
			case MESSAGE_TYPE_RESPONSE:
				return "RESPONSE";
			default:
				return "unknown";
		}
	}
}
