/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.Random;
import java.util.UUID;

/**
 * Statiske konstanter til brug ved udregninger af styklisten.
 * @author Claus
 */
public class Rules
{
    
    /** Udhæng i cm. */
    protected static final int OVERHANG = 30;
    /** Skurdørens vidde i cm. */
    protected static final int DOOR_WIDTH = 100;
    /** Mellemrum ml. lægter i cm. */
    protected static final float BATTENS_SPACING = 30F;
    /** Antal stolper v. skur i samme bredde som carport */
    protected static final int POSTS_SHED_FULL_WIDTH = 3;
    /** Antal stolper v. skur med mindre bredde end carport. */
    protected static final int POSTS_SHED = 4;
    /** Afstand ml. byg-selv spær. */
    protected static final float RAFTER_SPACING_SLOPED_ROOF = 89F;
    /** Afstand ml. spær, fladt tag. */
    protected static final float RAFTER_SPACING = 55F;
    /** Afstand ml. beklædningsbrædder, skur. */
    protected static final int PLANK_SPACING = 6;
    /** Beklædningsbrædders bredde i cm. */
    protected static final int PLANK_WIDTH = 10; // bør hentes fra db.
    /** Rem tykkelse i cm. */
    protected static final int HEAD_HEIGHT = 8;
    /** Stolpe tykkelse i cm. */
    protected static final int POST_HEIGHT = 20;    
    /** Teglstens bredde. */
    protected static final int ROOFTILE_WIDTH = 25; 
    /** Teglstens længde. */
    protected static final int ROOFTILE_LENGTH = 50;
    /** Rygstens længde. */
    protected static final int RIDGETILE_LENGTH = 50;
    /**
     * Samling af konstanter med materialetype id.
     * Bruges bl.a. til at opdele en List af MaterialDTO's i sub-lister med ens materialetype.
     * Eksempel: RAFTERS (4).     
     */
    protected static enum Materialtype
    {
        PLANKS (1), // Beklædningsplanker til skur.
        RAFTERS (4), // Materiale type id for spær træ.
        POST (5), // Materiale type id for stolper.
        BATTENS (6), // Materiale type id for lægter.
        SHEETING (7), // Materialetype id for tagfladebelægning.
        RIDGE (8), // Materialetype id for tagrygbelægning.
        PRE_FAB_RAFTERS (23); // Materiale type id byg selv spær.       
        
        private final int materialtypeId;
        
        private Materialtype(int materialtypeId)
        {
            this.materialtypeId = materialtypeId;
        }
        public int getMaterialtypeId(){return materialtypeId;}
    }
    
    /**
     * Enum som indikerer en given del af carporten.
     */
    protected static enum CarportPart
    {
        HEAD,
        RAFTERS,
        POST,
        BATTENS,
        PLANKS,
        PRE_FAB_RAFTERS,
        RIDGE,
        SHEETING
    }
    
    /**
     * Bruges til, at kun giv bruger et "user" ny adgangskode.
     * @return 
     */
    public static String RandomPassword()
    {
        Random rand = new Random();
        int max = rand.nextInt(10) + 5; //Hvis nextInt bliver "0" så vil den altid tilføj 5.
        
        String uniqueText = UUID.randomUUID().toString(); // 0f8fad5bd9cb-469f-a165-70867728950e
        return uniqueText.replace("-", "").substring(0, max);
    }
   
}
