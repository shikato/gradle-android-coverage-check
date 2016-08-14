package org.shikato.gradle.android.coverage.check.xml

import groovy.util.slurpersupport.GPathResult
import org.gradle.api.Project
import org.shikato.gradle.android.coverage.check.coverage.CoverageAll
import org.shikato.gradle.android.coverage.check.coverage.CoverageCounter
import org.shikato.gradle.android.coverage.check.coverage.CoverageClass
import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * ReportXmlParser.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ReportXmlParser {

    public static CoverageAll parse(Project project, String xmlReport) {

        XmlSlurper parser = new XmlSlurper(false, false, true);
        if (parser == null) {
            project.logger.warn "Failed getting a xml parser..";
            return null
        };

        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        GPathResult node = parser.parseText(xmlReport);
        if (node == null) {
            project.logger.warn "Failed getting a xml node.";
            return null
        };

        CoverageAll coverage = parseReportXml(node);
        if (coverage == null) {
            project.logger.warn "Failed parsing xml";
            return null
        }

        return coverage;
    }

    private static CoverageAll parseReportXml(GPathResult node) {

        List<CoverageClass> coverageSourcefileList = new ArrayList<>();
        String currentPackageName = DefaultValue.STRING;

        node.package.each {
            currentPackageName = it.@name.toString();

            it.class.each {
                if (!isTargetClassTag(it.@name.toString())) return;

                CoverageClass coverage = new CoverageClass();
                println(it.@name.toString());
                coverage.setPackageName(currentPackageName);
                coverage.setFileName(it.@name.toString());
                coverage.setCounterList(getCounterList(it.counter));
                coverageSourcefileList.add(coverage);
            };
        };

        CoverageAll coverage = new CoverageAll();
        coverage.setSourcefileList(coverageSourcefileList);
        coverage.setCounterList(getCounterList(node.counter));

        return coverage;
    }

    private static getCounterList(GPathResult node) {
        List<CoverageCounter> counterList = new ArrayList<>();
        node.each {
            CoverageCounter counter = new CoverageCounter();
            counter.setType(it.@type.toString());
            counter.setMissed(Integer.parseInt(it.@missed.toString()));
            counter.setCovered(Integer.parseInt(it.@covered.toString()));
            counterList.add(counter);
        }
        return counterList;
    }

    private static boolean isTargetClassTag(String className) {
        return !(className ==~ /.*\$.*$/)
    }
}
