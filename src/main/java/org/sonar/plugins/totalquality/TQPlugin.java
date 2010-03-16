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
package org.sonar.plugins.totalquality;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Plugin;
import org.sonar.api.Properties;
import org.sonar.api.Property;

/** Total quality plugin definition. */
@Properties( {
    @Property(key = TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY, defaultValue = TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY_DEFAULT, name = "Default value of Function Complexity for Design NOM.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY, defaultValue = TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY_DEFAULT, name = "Default value of Class Complexity for Design NOM.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_LCOM, defaultValue = TQPlugin.TQ_DESIGN_LCOM_DEFAULT, name = "Default value of lcom for Design LCOM.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_RFC, defaultValue = TQPlugin.TQ_DESIGN_RFC_DEFAULT, name = "Default value of rfc for Design RFC.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_CBO, defaultValue = TQPlugin.TQ_DESIGN_CBO_DEFAULT, name = "Default value of ce for Design CBO.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_DIT, defaultValue = TQPlugin.TQ_DESIGN_DIT_DEFAULT, name = "Default value of dit for Design DIT.", description = ""),
    @Property(key = TQPlugin.TQ_ARCHITECTURE_ADI, defaultValue = TQPlugin.TQ_ARCHITECTURE_ADI_DEFAULT, name = "Default value of distance for Architecture ADI.", description = ""),
    @Property(key = TQPlugin.TQ_ACE, defaultValue = TQPlugin.TQ_ACE_DEFAULT, name = "Default aceleration value.", description = ""),
    @Property(key = TQPlugin.TQ_DESIGN_FORMULA, defaultValue = TQPlugin.TQ_DESIGN_FORMULA_DEFAULT, name = "Default Design Formula.", description = ""),
    @Property(key = TQPlugin.TQ_ARCH_FORMULA, defaultValue = TQPlugin.TQ_ARCH_FORMULA_DEFAULT, name = "Default Architecture Formula.", description = ""),
    @Property(key = TQPlugin.TQ_TEST_FORMULA, defaultValue = TQPlugin.TQ_TEST_FORMULA_DEFAULT, name = "Default Test Formula.", description = ""),
    @Property(key = TQPlugin.TQ_CODE_FORMULA, defaultValue = TQPlugin.TQ_CODE_FORMULA_DEFAULT, name = "Default Code Formula.", description = ""),
    @Property(key = TQPlugin.TQ_TQ_FORMULA, defaultValue = TQPlugin.TQ_TQ_FORMULA_DEFAULT, name = "Default Total Quality Formula.", description = "") })
public class TQPlugin implements Plugin {

  public static final String TQ_DESIGN_NOM_FUNCTION_COMPLEXITY = "tq.design.nom.function.complexity";
  public static final String TQ_DESIGN_NOM_FUNCTION_COMPLEXITY_DEFAULT = "2.5";
  public static final String TQ_DESIGN_NOM_CLASS_COMPLEXITY = "tq.design.nom.class.complexity";
  public static final String TQ_DESIGN_NOM_CLASS_COMPLEXITY_DEFAULT = "12.0";
  public static final String TQ_DESIGN_LCOM = "tq.design.lcom";
  public static final String TQ_DESIGN_LCOM_DEFAULT = "1.0";
  public static final String TQ_DESIGN_RFC = "tq.design.rfc";
  public static final String TQ_DESIGN_RFC_DEFAULT = "50.0";
  public static final String TQ_DESIGN_CBO = "tq.design.cbo";
  public static final String TQ_DESIGN_CBO_DEFAULT = "5.0";
  public static final String TQ_DESIGN_DIT = "tq.design.dit";
  public static final String TQ_DESIGN_DIT_DEFAULT = "5.0";
  public static final String TQ_ARCHITECTURE_ADI = "tq.architecture.adi";
  public static final String TQ_ARCHITECTURE_ADI_DEFAULT = "20.0";
  public static final String TQ_ACE = "tq.acel";
  public static final String TQ_ACE_DEFAULT = "2";
  public static final String TQ_TQ_FORMULA = "tq.tq.formula";
  public static final String TQ_TQ_FORMULA_DEFAULT = "ARCH=0.25 DESIGN=0.25 CODE=0.25 TESTS=0.25";
  public static final String TQ_DESIGN_FORMULA = "tq.design.formula";
  public static final String TQ_DESIGN_FORMULA_DEFAULT = "NOM=0.15 LCOM=0.15 RFC=0.25 CBO=0.25 DIT=0.20";
  public static final String TQ_ARCH_FORMULA = "tq.architecture.formula";
  // public static final String TQ_ARCH_FORMULA_DEFAULT = "PTI=0.50 ADI=0.50";
  public static final String TQ_ARCH_FORMULA_DEFAULT = "PTI=1.00 ADI=0.00";
  public static final String TQ_TEST_FORMULA = "tq.test.formula";
  public static final String TQ_TEST_FORMULA_DEFAULT = "COV=0.80 SUC=0.20";
  public static final String TQ_CODE_FORMULA = "tq.code.formula";
  public static final String TQ_CODE_FORMULA_DEFAULT = "DOC=0.15 RULES=0.45 DRY=0.40";

  public String getDescription() {
    return "Total Quality";
  }

  public List<Class<? extends Extension>> getExtensions() {
    List<Class<? extends Extension>> list = new ArrayList<Class<? extends Extension>>();

    list.add(TQMetrics.class);
    list.add(DrynessDecorator.class);
    list.add(DesignCBODecorator.class);
    list.add(DesignDITDecorator.class);
    list.add(DesignLCOM4Decorator.class);
    list.add(DesignNOMDecorator.class);
    list.add(DesignRFCDecorator.class);
    list.add(ArchitectureDecorator.class);
    list.add(CodeDecorator.class);
    list.add(DesignDecorator.class);
    list.add(TestDecorator.class);
    list.add(TotalQualityDecorator.class);
    list.add(ArchitectureADIDecorator.class);
    list.add(ArchitecturePTIDecorator.class);

    return list;
  }

  public String getKey() {
    return "total-quality";
  }

  public String getName() {
    return "Total Quality";
  }

  @Override
  public String toString() {
    return getKey();
  }

}
