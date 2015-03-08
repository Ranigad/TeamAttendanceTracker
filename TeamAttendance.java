/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamattendance;

import java.util.*;
import java.net.*;
import java.io.*;
import org.joda.time.DateTime;
/**
 *
 * @author Charles
 */
public class TeamAttendance {

    String key;
    long ID;
    Boolean Attendance;
    
    TeamAttendance(){
        key = "api_key=a43a679f-1c29-4e1c-9d74-6b2698b7481a";
        Attendance = false;
    }
    
    TeamAttendance(String key){
        this.key = "?api_key=" + key;
        Attendance = false;
    }   
    
    public void getID() throws Exception {
        
        Scanner sc = new Scanner(System.in);
        String IDCheckPathName = "/api/lol/na/v1.4/summoner/by-name/";
        
        System.out.print("Enter a summoner: ");
        String summoner = sc.nextLine();
        
        summoner = summoner.replace(" ", "%20");
        
        URL summonerIDCheck = new URL("https", "na.api.pvp.net", IDCheckPathName + summoner + "?" + key);

        BufferedReader in = new BufferedReader(new InputStreamReader(summonerIDCheck.openStream()));
        
        String IdInput = in.readLine();
        
        
        int startIndex = IdInput.indexOf("id");
        int endIndex = getEndIndex(startIndex, IdInput);
        String summonerID = IdInput.substring(startIndex + 4, endIndex);
        ID = Long.parseLong(summonerID);
    }
    
        public void getID(String name) throws Exception {
        
        String IDCheckPathName = "/api/lol/na/v1.4/summoner/by-name/";
        
        String summoner = name;
        
        summoner = summoner.replace(" ", "%20");
        
        URL summonerIDCheck = new URL("https", "na.api.pvp.net", IDCheckPathName + summoner + "?" + key);

        BufferedReader in = new BufferedReader(new InputStreamReader(summonerIDCheck.openStream()));
        
        String IdInput = in.readLine();
        
        int startIndex = IdInput.indexOf("id\":");
        int endIndex = getEndIndex(startIndex, IdInput);
        String summonerID = IdInput.substring(startIndex + 4, endIndex);
        ID = Long.parseLong(summonerID);
    }
    
    public void checkAttendance() throws Exception {
        URL gameCheck = new URL("https", "na.api.pvp.net", "/api/lol/na/v2.2/matchhistory/" + ID + "?beginIndex=0&endIndex=15&" + key);
       
        BufferedReader in = new BufferedReader(new InputStreamReader(gameCheck.openStream()));
        
        Date[] timeComparisons = getDates();
        Date gameTime;

        String gameTimeData = in.readLine();
        
        int endIndex = 0, startIndex;
        for(int i = 0; i < 15; i++)
        {
            startIndex = gameTimeData.indexOf("matchCreation", endIndex);
            endIndex = getEndIndex(startIndex, gameTimeData);

            gameTime = new Date((Long.parseLong((gameTimeData.substring(startIndex + 15, endIndex)))));
            if(gameTime.after(timeComparisons[0]) && gameTime.before(timeComparisons[1])) {
               // System.out.println("True!");
                Attendance = true;
            }
        }    
    }
    
    public void checkAttendance(String date) throws Exception {
        URL gameCheck = new URL("https", "na.api.pvp.net", "/api/lol/na/v2.2/matchhistory/" + ID + "?beginIndex=0&endIndex=15&" + key);
        BufferedReader in = new BufferedReader(new InputStreamReader(gameCheck.openStream()));
        
        Date[] timeComparisons = getDates(date);
        Date gameTime;

        String gameTimeData = in.readLine();
        
        int endIndex = 0, startIndex;
        for(int i = 0; i < 15; i++)
        {
            startIndex = gameTimeData.indexOf("matchCreation", endIndex);
            endIndex = getEndIndex(startIndex, gameTimeData);

            gameTime = new Date((Long.parseLong((gameTimeData.substring(startIndex + 15, endIndex)))));
            if(gameTime.after(timeComparisons[0]) && gameTime.before(timeComparisons[1])) {
                //System.out.println("True!");
                Attendance = true;
            }
        }    
    }
    
    public static int getEndIndex(int start, String string){
        int end = start;
        do{
            end++;
        } while(string.charAt(end) != ',');
        return end;
        }
    

    public Date[] getDates() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Month/Day/Year: ");
        String date = sc.nextLine();
        String[] splitDate;
        int[] theDate = new int[3];

        
        // Error checking

        
        if (date.compareToIgnoreCase("today") == 0)
        {
            DateTime today = new DateTime();
            theDate[0] = today.getMonthOfYear();
            theDate[1] = today.getDayOfMonth();
            theDate[2] = today.getYear();
        }
        else if (date.compareToIgnoreCase("yesterday") == 0)
        {
            DateTime today = new DateTime();
            theDate[0] = today.getMonthOfYear();
            theDate[1] = today.getDayOfMonth() - 1;
            theDate[2] = today.getYear();
        }
        else 
        {
            splitDate = date.split("/");
            if (splitDate[2].length() == 2)
                splitDate[2] = "20" + splitDate[2];
        
            theDate[2] = Integer.parseInt(splitDate[2]);
            theDate[1] = Integer.parseInt(splitDate[0]);
            theDate[0] = Integer.parseInt(splitDate[1]);
        }
        
        
        Date[] startEnd = {new Date(theDate[2] - 1900, theDate[0] - 1, theDate[1], 17, 0), 
            new Date(theDate[2] - 1900, theDate[0] - 1, theDate[1], 22, 0)};
        return startEnd;
}
    
    public Date[] getDates(String date) {
        String[] splitDate;
        int[] theDate = new int[3];

        if (date.compareToIgnoreCase("today") == 0)
        {
            DateTime today = new DateTime();
            theDate[0] = today.getMonthOfYear();
            theDate[1] = today.getDayOfMonth();
            theDate[2] = today.getYear();
        }
        else if (date.compareToIgnoreCase("yesterday") == 0)
        {
            DateTime today = new DateTime();
            theDate[0] = today.getMonthOfYear();
            theDate[1] = today.getDayOfMonth() - 1;
            theDate[2] = today.getYear();
        }
        else 
        {
            splitDate = date.split("/");
            if (splitDate[2].length() == 2)
                splitDate[2] = "20" + splitDate[2];
        
            theDate[2] = Integer.parseInt(splitDate[2]);
            theDate[1] = Integer.parseInt(splitDate[0]);
            theDate[0] = Integer.parseInt(splitDate[1]);
        }
        
        
        Date[] startEnd = {new Date(theDate[2] - 1900, theDate[0] - 1, theDate[1], 17, 0), 
            new Date(theDate[2] - 1900, theDate[0] - 1, theDate[1], 22, 0)};
        return startEnd;
}
}