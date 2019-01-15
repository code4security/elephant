/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sjhy.platform.client.dto.log4j.filters;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author lord_rex
 * 
 */
public final class AuditFilter extends Filter
{
	/**
	 * Decides what to do with logging event.<br>
	 * This method accepts only log events that contain exceptions.
	 * 
	 * @param loggingEvent
	 *            log event that is going to be filtred.
	 * @return {@link Filter#ACCEPT} if admin command, {@link Filter#DENY}
	 *         otherwise
	 */
	@Override
	public final int decide(LoggingEvent loggingEvent)
	{
		Object message = loggingEvent.getMessage();

		if (((String) message).startsWith("[AUDIT]"))
		{
			return ACCEPT;
		}

		return DENY;
	}
}