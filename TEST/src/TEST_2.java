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
		Element root = document.getRootElement();   //获取文件的根节点元素
		Iterator iterator = root.elementIterator(); //获取迭代器
		
		int number = 0;
		
		while(iterator.hasNext()){  //遍历根节点的下的子节点
			
			Book_2 book = new Book_2();  //创建一个book实体对象
			number++;
			
			System.out.println("。。。开始遍历  " + "本书");
			
			Element e = (Element)iterator.next(); //取出根节点下的子节点
			
			List<Attribute> bookAttr = e.attributes(); //获取子节点的元素列表
			
			for(Attribute attr : bookAttr){
				
				System.out.println("属性名： " + attr.getName() + "--属性值： " + attr.getStringValue());
				book.Set_id( attr.getStringValue());
				
			}//end for
			
			
		}//end while
			
				
		
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
