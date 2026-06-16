class Segment
{
  int[] arr;
  int[] segment;
  Segment(int arr)
  {
    this.arr = new int[arr];
    this.segment = new int[4 * arr];
    build(0, arr, arr);

  }

  public void build(int index, int low, int high)
  {
    if(low == high)
    {
      segment[index] = arr[low];
    }
    else
    {
      int mid = (low + high) / 2;
      build(2 * index + 1, low, mid);
      build(2 * index + 2, mid + 1, high);
      segment[index] = Math.max(segment[2 * index + 1], segment[2 * index + 2]);
    }
  }


  public int query(int index, int low, int high, int l, int r)
  {
    if (r < low || high < l)
    {
      return 0;
    }
    if (l <= low && high <= r)
    {
      return segment[index];
    }
    int mid = (low + high) / 2;
    int p1 = query(2 * index + 1, low, mid, l, r);
    int p2 = query(2 * index + 2, mid + 1, high, l, r);
    return Math.max(p1, p2);
  }

  public void update(int index, int low, int high, int pos, int value)
  {
    if (low == high)
    {
      segment[index] = value;
    }
    else
    {
      int mid = (low + high) / 2;
      if (pos <= mid)
      {
        update(2 * index + 1, low, mid, pos, value);
      }
      else
      {
        update(2 * index + 2, mid + 1, high, pos, value);
      }
      segment[index] = Math.max(segment[2 * index + 1], segment[2 * index + 2]);
    }
  }
}