/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamattendance;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Charles
 */
public class attendanceDriver {
        public static void main(String[] args) throws Exception{
            TeamAttendance test = new TeamAttendance();
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Charles\\Desktop\\Games\\League\\NNNRoster.txt")); 
            String username;
            int counter = 0;
            while (counter < 9)
            {
                test.Attendance = false;
            username = in.readLine();
            test.getID(username);
            test.checkAttendance("yesterday");
            if(test.Attendance == true)
            {
                System.out.println(username);
            }
            counter++;
            if(counter % 5 == 0)
                Thread.sleep(10000);
            }
        }
}
