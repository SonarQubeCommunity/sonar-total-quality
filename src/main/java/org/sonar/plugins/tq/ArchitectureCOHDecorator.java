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
import java.util.Collection;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;
import org.sonar.api.utils.ParsingUtils;

public class ArchitectureCOHDecorator extends AbstractBaseDecorator {

	@DependedUpon
	public List<Metric> generatesMetrics() {
		return Arrays.asList(TQMetrics.TQ_ARCHITECTURE_COH);
	}

	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		return Arrays.asList(CoreMetrics.FILE_CYCLES, CoreMetrics.PACKAGE_CYCLES, CoreMetrics.NCLOC);
	}
	
	@Override
	void decorateFile(Resource resource, DecoratorContext context) {
		//doDecoration(resource, context, CoreMetrics.FILE_CYCLES);
	}

	@Override
	void decorateDir(Resource resource, DecoratorContext context) {
		//doDecoration(resource, context, CoreMetrics.PACKAGE_CYCLES);
		doDecoration(resource, context, CoreMetrics.FILE_CYCLES);
	}
	
	private void doDecoration(Resource resource, DecoratorContext context, Metric metric) {
		final Measure measure = context.getMeasure(metric);

		final double cycles = MeasureUtils.hasValue(measure) ? measure.getValue() : 0;
		final double res = cycles > 0 ? 0 : 1;
		
		context.saveMeasure(TQMetrics.TQ_ARCHITECTURE_COH, ParsingUtils.scaleValue(res * 100, 2));
	}
	
	@Override
	void decorateProj(Resource resource, DecoratorContext context) {
		
		final Collection<Measure> measures = context.getChildrenMeasures(TQMetrics.TQ_ARCHITECTURE_COH);
		if (measures == null || measures.size() == 0) {
			return;
		}
		double total = 0;
		double size = 0.0;
		for (Measure m : measures) {
			if (MeasureUtils.hasValue(m)) {
				total = total + m.getValue();
				size = size + 1;
			}
		}
		double value = size > 0.0 ? (total / size / 100) : 0.0;
		context.saveMeasure(TQMetrics.TQ_ARCHITECTURE_COH, ParsingUtils.scaleValue(value * 100, 2));
		
		//doDecoration(resource, context, CoreMetrics.PACKAGE_CYCLES);
	}

}
