/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculators;

import jc.fog.logic.RulesCalculator;
import jc.fog.logic.RulesDrawer;
import jc.fog.logic.Rules;
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.MaterialCount;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.Rectangle;
import jc.fog.logic.dto.ShedDTO;

/**
 * Udvidelse af RulesCalculator for udregning af stolper.
 * @author Claus
 */
public class RulesCalculatorPost extends RulesCalculator implements RulesDrawer
{    
    /**
     * Data vedr. spær materiale til tag, herunder hvor mange stk. træ pr. spær.
     */
    private MaterialCount mcRafters;
    /**
     * Data vedr. rem materiale til tag, herunder hvor mange stk. træ pr. rem.
     */
    private MaterialCount mcHeads;
    /**
     * Angiver antallet af remme i taget.
     */
    private int noHeads;
    
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {
        List<BillItem> result = new ArrayList();
        try
        {
            // Udregn materialeforbrug.
            MaterialCount materialCount = calculateMaterials(carportRequest);
            // Returner liste med bill items.
            result.add(new BillItem(materialCount.getMaterialDTO(), materialCount.getCount(), CarportPart.POST, "stolpetekst"));
            return result;                         
        }
        catch(Exception e)
        {
            throw new FogException("Stolper kunne ikke beregnes.", e.getMessage(), e);
        }
    }    

    @Override
    public List<Rectangle> draw(CarportRequestDTO carportRequest) throws FogException
    {
        // Udregn materialer først, som sættes på denne instans.
        calculateMaterials(carportRequest);
        // Placeholder for rektangler.
        rectangles = new ArrayList();
        // Hent skur fra forespørgsel.
        ShedDTO shed = carportRequest.getShedDTO();
        
        // Opret increments for matrice af stolper.        
        int widthSpacing = carportRequest.getWidth() / mcRafters.getCount(); /* widthSpacing angiver mellemrum mellem stolper i carportens vidde. 
                                                                                Under hvert spær brud (v. mcRafters.getCount() > 1) skal være en rem. 
                                                                                Under hver rem skal stå en stolpe i start, slut og v. hvert rem brud. */

        int lengthSpacing = (carportRequest.getLength() - 2 * Rules.OVERHANG) / mcHeads.getCount(); /* lengthSpacing angiver mellemrum stolper i carportens længde.
                                                                                                       Under hvert rem brud (v. mcHeads.getCount() > 1) skal være en stolpe. */
        
        // Stolpens halve godstykkelse bruges til at rykke stolpen korrekt under rem mv.
        int halfPostHeight = (int)Math.ceil(POST_HEIGHT/2);
        // Udregn positioner for stolper på tegningen.
        for(int x = 0; x <= mcRafters.getCount(); x++)
        {            
            // Udregn x positioner for stolper, x * mellemrum - halv godstykkelse.
            int xPos = x * widthSpacing - (int)Math.ceil(POST_HEIGHT/2);
            if (x == 0) // Første stolpe rykkes ind med udhængets længde, så stolpe står under rem.
                xPos += Rules.OVERHANG;
            if (x == mcRafters.getCount()) // sidste stolpe placeres i carport vidde minus udhæng minus halv godstykkelse.
                xPos = carportRequest.getWidth() - Rules.OVERHANG - halfPostHeight;
            
            // Udregn y positioner.
            for(int y = 0; y <= mcHeads.getCount(); y++)
            {
                // y positioner følger mellemrum i carportens længderetning minus udhæng og halv godstykkelse.
                int yPos = Rules.OVERHANG + (y * lengthSpacing) - halfPostHeight;
                rectangles.add(new Rectangle(xPos, yPos, POST_HEIGHT, POST_HEIGHT, "ffaa44" ));
            }
        }
        
        
        // Har vi et skur og hvor bredt?
        if (shed != null)
        {
            if (shed.getWidth() != carportRequest.getWidth())
            {
                // Stolpe i skurets bagerste hjørne, udhæng gælder også for skuret.
                rectangles.add(new Rectangle(shed.getWidth(), Rules.OVERHANG - halfPostHeight, POST_HEIGHT, POST_HEIGHT, "55ddcc"));
            }
            // Stolper i skurets forside.
            rectangles.add(new Rectangle(Rules.OVERHANG - halfPostHeight, Rules.OVERHANG + shed.getLength() - halfPostHeight, POST_HEIGHT, POST_HEIGHT, "55ddcc"));            
            rectangles.add(new Rectangle(shed.getWidth(), Rules.OVERHANG + shed.getLength() - halfPostHeight, POST_HEIGHT, POST_HEIGHT, "55ddcc"));
            // Stolpe til dørhængsel.
            rectangles.add(new Rectangle(shed.getWidth() - Rules.DOOR_WIDTH, Rules.OVERHANG + shed.getLength() - halfPostHeight, POST_HEIGHT, POST_HEIGHT, "55ddcc"));
        }
        
        
        return rectangles;
    }
    
    /**
     * Udregner antallet af stolper for carporten.
     * POST:
     * this.mcRafters, som angiver materiale samt antal pr. spær, er sat.
     * this.mcHeads, som angiver materiale samt antal pr. rem, er sat.
     * this.noHeads, som angiver antal remme, er sat.
     * @param carportRequest
     * @return
     * @throws FogException 
     */
    public MaterialCount calculateMaterials(CarportRequestDTO carportRequest) throws FogException
    {        
        // Find materialer.       
        List<MaterialDTO> posts = materials.get(Materialtype.POST.name());         
        List<MaterialDTO> rafters;
        if (carportRequest.getSlope() > 0)
        {
            // Byg selv spær - skal ej understøttes, skæres ej over, derfor kun 1 materiale til at dække bredden.
            rafters = materials.get(Materialtype.PRE_FAB_RAFTERS.name()); 
            mcRafters = new MaterialCount(1, rafters.get(0));
        }
        else
        {
            rafters = materials.get(Materialtype.RAFTERS.name()); // spær til fladt tag.        
            mcRafters = findShortest(rafters, carportRequest.getWidth()); // Antal træ til 1 spær.
        }
        
        mcHeads = findShortest(materials.get(Materialtype.RAFTERS.name()), carportRequest.getLength()); // Antal træ til 1 rem.
        
        MaterialDTO post = posts.get(0);
        noHeads = 2; // 2 remme som minimum.
        int noPostsPerHead = 2; // 2 stolper pr. rem som minimum.
        int count = 0; // antal stolper.

        // Tjek antal brud på rem (count > 1) og læg til stolpeantal pr. rem.
        noPostsPerHead += (mcHeads.getCount()-1);

        // Kun v. fladt tag skal vi tjekke for brud på spær.
        if (carportRequest.getSlope()==0)
        {
            // Læg ekstra rem til for hvert brud på spær (count > 1).
            noHeads += (mcRafters.getCount()-1);
        }

        // antal stolper er antal remme * stolper/rem.
        count = noHeads * noPostsPerHead;            

        // Skal der bygges skur, kræves flere stolper.
        ShedDTO shed = carportRequest.getShedDTO();
        if (shed != null)
        {            
            // Er skuret i fuld bredde?
            if (shed.getWidth() == carportRequest.getWidth())
                count += Rules.POSTS_SHED_FULL_WIDTH;
            else
                count += Rules.POSTS_SHED;                
        }
        return new MaterialCount(count, post);        
    }
}
