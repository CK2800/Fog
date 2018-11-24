package jc.fog.logic;

/**
 *
 * @author Jespe
 */
public class Rectangle {
    
    public int x, y, height, width;
    public String color;

    public Rectangle(int x, int y, int height, int width, String color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
    }
    
    
    public String toSvg()
    {
        String rect = "<rect x=\"$1\" y=\"$2\" width=\"$3\" height=\"$4\" style=\"fill:none;stroke:#$5;stroke-width:5;\">";
        rect = rect.replace("$1", String.valueOf(this.x));
        rect = rect.replace("$2", String.valueOf(this.y));
        rect = rect.replace("$3", String.valueOf(this.width));
        rect = rect.replace("$4", String.valueOf(this.height));
        rect = rect.replace("$5", String.valueOf(this.color));
        rect += "</rect>";
        return rect;
    }
    
    
}
