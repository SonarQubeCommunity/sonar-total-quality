/*
 * Sonar Total Quality Plugin, open source software quality management tool.
 * Copyright (C) 2010
 * mailto:e72636 AT gmail DTO com
 * mailto:escoem AT gmail DTO com
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

package org.sonar.plugins.totalquality;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

public class DesignNOMDecorator extends AbstractDesignDecorator {

  @DependedUpon
  @Override
  public List<Metric> generatesMetrics() {
    return Arrays.asList(TQMetrics.TQ_DESIGN_NOM);
  }

  @DependsUpon
  public List<Metric> dependsOnMetrics() {
    return Arrays.asList(CoreMetrics.FUNCTION_COMPLEXITY, CoreMetrics.CLASS_COMPLEXITY, CoreMetrics.NCLOC);
  }

  @Override
  void decorateFile(Resource resource, DecoratorContext context) {
    final int aceleration = context.getProject().getConfiguration().getInt(TQPlugin.TQ_ACE, Integer.parseInt(TQPlugin.TQ_ACE_DEFAULT));

    final double fc = doFileDecoration(resource, context, CoreMetrics.FUNCTION_COMPLEXITY, aceleration, context.getProject()
        .getConfiguration().getDouble(TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY,
            Double.parseDouble(TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY_DEFAULT)));

    final double cc = doFileDecoration(resource, context, CoreMetrics.CLASS_COMPLEXITY, aceleration, context.getProject()
        .getConfiguration().getDouble(TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY,
            Double.parseDouble(TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY_DEFAULT)));

    final double result = 0.5 * fc + 0.5 * cc;

    context.saveMeasure(TQMetrics.TQ_DESIGN_NOM, result);
  }

}
