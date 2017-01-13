package tech.xfyrewolfx.thegrid.apis;

/* ScoreboardAPI v2.1.1 by FireBreath15
 * 
 * It's pretty cruddy, but if you want to use it, be my guest.
 * 
 * (c) 2015 - 2017, All Rights Reserved
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {
	public static void giveScoreboard(Player p, String objective){
		if(!testIfNull(p.getScoreboard())){
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective(objective, "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			p.setScoreboard(board);
		}
	}
	
	public static void removeScoreboard(Player p){
		Scoreboard board = p.getScoreboard();
		if(testIfNull(p.getScoreboard())){
			for(Objective o : board.getObjectives()){
				o.unregister();
			}
			
			board.clearSlot(DisplaySlot.SIDEBAR);
			p.setScoreboard(board);
		}
	}
	
	public static void setScoreboard(Player p, Scoreboard board){
		p.setScoreboard(board);
	}
	
	public static void setScore(Player p, String target, int score){
		Scoreboard board = p.getScoreboard();
		if(testIfNull(p.getScoreboard())){
			for(Objective o : board.getObjectives()){
				Score s = o.getScore(target);
				s.setScore(score);
			}
		}
	}
	
	public static int getScore(Player p, String target){
		Scoreboard board = p.getScoreboard();
		int score = 0;
		
		if(testIfNull(p.getScoreboard())){
			for(Objective o : board.getObjectives()){
				Score s = o.getScore(target);
				score = s.getScore();
			}
		}
		
		return score;
	}
	
	private static boolean testIfNull(Scoreboard board){
		if(board != null){
			if(board.getObjectives().size() > 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
