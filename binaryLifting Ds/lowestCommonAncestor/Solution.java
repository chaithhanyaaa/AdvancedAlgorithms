public class Solution 
{

  public static int LowestCommonAncestor(BinaryLifting bl, int u, int v,int[] depth)
  {
    if (depth[u] < depth[v])
    {
      int temp = u;
      u = v;
      v = temp;
    }
    int diff = depth[u] - depth[v];
    u = bl.KthAnc(u, diff);
    if (u == v)
      return u;
    for (int i = bl.LOG; i >= 0; i--)
    {
      if (bl.up[u][i] != bl.up[v][i])
      {
        u = bl.up[u][i];
        v = bl.up[v][i];
      }
    }
    return bl.up[u][0];
   
  }

  

  
}
