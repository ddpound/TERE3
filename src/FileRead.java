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
    ArrayList<String> a_x = new ArrayList<String>(); // ���� �迭
    ArrayList<String> a_y = new ArrayList<String>();
    String[] b;
    ArrayList<String> b_x = new ArrayList<String>(); // ���� �迭
    ArrayList<String> b_y = new ArrayList<String>();
    
   
    
    double[] dtwValue ;
    double dtwResult = 3000.0; // ������ ��ȯ
    
	public void Read() {
		
		// ���� ��θ� �����ͼ� �ϵ��ڵ� �κ��� �ذ��غ���
	 	   JOptionPane.showMessageDialog(null,"ù������ ���������� �ι�°�� ���������� ��������");
		   
		   JFileChooser chooser1 = new JFileChooser(); //��ü ����
		   JFileChooser chooser2 = new JFileChooser();
		   

		   int ret1 = chooser1.showOpenDialog(null);  //����â ����
		   int ret2 = chooser2.showOpenDialog(null);

		   
		 

		   if (ret1 != JFileChooser.APPROVE_OPTION) {

		    JOptionPane.showMessageDialog(null, "������ ���������ʾҽ��ϴ�.",

		      "���", JOptionPane.WARNING_MESSAGE);
		    System.exit(0);
		    

		    return;

		   }
		   if (ret2 != JFileChooser.APPROVE_OPTION) {

			    JOptionPane.showMessageDialog(null, "������ ���������ʾҽ��ϴ�.",

			      "���", JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			    return;

			   }

		   String filePath1 = chooser1.getSelectedFile().getPath();  //���ϰ�θ� ������
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
		                 a_y.add(a[2]); // 0�� �迭�� �ð� �� �־���� ��
		                 i++; // ���� ������ ī��Ʈ �� 1�� �ø���
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
	
	//ũ�⸸ŭ �ڸ��� Ȯ���Ѱ�
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
		
		// b_x,y �� �����̰� a_x,y�� ����
		int a_xLength = b_x.size(); // ���� �� �ڸ������� ���� ���� ������ ��Ʈ��, �̰� �ڸ� �迭�� ũ�Ⱚ��
		int listStart = 0 ; // ù ���� ��Ʈ���� 0 ���߿� 10�� ������
		int listLast = b_x.size(); //������ �迭
		int count =0;
		ArrayList<Boolean> dtwR = new ArrayList<Boolean>();
		ArrayList<Double> dtwValue = new ArrayList<Double>();
		
		//b_x ���� 
		
		JFrame jframe3 = new JFrame();
		
		String[] columnNames = {"��,����","�׸�","DTW"};
		
		int whileCount=0;
		while(listLast < a_x.size()) { // ���� �迭�� ũ�⺸�� Ŀ���� ����
	    
		ArrayList<String> cut_x = new ArrayList<String>();
	    ArrayList<String> cut_y = new ArrayList<String>();
		
	    for(int i =listStart; i <= listLast-1; i++) {
			cut_x.add(a_x.get(i)) ; // ���� �迭�� ��
			cut_y.add(a_y.get(i)) ;
		}
		
	    //�� cut �迭�� ������ ���� �ֽ��ϴ� DTWŬ���� �����ڿ�
		DTW dtw = new DTW( b_x, b_y,cut_x, cut_y);
		
		if(Result(dtw.value)) {
			dtwValue.add(count, dtw.value);
			System.out.print(String.valueOf(dtwValue.get(whileCount))+" ");
			System.out.println(Result(dtw.value));		
			break;
		}
		else {
			// ǥ�� ��µ� �迭�� �߰����ݴϴ�
			listStart = listStart + 10;
			listLast = listLast + 10;
			dtwValue.add(count, dtw.value); // ǥ�� ���� ���� �迭
			count ++;
		}
		 System.out.print(String.valueOf(dtwValue.get(whileCount))+" ");
		 System.out.println(Result(dtw.value));
		 whileCount++;
		}
		//���� ���� ����ŭ ��� �ݺ�
		
		
		String[][] row = new String[dtwValue.size()][3];
		// ��Ʈ�簪 �ֱ�
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
        jframe3.setTitle("��� DTW");
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
	    
	    System.out.println("ȭ�鿡 ���� DTW��:"+String.format("%.2f", dtw.value));
	    
	    
	    
		for(int i=0; i<=dtw.data.length-1;i++) {
	           for(int j=0;j<=dtw.data.length-1;j++) {
	              row[i][j] = Double.parseDouble(String.format("%.2f",dtw.data[i][j]));
	              row2[i][j] = Double.parseDouble(String.format("%.2f",dtw.befordata[i][j])); // �ٲ�� �� ������
	           }
	        }
		
		for(double i=1.0; i<dtw.data.length;i++) {
			 columnNames[(int) i] = i; // �迭 ���̸�ŭ �÷��� �þ��.
			 }
		
		
		  // �� �Է�
	        DefaultTableModel dtm = new DefaultTableModel(row,columnNames);
	        DefaultTableModel dtm2 = new DefaultTableModel(row2,columnNames);
	        
	        // Talbe ����
	        JTable jTable = new JTable(dtm);
	        JTable jTable2 = new JTable(dtm2);
	        
	        // ��ũ�� ��� ����
	        JScrollPane pane = new JScrollPane(jTable);
	        JScrollPane pane2 = new JScrollPane(jTable2);
	        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        jframe2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        // JFrame �⺻����
	        jframe.setTitle("DTW�����");
	        jframe.setSize(900, 900);
	        jframe2.setTitle("DTW�� �����");
	        jframe2.setSize(900, 900);
	        jframe.setLocationRelativeTo(null);
	        jframe2.setLocationRelativeTo(null);
	        // JFrame�� ��� �߰�
	        jframe.add(pane);
	        jframe2.add(pane2);
	 
	        // JFrame ȭ�� ���̱�
	        jframe.setVisible(true);
	        jframe2.setVisible(true);
	        
		
	}
	
}