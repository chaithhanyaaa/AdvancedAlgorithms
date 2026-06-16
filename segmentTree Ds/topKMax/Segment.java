import java.util.Arrays;
public class Segment
{
  public static class Node
  {
    int[] data;
    public Node(int k)
    {
      data=new int[k];
    }
  }
  int[] arr;
  Node[] segment;
  int k;
  Segment(int n,int k,int[] arr)
  {
    this.arr=arr;
    this.k=k;
    segment=new Node[4*n];
  }
 
  public Node merge(Node left, Node right)
  {
      Node ans = new Node(k);

      int i = 0, j = 0, index = 0;

      while(index < k && i < k && j < k)
      {
          if(left.data[i] > right.data[j])
          {
              ans.data[index++] = left.data[i++];
          }
          else
          {
              ans.data[index++] = right.data[j++];
          }
      }

      while(index < k && i < k)
      {
          ans.data[index++] = left.data[i++];
      }

      while(index < k && j < k)
      {
          ans.data[index++] = right.data[j++];
      }

      return ans;
  }

  public void Build(int index,int low,int high)
  {
    if(low==high)
    {
      segment[index]=new Node(k);
      Arrays.fill(segment[index].data,Integer.MIN_VALUE);
      segment[index].data[0]=arr[low];
      return;
    }
    int mid=(low+high)/2;
    Build(2*index+1,low,mid);
    Build(2*index+2,mid+1,high);
    segment[index]=merge(segment[2*index+1],segment[2*index+2]);
  }


  public Node Query(int index,int l,int r,int low,int high)
  {
    if(r<low || l>high)
    {
      Node ans=new Node(k);
      Arrays.fill(ans.data,Integer.MIN_VALUE);
      return ans;
    }
    if(l<=low && r>=high)
    {
      return segment[index];
    }
    int mid=(low+high)/2;
    Node left=Query(2*index+1,l,r,low,mid);
    Node right=Query(2*index+2,l,r,mid+1,high);
    return merge(left,right);
  }





}