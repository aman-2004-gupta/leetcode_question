import java.util.*;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) return result;

        String[] mapping = {
            "",    "",    "abc", "def", "ghi",
            "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        backtrack(result, digits, mapping, 0, new StringBuilder());
        return result;
    }

    private void backtrack(List<String> result, String digits, String[] mapping,
                           int index, StringBuilder current) {

        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        String letters = mapping[digits.charAt(index) - '0'];
        for (char c : letters.toCharArray()) {
            current.append(c);
            backtrack(result, digits, mapping, index + 1, current);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
