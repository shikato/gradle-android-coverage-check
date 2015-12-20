package org.shikato.gradle.android.coverage.check

class AndroidCoverageCheckExtension {

    private static final String ENTRY_DIR = "entryPointDir";
    private static final String PATH = "path";

    private Map<String, String[]> excludesMap = new HashMap<>();
    {
        excludesMap.put(ENTRY_DIR, "src");
        excludesMap.put(PATH, []);
    }

    private Map<String, String> xmlReportsMap = new HashMap<>();
    {
        xmlReportsMap.put(ENTRY_DIR, "build");
//        xmlReportsMap.put(PATH, ["outputs/reports/coverage/**/report.xml"]);
        xmlReportsMap.put(PATH, ["**/coverage/**/report.xml"]);
    }


    // C0 minimum threshold
    int instruction = 50;
    // C1 minimum threshold
    int branch = 50;

    // チェック対象外ファイル
    def excludes(String entryDir, String[] path) {
        excludesMap.put(ENTRY_DIR, entryDir);
        excludesMap.put(PATH, path);
    }

    // カバレッジレポート(report.xml)の場所
    def xmlReports(String entryDir, String[] path) {
        xmlReportsMap.put(ENTRY_DIR, entryDir);
        xmlReportsMap.put(PATH, path);
    }

    // 閾値を満たさなかった時にビルド失敗とするかどうか
    Boolean isBuildFailure = true;
//    Boolean isBuildFailure = false;

    String getExcludesEntryDir() {
        return excludesMap.get(ENTRY_DIR);
    }

    String[] getExcludesPath() {
        return excludesMap.get(PATH);
    }

    String getXmlReportsEntryDir() {
        return xmlReportsMap.get(ENTRY_DIR);
    }

    String[] getXmlReportsPath() {
        return xmlReportsMap.get(PATH);
    }
}
