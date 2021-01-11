import java.util.ArrayList;
public class TabuSearch {
  public static int[][] adj;
  public static ArrayList<Integer> s;
  public static int upperBound;

  public static int run(int[][] adj_matrix, int[] initialColoring, int maxIterations, int maxDepth, int upperbound, int lowerBound, double alpha, int A) {
    adj = adj_matrix;
    upperBound = upperbound;
    ArrayList<ArrayList<Integer>> tabuList = new ArrayList<ArrayList<Integer>>();
    int depth = 0;
    boolean stop = false;

    s = new ArrayList<Integer>();
    int k = 0;
    for(int i = 0; i < initialColoring.length; i++) {
      s.add(i, initialColoring[i]);

    }
    int result = upperBound;
    ArrayList<Integer> recordS = (ArrayList<Integer>) s.clone();
    ArrayList<ArrayList<Double>> tenureList = new ArrayList<ArrayList<Double>>();
    int conflictNumber = getNumberOfConflicts(s);
    while(conflictNumber != 0 && !stop) {
      k++;
      ArrayList<ArrayList<Integer>> neighbourSolutions = getNeighbouringSolutions(s, tabuList);
      ArrayList<Integer> move = new ArrayList<Integer>();
      if(neighbourSolutions.size() != 0) {
        move = getBestMove(neighbourSolutions);
      }
      else {
        //random move
        int i = ((int) (s.size() * Math.random()));
        int color = (int) (upperBound * Math.random());
        move.add(i);
        move.add(color);
      }
      s = generateSolution(move);
      int sCost = getCost(s);
      conflictNumber = getNumberOfConflicts(s);
      if(sCost < getCost(recordS)) {
        depth = 0;
        recordS = (ArrayList<Integer>) s.clone();
        result = getNumberOfColors(s);
      }
      else {
        depth++;
      }
      int randomNumber = ((int) (A * Math.random()))-1;
      double tabuTenure = alpha * sCost + randomNumber + (int) (depth/((float) maxDepth));
      ArrayList<Double> tenure = new ArrayList<Double>();
      tenure.add(tabuTenure);
      tenure.add(0.0);
      tenureList.add(tenure);
      tabuList.add(move);
      updateTabu(tabuList, tenureList);
      if(k > maxIterations || lowerBound == result) {
        if(k > maxIterations) {
          return -1;
        }
        stop = true;
        break;
      }
    }
    return result;
  }

  private static void updateTabu(ArrayList<ArrayList<Integer>> tabuList, ArrayList<ArrayList<Double>> tenureList) {
    ArrayList<ArrayList<Double>> tenureTmp = (ArrayList<ArrayList<Double>>) tenureList.clone();
    for(ArrayList<Double> i : tenureTmp) {
      double t = i.get(1);
      i.set(1, t+1);
      if(i.get(0) <= i.get(1)) {
        int index = tenureList.indexOf(i);
        tabuList.remove(tabuList.get(index));
        tenureList.remove(i);
      }
    }
  }
  private static int getNumberOfConflicts(ArrayList<Integer> s) {
    int sum = 0;
    for(int i = 0; i < adj.length; i++) {
      for(int j = i; j < adj.length; j++) {
        if(adj[i][j] == 1 && s.get(i) == s.get(j)) {
          sum++;
        }
      }
    }
    return sum;
  }
  private static ArrayList<ArrayList<Integer>> getNeighbouringSolutions(ArrayList<Integer> s, ArrayList<ArrayList<Integer>> tabuList) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    for(int i = 0; i < s.size(); i++) {
      for(int k = 0; k < upperBound; k++) {
        if(isValid(i, k, s)) {
          ArrayList<Integer> possibleMove = new ArrayList<Integer>();
          possibleMove.add(i);
          possibleMove.add(k);
          if(!tabuList.contains(possibleMove)) {
            result.add(possibleMove);
          }
        }
      }
    }
    return result;
  }

  private static ArrayList<Integer> getBestMove(ArrayList<ArrayList<Integer>> neighbourSolutionSet) {
    ArrayList<Integer> recordHolder = neighbourSolutionSet.get(0);
    int recordHolderSolutionCost = getCost(generateSolution(recordHolder));
    for(int i = 1; i < neighbourSolutionSet.size(); i++) {
      ArrayList<Integer> solution = generateSolution(neighbourSolutionSet.get(i));
      int solutionCost = getCost(solution);
      if(solutionCost < recordHolderSolutionCost) {
        recordHolder = neighbourSolutionSet.get(i);
        recordHolderSolutionCost = solutionCost;
      }
    }
    return recordHolder;
  }

  private static ArrayList<Integer> generateSolution(ArrayList<Integer> move) {
    int i = move.get(0);
    int k = move.get(1);
    ArrayList<Integer> result = (ArrayList<Integer>) s.clone();
    result.set(i, k);
    return result;
  }

  private static int getCost(ArrayList<Integer> s) {
    return getNumberOfColors(s);
  }
  private static int getNumberOfColors(ArrayList<Integer> s) {
    int[] coloring = new int[s.size()];
    for(int i = 0; i < coloring.length; i++) {
      coloring[i] = s.get(i);
    }
    return HelperFunctions.getMax(coloring)+1;
  }

  private static boolean isValid(int currV, int color, ArrayList<Integer> arr) {
    for(int i = 0; i < adj.length; i++) {
      if(adj[currV][i] == 1 && arr.get(i) == color) {
        return false;
      }
    }
    return true;
  }
}
