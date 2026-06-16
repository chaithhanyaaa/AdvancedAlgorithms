
class BinaryLifting
{
    int LOG;
    int[][] up;
    List<List<Integer>> adj;

    BinaryLifting(int n, List<List<Integer>> adj, int root)
    {
        this.adj = adj;

        LOG = (int)(Math.log(n) / Math.log(2)) + 1;
        //log(n) base 2 + 1 gives max j such that 2^j <= n

        up = new int[n + 1][LOG];

        for(int i = 0; i <= n; i++)
        {
            Arrays.fill(up[i], -1);
        }

        dfs(root, -1);
    }

    private void dfs(int node, int parent)
    {
        up[node][0] = parent;

        for(int j = 1; j < LOG; j++)
        {
            if(up[node][j - 1] != -1)
            {
              int a = up[node][j - 1];
              up[node][j]=up[a][j - 1];
            }
        }

        for(int child : adj.get(node))
        {
            if(child != parent)
            {
                dfs(child, node);
            }
        }
    }

    public int kthAncestor(int node, int k)
    {
        for(int j = 0; j < LOG; j++)
        {
            if((k & (1 << j)) != 0)
            {
                node = up[node][j];

                if(node == -1)
                    return -1;
            }
        }

        return node;
    }
}