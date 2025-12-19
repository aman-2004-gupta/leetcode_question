#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<int> findAllPeople(int n, vector<vector<int>>& meetings, int firstPerson) {
        sort(meetings.begin(), meetings.end(),
             [](auto &a, auto &b) { return a[2] < b[2]; });

        vector<bool> hasSecret(n, false);
        hasSecret[0] = true;
        hasSecret[firstPerson] = true;

        int i = 0;
        while (i < meetings.size()) {
            int time = meetings[i][2];

            unordered_map<int, int> parent;

            function<int(int)> find = [&](int x) {
                if (parent[x] == x) return x;
                return parent[x] = find(parent[x]);
            };

            auto unite = [&](int a, int b) {
                a = find(a);
                b = find(b);
                if (a != b) parent[b] = a;
            };

            vector<int> people;

            // collect all meetings at the same time
            while (i < meetings.size() && meetings[i][2] == time) {
                int x = meetings[i][0];
                int y = meetings[i][1];

                if (!parent.count(x)) parent[x] = x;
                if (!parent.count(y)) parent[y] = y;

                unite(x, y);
                people.push_back(x);
                people.push_back(y);
                i++;
            }

            // group members by root
            unordered_map<int, vector<int>> groups;
            for (int p : people) {
                groups[find(p)].push_back(p);
            }

            // spread secret if anyone in group has it
            for (auto &g : groups) {
                bool spread = false;
                for (int p : g.second) {
                    if (hasSecret[p]) {
                        spread = true;
                        break;
                    }
                }
                if (spread) {
                    for (int p : g.second)
                        hasSecret[p] = true;
                }
            }
        }

        vector<int> ans;
        for (int i = 0; i < n; i++)
            if (hasSecret[i]) ans.push_back(i);

        return ans;
    }
};
