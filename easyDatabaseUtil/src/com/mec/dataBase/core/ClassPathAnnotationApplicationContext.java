package com.mec.dataBase.core;

import java.lang.reflect.Field;

import com.mec.dataBase.annotation.Column;
import com.mec.dataBase.annotation.Id;
import com.mec.dataBase.annotation.Table;
import com.mec.dataBase.exceptions.PrimaryKeyHasMoreThanOne;
import com.my.util.core.PackageScanner;

public class ClassPathAnnotationApplicationContext extends ClassPathApplicationContext {

	public ClassPathAnnotationApplicationContext(String packageName) {
		new PackageScanner() {
			@Override
			public void dealKlass(Class<?> klass) {
				if (klass.isArray() || klass.isAnnotation()
						||klass.isEnum() || klass.isInterface() 
						|| klass.isPrimitive() || !klass.isAnnotationPresent(Table.class)) {
					return;
				}
				String tableName = klass.getAnnotation(Table.class).value();
				TableClassDefinition tcd = new TableClassDefinition(klass, tableName);
				scanFields(klass, tcd);
				try {
					dealAnnotationFileds(klass, tcd);
				} catch (PrimaryKeyHasMoreThanOne e) {
					e.printStackTrace();
					return;
				}
				
				TableClassFactory.putDefinition(klass.getName(), tcd);
			}
		}.packageScanner(packageName);
	}
	
	private void dealAnnotationFileds(Class<?> klass, TableClassDefinition tcd) throws PrimaryKeyHasMoreThanOne {
		Field[] fields = klass.getDeclaredFields();
		
		for (Field field : fields) {
			PropertyDefinition pd = tcd.getProperty(field.getName());
			if (field.isAnnotationPresent(Column.class)) {
				String columnName = field.getAnnotation(Column.class).value();
				pd.setColumn(columnName);
			}
			if (field.isAnnotationPresent(Id.class)) {
				setId(tcd, pd);
			}
		}
	}
	
}
