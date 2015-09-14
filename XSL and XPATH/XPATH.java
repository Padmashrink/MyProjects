
package xpath;

import javax.xml.xpath.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

class XPATH {

    static void print ( Node e ) {
	if (e instanceof Text)
	    System.out.print(((Text) e).getData());
	else {
	    NodeList c = e.getChildNodes();
	    System.out.print("<"+e.getNodeName());
	    NamedNodeMap attributes = e.getAttributes();
	    for (int i = 0; i < attributes.getLength(); i++)
		System.out.print(" "+attributes.item(i).getNodeName()
				 +"=\""+attributes.item(i).getNodeValue()+"\"");
	    System.out.print(">");
	    for (int k = 0; k < c.getLength(); k++)
		print(c.item(k));
	    System.out.print("</"+e.getNodeName()+">");
	}
    }

    static void eval ( String query, String document ) throws Exception {
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	InputSource inputSource = new InputSource(document);
	NodeList result = (NodeList) xpath.evaluate(query,inputSource,XPathConstants.NODESET);
	System.out.println("XPath query: "+query);
	for (int i = 0; i < result.getLength(); i++)
	    print(result.item(i));
	System.out.println();
    }

    public static void main ( String[] args ) throws Exception {
	eval("//issue/articles/article[authors/author='David Maier']/title","SigmodRecord.xml");
	eval("//issue/articles/article[authors/author[@position='00']='David Maier']/title","SigmodRecord.xml");
	eval("//issue/articles/article[authors/author='David Maier'][authors/author='Stanley B. Zdonik']/title","SigmodRecord.xml");
	eval("//issue[volume='19' and number='2']/articles/article/title","SigmodRecord.xml");
	eval("//issue[volume='19' and number='2']/articles/article[authors/author='Jim Gray']/title | //issue[volume='19' and number='2']/articles/article[authors/author='Jim Gray']/initPage | //issue[volume='19' and number='2']/articles/article[authors/author='Jim Gray']/endPage","SigmodRecord.xml");
        eval("//issue[articles/article/authors/author='David Maier']/volume | //issue[articles/article/authors/author='David Maier']/number","SigmodRecord.xml");
	
    }
}

