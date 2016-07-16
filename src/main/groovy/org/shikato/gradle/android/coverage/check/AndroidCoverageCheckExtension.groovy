package org.shikato.gradle.android.coverage.check

/**
 * AndroidCoverageCheckExtension.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class AndroidCoverageCheckExtension {

    private static final String ENTRY_DIR = "entryPointDir";
    private static final String PATH = "path";

    private Map<String, String[]> excludesMap = new HashMap<>();
    {
        excludesMap.put(ENTRY_DIR, "src");
        excludesMap.put(PATH, []);
    }

    private Map<String, String> reportXmlMap = new HashMap<>();
    {
        reportXmlMap.put(ENTRY_DIR, "build");
        reportXmlMap.put(PATH, ["**/coverage/**/report.xml"]);
    }


    // C0 minimum threshold
    int instruction = 20;
    // C1 minimum threshold
    int branch = 20;

    // チェック対象外ファイル
    void excludes(String[] path) {
        excludesMap.put(PATH, path);
    }
    // チェック対象外ファイル
    void excludes(String entryDir, String[] path) {
        excludesMap.put(ENTRY_DIR, entryDir);
        excludesMap.put(PATH, path);
    }

    // カバレッジレポート(report.xml)の場所
    void reportXml(String[] path) {
        reportXmlMap.put(PATH, path);
    }
    // カバレッジレポート(report.xml)の場所
    void reportXml(String entryDir, String[] path) {
        reportXmlMap.put(ENTRY_DIR, entryDir);
        reportXmlMap.put(PATH, path);
    }

    // 閾値を満たさなかった時にビルド失敗とするかどうか
    Boolean isBuildFailure = true;

    String getExcludesEntryDir() {
        return excludesMap.get(ENTRY_DIR);
    }

    String[] getExcludesPath() {
        return excludesMap.get(PATH);
    }

    String getReportXmlEntryDir() {
        return reportXmlMap.get(ENTRY_DIR);
    }

    String[] getReportXmlPath() {
        return reportXmlMap.get(PATH);
    }
}
