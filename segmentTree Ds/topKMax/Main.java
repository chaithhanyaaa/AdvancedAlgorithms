public class Main 
{
  public static void main(String[] args) 
  {
    int n=5;
    int k=3;
    Segment s=new Segment(n,k,new int[]{5,10,4,6,3});
    s.Build(0,0,n-1);
   
    Segment.Node ans=s.Query(0,1,3,0,4);
    System.out.println(ans.data[0]+" "+ans.data[1]+" "+ans.data[2]);

  }
  
}
