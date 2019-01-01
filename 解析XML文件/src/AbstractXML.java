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
	public static List<Leaf> nodeList = new ArrayList<Leaf>();//�����Žڵ���Ϣ������
	static String data[][] = { { "1001", "�����", "c���Գ������", "̷��ǿ",  "35Ԫ" },
	                           { "1002", "��ѧ", "��ͼ��", "����",  "29Ԫ" },
	                           { "1003", "�˹�����", "����ѧϰ", "�", "38Ԫ" }
	                          };
	
	class Leaf
	{
		private String nodeText;//�ڵ��ı�
		private String nodeAttribute;//�ڵ�����
		private String nodePath;//�ڵ�·��
		
		public Leaf( String nodeText, String nodeAttribute, String nodePath )
		{
			this.nodeText = nodeText;
			this.nodeAttribute = nodeAttribute;
			this.nodePath = nodePath;
		}
		
		public String getText()//���ؽڵ��ı�
		{
			return nodeText;
		}
		
		public String getAttribute()//���ؽڵ�����
		{
			return nodeAttribute;
		}
		
		public String getPath()//���ؽڵ�����
		{
			return nodePath;
		}
		
	}//������ڵ�����ݽṹ
	
	public static void main(String[] args) throws DocumentException, IOException 
	{
		// TODO Auto-generated method stub
		
		SAXReader reader = new SAXReader();
		Document document = reader.read( new File( "E:\\Χ��\\zhp_weicheng01.xml" ) );
		
		Element root = document.getRootElement();
		AbstractXML object = new AbstractXML();
		object.treeTraverse( root );//�ݹ����XML�ļ�
		object.fileWriter();//���������XML�ļ�д�ش����ļ�
		Leaf node;//����һ�����νڵ�
		
		System.out.println( "XML�ڵ��������Ϣ:" );
		for( int i = 0; i < nodeList.size(); i++ )//����ڵ������
		{
			node = nodeList.get( i );
			System.out.println( /*node.getText() + "\t" + */node.getAttribute()/* + "\t" + node.getPath()*/ );
		}
		
		object.createXMLNodes( "C:\\Users\\Thinkpad\\Desktop\\zzubylong.xml" );
		
	}//end main
	
	public void createXMLNodes( String fileName ) throws IOException
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( "booklist" );//���Ӹ��ڵ�
		
		for( String str[] : data )
		{
			Element node1 = root.addElement( "book" );//����һ���ӽڵ�
			node1.setText( "֣�ݴ�ѧ��Ϣ����ѧԺ" );//���ӽڵ���ı�����
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
		OutputFormat outFormat = OutputFormat.createPrettyPrint();//���������ʽ
		outFormat.setEncoding( "GBK" );//�����ı���ʽ
		
		FileWriter fileWriter = new FileWriter( fileName );
		XMLWriter xmlWriter = new XMLWriter( fileWriter, outFormat );
		
		xmlWriter.write( document );
		xmlWriter.close();
		
		
	}//�γ�XML�ļ�
	
	public String getNodeAttribute( Element e )
	{
		String attribute = "";
		DefaultAttribute attri = null;
		List attrList = e.attributes();//��ȡ�ڵ�����Ե��б�;
			
		for( int i = 0; i < attrList.size(); i++ )
		{
			attri = ( DefaultAttribute )attrList.get( i );
			attribute += "[name = " + attri.getName() + ", value = " + attri.getText() + "]";
		}
		
		return attribute;//���ؽڵ�����Ե��б��ַ���
		
	}
	public  void treeTraverse( Element parent )
	{
		List treeNodes = parent.elements();
		String text;
		String path;
		
		if( treeNodes.isEmpty() )//���û���ӽڵ�
		{
			//System.out.println( parent.getTextTrim() );//����ý�������
			text = parent.getTextTrim();//��ȡ�ڵ���ı�
			path = parent.getPath();//��ȡ�ڵ�·��
			
			nodeList.add( new Leaf( text, getNodeAttribute( parent ), path ) );//���ڵ����Ϣ���뵽�ڵ���б���
			
			XML_str.add( parent.getTextTrim() );
			return;//������һ��
		}
		else
		{
			Iterator iterator = treeNodes.iterator();
			while( iterator.hasNext() )
			{
				Element element = (Element)iterator.next();//�������͵�ת��
				
				treeTraverse( element );//���н�һ���ĵݹ����
			}
		}
		
	}//end function
	
	public  void fileWriter() throws IOException//��������XNL�ļ�д�ش����ļ���//����XML�ļ�
	{
		FileWriter writer = new FileWriter( new File( "f:\\zzubylong.txt" ) );
		BufferedWriter bufWriter = new BufferedWriter( writer );
		String str = "";
		
		Iterator iterator = XML_str.iterator();//ʹ�õ����������б�ı���
		
		while( iterator.hasNext() )
		{
		    bufWriter.write( (String)iterator.next() );
		    bufWriter.newLine();
		}
	
		
	}//end function
}//end class
