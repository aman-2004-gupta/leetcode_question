import java.util.*;

class Solution {
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        // Map: "AB" -> list of possible top characters
        Map<String, List<Character>> map = new HashMap<>();

        for (String s : allowed) {
            String key = s.substring(0, 2);
            char top = s.charAt(2);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(top);
        }

        return dfs(bottom, map);
    }

    private boolean dfs(String bottom, Map<String, List<Character>> map) {
        // If only one block left, pyramid is complete
        if (bottom.length() == 1) return true;

        // Generate all possible next rows
        List<String> nextRows = new ArrayList<>();
        buildNextRow(bottom, 0, new StringBuilder(), nextRows, map);

        // Try each possibility
        for (String next : nextRows) {
            if (dfs(next, map)) return true;
        }

        return false;
    }

    private void buildNextRow(String bottom, int index,
                              StringBuilder current,
                              List<String> result,
                              Map<String, List<Character>> map) {

        if (index == bottom.length() - 1) {
            result.add(current.toString());
            return;
        }

        String key = "" + bottom.charAt(index) + bottom.charAt(index + 1);
        if (!map.containsKey(key)) return;

        for (char c : map.get(key)) {
            current.append(c);
            buildNextRow(bottom, index + 1, current, result, map);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
