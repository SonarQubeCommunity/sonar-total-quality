/*
 * Sonar Total Quality Plugin, open source software quality management tool.
 * Copyright (C) 2010 
 * mailto:e72636 AT gmail DTO com
 *
 * Sonar Total Quality Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar Total Quality Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.tq;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

/** Dryness (duplicated lines inverse) decorator. */
public final class DrynessDecorator implements Decorator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/** Dryness. */
	@DependedUpon
	public List<Metric> generatesMetrics() {
		return Arrays.asList(TQMetrics.TQ_DRY);
	}
	
	/** Duplicated lines density. */
	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		return Arrays.asList(CoreMetrics.DUPLICATED_LINES_DENSITY);
	}
	
	public void decorate(Resource resource, DecoratorContext context) {
		if (shouldSaveMeasure(resource)) {
			final Double value = MeasureUtils.getValue(context.getMeasure(CoreMetrics.DUPLICATED_LINES_DENSITY), 0.0);
			final Double dry = Double.valueOf(100D - value.doubleValue());
			
			context.saveMeasure(TQMetrics.TQ_DRY, dry);
			if (logger.isTraceEnabled()) {
				logger.trace("DrynessDecorator :: " + resource.getKey() + " DRYness = " + dry.toString());
			}
		} else if (logger.isTraceEnabled()) {
			logger.trace("DrynessDecorator :: " + resource.getKey() + " not computable.");
		}
		
	}
	
	public boolean shouldSaveMeasure(final Resource resource) {
	    return !Resource.QUALIFIER_UNIT_TEST_CLASS.equals(resource.getQualifier());
	  }
	
	/** Only for java projects. */
	public boolean shouldExecuteOnProject(Project project) {
		 return Java.INSTANCE.equals(project.getLanguage());
	}
}
