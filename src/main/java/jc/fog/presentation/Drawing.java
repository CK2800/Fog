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
    
    private static String initializeSvg(int width, int height, int carportWidth, int carportHeight) // viewport
    {
        // Brug preserveAspectRatio attributten så tegningen centreres i midten af svg elementet uanset højde-vidde relation mellem svg og viewbox.
        String svg = "<svg width=\"$1\" height=\"$2\" viewBox=\"0 0 $3 $4\" preserveAspectRatio=\"xMidYMid meet\">$body</svg>";
        svg = svg.replace("$1", String.valueOf(width));
        svg = svg.replace("$2", String.valueOf(height));    
        svg = svg.replace("$3", String.valueOf(carportWidth));    
        svg = svg.replace("$3", String.valueOf(carportHeight));    
        return svg;
    }
    
    public static String drawSvg(List<Rectangle> rectangles, int svgWidth, int svgHeight, int carportWidth, int carportHeight)
    {
        //rectangles must check if it has a value greater than svgWidth & svgHeight.
        //HERE
        
        // Initialiser <svg> element.
        String svg = initializeSvg(svgWidth, svgHeight, carportWidth, carportHeight);
        // Saml <rect>'s for alle Rectangle objekter i samlingen.
        StringBuilder stringBuilder = drawRectangles(rectangles);
        // Returner <svg> med <rect>'s
        return svg.replace("$body", stringBuilder.toString());
    }
}
