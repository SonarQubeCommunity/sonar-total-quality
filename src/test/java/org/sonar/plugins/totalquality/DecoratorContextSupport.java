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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.Event;
import org.sonar.api.design.Dependency;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasuresFilter;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Violation;
import org.sonar.api.violations.ViolationQuery;


public class DecoratorContextSupport implements DecoratorContext {

  private Measure m;
  private Metric c;
  private Double d;
  
  Measure getM() {
    return m;
  }
  Metric getC() {
    return c;
  }
  Double getD() {
    return d;
  }  
  
  public DecoratorContext saveMeasure(Measure arg0) {
    m = arg0;
    return this;
  }
  public DecoratorContext saveMeasure(Metric arg0, Double arg1) {
    c = arg0;
    d = arg1;
    return this;
  }

  
  public Event createEvent(String arg0, String arg1, String arg2, Date arg3) {
    return null;
  }
  public void deleteEvent(Event arg0) {
  }
  public List<DecoratorContext> getChildren() {
    return null;
  }
  public Collection<Measure> getChildrenMeasures(MeasuresFilter arg0) {
    return null;
  }
  public Collection<Measure> getChildrenMeasures(Metric arg0) {
    return null;
  }
  public Set<Dependency> getDependencies() {
    return null;
  }
  public List<Event> getEvents() {
    return null;
  }
  public Collection<Dependency> getIncomingDependencies() {
    return null;
  }
  public Measure getMeasure(Metric arg0) {
    return null;
  }
  public <M> M getMeasures(MeasuresFilter<M> arg0) {
    return null;
  }
  public Collection<Dependency> getOutgoingDependencies() {
    return null;
  }
  public Project getProject() {
    return null;
  }
  public Resource getResource() {
    return null;
  }
  public List<Violation> getViolations() {
    return null;
  }
  public Dependency saveDependency(Dependency arg0) {
    return null;
  }
  public DecoratorContext saveViolation(Violation arg0) {
    return null;
  }

  public List<Violation> getViolations(ViolationQuery vq) {
    return null;
  }

  public DecoratorContext saveViolation(Violation vltn, boolean bln) {
    return null;
  }
}
