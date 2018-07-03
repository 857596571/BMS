package com.base.codegen.utils;

import com.base.codegen.entity.ColumnEntity;
import com.base.codegen.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		templates.add("template/Mapper.java.vm");
		templates.add("template/Mapper.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Controller.java.vm");
		templates.add("template/Test.java.vm");
		templates.add("template/list.html.vm");
		templates.add("template/form.html.vm");
		templates.add("template/menu.sql.vm");

		templates.add("template/list.js.vm");
		templates.add("template/form.js.vm");
		templates.add("template/service.js.vm");
		templates.add("template/router.js.vm");
		return templates;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, ZipOutputStream zip){
		//配置信息
		Configuration config = getConfig();

		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		//列信息
		List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);

			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		//设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);

		//封装模板数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
        map.put("package", config.getString("package"));
        map.put("module", config.getString("module"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);

			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package"), config.getString("module"))));
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
			}
		}
	}


	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RRException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String module){
		String packageJavaPath = "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packageJavaPath += packageName.replace(".", File.separator)
					+ File.separator + "modules" + File.separator
					+ File.separator + module + File.separator;
		}
		String packageJsPath = "js" + File.separator;

		if(template.contains("Entity.java.vm")){
			return packageJavaPath + "entity" + File.separator + className + ".java";
		}

		if(template.contains("Mapper.java.vm")){
			return packageJavaPath + "mapper" + File.separator + className + "Mapper.java";
		}

		if(template.contains("Mapper.xml.vm")){
			return "resources" + File.separator
					+ "mappings" + File.separator + module + File.separator +className+ "Mapper.xml";
		}

		if(template.contains("Service.java.vm")){
			return packageJavaPath + "service" + File.separator + className + "Service.java";
		}

		if(template.contains("ServiceImpl.java.vm")){
			return packageJavaPath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		if(template.contains("Controller.java.vm")){
			return packageJavaPath + "controller" + File.separator + className + "Controller.java";
		}
//		if(template.contains("Test.java.vm")){
//			return packagePath + "test" + File.separator + className + "ServiceTest.java";
//		}

		if(template.contains("list.html.vm")){
			return packageJsPath + File.separator + className.toLowerCase() + File.separator + className.toLowerCase()+"-list" + ".html";
		}

		if(template.contains("form.html.vm")){
			return "main" + File.separator + "page" +
					File.separator + className.toLowerCase()+ File.separator + className.toLowerCase() +"-form" + ".html";
		}



		if(template.contains("list.js.vm")){
			return "main" + File.separator  + "js"
					+ File.separator + className.toLowerCase() + File.separator + className.toLowerCase()+"-list" + ".js";
		}

		if(template.contains("form.js.vm")){
			return "main" + File.separator + "js" +
					File.separator + className.toLowerCase()+ File.separator + className.toLowerCase() +"-form" + ".js";
		}

		if(template.contains("service.js.vm")){
			return "main" + File.separator + "js" +
					File.separator + className.toLowerCase()+ File.separator + className.toLowerCase() +"-service" + ".js";
		}

		if(template.contains("router.js.vm")){
			return "main" + File.separator + "config" +
					File.separator + className.toLowerCase()+ File.separator + "config.router.js";
		}
		if(template.contains("menu.sql.vm")){
			return className.toLowerCase() + "_menu.sql";
		}

		return null;
	}
}