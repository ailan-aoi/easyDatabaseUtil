package com.mec.dataBase.core;

import org.w3c.dom.Element;

import com.mec.dataBase.exceptions.PrimaryKeyHasMoreThanOne;
import com.my.util.core.XMLParser;

public class ClassPathXMLApplicationContext extends ClassPathApplicationContext {
	
	public ClassPathXMLApplicationContext(String xmlPath) {
		new XMLParser() {
			@Override
			public void dealElement(Element element) {
				String className = element.getAttribute("class");
				String tableName = element.getAttribute("table");
				try {
					Class<?> klass = Class.forName(className);
					TableClassDefinition tcd = new TableClassDefinition(klass, tableName);
					
					scanFields(klass, tcd);
					
					scanXMLFields(element, tcd);
					
					TableClassFactory.putDefinition(className, tcd);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}.getElementByTag(XMLParser.getDocument(xmlPath), "mapping");
	}
	
	private void scanXMLFields(Element element, TableClassDefinition tcd) {
		new XMLParser() {
			@Override
			public void dealElement(Element element) {
				String columnName = element.getAttribute("column");
				String fieldName = element.getAttribute("property");
				PropertyDefinition pd = tcd.getProperty(fieldName);
				pd.setColumn(columnName);
				
				String id = element.getAttribute("id");
				
				if (id != "") {
					try {
						setId(tcd, pd);
					} catch (PrimaryKeyHasMoreThanOne e) {
						e.printStackTrace();
					}
				}
			}
		}.getElementByTag(element, "column");
	}
}
