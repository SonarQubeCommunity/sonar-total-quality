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
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

/**
 * Total quality plugin definition.
 */
@Properties({
  @Property(
        key = TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY, 
        defaultValue = TQPlugin.TQ_DESIGN_NOM_FUNCTION_COMPLEXITY_DEFAULT, 
        name = "Default value of Function Complexity for Design NOM.", 
        project=true),
  @Property(
        key = TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY, 
        defaultValue = TQPlugin.TQ_DESIGN_NOM_CLASS_COMPLEXITY_DEFAULT, 
        name = "Default value of Class Complexity for Design NOM.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_DESIGN_LCOM, 
        defaultValue = TQPlugin.TQ_DESIGN_LCOM_DEFAULT, 
        name = "Default value of lcom for Design LCOM.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_DESIGN_RFC, 
        defaultValue = TQPlugin.TQ_DESIGN_RFC_DEFAULT, 
        name = "Default value of rfc for Design RFC.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_DESIGN_CBO, 
        defaultValue = TQPlugin.TQ_DESIGN_CBO_DEFAULT, 
        name = "Default value of ce for Design CBO.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_DESIGN_DIT, 
        defaultValue = TQPlugin.TQ_DESIGN_DIT_DEFAULT, 
        name = "Default value of dit for Design DIT.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_ARCHITECTURE_ADI, 
        defaultValue = TQPlugin.TQ_ARCHITECTURE_ADI_DEFAULT, 
        name = "Default value of distance for Architecture ADI.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_ACE, 
        defaultValue = TQPlugin.TQ_ACE_DEFAULT, 
        name = "Default aceleration value.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_DESIGN_FORMULA, 
        defaultValue = TQPlugin.TQ_DESIGN_FORMULA_DEFAULT, 
        name = "Default Design Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_ARCH_FORMULA, 
        defaultValue = TQPlugin.TQ_ARCH_FORMULA_DEFAULT, 
        name = "Default Architecture Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_TEST_FORMULA, 
        defaultValue = TQPlugin.TQ_TEST_FORMULA_DEFAULT, 
        name = "Default Test Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_OVERALL_TEST_FORMULA, 
        defaultValue = TQPlugin.TQ_OVERALL_TEST_FORMULA_DEFAULT, 
        name = "Default Overall Test Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_CODE_FORMULA, 
        defaultValue = TQPlugin.TQ_CODE_FORMULA_DEFAULT, 
        name = "Default Code Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_TQ_FORMULA, 
        defaultValue = TQPlugin.TQ_TQ_FORMULA_DEFAULT, 
        name = "Default Total Quality Formula.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_TQ_FORMULA_WITH_IT, 
        defaultValue = TQPlugin.TQ_TQ_FORMULA_WITH_IT_DEFAULT, 
        name = "Default Total Quality Formula including Integration Tests.", 
        project = true),
  @Property(
        key = TQPlugin.TQ_INCLUDE_IT_TESTS, 
        type= org.sonar.api.PropertyType.BOOLEAN,
        defaultValue = "false", 
        name = "Include IT Tests in Test Formula", 
        project = true)
  })
public class TQPlugin extends SonarPlugin {

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
  public static final String TQ_TQ_FORMULA_WITH_IT = "tq.tq.formula_with_it";
  public static final String TQ_TQ_FORMULA_WITH_IT_DEFAULT = "ARCH=0.25 DESIGN=0.25 CODE=0.25 OVERALL_TESTS=0.25";
  public static final String TQ_DESIGN_FORMULA = "tq.design.formula";
  public static final String TQ_DESIGN_FORMULA_DEFAULT = "NOM=0.15 LCOM=0.15 RFC=0.25 CBO=0.25 DIT=0.20";
  public static final String TQ_ARCH_FORMULA = "tq.architecture.formula";
  public static final String TQ_ARCH_FORMULA_DEFAULT = "PTI=1.00 ADI=0.00";
  public static final String TQ_TEST_FORMULA = "tq.test.formula";
  public static final String TQ_TEST_FORMULA_DEFAULT = "COV=0.80 SUC=0.20";
  public static final String TQ_OVERALL_TEST_FORMULA = "tq.overall_test.formula";
  public static final String TQ_OVERALL_TEST_FORMULA_DEFAULT = "IT_COV=0.40 TESTS=0.60";
  public static final String TQ_CODE_FORMULA = "tq.code.formula";
  public static final String TQ_CODE_FORMULA_DEFAULT = "DOC=0.15 RULES=0.45 DRY=0.40";
  public static final String TQ_INCLUDE_IT_TESTS = "sonar.tq.it_tests.enabled";
  
  public List getExtensions() {
    return Arrays.asList(TQMetrics.class,
            DrynessDecorator.class,
            DesignCBODecorator.class,
            DesignDITDecorator.class,
            DesignLCOM4Decorator.class,
            DesignNOMDecorator.class,
            DesignRFCDecorator.class,
            ArchitectureDecorator.class,
            CodeDecorator.class,
            DesignDecorator.class,
            TestDecorator.class,
            OverallTestDecorator.class,
            TotalQualityDecorator.class,
            ArchitectureADIDecorator.class,
            ArchitecturePTIDecorator.class,
            TotalQualityWidget.class);
  }

  @Override
  public String toString() {
    return "Total Quality Plugin";
  }
}
