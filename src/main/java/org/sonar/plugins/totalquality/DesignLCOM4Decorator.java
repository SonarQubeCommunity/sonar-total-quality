/*
 * Sonar Total Quality Plugin
 * Copyright (C) 2010 Martin (e72636) and Emilio Escobar Reyero (escoem)
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
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

public class DesignLCOM4Decorator extends AbstractDesignDecorator {
  private Settings settings;

  public DesignLCOM4Decorator(Settings settings) {
    this.settings = settings;
  }
  @DependedUpon
  @Override
  public List<Metric> generatesMetrics() {
    return Arrays.asList(TQMetrics.TQ_DESIGN_LCOM4);
  }

  @DependsUpon
  public List<Metric> dependsOnMetrics() {
    return Arrays.asList(CoreMetrics.LCOM4, CoreMetrics.NCLOC);
  }

  @Override
  void decorateFile(Resource resource, DecoratorContext context) {
    final int aceleration = settings.getInt(TQPlugin.TQ_ACE);

    final double lcom = doFileDecoration(resource, context, CoreMetrics.LCOM4, 
            aceleration, Double.parseDouble(settings.getString(TQPlugin.TQ_DESIGN_LCOM)));

    context.saveMeasure(TQMetrics.TQ_DESIGN_LCOM4, lcom);
  }

}
