import java.util.Arrays;
import java.util.List;
public class BinaryLifting
{
  int[][] up;
  List<List<Integer>> adj;
  int LOG;

  public BinaryLifting(List<List<Integer>> adj, int n)
  {
    this.adj = adj;
    LOG = (int) Math.ceil(Math.log(n) / Math.log(2));
    up = new int[n][LOG + 1];
    for(int[] i:up)
    {
      Arrays.fill(i, -1);
    }
    Build(0,-1);
  }

  public void Build(int node, int parent)
  {
    up[node][0] = parent;
    for (int i = 1; i <= LOG; i++)
    {
      if (up[node][i - 1] != -1)
        up[node][i] = up[up[node][i - 1]][i - 1];
    }
    for (int u : adj.get(node))
    {
      if (u != parent)
        Build(u, node);
    }
  }

  public int KthAnc(int node ,int k)
  {
    for (int i = 0; i <= LOG; i++)
    {
      if ((k & (1 << i)) > 0)
      {
        node = up[node][i];
        if (node == -1)
          break;
      }
    }
    return node;

  }



}