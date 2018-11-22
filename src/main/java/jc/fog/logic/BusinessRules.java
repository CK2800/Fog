/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

/**
 * Statiske konstanter til brug ved udregninger af styklisten.
 * @author Claus
 */
public class BusinessRules
{
    protected static int OVERHANG = 30;
    protected static float BATTENS_SPACING = 30F;
    protected static int POSTS_SHED_FULL_WIDTH = 3;
    protected static int POSTS_SHED = 4;
    protected static float RAFTER_SPACING_SLOPED_ROOF = 89F;
    protected static float RAFTER_SPACING = 55F;
    protected static int PLANK_SPACING = 6;
    protected static int PLANK_WIDTH = 10; // bør hentes fra db.
    /**
     * Materiale type id for stolper.
     */
    protected static int POST_TYPE_ID = 5;
    /**
     * Materiale type id for spær træ.
     */
    protected static int HEAD_TYPE_ID = 4;
    /**
     * Materiale type id for byg selv spær.
     */
    protected static int PRE_FAB_RAFTERS_TYPE_ID = 23;
    /**
     * Materiale type id for lægter.
     */
    protected static int BATTENS_TYPE_ID = 6;
    
}
