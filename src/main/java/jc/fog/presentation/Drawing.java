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
    
    /**
     * Initialiserer SVG elementet.
     * @param width Kan angives i hele pixels eller som procent, em osv.
     * @param height Kan angives i hele pixels eller som procent, em osv.
     * @param carportWidth
     * @param carportLength
     * @return 
     */
    private static String initializeSvg(String width, String height, int carportWidth, int carportLength) // viewport
    {
        // Brug preserveAspectRatio attributten så tegningen centreres i midten af svg elementet uanset højde-vidde relation mellem svg og viewbox.
        String svg = "<svg width=\"$1\" height=\"$2\" viewBox=\"0 0 $3 $4\" preserveAspectRatio=\"xMidYMid meet\">$body</svg>";
        svg = svg.replace("$1", width);
        svg = svg.replace("$2", height);    
        svg = svg.replace("$3", String.valueOf(carportWidth));    
        svg = svg.replace("$4", String.valueOf(carportLength));    
        return svg;
    }
    
    public static String drawSvg(List<Rectangle> rectangles, String svgWidth, String svgHeight, int carportWidth, int carportLength)
    {        
        // Initialiser <svg> element.
        String svg = initializeSvg(svgWidth, svgHeight, carportWidth, carportLength);
        // Saml <rect>'s for alle Rectangle objekter i samlingen.
        StringBuilder stringBuilder = drawRectangles(rectangles);
        // Returner <svg> med <rect>'s
        return svg.replace("$body", stringBuilder.toString());
    }
}
