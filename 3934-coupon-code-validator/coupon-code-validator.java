import java.util.*;

class Solution {
    public List<String> validateCoupons(String[] code,
                                        String[] businessLine,
                                        boolean[] isActive) {

        Map<String, Integer> order = new HashMap<>();
        order.put("electronics", 0);
        order.put("grocery", 1);
        order.put("pharmacy", 2);
        order.put("restaurant", 3);

        List<Coupon> valid = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {

            if (!isActive[i]) continue;
            if (!order.containsKey(businessLine[i])) continue;
            if (code[i].isEmpty()) continue;

            boolean ok = true;
            for (char c : code[i].toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '_') {
                    ok = false;
                    break;
                }
            }
            if (!ok) continue;

            valid.add(new Coupon(order.get(businessLine[i]), code[i]));
        }

        Collections.sort(valid, (a, b) -> {
            if (a.priority != b.priority)
                return a.priority - b.priority;
            return a.code.compareTo(b.code);
        });

        List<String> result = new ArrayList<>();
        for (Coupon c : valid) {
            result.add(c.code);
        }

        return result;
    }

    static class Coupon {
        int priority;
        String code;

        Coupon(int priority, String code) {
            this.priority = priority;
            this.code = code;
        }
    }
}
