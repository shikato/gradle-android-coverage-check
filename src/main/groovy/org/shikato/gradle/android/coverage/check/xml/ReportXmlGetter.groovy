package org.shikato.gradle.android.coverage.check.xml

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor

/**
 * ReportXmlGetter.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ReportXmlGetter {

    public static List<File> get(Project project, String entryPoint, String[] path) {
        List<File> xmlReports = new ArrayList<>();

        FileTree xmlReportsFileTree = project.fileTree(entryPoint);

        path.each {
            xmlReportsFileTree.include(it).visit(new FileVisitor() {
                @Override
                void visitDir(FileVisitDetails dirDetails) {}

                @Override
                void visitFile(FileVisitDetails fileDetails) {
                    File file = project.file(fileDetails.getFile());
                    if (!file.exists()) return;
                    if (xmlReports.contains(file)) return;
                    xmlReports.add(file);
                }
            });
        }

        return xmlReports;
    }
}
