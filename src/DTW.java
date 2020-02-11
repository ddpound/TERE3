import java.io.*;

import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DTW {
	 ArrayList<String> user_x = new ArrayList<String>(); // 사용자
     ArrayList<String> user_y = new ArrayList<String>();
     ArrayList<String> bus_x = new ArrayList<String>(); // 버스
     ArrayList<String> bus_y = new ArrayList<String>();
     double[][] data;
     Double[][] befordata; // dtw알고리즘 돌아가기 전
     double value;

     public DTW(ArrayList user_x, ArrayList user_y, ArrayList bus_x, ArrayList bus_y) {
        this.user_x = user_x;
        this.user_y = user_y;
        this.bus_x = bus_x;
        this.bus_y = bus_y;
        this.data = dpDistance();
        this.value = dtwDistance();
     }
     
     private double[][] dpDistance() {
        double[][] data = new double[user_x.size()][user_x.size()];
        Double[][] befordata1 = new Double[user_x.size()][user_x.size()];
        int d = user_x.size();
        
        for(int i=0; i<d; i++) {
           for(int j=0; j<d; j++) {
             data[i][j] = Math.sqrt(EuclideanDistance(Double.parseDouble(user_x.get(i)) , Double.parseDouble(bus_x.get(j))) 
            		              + EuclideanDistance(Double.parseDouble(user_y.get(i)), Double.parseDouble( bus_y.get(j))));
              
            befordata1[i][j] = Math.sqrt(EuclideanDistance(Double.parseDouble(user_x.get(i)) , Double.parseDouble(bus_x.get(j))) 
            		               + EuclideanDistance(Double.parseDouble(user_y.get(i)), Double.parseDouble( bus_y.get(j))));
           }
        }
        
        befordata = befordata1;
        return data;
     }
     
     public double EuclideanDistance(double x, double y) {

        double result = 0;
        result = (x - y) * (x - y);
        
        return result;
     }

     public double dtwDistance() {
    	 /*
    	 double cost = 0;
    	 
    	 for(int i=0; i<user_x.size(); i++) {
             for(int j=0; j<user_x.size(); j++) {
                cost = data[i][j];
                data[i][j] = cost + Math.min(data[i+1][j], Math.min(data[i][j+1],data[i+1][j+1] ));
             }
        }
    	 
    	 
    	 
    	 
    	 return data[user_x.size()-1][user_x.size()-1];
    	 */
    	 
    	double cost = 0;
        
        for(int i=1; i<user_x.size(); i++) {
              data[0][i] = data[0][i] + data[0][i-1];
         }
         for(int i=1; i<user_x.size(); i++) {
              data[i][0] = data[i][0] + data[i-1][0];
         }
           
        for(int i=1; i<user_x.size(); i++) {
              for(int j=1; j<user_x.size(); j++) {
                 cost = data[i][j];
                 data[i][j] = cost + Math.min(data[i-1][j], Math.min(data[i][j-1],data[i-1][j-1] ));
              }
         }
        
        return data[user_x.size()-1][user_x.size()-1];
        
     }
     
     public void show() {
        for(int i=0; i<data.length;i++) {
           for(int j=0;j<data.length;j++) {
              System.out.print(Double.parseDouble(String.format("%.2f",data[i][j])) +" ");
              
           }
           System.out.println();
        }
        
     }
}
  
