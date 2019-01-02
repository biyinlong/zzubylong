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
	
	public  LinkedHashMap<String,LinkedHashSet<String>>  LeafNodeList;//存放XML树的结构
	
	public AbstractXML()
	{
		this.LeafNodeList = new LinkedHashMap< String,LinkedHashSet<String> >();
	}
	/*
	 * 函数功能：读取XML文件
	 * */
	public void ReadXML() throws DocumentException
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read( new File( "f:\\Move_scheme.xml" ) ); //以文件为输入流的源点
		Element root = document.getRootElement();//获取XML文件的根节点
		
		List<Element> rootChildSet = root.elements();//获取根节点的所有的子节点
		
		Element e;//XML节点
		List attrList;//属性列表
		DefaultAttribute attr;//单个属性变量
		String nodeName = "";
		
		for( int i = 0; i < rootChildSet.size(); i++ )
		{
			e = rootChildSet.get( i );//获取根节点的一个孩子节点
			attrList = e.attributes();//获取一个孩子节点的属性列表
			
			attr = (DefaultAttribute) attrList.get( 1 );//获取一个属性节点的属性列表的第2个属性
			nodeName = attr.getText();//获取一个具体属性的值
			
			List<Element> childList = e.elements();//获取孩子节点的子节点
			LinkedHashSet<String> childNodeName = new LinkedHashSet<String> ();//保存孩子节点的子节点的名称
			
		    //System.out.println( nodeName );//输出根节点的孩子节点的名称
			
			for( int j = 0; j < childList.size(); j++ )
			{
				Element child = childList.get( j );
				List attrs = child.attributes();
				DefaultAttribute attribute = (DefaultAttribute) attrs.get( 1 );
				childNodeName.add( attribute.getText() );//保存孩子节点的子节点的属性名称
			    //System.out.println( "\t" + attribute.getText() );//输出根节点的孩子节点的子节点的名称
			}//end for
			
			this.LeafNodeList.put( nodeName, childNodeName );//保存一个根节点的孩子的名称及其孩子节点子节点的名称
			
		  }//end for

    }//end function
	
	/*
	 * 函数功能：输出XML文件的结构
	 * */
	public void OutputXML() throws IOException
	{
		OutputStream os = new FileOutputStream("create1.xlsx");    
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("1");   
		//生成第一行   
		
		LinkedHashSet<String> rootChildrenName = this.LeafNodeList.get( "MOVE" );
		Iterator iterator =  rootChildrenName.iterator();
		
		System.out.println( "MOVE" );
		
		XSSFRow row = sheet.createRow(0); 
		row.createCell(0).setCellValue("MOVE");   
		//给这一行的第一列赋值   
		int i = 1;
		while( iterator.hasNext() )
		{
			String key = iterator.next().toString() ;
			
			XSSFRow row_1 = sheet.createRow( i );
			row_1.createCell( 1 ).setCellValue( key );
			i++;
					
		    System.out.println( "\t" + key );//输出根节点孩子节点的名称
		}
		
		System.out.println();
		
		Iterator iterator_1 = rootChildrenName.iterator();
		while( iterator_1.hasNext() )//遍历根节点的孩子的节点
		{
			String key = iterator_1.next().toString();
			
			XSSFRow row_2 = sheet.createRow( i );
			row_2.createCell( 2 ).setCellValue( key );
					
			System.out.println( "\t" + key );//输出根节点的孩子节点的名称
			
			LinkedHashSet<String> childName = this.LeafNodeList.get( key );//获取根节点的孩子节点的名称
			
			int j = i + 1 ;
			   
			if( childName != null )//检测Move孩子节点的子节点是否为空//在取集合的迭代器之前判断是否为空
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
				   
				   if( childName_1 != null )//取集合的迭代器之前判断是否为空
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
		//关闭输出流   
		os.close();
	
	}//end function
	
	/*
	 * 函数功能：写EXCEL文件
	 * 
	 * */
	public void  WriteExcelFile() throws IOException, BiffException, RowsExceededException, WriteException
	{
		OutputStream os = new FileOutputStream("create.xlsx");    
		//工作区   
		XSSFWorkbook wb = new XSSFWorkbook();   
		//创建第一个sheet   
		XSSFSheet sheet= wb.createSheet("1");   
		//生成第一行   
		XSSFRow row = sheet.createRow(0);   
		//给这一行的第一列赋值   
		row.createCell(0).setCellValue("column1");   
		//给这一行的第一列赋值   
		row.createCell(1).setCellValue("column2");  
		
		XSSFRow row1 = sheet.createRow( 1 );
		row1.createCell( 3 ).setCellValue( "zzu" );
		//写文件   
		wb.write(os);   
		//关闭输出流   
		os.close();
	}
	    
	
	public static void main(String[] args) throws DocumentException, IOException, BiffException, RowsExceededException, WriteException 
	{
		// TODO Auto-generated method stub
        
		AbstractXML XML = new AbstractXML();
		XML.ReadXML();//读取XML文件
		XML.OutputXML();//输出XML文件
		
	//	XML.WriteExcelFile();
		
	}

	
}
