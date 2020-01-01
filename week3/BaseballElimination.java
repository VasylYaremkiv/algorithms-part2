import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;


public class BaseballElimination {
    private final HashMap<String, Integer> teams;
    private final String[] teamsArray;

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
        this.teamsArray = new String[n];

        this.wins = new int[n];
        this.losses = new int[n];
        this.remaining = new int[n];
        this.against = new int[n][n];

        String[] splited;
        String line;
        for (int i = 0; i < n; i++) {
            line = in.readLine().trim();
            splited = line.split("\\s+");
            this.teams.put(splited[0], i);
            this.teamsArray[i] = splited[0];

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

        return this.certificateOfElimination(team) != null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        Bag<String> eliminationTeams = new Bag<String>();


        int teamWings = this.wins(team) + this.remaining(team);
        for (String t : this.teams()) {
            if (t != team && teamWings < this.wins(t)) {
                eliminationTeams.add(t);
            }
        }

        if (!eliminationTeams.isEmpty()) {
            return eliminationTeams;
        }

        int n = this.numberOfTeams();
        int gameNodes = (n - 1) * (n - 2) / 2;
        int total = 2 + (n - 1) + gameNodes;

        int s = 0, t = total - 1;
        int teamId = this.teamId(team);
        int gameNodesIndex = 1;
        int teamI = 0;
        int teamJ = 0;

        FlowNetwork G = new FlowNetwork(total);

        for (int i = 0; i < n; i++) {
            if (i == teamId) {
                continue;
            }

            teamJ = teamI + 1;
            for (int j = i + 1; j < n; j++) {
                if (j == teamId) {
                    continue;
                }
                G.addEdge(new FlowEdge(s, gameNodesIndex, this.against[i][j]));

                G.addEdge(new FlowEdge(gameNodesIndex, gameNodes + teamI + 1, Double.POSITIVE_INFINITY));
                G.addEdge(new FlowEdge(gameNodesIndex, gameNodes + teamJ + 1, Double.POSITIVE_INFINITY));

                gameNodesIndex++;
                teamJ++;
            }

            teamI++;
        }

        teamI = 0;

        for (int i = 0; i < n; i++) {
            if (i == teamId) {
                continue;
            }

            G.addEdge(new FlowEdge(gameNodes + teamI + 1, t, teamWings - this.wins[i]));
            teamI++;
        }
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        for (FlowEdge e : G.adj(t)) {
            if (maxflow.inCut(e.from())) {
                int tid = e.from() - gameNodes - 1;
                if (tid >= teamId) {
                    tid++;
                }
                eliminationTeams.add(this.teamsArray[tid]);
            }
        }

        if (eliminationTeams.isEmpty()) {
            return null;
        } else {
            return eliminationTeams;
        }
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
