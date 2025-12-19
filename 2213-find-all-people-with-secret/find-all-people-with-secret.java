import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);

        boolean[] hasSecret = new boolean[n];
        hasSecret[0] = true;
        hasSecret[firstPerson] = true;

        int i = 0;
        while (i < meetings.length) {
            int time = meetings[i][2];

            Map<Integer, Integer> parent = new HashMap<>();

            // Find with path compression
            java.util.function.IntUnaryOperator find = new java.util.function.IntUnaryOperator() {
                @Override
                public int applyAsInt(int x) {
                    if (parent.get(x) != x)
                        parent.put(x, applyAsInt(parent.get(x)));
                    return parent.get(x);
                }
            };

            // Union
            java.util.function.BiConsumer<Integer, Integer> union = (a, b) -> {
                int pa = find.applyAsInt(a);
                int pb = find.applyAsInt(b);
                if (pa != pb)
                    parent.put(pb, pa);
            };

            List<Integer> people = new ArrayList<>();

            // Collect all meetings at same time
            while (i < meetings.length && meetings[i][2] == time) {
                int x = meetings[i][0];
                int y = meetings[i][1];

                parent.putIfAbsent(x, x);
                parent.putIfAbsent(y, y);

                union.accept(x, y);
                people.add(x);
                people.add(y);
                i++;
            }

            // Group by root
            Map<Integer, List<Integer>> groups = new HashMap<>();
            for (int p : people) {
                int root = find.applyAsInt(p);
                groups.computeIfAbsent(root, k -> new ArrayList<>()).add(p);
            }

            // Spread secret
            for (List<Integer> group : groups.values()) {
                boolean spread = false;
                for (int p : group) {
                    if (hasSecret[p]) {
                        spread = true;
                        break;
                    }
                }
                if (spread) {
                    for (int p : group)
                        hasSecret[p] = true;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int p = 0; p < n; p++)
            if (hasSecret[p])
                result.add(p);

        return result;
    }
}
