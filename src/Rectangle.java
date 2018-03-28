public class Rectangle
{
    public int length;
    public int width;

    public Rectangle(int len, int wid)
    {
        this.length = len;
        this.width  = wid;
    }

    public int getArea()
    {
        return length * width;
    }

    public int getPerimeter()
    {
        return 2 * (length + width);
    }
}
