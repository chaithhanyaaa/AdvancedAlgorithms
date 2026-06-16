public class Segment
{
    class Node
    {
        int firstMax, secondMax;

        Node(int f, int s)
        {
            firstMax = f;
            secondMax = s;
        }
    }

    int[] arr;
    Node[] segment;

    Segment(int[] arr)
    {
        this.arr = arr;
        segment = new Node[4 * arr.length];
        build(0, 0, arr.length - 1);
    }

    private Node merge(Node left, Node right)
    {
        int firstMax = Math.max(left.firstMax, right.firstMax);

        int secondMax = Math.max(
                Math.min(left.firstMax, right.firstMax),
                Math.max(left.secondMax, right.secondMax)
        );

        return new Node(firstMax, secondMax);
    }

    public void build(int index, int low, int high)
    {
        if (low == high)
        {
            segment[index] = new Node(arr[low], Integer.MIN_VALUE);
            return;
        }

        int mid = (low + high) / 2;

        build(2 * index + 1, low, mid);
        build(2 * index + 2, mid + 1, high);

        segment[index] = merge(
                segment[2 * index + 1],
                segment[2 * index + 2]
        );
    }

    public Node query(int index, int low, int high, int l, int r)
    {
        if (r < low || l > high)
        {
            return new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }

        if (l <= low && high <= r)
        {
            return segment[index];
        }

        int mid = (low + high) / 2;

        Node left = query(
                2 * index + 1,
                low,
                mid,
                l,
                r
        );

        Node right = query(
                2 * index + 2,
                mid + 1,
                high,
                l,
                r
        );

        return merge(left, right);
    }
}