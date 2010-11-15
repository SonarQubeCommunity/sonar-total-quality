/*
 * Sonar Total Quality Plugin
 * Copyright (C) 2010 Martin (e72636) & Emilio Escobar Reyero (escoem)
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.totalquality;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

public class DesignDecorator extends AbstractFormulaBasedDecorator {

  @Override
  protected String getLine(DecoratorContext context) {
    return context.getProject().getConfiguration().getString(TQPlugin.TQ_DESIGN_FORMULA, TQPlugin.TQ_DESIGN_FORMULA_DEFAULT);
  }

  @DependedUpon
  @Override
  public Metric generatesMetric() {
    return TQMetrics.TQ_DESIGN;
  }

  @DependsUpon
  public List<Metric> dependsOnMetrics() {
    return Arrays.asList(TQMetrics.TQ_DESIGN_CBO, TQMetrics.TQ_DESIGN_DIT, TQMetrics.TQ_DESIGN_LCOM4, TQMetrics.TQ_DESIGN_NOM,
        TQMetrics.TQ_DESIGN_RFC);
  }

  @Override
  public boolean shouldSaveMeasure(Resource resource) {
    return super.shouldSaveMeasure(resource) && isProj(resource);
  }
  
}
