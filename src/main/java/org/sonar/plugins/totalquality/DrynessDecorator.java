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
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

/** Dryness (duplicated lines inverse) decorator. */
public final class DrynessDecorator extends AbstractDecorator {

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
    if (hasCode(context) && shouldSaveMeasure(resource)) {
      final Double value = MeasureUtils.getValue(context.getMeasure(CoreMetrics.DUPLICATED_LINES_DENSITY), 0.0);
      final Double dry = Double.valueOf(100D - value.doubleValue());

      context.saveMeasure(TQMetrics.TQ_DRY, dry);
    }
  }

  @Override
  public boolean shouldSaveMeasure(Resource resource) {
    return super.shouldSaveMeasure(resource) && isProj(resource);
  }
  
}
