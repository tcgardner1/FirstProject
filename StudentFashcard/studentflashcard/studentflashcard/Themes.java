/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentflashcard;

/**
 *
 * @author Neel
 */

import javax.swing.*;

import studentflashcard.PointEnums.Rank;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public enum Themes{
    //Cabra can have different themes... here they are
    
    /* Info on Nimbus color properties
     * control - background
     * nimbusBase - buttons, tabs, menubar
     * nimbusFocus - outline for clicked components
     * nimbusDisabledText - for disabled text and buttons
     */
    
    /** The default Nimbus coloring scheme
     * 
     */
    DEFAULT("Default","default.png", Rank._01_KID){ 
        @Override
        public void doColoring(){
            resetDefaults();      
        }
    },
    /** A rich, colorful theme with a red/green base
     * 
     */
    /*CHERRY("Cherry"){
        @Override
        public void doColoring(){
            resetDefaults();
            UIManager.put("control",ColorManager.createColor("C60707"));
            UIManager.put("nimbusBase",ColorManager.createColor("2A5103"));
            UIManager.put("nimbusDisabledText", ColorManager.createColor("565656")); //for disabled buttons
        }
    },*/
    
    /** A simple, light theme of white and light blue
     * 
     */
    SNOW("Snow","snow.png", Rank._01_KID){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("F9FBFB"));
                    UIManager.put("nimbusBase",ColorManager.createColor("3D3DFF"));
                }
    },
    
    /** Cherry returns! This time it's pink and red
     * 
     */
    CHERRY("Cherry","cherry.png", Rank._01_KID){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("FFC1C1"));
                    UIManager.put("nimbusBase",ColorManager.createColor("72000B"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("563D3D"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("99545B"));
                }  
    },       
    
    /** A smoky but brilliant theme with reds and oranges
     * 
     */
    DUSK("Dusk","dusk.png", Rank._02_STUDENT){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("998080"));
                    UIManager.put("nimbusBase",ColorManager.createColor("702900")); 
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("56463D"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("994A4A"));
                }
    },
    
    /** A fun, bright color scheme of sandy yellow and bright blue
     * 
     */
    BEACH("Beach","beach.png", Rank._02_STUDENT){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("FFFDAD"));
                    UIManager.put("nimbusBase",ColorManager.createColor("4AC0FF"));                     
                }
    },
    
    /** A dark, blue-and-gray theme.
     * 
     */
   
    RAIN("Rain","rain.png", Rank._03_NOVICE){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("699BD3"));
                    UIManager.put("nimbusBase",ColorManager.createColor("3A4668"));
                    UIManager.put("nimbusDisabledText", ColorManager.createColor("565656"));   
                    UIManager.put("nimbusFocus",ColorManager.createColor("2A3554"));
                }
    },
    
    /** A bright theme with greens and blues.
     * 
     */
    SPRING("Spring","spring.png", Rank._04_AMATEUR){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("C4FF93"));
                    UIManager.put("nimbusBase",ColorManager.createColor("4D92E6"));//4E8AFE
                }
    },
    
    /** A polished brown-and-caramel theme.
     * 
     */
    MOCHA("Mocha","mocha.png", Rank._05_JOURNEYGOAT){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("E3CE9B"));
                    UIManager.put("nimbusBase",ColorManager.createColor("662300"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("704D4D"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("9E3400"));
                }  
    },
   
    
   /* A soft purple theme.
     * 
     */
   LILAC("Lilac","purple-flower.png", Rank._06_PRODIGY){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("CFBCE2"));
                    UIManager.put("nimbusBase",ColorManager.createColor("3D1B63"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("544960"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("412165"));
                }
    },      
    
   /* A colorful orange-and-black (and some green) theme.
     * 
     */
   HALLOWEEN("Halloween","halloween.png", Rank._07_GENIUS){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("FF9C4C"));
                    UIManager.put("nimbusBase",ColorManager.createColor("3F3F3F"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("404040"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("006009"));
                }
    },     
   
   /** A nice blue theme.
    * 
    */
   UNDER_THE_SEA("Under The Sea","fish.png", Rank._08_ARCHGOAT){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("9EE8FF"));
                    UIManager.put("nimbusBase",ColorManager.createColor("1C3877"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("768096"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("BFE0FF"));
                }       
   },
   
   /** A dark theme with reds and blacks.
    * 
    */
   EVIL_GOAT("Evil Goat","evil-goat.png", Rank._09_MASTERMIND){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("E55754"));
                    UIManager.put("nimbusBase",ColorManager.createColor("652222"));
                    UIManager.put("nimbusDisabledText",ColorManager.createColor("636363"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("AF0000"));
                }       
   },   
   
   /*EPIC("Epic","goat20.png",Rank._12_GOD){
       
   },*/
   
    /** Makes Cabra a cyborg, albeit a good-looking one.
     * 
     */
    /*CHROME("Chrome","default.png", Rank._01_KID){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("D0DBE6"));
                    UIManager.put("nimbusBase",ColorManager.createColor("4D92E6"));
                }  
    },*/  
   
/*
    BETA("Beta","soap.png"){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("EAC2F3"));
                    UIManager.put("nimbusBase",ColorManager.createColor("75249E"));
                }  
    },   
    */
   
    
   /* A pinkish pastel theme.
     * 
     */
   /*SOAP("Soap","soap.png"){
        @Override
                public void doColoring(){
                    resetDefaults();
                    UIManager.put("control",ColorManager.createColor("FFF7F9"));
                    UIManager.put("nimbusBase",ColorManager.createColor("B53056"));//B53056
                    //UIManager.put("nimbusDisabledText",ColorManager.createColor("51343E"));
                    UIManager.put("nimbusFocus",ColorManager.createColor("FFA8C6"));
                }
    },    */
   
   
   
    /** More or less random colors (but the extreme/dark ones are omitted.)
     * 
     */
    RANDOM_COLORS("Random Colors","colors.png", Rank._06_PRODIGY){
        @Override
                public void doColoring(){
                    //choose random colors
                    Color control = Color.black;
                    Color nimbusBase = Color.black;
                    
                    while(ColorManager.sumOfrgbValues(control) <= 256*2)
                        control = ColorManager.randomColor(); //any sum under that is too dark/too extreme
                    
                    nimbusBase = ColorManager.randomColor(); //no matter how dark this is it's OK
                    
                    UIManager.put("control",control);
                    UIManager.put("nimbusBase",nimbusBase);
                    UIManager.put("nimbusFocus",ColorManager.randomColor());
                }
    },
    

    ///////////////////RANDOM THEME////////////////
    RANDOM("Random","questionorange.png", Rank._01_KID){
        @Override
                public void doColoring(){
                    resetDefaults();
                    Themes theme = randomTheme();
                    Themes.setTheme(theme);
                }
    },

    //Seasons
    SEASONS("Seasons","seasons.png", Rank._01_KID){
        @Override
                public void doColoring(){
                    Calendar now = Calendar.getInstance();
                    final int year = now.get(Calendar.YEAR);
                    
                    //compare to now to determine theme
                    Calendar spring = Calendar.getInstance();
                    spring.set(year, 3-1, 1); //March 1
                    
                    Calendar summerSolstice = Calendar.getInstance();
                    summerSolstice.set(year, 6-1, 21); //June 21
                    
                    Calendar fall = Calendar.getInstance();
                    fall.set(year, 9-1, 1); //September 1
                    
                    Calendar halloween = Calendar.getInstance();
                    halloween.set(year, 10-1, 31); //October 31
                    
                    //today's relation to a certain event
                    //on or before counts as yes; so:
                        //>0 to see if you're after
                        //<= 0 to see if you're before
                    
                    //think about it
                        //Nov to Feb - Snow (4 months)
                        //Mar to May - Spring (3 months)
                        //Jun to Aug - Beach (3 months)
                        //Sep to Oct - Halloween (2 months)
                    
                    Themes theme = Themes.DEFAULT;
                    if(now.compareTo(halloween) > 0 || now.compareTo(spring) <= 0){
                        //after halloween, before spring solstice
                        theme = Themes.SNOW;
                    }
                    else if(now.compareTo(fall) > 0){
                        //before halloween (necessarily), after fall solstice
                        theme = Themes.HALLOWEEN;
                    }
                    else if(now.compareTo(summerSolstice) > 0){
                        //before fall and halloween (necessarily), after summer solstice
                        theme = Themes.BEACH;
                    }
                    else if(now.compareTo(spring) > 0){
                        //before fall, summer, and halloween (necessarily), after spring solstice
                        theme = Themes.SPRING;
                    }
                    
                    Themes.setTheme(theme);
                }
    },    
    

    ;
    
    private static void resetDefaults(){
            UIManager.put("control",ColorManager.createColor("D0DBE6"));//d6d9df D0DBE6 C9D0DD
            UIManager.put("nimbusBase",ColorManager.createColor("386BA5"));//33628c 
            UIManager.put("nimbusFocus",ColorManager.createColor("73A4D1"));
            UIManager.put("nimbusDisabledText", ColorManager.createColor("8e8f91"));
          //  UIManager.put("TextArea.foreground",Color.black);
          //  UIManager.put("List.foreground",Color.black);        
    }
    
    //class-like stuff
    
    private static Themes currentTheme = Themes.DEFAULT; //doesn't really do anything... just signals the name of the active theme
    public static Themes getCurrentTheme() { return Themes.currentTheme; }
 
        
    /** Sets the current theme.
     * 
     * @param theme the new theme
     */
    public static void setTheme(Themes theme){
        currentTheme = theme;
        //activate current theme
        GUI.resetLookAndFeel(); //wipes the slate clean
        theme.doColoring();
        GUI.setNimbusLookAndFeel(); //puts nimbus back on  
    }
    
    private String name;
    private String imageIconPath;
    private ImageIcon image;
    private Rank rankToUnlock;
    
    /**
     * Creates a new Theme.
     * @param name the theme's name
     * @param imageIconPath the path to the image of the rank (usually "[name].png")
     * @param rankToUnlock the rank needed to unlock this rank
     */
    Themes(String name, String imageIconPath, Rank rankToUnlock){
        this.name = name;
        this.imageIconPath = imageIconPath;
        this.image = GUI.createImageIcon(imageIconPath);
        this.rankToUnlock = rankToUnlock;
    }
    
    /** Should be overridden, but it resets defaults
     * 
     */
    public void doColoring(){
        resetDefaults();
    }
    
    /**
     * Determines if this theme is available - based on the current theme.
     * @return true if the theme is available, false otherwise
     */
    public boolean isUnlocked(){
        //if the user's rank is as good or better than the needed, this is unlocked
        Rank myRank = PointManager.myRank;
        Rank neededRank = this.rankToUnlock;
        
        //compare ranks based on point requirements
        //if my rank's point requirement is higher than needed, then we our rank is more than enough
        if(myRank.getPointsToReachLevel() >= neededRank.getPointsToReachLevel()){
            return true;
        }
        else{
            //points for user's rank is less than needed points - so lower than needed rank. No dice.
            return false;
        }
    }
    
    /** Same thing as getName().
     * 
     * @return this theme's name
     */
    @Override
        public String toString(){ 
            return name; 
        }
    public String getName(){ 
        return name; 
    }
    
    public ImageIcon getImageIcon(){
        return image;
    }
    
    public String getImageIconPath(){
        return imageIconPath;
    }
    
    /** Returns a random theme
     * 
     * @return the random theme created
     */
    public static Themes randomTheme(){
       ArrayList<Themes> themes = getAvailableThemes(); //get all the themes
       while(true){
            int randomIndex = (int)(Math.random() * themes.size()); //get a random index from the array
            Themes newTheme = themes.get(randomIndex); //a new theme    
            //is the new theme the same as the old one?
            if(newTheme == currentTheme) continue; //try again
            return newTheme; //or just return the newly created theme
       }      
    }
    
    /** Finds all the themes available and returns them.
     * 
     * @return all the themes available to the user, in an arraylist
     */
    public static ArrayList<Themes> getAvailableThemes(){
        ArrayList<Themes> themes = new ArrayList<Themes>();
        
        for(Themes theme : Themes.values()){
            if(theme.isUnlocked())
                themes.add(theme);
        }
        
        return themes;
    }
    
    public static ArrayList<Themes> getAllThemes(){
        ArrayList<Themes> themes = new ArrayList<Themes>();
        themes.addAll(Arrays.asList(Themes.values()));
        return themes;
    }
    
    /** Turns the given string into the corresponding Themes.
     * 
     * @param theName the name of the theme you want to get
     * @return the Themes that was created
     */
    
    public static Themes getThemeByName(String theName){
        //returns the Themes value based on its name
        try{
            theName = theName.replace(' ', '_'); //replaces the spaces with underscores so that stuff like Evil Goat won't break
            return Themes.valueOf(theName.toUpperCase()); //if theName's "Cherry", it turns it to "CHERRY" and returns Themes.CHERRY
        }
        catch(IllegalArgumentException e){
            //no idea what the theme is, so return the default one
            return Themes.DEFAULT;
        }
    }
    
    /**
     * Determines which themes were just unlocked by growing to this rank
     * @param newRank the user's new rank
     * @return an ArrayList of the Themes just unlocked. Max size is 2.
     */
    public static ArrayList<Themes> getUnlockedThemes(Rank newRank){
        ArrayList<Themes> themes = new ArrayList<Themes>();
        for(Themes theme : getAvailableThemes()){
            if(theme.rankToUnlock == newRank){
                themes.add(theme);
            }
        }
        
        return themes;
    }
}
