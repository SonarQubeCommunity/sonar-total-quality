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


import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;


public abstract class AbstractFormulaBasedDecorator extends AbstractDecorator {

	public abstract Metric generatesMetric();
	protected abstract String getLine(DecoratorContext context);
	
	public void decorate(Resource resource, DecoratorContext context) {
		if (hasCode(context)) {
			final String line = getLine(context);

			final Double value = solve(context, line);

			context.saveMeasure(generatesMetric(), value);
		}
		
	}
	
	protected Double solve(DecoratorContext context, String formula) {
		double sum = 0;

		final String[] params = formula.split(" ");
		for (int i = 0; i < params.length; i++) {
			final String[] param = params[i].split("=");
			final Metric metric = TQMetrics.formulaParams.get(param[0]);
			final Double mod = Double.parseDouble(param[1]);
			final Measure measure = context.getMeasure(metric);
			if (MeasureUtils.hasValue(measure)) {
				final double value = measure.getValue().doubleValue() * mod.doubleValue();
				sum = sum + value;
			}
		}

		return Double.valueOf(sum);
	}

}
