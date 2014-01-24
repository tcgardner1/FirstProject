/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentflashcard;

import java.util.ArrayList;

/** Contains enums for points.
 *
 * @author Neel
 */
public class PointEnums {
    public enum Rank{
        _01_KID         ("Kid",         0,      "a"),
        _02_STUDENT     ("Student",     50,     "a"),
        _03_NOVICE      ("Novice",      100,    "a"),
        _04_AMATEUR     ("Amateur",     250,    "an"),
        _05_JOURNEYGOAT ("Journeygoat", 500,    "a"),
        _06_PRODIGY     ("Prodigy",     1000,   "a"),
        _07_GENIUS      ("Genius",      2000,   "a"),
        _08_ARCHGOAT    ("Archgoat",    3000,   "an"),
        _09_MASTERMIND  ("Mastermind",  5000,   "a"),
        _10_STUD        ("Stud",        10000,  "a"),
        _11_CABRA       ("Cabra",       25000, "a"),
        _12_GOD         ("God",         100000,"a"),
        ;
        
        private String name;
        private int toReachLevel;
        private String article;
        
        /** Creates a point rank. As a person earns points, they move up the ranks.
         * 
         * @param name the name of the rank 
         * @param x how many CPoints are needed to reach this level.
         * @param article either 'a' or 'an'. This will sometimes be used in front of the name.
         */
        Rank(String name, int toReachLevel, String article){
            this.name = name;
            this.toReachLevel = toReachLevel;
            this.article = article;
        }
        
        public String getName(){
            return name;
        }
        
        /**
         * Returns the name, except it has 'a' or 'an' at the front.
         * @return the modified name ready to be plopped into a sentence
         */
        public String getNameWithArticle(){
            return article + " " + name;
        }
        
        /**
         * Returns getNameWithArticle(), except only the name is bolded.
         * @return the super-modified name.
         */
        public String getBoldedNameWithArticle(){
            return article + " <b>" + name + "</b>";
        }
        
        public int getPointsToReachLevel(){
            return toReachLevel;
        }
        
        @Override
        public String toString(){
            return getBoldedNameWithArticle();
        }
        
        /**
         * Returns the rank directly above this one.
         * @return the next rank.
         */
        public Rank nextRank(){
            int ordinal = this.ordinal(); //what number rank this is (0, 1, 2)
            if(ordinal == Rank.values().length - 1){
                //last rank, nothing above
                //<TODO>: make special case for this
                return Rank._12_GOD;
            }
            return Rank.values()[ordinal + 1];
        }
        
        /** Finds which rank you belong to if you have the given amount of points.
         * 
         * @param points
         * @return your rank
         */
        public static Rank getRank(int points){
            int length = Rank.values().length;
            for(int i=length-1; i>0; i--){
                Rank rank = Rank.values()[i];
                if(points >= rank.getPointsToReachLevel())
                    return rank;
            }
            
            return Rank.values()[0];
        }
    }
    
    public enum Coin{
        BRONZE      ("coin-bronze.png",     1,  9),
        SILVER      ("coin-silver.png",     10, 24),
        GOLD        ("coin-gold.png",       25, 99),
        GOLDSTACK   ("coin-goldstack.png",  100,9999),
        ;
        
        private String imageName;
        private int minPoints;
        private int maxPoints;
        
        /** A coin is used to represent coin earnings.
         * Earnings of a certain range are associated with a certain coin.
         * 
         * @param imageName
         * @param minPoints
         * @param maxPoints 
         */
        Coin(String imageName, int minPoints, int maxPoints){
            this.imageName = imageName;
            this.minPoints = minPoints;
            this.maxPoints = maxPoints;
        }
        
        public String getImageName(){
            return imageName;
        }
        
        public javax.swing.ImageIcon getImageIcon(){
            return ImageManager.createImageIcon(imageName);
        }
        
        /**
         * Determines if earnings of a certain amount fall in this coin's range.
         * @param points how many points are earned
         * @return true if the earnings fall in this coin's range, false otherwise
         */
        public boolean isInRange(int points){
            return points >= minPoints && points <= maxPoints;
        }
        
        /**
         * Returns the coin appropriate for the given number of points.
         * @param points how many points are earned
         * @return the coin that fits for that point value.
         */
        public static Coin getCoin(int points){
            //just check if it's in the range of any coin
            for(Coin coin : Coin.values()){
                if(coin.isInRange(points))
                    return coin;
            }
            
            return Coin.GOLDSTACK;
        }
    }
    
    public enum Activity{
        STUDY_CORRECT   (1,     "Study a card and get it right"),
        CREATE_CARD     (2,     "Create a flashcard"),
        ADD_IMAGE       (3,     "Add an image to a flashcard"),
        CREATE_NOTE     (3,     "Create a note"),
        EXPORT_PROJECT  (5,     "Export a project"),
        RANK_E          (5,     "Get a card to rank E"),
        PRINT_CARDS     (10,    "Print out a project's flashcards"),
        CREATE_PROJECT  (10,    "Create a project"),
        //PERFECT_SESS_10 (15,    "Get all cards correct in a study session (min. 10)"),
        IMPORT_PROJECT  (20,    "Import a project"),
        //PERFECT_SESS_25 (40,    "Get all cards correct in a study session (min. 25)"),
        USE_NEW_VERSION (50,    "Use a new version of Cabra"),
        //PERFECT_SESS_50 (75,    "Get all cards correct in a study session (min. 50)"),
        USE_BETA        (100,   "Use a beta version of Cabra"), 
        GET_LUCKY       (100,   "Get lucky"),
     
        /* secret */
        CODE_FIRSTRUN   (20,    "???", true),
        CODE_HELP       (50,    "???", true),        
        CODE_SOCIAL     (100,   "???", true),        
        CODE_SECRET     (50,    "???", true),
        
        /* testing */
        ZERO            (0,     "0", true),
        ;
        
        private int points;
        private String description;
        private boolean secret;
        
        /**
         * An activity has a certain number of points allotted to it.
         * @param points how many points the activity is worth.
         * @param description a description of the achievement
         * @param secret true if the activity should not be shown on the "Points" tab.
         */
        Activity(int points, String description, boolean secret){
            this.points = points;
            this.description = description;
            this.secret = secret;
        }
        
        /**
         * Overload for Activity(int, String, boolean.) This activity is NOT secret.
         * @param points how many points the activity is worth.
         * @param description a description of the achievement
         */
        Activity(int points, String description){
            this(points, description, false);
        }
        
        public int getPoints(){
            return points;
        }
        
        public String getDescription(){
            return description;
        }
        
        public boolean isSecret(){
            return secret;
        }
        
        /**
         * Returns a list of all the activities that are not secret.
         * @return the list of activities
         */
        public static ArrayList<Activity> getNonSecretActivities(){
            ArrayList<Activity> list = new ArrayList<Activity>();
            for(Activity activity : values()){
                if(activity.isSecret() == false)
                    list.add(activity);
            }
            
            return list;
        }
    }
}
