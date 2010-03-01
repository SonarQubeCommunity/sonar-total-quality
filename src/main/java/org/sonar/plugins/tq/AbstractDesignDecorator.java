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


import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.utils.ParsingUtils;


public abstract class AbstractDesignDecorator implements Decorator {

	abstract void decorateFile(Resource resource, DecoratorContext context);

	abstract List<Metric> generatesMetrics();

	double doFileDecoration(final Resource resource, final DecoratorContext context, final Metric orig,
		final int aceleration, final double cota) {
		final double value;

		final Measure measure = context.getMeasure(orig);
		final double metric = measure != null ? measure.getValue() : 0;
		final double top = aceleration <= 1 ? cota : aceleration * cota;

		if (metric <= cota) {
			value = 100.0;
		} else if (metric > top) {
			value = 0.0;
		} else {
			value = ParsingUtils.scaleValue((1 - ((metric - cota) / (top - cota))) * 100, 2);
		}

		return value;
	}

	void decorateDir(Resource resource, DecoratorContext context) {
		final List<DecoratorContext> children = context.getChildren();

		if (children != null && !children.isEmpty()) {
			final List<Metric> metrics = generatesMetrics();

			for (Metric metric : metrics) {
				double sum = 0.0;
				double size = 0.0;

				for (DecoratorContext dc : children) {
					final Resource c = dc.getResource();
					if (!c.getQualifier().equals(Resource.QUALIFIER_UNIT_TEST_CLASS)) {
						final Measure measure = dc.getMeasure(metric);

						if (measure != null && measure.getValue() != null) {
							sum += measure.getValue();
							size = size + 1;
						}
					}
				}
				double value = size > 0.0 ? (sum / size) : -1;
				if (value != -1) {
					context.saveMeasure(new Measure(metric, ParsingUtils.scaleValue(value, 2)));
				}
			}
		}
	}

	void decorateProj(Resource resource, DecoratorContext context) {
		decorateDir(resource, context);
	}

	public void decorate(Resource resource, DecoratorContext context) {
		if (isFile(resource, context)) {
			decorateFile(resource, context);
		} else if (isDir(resource, context)) {
			decorateDir(resource, context);
		} else if (isProj(resource, context)) {
			decorateProj(resource, context);
		} else {
			// not hear please!
		}
	}

	public boolean isFile(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource)
			&& (Resource.QUALIFIER_FILE.equals(resource.getQualifier()) || Resource.QUALIFIER_CLASS.equals(resource
				.getQualifier()));
	}

	public boolean isDir(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource) && hasCode(context)
			&& (Resource.QUALIFIER_DIRECTORY.equals(resource.getQualifier()) || Resource.QUALIFIER_PACKAGE
				.equals(resource.getQualifier()));
	}

	public boolean isProj(final Resource resource, final DecoratorContext context) {
		return shouldSaveMeasure(resource) && hasCode(context)
			&& (Resource.QUALIFIER_MODULE.equals(resource.getQualifier())
				|| Resource.QUALIFIER_PROJECT.equals(resource.getQualifier())
				|| Resource.QUALIFIER_SUBVIEW.equals(resource.getQualifier()) || Resource.QUALIFIER_VIEW
				.equals(resource.getQualifier()));
	}

	public boolean hasCode(final DecoratorContext context) {
		return context.getMeasure(CoreMetrics.NCLOC) != null
			&& context.getMeasure(CoreMetrics.NCLOC).getValue() != null
			&& context.getMeasure(CoreMetrics.NCLOC).getValue().doubleValue() > 0;
	}

	public boolean shouldSaveMeasure(final Resource resource) {
		return !Resource.QUALIFIER_UNIT_TEST_CLASS.equals(resource.getQualifier());
	}

	/** Only for java projects. */
	public boolean shouldExecuteOnProject(Project project) {
		return Java.INSTANCE.equals(project.getLanguage());
	}
}
