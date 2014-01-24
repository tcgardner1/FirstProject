package studentflashcard;

import java.util.ArrayList;

import studentflashcard.PointEnums.Rank;

/** Manages "Points", which are earned by doing stuff in Cabra.
 *
 * @author Neel
 */
public class PointManager {
       
    public static Rank myRank;
    private int points;
    
    public PointManager(){
        //load points from UserData
        this.points = UserData.getInt("Points");
        setRank(getRank());
    }
    
    public int getPoints(){
        return points;
    }
    
    public Rank getRank(){
        return PointEnums.Rank.getRank(points);
    }
    
    private void setPoints(int points){
        Rank oldRank = getRank();
        this.points = points;
        Rank newRank = getRank();
        
        //change current rank
        setRank(newRank);
        
        //did you go up a rank?
        if(newRank != oldRank){
            rankUp(newRank);
        }
        
        //save it
        UserData.setString("Points", points + "");
    }
    
    /**
     * Called when the user gains a rank.
     * @param newRank the user's new rank.
     */
    private void rankUp(Rank newRank){
        //show message
        int points = newRank.getPointsToReachLevel();
        String text = "Congrats! You've accumulated " + points + " points, enough to become " + newRank + "!";       
        
        //was anything unlocked?
        ArrayList<Themes> unlockedThemes = Themes.getUnlockedThemes(newRank);
        if(unlockedThemes.size() == 1){
            text += "<br>You unlocked the <b>" + unlockedThemes.get(0) + "</b> theme!";
        }
        else if(unlockedThemes.size() == 2){
            text += "<br>You unlocked the <b>" + unlockedThemes.get(0) + 
                    "</b> and <b>" + unlockedThemes.get(1) + "</b> themes!";
        }
        else if(unlockedThemes.isEmpty()){
            //no action
        }
        
        text += "<br><br>Check out your progress in the Points tab.";         

        Utils.showDialog(null, 
                "<html><center>" + text,
                "Cabra Rank Up!");        
    }
    
    
    private void setRank(Rank rank){
        myRank = rank;
    }    
    
    /**
     * Adds points.
     * @param activity an activity that the user just did. 
     * @return true if the user gained a rank because of gaining these points
     */
    public boolean gainPoints(PointEnums.Activity activity){
        int pointsGained = activity.getPoints();
        
        //determine if rank changed
        Rank rankBefore = this.getRank();
        setPoints(points + pointsGained);
        Rank rankAfter = this.getRank();
        
        if(rankAfter != rankBefore)
            return true;
        else
            return false;
    }
}
