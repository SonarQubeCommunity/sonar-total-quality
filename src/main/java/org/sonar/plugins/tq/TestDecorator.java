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

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;


public class TestDecorator extends AbstractFormulaBasedDecorator {

	@Override
	protected String getLine(DecoratorContext context) {
		return context.getProject().getConfiguration().getString(TQPlugin.TQ_TEST_FORMULA,
			TQPlugin.TQ_TEST_FORMULA_DEFAULT);
	}
	
	@DependedUpon
	@Override
	public Metric generatesMetric() {
		return TQMetrics.TQ_TS;
	}

	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		return Arrays.asList(CoreMetrics.NCLOC, CoreMetrics.COVERAGE, CoreMetrics.BRANCH_COVERAGE, CoreMetrics.LINE_COVERAGE);
	}


}
