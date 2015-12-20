package org.shikato.gradle.android.coverage.check

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.coverage.CoverageAll
import org.shikato.gradle.android.coverage.check.coverage.CoverageChecker
import org.shikato.gradle.android.coverage.check.log.CoverageTableLog
import org.shikato.gradle.android.coverage.check.xml.ReportXmlGetter
import org.shikato.gradle.android.coverage.check.xml.ReportXmlParser

class AndroidCoverageCheckPlugin implements Plugin<Project> {

    private static final String TASK_NAME_TYPE = "androidCoverageCheck";

    private static final String DESCRIPTION_TYPE = "Checks coverage reports.";

    private static final String EXTENSIONS_NAME = "androidCoverageCheck";

    @Override
    void apply(Project project) {
        project.extensions.create(EXTENSIONS_NAME, AndroidCoverageCheckExtension);

        project.task(TASK_NAME_TYPE, description: DESCRIPTION_TYPE) << {
            androidCoverageCheck(project);
        }
    }

    private def androidCoverageCheck(Project project) {
        AndroidCoverageCheckExtension extension = project.androidCoverageCheck;

        // getting report.xml
        List<File> xmlReports = ReportXmlGetter.get(project,
                extension.getXmlReportsEntryDir(), extension.getXmlReportsPath());

        if (xmlReports == null || xmlReports.size() == 0) {
            project.logger.warn("Failed getting report.xml")
            return;
        }

        xmlReports.each {

            // parsing report.xml
            CoverageAll coverage = ReportXmlParser.parse(project, it.text);
            coverage.setReportPath(it.path);
            if (coverage == null || coverage.getSourcefileList().size() == 0) return;

            // checking coverage
            coverage = CoverageChecker.check(project, coverage, extension);

            // output log
            CoverageTableLog.show(project, coverage, extension);

            // isHavingUnsatisfiedCoverage && isBuidFailure → failure build
            if (coverage.getIsHavingUnsatisfiedCoverage() && extension.isBuildFailure) {
                throw new GradleException("Coverage did not satisfy the minimum threshold.");
            }
        }
    }
}
