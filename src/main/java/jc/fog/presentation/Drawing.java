package jc.fog.presentation;

import java.util.List;
import jc.fog.logic.Rectangle;

public class Drawing {  
    
    
    private static StringBuilder drawRectangles(List<Rectangle> rectangles)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(Rectangle rectangle:rectangles)
            stringBuilder.append(rectangle.toSvg());
            
        return stringBuilder;
    }
    
    private static String initializeSvg(int width, int height) // viewport
    {
        String svg = "<svg width=\"$1\" height=\"$2\" style=\"overflow:visible;\">$body</svg>";
        svg = svg.replace("$1", String.valueOf(width));
        svg = svg.replace("$2", String.valueOf(height));       
        return svg;
    }
    
    public static String drawSvg(List<Rectangle> rectangles, int svgWidth, int svgHeight)
    {
        //rectangles must check if it has a value greater than svgWidth & svgHeight.
        //HERE
        
        // Initialiser <svg> element.
        String svg = initializeSvg(svgWidth, svgHeight);
        // Saml <rect>'s for alle Rectangle objekter i samlingen.
        StringBuilder stringBuilder = drawRectangles(rectangles);
        // Returner <svg> med <rect>'s
        return svg.replace("$body", stringBuilder.toString());
    }
}
