import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.HashMap;

public class BaseballElimination {
    private final HashMap<String, Integer> teams;

    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;


    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        in.readLine();

        this.teams = new HashMap<String, Integer>();

        this.wins = new int[n];
        this.losses = new int[n];
        this.remaining = new int[n];
        this.against = new int[n][n];

        String[] splited;
        for (int i = 0; i < n; i++) {
            splited = in.readLine().split("\\s+");
            this.teams.put(splited[0], i);

            this.wins[i] = Integer.parseInt(splited[1]);
            this.losses[i] = Integer.parseInt(splited[2]);
            this.remaining[i] = Integer.parseInt(splited[3]);

            for (int j = 0; j < n; j++) {
                this.against[i][j] = Integer.parseInt(splited[4 + j]);
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return this.teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return this.teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return this.wins[this.teamId(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return this.losses[this.teamId(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return this.remaining[this.teamId(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!this.teams.containsKey(team1)) {
            throw new IllegalArgumentException();
        }
        if (!this.teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }

        return this.against[this.teamId(team1)][this.teamId(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        Bag<String> eliminationTeams = new Bag<String>();

        return eliminationTeams;
    }

    private int teamId(String team) {
        return this.teams.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {
            StdOut.print(team + " " + division.wins(team) + "W " + division.losses(team) + "L " + division.remaining(team) + "G ");
            for (String t : division.teams()) {
                StdOut.print(division.against(team, t) + " ");
            }
            StdOut.println();
        }

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
