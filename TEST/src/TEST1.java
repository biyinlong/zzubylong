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
		// Dom4j解析books.xml
	    // 创建的对象reader
		SAXReader reader = new SAXReader();
		
		try {
			
			Document document = reader.read(new File("E:\\1.xml"));
			Element bookstore = document.getRootElement();  //通过document对象获取根节点bookstore
			Iterator it = bookstore.elementIterator();      //通过element对象的elementIterator方法获取迭代器
			int num = 0;                                    //全局变量记录第几本书籍
						
			while (it.hasNext()) {                          //遍历迭代器，获取根节点中的信息(书籍) 
				
				Book bookEntity = new Book();
				
				num++;
				System.out.println("====开始遍历" + num + "本书====");
				
				Element book = (Element) it.next();            //获取根节点的子节点信息
				List<Attribute> bookAttrs = book.attributes(); // 获取book的属性名以及属性值
				
				for (Attribute attr : bookAttrs) {             //遍历子节点的属性列表
					System.out.println("属性名：" + attr.getName() + "--属性值："
							                    + attr.getStringValue());
					bookEntity.setId(attr.getStringValue());
				}//end for 
						
				Iterator itt = book.elementIterator();         //解析子节点的信息，获取子节点的迭代器
				while (itt.hasNext()) {
					Element bookChild = (Element) itt.next();
					System.out.println("节点名：" + bookChild.getName() + "--节点值："
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
				
				// 将书籍存入书籍集合中
				bookList.add(bookEntity);
				// 将书籍实体设置为null，节省资源
				bookEntity = null;
				System.out.println("====结束遍历" + num + "本书====");
				System.out.println();//换行
			
			}//end while
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 验证书籍集合中是否成功存入书籍
		for (Book b : bookList) {
				System.out.println("++++开始++++");
				System.out.println(b.getId());
				System.out.println(b.getName());
				System.out.println(b.getAuthor());
				System.out.println(b.getYear());
				System.out.println(b.getPrice());
				System.out.println("++++结束++++");
				System.out.println();//换行
		}//end for
   }	
	
   public static void main(String[] args) {
		// TODO Auto-generated method stub
	   TEST1 object = new TEST1();
	   object.dom4jReadXMLFile();
   }

}//end class
