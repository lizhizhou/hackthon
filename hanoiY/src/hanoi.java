public class hanoi   
{   
       
    void Move(char chSour,char chDest){   
        System.out.println("Move the top plate of "+chSour+"-->"+chDest);   
    }   
  
    void Hanoi(int n,char chA,char chB,char chC)   
    {   
        if(n==1)   
            Move(chA,chC);   
        else  
        {   
            Hanoi(n-1,chA,chC,chB);   
            this.Move(chA,chC);   
            Hanoi(n-1,chB,chA,chC);   
        }   
    }   
  
    public static void main(String[] args)   
    {   
        int n=Integer.parseInt(args[0]);   
        hanoi han=new hanoi();   
        han.Hanoi(n,'A','B','C');   
    }   
}  