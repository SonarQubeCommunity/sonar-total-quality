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

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;


public class DesignDecorator implements Decorator {

	@DependedUpon
	public List<Metric> generatesMetrics() {
		return Arrays.asList(TQMetrics.TQ_DESIGN);
	}

	@DependsUpon
	public List<Metric> dependsOnMetrics() {
		return Arrays.asList(TQMetrics.TQ_DESIGN_CBO, TQMetrics.TQ_DESIGN_DIT, TQMetrics.TQ_DESIGN_LCOM,
			TQMetrics.TQ_DESIGN_NOM, TQMetrics.TQ_DESIGN_RFC);
	}

	public void decorate(Resource resource, DecoratorContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean shouldExecuteOnProject(Project project) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
