package org.shikato.gradle.android.coverage.check

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.coverage.All
import org.shikato.gradle.android.coverage.check.coverage.Checker
import org.shikato.gradle.android.coverage.check.log.CoverageTableLog
import org.shikato.gradle.android.coverage.check.xml.ReportXmlGetter
import org.shikato.gradle.android.coverage.check.xml.ReportXmlParser

/**
 * AndroidCoverageCheckPlugin.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class AndroidCoverageCheckPlugin implements Plugin<Project> {

    private static final String TASK_NAME_TYPE = "androidCoverageCheck";
    private static final String DESCRIPTION_TYPE = "Check jacoco coverage xml reports.";
    private static final String EXTENSIONS_NAME = "androidCoverageCheck";

    @Override
    void apply(Project project) {
        project.extensions.create(EXTENSIONS_NAME, AndroidCoverageCheckExtension);

        project.task(TASK_NAME_TYPE, description: DESCRIPTION_TYPE) {
            mustRunAfter 'createDebugCoverageReport'
            doLast {
              androidCoverageCheck(project);
            }
        }
    }

    private void androidCoverageCheck(Project project) {
        AndroidCoverageCheckExtension extension = project.androidCoverageCheck;

        // getting report.xml
        List<File> reportXmlList = ReportXmlGetter.get(project,
                extension.getReportXmlEntryDir(), extension.getReportXmlPath());

        if (reportXmlList == null || reportXmlList.size() == 0) {
            project.logger.warn("Failed getting coverage reports.")
            return;
        }

        reportXmlList.each {

            // parsing report.xml
            All coverage = ReportXmlParser.parse(project, it.text);
            coverage.setReportPath(it.path);
            if (coverage == null || coverage.getClassList().size() == 0) return;

            // checking coverage
            coverage = Checker.check(project, coverage, extension);

            // output log
            CoverageTableLog.show(project, coverage, extension);

            // hasUnsatisfiedCoverage && isBuildFailure → failure build
            if (coverage.hasUnsatisfiedCoverage && extension.isBuildFailure) {
                throw new GradleException("Coverage did not satisfy the minimum threshold.");
            }
        }
    }
}
