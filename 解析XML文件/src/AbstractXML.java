import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.Element;


public class AbstractXML {

	/**
	 * @param args
	 * @throws DocumentException 
	*/
	public static List<String> XML_str = new ArrayList<String>();
	public static List<Leaf> nodeList = new ArrayList<Leaf>();//定义存放节点信息的数组
	static String data[][] = { { "1001", "计算机", "c语言程序设计", "谭浩强",  "35元" },
	                           { "1002", "文学", "狼图腾", "姜戎",  "29元" },
	                           { "1003", "人工智能", "机器学习", "李航", "38元" }
	                          };
	
	class Leaf
	{
		private String nodeText;//节点文本
		private String nodeAttribute;//节点属性
		private String nodePath;//节点路径
		
		public Leaf( String nodeText, String nodeAttribute, String nodePath )
		{
			this.nodeText = nodeText;
			this.nodeAttribute = nodeAttribute;
			this.nodePath = nodePath;
		}
		
		public String getText()//返回节点文本
		{
			return nodeText;
		}
		
		public String getAttribute()//返回节点属性
		{
			return nodeAttribute;
		}
		
		public String getPath()//返回节点属性
		{
			return nodePath;
		}
		
	}//定义书节点的数据结构
	
	public static void main(String[] args) throws DocumentException, IOException 
	{
		// TODO Auto-generated method stub
		
		SAXReader reader = new SAXReader();
		Document document = reader.read( new File( "E:\\围城\\zhp_weicheng01.xml" ) );
		
		Element root = document.getRootElement();
		AbstractXML object = new AbstractXML();
		object.treeTraverse( root );//递归遍历XML文件
		object.fileWriter();//将解析后的XML文件写回磁盘文件
		Leaf node;//定义一个树形节点
		
		System.out.println( "XML节点的属性信息:" );
		for( int i = 0; i < nodeList.size(); i++ )//输出节点的属性
		{
			node = nodeList.get( i );
			System.out.println( /*node.getText() + "\t" + */node.getAttribute()/* + "\t" + node.getPath()*/ );
		}
		
		object.createXMLNodes( "C:\\Users\\Thinkpad\\Desktop\\zzubylong.xml" );
		
	}//end main
	
	public void createXMLNodes( String fileName ) throws IOException
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( "booklist" );//增加根节点
		
		for( String str[] : data )
		{
			Element node1 = root.addElement( "book" );//增加一个子节点
			node1.setText( "郑州大学信息工程学院" );//增加节点的文本内容
			node1.addAttribute( "bookid", str[ 0 ] );
			node1.addAttribute( "category", str[ 1 ] );
			
			Element node2 = node1.addElement( "book1" );
			node2.addAttribute( "bookname", str[ 2 ] );
			node2.addAttribute( "author", str[ 3 ] );
			node2.addAttribute( "price", str[ 4 ] );
				
		}
		
		createXMLFile( document, fileName );
	}//end function
	
	public void createXMLFile( Document document, String fileName ) throws IOException
	{
		OutputFormat outFormat = OutputFormat.createPrettyPrint();//创建输出格式
		outFormat.setEncoding( "GBK" );//设置文本格式
		
		FileWriter fileWriter = new FileWriter( fileName );
		XMLWriter xmlWriter = new XMLWriter( fileWriter, outFormat );
		
		xmlWriter.write( document );
		xmlWriter.close();
		
		
	}//形成XML文件
	
	public String getNodeAttribute( Element e )
	{
		String attribute = "";
		DefaultAttribute attri = null;
		List attrList = e.attributes();//获取节点的属性的列表;
			
		for( int i = 0; i < attrList.size(); i++ )
		{
			attri = ( DefaultAttribute )attrList.get( i );
			attribute += "[name = " + attri.getName() + ", value = " + attri.getText() + "]";
		}
		
		return attribute;//返回节点的属性的列表字符串
		
	}
	public  void treeTraverse( Element parent )
	{
		List treeNodes = parent.elements();
		String text;
		String path;
		
		if( treeNodes.isEmpty() )//如果没有子节点
		{
			//System.out.println( parent.getTextTrim() );//输出该结点的内容
			text = parent.getTextTrim();//获取节点的文本
			path = parent.getPath();//获取节点路径
			
			nodeList.add( new Leaf( text, getNodeAttribute( parent ), path ) );//将节点的信息加入到节点的列表中
			
			XML_str.add( parent.getTextTrim() );
			return;//返回上一层
		}
		else
		{
			Iterator iterator = treeNodes.iterator();
			while( iterator.hasNext() )
			{
				Element element = (Element)iterator.next();//进行类型的转换
				
				treeTraverse( element );//进行进一步的递归调用
			}
		}
		
	}//end function
	
	public  void fileWriter() throws IOException//将解析的XNL文件写回磁盘文件中//生成XML文件
	{
		FileWriter writer = new FileWriter( new File( "f:\\zzubylong.txt" ) );
		BufferedWriter bufWriter = new BufferedWriter( writer );
		String str = "";
		
		Iterator iterator = XML_str.iterator();//使用迭代器进行列表的遍历
		
		while( iterator.hasNext() )
		{
		    bufWriter.write( (String)iterator.next() );
		    bufWriter.newLine();
		}
	
		
	}//end function
}//end class
