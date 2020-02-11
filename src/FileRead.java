import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class FileRead {
	String[] a;
    ArrayList<String> a_x = new ArrayList<String>(); // 버스 배열
    ArrayList<String> a_y = new ArrayList<String>();
    String[] b;
    ArrayList<String> b_x = new ArrayList<String>(); // 유저 배열
    ArrayList<String> b_y = new ArrayList<String>();
    
   
    
    double[] dtwValue ;
    double dtwResult = 3000.0; // 최종값 반환
    
	public void Read() {
		
		// 파일 경로를 가져와서 하드코딩 부분을 해결해보자
	 	   JOptionPane.showMessageDialog(null,"첫번쨰는 버스파일을 두번째는 유저파일을 넣으세요");
		   
		   JFileChooser chooser1 = new JFileChooser(); //객체 생성
		   JFileChooser chooser2 = new JFileChooser();
		   

		   int ret1 = chooser1.showOpenDialog(null);  //열기창 정의
		   int ret2 = chooser2.showOpenDialog(null);

		   
		 

		   if (ret1 != JFileChooser.APPROVE_OPTION) {

		    JOptionPane.showMessageDialog(null, "파일을 선택하지않았습니다.",

		      "경고", JOptionPane.WARNING_MESSAGE);
		    System.exit(0);
		    

		    return;

		   }
		   if (ret2 != JFileChooser.APPROVE_OPTION) {

			    JOptionPane.showMessageDialog(null, "파일을 선택하지않았습니다.",

			      "경고", JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			    return;

			   }

		   String filePath1 = chooser1.getSelectedFile().getPath();  //파일경로를 가져옴
		   String filePath2 = chooser2.getSelectedFile().getPath();
		
		     File busfile = new File(filePath1);
		     File userfile = new File(filePath2);

		     try {
		         BufferedReader busFiles
		         = new BufferedReader(new InputStreamReader(new FileInputStream(busfile.getAbsolutePath()), "UTF8"));
		 
		         BufferedReader userFiles
		         = new BufferedReader(new InputStreamReader(new FileInputStream(userfile.getAbsolutePath()), "UTF8"));
		         
		         String busline = "";
		         String userline = "";
		         
		         while((busline = busFiles.readLine()) != null) {
		             if(busline.trim().length() > 0) {
		                int i = 0;
		                
		                 a = busline.split(",");
		                 //a_x = new ArrayList(Arrays.asList(a[1]));
		                // System.out.println(Arrays.asList(a[1]));
		                 a_x.add(a[1]);
		                 a_y.add(a[2]); // 0번 배열이 시간 값 넣어놓은 곳
		                 i++; // 한줄 읽으면 카운트 값 1씩 올리기
		             }
		         }
		         while((userline = userFiles.readLine()) != null) {
		             if(userline.trim().length() > 0) {
		                int i = 0;
		                 b = userline.split(",");
		                 //b_x[i] = Double.parseDouble(b[1]);
		                 //b_y[i] = Double.parseDouble(b[2]);  
		                b_x.add(b[1]);
		                b_y.add(b[2]);
		                 i++;
		             }
		         }
		         
		         
		         busFiles.close();   
		         userFiles.close();
		     }
		     
		     
		     catch (Exception e) {
		         e.printStackTrace();
		     }
		     
		     

		}
	
	//크기만큼 자르고 확인한거
	public void test() {
		/*
		cut_x =  new double[b_x.size()];
		cut_y = new double[b_x.size()];
		
		int a_xLength = b_x.size();
		
		for(int i =0; i <= a_xLength-1; i++) {
			cut_x[i] = Double.parseDouble(a_x.get(i)) ;
			cut_y[i] =  Double.parseDouble(a_y.get(i)) ;
		
		}
		
		for(int i =0 ; i< cut_x.length-1;i++) {
			System.out.print(cut_x[i]+" ");
			
		}
		System.out.println();
		for(int j =0 ; j< cut_x.length-1;j++) {
			
			System.out.print(cut_y[j]+" ");
		}
		System.out.print("\n"+a_xLength+" ");
		*/
	}
	
	
	
	public void LastDTW() {
		
		// b_x,y 과 유저이고 a_x,y가 버스
		int a_xLength = b_x.size(); // 버스 값 자르기위한 유저 라인 사이즈 인트값, 이게 자른 배열의 크기값임
		int listStart = 0 ; // 첫 시작 인트값은 0 나중에 10씩 증가함
		int listLast = b_x.size(); //유저의 배열
		int count =0;
		ArrayList<Boolean> dtwR = new ArrayList<Boolean>();
		ArrayList<Double> dtwValue = new ArrayList<Double>();
		
		//b_x 유저 
		
		JFrame jframe3 = new JFrame();
		
		String[] columnNames = {"참,거짓","항목","DTW"};
		
		int whileCount=0;
		while(listLast < a_x.size()) { // 버스 배열의 크기보다 커지면 멈춤
	    
		ArrayList<String> cut_x = new ArrayList<String>();
	    ArrayList<String> cut_y = new ArrayList<String>();
		
	    for(int i =listStart; i <= listLast-1; i++) {
			cut_x.add(a_x.get(i)) ; // 버스 배열의 값
			cut_y.add(a_y.get(i)) ;
		}
		
	    //즉 cut 배열에 버스의 값을 넣습니다 DTW클래싀 생성자에
		DTW dtw = new DTW( b_x, b_y,cut_x, cut_y);
		
		if(Result(dtw.value)) {
			dtwValue.add(count, dtw.value);
			System.out.print(String.valueOf(dtwValue.get(whileCount))+" ");
			System.out.println(Result(dtw.value));		
			break;
		}
		else {
			// 표로 출력될 배열에 추가해줍니다
			listStart = listStart + 10;
			listLast = listLast + 10;
			dtwValue.add(count, dtw.value); // 표에 띄우기 위한 배열
			count ++;
		}
		 System.out.print(String.valueOf(dtwValue.get(whileCount))+" ");
		 System.out.println(Result(dtw.value));
		 whileCount++;
		}
		//버스 길이 값만큼 계속 반복
		
		
		String[][] row = new String[dtwValue.size()][3];
		// 참트루값 넣기
		 for(int i=0; i <= dtwValue.size()-1;i++){
			if(  dtwValue.get(i) <= 4) {
				dtwR.add(true);
				row[i][0] = "true"; 
				row[i][1] = String.valueOf(i);
				row[i][2] = String.valueOf(dtwValue.get(i));	
				break;
				
			}
			else {
				dtwR.add(false);
				row[i][0] = "false"; 
				row[i][1] = String.valueOf(i);
				row[i][2] = String.valueOf(dtwValue.get(i));	
				
			
			}
			
		}
		
		DefaultTableModel dtm3 = new DefaultTableModel(row,columnNames);
		JTable jTable3 = new JTable(dtm3);
		JScrollPane pane3 = new JScrollPane(jTable3);
        jframe3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe3.setTitle("모든 DTW");
        jframe3.setSize(900, 900);
        jframe3.setLocationRelativeTo(null);
        jframe3.add(pane3);
        jframe3.setVisible(true);
		
		
		
	}
	
	public boolean Result(double value) {
		if(value <= 4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void dtwShowRead() {
		
		DTW dtw = new DTW( b_x, b_y,a_x, a_y);
		JFrame jframe = new JFrame();
		JFrame jframe2 = new JFrame();
		
		Double[][] row = new Double[dtw.data.length][dtw.data.length];
		Double[][] row2 = new Double[dtw.data.length][dtw.data.length];
		Vector<String> Vdata = new Vector<String>();
	    Double[] columnNames = new Double[dtw.data.length];
	    
	    
	    
	    LastDTW();
	    
	    System.out.println("화면에 나온 DTW값:"+String.format("%.2f", dtw.value));
	    
	    
	    
		for(int i=0; i<=dtw.data.length-1;i++) {
	           for(int j=0;j<=dtw.data.length-1;j++) {
	              row[i][j] = Double.parseDouble(String.format("%.2f",dtw.data[i][j]));
	              row2[i][j] = Double.parseDouble(String.format("%.2f",dtw.befordata[i][j])); // 바뀌기 전 데이터
	           }
	        }
		
		for(double i=1.0; i<dtw.data.length;i++) {
			 columnNames[(int) i] = i; // 배열 길이만큼 컬럼이 늘어난다.
			 }
		
		
		  // 값 입력
	        DefaultTableModel dtm = new DefaultTableModel(row,columnNames);
	        DefaultTableModel dtm2 = new DefaultTableModel(row2,columnNames);
	        
	        // Talbe 생성
	        JTable jTable = new JTable(dtm);
	        JTable jTable2 = new JTable(dtm2);
	        
	        // 스크롤 페널 생성
	        JScrollPane pane = new JScrollPane(jTable);
	        JScrollPane pane2 = new JScrollPane(jTable2);
	        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        jframe2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        // JFrame 기본설정
	        jframe.setTitle("DTW결과값");
	        jframe.setSize(900, 900);
	        jframe2.setTitle("DTW전 결과값");
	        jframe2.setSize(900, 900);
	        jframe.setLocationRelativeTo(null);
	        jframe2.setLocationRelativeTo(null);
	        // JFrame에 페널 추가
	        jframe.add(pane);
	        jframe2.add(pane2);
	 
	        // JFrame 화면 보이기
	        jframe.setVisible(true);
	        jframe2.setVisible(true);
	        
		
	}
	
}