package com.qieren.tool.core;

import com.qieren.tool.util.ConfigUtil;
import com.qieren.tool.util.Util;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.Velocity;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author qieren
 * @version 1.0
 * @since 2013-11-04
 */
public class Config {
    public static final String TMP = "tmp";
    public static String TMP_DIR;
    private Project project = new Project();
    private DB db = new DB();
    private Map<String, Table> tables = new HashMap<String, Table>();
    //private Template template = new Template();

    public Project getProject() {
        return project;
    }

    public DB getDb() {
        return db;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    /*public Template getTemplate() {
        return template;
    }*/

    private Config() {
        /*this.project = new Project();
        this.db = new DB();*/
    }

    private static Config _config;

    public static void init(){
        _config = new Config();
        try {
            _config._init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Config instance() {
        if (_config == null) {
            init();
        }
        return _config;
    }

    public class DB {
        private String dbDriver;
        private String dbUrl;
        private String userName;
        private String password;

        private List<String> includeTables = new ArrayList<String>();
        private List<String> excludeTables = new ArrayList<String>();

        public String getDbDriver() {
            return dbDriver;
        }

        public void setDbDriver(String dbDriver) {
            this.dbDriver = dbDriver;
        }

        public String getDbUrl() {
            return dbUrl;
        }

        public void setDbUrl(String dbUrl) {
            this.dbUrl = dbUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getIncludeTables() {
            return includeTables;
        }

        public List<String> getExcludeTables() {
            return excludeTables;
        }

        public void addIncludeTables(String table) {
            if (table != null) {
                includeTables.add(table.toUpperCase());
            }
        }

        public void addExcludeTables(String table) {
            if (table != null) {
                excludeTables.add(table.toUpperCase());
            }
        }

        public void addIncludeTables(List<String> tables) {
            if (tables != null && !tables.isEmpty()) {
                for (String table : tables) {
                    addIncludeTables(table);
                }
            }
        }

        public void addExcludeTables(List<String> tables) {
            if (tables != null && !tables.isEmpty()) {
                for (String table : tables) {
                    addExcludeTables(table);
                }
            }
        }
    }

    public class Project {
        private String name;
        private String dir;
        private String pkg;
        private Boolean defaultGene;
        private String globalLibrary;
        private String theme;
        private List<GeneItem> geneItems = new ArrayList<GeneItem>();
        private String defaultEncoding;
        private String fk;
//        private List<>

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            if (this.name != null) {
                this.name = this.name.toLowerCase();
            }
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getDir() {
            return dir;
        }

        public String getPkg() {
            return pkg;
        }

        public Boolean getDefaultGene() {
            return defaultGene;
        }

        public void setDefaultGene(Boolean defaultGene) {
            this.defaultGene = defaultGene;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getGlobalLibrary() {
            return globalLibrary;
        }

        public void setGlobalLibrary(String globalLibrary) {
            this.globalLibrary = globalLibrary;
        }

        public List<GeneItem> getGeneItems() {
            return geneItems;
        }

        public void addGeneItems(GeneItem geneItem) {
            geneItems.add(geneItem);
        }

        public String getDefaultEncoding() {
            return defaultEncoding;
        }

        public void setDefaultEncoding(String defaultEncoding) {
            this.defaultEncoding = defaultEncoding;
        }

		public String getFk() {
			return fk;
		}

		public void setFk(String fk) {
			this.fk = fk;
		}
    }

    public class Table {
        private String tableName;
        private String boName;
        private Integer columnSize;
        private Map<String, Column> columns = new HashMap<String, Column>();
        private boolean hasQuery;
        private boolean hasListShow;
        private String displayName;
        private String fk;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getBoName() {
            return boName;
        }

        public void setBoName(String boName) {
            this.boName = boName;
        }

        public Integer getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(Integer columnSize) {
            this.columnSize = columnSize;
        }

        public Map<String, Column> getColumns() {
            return columns;
        }

        public void addColumns(String columnName, Column column) {
            columns.put(columnName, column);
        }

        public boolean getHasListShow() {
            return hasListShow;
        }

        public void setHasListShow(boolean hasListShow) {
            this.hasListShow = hasListShow;
        }

        public boolean getHasQuery() {
            return hasQuery;
        }

        public void setHasQuery(boolean hasQuery) {
            this.hasQuery = hasQuery;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

		public String getFk() {
			return fk;
		}

		public void setFk(String fk) {
			this.fk = fk;
		}
    }

    public class Column{
        private String columnName;
        private String property;
        private String inputType;
        private String typeClass;
        private Integer columnSize;
        private Boolean nullAble;
        private Integer geneType;
        private Integer colSpan;
        private Boolean query;
        private Boolean listShow;
        private String emptyText;
        private Integer order;
        private String displayName;
        private Map<String, String> extProp = new HashMap<String, String>();

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getInputType() {
            return inputType;
        }

        public void setInputType(String inputType) {
            this.inputType = inputType;
        }

        public String getTypeClass() {
            return typeClass;
        }

        public void setTypeClass(String typeClass) {
            this.typeClass = typeClass;
        }

        public Integer getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(Integer columnSize) {
            this.columnSize = columnSize;
        }

        public Boolean getNullAble() {
            return nullAble;
        }

        public void setNullAble(Boolean nullAble) {
            this.nullAble = nullAble;
        }

        public Integer getGeneType() {
            return geneType;
        }

        public void setGeneType(Integer geneType) {
            this.geneType = geneType;
        }

        public Integer getColSpan() {
            return colSpan;
        }

        public void setColSpan(Integer colSpan) {
            this.colSpan = colSpan;
        }

        public Boolean getQuery() {
            return query;
        }

        public void setQuery(Boolean query) {
            this.query = query;
        }

        public Boolean getListShow() {
            return listShow;
        }

        public void setListShow(Boolean listShow) {
            this.listShow = listShow;
        }
        
        public String getEmptyText() {
			return emptyText;
		}

		public void setEmptyText(String emptyText) {
			this.emptyText = emptyText;
		}

		public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Map<String, String> getExtProp() {
            return extProp;
        }

        public void addExtProp(String name, String value) {
            extProp.put(name, value);
        }
    }

    public class GeneItem implements Cloneable{
        private String name;
        private boolean gene;
        private String template;
        private boolean single;
        private String out;
        private String encoding;

        public GeneItem() {
        }

        public GeneItem(String name, boolean gene, String template, boolean single, String out) {
            this.name = name;
            this.gene = gene;
            this.template = getTmp(template);
            this.single = single;
            this.out = out;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isGene() {
            return gene;
        }

        public void setGene(boolean gene) {
            this.gene = gene;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = getTmp(template);
        }
        
        public boolean isSingle() {
            return single;
        }

        public void setSingle(boolean single) {
            this.single = single;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public GeneItem clone() throws CloneNotSupportedException {
            return (GeneItem) super.clone();
        }
    }

    public class Template{
        private String dir;
        private String globalLibrary;
        private Boolean extPage;
        private Map<String, String> files;

        public Template() {
            files = new HashMap<String, String>();
            globalLibrary = "tmp/global_library.vm";
            extPage = false;
            build();
        }

        public void build(){
            files.put(HBM, "tmp/conf/hbm/hbm.vm");
            files.put(BO, "tmp/src/bo.vm");
            files.put(FORM, "tmp/src/form.vm");
            files.put(ACTION, "tmp/src/action.vm");
            files.put(FACADE, "tmp/src/facade.vm");
            files.put(SERVICE, "tmp/src/service.vm");
            files.put(PAGE_LIST, "tmp/web/list"+(extPage ? "_ext.vm" : ".vm"));
            files.put(PAGE_VIEW, "tmp/web/view.vm");
            files.put(PAGE_INSERT, "tmp/web/insert.vm");
            files.put(PAGE_UPDATE, "tmp/web/update.vm");
            files.put(SPRING, "tmp/web/WEB-INF/spring.vm");
            files.put(STRUTS, "tmp/web/WEB-INF/struts.vm");
            files.put(VALIDATION, "tmp/web/WEB-INF/validation.vm");
            files.put(I18N, "tmp/conf/i18n/i18n.vm");
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            files.clear();
            this.dir = dir;
        }

        public String getGlobalLibrary() {
            return globalLibrary;
        }

        public void setGlobalLibrary(String globalLibrary) {
            this.globalLibrary = globalLibrary;
        }

        public Boolean getExtPage() {
            return extPage;
        }

        public void setExtPage(Boolean extPage) {
            this.extPage = extPage;
        }

        public String getFile(String type) {
            return files.get(type);
        }

        public void addFile(String type, String file) {
            files.put(type, file);
        }
    }

    private void _init() throws Exception {
        String fs = ConfigUtil.get("gene-xml");
        if (StringUtils.isBlank(fs)) {
            throw new RuntimeException("common-config.properties中的生成配置文件(gene-xml)为空");
        }
        String[] files = fs.split(",");
        SAXReader saxReader = new SAXReader();
        InputStream is;
        for (String file : files) {
            if (StringUtils.isBlank(file)) {
                continue;
            }
            file = file.trim();
            if (file.startsWith("classpath:")) {
                is = getClass().getResourceAsStream(file.substring(10));
            }else {
                is = new FileInputStream(file);
            }
            if (is == null) {
                throw new RuntimeException("common-config.properties中的生成配置文件(gene-xml)中的文件"+file+"不存在");
            }

            parseXml(saxReader, is);
        }

        _initVelocity();
    }

    private void _initVelocity() {
        //模块初始化
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        /*if (template.getDir() == null) {
        } else {
            p.put(Velocity.FILE_RESOURCE_LOADER_PATH, template.getDir());
        }*/
        p.put("input.encoding", "UTF-8");
        p.put("output.encoding", "UTF-8");
        p.put("velocimacro.permissions.allow.inline.to.replace.global", "true");
        //p.put("velocimacro.permissions.allow.inline.local.scope", "true");
        /*if (template.getGlobalLibrary() != null) {
        }*/
        p.put("velocimacro.library", getTmp(project.getGlobalLibrary()));
        Velocity.init(p);
    }

    private String getTmp(String tmp) {
        return TMP_DIR + tmp;
    }

    private void parseXml(SAXReader saxReader, InputStream is) throws Exception {
        Document document = saxReader.read(is);
        Element root = document.getRootElement();
        Iterator<Element> ite = root.elementIterator();
        Element element;
        while (ite.hasNext()) {
            element = ite.next();
            /*if (element.getName().equals("template")) {
                configTemplate(element);
            } else*/
            if (element.getName().equals("project")) {
                configProject(element);
            } else if (element.getName().equals("db")) {
                configDB(element);
            } else if (element.getName().equals("table")) {
                configTable(element);
            }
        }
    }

    /*private void configTemplate(Element element) {
        String dir = element.attributeValue("dir");
        if (StringUtils.isNotBlank(dir)){
            template.setDir(dir);
        }
        String globalLibrary = element.attributeValue("globalLibrary");
        if (StringUtils.isNotBlank(globalLibrary)) {
            template.setGlobalLibrary(globalLibrary);
        }
        template.setExtPage(getBoolean(element.attributeValue("extPage"), template.getExtPage()));
        template.build();
        Iterator<Element> iterator = element.elementIterator();
        Element e;
        String file;
        while (iterator.hasNext()) {
            e = iterator.next();
            file = e.attributeValue("template");
            if (StringUtils.isNotBlank(file)){
                template.addFile(e.getName(), file);
            }
        }
    }*/

    private void configTable(Element element) {
        Table table = new Table();
        table.setTableName(element.attributeValue("tableName"));
        table.setFk(element.attributeValue("fk"));
        if (table.getTableName() != null) {
            table.setTableName(table.getTableName().trim().toUpperCase());
        }
        String boName = element.attributeValue("boName");
        if (Util.isNotBlank(boName)) {
            table.setBoName(boName);
        }
        table.setDisplayName(element.attributeValue("displayName"));
        table.setColumnSize(getInt(element.attributeValue("columnSize")));
        tables.put(table.getTableName(), table);
        Iterator<Element> iterator = element.elementIterator();
        Element e;
        Column column;
        boolean hasQuery = false;
        boolean hasListShow = false;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (e.getName().equals("column")) {
                column = configColumn(e, table);
                if (!hasQuery && column.getQuery() != null && column.getQuery()){
                    hasQuery = true;
                }
                if (!hasListShow && column.getListShow() != null && column.getListShow()) {
                    hasListShow = true;
                }
            }
        }
        table.setHasQuery(hasQuery);
        table.setHasListShow(hasListShow);
    }

    private Column configColumn(Element element, Table table) {
        Column column = new Column();
        column.setColumnName(element.attributeValue("columnName"));
        if (column.getColumnName() != null) {
            column.setColumnName(column.getColumnName().trim().toUpperCase());
        }
        String property = element.attributeValue("property");
        if (Util.isNotBlank(property)) {
            column.setProperty(Util.lowerFirst(property));
        }
        column.setInputType(element.attributeValue("inputType"));
        column.setNullAble(getBoolean(element.attributeValue("nullAble")));
        column.setColumnSize(getInt(element.attributeValue("columnSize")));
        column.setTypeClass(element.attributeValue("typeClass"));
        column.setGeneType(getInt(element.attributeValue("geneType")));
        column.setColSpan(getInt(element.attributeValue("colSpan")));
        column.setQuery(getBoolean(element.attributeValue("queryCond")));
        column.setListShow(getBoolean(element.attributeValue("listShow")));
        column.setOrder(getInt(element.attributeValue("order")));
        column.setDisplayName(element.attributeValue("displayName"));

        //-------
        Iterator<Attribute> iterator = element.attributeIterator();
        Attribute attribute;
        while (iterator.hasNext()) {
            attribute = iterator.next();
            if ("ext".equals(attribute.getNamespace().getPrefix())) {
                column.addExtProp(attribute.getName(), attribute.getValue());
            }
        }

        String paraType = column.getExtProp().get("paraType");
        if ("parameter".equals(column.getInputType()) && StringUtils.isBlank(paraType)) {
            throw new RuntimeException("table:" + table.getTableName() + ",column:" + column.getColumnName() + ",inputType:parameter,ext:paraType:<isBlank>");
        }
        String dicType = column.getExtProp().get("dicType");
        if (("dicSelect".equals(column.getInputType()) || "dicTree".equals(column.getInputType())) && StringUtils.isBlank(dicType)) {
            throw new RuntimeException("table:" + table.getTableName() + ",column:" + column.getColumnName() + ",inputType:" + column.getInputType() + ",ext:dicType:<isBlank>");
        }
        //-----------

        table.addColumns(column.getColumnName(), column);
        return column;
    }

    private void configDB(Element element) {
        //db = new DB();
        db.setDbDriver(element.attributeValue("dbDriver"));
        db.setDbUrl(element.attributeValue("dbUrl"));
        db.setUserName(element.attributeValue("userName"));
        db.setPassword(element.attributeValue("password"));
        Iterator<Element> iterator = element.elementIterator();
        Element e;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (e.getName().equals("include")) {
                configInclude(e);
            } else if (e.getName().equals("exclude")) {
                configExclude(e);
            }
        }
    }

    private void configExclude(Element e) {
        String tables = e.attributeValue("tables");
        if (StringUtils.isBlank(tables)) {
            return;
        }
        tables = tables.toUpperCase();
        for (String table : tables.split(",")) {
            db.addExcludeTables(table.trim());
        }
    }

    private void configInclude(Element e) {
        String tables = e.attributeValue("tables");
        if (StringUtils.isBlank(tables)) {
            return;
        }
        tables = tables.toUpperCase();
        for (String table : tables.split(",")) {
            db.addIncludeTables(table.trim());
        }
    }

    private void configProject(Element element) throws Exception{
        //project = new Project();
        project.setName(element.attributeValue("name"));
        project.setDir(element.attributeValue("dir"));
        project.setPkg(element.attributeValue("pkg"));
        project.setFk(element.attributeValue("fk"));
        project.setDefaultGene(getBoolean(element.attributeValue("defaultGene"), false));
        String globalLibrary = element.attributeValue("globalLibrary");
        project.setGlobalLibrary(Util.isBlank(globalLibrary) ? "global_library.vm" : globalLibrary);
        String theme = element.attributeValue("theme");
        project.setTheme(Util.isBlank(theme) ? "default" : theme);
        String defaultEncoding = element.attributeValue("defaultEncoding");
        if (Util.isBlank(defaultEncoding)) {
            project.setDefaultEncoding("UTF-8");
        } else {
            project.setDefaultEncoding(defaultEncoding);
        }
        TMP_DIR = TMP + "/" + project.getTheme() + "/";
        templates = getTemplates();
        Iterator<Element> iterator = element.elementIterator();
        Element e;
        GeneItem df;
        String name;
        boolean gene;
        String template;
        String single;
        String out;
        String encoding;
        GeneItem geneItem;
        while (iterator.hasNext()) {
            e = iterator.next();
            name = e.getName();
            gene = getBoolean(e.attributeValue("gene"), project.getDefaultGene());
            template = e.attributeValue("template");
            single = e.attributeValue("single");
            out = e.attributeValue("out");
            encoding = e.attributeValue("encoding");
            df = templates.get(name);
            if (df != null) {
                geneItem = df.clone();
                geneItem.setGene(gene);
                if (Util.isNotBlank(template)) {
                    geneItem.setTemplate(template);
                }
                if (Util.isNotBlank(out)) {
                    geneItem.setOut(out);
                }
            } else {
                geneItem = new GeneItem();
                geneItem.setName(name);
                geneItem.setGene(gene);
                if (Util.isNotBlank(template)) {
                    geneItem.setTemplate(template);
                } else {
                    throw new Exception("project定义中,自定义模板" + name + "没有定义template属性!");
                }
                if (Util.isNotBlank(single)) {
                    geneItem.setSingle(getBoolean(single));
                } else {
                    throw new Exception("project定义中,自定义模板" + name + "没有定义single属性!");
                }
                if (Util.isNotBlank(out)) {
                    geneItem.setOut(out);
                } else {
                    throw new Exception("project定义中,自定义模板" + name + "没有定义out属性!");
                }
            }
            if (Util.isBlank(encoding)) {
                geneItem.setEncoding(project.getDefaultEncoding());
            }else {
                geneItem.setEncoding(encoding);
            }
            project.addGeneItems(geneItem);
        }
    }

    private Integer getInt(String v) {
        if (StringUtils.isBlank(v)) {
            return null;
        }
        try {
            return Integer.valueOf(v);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBoolean(String v, Boolean defaultValue) {
        Boolean nv = getBoolean(v);
        return nv == null ? defaultValue : nv;
    }

    private Boolean getBoolean(String v) {
        if (StringUtils.isBlank(v)) {
            return null;
        }
        v = v.trim().toLowerCase();
        return v.equals("true") || v.equals("1") || v.equals("y");
    }

    private static String HBM = "hbm";
    private static String BO = "bo";
    private static String FORM = "form";
    private static String ACTION = "action";
    private static String FACADE = "facade";
    private static String SERVICE = "service";
    private static String PAGE_LIST = "page-list";
    private static String PAGE_VIEW = "page-view";
    private static String PAGE_INSERT = "page-insert";
    private static String PAGE_UPDATE = "page-update";
    private static String SPRING = "spring";
    private static String STRUTS = "struts";
    private static String VALIDATION = "validation";
    private static String I18N = "i18n";

    private Map<String, GeneItem> templates;

    private Map<String, GeneItem> getTemplates() {
        Map<String, GeneItem> templates = new HashMap<String, GeneItem>();
        templates.put(HBM, new GeneItem(HBM, false, "conf/hbm/hbm.vm", false, "conf/hbm/${Bo}.hbm.xml"));
        templates.put(BO, new GeneItem(BO, false, "src/bo.vm", false, "src/${pkgDir}/bo/${Bo}.java"));
        templates.put(FORM, new GeneItem(FORM, false, "src/form.vm", false, "src/${pkgDir}/web/form/${Bo}Form.java"));
        templates.put(ACTION, new GeneItem(ACTION, false, "src/action.vm", false, "src/${pkgDir}/web/action/${Bo}Action.java"));
        templates.put(FACADE, new GeneItem(FACADE, false, "src/facade.vm", false, "src/${pkgDir}/facade/${Bo}Facade.java"));
        templates.put(SERVICE, new GeneItem(SERVICE, false, "src/service.vm", false, "src/${pkgDir}/service/impl/${Bo}ServiceImpl.java"));
        templates.put(PAGE_LIST, new GeneItem(PAGE_LIST, false, "web/list.vm", false, "web/${bo}/${bo}_list.jsp"));
        templates.put(PAGE_VIEW, new GeneItem(PAGE_VIEW, false, "web/view.vm", false, "web/${bo}/${bo}_view.jsp"));
        templates.put(PAGE_INSERT, new GeneItem(PAGE_INSERT, false, "web/insert.vm", false, "web/${bo}/${bo}_insert.jsp"));
        templates.put(PAGE_UPDATE, new GeneItem(PAGE_UPDATE, false, "web/update.vm", false, "web/${bo}/${bo}_update.jsp"));
        templates.put(SPRING, new GeneItem(SPRING, false, "web/WEB-INF/spring.vm", true, "web/WEB-INF/spring-${project}.xml"));
        templates.put(STRUTS, new GeneItem(STRUTS, false, "web/WEB-INF/struts.vm", true, "web/WEB-INF/struts-${project}.xml"));
        templates.put(VALIDATION, new GeneItem(VALIDATION, false, "web/WEB-INF/validation.vm", true, "web/WEB-INF/validation-${project}.xml"));
        templates.put(I18N, new GeneItem(I18N, false, "conf/i18n/i18n.vm", true, "conf/i18n/${project}.xml"));
        return templates;
    }

    public static <T> T getValue(Table table, String property, T defaultValue) {
        if (table == null) {
            return defaultValue;
        }
        try {
            Object v = PropertyUtils.getProperty(table, property);
            if (v != null) {
                if (v instanceof String && Util.isBlank((String) v)) {
                    return defaultValue;
                }
                return (T) v;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return defaultValue;
    }

    public static <T> T getValue(Table table, String columnName, String property, T defaultValue) {
        if (table == null) {
            return defaultValue;
        }
        Column column = table.getColumns().get(columnName);
        if (column == null) {
            return defaultValue;
        }
        try {
            Object v = PropertyUtils.getProperty(column, property);
            if (v != null) {
                return (T) v;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return defaultValue;
    }
}
