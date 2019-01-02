import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;


public class AbstractXML 
{
	/**
	 * @param args
	 */
	
	public  LinkedHashMap<String,LinkedHashSet<String>>  LeafNodeList;//���XML���Ľṹ
	
	public AbstractXML()
	{
		this.LeafNodeList = new LinkedHashMap< String,LinkedHashSet<String> >();
	}
	/*
	 * �������ܣ���ȡXML�ļ�
	 * */
	public void ReadXML() throws DocumentException
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read( new File( "f:\\Move_scheme.xml" ) ); //���ļ�Ϊ��������Դ��
		Element root = document.getRootElement();//��ȡXML�ļ��ĸ��ڵ�
		
		List<Element> rootChildSet = root.elements();//��ȡ���ڵ�����е��ӽڵ�
		
		Element e;//XML�ڵ�
		List attrList;//�����б�
		DefaultAttribute attr;//�������Ա���
		String nodeName = "";
		
		for( int i = 0; i < rootChildSet.size(); i++ )
		{
			e = rootChildSet.get( i );//��ȡ���ڵ��һ�����ӽڵ�
			attrList = e.attributes();//��ȡһ�����ӽڵ�������б�
			
			attr = (DefaultAttribute) attrList.get( 1 );//��ȡһ�����Խڵ�������б�ĵ�2������
			nodeName = attr.getText();//��ȡһ���������Ե�ֵ
			
			List<Element> childList = e.elements();//��ȡ���ӽڵ���ӽڵ�
			LinkedHashSet<String> childNodeName = new LinkedHashSet<String> ();//���溢�ӽڵ���ӽڵ������
			
		    //System.out.println( nodeName );//������ڵ�ĺ��ӽڵ������
			
			for( int j = 0; j < childList.size(); j++ )
			{
				Element child = childList.get( j );
				List attrs = child.attributes();
				DefaultAttribute attribute = (DefaultAttribute) attrs.get( 1 );
				childNodeName.add( attribute.getText() );//���溢�ӽڵ���ӽڵ����������
			    //System.out.println( "\t" + attribute.getText() );//������ڵ�ĺ��ӽڵ���ӽڵ������
			}//end for
			
			this.LeafNodeList.put( nodeName, childNodeName );//����һ�����ڵ�ĺ��ӵ����Ƽ��亢�ӽڵ��ӽڵ������
			
		  }//end for

    }//end function
	
	/*
	 * �������ܣ����XML�ļ��Ľṹ
	 * */
	public void OutputXML() throws IOException
	{
		OutputStream os = new FileOutputStream("create1.xlsx");    
		//������   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//������һ��sheet   
		XSSFSheet sheet= wb.createSheet("1");   
		//���ɵ�һ��   
		
		LinkedHashSet<String> rootChildrenName = this.LeafNodeList.get( "MOVE" );
		Iterator iterator =  rootChildrenName.iterator();
		
		System.out.println( "MOVE" );
		
		XSSFRow row = sheet.createRow(0); 
		row.createCell(0).setCellValue("MOVE");   
		//����һ�еĵ�һ�и�ֵ   
		int i = 1;
		while( iterator.hasNext() )
		{
			String key = iterator.next().toString() ;
			
			XSSFRow row_1 = sheet.createRow( i );
			row_1.createCell( 1 ).setCellValue( key );
			i++;
					
		    System.out.println( "\t" + key );//������ڵ㺢�ӽڵ������
		}
		
		System.out.println();
		
		Iterator iterator_1 = rootChildrenName.iterator();
		while( iterator_1.hasNext() )//�������ڵ�ĺ��ӵĽڵ�
		{
			String key = iterator_1.next().toString();
			
			XSSFRow row_2 = sheet.createRow( i );
			row_2.createCell( 2 ).setCellValue( key );
					
			System.out.println( "\t" + key );//������ڵ�ĺ��ӽڵ������
			
			LinkedHashSet<String> childName = this.LeafNodeList.get( key );//��ȡ���ڵ�ĺ��ӽڵ������
			
			int j = i + 1 ;
			   
			if( childName != null )//���Move���ӽڵ���ӽڵ��Ƿ�Ϊ��//��ȡ���ϵĵ�����֮ǰ�ж��Ƿ�Ϊ��
			{
			    Iterator iterator_2 = childName.iterator();
			   			    
			    while( iterator_2.hasNext() )
			    {
				   String key_2 = iterator_2.next().toString();
				   
				   XSSFRow row_3 = sheet.createRow( j );
				   row_3.createCell( 3 ).setCellValue( key_2 );
				   
				   System.out.println( "\t\t" + key_2 );
				
				   LinkedHashSet<String> childName_1 = this.LeafNodeList.get( key_2 );
				   int k = j + 1;
				   
				   if( childName_1 != null )//ȡ���ϵĵ�����֮ǰ�ж��Ƿ�Ϊ��
				   {
					   Iterator iterator_3 = childName_1.iterator();
					   while( iterator_3.hasNext() )
					   {
						   String key_3 = iterator_3.next().toString();
						   
						   XSSFRow row_4 = sheet.createRow( k );
					       row_4.createCell( 4 ).setCellValue( key_3 );
						   
				 		   System.out.println( "\t\t\t" + key_3 );
				 		   
				 		   k++;
					    }//end while
				     }//end if
				     j = k;
					      
			       }//end while
			    
			     }//end if
			
			i = j + 1;
					
		  }//end while
		wb.write(os);   
		//�ر������   
		os.close();
	
	}//end function
	
	/*
	 * �������ܣ�дEXCEL�ļ�
	 * 
	 * */
	public void  WriteExcelFile() throws IOException, BiffException, RowsExceededException, WriteException
	{
		OutputStream os = new FileOutputStream("create.xlsx");    
		//������   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//������һ��sheet   
		XSSFSheet sheet= wb.createSheet("1");   
		//���ɵ�һ��   
		XSSFRow row = sheet.createRow(0);   
		//����һ�еĵ�һ�и�ֵ   
		row.createCell(0).setCellValue("column1");   
		//����һ�еĵ�һ�и�ֵ   
		row.createCell(1).setCellValue("column2");  
		
		XSSFRow row1 = sheet.createRow( 1 );
		row1.createCell( 3 ).setCellValue( "zzu" );
		//д�ļ�   
		wb.write(os);   
		//�ر������   
		os.close();
	}
	    
	
	public static void main(String[] args) throws DocumentException, IOException, BiffException, RowsExceededException, WriteException 
	{
		// TODO Auto-generated method stub
        
		AbstractXML XML = new AbstractXML();
		XML.ReadXML();//��ȡXML�ļ�
		XML.OutputXML();//���XML�ļ�
		
	//	XML.WriteExcelFile();
		
	}

	
}
