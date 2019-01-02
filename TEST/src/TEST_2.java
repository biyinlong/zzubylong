import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TEST_2 {
	
	private static ArrayList<Book> bookList = new ArrayList<Book>();
	private static ArrayList<Book> getBookList(){
		return bookList;
	}
	
	private void dom4jReadXMLFile() throws DocumentException{
		
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(new File("E:\\1.xml"));
		Element root = document.getRootElement();   //��ȡ�ļ��ĸ��ڵ�Ԫ��
		Iterator iterator = root.elementIterator(); //��ȡ������
		
		int number = 0;
		
		while(iterator.hasNext()){  //�������ڵ���µ��ӽڵ�
			
			Book_2 book = new Book_2();  //����һ��bookʵ�����
			number++;
			
			System.out.println("��������ʼ����  " + "����");
			
			Element e = (Element)iterator.next(); //ȡ�����ڵ��µ��ӽڵ�
			
			List<Attribute> bookAttr = e.attributes(); //��ȡ�ӽڵ��Ԫ���б�
			
			for(Attribute attr : bookAttr){
				
				System.out.println("�������� " + attr.getName() + "--����ֵ�� " + attr.getStringValue());
				book.Set_id( attr.getStringValue());
				
			}//end for
			
			
		}//end while
			
				
		
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
