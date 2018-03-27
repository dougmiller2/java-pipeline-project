public class Rectangulator
{
    public static void main(String[] args)
    {
        int len = Integer.parseInt(args[0]);
        int wid = Integer.parseInt(args[1]);
        
        Rectangle myRectangle = new Rectangle(len, wid);

        String output = String.format("\n*** Your Rectangle ***\n\nLength:    %d\nWidth:     %d\nArea:      %d\nPerimeter: %d\n\n",
          myRectangle.length,
          myRectangle.width,
          myRectangle.getArea(),
          myRectangle.getPerimeter()
        );

        System.out.println(output);
    }
}
