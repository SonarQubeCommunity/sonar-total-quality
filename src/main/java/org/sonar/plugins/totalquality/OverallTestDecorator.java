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
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

public class OverallTestDecorator extends AbstractFormulaBasedDecorator {

  private Settings settings;

  public OverallTestDecorator(Settings settings) {
    this.settings = settings;
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return settings.getBoolean(TQPlugin.TQ_INCLUDE_IT_TESTS);
  }

  @Override
  protected String getLine(DecoratorContext context) {
    return settings.getString(TQPlugin.TQ_OVERALL_TEST_FORMULA);
  }

  @DependedUpon
  @Override
  public Metric generatesMetric() {
    return TQMetrics.TQ_OVERALL_TS;
  }

  @DependsUpon
  public List<Metric> dependsOnMetrics() {
    return Arrays.asList(CoreMetrics.NCLOC, TQMetrics.TQ_TS, CoreMetrics.IT_COVERAGE);
  }

  @Override
  public boolean shouldSaveMeasure(Resource resource) {
    return super.shouldSaveMeasure(resource) && isProj(resource);
  }
}
