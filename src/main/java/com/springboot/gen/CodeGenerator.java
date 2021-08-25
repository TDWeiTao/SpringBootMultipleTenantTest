package com.springboot.gen;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

public class CodeGenerator {

    //获取 项目绝对路径
    private static String canonicalPath = "";

    public static void main(String[] args) {

        //获取项目路径
        try {
            canonicalPath = new File("").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("canonicalPath=" + canonicalPath);
//        String filePath = canonicalPath+"\\mybatisPlusGer\\src\\main\\";
//        String filePath = canonicalPath + "\\src\\main\\";

        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String childProjectPath = "/";
        String projectPath = System.getProperty("user.dir")+childProjectPath;
        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("taowei");//作者名
        gc.setSwagger2(true); //实体属性 Swagger2 注解
//        gc.setOutputDir(filePath + "java");//代码生成路径
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);//生成后打开文件夹
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
//        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //指定数据库类型，DbType中有对应的枚举类型，pom文件中要添加对应的数据库引用
//        dsc.setDbType(DbType.MYSQL);//MySQL数据库
        dsc.setDbType(DbType.POSTGRE_SQL);//PostGre数据库
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                //默认会把日期类型 转为LocalDateTime ，在查询的时候会报错，这里改为Date
                String t = fieldType.toLowerCase();
                if (t.contains("date") || t.contains("time") || t.contains("year")) {
                    return DbColumnType.DATE;
                } else {
                    return super.processTypeConvert(gc, fieldType);
                }
            }
        });
        //数据库连接配置
//        dsc.setDriverName("com.mysql.jdbc.Driver");
////      dsc.setDriverName("com.mysql.cj.jdbc.Driver"); //mysql8.0使用
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8");

        //PostGre数据库连接
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("postgres");
        dsc.setPassword("tdpassword");
        dsc.setUrl("jdbc:postgresql://114.115.160.150:5432/td_test?characterEncoding=utf8");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setTablePrefix("tb_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//采用驼峰映射
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
        strategy.setInclude(new String[]{"td_master_user"}); // 需要生成的表.如果需要生成所有的, 注释掉此行就可以
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表

        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.springboot.mutilpletenant");// 自定义包路径
//        pc.setController("controller");// 这里是控制器包名，默认 web
        pc.setMapper("mapper");// 设置Mapper包名，默认mapper
        pc.setService("service");// 设置Service包名，默认service
        pc.setEntity("entity");// 设置Entity包名，默认entity,继承的父类  已序列化
        pc.setXml("mapper.xml");// 设置Mapper XML包名，默认mapper.xml
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        // 调整 xml 生成目录演示
        List<FileOutConfig> focList = new ArrayList<>();
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        TemplateConfig templateConfig = new TemplateConfig();
        //不生成controller
        templateConfig.setController("");
        cfg.setFileOutConfigList(focList);
        mpg.setTemplate(templateConfig);
        mpg.setCfg(cfg);
        // 执行生成
        mpg.execute();
    }
}
