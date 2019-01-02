import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TEST1 {
	
	private static ArrayList<Book> bookList = new ArrayList<Book>();
	public static ArrayList<Book> getBookList() {
		return bookList;
	}
	
	private void dom4jReadXMLFile() {
		// Dom4j����books.xml
	    // �����Ķ���reader
		SAXReader reader = new SAXReader();
		
		try {
			
			Document document = reader.read(new File("E:\\1.xml"));
			Element bookstore = document.getRootElement();  //ͨ��document�����ȡ���ڵ�bookstore
			Iterator it = bookstore.elementIterator();      //ͨ��element�����elementIterator������ȡ������
			int num = 0;                                    //ȫ�ֱ�����¼�ڼ����鼮
						
			while (it.hasNext()) {                          //��������������ȡ���ڵ��е���Ϣ(�鼮) 
				
				Book bookEntity = new Book();
				
				num++;
				System.out.println("====��ʼ����" + num + "����====");
				
				Element book = (Element) it.next();            //��ȡ���ڵ���ӽڵ���Ϣ
				List<Attribute> bookAttrs = book.attributes(); // ��ȡbook���������Լ�����ֵ
				
				for (Attribute attr : bookAttrs) {             //�����ӽڵ�������б�
					System.out.println("��������" + attr.getName() + "--����ֵ��"
							                    + attr.getStringValue());
					bookEntity.setId(attr.getStringValue());
				}//end for 
						
				Iterator itt = book.elementIterator();         //�����ӽڵ����Ϣ����ȡ�ӽڵ�ĵ�����
				while (itt.hasNext()) {
					Element bookChild = (Element) itt.next();
					System.out.println("�ڵ�����" + bookChild.getName() + "--�ڵ�ֵ��"
							+ bookChild.getStringValue());
					
					if (bookChild.getName().equals("name")) {
						bookEntity.setName(bookChild.getStringValue());
					}
					
					if (bookChild.getName().equals("author")) {
						bookEntity.setAuthor(bookChild.getStringValue());
					}
					
					if (bookChild.getName().equals("year")) {
						bookEntity.setYear(bookChild.getStringValue());
					}
					
					if (bookChild.getName().equals("price")) {
						bookEntity.setPrice(bookChild.getStringValue());
					}
				}//end while
				
				// ���鼮�����鼮������
				bookList.add(bookEntity);
				// ���鼮ʵ������Ϊnull����ʡ��Դ
				bookEntity = null;
				System.out.println("====��������" + num + "����====");
				System.out.println();//����
			
			}//end while
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ��֤�鼮�������Ƿ�ɹ������鼮
		for (Book b : bookList) {
				System.out.println("++++��ʼ++++");
				System.out.println(b.getId());
				System.out.println(b.getName());
				System.out.println(b.getAuthor());
				System.out.println(b.getYear());
				System.out.println(b.getPrice());
				System.out.println("++++����++++");
				System.out.println();//����
		}//end for
   }	
	
   public static void main(String[] args) {
		// TODO Auto-generated method stub
	   TEST1 object = new TEST1();
	   object.dom4jReadXMLFile();
   }

}//end class
