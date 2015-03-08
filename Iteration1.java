/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamattendance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Charles
 
public class attendanceDriver {
        public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);
        String key = "?api_key=a43a679f-1c29-4e1c-9d74-6b2698b7481a";
        String IDCheckPathName = "/api/lol/na/v1.4/summoner/by-name/";
        
        System.out.print("Enter a summoner: ");
        String summoner = sc.nextLine();
        summoner = summoner.replace(" ", "%20");
        
        URL summonerIDCheck = new URL("https", "na.api.pvp.net", IDCheckPathName + summoner + key);

        BufferedReader in = new BufferedReader(new InputStreamReader(summonerIDCheck.openStream()));
        
        String IdInput = in.readLine();
        System.out.println(IdInput);
        
        int startIndex = IdInput.indexOf("id");
        int endIndex = getEndIndex(startIndex, IdInput);
        String summonerID = IdInput.substring(startIndex + 4, endIndex);
        
        URL gameCheck = new URL("https", "na.api.pvp.net", "/api/lol/na/v2.2/matchhistory/" + summonerID + key);
        in = new BufferedReader(new InputStreamReader(gameCheck.openStream()));
        
        System.out.print("Month/Day/Year: ");
        String date = sc.next();
        String[] splitDate = date.split("/");
        if (splitDate[2].length() == 2)
            splitDate[2] = "20" + splitDate[2];
        int year = Integer.parseInt(splitDate[2]) - 1900;
        int month = Integer.parseInt(splitDate[0]) - 1;
        int day = Integer.parseInt(splitDate[1]);
        
        Date startTimeCheck = new Date(year, month, day, 17, 0);
        Date endTimeCheck = new Date(year, month, day, 22, 0);
        Date gameTime;

        String gameTimeData = in.readLine();
        System.out.println(gameTimeData);
        endIndex = 0;
        for(int i = 0; i < 10; i++)
        {
            startIndex = gameTimeData.indexOf("matchCreation", endIndex);
            endIndex = getEndIndex(startIndex, gameTimeData);

            gameTime = new Date((Long.parseLong((gameTimeData.substring(startIndex + 15, endIndex)))));
            System.out.println(gameTime);
            System.out.println(startTimeCheck);
            System.out.println(endTimeCheck);
            if(gameTime.after(startTimeCheck) && gameTime.before(endTimeCheck))
                System.out.println("True!");
        }
  
        
        
    }
    
    public static int getEndIndex(int start, String string){
        int end = start;
        do{
            end++;
        } while(string.charAt(end) != ',');
        return end;
        }
    }

*/