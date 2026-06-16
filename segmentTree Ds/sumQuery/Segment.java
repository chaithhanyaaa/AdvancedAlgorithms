class Segment{
    //range sum query
    int[] arr;
    int[] segment;

    Segment(int[] arr)
    {
      this.arr = arr;
      this.segment = new int[4*arr.length];
      buildTree(0,0,arr.length-1);
    }


    public void buildTree(int index,int low,int high)
    {
      if(low==high)
      {
        segment[index]=arr[low];
        return;
      }
      int mid = low + (high-low)/2;
      buildTree(2*index+1,low,mid);
      buildTree(2*index+2,mid+1,high);
      segment[index] = segment[2*index+1]+segment[2*index+2];   
    }

    public int query(int index,int low,int high,int l,int r)
    {
      if(r<low||l>high)
      {
        return 0;
      }
      if(l<=low&&r>=high)
      {
        return segment[index];
      }
      int mid = low + (high-low)/2;
      int left = query(2*index+1,low,mid,l,r);
      int right = query(2*index+2,mid+1,high,l,r);
      return left+right; 
    }


    public void update(int index,int low,int high,int pos,int val)
    {
      if(low==high)
      {
        arr[pos]=val;
        segment[index]=val;
        return;
      }
      int mid = low + (high-low)/2;
      if(pos<=mid)
      {
        update(2*index+1,low,mid,pos,val);
      }
      else
      {
        update(2*index+2,mid+1,high,pos,val);
      }
      segment[index] = segment[2*index+1]+segment[2*index+2];
    }
}