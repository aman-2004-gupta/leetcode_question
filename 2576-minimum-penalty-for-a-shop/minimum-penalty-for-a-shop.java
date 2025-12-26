class Solution {
    public int bestClosingTime(String customers) {
        int n = customers.length();

        // Count total 'Y' (penalty if shop closes at hour 0)
        int closedPenalty = 0;
        for (int i = 0; i < n; i++) {
            if (customers.charAt(i) == 'Y') {
                closedPenalty++;
            }
        }

        int openPenalty = 0;
        int minPenalty = closedPenalty;
        int bestHour = 0;

        // Try closing at hour j = 1 to n
        for (int j = 1; j <= n; j++) {
            char prevHour = customers.charAt(j - 1);

            if (prevHour == 'N') {
                openPenalty++;       // open but no customer
            } else {
                closedPenalty--;     // customer comes but shop is closed
            }

            int totalPenalty = openPenalty + closedPenalty;

            // Keep earliest hour with minimum penalty
            if (totalPenalty < minPenalty) {
                minPenalty = totalPenalty;
                bestHour = j;
            }
        }

        return bestHour;
    }
}
