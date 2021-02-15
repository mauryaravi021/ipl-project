import java.util.*;
import java.io.*;

public class TestIPL {

    private static final int TOTAL_RUNS = 17;
    private static final int EXTRA_RUNS = 16;
    private static final int BATS_MAN_RUNS = 15;
    private static final int PENALTY_RUNS = 14;
    private static final int NO_BALL_RUNS = 13;
    private static final int LEG_BY_RUNS = 12;
    private static final int BY_RUNS = 11;
    private static final int WIDE_RUNS = 10;
    private static final int IS_SUPER_OVER = 9;
    private static final int BOWLER = 8;
    private static final int NON_STRICKER = 7;
    private static final int BATSMAN = 6;
    private static final int BALL = 5;
    private static final int OVER = 4;
    private static final int BOWLING_TEAM = 3;
    private static final int BATTING_TEAM = 2;
    private static final int INNING = 1;
    private static final int MATCH_ID = 0;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_ZERO = 0;
    private static final int VENUE = 14;
    private static final int PLAYER_OF_MATCH = 13;
    private static final int WIN_BY_WICKETS = 12;
    private static final int WIN_BY_RUNS = 11;
    private static final int WINNER = 10;
    private static final int DL_APPLIED = 9;
    private static final int RESULT = 8;
    private static final int TOSS_DECISION = 7;
    private static final int TOSS_WINNWE = 6;
    private static final int TEAM2 = 5;
    private static final int TEAM1 = 4;
    private static final int DATE = 3;
    private static final int CITY = 2;
    private static final int SEASON = 1;
    private static final int ID = 0;

    public static void main(String[] args) throws Exception {

        // reading matches data
        List<Matches> matches = getMatchesData();

        // reading deliveries data
        List<Deliveries> deliveries = getDelivriesData();

        // Que -1 : Number of matches played per year of all the years in IPL.
        findMatchesPlayedPerYear(matches);

        // Que -2 : Number of matches won of all teams over all the Years in ipl
        findNumberOfMatchesWonByTeamOverAllYear(matches);

        // Que -3 : find extra runs conceded per team in 2016
        findExtraRunsConcededPerTeamIn2016(matches, deliveries, "2016");

        // Que -3 : find top economical bowler in 2015
        findTopEconomicalBowlerIn2015(matches, deliveries, "2015");

    }

    public static void findMatchesPlayedPerYear(List<Matches> matches) {

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        ListIterator ltr = matches.listIterator();
        while (ltr.hasNext()) {
            Matches match = (Matches) ltr.next();
            String season = match.getSeason();
            if (map.containsKey(season)) {
                int noOfMatches = map.get(season);
                map.put(season, noOfMatches + 1);
            } else {
                map.put(season, 1);
            }
        }
        System.out.println("Number of matches played per year of all the years in IPL.");
        System.out.println(map);
    }

    public static void findNumberOfMatchesWonByTeamOverAllYear(List<Matches> matches) {

        HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
        ListIterator ltr = matches.listIterator();
        while (ltr.hasNext()) {
            Matches match = (Matches) ltr.next();
            String season = match.getSeason();
            String winner = match.getWinner();
            if (map.containsKey(season)) {
                HashMap innerMap = map.get(season);
                if (innerMap.containsKey(winner)) {
                    int winningMatch = (Integer) innerMap.get(winner);
                    innerMap.put(winner, winningMatch + 1);
                } else {
                    innerMap.put(winner, 1);
                }
            } else {
                HashMap<String, Integer> hm = new HashMap<String, Integer>();
                hm.put(winner, 1);
                map.put(season, hm);
            }
        }
        System.out.println("Number of matches won of all teams over all the Years in ipl.");
        System.out.println(map);
    }

    public static void findExtraRunsConcededPerTeamIn2016(List<Matches> matches, List<Deliveries> deliveries, String year) {

        ArrayList<Integer> matchOfYear = findMatchIdOfYear(matches, year);

        int firstMatchIdOfYear = matchOfYear.get(0);
        int lastMatchIdOfYear = matchOfYear.get(1);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ListIterator ltr = deliveries.listIterator();
        while (ltr.hasNext()) {

            Deliveries delivery = (Deliveries) ltr.next();

            int match_id = Integer.parseInt(delivery.getMatchId());
            String Batting_team = delivery.getBattingTeam();
            int extra_run = Integer.parseInt(delivery.getExtraRuns());
            if (match_id >= firstMatchIdOfYear && match_id <= lastMatchIdOfYear) {
                if (map.containsKey(Batting_team)) {
                    int extraRunValue = map.get(Batting_team) + extra_run;
                    map.put(Batting_team, extraRunValue);
                } else {
                    map.put(Batting_team, extra_run);
                }
            }
        }
        System.out.println("find extra runs conceded per team in 2016.");
        System.out.println(map);
    }

    public static ArrayList<Integer> findMatchIdOfYear(List<Matches> matches, String year) {

        ListIterator ltr = matches.listIterator();
        int countMatches = 0;
        String matchId = null;
        while (ltr.hasNext()) {

            Matches match = (Matches) ltr.next();
            String season = match.getSeason();

            if (season.equals(year)) {
                matchId = match.getId();
                countMatches += 1;
            }
        }
        int lastMatchIdOfYear = Integer.parseInt(matchId);
        int firstMatchIdOfYear = lastMatchIdOfYear - countMatches + 1;

        ArrayList<Integer> al = new ArrayList<Integer>();
        al.add(firstMatchIdOfYear);
        al.add(lastMatchIdOfYear);
        return al;
    }

    public static void findTopEconomicalBowlerIn2015(List<Matches> matches, List<Deliveries> deliveries, String year) {

        HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        ListIterator ltr = deliveries.listIterator();

        ArrayList<Integer> matchOfYear = findMatchIdOfYear(matches, year);
        int firstMatchIdOfYear = matchOfYear.get(0);
        int lastMatchIdOfYear = matchOfYear.get(1);

        while (ltr.hasNext()) {

            Deliveries delivery = (Deliveries) ltr.next();

            int match_id = Integer.parseInt(delivery.getMatchId());
            String bowler = delivery.getBowler();
            int total_run = Integer.parseInt(delivery.getTotalRuns());
            if (match_id >= firstMatchIdOfYear && match_id <= lastMatchIdOfYear) {
                if (map.containsKey(bowler)) {
                    ArrayList<Integer> run_ball = map.get(bowler);
                    run_ball.set(INDEX_ZERO, run_ball.get(INDEX_ZERO) + total_run);
                    run_ball.set(INDEX_ONE, run_ball.get(INDEX_ONE) + 1);
                } else {
                    ArrayList<Integer> run_ball = new ArrayList<Integer>();
                    run_ball.add(INDEX_ZERO, total_run);
                    run_ball.add(INDEX_ONE, 1);
                    map.put(bowler, run_ball);
                }
            }
        }
        HashMap<String, Float> hm = new HashMap<String, Float>();
        Set<String> bowlers = map.keySet();
        for (String bowler : bowlers) {
            ArrayList<Integer> list = map.get(bowler);
            float economy = (list.get(INDEX_ZERO) / (float) list.get(INDEX_ONE)) * 6; // calculating economy
            economy = Math.round(economy * 100.0) / 100.0f; // rounding 2 place of decimal
            hm.put(bowler, economy);
        }
        hm = doSortByEconomy(hm);
        System.out.println("find top economical bowler in 2015");
        System.out.println(hm);
    }

    public static HashMap<String, Float> doSortByEconomy(HashMap<String, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Float>> list = new LinkedList<Map.Entry<String, Float>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static List<Matches> getMatchesData() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("csv data/matches.csv"));
        String line = null;

        List<Matches> matches = new ArrayList<Matches>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (str[ID].equals("id") == false) {            // to avoid reading header data

                Matches match = new Matches();

                match.setId(str[ID]);
                match.setSeason(str[SEASON]);
                match.setCity(str[CITY]);
                match.setDate(str[DATE]);
                match.setTeam1(str[TEAM1]);
                match.setTeam2(str[TEAM2]);
                match.setToss_winner(str[TOSS_WINNWE]);
                match.setToss_decision(str[TOSS_DECISION]);
                match.setResult(str[RESULT]);
                match.setDl_applied(str[DL_APPLIED]);
                match.setWinner(str[WINNER]);
                match.setWin_by_runs(str[WIN_BY_RUNS]);
                match.setWin_by_wickets(str[WIN_BY_WICKETS]);
                match.setPlayer_of_match(str[PLAYER_OF_MATCH]);
                match.setVenue(str[VENUE]);
                // match.setUmpire1(str[15]);
                // match.setUmpire2(str[16]);
                // match.setUmpire3(str[17]);

                matches.add(match);
            }

        }
        br.close();
        return matches;
    }

    private static List<Deliveries> getDelivriesData() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("csv data/deliveries.csv"));
        String line = null;

        List<Deliveries> deliveries = new ArrayList<Deliveries>();

        while ((line = br.readLine()) != null) {

            String str[] = line.split(",");
            if (str[0].equals("match_id") == false) {

                Deliveries delivery = new Deliveries();

                delivery.setMatchId(str[MATCH_ID]);
                delivery.setInning(str[INNING]);
                delivery.setBattingTeam(str[BATTING_TEAM]);
                delivery.setBowlingTeam(str[BOWLING_TEAM]);
                delivery.setOver(str[OVER]);
                delivery.setBall(str[BALL]);
                delivery.setBatsman(str[BATSMAN]);
                delivery.setNonStricker(str[NON_STRICKER]);
                delivery.setBowler(str[BOWLER]);
                delivery.setIsSuperOver(str[IS_SUPER_OVER]);
                delivery.setWideRuns(str[WIDE_RUNS]);
                delivery.setByeRuns(str[BY_RUNS]);
                delivery.setLegByeRus(str[LEG_BY_RUNS]);
                delivery.setNoBallRuns(str[NO_BALL_RUNS]);
                delivery.setPenaltyRuns(str[PENALTY_RUNS]);
                delivery.setBatsManRun(str[BATS_MAN_RUNS]);
                delivery.setExtraRuns(str[EXTRA_RUNS]);
                delivery.setTotalRuns(str[TOTAL_RUNS]);
                // delivery.setPlayerDismissed(str[18]);
                // delivery.setDismissalKind(str[19]);
                // delivery.setFilder(str[20]);

                deliveries.add(delivery);
            }
        }
        br.close();
        return deliveries;
    }

}
