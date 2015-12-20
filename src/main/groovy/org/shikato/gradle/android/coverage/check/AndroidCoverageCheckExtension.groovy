package org.shikato.gradle.android.coverage.check

// TOOD: もう少しgroovyっぽく書く必要がありそう
// String[] targets = []; みたいにexcludesを指定しないといけなくなってしまっている、、、
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
        xmlReportsMap.put(PATH, ["**/coverage/**/report.xml"]);
    }


    // C0 minimum threshold
    int instruction = 0;
    // C1 minimum threshold
    int branch = 0;

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
    void xmlReports(String[] path) {
        xmlReportsMap.put(PATH, path);
    }
    // カバレッジレポート(report.xml)の場所
    void xmlReports(String entryDir, String[] path) {
        xmlReportsMap.put(ENTRY_DIR, entryDir);
        xmlReportsMap.put(PATH, path);
    }

    // 閾値を満たさなかった時にビルド失敗とするかどうか
    Boolean isBuildFailure = true;

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
