import java.util.*;

class Solution {

    int ROWS, COLS;
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    // Check if we can cross on given day
    private boolean canCross(int day, int[][] cells) {
        int[][] grid = new int[ROWS][COLS];

        // Flood cells up to 'day'
        for (int i = 0; i < day; i++) {
            int r = cells[i][0] - 1;
            int c = cells[i][1] - 1;
            grid[r][c] = 1;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[ROWS][COLS];

        // Start BFS from top row
        for (int c = 0; c < COLS; c++) {
            if (grid[0][c] == 0) {
                queue.offer(new int[]{0, c});
                visited[0][c] = true;
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            if (r == ROWS - 1) return true;

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS &&
                    !visited[nr][nc] && grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    public int latestDayToCross(int row, int col, int[][] cells) {
        ROWS = row;
        COLS = col;

        int left = 0, right = cells.length;
        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canCross(mid, cells)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }
}
